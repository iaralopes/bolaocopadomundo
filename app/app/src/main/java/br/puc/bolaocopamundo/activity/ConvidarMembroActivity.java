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

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.entity.Bolao;
import br.puc.bolaocopamundo.service.BolaoService;
import br.puc.bolaocopamundo.util.BolaoSelecionado;

public class ConvidarMembroActivity extends AppCompatActivity {

    /*
        Attributes
     */

    private static  final String TITLE = "Convidar Membros";

    private EditText txtEmail;
    private Button btnConvidar;

    private ProgressDialog load;
    /*
        Methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convidar_membro);
        setTitle(TITLE);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        txtEmail = (EditText) findViewById(R.id.txtEmail);

        btnConvidar = (Button) findViewById(R.id.btnConvidar);


        btnConvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convidarMembro();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent bolaoIntent = new Intent(ConvidarMembroActivity.this, BolaoPrincipalActivity.class);
        ConvidarMembroActivity.this.startActivity(bolaoIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent bolaoIntent = new Intent(ConvidarMembroActivity.this, BolaoPrincipalActivity.class);
            ConvidarMembroActivity.this.startActivity(bolaoIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void convidarMembro(){
        ConvidarMembroBolaoWebService convidarMembroBolao = new ConvidarMembroBolaoWebService();
        convidarMembroBolao.execute();
    }

    private class ConvidarMembroBolaoWebService extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(ConvidarMembroActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            BolaoService bolaoService = new BolaoService();
            return bolaoService.convidarMembro(txtEmail.getText().toString(), BolaoSelecionado.getBolao().getId());
        }

        @Override
        protected void onPostExecute(Boolean resp){
            if(resp){
                Toast.makeText(getBaseContext(), "Convidado com sucesso.", Toast.LENGTH_SHORT).show();
                txtEmail.getText().clear();
            }else{
                Toast.makeText(getBaseContext(), "Erro ao convidar.", Toast.LENGTH_SHORT).show();
            }
            load.dismiss();
        }
    }

}
