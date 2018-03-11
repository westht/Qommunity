package com.team.a404.qommunity.Comunidad;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.a404.qommunity.Favores.ListFavores;
import com.team.a404.qommunity.Objetos.CommunityInformation;
import com.team.a404.qommunity.Objetos.UserInformation;
import com.team.a404.qommunity.R;

import java.util.ArrayList;

public class ListaComunidad extends AppCompatActivity  {
    private ListView listacomuns;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference DataRef;
    private DatabaseReference DataRef2;
    private TextView nombcomuni;
    private Button unirse, quitarse;
    private ArrayList<String> arraycomunidades = new ArrayList<>();

    public void crearComu(View view) {
        Intent intenti = new Intent(this, CrearComunidad.class);
        startActivity(intenti);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comunidad);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        firebaseAuth = FirebaseAuth.getInstance();
        DataRef = FirebaseDatabase.getInstance().getReference("comunidades");
        DataRef2 = FirebaseDatabase.getInstance().getReference("usuarios");
        listacomuns = (ListView)findViewById(R.id.comunidades);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arraycomunidades);
        listacomuns.setAdapter(arrayAdapter);
        listacomuns.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                final Dialog dialog = new Dialog(ListaComunidad.this);
                dialog.setContentView(R.layout.dialogunircomunidad);
                dialog.show();
                nombcomuni = (TextView)dialog.findViewById(R.id.NombreCom);
                unirse = (Button)dialog.findViewById(R.id.buttonjoin);
                quitarse = (Button)dialog.findViewById(R.id.buttonborrar);
                String [] arr0 = arraycomunidades.get(i).split(":");
                final String nombre = arr0[0].toString().trim();
                final String direccion = arr0[1].toString().trim();
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("comunidades");
                nombcomuni.setText(getString(R.string.comnombreunir)+"\n"+nombre+"\n"+getString(R.string.unirdirec)+"\n"+direccion);
                unirse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabase.child(nombre).child("usuarios").child(user.getUid()).child("email").setValue(user.getEmail().replace("@", "").replace(".", ""));
                        DataRef2.child(user.getUid()).child("comunidad").child(nombre).setValue(nombre);
                        dialog.hide();
                    }

                });
                quitarse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabase.child(nombre).child("usuarios").child(user.getUid()).child("email").removeValue();
                        DataRef2.child(user.getUid()).child("comunidad").child(nombre).removeValue();
                        dialog.hide();
                    }
                });
            }
        });
        DataRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CommunityInformation comun = dataSnapshot.getValue(CommunityInformation.class);
                arraycomunidades.add(comun.getNombre()+":"+"\n"+comun.getDireccion());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}