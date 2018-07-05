package br.puc.bolaocopamundo.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.puc.bolaocopamundo.network.NetworkUtil;
import br.puc.bolaocopamundo.entity.Pessoa;
import br.puc.bolaocopamundo.util.HashUtil;
import br.puc.bolaocopamundo.util.ParseUtil;

public class UsuarioService {

    private final String ENDPOINT_CADASTRO_PESSOAS = NetworkUtil.ENDERECO_FUNCOES + "inserirPessoas.php";
    private final String ENDPOINT_LOGIN_FACEBOOK = NetworkUtil.ENDERECO_FUNCOES + "validarLoginFacebook.php";
    private final String ENDPOINT_LOGIN = NetworkUtil.ENDERECO_FUNCOES + "validarLogin.php";

    private NetworkUtil networkUtils;

    public Boolean realizarCadastro(Pessoa pessoa) {
        JSONObject jsonPessoa = ParseUtil.parsePessoaJson(pessoa);
        networkUtils = new NetworkUtil();
        String resposta = networkUtils.excutePost(ENDPOINT_CADASTRO_PESSOAS, jsonPessoa.toString()).trim();
        return (resposta.equals("1")? true : false);
    }

    public Pessoa realizarLogin(Pessoa pessoa) {
        JSONObject jsonPessoa = ParseUtil.parsePessoaJson(pessoa);
        networkUtils = new NetworkUtil();
        String resposta;
        if(pessoa.getFacebook()){
            resposta = networkUtils.excutePost(ENDPOINT_LOGIN_FACEBOOK, jsonPessoa.toString()).trim();
        }else{
            resposta = networkUtils.excutePost(ENDPOINT_LOGIN, jsonPessoa.toString()).trim();
        }
        if(!resposta.equals("0")){
            return ParseUtil.parseJsonPessoa(resposta);
        }else{
            return null;
        }
    }

}

