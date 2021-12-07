package com.edu.unab.oba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

<<<<<<< HEAD
    // Authentication for Firebase
    FirebaseAuth firebaseAuth;
    GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "Login with Google";
=======
    private ImageView btnHistorico,btnTienda,btnCarrito,btnChat;

>>>>>>> main

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Authorization
        firebaseAuth = FirebaseAuth.getInstance();

        signIn();


        // Botón para ir al marketplace
        btnMarketplace = findViewById(R.id.btnMarketplace);
        btnMarketplace.setOnClickListener(this);
=======
        btnHistorico = findViewById(R.id.btnHistorico);
        btnTienda = findViewById(R.id.btnTienda);
        btnCarrito = findViewById(R.id.btnCarrito);
        btnChat = findViewById(R.id.btnChat);

        btnTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MarketplaceActivity.class);
                startActivity(intent);
            }
        });
        btnHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, historico.class);
                startActivity(intent);
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AssistantActivity.class);
                startActivity(intent);
            }
        });

        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ModuloAdministrador.class);
                startActivity(intent);
            }
        });

>>>>>>> main
    }

    public void onLoggedIn(String usuario){
        Toast.makeText(this, "Bienvenido " + usuario, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

<<<<<<< HEAD
        switch (v.getId()){
            case R.id.btnMarketplace:
                Intent intMarketplace = new Intent(this, MarketplaceActivity.class);
                startActivity(intMarketplace);
                break;
            default:
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mStartForResult.launch(new Intent(signInIntent));
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            onLoggedIn(firebaseAuth.getCurrentUser().getDisplayName());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            onLoggedIn("Anónimo");
                        }
                    }
                });
=======
>>>>>>> main
    }

}