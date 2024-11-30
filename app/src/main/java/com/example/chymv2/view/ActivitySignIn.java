package com.example.chymv2.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chymv2.R;
import com.example.chymv2.model.Usuario;
import com.example.chymv2.sources.InitializeData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class ActivitySignIn extends AppCompatActivity {

    private EditText signInUsername, signInPassword;
    private Button signInBtn, signUpRedirectBtn, googleRedirectBtn;
    //private ProgressDialog progressDialog;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signInUsername = findViewById(R.id.signInEmail);
        signInPassword = findViewById(R.id.signInPassword);
        signInBtn = findViewById(R.id.loginBtn);
        signUpRedirectBtn = findViewById(R.id.signUpRedirectBtn);
        googleRedirectBtn = findViewById(R.id.googleRedirectBtn);

        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signInUsername.getText().toString();
                String password = signInPassword.getText().toString();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    signInUsername.setError("Inavlid email");
                    signInUsername.setFocusable(true);
                }
                else if(password.length()<6){
                    signInPassword.setError("Min lenght is 6 characters");
                    signInPassword.setFocusable(true);
                }
                else{
                    LOGINUSUARIO(email,password);
                }
            }
        });

        signUpRedirectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySignIn.this, ActivitySignUp.class);
                startActivity(intent);
                finish();
            }
        });

        googleRedirectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));
            }
        });
    }

    private void LOGINUSUARIO(String email, String password) {
        //progressDialog.setCancelable(false);
        //progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //progressDialog.dismiss();
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    startActivity(new Intent(ActivitySignIn.this, ActivityMain.class).putExtra("parametro",1));
                    assert user != null;
                    Toast.makeText(ActivitySignIn.this, "Bienvenido "+user.getEmail(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    //progressDialog.dismiss();
                    //Toast.makeText(SignInActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //progressDialog.dismiss();
                Toast.makeText(ActivitySignIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK){
                Intent intent = result.getData();
                Task<GoogleSignInAccount> task  = GoogleSignIn.getSignedInAccountFromIntent(intent);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);

                    assert account != null;
                    AutentificadorFirebase(account.getIdToken());
                }
                catch(ApiException e){
                    //Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    });

    private void AutentificadorFirebase(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(credential);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    if (task.getResult().getAdditionalUserInfo().isNewUser()){
                        String userID = user.getUid();
                        String username = "username chym";
                        String correo = user.getEmail();
                        Usuario usuario = new Usuario(username,correo);
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference reference = firebaseDatabase.getReference("users");
                        reference.child(userID).setValue(usuario);
                    }
                    startActivity(new Intent(ActivitySignIn.this, ActivityMain.class).putExtra("parametro",1));
                    finish();
                }
                else{
                }
            }
        });
    }

}