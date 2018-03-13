package com.team.a404.qommunity.Ajustes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.team.a404.qommunity.Login.LoginActivity;
import com.team.a404.qommunity.Objetos.UserInformation;
import com.team.a404.qommunity.R;

import java.util.ArrayList;

import static android.support.v4.view.MotionEventCompat.getActionMasked;

public class Ajustes extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private LinearLayout m_cerrar,m_pass,m_edit_perfil;
    private TextView nombre_user,email_user;

    private CircularImageView avatar_user;
    private DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference().child("usuarios");
    FirebaseUser usuario = firebaseAuth.getCurrentUser();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        m_cerrar = (LinearLayout) findViewById(R.id.m_cerrar);
        m_pass = (LinearLayout) findViewById(R.id.m_pass);
        m_edit_perfil = (LinearLayout) findViewById(R.id.m_edit_perfil);
        avatar_user = (CircularImageView)findViewById(R.id.avatar_user);
        nombre_user = (TextView)findViewById(R.id.nombre_user);
        email_user = (TextView)findViewById(R.id.email_user);

        try{
            DataRef.child(usuario.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    ArrayList<UserInformation> userlist = new ArrayList<UserInformation>();
                    UserInformation user = dataSnapshot.getValue(UserInformation.class);
                    userlist.add(user);
                    nombre_user.setText(user.getPersonName());
                    email_user.setText(user.getEmail());



                    StorageReference stor = FirebaseStorage.getInstance().getReference().child("images/"+usuario.getUid().toString()+"/userphoto.jpg");
                    final long ONE_MEGABYTE = 1024 * 1024;
                    stor.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            avatar_user.setImageBitmap(bmp);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            avatar_user.setImageResource(R.drawable.logo_qommunity);
                        }
                    });
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    avatar_user.setImageResource(R.drawable.logo_qommunity);
                }
            });

        }catch (Exception e){
            avatar_user.setImageResource(R.drawable.logo_qommunity);
        }




        m_edit_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ajustes.this, ModificaUsuario.class);
                startActivity(intent);
            }
        });


        m_cerrar.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = getActionMasked(motionEvent);

                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        m_cerrar.setBackgroundColor(getResources().getColor(R.color.ColorSecundario));
                        return false;

                    case (MotionEvent.ACTION_UP):
                        m_cerrar.setBackgroundColor(getResources().getColor(R.color.ColorPrimario));

                        return false;


                }
                return false;
            }
        });

        m_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                firebaseAuth.signOut();
                Intent intent = new Intent(Ajustes.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        m_pass.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = getActionMasked(motionEvent);

                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        m_pass.setBackgroundColor(getResources().getColor(R.color.ColorSecundario));
                        return false;

                    case (MotionEvent.ACTION_UP):
                        m_pass.setBackgroundColor(getResources().getColor(R.color.ColorPrimario));
                        return false;


                }
                return false;
            }
        });
        m_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
