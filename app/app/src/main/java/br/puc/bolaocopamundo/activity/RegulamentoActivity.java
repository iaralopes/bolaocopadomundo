package br.puc.bolaocopamundo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.adapter.ListaBolaoAdapter;
import br.puc.bolaocopamundo.entity.Bolao;
import br.puc.bolaocopamundo.service.BolaoService;
import br.puc.bolaocopamundo.service.ConfiguracaoService;
import br.puc.bolaocopamundo.util.BolaoSelecionado;

public class RegulamentoActivity extends AppCompatActivity {

    /*
        Attributes
     */
    private static  final String TITLE = "Regulamento";

    private TextView txtRegulamento;

    private ProgressDialog load;

    /**
     Methods
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulamento);
        setTitle(TITLE);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtRegulamento = (TextView) findViewById(R.id.txtRegulamento);

        BuscarConfiguracoesWebService buscarConfiguracoesWebService = new BuscarConfiguracoesWebService();
        buscarConfiguracoesWebService.execute();
    }

    @Override
    public void onBackPressed() {
        Intent boloesIntent = new Intent(RegulamentoActivity.this, BoloesActivity.class);
        RegulamentoActivity.this.startActivity(boloesIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent boloesIntent = new Intent(RegulamentoActivity.this, BoloesActivity.class);
            RegulamentoActivity.this.startActivity(boloesIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private class BuscarConfiguracoesWebService extends AsyncTask<Void, Void, Map<String, Integer>> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(RegulamentoActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Map<String, Integer> doInBackground(Void... params) {
            ConfiguracaoService configuracaoService = new ConfiguracaoService();
            return configuracaoService.buscarConfiguracoes();
        }

        @Override
        protected void onPostExecute(final Map<String, Integer> configs){
            if(!configs.isEmpty()){
                txtRegulamento.setText(
                        "Palpites devem ser lançados até " + configs.get("horas-palpites") + "hs antes de cada jogo." +
                        "\n\nPontuações:" +
                        "\nAcerto em cheio " + configs.get("acerto-cheio") + "pts " +
                        "\nAcerto do resultado e diferença de gols " + configs.get("acerto-diferenca") + "pts " +
                        "\nAcerto do resultado " + configs.get("acerto-ganhador") + "pts " +
                        "\nErro de resultado " + configs.get("erro") + "pts " +
                        "\n\nDurante os jogos da fase de mata-mata o resultado que será considerado é do tempo normal mais prorrogação, o resultado dos pênaltis não entram na aposta. " +
                        "(Ex: Brasil 0(5) x (4)0 Argentina, resultado certo de aposta é 0 x 0)");
            }else{
                Toast.makeText(getBaseContext(), "Não foi possivel carregar o regulamento.", Toast.LENGTH_SHORT).show();
                txtRegulamento.setText("Erro ao carregar regulamento.");
            }
            load.dismiss();
        }
    }

}
