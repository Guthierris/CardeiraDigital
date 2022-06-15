package com.example.carteiradigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText rnome, rsenha, rcpf, rrg, remail, rcelular, rtitulo, rnascimento, rendereco;
    private Button bt_cadastrar;
    String[] mensagens = {"preencha todos os campos","cadastro reaqlizado com sucesso"};
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();
        IniciarComponentes();
        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = rnome.getText().toString();
                String senha = rsenha.getText().toString();
                String cpf = rcpf.getText().toString();
                String rg = rrg.getText().toString();
                String email = remail.getText().toString();
                String celular = rcelular.getText().toString();
                String titulo = rtitulo.getText().toString();
                String nascimento = rnascimento.getText().toString();
                String endereco = rendereco.getText().toString();


                if (nome.isEmpty() || senha.isEmpty() || cpf.isEmpty() || rg.isEmpty() || email.isEmpty() || celular.isEmpty() || titulo.isEmpty() || nascimento.isEmpty() || celular.isEmpty() || endereco.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                }else{
                    CadastrarUsuario(v);
                }
            }
        });
    }
    private void CadastrarUsuario(View v){
        String email = remail.getText().toString();
        String senha = rsenha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    SalvarDadosUsuario();


                    Snackbar snackbar = Snackbar.make(v,mensagens[1],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                }else{
                    String erro;
                    try {
                        throw task.getException();

                    }catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Digite uma senha com no minimo 6 numeros !";
                    }catch (FirebaseAuthUserCollisionException e) {
                        erro = "Esta conta já está em uso !";
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "E-mail inválido !";
                    }catch (Exception e){
                        erro = "Erro ao cadastrar usuário";
                    }
                    Snackbar snackbar = Snackbar.make(v,erro,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                }
            }
        });
    }

    private void SalvarDadosUsuario(){
        String nome = rnome.getText().toString();
        String cpf = rcpf.getText().toString();
        String rg = rrg.getText().toString();
        String celular = rcelular.getText().toString();
        String titulo = rtitulo.getText().toString();
        String nascimento = rnascimento.getText().toString();
        String endereco = rendereco.getText().toString();

        FirebaseFirestore database = FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("nome",nome);
        usuarios.put("cpf",cpf);
        usuarios.put("rg",rg);
        usuarios.put("celular",celular);
        usuarios.put("titulo",titulo);
        usuarios.put("nascimento",nascimento);
        usuarios.put("endereco",endereco);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = database.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("database", "sucesso ao salvar os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("database", "erro ao salvar os dados" + e.toString() );
                    }
                });
    }

    private void IniciarComponentes(){
        rnome = findViewById(R.id.rnome);
        rsenha = findViewById(R.id.rsenha);
        rcpf = findViewById(R.id.rcpf);
        rrg = findViewById(R.id.rrg);
        remail = findViewById(R.id.remail);
        rcelular = findViewById(R.id.rcelular);
        rtitulo = findViewById(R.id.rtitulo);
        rnascimento = findViewById(R.id.rnascimento);
        rendereco = findViewById(R.id.rendereco);

        bt_cadastrar = findViewById(R.id.bt_cadastrar);
    }

}