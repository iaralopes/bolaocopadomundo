package br.puc.bolaocopamundo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.entity.Pessoa;
import br.puc.bolaocopamundo.service.UsuarioService;
import br.puc.bolaocopamundo.service.VersaoService;
import br.puc.bolaocopamundo.util.SessionManager;
import br.puc.bolaocopamundo.util.VersionManager;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    private TextView linkCadastrar;
    private EditText et_username, et_password;
    private Button bt_login;

    private Pessoa pessoa;

    private Boolean versionChecked = false;

    private ProgressDialog load;

    // Session Manager Class
    private SessionManager session;

    /*
        Methods
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        VerificarVersaoWebService verificarVersaoWebService = new VerificarVersaoWebService();
        verificarVersaoWebService.execute();

        if(verificaConexao()){
            session = new SessionManager(getApplicationContext());

            if(session.isLoggedIn()){
                pularLogin();
            }else{
                callbackManager = CallbackManager.Factory.create();

                linkCadastrar = (TextView)findViewById(R.id.linkCadastrar);
                et_username = (EditText)findViewById(R.id.et_username);
                et_password = (EditText)findViewById(R.id.et_password);
                bt_login = (Button)findViewById(R.id.bt_login);

                bt_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        validarLogin();
                    }
                });

                linkCadastrar.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent cadastrarIntent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                        LoginActivity.this.startActivity(cadastrarIntent);
                    }
                });

                LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
                loginButton.setReadPermissions(Arrays.asList("public_profile","email"));

                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        if(loginResult.getAccessToken() != null){

                            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    if(object != null){
                                        definirPessoaFacebook(object);
                                        EfetuarLoginWebService efetuarLogin = new EfetuarLoginWebService();
                                        efetuarLogin.execute();
                                    }
                                }
                            });

                            Bundle parameters = new Bundle();
                            parameters.putString("fields","email, name");
                            request.setParameters(parameters);
                            request.executeAsync();
                        }
                    }

                    @Override
                    public void onCancel() {
                        pessoa = new Pessoa();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        pessoa = new Pessoa();
                    }
                });
            }
        }else{
            Toast.makeText(getBaseContext(), "Para utilizar este app você precisa estar conectado na internet.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    /**
     * Função para verificar existência de conexão com a internet
     */
    public  boolean verificaConexao() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return conectivtyManager.getActiveNetworkInfo() != null && conectivtyManager.getActiveNetworkInfo().isAvailable() && conectivtyManager.getActiveNetworkInfo().isConnected();
    }

    public void pularLogin(){
        Intent boloesList = new Intent(LoginActivity.this, BoloesActivity.class);
        LoginActivity.this.startActivity(boloesList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void definirPessoaFacebook(JSONObject object) {
        try{
            pessoa = new Pessoa();
            pessoa.setNome(object.getString("name"));
            pessoa.setEmail(object.getString("email"));
            pessoa.setFacebook(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void validarLogin(){
        pessoa = new Pessoa();
        pessoa.setEmail(et_username.getText().toString());
        pessoa.setSenha(et_password.getText().toString());
        EfetuarLoginWebService efetuarLogin = new EfetuarLoginWebService();
        efetuarLogin.execute();
    }

    private class EfetuarLoginWebService extends AsyncTask<Void, Void, Pessoa> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(LoginActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Pessoa doInBackground(Void... params) {
            UsuarioService cadastroService = new UsuarioService();
            return cadastroService.realizarLogin(pessoa);
        }

        @Override
        protected void onPostExecute(Pessoa pessoa){
            if(pessoa != null){
                session.createLoginSession(pessoa.getId().toString(), pessoa.getNome(), pessoa.getEmail());
                Toast.makeText(getBaseContext(), "Login realizado com sucesso." , Toast.LENGTH_SHORT).show();
                Intent boloesList = new Intent(LoginActivity.this, BoloesActivity.class);
                LoginActivity.this.startActivity(boloesList);
                finish();
            }else{
                Toast.makeText(getBaseContext(), "Erro no login.", Toast.LENGTH_SHORT).show();
            }
            load.dismiss();
        }
    }

    private class VerificarVersaoWebService extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            VersaoService versaoService = new VersaoService();
            return versaoService.verificaVersao(VersionManager.VERSAO);
        }

        @Override
        protected void onPostExecute(final Boolean resp){
            if(!resp){
                Intent versaoIntent = new Intent(LoginActivity.this, VersaoActivity.class);
                LoginActivity.this.startActivity(versaoIntent);
            }
        }
    }

}