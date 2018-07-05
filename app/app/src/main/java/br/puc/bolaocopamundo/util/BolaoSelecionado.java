package br.puc.bolaocopamundo.util;

import br.puc.bolaocopamundo.entity.Bolao;

public class BolaoSelecionado {

    private static Bolao bolao;

    public BolaoSelecionado(){

    }

    public BolaoSelecionado(Bolao bolao){
        this.setBolao(bolao);
    }

    public static Bolao getBolao() {
        return bolao;
    }

    public static void setBolao(Bolao bolao) {
        BolaoSelecionado.bolao = bolao;
    }

}
