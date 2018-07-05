package br.puc.bolaocopamundo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.entity.Bolao;
import br.puc.bolaocopamundo.entity.Pessoa;
import br.puc.bolaocopamundo.service.BolaoService;
import br.puc.bolaocopamundo.service.UsuarioService;
import br.puc.bolaocopamundo.util.SessionManager;

public class CadastroBolaoActivity extends AppCompatActivity {

    /**
     Attributes
     */
    private static final String TITLE = "Novo Bolão";

    private EditText cadastroNome, cadastroNumeroJogadores;
    private Button btnCadastrar;

    private Bolao bolao;

    private SessionManager session;

    private ProgressDialog load;

    /**
     Methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_bolao);
        setTitle(TITLE);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext());

        cadastroNome = (EditText) findViewById(R.id.nomeBolao);
        cadastroNumeroJogadores = (EditText) findViewById(R.id.numeroGanhadores);

        btnCadastrar = (Button) findViewById(R.id.button_cadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarBolao();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent boloesIntent = new Intent(CadastroBolaoActivity.this, BoloesActivity.class);
        CadastroBolaoActivity.this.startActivity(boloesIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent bolaoIntent = new Intent(CadastroBolaoActivity.this, BoloesActivity.class);
            CadastroBolaoActivity.this.startActivity(bolaoIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void registrarBolao(){
        bolao = new Bolao(null, cadastroNome.getText().toString(), Integer.parseInt(cadastroNumeroJogadores.getText().toString()), session.getId());
        CadastrarBolaoWebService cadastrarBolao = new CadastrarBolaoWebService();
        cadastrarBolao.execute();
    }

    private class CadastrarBolaoWebService extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(CadastroBolaoActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            BolaoService bolaoService = new BolaoService();
            return bolaoService.realizarCadastro(bolao);
        }

        @Override
        protected void onPostExecute(Boolean resp){
            if(resp){
                Toast.makeText(getBaseContext(), "Sucesso ao cadastrar.", Toast.LENGTH_SHORT).show();
                Intent boloesIntent = new Intent(CadastroBolaoActivity.this, BoloesActivity.class);
                CadastroBolaoActivity.this.startActivity(boloesIntent);
            }else{
                Toast.makeText(getBaseContext(), "Erro ao cadastrar.", Toast.LENGTH_SHORT).show();
            }
            load.dismiss();
        }
    }


}