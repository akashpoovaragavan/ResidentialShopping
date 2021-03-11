package com.example.residentialshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterUser extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView registertxt;
    private EditText username,emailregister,passwordregister;
    private Button registerbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_user);
        mAuth=FirebaseAuth.getInstance();
        registertxt=findViewById(R.id.registertxt);
        username=findViewById(R.id.username);
        emailregister=findViewById(R.id.emailregister);
        passwordregister=findViewById(R.id.passwordregister);
        registerbutton=findViewById(R.id.registerbutton);
        Intent intentlogin=new Intent(this,Login.class);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registeruser();
            }

            private void registeruser() {
                String name=username.getText().toString().trim();
                String email=emailregister.getText().toString().trim();
                String password=passwordregister.getText().toString().trim();
                if(name.isEmpty()){
                    username.setError("Name is required!..");
                    username.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    emailregister.setError("Email id is required!..");
                    emailregister.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailregister.setError("Please provide valid email id!...");
                    emailregister.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    passwordregister.setError("Password is required!..");
                    passwordregister.requestFocus();
                    return;
                }
                if(password.length()<8){
                    passwordregister.setError("Min Password length is 8 characters!... ");
                    passwordregister.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user=new User(name,email);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this,"User has been registered!..",Toast.LENGTH_LONG).show();
                                                startActivity(intentlogin);
                                            }
                                            else{
                                                Toast.makeText(RegisterUser.this,"Failed to register! Try again!..",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(RegisterUser.this,"Failed to register! Try again!..",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
}