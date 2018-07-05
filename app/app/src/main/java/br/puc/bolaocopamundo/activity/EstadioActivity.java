package br.puc.bolaocopamundo.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.adapter.ListaEstadioAdapter;
import br.puc.bolaocopamundo.entity.Estadio;
import br.puc.bolaocopamundo.service.EstadioService;

public class EstadioActivity extends AppCompatActivity {

    private static  final String TITLE = "Estádios";

    private ListView listEstadios;

    private ProgressDialog load;

    /**
     Methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadio);

        setTitle(TITLE);

        setListEstadios((ListView) findViewById(R.id.listEstadios));

        BuscarEstadiosWebService getEstadios = new BuscarEstadiosWebService();
        getEstadios.execute();
    }

    private class BuscarEstadiosWebService extends AsyncTask<Void, Void, List<Estadio>> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(EstadioActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected List<Estadio> doInBackground(Void... params) {
            EstadioService estadioService = new EstadioService();
            return estadioService.buscarEstadios();
        }

        @Override
        protected void onPostExecute(final List<Estadio> estadios){
            if(!estadios.isEmpty()){
                ListaEstadioAdapter adapter = new ListaEstadioAdapter(EstadioActivity.this,R.layout.adapter_lista_estadio,estadios);
                listEstadios.setAdapter(adapter);
            }else{
                Toast.makeText(getBaseContext(), "Nenhum estádio encontrada.", Toast.LENGTH_SHORT).show();
                listEstadios.setAdapter(null);
            }
            load.dismiss();

        }
    }

    /**
     Getters & Setters
     */

    public ListView getListEstadios() {
        return listEstadios;
    }

    public void setListEstadios(ListView listEstadios) {
        this.listEstadios = listEstadios;
    }

}
