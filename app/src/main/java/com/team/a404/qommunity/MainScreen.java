package com.team.a404.qommunity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.a404.qommunity.Ajustes.Ajustes;
import com.team.a404.qommunity.Comunidad.ListaComunidad;
import com.team.a404.qommunity.Favores.ListFavores;
import com.team.a404.qommunity.Login.CompletaPerfil;
import com.team.a404.qommunity.Objetos.UserInformation;
import com.team.a404.qommunity.Objetos.favoresInformation;

import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth firebaseAuth;

    private ListView lista_de_favores;
    private Spinner spinnercomunidades;
    SwipeRefreshLayout swipeRefreshLayout;
    private TextView desc;
    private TextView fecha;
    private TextView hora;
    private TextView nombreuser;
    private Button aceptafav;

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<favoresInformation> favores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.miswipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lista_de_favores.invalidateViews();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        lista_de_favores = (ListView) findViewById(R.id.lista_de_favores);
        spinnercomunidades = (Spinner) findViewById(R.id.spinnercomunidad);
        final FirebaseUser fbuser = firebaseAuth.getCurrentUser();
        final DatabaseReference DataRefe = FirebaseDatabase.getInstance().getReference("usuarios").child(fbuser.getUid()).child("comunidad");
        DataRefe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> areas = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.getKey();
                    areas.add(areaName);
                }
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(MainScreen.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnercomunidades.setAdapter(areasAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        spinnercomunidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                favores.clear();
                arrayList.clear();
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
                lista_de_favores.setAdapter(adapter);
                final DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference("comunidades").child(spinnercomunidades.getSelectedItem().toString()).child("favores");
                DataRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        try{
                        favoresInformation favor = dataSnapshot.getValue(favoresInformation.class);

                            if (favor.getEstado().equals("pendiente")) {
                                arrayList.add(dataSnapshot.getKey());
                                favores.add(favor);
                                adapter.notifyDataSetChanged();

                            }
                        }catch (NullPointerException e){
                            Log.v("C", "Sin nada");

                        }
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
                lista_de_favores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        final Dialog dialog = new Dialog(MainScreen.this);
                        dialog.setTitle("Detalles del favor");
                        dialog.setContentView(R.layout.dialog_favoresmainscreen);
                        dialog.show();
                        desc = (TextView) dialog.findViewById(R.id.descripfav);
                        fecha = (TextView) dialog.findViewById(R.id.diafav);
                        hora = (TextView) dialog.findViewById(R.id.horafav);
                        nombreuser = (TextView) dialog.findViewById(R.id.usuariofav);
                        aceptafav = (Button) dialog.findViewById(R.id.aceptar);
                        desc.setSingleLine(false);
                        String uiduser = favores.get(i).getUsuario();
                        final DatabaseReference DataReferencia = FirebaseDatabase.getInstance().getReference("usuarios").child(uiduser);
                        desc.setText(favores.get(i).getDescripcion().toString());
                        fecha.setText(favores.get(i).getFecha().toString());
                        hora.setText(favores.get(i).getHora().toString());
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
                        aceptafav.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mbase = FirebaseDatabase.getInstance().getReference("usuarios").child(fbuser.getUid()).child("favores_aceptados");
                                mbase.child(arrayList.get(i).toString()).child("descripcion").setValue(desc.getText().toString());
                                mbase.child(arrayList.get(i).toString()).child("fecha").setValue(fecha.getText().toString());
                                mbase.child(arrayList.get(i).toString()).child("hora").setValue(hora.getText().toString());
                                mbase.child(arrayList.get(i).toString()).child("comunidad").setValue(spinnercomunidades.getSelectedItem().toString());
                                mbase.child(arrayList.get(i).toString()).child("estado").setValue("aceptado");
                                DatabaseReference nuevo = FirebaseDatabase.getInstance().getReference("usuarios").child(favores.get(i).getUsuario()).child("favores");
                                nuevo.child(arrayList.get(i).toString()).child("usuario").setValue(fbuser.getUid().toString());
                                DatabaseReference mbase2 = FirebaseDatabase.getInstance().getReference("comunidades").child(spinnercomunidades.getSelectedItem().toString()).child("favores");
                                mbase2.child(arrayList.get(i).toString()).child("estado").setValue("aceptado");
                                mbase2.child(arrayList.get(i).toString()).child("usuarioacepta").setValue(fbuser.getUid().toString());
                                lista_de_favores.invalidateViews();

                                Toast.makeText(MainScreen.this, getString(R.string.yaaceptado), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });


                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        final TextView id_nombre = findViewById(R.id.id_nombre);
        //final TextView id_email = findViewById(R.id.id_email);
        FirebaseUser usero = FirebaseAuth.getInstance().getCurrentUser();
        String userRef = usero.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("usuarios");
        mDatabase.child(userRef).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<UserInformation> userlist = new ArrayList<UserInformation>();

                UserInformation user = dataSnapshot.getValue(UserInformation.class);
                userlist.add(user);
                try{
                    id_nombre.setText(getString(R.string.hello) + user.getPersonName());
                    String numero = user.getTel();
                } catch(Exception x){
                    Intent intent = new Intent(MainScreen.this, CompletaPerfil.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                //id_email.setText(user.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            // Handle the camera action
        } else if (id == R.id.nav_mis_favores) {
            Intent intent = new Intent(this, ListFavores.class);
            favores.clear();
            arrayList.clear();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
       /*else if (id == R.id.logout) {
            finish();
            firebaseAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } }*/
        } else if (id == R.id.nav_comunidades) {
            Intent intent = new Intent(this, ListaComunidad.class);
            startActivity(intent);
        } else if (id == R.id.opcions) {
            Intent intent = new Intent(this, Ajustes.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
