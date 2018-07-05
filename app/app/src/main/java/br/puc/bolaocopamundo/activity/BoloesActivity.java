package br.puc.bolaocopamundo.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.adapter.ListaBolaoAdapter;
import br.puc.bolaocopamundo.entity.Bolao;
import br.puc.bolaocopamundo.service.BolaoService;
import br.puc.bolaocopamundo.util.BolaoSelecionado;
import br.puc.bolaocopamundo.util.SessionManager;
import br.puc.bolaocopamundo.util.VersionManager;

public class BoloesActivity extends AppCompatActivity {

    /**
     Attributes
     */

    private static  final String TITLE = "Meus Bolões";

    private ListView listBoloes;
    private FloatingActionButton btnAddBolao;

    private ProgressDialog load;

    // Session Manager Class
    private SessionManager session;

    /**
     Methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boloes);
        setTitle(TITLE);

        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        listBoloes = (ListView) findViewById(R.id.listBoloes);
        btnAddBolao = (FloatingActionButton) findViewById(R.id.btnAddBolao);

        btnAddBolao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addBolaoIntent = new Intent(BoloesActivity.this, CadastroBolaoActivity.class);
                BoloesActivity.this.startActivity(addBolaoIntent);
            }
        });

        BuscarBoloesUsuarioWebService buscarBoloesUsuarioWebService = new BuscarBoloesUsuarioWebService();
        buscarBoloesUsuarioWebService.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_perfil) {
            Intent perfilIntent = new Intent(BoloesActivity.this, PerfilActivity.class);
            BoloesActivity.this.startActivity(perfilIntent);
            return true;
        }
        if (id == R.id.action_regulamento) {
            Intent regulamentoIntent = new Intent(BoloesActivity.this, RegulamentoActivity.class);
            BoloesActivity.this.startActivity(regulamentoIntent);
            return true;
        }
        if (id == R.id.action_logout) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Deseja fazer Logout?");
            alertDialogBuilder
                    .setCancelable(true)
                    .setPositiveButton("Sim",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
                                    SessionManager session = new SessionManager(getApplicationContext());
                                    session.logoutUser();
                                }
                            })

                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Deseja fazer Logout?");
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                SessionManager session = new SessionManager(getApplicationContext());
                                session.logoutUser();
                            }
                        })

                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private class BuscarBoloesUsuarioWebService extends AsyncTask<Void, Void, List<Bolao>> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(BoloesActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected List<Bolao> doInBackground(Void... params) {
            BolaoService bolaoService = new BolaoService();
            return bolaoService.buscarBoloesUsuario(session.getId());
        }

        @Override
        protected void onPostExecute(final List<Bolao> boloes){
            if(!boloes.isEmpty()){
                ListaBolaoAdapter adapter = new ListaBolaoAdapter(BoloesActivity.this, R.layout.adapter_lista_bolao, boloes);
                listBoloes.setAdapter(adapter);
                listBoloes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        BolaoSelecionado.setBolao(boloes.get(position));
                        Intent bolaoIntent = new Intent(BoloesActivity.this, BolaoPrincipalActivity.class);
                        BoloesActivity.this.startActivity(bolaoIntent);
                    }
                });
            }else{
                Toast.makeText(getBaseContext(), "Nenhum bolão encontrado.", Toast.LENGTH_SHORT).show();
                listBoloes.setAdapter(null);
            }
            load.dismiss();
        }
    }

}
