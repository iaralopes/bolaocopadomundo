package br.puc.bolaocopamundo.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.puc.bolaocopamundo.entity.Estadio;
import br.puc.bolaocopamundo.network.NetworkUtil;
import br.puc.bolaocopamundo.util.ParseUtil;

public class EstadioService {

    private final String ENDPOINT_ESTADIOS = NetworkUtil.ENDERECO_FUNCOES + "buscarEstadios.php";

    private NetworkUtil networkUtils;

    public List<Estadio> buscarEstadios(){
        networkUtils = new NetworkUtil();

        String resposta = networkUtils.excuteGET(ENDPOINT_ESTADIOS);

        return ParseUtil.parseJsonEstadios(resposta);
    }

}
