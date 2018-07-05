package br.puc.bolaocopamundo.util;

import br.puc.bolaocopamundo.entity.Rank;

public class RankSelecionado {

    private static Rank rank;

    public RankSelecionado(){

    }

    public RankSelecionado(Rank rank){
        RankSelecionado.rank = rank;
    }

    public static Rank getRank() {
        return rank;
    }

    public static void setRank(Rank rank) {
        RankSelecionado.rank = rank;
    }
}
