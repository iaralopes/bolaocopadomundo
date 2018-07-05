package br.puc.bolaocopamundo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.adapter.ListaApostasAdapter;
import br.puc.bolaocopamundo.entity.Aposta;
import br.puc.bolaocopamundo.entity.Bolao;
import br.puc.bolaocopamundo.service.ApostaService;
import br.puc.bolaocopamundo.util.BolaoSelecionado;
import br.puc.bolaocopamundo.util.SessionManager;

public class BolaoPrincipalActivity extends AppCompatActivity {

    /*
        Attributes
     */
    private Bolao bolao;
    private List<Aposta> apostas;

    private ListView listApostas;
    private FloatingActionButton fbtnSalvarPalpites;

    private SessionManager session;

    private ProgressDialog load;

    /*
        Methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolao_principal);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext());

        //Verifica Bolão Selecionado
        if(BolaoSelecionado.getBolao() == null){
            Intent boloesIntent = new Intent(BolaoPrincipalActivity.this, BoloesActivity.class);
            BolaoPrincipalActivity.this.startActivity(boloesIntent);
        }
        //Define na activity o bolão selecionado
        bolao = BolaoSelecionado.getBolao();
        //Set titulo do grupo
        setTitle(bolao.getNome());

        listApostas = (ListView) findViewById(R.id.listApostas);
        fbtnSalvarPalpites = (FloatingActionButton) findViewById(R.id.fbtnSalvarPalpites);

        fbtnSalvarPalpites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizarApostas();
            }
        });

        ApostasPendentesWebService apostasPendentes = new ApostasPendentesWebService();
        apostasPendentes.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(bolao.getIdCriador() != session.getId()){
            getMenuInflater().inflate(R.menu.menu_bolao_principal, menu);
        }else {
            getMenuInflater().inflate(R.menu.menu_bolao_principal_lider, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_convidar) {
            Intent convidarMembroIntent = new Intent(BolaoPrincipalActivity.this, ConvidarMembroActivity.class);
            BolaoPrincipalActivity.this.startActivity(convidarMembroIntent);
            return true;
        }
        if (id == R.id.action_membros) {
            Intent membrosIntent = new Intent(BolaoPrincipalActivity.this, MembrosActivity.class);
            BolaoPrincipalActivity.this.startActivity(membrosIntent);
            return true;
        }
        if (id == R.id.action_resultados) {
            Intent resultadosIntent = new Intent(BolaoPrincipalActivity.this, BolaoResultados.class);
            BolaoPrincipalActivity.this.startActivity(resultadosIntent);
            return true;
        }
        if(id == R.id.action_rank){
            Intent rankIntent = new Intent(BolaoPrincipalActivity.this, BolaoRankActivity.class);
            BolaoPrincipalActivity.this.startActivity(rankIntent);
            return true;
        }
        if(id == android.R.id.home){
            Intent boloesIntent = new Intent(BolaoPrincipalActivity.this, BoloesActivity.class);
            BolaoPrincipalActivity.this.startActivity(boloesIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent boloesIntent = new Intent(BolaoPrincipalActivity.this, BoloesActivity.class);
        BolaoPrincipalActivity.this.startActivity(boloesIntent);
    }

    public void atualizarApostas(){
        apostas = new ArrayList<>();

        for(int i = 0; i < listApostas.getCount(); i++){
            Aposta a = (Aposta) listApostas.getItemAtPosition(i);
            if(a.getGolsCasa() != null && a.getGolsVisitante() != null){
                apostas.add(a);
            }
        }
        AtualizarApostaWebService atualizarAposta = new AtualizarApostaWebService();
        atualizarAposta.execute();
    }

    private class ApostasPendentesWebService extends AsyncTask<Void, Void, List<Aposta>> {
        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(BolaoPrincipalActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected List<Aposta> doInBackground(Void... params) {
            ApostaService apostaService = new ApostaService();
            return apostaService.buscarApostasPendentes(session.getId(), BolaoSelecionado.getBolao().getId());
        }

        @Override
        protected void onPostExecute(final List<Aposta> apostas){
            if(!apostas.isEmpty()){
                ListaApostasAdapter adapter = new ListaApostasAdapter(BolaoPrincipalActivity.this, R.layout.adapter_lista_apostas, apostas);
                listApostas.setAdapter(adapter);
            }else{
                Toast.makeText(getBaseContext(), "Nenhuma aposta encontrada.", Toast.LENGTH_SHORT).show();
                listApostas.setAdapter(null);
            }
            load.dismiss();
        }

    }

    private class AtualizarApostaWebService extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(BolaoPrincipalActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ApostaService apostaService = new ApostaService();
            return apostaService.atualizarAposta(apostas);
        }

        @Override
        protected void onPostExecute(Boolean resp){
            if(resp){
                Toast.makeText(getBaseContext(), "Sucesso ao atualizar apostas.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getBaseContext(), "Erro ao atualizar apostas.", Toast.LENGTH_SHORT).show();
            }
            load.dismiss();
        }

    }

}
