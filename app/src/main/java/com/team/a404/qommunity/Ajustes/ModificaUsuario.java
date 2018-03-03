package com.team.a404.qommunity.Ajustes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.team.a404.qommunity.R;
import com.team.a404.qommunity.Objetos.UserInformation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ModificaUsuario extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference().child("usuarios");
    private TextView nombre, telefon;
    private Button guarda;
    private EditText nomb, telf;
    private ImageView imagenperfil;
    private CircularImageView imagen;
    private static final int PICK_IMAGE_REQUEST = 100;
    Uri imageUri;
    StorageReference storageReference;
    FirebaseUser usuario = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_usuario);
        nombre = (TextView) findViewById(R.id.nombre);
        telefon = (TextView) findViewById(R.id.numeromod);
        nomb = (EditText) findViewById(R.id.nombremodifica);
        telf = (EditText) findViewById(R.id.numeromodifica);
        guarda = (Button) findViewById(R.id.guarda);
        imagenperfil = (ImageView) findViewById(R.id.imagenperfil);
        imagen = (CircularImageView)findViewById(R.id.imagen);
        imagenperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();

            }
        });
        DataRef.child(usuario.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                ArrayList<UserInformation> userlist = new ArrayList<UserInformation>();
                UserInformation user = dataSnapshot.getValue(UserInformation.class);
                userlist.add(user);
                nomb.setText(user.getPersonName());
                telf.setText(user.getTel());
                StorageReference stor = FirebaseStorage.getInstance().getReference().child("images/"+usuario.getUid().toString()+"/userphoto.jpg");
                final long ONE_MEGABYTE = 1024 * 1024;
                stor.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imagen.setImageBitmap(bmp);
                        imagenperfil.setVisibility(View.VISIBLE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        guarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardaDatos();
            }
        });


    }

    private void OpenGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getInstance().getReference().child("images").child(firebaseAuth.getCurrentUser().getUid()).child("userphoto.jpg");
        if (resultcode == RESULT_OK && requestcode == PICK_IMAGE_REQUEST) {
            imageUri = data.getData();
            imagen.setImageURI(imageUri);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] img = baos.toByteArray();
            UploadTask uploadTask = storageRef.putBytes(img);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    DataRef.child(usuario.getUid()).child("urlfoto").setValue(downloadUrl.toString());
                    Log.d("downloadUrl-->", "" + downloadUrl);
                    Snackbar.make(guarda, "Imagen Guardada", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
        }


    }
    private void GuardaDatos() {
        String nombre = nomb.getText().toString().trim();
        String numero = telf.getText().toString().trim();
        DataRef.child(usuario.getUid()).child("nombre").setValue(nombre);
        DataRef.child(usuario.getUid()).child("telefono").setValue(numero);
        Snackbar.make(guarda, "Datos Guardados", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
