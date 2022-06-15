package com.example.carteiradigital;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Menuu extends AppCompatActivity {

    private TextView nomeusuario, emailusuario, cpfusuario, rgusuario, celularusuario, nascimentousuario, enderecousuario, titulousuario;
    private Button bt_deslogar;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    String usuarioID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuu);
        getSupportActionBar().hide();

        IniciarComponentes();
        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Menuu.this,Login.class);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();



        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = database.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    nomeusuario.setText(documentSnapshot.getString("nome"));
                    cpfusuario.setText(documentSnapshot.getString("cpf"));
                    rgusuario.setText(documentSnapshot.getString("rg"));
                    celularusuario.setText(documentSnapshot.getString("celular"));
                    nascimentousuario.setText(documentSnapshot.getString("nascimento"));
                    enderecousuario.setText(documentSnapshot.getString("endereco"));
                    titulousuario.setText(documentSnapshot.getString("titulo"));




                    emailusuario.setText(email);

                }
            }
        });
    }

    private void IniciarComponentes(){
        nomeusuario=findViewById(R.id.tnome);
        emailusuario = findViewById(R.id.temail);
        cpfusuario = findViewById(R.id.tcpf);
        rgusuario = findViewById(R.id.trg);
        celularusuario = findViewById(R.id.tnumero);
        nascimentousuario = findViewById(R.id.tnascimento);
        enderecousuario = findViewById(R.id.tendereco);
        titulousuario = findViewById(R.id.ttitulo);
        bt_deslogar = findViewById(R.id.bt_deslogar);


    }



}