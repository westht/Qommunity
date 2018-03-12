package com.team.a404.qommunity.Favores;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private ArrayList<String> arrayaceptado = new ArrayList<>();
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
        arrayaceptado.clear();
        listafinal = (ListView) rootView.findViewById(R.id.listafinalizados);
        firebaseAuth = FirebaseAuth.getInstance();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, arrayaceptado);
        listafinal.setAdapter(arrayAdapter);
        final FirebaseUser fbuser = firebaseAuth.getCurrentUser();
        final DatabaseReference DataRefe = FirebaseDatabase.getInstance().getReference("usuarios").child(fbuser.getUid()).child("favores_finalizados");
        DataRefe.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrayaceptado.add(dataSnapshot.getKey());
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

            }
        });
        return rootView;
    }

}
