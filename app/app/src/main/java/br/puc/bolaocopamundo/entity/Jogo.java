package br.puc.bolaocopamundo.entity;

import java.sql.Timestamp;

public class Jogo {

    /**
     Attributes
     */

    private Long id;
    private String horario;
    private Selecao casa;
    private Integer golsCasa;
    private Selecao visitante;
    private Integer golsVisitante;
    private Character numRodada;
    private Estadio estadio;

    /**
     Constructors
     */

    public Jogo(){

    }

    public Jogo(Long id, String horario, Selecao casa,Selecao visitante, Character numRodada, Estadio estadio){
        this.id = id;
        this.horario = horario;
        this.casa = casa;
        this.visitante = visitante;
        this.numRodada = numRodada;
        this.estadio = estadio;
    }

    public Jogo(Long id, String horario, Selecao casa, Integer golsCasa, Selecao visitante, Integer golsVisitante, Character numRodada, Estadio estadio){
        this.id = id;
        this.horario = horario;
        this.casa = casa;
        this.golsCasa = golsCasa;
        this.visitante = visitante;
        this.golsVisitante = golsVisitante;
        this.numRodada = numRodada;
        this.estadio = estadio;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Selecao getCasa() {
        return casa;
    }

    public void setCasa(Selecao casa) {
        this.casa = casa;
    }

    public Integer getGolsCasa() {
        return golsCasa;
    }

    public void setGolsCasa(Integer golsCasa) {
        this.golsCasa = golsCasa;
    }

    public Selecao getVisitante() {
        return visitante;
    }

    public void setVisitante(Selecao visitante) {
        this.visitante = visitante;
    }

    public Integer getGolsVisitante() {
        return golsVisitante;
    }

    public void setGolsVisitante(Integer golsVisitante) {
        this.golsVisitante = golsVisitante;
    }

    public Character getNumRodada() {
        return numRodada;
    }

    public void setNumRodada(Character numRodada) {
        this.numRodada = numRodada;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public String getHorarioFormatado(){
        String data[] = horario.split(" ")[0].split("-");
        String hora[] = horario.split(" ")[1].split(":");

        return hora[0] + ":" + hora[1] + " " + data[2] + "/" + data[1] + "/" + data[0];
    }

    public String getSelecoesResultado(){
        if(golsCasa == null || golsVisitante == null){
            return casa.getNome() + " X " + visitante.getNome();
        }
        return casa.getNome() + " " + golsCasa + " X " + golsVisitante + " " + visitante.getNome();
    }

    public String getHorarioLocal(){
        return getHorarioFormatado() + " " + estadio.getNome() + "(" + estadio.getLocal() + ")";
    }

}
