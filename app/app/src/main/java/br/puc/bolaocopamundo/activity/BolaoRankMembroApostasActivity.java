package br.puc.bolaocopamundo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.adapter.ListaResultadosAdapter;
import br.puc.bolaocopamundo.entity.Aposta;
import br.puc.bolaocopamundo.entity.Rank;
import br.puc.bolaocopamundo.service.ApostaService;
import br.puc.bolaocopamundo.util.BolaoSelecionado;
import br.puc.bolaocopamundo.util.RankSelecionado;

public class BolaoRankMembroApostasActivity extends AppCompatActivity {

    /**
     Attributes
     */
    private static final String TITLE = "Apostas de: ";

    private ListView listApostas;

    private ProgressDialog load;


    /**
     Methods
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolao_rank_membro_apostas);
        setTitle(TITLE + RankSelecionado.getRank().getNome());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listApostas = (ListView) findViewById(R.id.listResultados);

        RankApostasConcluidasWebService rankApostasConcluidas = new RankApostasConcluidasWebService();
        rankApostasConcluidas.execute();
    }

    @Override
    public void onBackPressed() {
        Intent bolaoRankIntent = new Intent(BolaoRankMembroApostasActivity.this, BolaoRankActivity.class);
        BolaoRankMembroApostasActivity.this.startActivity(bolaoRankIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent bolaoRankIntent = new Intent(BolaoRankMembroApostasActivity.this, BolaoRankActivity.class);
            BolaoRankMembroApostasActivity.this.startActivity(bolaoRankIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private class RankApostasConcluidasWebService extends AsyncTask<Void, Void, List<Aposta>> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(BolaoRankMembroApostasActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected List<Aposta> doInBackground(Void... params) {
            ApostaService apostaService = new ApostaService();
            return apostaService.buscarApostasConcluidas(RankSelecionado.getRank().getIdPessoa(), BolaoSelecionado.getBolao().getId());
        }

        @Override
        protected void onPostExecute(final List<Aposta> apostas){
            if(!apostas.isEmpty()){
                ListaResultadosAdapter adapter = new ListaResultadosAdapter(BolaoRankMembroApostasActivity.this, R.layout.adapter_lista_resultados, apostas);
                listApostas.setAdapter(adapter);
            }else{
                Toast.makeText(getBaseContext(), "Nenhuma aposta encontrada.", Toast.LENGTH_SHORT).show();
                listApostas.setAdapter(null);
            }
            load.dismiss();
        }
    }

}
