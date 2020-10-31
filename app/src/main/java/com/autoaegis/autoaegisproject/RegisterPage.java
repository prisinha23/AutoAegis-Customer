package com.autoaegis.autoaegisproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {

    private Button register_button;
    private Button login_button;
    private ProgressBar register_progress_bar;
    private EditText register_email_id;
    private EditText register_password;
    private EditText register_confirm_password;
    private EditText register_contact;


    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        register_button=findViewById(R.id.register_me_buton);
        login_button=findViewById(R.id.register_login_button);
        register_email_id=findViewById(R.id.register_email);
        register_password=findViewById(R.id.register_password);
        register_confirm_password=findViewById(R.id.register_confirm_password);
        register_contact=findViewById(R.id.register_contact);
        register_progress_bar=findViewById(R.id.register_progress);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!(register_email_id.getText().toString().isEmpty())) && register_email_id.getText().toString().matches(emailPattern)){
                    if(!(register_password.getText().toString().isEmpty()) && register_password.getText().toString().length() >8){
                        if((register_confirm_password.getText().toString().equals(register_password.getText().toString()))){
                            if(register_contact.getText().toString().length()==10){
                                register_progress_bar.setVisibility(View.VISIBLE);

                                firebaseAuth.createUserWithEmailAndPassword(register_email_id.getText().toString(),register_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            final Map<Object,String> Registered_user=new HashMap<>();
                                            Registered_user.put("emailId",register_email_id.getText().toString());
                                            Registered_user.put("password",register_password.getText().toString());
                                            Registered_user.put("contact",register_contact.getText().toString());
                                            Registered_user.put("address","Update Your Address");
                                            Registered_user.put("name","Update Your Name");
                                            Registered_user.put("carName","Update Your Car Information");
                                            Registered_user.put("carImage","Update Your Car Image");
                                            Registered_user.put("location","no location selected");

                                            firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).set(Registered_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(RegisterPage.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                                                        Intent home_page_intent=new Intent(RegisterPage.this,SplashScreen.class);
                                                        startActivity(home_page_intent);
                                                        finish();
                                                    }else {
                                                        register_progress_bar.setVisibility(View.INVISIBLE);
                                                        String error=task.getException().getMessage();
                                                        Toast.makeText(RegisterPage.this,error,Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }else{
                                            register_progress_bar.setVisibility(View.INVISIBLE);
                                            String error=task.getException().getMessage();
                                            Toast.makeText(RegisterPage.this,error,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                register_contact.setError("Not A Valid Contact");
                            }
                        }else{
                            register_confirm_password.setError("Password Does not Match");
                        }
                    }else{
                        register_password.setError("Not a Valid Password");
                    }
                }else{
                    register_email_id.setError("Not a valid email id");
                }
            }
        });



        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent=new Intent(RegisterPage.this,LoginPage.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }

}
