package br.puc.bolaocopamundo.entity;

public class Estadio {

    /**
     Attributes
     */

    private Long id;
    private String nome;
    private String local;

    /**
     Constructors
     */

    public Estadio(){

    }

    public Estadio(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public Estadio(Long id, String nome, String local){
        this.id = id;
        this.nome = nome;
        this.local = local;
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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

}
