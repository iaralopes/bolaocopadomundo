package br.puc.bolaocopamundo.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.puc.bolaocopamundo.entity.Bolao;
import br.puc.bolaocopamundo.network.NetworkUtil;


import br.puc.bolaocopamundo.entity.Pessoa;
import br.puc.bolaocopamundo.util.ParseUtil;
import br.puc.bolaocopamundo.util.SessionManager;

public class BolaoService {

    private final String ENDPOINT_BOLOES_USUARIO = NetworkUtil.ENDERECO_FUNCOES + "buscarBoloesUsuario.php";
    private final String ENDPOINT_CADASTRO_BOLAO = NetworkUtil.ENDERECO_FUNCOES + "inserirBolao.php";
    private final String ENDPOINT_MEMBROS_BOLAO = NetworkUtil.ENDERECO_FUNCOES + "membrosBolao.php";
    private final String ENDPOINT_CONVIDAR_MEMBRO_BOLAO = NetworkUtil.ENDERECO_FUNCOES + "inserirMembroBolao.php";
    private final String ENDPOINT_REMOVER_MEMBRO_BOLAO = NetworkUtil.ENDERECO_FUNCOES + "removerMembroBolao.php";

    private NetworkUtil networkUtils;

    public List<Bolao> buscarBoloesUsuario(Long id){
        networkUtils = new NetworkUtil();

        String resposta = networkUtils.excuteGET(ENDPOINT_BOLOES_USUARIO + "?id=" + id);

        return ParseUtil.parseJsonBoloes(resposta);
    }

    public Boolean realizarCadastro(Bolao bolao) {
        try {
            JSONObject jsonBolao = new JSONObject();

            jsonBolao.put("nome", bolao.getNome());
            jsonBolao.put("num_ganhadores", bolao.getNumGanhadores());
            jsonBolao.put("id_criador", bolao.getIdCriador());

            networkUtils = new NetworkUtil();

            String resposta = networkUtils.excutePost(ENDPOINT_CADASTRO_BOLAO, jsonBolao.toString()).trim();
            return (resposta.equals("1")? true : false);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Pessoa> buscarMembrosBolao(Long idbolao){
        networkUtils = new NetworkUtil();

        String resposta = networkUtils.excuteGET(ENDPOINT_MEMBROS_BOLAO + "?id=" + idbolao);

        return ParseUtil.parseJsonPessoas(resposta);
    }

    public Boolean convidarMembro(String email, Long idBolao) {
        try {
            JSONObject jsonConvite = new JSONObject();

            jsonConvite.put("email", email);
            jsonConvite.put("id_bolao", idBolao);

            networkUtils = new NetworkUtil();

            String resposta = networkUtils.excutePost(ENDPOINT_CONVIDAR_MEMBRO_BOLAO, jsonConvite.toString()).trim();
            return (resposta.equals("1")? true : false);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Boolean removerMembro(Long idMembro, Long idBolao) {
        try {
            JSONObject jsonRemover = new JSONObject();

            jsonRemover.put("id_pessoa", idMembro);
            jsonRemover.put("id_bolao", idBolao);

            networkUtils = new NetworkUtil();

            String resposta = networkUtils.excutePost(ENDPOINT_REMOVER_MEMBRO_BOLAO, jsonRemover.toString()).trim();
            Log.d("resposta", resposta);
            return (resposta.equals("1")? true : false);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
