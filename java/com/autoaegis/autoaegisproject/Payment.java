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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Payment extends AppCompatActivity {
    private TextView name;
    private TextView address;
    private ImageView edit;
    private EditText edit_address;
    private TextView price;
    private ImageView gpay;
    private ImageView paytm;
    private LinearLayout edit_main;
    private ImageView change_address;
    private Button cancel;
    private ProgressBar progressBar;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public static String payerName,payerAddress,payerAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name=findViewById(R.id.payment_name);
        address=findViewById(R.id.payment_address);
        edit=findViewById(R.id.payment_edit_button);
        edit_address=findViewById(R.id.payment_edit_text);
        price=findViewById(R.id.payment_price);
        gpay=findViewById(R.id.payment_button_gpay);
        paytm=findViewById(R.id.payment_button_paytm);
        edit_main=findViewById(R.id.payment_edit_linear);
        change_address=findViewById(R.id.ok_address);
        cancel=findViewById(R.id.payment_cancel);
        progressBar=findViewById(R.id.payment_progress);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_main.setVisibility(View.VISIBLE);
            }
        });

        final String userId=firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("USERS").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if(documentSnapshot.exists() && documentSnapshot!=null){
                        String user_name=documentSnapshot.getString("name");
                        String user_address=documentSnapshot.getString("address");

                        name.setText(user_name);
                        address.setText(user_address);


                    }else{
                        String error=task.getException().getMessage();
                        Toast.makeText(Payment.this,error,Toast.LENGTH_SHORT).show();
                    }
                }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(Payment.this,error,Toast.LENGTH_SHORT).show();
                }
            }
        });


        change_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edit_address.getText().toString().isEmpty()){
                    firebaseFirestore.collection("USERS").document(userId).update("address",edit_address.getText().toString());
                    startActivity(new Intent(Payment.this,Payment.class));
                    Toast.makeText(Payment.this,"Your Address has been Updated",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Payment.this,"Please Fill this column first",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Intent intent=getIntent();
        String total_price=intent.getStringExtra("total_amount");
        price.setText(total_price);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancel=new Intent(Payment.this,HomePage.class);
                startActivity(cancel);
                finish();
            }
        });

        payerName=name.getText().toString();
        payerAddress=address.getText().toString();
        payerAmount=price.getText().toString();
        gpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payerName=name.getText().toString();
                payerAddress=address.getText().toString();
                payerAmount=price.getText().toString();
                cancel.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(Payment.this,"PAYMENT IN PROGRESS",Toast.LENGTH_SHORT).show();
            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancel.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(Payment.this,"PAYMENT IN PROGRESS",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
