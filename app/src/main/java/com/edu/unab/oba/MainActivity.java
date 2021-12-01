package com.edu.unab.oba;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

import java.util.ArrayList;

import model.Cart;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnMarketplace;
    private ImageView iv_logo,btnHistorico,btnTienda,btnCarrito,btnChat;
    private EditText et_usuario,et_contraseña;
    private TextView tv_recuperarContra,tv_registrarse;
    private String correo,contraseña;
    ArrayList<Cart> cartProducts = new ArrayList<>();

    // Authentication for Firebase
    FirebaseAuth firebaseAuth;
    GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "Login with Google";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botón para ir al marketplace
        btnMarketplace = findViewById(R.id.btnMarketplace);
        btnHistorico = findViewById(R.id.btnHistorico);
        btnTienda = findViewById(R.id.btnTienda);
        btnCarrito = findViewById(R.id.btnCarrito);
        btnChat = findViewById(R.id.btnChat);
        iv_logo = findViewById(R.id.iv_logo);
        et_usuario = findViewById(R.id.et_usuario);
        et_contraseña = findViewById(R.id.et_contraseña);
        tv_recuperarContra = findViewById(R.id.tv_recuperarContra);
        tv_registrarse = findViewById(R.id.tv_registrarse);

        //iv_logo.setImageResource(R.drawable.ic_usuario);

        correo =et_usuario.getText().toString();
        contraseña =et_contraseña.getText().toString();

        btnMarketplace.setOnClickListener(this);

        tv_recuperarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RecuperarContraActivity.class);
                startActivity(intent);
            }
        });
        tv_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrarUsuarioActivity.class));
            }
        });

        btnTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MarketplaceActivity.class);
                startActivity(intent);
            }
        });
        btnHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,historico.class);
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

    }

    @Override
    public void onClick(View v) {

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
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                        }
                    }
                });
    }
    // [END auth_with_google]

    // Google sign in
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            // Google Sign In was successful, authenticate with Firebase
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                            firebaseAuthWithGoogle(account.getIdToken());
                        } catch (ApiException e) {
                            // Google Sign In failed, update UI appropriately
                            Log.w(TAG, "Google sign in failed", e);
                        }
                    }
                }
            });
}