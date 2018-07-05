package br.puc.bolaocopamundo.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.puc.bolaocopamundo.R;
import br.puc.bolaocopamundo.network.NetworkUtil;

public class VersaoActivity extends AppCompatActivity {

    private Button btnBaixar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versao);

        btnBaixar = (Button) findViewById(R.id.btnBaixar);
        btnBaixar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uriUrl = Uri.parse(NetworkUtil.ENDERECO_APK);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

}
