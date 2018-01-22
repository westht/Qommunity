package com.team.a404.qommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Sergio Cuadrado on 22/01/2018.
 */

public class Log_inActivity extends AppCompatActivity {
    protected EditText user;
    protected EditText pass;
    protected TextView sincuenta;
    protected Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activ);
        getSupportActionBar().hide();
        user = (EditText)findViewById(R.id.usuario);
        pass=(EditText)findViewById(R.id.pass);
        sincuenta = (TextView)findViewById(R.id.nocuenta);
        log = (Button)findViewById(R.id.logbutt);
        sincuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_inActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
