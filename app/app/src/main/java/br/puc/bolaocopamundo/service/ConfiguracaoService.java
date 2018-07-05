package br.puc.bolaocopamundo.service;

import android.util.Log;

import java.util.Map;

import br.puc.bolaocopamundo.network.NetworkUtil;
import br.puc.bolaocopamundo.util.ParseUtil;

public class ConfiguracaoService {

    private final String ENDPOINT_CONFIGURACOES = NetworkUtil.ENDERECO_FUNCOES + "buscarConfiguracoes.php";

    private NetworkUtil networkUtils;

    public Map<String, Integer> buscarConfiguracoes(){
        networkUtils = new NetworkUtil();

        String resposta = networkUtils.excuteGET(ENDPOINT_CONFIGURACOES).trim();
        return ParseUtil.parseJsonConfigMap(resposta);
    }

}
