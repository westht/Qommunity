package com.team.a404.qommunity.Favores;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.a404.qommunity.Comunidad.CrearComunidad;
import com.team.a404.qommunity.Login.LoginActivity;
import com.team.a404.qommunity.MainActivity;
import com.team.a404.qommunity.MainScreen;
import com.team.a404.qommunity.Objetos.UserInformation;
import com.team.a404.qommunity.Objetos.favoresInformation;
import com.team.a404.qommunity.R;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFavores_my extends Fragment {
    private ListView listafav;
    private FirebaseAuth firebaseAuth;
    private ArrayList<String> arrayList = new ArrayList<>();
    private EditText desc, fecha, hora;
    private TextView nombreuser;
    private Button finalizafav;
    private ArrayList<favoresInformation> favores = new ArrayList<>();


    public ListFavores_my() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        arrayList.clear();
        favores.clear();
        View rootView = inflater.inflate(R.layout.fragment_list_favores_my, container, false);
        listafav = (ListView) rootView.findViewById(R.id.milistafavores);
        firebaseAuth = FirebaseAuth.getInstance();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayList);
        listafav.setAdapter(arrayAdapter);
        final FirebaseUser fbuser = firebaseAuth.getCurrentUser();
        final DatabaseReference DataRefe = FirebaseDatabase.getInstance().getReference("usuarios").child(fbuser.getUid()).child("favores");
        DataRefe.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                favoresInformation favor = dataSnapshot.getValue(favoresInformation.class);
                arrayList.add(dataSnapshot.getKey());
                favores.add(favor);
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
        listafav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Dialog dialog = new Dialog(getContext());
                dialog.setTitle("Detalles del favor");
                dialog.setContentView(R.layout.dialog_favorespropios);
                dialog.show();
                desc = (EditText) dialog.findViewById(R.id.descripfav);
                fecha = (EditText) dialog.findViewById(R.id.diafav);
                hora = (EditText) dialog.findViewById(R.id.horafav);
                nombreuser = (TextView) dialog.findViewById(R.id.usuariofav);
                finalizafav = (Button) dialog.findViewById(R.id.finalizafavor);
                desc.setText(favores.get(i).getDescripcion().toString());
                fecha.setText(favores.get(i).getFecha().toString());
                hora.setText(favores.get(i).getHora().toString());
                String uiduser = favores.get(i).getUsuario_acepta();
                try {
                        final DatabaseReference DataReferencia = FirebaseDatabase.getInstance().getReference("usuarios").child(uiduser);
                        DataReferencia.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                UserInformation usr = dataSnapshot.getValue(UserInformation.class);
                                nombreuser.setText(usr.getPersonName().toString());


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                } catch (NullPointerException e) {
                    nombreuser.setText(getString(R.string.sinuser));
                    Log.v("C", "Sin nada");
                }


            }
        });
        return rootView;
    }
}