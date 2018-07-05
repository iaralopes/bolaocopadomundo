package br.puc.bolaocopamundo.entity;

import java.util.List;

public class Bolao {

    /**
     Attributes
     */

    private Long id;
    private String nome;
    private Integer numGanhadores;
    private List<Pessoa> membros;
    private Long idCriador;

    /**
     Constructors
     */

    public Bolao(){

    }

    public Bolao(Long id, String nome, Integer numGanhadores, Long idCriador){
        this.id = id;
        this.nome = nome;
        this.numGanhadores = numGanhadores;
        this.idCriador = idCriador;
    }

    public Bolao(Long id, String nome, Integer numGanhadores, Long idCriador, List<Pessoa> membros){
        this.id = id;
        this.nome = nome;
        this.numGanhadores = numGanhadores;
        this.idCriador = idCriador;
        this.membros = membros;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumGanhadores() {
        return numGanhadores;
    }

    public void setNumGanhadores(Integer numGanhadores) {
        this.numGanhadores = numGanhadores;
    }

    public List<Pessoa> getMembros() {
        return membros;
    }

    public void setMembros(List<Pessoa> membros) {
        this.membros = membros;
    }

    public Long getIdCriador() {
        return idCriador;
    }

    public void setIdCriador(Long idCriador) {
        this.idCriador = idCriador;
    }
}
