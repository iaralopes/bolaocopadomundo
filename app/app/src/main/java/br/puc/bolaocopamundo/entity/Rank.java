package br.puc.bolaocopamundo.entity;

import android.support.annotation.NonNull;

import java.util.Map;

public class Rank implements Comparable<Rank>{

    /**
     Attributes
     */
    private Long idPessoa;
    private String nome;
    private Integer acertosCheio = 0;
    private Integer acertosDiferenca = 0;
    private Integer acertosResultado = 0;
    private Integer erros = 0;
    private Integer pontos;

    /**
     Constructors
     */

    public Rank(){

    }

    public Rank(Long idPessoa, String nome, Integer acertosCheio, Integer acertosDiferenca, Integer acertosResultado, Integer erros){
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.acertosCheio = acertosCheio;
        this.acertosDiferenca = acertosDiferenca;
        this.acertosResultado = acertosResultado;
        this.erros = erros;
    }

    public Rank(Long idPessoa, String nome, Integer acertosCheio, Integer acertosDiferenca, Integer acertosResultado, Integer erros, Integer pontos){
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.acertosCheio = acertosCheio;
        this.acertosDiferenca = acertosDiferenca;
        this.acertosResultado = acertosResultado;
        this.erros = erros;
        this.pontos = pontos;
    }

    public void calcularPontos(Map<String, Integer> configs){
        pontos = 0;
        pontos += acertosCheio * configs.get("acerto-cheio");
        pontos += acertosDiferenca * configs.get("acerto-diferenca");
        pontos += acertosResultado * configs.get("acerto-ganhador");
        pontos += erros * configs.get("erro");
    }

    @Override
    public int compareTo( Rank rank) {
        return rank.pontos.compareTo(this.pontos);
    }

    /**
     Getters & Setters
     */

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAcertosCheio() {
        return acertosCheio;
    }

    public void setAcertosCheio(Integer acertosCheio) {
        this.acertosCheio = acertosCheio;
    }

    public Integer getAcertosDiferenca() {
        return acertosDiferenca;
    }

    public void setAcertosDiferenca(Integer acertosDiferenca) {
        this.acertosDiferenca = acertosDiferenca;
    }

    public Integer getAcertosResultado() {
        return acertosResultado;
    }

    public void setAcertosResultado(Integer acertosResultado) {
        this.acertosResultado = acertosResultado;
    }

    public Integer getErros() {
        return erros;
    }

    public void setErros(Integer erros) {
        this.erros = erros;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public String getPontuacao(){
        return "AC: " + acertosCheio + " | AD: " + acertosDiferenca + " | AR: " + acertosResultado + " | E: " + erros + " | Pontos: " + pontos;
    }

}
