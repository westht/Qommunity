package com.team.a404.qommunity.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.team.a404.qommunity.R;

/**
 * Created by Sergio Cuadrado on 22/01/2018.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    protected EditText mail,pass1,pass2,nombre,telf;
    protected Button log;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference DataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        mail = (EditText) findViewById(R.id.email);
        pass1 = (EditText) findViewById(R.id.pass1);
        pass2 = (EditText) findViewById(R.id.pass2);
        log = (Button) findViewById(R.id.crear);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        log.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == log) {
            registrar();
        }
    }

    private void registrar() {
         String email = mail.getText().toString().trim();
         String password1 = pass1.getText().toString().trim();
         String password2 = pass2.getText().toString().trim();



        if (password1.equals(password2)){
            if (TextUtils.isEmpty(email)) {
                // email is empty
                Toast.makeText(this, getString(R.string.meteuser), Toast.LENGTH_SHORT).show();
                // para la ejecucion
                return;
            }
            if (TextUtils.isEmpty(password1)) {
                // password is empty
                Toast.makeText(this, getString(R.string.metepass), Toast.LENGTH_SHORT).show();
                // para la ejecucion
                return;
            }


            progressDialog.setMessage(getString(R.string.registrando));
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // se ha registrado
                        Toast.makeText(SignUpActivity.this, getString(R.string.registra), Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        Intent intent = new Intent(SignUpActivity.this,CompletaPerfil.class);
                        startActivity(intent);



                    } else {
                        Toast.makeText(SignUpActivity.this, getString(R.string.noregistro), Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                }
            });
        }else{
            Toast.makeText(SignUpActivity.this, getString(R.string.nopass), Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        }
    }
}


