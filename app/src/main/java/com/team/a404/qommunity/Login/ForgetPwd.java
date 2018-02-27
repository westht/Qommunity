package com.team.a404.qommunity.Login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.team.a404.qommunity.R;

public class ForgetPwd extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    protected Button boton_enviar;
    protected EditText Correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

        Correo = (EditText) findViewById(R.id.correo);
        boton_enviar = (Button) findViewById(R.id.boton_enviar);
        firebaseAuth = FirebaseAuth.getInstance();
        boton_enviar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == boton_enviar){
            String text_correo = Correo.getText().toString();
            if (text_correo.contains("@gmail") || text_correo.contains("@hotmail")) {
                firebaseAuth.sendPasswordResetEmail(text_correo);
            }
        }
    }
}
