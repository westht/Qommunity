package com.team.a404.qommunity.Comunidad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class CrearComunidad extends AppCompatActivity {
    private Button crear;
    private EditText nombre, direccion;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference DataRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crea_comunidad);
        crear = (Button) findViewById(R.id.crearnueva);
        nombre = (EditText) findViewById(R.id.comuninombre);
        direccion = (EditText) findViewById(R.id.direccion);
        firebaseAuth = FirebaseAuth.getInstance();
        DataRef = FirebaseDatabase.getInstance().getReference();
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearComun();
            }
        });

    }

    protected void CrearComun() {
        /*Intent intento = new Intent();
        PendingIntent Pintent = PendingIntent.getActivity(CrearComunidad.this,0,intento,0);
        Notification notifi = new Notification.Builder(CrearComunidad.this)
                .setTicker("Ticker Title")
                .setContentTitle("Content Title")
                .setContentText("Content text")
                .setSmallIcon(R.drawable.q)
                .setContentIntent(Pintent).getNotification();

        notifi.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,notifi);*/
        String nomb = nombre.getText().toString().trim();
        String direc = direccion.getText().toString().trim();
        FirebaseUser usuario = firebaseAuth.getCurrentUser();
        DataRef.child("comunidades").child(nomb).child("nombre").setValue(nomb);
        DataRef.child("comunidades").child(nomb).child("direccion").setValue(direc);
        DataRef.child("comunidades").child(nomb).child("usuarios").child(usuario.getUid()).child("email").setValue(usuario.getEmail().replace("@", "").replace(".", ""));
        Toast.makeText(this, "Comunidad creada", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CrearComunidad.this, ListaComunidad.class);
        startActivity(intent);
    }
}
