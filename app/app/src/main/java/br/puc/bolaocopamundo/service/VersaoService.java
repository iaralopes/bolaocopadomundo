package br.puc.bolaocopamundo.service;

import android.content.Intent;
import android.util.Log;

import br.puc.bolaocopamundo.activity.CadastroUsuarioActivity;
import br.puc.bolaocopamundo.activity.LoginActivity;
import br.puc.bolaocopamundo.network.NetworkUtil;

public class VersaoService {

    private final String ENDPOINT_VERSAO = NetworkUtil.ENDERECO_FUNCOES + "versao.php";

    private NetworkUtil networkUtils;

    public Boolean verificaVersao(String v){
        networkUtils = new NetworkUtil();

        String resposta = networkUtils.excuteGET(ENDPOINT_VERSAO).trim();
        return resposta.equals(v);
    }

}
