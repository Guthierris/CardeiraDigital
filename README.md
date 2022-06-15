# CardeiraDigital<img align="center" alt="Gut-Js" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/javascript/javascript-plain.svg"><img align="center" alt="Gut-Android" height="30" width="40" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/androidstudio/androidstudio-original.svg">
Esta aplicação funciona com 3 activity, uma efetua o login, outra é uma activity de menu pós login, a outra faz o registro do usuário, o programa é integrado com o banco de dados FIREBASE do google.
deve ser feito a devida configuração com o android studio e o firebase, o programa tem todas as mensagens de error possiveis.
o programa registra os dados e armazena no banco de dados, logo apos o login ser autorizado, a tela de menu resgata as variaveis armazenadas no banco de dados, assim funcionando como uma carteira digital para o usuario, não monetária e sim de dados de documentos.
Para funcionar o programa você precisa emular o codigo no android studio, e utilizar de um android connectado usb ou em ambiente virtual.

#
⚙️ Configuração com banco de dados: em "build.gradle(projet..)"(linha5): id 'com.google.gms.google-services' version '4.3.10' apply false <br>
em "bild.gradle(module..)"(linha41: implementation platform('com.google.firebase:firebase-bom:30.1.0')
#
📱 Inteface: deve ser feito a configuração de capturar os devidos dados para registro antes do login, o usuario deverá ser autenticado antes de efeturar o login.
#
Codigo Fonte:

Classe de login

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
    
   Classe de menu
   
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
    
    
   Classe de Registro
   
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


    
