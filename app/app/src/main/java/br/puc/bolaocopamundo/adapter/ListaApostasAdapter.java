package br.puc.bolaocopamundo.adapter;

import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.entity.Aposta;
import br.puc.bolaocopamundo.util.MapImagemSelecoesUtil;

public class ListaApostasAdapter  extends ArrayAdapter<Aposta> {

    private Context context;
    private List<Aposta> listaApostas;
    private static LayoutInflater inflater = null;

    public ListaApostasAdapter(Context context, int textViewResourceId, List<Aposta> listaApostas) {
        super(context, 0, listaApostas);
        this.context = context;
        this.listaApostas = listaApostas;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final Aposta posicaoAposta = this.listaApostas.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.adapter_lista_apostas, null);

        String selecaoCasa = posicaoAposta.getJogo().getCasa().getNome();
        if (MapImagemSelecoesUtil.getImgMap().containsKey(selecaoCasa)) {
            ImageView imageCasa = (ImageView) convertView.findViewById(R.id.iconCasa);
            imageCasa.setImageResource(MapImagemSelecoesUtil.getImgMap().get(selecaoCasa));
        }

        String selecaoVisitante = posicaoAposta.getJogo().getVisitante().getNome();
        if (MapImagemSelecoesUtil.getImgMap().containsKey(selecaoVisitante)) {
            ImageView imageCasa = (ImageView) convertView.findViewById(R.id.iconVisitante);
            imageCasa.setImageResource(MapImagemSelecoesUtil.getImgMap().get(selecaoVisitante));
        }

        TextView txtJogoResultado = (TextView) convertView.findViewById(R.id.txtJogoResultado);
        txtJogoResultado.setText(posicaoAposta.getJogo().getSelecoesResultado());
        TextView txtHorarioLocal = (TextView) convertView.findViewById(R.id.txtHorarioLocal);
        txtHorarioLocal.setText(posicaoAposta.getJogo().getHorarioLocal());
        final EditText txtGolsCasa = (EditText) convertView.findViewById(R.id.txtGolsCasa);
        if(posicaoAposta.getGolsCasa() != null){
            txtGolsCasa.setText(posicaoAposta.getGolsCasa().toString());
        }
        txtGolsCasa.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(!txtGolsCasa.getText().toString().equals("")) {
                    posicaoAposta.setGolsCasa(Integer.parseInt(txtGolsCasa.getText().toString()));
                }else{
                    Toast.makeText(getContext(), "Jogos com placar incompleto não seram salvos.", Toast.LENGTH_SHORT).show();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        final EditText txtGolsVisitante = (EditText) convertView.findViewById(R.id.txtGolsVisitante);
        if(posicaoAposta.getGolsVisitante() != null){
            txtGolsVisitante.setText(posicaoAposta.getGolsVisitante().toString());
        }
        txtGolsVisitante.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(!txtGolsVisitante.getText().toString().equals("")) {
                    posicaoAposta.setGolsVisitante(Integer.parseInt(txtGolsVisitante.getText().toString()));
                }else{
                    Toast.makeText(getContext(), "Jogos com placar incompleto não seram salvos.", Toast.LENGTH_SHORT).show();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        return convertView;
    }

}
