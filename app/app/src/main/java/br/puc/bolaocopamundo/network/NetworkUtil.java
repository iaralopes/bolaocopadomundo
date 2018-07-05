package br.puc.bolaocopamundo.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtil {

    public static final String ENDERECO_FUNCOES = "http://www.esbolao.hospedagemdesites.ws/functions/";
    public static final String ENDERECO_APK = "http://www.esbolao.hospedagemdesites.ws/apk/BolaoCopaMundo.apk";

    //Responsavel por carregar o Objeto JSON
    public String excuteGET(String url){
        try {
            URL apiEnd = new URL(url);
            InputStream is;
            String retorno;

            HttpURLConnection conexao = (HttpURLConnection) apiEnd.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.setDoInput(true);
            conexao.setDoOutput(true);
            conexao.connect();

            int codigoResposta = conexao.getResponseCode();
            if(codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                is = conexao.getInputStream();
            }else{
                is = conexao.getErrorStream();
            }

            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();
            return retorno;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public String excutePost(String urlpath, String json) {
        HttpURLConnection connection = null;
        try {
            URL url=new URL(urlpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            streamWriter.write(json);
            streamWriter.flush();
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                Log.d("POST", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e("POST", connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception){
            Log.e("POST", exception.toString());
            exception.printStackTrace();
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    private static String converterInputStreamToString(InputStream is) {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(is));
            while ((linha = br.readLine()) != null) {
                buffer.append(linha);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }
}
