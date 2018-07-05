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

public class ListaEstadioAdapter extends ArrayAdapter<Estadio> {

    private Context context;
    private List<Estadio> listaEstadio;
    private static LayoutInflater inflater = null;

    public ListaEstadioAdapter(Context context, int textViewResourceId, List<Estadio> listaEstadio) {
        super(context, 0, listaEstadio);
        this.context = context;
        this.listaEstadio = listaEstadio;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Estadio pontuacaoEstadio = this.listaEstadio.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.adapter_lista_estadio, null);

        TextView textId = (TextView) convertView.findViewById(R.id.textId);
        TextView textNome = (TextView) convertView.findViewById(R.id.textNome);
        TextView textLocal = (TextView) convertView.findViewById(R.id.textLocal);
        textId.setText(pontuacaoEstadio.getId().toString());
        textNome.setText(pontuacaoEstadio.getNome());
        textLocal.setText(pontuacaoEstadio.getLocal().toString());

        return convertView;
    }

}
