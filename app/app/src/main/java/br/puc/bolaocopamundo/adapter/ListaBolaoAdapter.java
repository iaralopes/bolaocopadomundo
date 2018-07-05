package br.puc.bolaocopamundo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.entity.Bolao;
import br.puc.bolaocopamundo.entity.Estadio;

public class ListaBolaoAdapter extends ArrayAdapter<Bolao> {

    private Context context;
    private List<Bolao> listaBolao;
    private static LayoutInflater inflater = null;

    public ListaBolaoAdapter(Context context, int textViewResourceId, List<Bolao> listaBolao) {
        super(context, 0, listaBolao);
        this.context = context;
        this.listaBolao = listaBolao;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Bolao posicaoBolao = this.listaBolao.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.adapter_lista_bolao, null);

        TextView textNome = (TextView) convertView.findViewById(R.id.textNome);
        textNome.setText(posicaoBolao.getNome());

        return convertView;
    }

}
