package com.team.a404.qommunity.Favores;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team.a404.qommunity.Objetos.favoresInformation;
import com.team.a404.qommunity.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFavores_final extends Fragment {
    private ListView listafinal;
    private FirebaseAuth firebaseAuth;
    private ArrayList<String> arrayfinal = new ArrayList<>();
    private TextView desc;
    private TextView fecha;
    private TextView hora, comunidad;
    private ArrayList<favoresInformation> favores = new ArrayList<>();



    public ListFavores_final() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_favores_final, container, false);
        favores.clear();
        arrayfinal.clear();
        listafinal = (ListView) rootView.findViewById(R.id.listafinalizados);
        firebaseAuth = FirebaseAuth.getInstance();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, arrayfinal);
        listafinal.setAdapter(arrayAdapter);
        final FirebaseUser fbuser = firebaseAuth.getCurrentUser();
        final DatabaseReference DataRefe = FirebaseDatabase.getInstance().getReference("usuarios").child(fbuser.getUid()).child("favores_finalizados");
        DataRefe.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                favoresInformation favor = dataSnapshot.getValue(favoresInformation.class);
                favores.add(favor);
                arrayfinal.add(dataSnapshot.getKey());
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
        listafinal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setTitle("Detalles del favor");
                dialog.setContentView(R.layout.dialog_favoresfinal);
                dialog.show();
                desc = (TextView) dialog.findViewById(R.id.descripfav);
                fecha = (TextView) dialog.findViewById(R.id.diafav);
                hora = (TextView) dialog.findViewById(R.id.horafav);
                comunidad = (TextView) dialog.findViewById(R.id.comunidadmifav);
                desc.setText(favores.get(i).getDescripcion().toString());
                desc.setSingleLine(false);
                fecha.setText(favores.get(i).getFecha().toString());
                hora.setText(favores.get(i).getHora().toString());
                comunidad.setText(favores.get(i).getComunidad());

            }
        });
        return rootView;
    }

}
