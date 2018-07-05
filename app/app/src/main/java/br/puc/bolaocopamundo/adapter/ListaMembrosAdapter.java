package br.puc.bolaocopamundo.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.entity.Estadio;
import br.puc.bolaocopamundo.entity.Pessoa;

public class ListaMembrosAdapter extends ArrayAdapter<Pessoa> {

    private Context context;
    private List<Pessoa> listaMembros;
    private static LayoutInflater inflater = null;

    public ListaMembrosAdapter(Context context, int textViewResourceId, List<Pessoa> listaMembros) {
        super(context, 0, listaMembros);
        this.context = context;
        this.listaMembros = listaMembros;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Pessoa membroPosicao = this.listaMembros.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.adapter_lista_membros, null);

        TextView textNome = (TextView) convertView.findViewById(R.id.textNome);
        textNome.setText(membroPosicao.getNome());

        return convertView;
    }

}
