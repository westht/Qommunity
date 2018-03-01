package com.team.a404.qommunity.Favores;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.a404.qommunity.Comunidad.ListaComunidad;
import com.team.a404.qommunity.Login.LoginActivity;
import com.team.a404.qommunity.MainScreen;
import com.team.a404.qommunity.R;
import com.team.a404.qommunity.Ajustes.SettingsActivity;
import com.team.a404.qommunity.Objetos.UserInformation;

import java.util.ArrayList;
import java.util.Calendar;

public class ListFavores extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    private int year_x, month_x, day_x, hora_x, min_x;
    private EditText nombrefav, descfav, fechafav, horafav;
    private Spinner spinnercommunidades;
    private Button creafav;
    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list_favores);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ListFavores.this);
                dialog.setTitle("Detalles del favor");
                dialog.setContentView(R.layout.datosfavor);
                dialog.show();
                nombrefav = (EditText) dialog.findViewById(R.id.nombfavor);
                descfav = (EditText) dialog.findViewById(R.id.descripfavor);
                fechafav = (EditText) dialog.findViewById(R.id.CogerFecha);
                horafav = (EditText) dialog.findViewById(R.id.horafavor);
                creafav = (Button) dialog.findViewById(R.id.creafav);
                spinnercommunidades = (Spinner)dialog.findViewById(R.id.spincomuni);
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("comunidades");
                final FirebaseUser fbuser = firebaseAuth.getCurrentUser();
                fechafav.setInputType(InputType.TYPE_NULL);
                fechafav.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        showDialog(1);
                        return false;
                    }
                });

                horafav.setInputType(InputType.TYPE_NULL);
                horafav.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   showDialog(2);
                                               }
                                           }
                );
                creafav.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        int action = MotionEventCompat.getActionMasked(motionEvent);

                        switch (action) {
                            case (MotionEvent.ACTION_DOWN):
                                creafav.setBackgroundColor(getResources().getColor(R.color.ColorSecundario));
                                return false;

                            case (MotionEvent.ACTION_UP):
                                creafav.setBackgroundColor(getResources().getColor(R.color.ColorPrimario));
                                return false;


                        }
                        return false;
                    }
                });
                creafav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String nombre = nombrefav.getText().toString().trim();
                        String desc = descfav.getText().toString().trim();
                        String fecha = fechafav.getText().toString().trim();
                        String hora = horafav.getText().toString().trim();
                        mDatabase.child("yugi boy").child("favores").child(nombre).child("descripcion").setValue(desc);
                        mDatabase.child("yugi boy").child("favores").child(nombre).child("fecha").setValue(fecha);
                        mDatabase.child("yugi boy").child("favores").child(nombre).child("hora").setValue(hora);
                        mDatabase.child("yugi boy").child("favores").child(nombre).child("usuario").setValue(fbuser.getUid());
                        dialog.hide();


                    }
                });


            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new DatePickerDialog(this, DateLisen, year_x, month_x, day_x);
            case 2:
                return new TimePickerDialog(this, TimeLisen, hora_x, min_x, false);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener DateLisen = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            year_x = year;
            month_x = month + 1;
            day_x = day;
            fechafav.setText(day_x + " / " + month_x + " / " + year_x);
        }
    };

    private TimePickerDialog.OnTimeSetListener TimeLisen = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hora, int min) {
            hora_x = hora;
            min_x = min;
            horafav.setText(hora_x + " : " + min_x);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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

                id_nombre.setText("Hola " + user.getPersonName());
                //id_email.setText(user.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manejar clics del elemento de la barra de acción aquí. La barra de acción
        // maneja automáticamente los clics en el botón Inicio / Arriba, tan largo
        // cuando especifica una actividad principal en AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Un fragmento de marcador de posición que contiene una vista simple.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * El argumento de fragmento que representa el número de sección para este fragmento.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Devuelve una nueva instancia de este fragmento para el número de sección dado.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list_favores, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // se llama a getItem para crear una instancia del fragmento para la página dada.
            // Devuelve un PlaceholderFragment (definido como una clase interna estática a continuación).

            switch (position) {
                case 0:
                    return new ListFavores_my();
                case 1:
                    return new ListFavores_aceptado();
                case 2:
                    return new ListFavores_final();
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }
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


    /*
    *   MENU LATERAL
    */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            Intent intent = new Intent(this, MainScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_mis_favores) {

        } else if (id == R.id.nav_chats) {

        } /*else if (id == R.id.logout) {
            finish();
            firebaseAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } */else if (id == R.id.nav_comunidades) {
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
