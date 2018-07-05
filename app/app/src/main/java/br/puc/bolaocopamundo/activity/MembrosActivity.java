package br.puc.bolaocopamundo.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.adapter.ListaBolaoAdapter;
import br.puc.bolaocopamundo.adapter.ListaMembrosAdapter;
import br.puc.bolaocopamundo.entity.Bolao;
import br.puc.bolaocopamundo.entity.Pessoa;
import br.puc.bolaocopamundo.service.BolaoService;
import br.puc.bolaocopamundo.util.BolaoSelecionado;
import br.puc.bolaocopamundo.util.SessionManager;

public class MembrosActivity extends AppCompatActivity {

    /**
     Attributes
     */

    private static  final String TITLE = "Membros";

    private ListView listMembros;

    private ProgressDialog load;

    // Session Manager Class
    private SessionManager session;

    /**
     Methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membros);
        setTitle(TITLE);

        session = new SessionManager(getApplicationContext());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listMembros = (ListView) findViewById(R.id.listMembros);

        BuscarMembrosBolaoWebService buscarMembrosBolaoWebService = new BuscarMembrosBolaoWebService();
        buscarMembrosBolaoWebService.execute();
    }

    @Override
    public void onBackPressed() {
        Intent bolaoIntent = new Intent(MembrosActivity.this, BolaoPrincipalActivity.class);
        MembrosActivity.this.startActivity(bolaoIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent bolaoIntent = new Intent(MembrosActivity.this, BolaoPrincipalActivity.class);
            MembrosActivity.this.startActivity(bolaoIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private class BuscarMembrosBolaoWebService extends AsyncTask<Void, Void, List<Pessoa>> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(MembrosActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected List<Pessoa> doInBackground(Void... params) {
            BolaoService bolaoService = new BolaoService();
            return bolaoService.buscarMembrosBolao(BolaoSelecionado.getBolao().getId());
        }

        @Override
        protected void onPostExecute(final List<Pessoa> membros){
            if(!membros.isEmpty()){
                ListaMembrosAdapter adapter = new ListaMembrosAdapter(MembrosActivity.this, R.layout.adapter_lista_membros, membros);
                listMembros.setAdapter(adapter);
                listMembros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(BolaoSelecionado.getBolao().getIdCriador() == session.getId()){
                            if(BolaoSelecionado.getBolao().getIdCriador() == membros.get(position).getId()){
                                Toast.makeText(getBaseContext(), "Criador do bolão não pode ser excluído.", Toast.LENGTH_SHORT).show();
                            }else{
                                carregarModalExclusao(membros.get(position));
                            }
                        }
                    }
                });
            }else{
                Toast.makeText(getBaseContext(), "Nenhum membro encontrado.", Toast.LENGTH_SHORT).show();
                listMembros.setAdapter(null);
            }
            load.dismiss();
        }
    }

    private void carregarModalExclusao(final Pessoa membro){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Você deseja remover o membro "+membro.getNome()+ "?");

        alert.setNegativeButton("Excluir",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        removerMembro(membro.getId());
                    }
                });

        alert.show();
    }

    public void removerMembro(Long idMembro){
        RemoverMembroBolaoWebService removerMembroBolaoWebService = new RemoverMembroBolaoWebService(idMembro);
        removerMembroBolaoWebService.execute();
    }

    private class RemoverMembroBolaoWebService extends AsyncTask<Void, Void, Boolean> {

        private Long idMembro;

        public RemoverMembroBolaoWebService(){

        }

        public RemoverMembroBolaoWebService(Long idMembro){
            this.idMembro = idMembro;
        }

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(MembrosActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            BolaoService bolaoService = new BolaoService();
            return bolaoService.removerMembro(idMembro, BolaoSelecionado.getBolao().getId());
        }

        @Override
        protected void onPostExecute(Boolean resp){
            if(resp){
                Toast.makeText(getBaseContext(), "Membro removido.", Toast.LENGTH_SHORT).show();
                Intent membrosIntent = new Intent(MembrosActivity.this, MembrosActivity.class);
                MembrosActivity.this.startActivity(membrosIntent);
            }else{
                Toast.makeText(getBaseContext(), "Erro ao remover.", Toast.LENGTH_SHORT).show();
            }
            load.dismiss();
        }
    }

}
