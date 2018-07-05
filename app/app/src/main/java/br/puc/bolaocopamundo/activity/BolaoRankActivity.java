package br.puc.bolaocopamundo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.adapter.ListaRanksAdapter;
import br.puc.bolaocopamundo.entity.Aposta;
import br.puc.bolaocopamundo.entity.Rank;
import br.puc.bolaocopamundo.service.ApostaService;
import br.puc.bolaocopamundo.util.BolaoSelecionado;
import br.puc.bolaocopamundo.util.RankSelecionado;

public class BolaoRankActivity extends AppCompatActivity {

    /**
     Attributes
     */
    private static final String TITLE = "Ranks";

    private List<Rank> ranks;
    private Map<String, Integer> configs;

    private ListView listRanks;

    private ProgressDialog load;

    /**
     Methods
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolao_rank);
        setTitle(TITLE);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listRanks = (ListView) findViewById(R.id.listRanks);

        BuscarRankWebService buscarRank = new BuscarRankWebService();
        buscarRank.execute();
    }

    @Override
    public void onBackPressed() {
        Intent bolaoIntent = new Intent(BolaoRankActivity.this, BolaoPrincipalActivity.class);
        BolaoRankActivity.this.startActivity(bolaoIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent bolaoIntent = new Intent(BolaoRankActivity.this, BolaoPrincipalActivity.class);
            BolaoRankActivity.this.startActivity(bolaoIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private class BuscarRankWebService extends AsyncTask<Void, Void, List<Rank>> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(BolaoRankActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected List<Rank> doInBackground(Void... params) {
            ApostaService apostaService = new ApostaService();
            return apostaService.buscarApostasBolao(BolaoSelecionado.getBolao().getId());
        }

        @Override
        protected void onPostExecute(final List<Rank> ranks){
            if(!ranks.isEmpty()){
                ListaRanksAdapter adapter = new ListaRanksAdapter(BolaoRankActivity.this,R.layout.adapter_lista_ranks , ranks);
                listRanks.setAdapter(adapter);
                listRanks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        RankSelecionado.setRank(ranks.get(position));
                        Intent membroApostasIntent = new Intent(BolaoRankActivity.this, BolaoRankMembroApostasActivity.class);
                        BolaoRankActivity.this.startActivity(membroApostasIntent);
                    }
                });
            }else{
                Toast.makeText(getBaseContext(), "Rank vazio.", Toast.LENGTH_SHORT).show();
                listRanks.setAdapter(null);
            }
            load.dismiss();
        }
    }

}
