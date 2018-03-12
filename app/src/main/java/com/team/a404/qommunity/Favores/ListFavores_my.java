package com.team.a404.qommunity.Favores;


import android.app.Dialog;
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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.a404.qommunity.Objetos.UserInformation;
import com.team.a404.qommunity.Objetos.favoresInformation;
import com.team.a404.qommunity.R;

import java.util.ArrayList;

import static java.lang.Thread.sleep;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFavores_my extends Fragment {
    private ListView listafav;
    private FirebaseAuth firebaseAuth;
    private ArrayList<String> arrayList = new ArrayList<>();
    private TextView desc;
    private TextView fecha;
    private TextView hora, comunidad;
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
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                final Dialog dialog = new Dialog(getContext());
                dialog.setTitle("Detalles del favor");
                dialog.setContentView(R.layout.dialog_favorespropios);
                dialog.show();
                desc = (TextView) dialog.findViewById(R.id.descripfav);
                fecha = (TextView) dialog.findViewById(R.id.diafav);
                hora = (TextView) dialog.findViewById(R.id.horafav);
                nombreuser = (TextView) dialog.findViewById(R.id.usuariofav);
                finalizafav = (Button) dialog.findViewById(R.id.finalizafavor);
                comunidad = (TextView) dialog.findViewById(R.id.comunidadmifav);
                desc.setText(favores.get(i).getDescripcion().toString());
                fecha.setText(favores.get(i).getFecha().toString());
                hora.setText(favores.get(i).getHora().toString());
                comunidad.setText(favores.get(i).getComunidad());


                try {
                    String uiduser = favores.get(i).getUsuario().toString();
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

                finalizafav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            final DatabaseReference DataRefe = FirebaseDatabase.getInstance().getReference("usuarios").child(favores.get(i).getUsuario().toString()).child("favores_finalizados").child(arrayList.get(i).toString());
                            DataRefe.child("descripcion").setValue(favores.get(i).getDescripcion().toString());
                            DataRefe.child("comunidad").setValue(favores.get(i).getComunidad().toString());
                            DataRefe.child("fecha").setValue(favores.get(i).getFecha().toString());
                            DataRefe.child("hora").setValue(favores.get(i).getHora().toString());


                            final DatabaseReference DataReferencia = FirebaseDatabase.getInstance().getReference("comunidades").child(favores.get(i).getComunidad().toString()).child("favores").child(arrayList.get(i).toString());
                            DataReferencia.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        child.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            final DatabaseReference DataReferencia2 = FirebaseDatabase.getInstance().getReference("usuarios").child(fbuser.getUid()).child("favores").child(arrayList.get(i).toString());
                            DataReferencia2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        child.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            final DatabaseReference DataReferencia3 = FirebaseDatabase.getInstance().getReference("usuarios").child(favores.get(i).getUsuario().toString()).child("favores_aceptados").child(arrayList.get(i).toString());
                            DataReferencia3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        child.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        } catch (NullPointerException e) {
                            Log.v("C", "Sin nada");
                        }

                        dialog.dismiss();

                    }
                });
            }
        });

        return rootView;
    }
}