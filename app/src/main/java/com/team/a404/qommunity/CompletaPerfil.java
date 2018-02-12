package com.team.a404.qommunity;

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


/**
 * Created by Sergio Cuadrado on 25/01/2018.
 */



public class CompletaPerfil extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button boton_logout;
    private DatabaseReference DataRef;

    private EditText et_name;
    private EditText et_adres;
    private Button btn_enviar;
    private Button chg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completa_perfil);
        getSupportActionBar().hide();

        et_name = (EditText) findViewById(R.id.et_name);
        et_adres = (EditText) findViewById(R.id.et_address);
        btn_enviar = (Button) findViewById(R.id.btn_enviar);
        chg = (Button) findViewById(R.id.button2);
        chg.setOnClickListener(new View.OnClickListener() {
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
        boton_logout = (Button) findViewById(R.id.boton_logout);

        boton_logout.setOnClickListener(this);
        btn_enviar.setOnClickListener(this);
    }

    private void SalvarDatos(){
        String name = et_name.getText().toString().trim();
        String address = et_adres.getText().toString().trim();

        UserInformation user = new UserInformation(name,address);

        FirebaseUser usuario = firebaseAuth.getCurrentUser();

        DataRef.child(usuario.getDisplayName()).setValue(user);


        Toast.makeText(this, "Informacion guardada",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick(View view) {
        if (view == boton_logout) {
            finish();
            firebaseAuth.signOut();
            Intent intent = new Intent(CompletaPerfil.this, LoginActivity.class);
            startActivity(intent);
        }
        if (view == btn_enviar){
            SalvarDatos();
        }
    }
}
