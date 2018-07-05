package br.puc.bolaocopamundo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.entity.Pessoa;
import br.puc.bolaocopamundo.service.UsuarioService;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText cadastroNome, cadastroEmail, cadastroSenha;

    private TextView linkLogar;

    private Button btnCadastrar;

    private Pessoa pessoa;

    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        getSupportActionBar().hide();

        linkLogar = (TextView)findViewById(R.id.linkCadastrar);
        cadastroNome = (EditText) findViewById(R.id.nomePessoa);
        cadastroEmail = (EditText) findViewById(R.id.emailPessoa);
        cadastroSenha = (EditText) findViewById(R.id.senhaPessoa);

        btnCadastrar = (Button) findViewById(R.id.button_cadastrarPessoa);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });

        linkLogar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent logarIntent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
                CadastroUsuarioActivity.this.startActivity(logarIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent loginIntent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
        CadastroUsuarioActivity.this.startActivity(loginIntent);
    }

    public void registrarUsuario(){
        pessoa = new Pessoa(null, cadastroNome.getText().toString(), cadastroEmail.getText().toString(), cadastroSenha.getText().toString(), false);
        CadastrarUsuarioWebService cadastrarUsuario = new CadastrarUsuarioWebService();
        cadastrarUsuario.execute();
    }

    private class CadastrarUsuarioWebService extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(CadastroUsuarioActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
                UsuarioService cadastroService = new UsuarioService();
                return cadastroService.realizarCadastro(pessoa);
        }

        @Override
        protected void onPostExecute(Boolean resp){
            if(resp){
                Toast.makeText(getBaseContext(), "Sucesso ao cadastrar.", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
                CadastroUsuarioActivity.this.startActivity(loginIntent);
            }else{
                Toast.makeText(getBaseContext(), "Erro ao cadastrar.", Toast.LENGTH_SHORT).show();
            }
            load.dismiss();
        }
    }


}
