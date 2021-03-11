package com.example.residentialshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {
    private EditText resetemail;
    private Button reset;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot);
        resetemail=findViewById(R.id.resetemail);
        reset=findViewById(R.id.reset);
        auth=FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });

    }
    public void resetpassword(){
        String emailstr=resetemail.getText().toString().trim();
        if(emailstr.isEmpty()){
            resetemail.setError("Email is required!");
            resetemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailstr).matches()){
            resetemail.setError("Please provide valid Email!");
            resetemail.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(emailstr).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Forgot.this,"Check your email to reset password!",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(Forgot.this,"Try again! Something went wrong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}