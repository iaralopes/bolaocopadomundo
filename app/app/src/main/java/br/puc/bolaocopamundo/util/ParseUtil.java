package br.puc.bolaocopamundo.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.puc.bolaocopamundo.entity.Aposta;
import br.puc.bolaocopamundo.entity.Bolao;
import br.puc.bolaocopamundo.entity.Estadio;
import br.puc.bolaocopamundo.entity.Jogo;
import br.puc.bolaocopamundo.entity.Pessoa;
import br.puc.bolaocopamundo.entity.Rank;
import br.puc.bolaocopamundo.entity.Selecao;

public class ParseUtil {

    /**
        Parse Apostas
     */
    public static final JSONArray parseApostasJson(List<Aposta> apostas){
        try {
            JSONArray jsonArray = new JSONArray();
            for(Aposta a : apostas) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", a.getId());
                jsonObject.put("gols_casa", a.getGolsCasa());
                jsonObject.put("gols_visitante", a.getGolsVisitante());

                jsonArray.put(jsonObject);
            }
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public static final List<Aposta> parseJsonApostas(String json){
        try {
            List<Aposta> apostas = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++) {
                Aposta aposta = new Aposta();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                aposta.setId(jsonObject.getLong("id"));
                if(!jsonObject.getString("gols_casa").equals("null")){
                    aposta.setGolsCasa(jsonObject.getInt("gols_casa"));
                }
                if(!jsonObject.getString("gols_visitante").equals("null")) {
                    aposta.setGolsVisitante(jsonObject.getInt("gols_visitante"));
                }

                JSONObject jsonJogo = jsonObject.getJSONObject("jogo");
                Jogo jogo = new Jogo();
                jogo.setId(jsonJogo.getLong("id"));
                jogo.setHorario(jsonJogo.getString("horario"));
                jogo.setNumRodada(jsonJogo.getString("num_rodada").charAt(0));

                Selecao selecaoCasa = new Selecao();
                JSONObject jsonCasa = jsonJogo.getJSONObject("selecao_casa");
                selecaoCasa.setId(jsonCasa.getLong("id"));
                selecaoCasa.setNome(jsonCasa.getString("nome"));
                selecaoCasa.setGrupo(jsonCasa.getString("grupo"));
                jogo.setCasa(selecaoCasa);
                if(!jsonJogo.getString("gols_casa").equals("null")){
                    jogo.setGolsCasa(jsonJogo.getInt("gols_casa"));
                }

                Selecao selecaoVisitante = new Selecao();
                JSONObject jsonVisitante = jsonJogo.getJSONObject("selecao_visitante");
                selecaoVisitante.setId(jsonVisitante.getLong("id"));
                selecaoVisitante.setNome(jsonVisitante.getString("nome"));
                selecaoVisitante.setGrupo(jsonVisitante.getString("grupo"));
                jogo.setVisitante(selecaoVisitante);
                if(!jsonJogo.getString("gols_visitante").equals("null")) {
                    jogo.setGolsVisitante(jsonJogo.getInt("gols_visitante"));
                }

                Estadio estadio = new Estadio();
                JSONObject jsonEstadio = jsonJogo.getJSONObject("estadio");
                estadio.setId(jsonEstadio.getLong("id"));
                estadio.setNome(jsonEstadio.getString("nome"));
                estadio.setLocal(jsonEstadio.getString("local"));
                jogo.setEstadio(estadio);

                aposta.setJogo(jogo);

                apostas.add(aposta);
            }
            return apostas;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     Parse Rank
     */
    public static final List<Rank> parseJsonRank(String json){
        try {
            List<Rank> ranks = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(json);
            JSONArray jsonApostas = jsonArray.getJSONObject(0).getJSONArray("apostas");
            JSONArray jsonConfigs = jsonArray.getJSONObject(0).getJSONArray("configs");
            if(jsonApostas.length() > 0 && jsonConfigs.length() > 0){
                Map<String, Integer> configs = new HashMap<>();
                for(int i = 0; i < jsonConfigs.length(); i++) {
                    JSONObject jsonConfig = jsonConfigs.getJSONObject(i);
                    configs.put(jsonConfig.getString("descricao"), jsonConfig.getInt("valor"));
                }

                List<Aposta> apostas = new ArrayList<>();
                Map<Long, Rank> rankMap = new HashMap<>();
                for(int i = 0; i < jsonApostas.length(); i++) {
                    Aposta a = new Aposta();
                    JSONObject jsonAposta = jsonApostas.getJSONObject(i);
                    a.setId(jsonAposta.getLong("id"));
                    if(!jsonAposta.getString("gols_casa").equals("null"))
                        a.setGolsCasa(jsonAposta.getInt("gols_casa"));
                    if(!jsonAposta.getString("gols_visitante").equals("null"))
                        a.setGolsVisitante(jsonAposta.getInt("gols_visitante"));
                    a.setJogo(new Jogo());
                    a.getJogo().setGolsCasa(jsonAposta.getJSONObject("jogo").getInt("gols_casa"));
                    a.getJogo().setGolsVisitante(jsonAposta.getJSONObject("jogo").getInt("gols_visitante"));
                    a.setMembro(new Pessoa());
                    a.getMembro().setId(jsonAposta.getJSONObject("membro").getLong("id"));
                    a.getMembro().setNome(jsonAposta.getJSONObject("membro").getString("nome"));

                    if(!rankMap.containsKey(a.getMembro().getId())){
                        rankMap.put(a.getMembro().getId(),new Rank());
                    }
                    apostas.add(a);
                }

                for(Aposta a : apostas) {
                    rankMap.get(a.getMembro().getId()).setIdPessoa(a.getMembro().getId());
                    rankMap.get(a.getMembro().getId()).setNome(a.getMembro().getNome());
                    if (a.getResultadoAposta().equals("Não apostou") || a.getResultadoAposta().equals("Errou Resultado")) {
                        rankMap.get(a.getMembro().getId()).setErros(rankMap.get(a.getMembro().getId()).getErros() + 1);
                    } else if (a.getResultadoAposta().equals("Acertou em cheio")) {
                        rankMap.get(a.getMembro().getId()).setAcertosCheio(rankMap.get(a.getMembro().getId()).getAcertosCheio() + 1);
                    } else if (a.getResultadoAposta().equals("Acertou ganhador") || a.getResultadoAposta().equals("Acertou empate")) {
                        rankMap.get(a.getMembro().getId()).setAcertosResultado(rankMap.get(a.getMembro().getId()).getAcertosResultado() + 1);
                    } else if (a.getResultadoAposta().equals("Acertou diferença de gols")) {
                        rankMap.get(a.getMembro().getId()).setAcertosDiferenca(rankMap.get(a.getMembro().getId()).getAcertosDiferenca() + 1);
                    }
                    rankMap.get(a.getMembro().getId()).calcularPontos(configs);
                }

                for (Map.Entry<Long, Rank> rank : rankMap.entrySet()){
                    ranks.add(rank.getValue());
                }
            }
            Collections.sort(ranks);
            return ranks;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static final Map<String, Integer> parseJsonConfigMap(String json){
        try {
            Map<String, Integer> configs = new HashMap<>();

            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonConfig = jsonArray.getJSONObject(i);
                configs.put(jsonConfig.getString("descricao"), jsonConfig.getInt("valor"));
            }
            return  configs;
        } catch (JSONException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     Parse Boloes
     */
    public static final List<Bolao> parseJsonBoloes(String json){
        try{
            List<Bolao> boloes = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0; i < jsonArray.length(); i++){
                Bolao bolao = new Bolao();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                bolao.setId(Long.parseLong(jsonObject.getString("id")));
                bolao.setNome(jsonObject.getString("nome").toString());
                bolao.setNumGanhadores(Integer.parseInt(jsonObject.getString("num_ganhadores")));
                bolao.setIdCriador(Long.parseLong(jsonObject.getString("id_criador")));

                boloes.add(bolao);
            }
            return boloes;
        }catch(JSONException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     Parse Pessoa
     */
    public static final JSONObject parsePessoaJson(Pessoa pessoa){
        try {
            JSONObject jsonPessoa = new JSONObject();
            if (pessoa.getId() != null) {
                jsonPessoa.put("id", pessoa.getId());
            }
            if (pessoa.getNome() != null) {
                jsonPessoa.put("nome", pessoa.getNome());
            }
            if (pessoa.getEmail() != null) {
                jsonPessoa.put("email", pessoa.getEmail());
            }
            if (pessoa.getSenha() != null) {
                jsonPessoa.put("senha", HashUtil.md5(pessoa.getSenha()));
            }
            jsonPessoa.put("facebook", pessoa.getFacebookInteger());

            return jsonPessoa;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public static final Pessoa parseJsonPessoa(String json){
        try {
            Pessoa pessoa = new Pessoa();

            JSONArray jsonArray = new JSONArray(json);

            JSONObject jsonPessoa = jsonArray.getJSONObject(0);
            pessoa.setId(Long.parseLong(jsonPessoa.getString("id")));
            pessoa.setNome(jsonPessoa.getString("nome"));
            pessoa.setEmail(jsonPessoa.getString("email"));
            pessoa.setFacebookInteger(Integer.parseInt(jsonPessoa.getString("facebook")));

            return pessoa;
        }catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public static final List<Pessoa> parseJsonPessoas(String json){
        try {
            List<Pessoa> pessoas = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(json);

            for(int i=0; i < jsonArray.length(); i++) {
                Pessoa pessoa = new Pessoa();

                JSONObject jsonPessoa = jsonArray.getJSONObject(i);
                pessoa.setId(Long.parseLong(jsonPessoa.getString("id")));
                pessoa.setNome(jsonPessoa.getString("nome"));
                pessoa.setEmail(jsonPessoa.getString("email"));
                pessoa.setFacebookInteger(Integer.parseInt(jsonPessoa.getString("facebook")));

                pessoas.add(pessoa);
            }

            return pessoas;
        }catch(JSONException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     Parse Estadio
     */
    public static final List<Estadio> parseJsonEstadios(String json){
        try {
            List<Estadio> estadios = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(json);

            for(int i=0; i < jsonArray.length(); i++) {
                Estadio estadio = new Estadio();
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                estadio.setId(Long.parseLong(jsonobject.getString("id")));
                estadio.setNome(jsonobject.getString("nome"));
                estadio.setLocal(jsonobject.getString("local"));

                estadios.add(estadio);
            }

            return estadios;
        }catch (JSONException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
