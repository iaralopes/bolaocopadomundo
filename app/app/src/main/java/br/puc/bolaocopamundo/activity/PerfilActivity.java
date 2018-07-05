package br.puc.bolaocopamundo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.util.SessionManager;

public class PerfilActivity extends AppCompatActivity {

    /*
        Attributes
     */
    private static  final String TITLE = "Perfil";

    private TextView txtNome, txtEmail;

    private SessionManager session;

    /*
        Methods
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setTitle(TITLE);

        session = new SessionManager(getApplicationContext());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNome = (TextView) findViewById(R.id.txtNome);
        txtNome.setText("Nome: " + session.getNome());
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEmail.setText("Email: " + session.getEmail());

    }

    @Override
    public void onBackPressed() {
        Intent boloesIntent = new Intent(PerfilActivity.this, BoloesActivity.class);
        PerfilActivity.this.startActivity(boloesIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent boloesIntent = new Intent(PerfilActivity.this, BoloesActivity.class);
            PerfilActivity.this.startActivity(boloesIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
