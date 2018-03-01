package com.team.a404.qommunity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;
import com.team.a404.qommunity.Ajustes.SettingsActivity;
import com.team.a404.qommunity.Comunidad.ListaComunidad;
import com.team.a404.qommunity.Favores.ListFavores;
import com.team.a404.qommunity.Login.LoginActivity;
import com.team.a404.qommunity.Objetos.CommunityInformation;
import com.team.a404.qommunity.Objetos.UserInformation;
import com.team.a404.qommunity.Objetos.favoresInformation;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private FirebaseAuth firebaseAuth;

    private ListView lista_de_favores;

    private ArrayList<String> arrayList =new ArrayList<>();

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

        final DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference("comunidades/yugi boy/favores");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);

        lista_de_favores = (ListView) findViewById(R.id.lista_de_favores);
        lista_de_favores.setAdapter(adapter);


        DataRef.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s){

                favoresInformation favor = dataSnapshot.getValue(favoresInformation.class);
                arrayList.add(favor.getDescripcion() + ":" + favor.getFecha() + ":" + favor.getHora() + ":" + favor.getUsuario());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s){

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot){

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s){

            }
            @Override
            public void onCancelled(DatabaseError databaseError){

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
                id_nombre.setText("Hola "+user.getPersonName());
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
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_chats) {

        } else if (id == R.id.logout) {
            finish();
            firebaseAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_comunidades) {
            Intent intent = new Intent(this, ListaComunidad.class);
            startActivity(intent);
        } else if (id == R.id.opcions) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
