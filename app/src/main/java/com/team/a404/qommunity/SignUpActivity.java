package com.team.a404.qommunity;

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
import com.google.firebase.database.FirebaseDatabase;

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
        nombre = (EditText)findViewById(R.id.Nombre);
        telf=(EditText)findViewById(R.id.telefono);
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
        String nom = nombre.getText().toString().trim();
        String tel = telf.getText().toString().trim();
        firebaseAuth = FirebaseAuth.getInstance();
        DataRef = FirebaseDatabase.getInstance().getReference();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        if (password1.equals(password2)){
            if (TextUtils.isEmpty(email)) {
                // email is empty
                Toast.makeText(this, "Introduzca el E-mail", Toast.LENGTH_SHORT).show();
                // para la ejecucion
                return;
            }
            if (TextUtils.isEmpty(password1)) {
                // password is empty
                Toast.makeText(this, "Introduzca la contraseña", Toast.LENGTH_SHORT).show();
                // para la ejecucion
                return;
            }

            progressDialog.setMessage("Creando usuario.");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // se ha registrado
                        Toast.makeText(SignUpActivity.this, "Registrado", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        DataRef.child("usuarios");
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));


                    } else {
                        Toast.makeText(SignUpActivity.this, "No ha podido registrarse, intentalo de nuevo", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                }
            });
        }else{
            Toast.makeText(SignUpActivity.this, "La contraseña no coincide.", Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        }


    }
}
