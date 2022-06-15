package com.example.carteiradigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private TextView text_tela_cadastro;
    private EditText remail, rsenha;
    private Button bt_entrar;
    String[] mensagens = {"Preencha todos os campos","Login realizado com sucesso"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        IniciarComponentes();
        getSupportActionBar().hide();

        text_tela_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });

        bt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = remail.getText().toString();
                String senha = rsenha.getText().toString();

                if(email.isEmpty() || senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();

                }else{
                    AutenticarUsuario();
                }

            }
        });


    }
    private void AutenticarUsuario() {
        String email = remail.getText().toString();
        String senha = rsenha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                TelaPrincipal();
            }
        });
    }
    private void TelaPrincipal(){
        Intent intent = new Intent(Login.this,Menuu.class);
        startActivity(intent);
        finish();
    }

    private void IniciarComponentes(){

        text_tela_cadastro = findViewById(R.id.bregister);
        remail = findViewById(R.id.remail);
        rsenha = findViewById(R.id.rsenha);
        bt_entrar = findViewById(R.id.bt_entrar);

    }

}