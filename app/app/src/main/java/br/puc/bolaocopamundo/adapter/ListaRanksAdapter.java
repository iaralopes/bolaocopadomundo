package br.puc.bolaocopamundo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.entity.Rank;

public class ListaRanksAdapter extends ArrayAdapter<Rank> {

    private Context context;
    private List<Rank> listaRanks;

    public ListaRanksAdapter(Context context, int textViewResourceId, List<Rank> listaRanks) {
        super(context, 0, listaRanks);
        this.context = context;
        this.listaRanks = listaRanks;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Rank posicaoRank = this.listaRanks.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.adapter_lista_ranks, null);

        TextView txtNome = (TextView) convertView.findViewById(R.id.txtNome);
        txtNome.setText(posicaoRank.getNome());
        TextView txtPontuacao = (TextView) convertView.findViewById(R.id.txtPontuacao);
        txtPontuacao.setText(posicaoRank.getPontuacao());

        return convertView;
    }

}
