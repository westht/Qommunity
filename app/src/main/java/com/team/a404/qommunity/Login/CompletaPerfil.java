package com.team.a404.qommunity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team.a404.qommunity.MainScreen;
import com.team.a404.qommunity.R;


/**
 * Created by Sergio Cuadrado on 25/01/2018.
 */



public class CompletaPerfil extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference DataRef;

    private EditText et_name;
    private EditText telefono;
    private Button btn_enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completa_perfil);
        getSupportActionBar().hide();
        et_name = (EditText) findViewById(R.id.name);
        telefono = (EditText) findViewById(R.id.numero);
        btn_enviar = (Button) findViewById(R.id.btn_enviar);
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompletaPerfil.this,MainScreen.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        DataRef = FirebaseDatabase.getInstance().getReference();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            Intent intent = new Intent(CompletaPerfil.this, LoginActivity.class);
            startActivity(intent);
        }
        btn_enviar.setOnClickListener(this);
    }

    private void SalvarDatos(){
        String name = et_name.getText().toString().trim();
        String telf = telefono.getText().toString().trim();
        FirebaseUser usuario = firebaseAuth.getCurrentUser();
        DataRef.child("usuarios").child(usuario.getUid()).child("nombre").setValue(name);
        DataRef.child("usuarios").child(usuario.getUid()).child("telefono").setValue(telf);
        DataRef.child("usuarios").child(usuario.getUid()).child("email").setValue(usuario.getEmail());
        Toast.makeText(this, "Informacion guardada",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(CompletaPerfil.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
    @Override
    public void onClick(View view) {
            SalvarDatos();

    }
}
