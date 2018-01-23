package com.team.a404.qommunity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Sergio Cuadrado on 22/01/2018.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    protected EditText mail;
    protected EditText pass;
    protected Button log;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        mail = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        log = (Button)findViewById(R.id.crear);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        log.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == log){
            registrar();
        }
    }

    private void registrar() {
        String email = mail.getText().toString().trim();
        String password =pass.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            // email is empty
            Toast.makeText(this,"Introduzca el E-mail",Toast.LENGTH_SHORT).show();
            // para la ejecucion
            return;
        }
        if (TextUtils.isEmpty(password)){
            // password is empty
            Toast.makeText(this,"Introduzca la contraseña",Toast.LENGTH_SHORT).show();
            // para la ejecucion
            return;
        }
        progressDialog.setMessage("Creando usuario.");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // se ha registrado
                    Toast.makeText(SignUpActivity.this,"Registrado",Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }else {
                    Toast.makeText(SignUpActivity.this,"No ha podido registrarse, intentalo de nuevo",Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }
            }
        });
    }
}
