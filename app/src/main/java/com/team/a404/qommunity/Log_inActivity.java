package com.team.a404.qommunity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Sergio Cuadrado on 22/01/2018.
 */

public class Log_inActivity extends AppCompatActivity implements View.OnClickListener {
    protected EditText user;
    protected EditText pass;
    protected TextView sincuenta;
    protected Button log;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activ);
        getSupportActionBar().hide();
        user = (EditText) findViewById(R.id.usuario);
        pass = (EditText) findViewById(R.id.pass);
        sincuenta = (TextView) findViewById(R.id.nocuenta);
        log = (Button) findViewById(R.id.logbutt);
        progressDialog = new ProgressDialog(this);
        log.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            //prfi
            finish();
            Intent intent1 = new Intent(Log_inActivity.this,MainScreen.class);
            startActivity(intent1);

        }
        sincuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_inActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == log) {
            userLogin();
        }
    }
    private void userLogin() {
        String usr = user.getText().toString().trim();
        String pwd = pass.getText().toString().trim();


        if (TextUtils.isEmpty(usr)){
            // email is empty
            Toast.makeText(this,"Introduzca el E-mail",Toast.LENGTH_SHORT).show();
            // para la ejecucion
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            // password is empty
            Toast.makeText(this,"Introduzca la contraseña",Toast.LENGTH_SHORT).show();
            // para la ejecucion
            return;
        }
        progressDialog.setMessage("Creando usuario.");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(usr,pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()){
                            finish();
                            Intent intent1 = new Intent(Log_inActivity.this,MainScreen.class);
                            startActivity(intent1);
                        }

                    }
                });

    }
}