package com.team.a404.qommunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ModificaUsuario extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference DataRef;
    private TextView nombre,telefon;
    private Button guarda;
    private EditText nomb,telf;
    private ImageButton imagenperfil;
    FirebaseUser usuario = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_usuario);
        getSupportActionBar().hide();
        nombre = (TextView)findViewById(R.id.nombre);
        telefon = (TextView)findViewById(R.id.numeromod);
        nomb = (EditText)findViewById(R.id.nombremodifica);
        telf = (EditText)findViewById(R.id.numeromodifica);
        guarda=(Button)findViewById(R.id.cambia);
        imagenperfil=(ImageButton)findViewById(R.id.perfilimage);


        guarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardaDatos();
            }
        });



    }
    private void GuardaDatos(){
        String nombre = nomb.getText().toString().trim();
        String numero = telf.getText().toString().trim();
        DataRef.child("usuarios").child(usuario.getUid()).child("nombre").setValue(nombre);
        DataRef.child("usuarios").child(usuario.getUid()).child("telefono").setValue(numero);
        Toast.makeText(this, "Informacion guardada",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ModificaUsuario.this,SettingsActivity.class);
        startActivity(intent);
    }
}
