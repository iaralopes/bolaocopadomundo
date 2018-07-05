package br.puc.bolaocopamundo.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.puc.bolaocopamundo.entity.Aposta;
import br.puc.bolaocopamundo.entity.Estadio;
import br.puc.bolaocopamundo.entity.Jogo;
import br.puc.bolaocopamundo.entity.Pessoa;
import br.puc.bolaocopamundo.entity.Rank;
import br.puc.bolaocopamundo.entity.Selecao;
import br.puc.bolaocopamundo.network.NetworkUtil;
import br.puc.bolaocopamundo.util.ParseUtil;

public class ApostaService {

    private final String ENDPOINT_BUSCAR_APOSTAS_CONCLUIDAS = NetworkUtil.ENDERECO_FUNCOES + "buscarApostasConcluidas.php";
    private final String ENDPOINT_BUSCAR_APOSTAS_PENDENTES = NetworkUtil.ENDERECO_FUNCOES + "buscarApostasPendentes.php";
    private final String ENDPOINT_BUSCAR_APOSTAS_BOLAO = NetworkUtil.ENDERECO_FUNCOES + "buscarRankBolao.php";
    private final String ENDPOINT_ATUALIZAR_APOSTA = NetworkUtil.ENDERECO_FUNCOES + "atualizarAposta.php";

    private NetworkUtil networkUtils;

    public List<Aposta> buscarApostasConcluidas(Long idPessoa, Long idBolao){
        networkUtils = new NetworkUtil();

        String parametros = "?id_pessoa=" + idPessoa + "&id_bolao=" + idBolao;
        String resposta = networkUtils.excuteGET(ENDPOINT_BUSCAR_APOSTAS_CONCLUIDAS + parametros).trim();

        return ParseUtil.parseJsonApostas(resposta);
    }

    public List<Aposta> buscarApostasPendentes(Long idPessoa, Long idBolao){
        networkUtils = new NetworkUtil();

        String parametros = "?id_pessoa=" + idPessoa + "&id_bolao=" + idBolao;
        String resposta = networkUtils.excuteGET(ENDPOINT_BUSCAR_APOSTAS_PENDENTES + parametros);

        return ParseUtil.parseJsonApostas(resposta);
    }

    public List<Rank> buscarApostasBolao(Long idBolao){
        networkUtils = new NetworkUtil();

        String parametros = "?id_bolao=" + idBolao;
        String resposta = networkUtils.excuteGET(ENDPOINT_BUSCAR_APOSTAS_BOLAO + parametros).trim();

        return ParseUtil.parseJsonRank(resposta);
    }

    public Boolean atualizarAposta(List<Aposta> apostas){
        try {
            JSONArray jsonAposta = ParseUtil.parseApostasJson(apostas);

            networkUtils = new NetworkUtil();

            String resposta = networkUtils.excutePost(ENDPOINT_ATUALIZAR_APOSTA, jsonAposta.toString()).trim();
            return (resposta.equals("1")? true : false);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
