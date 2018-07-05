package br.puc.bolaocopamundo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.entity.Aposta;
import br.puc.bolaocopamundo.util.MapImagemSelecoesUtil;

public class ListaResultadosAdapter extends ArrayAdapter<Aposta> {

    private Context context;
    private List<Aposta> listaApostas;
    private static LayoutInflater inflater = null;

    public ListaResultadosAdapter(Context context, int textViewResourceId, List<Aposta> listaApostas) {
        super(context, 0, listaApostas);
        this.context = context;
        this.listaApostas = listaApostas;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Aposta posicaoAposta = this.listaApostas.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.adapter_lista_resultados, null);

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

        String resultado = posicaoAposta.getResultadoAposta();
        TextView txtAposta = (TextView) convertView.findViewById(R.id.txtAposta);
        txtAposta.setText(resultado);
        if (resultado.equals("Aguardando resultado")) {
            txtAposta.setTextColor(Color.parseColor("#000000"));
        }else if (resultado.equals("Acertou em cheio")) {
            txtAposta.setTextColor(Color.parseColor("#008000"));
        }else if (resultado.equals("Acertou ganhador") || resultado.equals("Acertou empate")){
            txtAposta.setTextColor(Color.parseColor("#ff9800"));
        }else if (resultado.equals("Acertou diferença de gols")) {
            txtAposta.setTextColor(Color.parseColor("#ffc107"));
        }else if (resultado.equals("Errou Resultado")) {
            txtAposta.setTextColor(Color.parseColor("#ff0000"));
        }else if (resultado.equals("Não apostou")) {
            txtAposta.setTextColor(Color.parseColor("#000000"));
        }


        TextView txtResultadoAposta = (TextView) convertView.findViewById(R.id.txtResultadoAposta);
        txtResultadoAposta.setText(posicaoAposta.getValoresAposta());


        return convertView;
    }

}
