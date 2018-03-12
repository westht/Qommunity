package com.team.a404.qommunity.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.team.a404.qommunity.MainScreen;
import com.team.a404.qommunity.Objetos.UserInformation;
import com.team.a404.qommunity.R;

import java.util.ArrayList;

/**
 * Created by Sergio Cuadrado on 22/01/2018.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    protected EditText user;
    protected EditText pass;
    protected TextView sincuenta;
    protected Button log;
    protected TextView nopass;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference().child("usuarios");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activ);
        getSupportActionBar().hide();
        user = (EditText) findViewById(R.id.usuario);
        pass = (EditText) findViewById(R.id.pass1);
        sincuenta = (TextView) findViewById(R.id.nocuenta);
        log = (Button) findViewById(R.id.logbutt);
        nopass = (TextView) findViewById(R.id.nopass);
        progressDialog = new ProgressDialog(this);
        log.setOnClickListener(this);
        nopass.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            //prfi
            finish();
            FirebaseUser usuario = firebaseAuth.getCurrentUser();
            DataRef.child(usuario.getUid());
            DataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    ArrayList<UserInformation> userlist = new ArrayList<UserInformation>();
                    FirebaseUser usero = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("usuarios/"+usero.getUid());
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            UserInformation user = dataSnapshot.getValue(UserInformation.class);


                            try {
                                if (user.getPersonName() != null || user.getTel() != null){
                                    Intent intent = new Intent(LoginActivity.this, MainScreen.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent(LoginActivity.this, CompletaPerfil.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }catch (Exception x){
                                Intent intent = new Intent(LoginActivity.this, CompletaPerfil.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        sincuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        closeSoftKeyBoard();
        if (view == log) {
            userLogin();
        }
        if (view == nopass){
            Intent intent = new Intent(LoginActivity.this, ForgetPwd.class);
            startActivity(intent);
        }
    }

    private void userLogin() {
        String usr = user.getText().toString().trim();
        String pwd = pass.getText().toString().trim();


        if (TextUtils.isEmpty(usr)) {
            // email is empty
            Snackbar.make(log, getString(R.string.meteuser), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return;
            // para la ejecucion
        }
        if (TextUtils.isEmpty(pwd)) {
            // password is empty
            Snackbar.make(log, getString(R.string.metepass), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            // para la ejecucion
            return;
        }
        progressDialog.setMessage(getString(R.string.logeando));
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(usr, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            FirebaseUser usuario = firebaseAuth.getCurrentUser();
                            DataRef.child(usuario.getUid());
                            DataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(final DataSnapshot dataSnapshot) {
                                    ArrayList<UserInformation> userlist = new ArrayList<UserInformation>();
                                    FirebaseUser usero = FirebaseAuth.getInstance().getCurrentUser();
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("usuarios/"+usero.getUid());
                                    mDatabase.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            UserInformation user = dataSnapshot.getValue(UserInformation.class);


                                            try {
                                                if (user.getPersonName() != null || user.getTel() != null){
                                                    Intent intent = new Intent(LoginActivity.this, MainScreen.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                }else{
                                                    Intent intent = new Intent(LoginActivity.this, CompletaPerfil.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                }
                                            }catch (Exception x){
                                                Intent intent = new Intent(LoginActivity.this, CompletaPerfil.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }else{
                            Snackbar.make(log, getString(R.string.nolog), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }

                    }
                });
    }
    public void closeSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(log.getWindowToken(), 0);
    }
}