package com.autoaegis.autoaegisproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MyAccountFragment extends Fragment {

    private TextView email;
    private TextView contact;
    private TextView address;
    private TextView user_car;
    private Button edit_car;
    private Button cart;
    private Button sign_out;
    private ImageView car_image;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my_account,container,false);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        email=view.findViewById(R.id.user_email);
        contact=view.findViewById(R.id.user_contact);
        address=view.findViewById(R.id.user_address);
        user_car=view.findViewById(R.id.car_name);
        final FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();


        ///get data from fire_store/////

        final String userId=firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("USERS").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if(documentSnapshot.exists() && documentSnapshot!=null){
                        String user_email=documentSnapshot.getString("name");
                        String user_contact=documentSnapshot.getString("contact");
                        String user_address=documentSnapshot.getString("address");
                        String user_car_name=documentSnapshot.getString("carName");

                        email.setText(user_email);
                        contact.setText(user_contact);
                        address.setText(user_address);
                        user_car.setText(user_car_name);


                    }else{
                        String error=task.getException().getMessage();
                        Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                    }
                }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                }
            }
        });

        ///get data from fire_store/////

        email=view.findViewById(R.id.user_email);
        address=view.findViewById(R.id.user_address);
        edit_car=view.findViewById(R.id.edit_car_button);
        edit_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carEdit=new Intent(getActivity(), EditDetails.class);
                startActivity(carEdit);
            }
        });
        cart=view.findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart=new Intent(getActivity(),Cart.class);
                startActivity(cart);
            }
        });

        sign_out=view.findViewById(R.id.signout_button);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               firebaseAuth.signOut();
               Intent intent=new Intent(getActivity(),LoginPage.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(intent);
            }
        });




        car_image=view.findViewById(R.id.user_car_image);

        StorageReference imageRef=firebaseStorage.getReference().child("carImages").child(firebaseAuth.getCurrentUser().getUid()+".png");
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (getActivity()==null){
                    return;
                }
                firebaseFirestore.collection("USERS").document(userId).update("carImage",uri.toString());
                Glide.with(MyAccountFragment.this).load(uri.toString()).into(car_image);
            }
        });


        return view;
    }
}
