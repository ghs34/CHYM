package com.example.chymv2.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chymv2.R;
import com.example.chymv2.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivitySignUp extends AppCompatActivity {
    private Boolean existEmail = false;
    private Boolean existUsername = false;
    private EditText signUpEmail, signUpPassword, signUpUsername, signUpRepassword;
    private TextView loginRedirectText;
    private Button signUpButton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpUsername = findViewById(R.id.singUpUsername);
        signUpEmail = findViewById(R.id.singUpEmail);
        signUpPassword = findViewById(R.id.singUpPassword);
        signUpButton = findViewById(R.id.signInRedirectBtn);
        loginRedirectText = findViewById(R.id.signInRedirectText);
        signUpRepassword = findViewById(R.id.signUpRepassword);

        firebaseAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //database = FirebaseDatabase.getInstance();
                //reference = database.getReference("users");

                //String name = signUpName.getText().toString();
                //String email = signUpEmail.getText().toString();
                String password = signUpPassword.getText().toString();
                //String username = signUpUsername.getText().toString();
                String userEmail = signUpEmail.getText().toString().trim();
                String repassword = signUpRepassword.getText().toString();
                //String userUsername = signUpUsername.getText().toString().trim();
                //createUser(userUsername,userEmail,username,name,email,password,reference);

                if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                    signUpEmail.setError("Inavlid email");
                    signUpEmail.setFocusable(true);
                }
                else if(password.length()<6){
                    signUpPassword.setError("Min lenght is 6 characters");
                    signUpPassword.setFocusable(true);
                } else if (!password.equals(repassword)) {
                    signUpRepassword.setError("The passwords doesn't match");
                    signUpPassword.setError("The passwords doesn't match");
                    signUpPassword.setFocusable(true);
                    signUpRepassword.setFocusable(true);
                } else{
                    REGISTRAR(userEmail,password);
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySignUp.this, ActivitySignIn.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void REGISTRAR(String userEmail, String password) {

        firebaseAuth.createUserWithEmailAndPassword(userEmail,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    assert user != null;
                    String email = signUpEmail.getText().toString();
                    String username = signUpUsername.getText().toString();

                    Usuario usuario = new Usuario(username,email);

                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");
                    reference.child(user.getUid()).setValue(usuario);

                    startActivity(new Intent(ActivitySignUp.this, ActivitySignIn.class));
                    Toast.makeText(ActivitySignUp.this, "Bienvenido "+user.getEmail(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(ActivitySignUp.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(ActivitySignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}