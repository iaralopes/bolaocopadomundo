package br.puc.bolaocopamundo.entity;

import java.util.List;

public class Pessoa {

    /**
     Attributes
     */

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Boolean facebook = false;
    private List<Bolao> boloes;

    /**
     Constructors
     */

    public Pessoa(){

    }

    public Pessoa(Long id, String nome, String email, String senha, Boolean facebook){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.setFacebook(facebook);
    }

    public Pessoa(Long id, String nome, String email, String senha, List<Bolao> boloes){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.boloes = boloes;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getFacebook() {
        return facebook;
    }

    public void setFacebook(Boolean facebook) {
        this.facebook = facebook;
    }

    public Integer getFacebookInteger(){
        if(facebook){
            return 1;
        }else{
            return 0;
        }
    }

    public void setFacebookInteger(Integer facebook){
        if(facebook == 1){
            this.facebook = true;
        }else{
            this.facebook = false;
        }
    }

    public List<Bolao> getBoloes() {
        return boloes;
    }

    public void setBoloes(List<Bolao> boloes) {
        this.boloes = boloes;
    }
}
