package br.puc.bolaocopamundo.entity;

public class Aposta {

    /**
     Attributes
     */

    private Long id;
    private Jogo jogo;
    private Pessoa membro;
    private Bolao bolao;
    private Integer golsCasa;
    private Integer golsVisitante;

    /**
     Constructors
     */

    public Aposta(){

    }

    public Aposta(Long id, Jogo jogo, Integer golsCasa, Integer golsVisitante){
        this.id = id;
        this.jogo = jogo;
        this.golsCasa = golsCasa;
        this.golsVisitante = golsVisitante;
    }

    public Aposta(Long id, Jogo jogo, Pessoa membro, Bolao bolao, Integer golsCasa, Integer golsVisitante){
        this.id = id;
        this.jogo = jogo;
        this.membro = membro;
        this.bolao = bolao;
        this.golsCasa = golsCasa;
        this.golsVisitante = golsVisitante;
    }

    /**
     Getters & Setters
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Pessoa getMembro() {
        return membro;
    }

    public void setMembro(Pessoa membro) {
        this.membro = membro;
    }

    public Bolao getBolao() {
        return bolao;
    }

    public void setBolao(Bolao bolao) {
        this.bolao = bolao;
    }

    public Integer getGolsCasa() {
        return golsCasa;
    }

    public void setGolsCasa(Integer golsCasa) {
        this.golsCasa = golsCasa;
    }

    public Integer getGolsVisitante() {
        return golsVisitante;
    }

    public void setGolsVisitante(Integer golsVisitante) {
        this.golsVisitante = golsVisitante;
    }

    public String getValoresAposta(){
        if(golsCasa == null || golsVisitante == null){
            return "";
        }
        return "Aposta: " + golsCasa + " X " + golsVisitante;
    }

    public String getResultadoAposta(){
        if(jogo.getGolsCasa() == null || jogo.getGolsVisitante() == null){
            return "Aguardando resultado";
        }else if(golsCasa == null || golsVisitante == null){
            return "Não apostou";
        }else if(golsCasa == jogo.getGolsCasa() && golsVisitante == jogo.getGolsVisitante()){
            return "Acertou em cheio";
        }else if(golsCasa == golsVisitante && jogo.getGolsCasa() == jogo.getGolsVisitante()){
            return "Acertou empate";
        }else if(golsCasa - golsVisitante == jogo.getGolsCasa() - jogo.getGolsVisitante()){
            return "Acertou diferença de gols";
        }else if((golsCasa > golsVisitante && jogo.getGolsCasa() > jogo.getGolsVisitante())
                || (golsCasa < golsVisitante && jogo.getGolsCasa() < jogo.getGolsVisitante())){
            return "Acertou ganhador";
        }else{
            return "Errou Resultado";
        }
    }

}
