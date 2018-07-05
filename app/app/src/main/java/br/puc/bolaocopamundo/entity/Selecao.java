package br.puc.bolaocopamundo.entity;

public class Selecao {

    /**
     Attributes
     */

    private Long id;
    private String nome;
    private String grupo;
    private Boolean eliminado;

    /**
     Constructors
     */

    public Selecao(){

    }

    public Selecao(Long id, String nome, String grupo, Boolean eliminado){
        this.id = id;
        this.nome = nome;
        this.grupo = grupo;
        this.eliminado = eliminado;
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

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

}
