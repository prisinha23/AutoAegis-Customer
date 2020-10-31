package com.autoaegis.autoaegisproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class EditDetails extends AppCompatActivity {

    private ImageView close_image;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private EditText name_details;
    private Button name_details_button;

    private EditText contact_details;
    private Button contact_details_button;

    private EditText address_details;
    private Button address_details_button;

    private EditText car_details;
    private TextView upload_text;
    private Button car_details_button;
    private ImageView car_image;
    private int TAKE_IMAGE_CODE=1001;
    private ProgressBar uploading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        final String user_id=firebaseAuth.getCurrentUser().getUid();


        name_details=findViewById(R.id.edit_name);
        name_details_button=findViewById(R.id.update_name);
        name_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(name_details.getText().toString().isEmpty())){
                    firebaseFirestore.collection("USERS").document(user_id).update("name",name_details.getText().toString());
                    name_details_button.setText("Success");
                    name_details_button.setEnabled(false);
                    name_details_button.setBackgroundColor(getResources().getColor(R.color.royal));
                }else {
                    name_details_button.setEnabled(true);
                    name_details.setError("Fill this Field Please");
                }
            }
        });



        contact_details=findViewById(R.id.edit_contact);
        contact_details_button=findViewById(R.id.update_contact);
        contact_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(contact_details.getText().toString().isEmpty()) && contact_details.getText().toString().length()==10){
                    firebaseFirestore.collection("USERS").document(user_id).update("contact",contact_details.getText().toString());
                    contact_details_button.setText("Success");
                    contact_details_button.setEnabled(false);
                    contact_details_button.setBackgroundColor(getResources().getColor(R.color.royal));
                }else{
                    contact_details_button.setEnabled(true);
                    contact_details.setError("Fill this Field Please");
                }
            }
        });

        address_details=findViewById(R.id.edit_address);
        address_details_button=findViewById(R.id.update_address);
        address_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(address_details.getText().toString().isEmpty())){
                    firebaseFirestore.collection("USERS").document(user_id).update("address",address_details.getText().toString());
                    address_details_button.setText("Success");
                    address_details_button.setEnabled(false);
                    address_details_button.setBackgroundColor(getResources().getColor(R.color.royal));
                }else {
                    address_details_button.setEnabled(true);
                    address_details.setError("Fill this Field Please");
                }
            }
        });



        uploading=findViewById(R.id.upload_image);
        upload_text=findViewById(R.id.upoad_text);
        car_details=findViewById(R.id.edit_car_name);
        car_details_button=findViewById(R.id.update_car_name);
        car_image=findViewById(R.id.car_image);
        car_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (camera.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(camera,TAKE_IMAGE_CODE);
                }
            }
        });

        car_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(car_details.getText().toString().isEmpty())){
                    firebaseFirestore.collection("USERS").document(user_id).update("carName",car_details.getText().toString());
                    car_details_button.setText("Success");
                    car_details_button.setEnabled(false);
                    car_details_button.setBackgroundColor(getResources().getColor(R.color.royal));
                }else {
                    car_details_button.setEnabled(true);
                    car_details.setError("Fill this Field Please");
                }
            }
        });


        close_image=findViewById(R.id.close_edit);
        close_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homepage=new Intent(EditDetails.this,HomePage.class);
                startActivity(homepage);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==TAKE_IMAGE_CODE){
            switch (resultCode){
                case RESULT_OK:
                    Bitmap bitmap=(Bitmap)data.getExtras().get("data");
                    car_image.setImageBitmap(bitmap);
                    handleUpload(bitmap);
            }
        }
    }
    private void handleUpload(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);

        firebaseAuth=FirebaseAuth.getInstance();
        String uid=firebaseAuth.getCurrentUser().getUid();
        uploading.setVisibility(View.VISIBLE);
        uploading.setProgress(3000);
        StorageReference reference= FirebaseStorage.getInstance().getReference().child("carImages").child(uid+".png");
        reference.putBytes(byteArrayOutputStream.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploading.setVisibility(View.INVISIBLE);
                upload_text.setText("Uploaded Successfully");
                upload_text.setTextColor(Color.RED);
                upload_text.setTextSize(18);
                Toast.makeText(EditDetails.this,"uploading Complete",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //
    }
}
