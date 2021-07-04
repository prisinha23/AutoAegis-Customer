package com.autoaegis.autoaegisproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    private Button register_Button;
    private Button log_me_in;
    private EditText email_id;
    private EditText password;
    private ProgressBar loginProgressBar;
    private TextView forgotPassword;
    private TextView sentMail;
    private ImageView sentMailPhoto;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        log_me_in=findViewById(R.id.login_button);
        email_id=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        loginProgressBar=findViewById(R.id.login_progress_bar);
        forgotPassword=findViewById(R.id.forgot_password);
        sentMail=findViewById(R.id.forgot_password_text);
        sentMailPhoto=findViewById(R.id.forgot_password_mail_icon);

        firebaseAuth=FirebaseAuth.getInstance();

        log_me_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(email_id.getText().toString().isEmpty())){
                    if(!(password.getText().toString().isEmpty())){
                        loginProgressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.signInWithEmailAndPassword(email_id.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginPage.this,"Logged In Successfully",Toast.LENGTH_SHORT).show();
                                    Intent enter=new Intent(LoginPage.this,SplashScreen.class);
                                    startActivity(enter);
                                    finish();
                                }else{
                                    loginProgressBar.setVisibility(View.INVISIBLE);
                                    String error=task.getException().getMessage();
                                    Toast.makeText(LoginPage.this,error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else {
                        password.setError("Not a Valid Password");
                    }
                }else{
                    email_id.setError("invalid Email Entered");
                }

            }
        });



        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email_id.getText().toString().isEmpty()){
                    email_id.setError("Please enter the Registered Email id");
                }else{
                    loginProgressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.sendPasswordResetEmail(email_id.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                sentMail.setVisibility(View.VISIBLE);
                                sentMailPhoto.setVisibility(View.VISIBLE);
                                loginProgressBar.setVisibility(View.INVISIBLE);
                            }else {
                                loginProgressBar.setVisibility(View.INVISIBLE);
                                sentMailPhoto.setVisibility(View.INVISIBLE);
                                sentMail.setVisibility(View.INVISIBLE);
                                String error=task.getException().getMessage();
                                Toast.makeText(LoginPage.this,error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    sentMail.setVisibility(View.INVISIBLE);
                    sentMailPhoto.setVisibility(View.INVISIBLE);
                    forgotPassword.setEnabled(false);
                }
            }
        });


        register_Button=findViewById(R.id.login_register_button);
        register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginPage.this,RegisterPage.class);
                startActivity(intent);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
       finish();
    }
}
