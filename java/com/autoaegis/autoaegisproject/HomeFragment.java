package com.autoaegis.autoaegisproject;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HomeFragment extends Fragment {
    private ImageView image1,image2,image3,image4,image5;
    private ImageView offer_image1,offer_image2,offer_image3;


    private ImageView img1,img2,img3,img4,img5,img6;
    private TextView title1,title2,title3,title4,title5,title6;

    private ImageView mechanic,tyre,petrol,insurance;


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home,container,false);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        image1=view.findViewById(R.id.deal_image1);
        image2=view.findViewById(R.id.deal_image2);
        image3=view.findViewById(R.id.deal_image3);
        image4=view.findViewById(R.id.deal_image4);
        image5=view.findViewById(R.id.deal_image5);


        StorageReference reference1=firebaseStorage.getReference().child("sliderImage").child("dealSlider").child("image1.jpg");
        reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri1) {
                if (getActivity()==null){
                    return;
                }
               firebaseFirestore.collection("Slider").document("Deals").update("image1",uri1.toString());
               Glide.with(getActivity()).load(uri1.toString()).into(image1);
            }
        });
        StorageReference reference2=firebaseStorage.getReference().child("sliderImage").child("dealSlider").child("image2.jpg");
        reference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri2) {
                if (getActivity()==null){
                    return;
                }
                firebaseFirestore.collection("Slider").document("Deals").update("image2",uri2.toString());
                Glide.with(getActivity()).load(uri2.toString()).into(image2);
            }
        });
        StorageReference reference3=firebaseStorage.getReference().child("sliderImage").child("dealSlider").child("image3.jpg");
        reference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri3) {
                if (getActivity()==null){
                    return;
                }
                firebaseFirestore.collection("Slider").document("Deals").update("image3",uri3.toString());
                Glide.with(getActivity()).load(uri3.toString()).into(image3);
            }
        });
        StorageReference reference4=firebaseStorage.getReference().child("sliderImage").child("dealSlider").child("image4.jpg");
        reference4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri4) {
                if (getActivity()==null){
                    return;
                }
                firebaseFirestore.collection("Slider").document("Deals").update("image4",uri4.toString());
                Glide.with(getActivity()).load(uri4.toString()).into(image4);
            }
        });
        StorageReference reference5=firebaseStorage.getReference().child("sliderImage").child("dealSlider").child("image5.jpg");
        reference5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri5) {
                if (getActivity()==null){
                    return;
                }
                firebaseFirestore.collection("Slider").document("Deals").update("image5",uri5.toString());
                Glide.with(getActivity()).load(uri5.toString()).into(image5);
            }
        });


        img1=view.findViewById(R.id.location_image1);
        img2=view.findViewById(R.id.location_image2);
        img3=view.findViewById(R.id.location_image3);
        img4=view.findViewById(R.id.location_image4);
        img5=view.findViewById(R.id.location_image5);
        img6=view.findViewById(R.id.location_image6);

        title1=view.findViewById(R.id.location_title1);
        title2=view.findViewById(R.id.location_title2);
        title3=view.findViewById(R.id.location_title3);
        title4=view.findViewById(R.id.location_title4);
        title5=view.findViewById(R.id.location_title5);
        title6=view.findViewById(R.id.location_title6);

        ////////////////LOCATION CODE 1

        StorageReference location=firebaseStorage.getReference().child("sliderImage").child("location").child("image1.jpg");
        location.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri1) {
                firebaseFirestore.collection("Slider").document("location").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        if(documentSnapshot.exists() && documentSnapshot!=null) {
                            if (getActivity()==null){
                                return;
                            }
                            firebaseFirestore.collection("Slider").document("location").update("image1",uri1.toString());
                            String location1 = documentSnapshot.getString("title1");
                            Glide.with(getActivity()).load(uri1.toString()).into(img1);
                            title1.setText(location1);
                        }
                    }
                });
            }
        });

        ////////////////LOCATION CODE 1

        ////////////////LOCATION CODE 2

        StorageReference location2=firebaseStorage.getReference().child("sliderImage").child("location").child("image2.jpg");
        location2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri2) {
                firebaseFirestore.collection("Slider").document("location").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        if(documentSnapshot.exists() && documentSnapshot!=null) {
                            if (getActivity()==null){
                                return;
                            }
                            firebaseFirestore.collection("Slider").document("location").update("image2",uri2.toString());
                            String location1 = documentSnapshot.getString("title2");
                            Glide.with(getActivity()).load(uri2.toString()).into(img2);
                            title2.setText(location1);
                        }
                    }
                });
            }
        });

        ////////////////LOCATION CODE 2


        ////////////////LOCATION CODE 3

        StorageReference location3=firebaseStorage.getReference().child("sliderImage").child("location").child("image3.jpg");
        location3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri3) {
                firebaseFirestore.collection("Slider").document("location").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        if(documentSnapshot.exists() && documentSnapshot!=null) {
                            if (getActivity()==null){
                                return;
                            }
                            firebaseFirestore.collection("Slider").document("location").update("image3",uri3.toString());
                            String location1 = documentSnapshot.getString("title3");
                            Glide.with(getActivity()).load(uri3.toString()).into(img3);
                            title3.setText(location1);
                        }
                    }
                });
            }
        });

        ////////////////LOCATION CODE 3
        ////////////////LOCATION CODE 4

        StorageReference location4=firebaseStorage.getReference().child("sliderImage").child("location").child("image4.jpg");
        location4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri4) {
                firebaseFirestore.collection("Slider").document("location").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        if(documentSnapshot.exists() && documentSnapshot!=null) {
                            if (getActivity()==null){
                                return;
                            }
                            firebaseFirestore.collection("Slider").document("location").update("image4",uri4.toString());
                            String location1 = documentSnapshot.getString("title4");
                            Glide.with(getActivity()).load(uri4.toString()).into(img4);
                            title4.setText(location1);
                        }
                    }
                });
            }
        });

        ////////////////LOCATION CODE 4


        ////////////////LOCATION CODE 5

        StorageReference location5=firebaseStorage.getReference().child("sliderImage").child("location").child("image5.jpg");
        location5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri5) {
                firebaseFirestore.collection("Slider").document("location").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        if(documentSnapshot.exists() && documentSnapshot!=null) {
                            if (getActivity()==null){
                                return;
                            }
                            firebaseFirestore.collection("Slider").document("location").update("image5",uri5.toString());
                            String location1 = documentSnapshot.getString("title5");
                            Glide.with(getActivity()).load(uri5.toString()).into(img5);
                            title5.setText(location1);
                        }
                    }
                });
            }
        });

        ////////////////LOCATION CODE 5

        ////////////////LOCATION CODE 6

        StorageReference location6=firebaseStorage.getReference().child("sliderImage").child("location").child("image6.jpg");
        location6.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri6) {
                firebaseFirestore.collection("Slider").document("location").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        if(documentSnapshot.exists() && documentSnapshot!=null) {
                            if (getActivity()==null){
                                return;
                            }
                            firebaseFirestore.collection("Slider").document("location").update("image6",uri6.toString());
                            String location1 = documentSnapshot.getString("title6");
                            title6.setText(location1);
                            Glide.with(getActivity()).load(uri6.toString()).into(img6);
                        }
                    }
                });
            }
        });

        ////////////////LOCATION CODE 6




        offer_image1=view.findViewById(R.id.offer_image1);
        offer_image2=view.findViewById(R.id.offer_image2);
        offer_image3=view.findViewById(R.id.offer_image3);


        /////offer slider column////

        StorageReference offer1=firebaseStorage.getReference().child("sliderImage").child("offers").child("image1.jpg");
        offer1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri1) {
                if (getActivity()==null){
                    return;
                }
                firebaseFirestore.collection("Slider").document("offers").update("image1",uri1.toString());
                Glide.with(getActivity()).load(uri1.toString()).into(offer_image1);
            }
        });
        StorageReference offer2=firebaseStorage.getReference().child("sliderImage").child("offers").child("image2.jpg");
        offer2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri2) {
                if (getActivity()==null){
                    return;
                }
                firebaseFirestore.collection("Slider").document("offers").update("image2",uri2.toString());
                Glide.with(getActivity()).load(uri2.toString()).into(offer_image2);
            }
        });
        StorageReference offer3=firebaseStorage.getReference().child("sliderImage").child("offers").child("image3.jpg");
        offer3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri3) {
                if (getActivity()==null){
                    return;
                }
                firebaseFirestore.collection("Slider").document("offers").update("image3",uri3.toString());
                Glide.with(getActivity()).load(uri3.toString()).into(offer_image3);
            }
        });
        /////offer slider column////


        ////services column/////

        mechanic=view.findViewById(R.id.mechanic_fragment);
        mechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.dialouge_mechanic);
                dialog.show();
            }
        });
        tyre=view.findViewById(R.id.tyre_fragment);
        tyre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.dialouge_tyre);
                dialog.show();
            }
        });
        insurance=view.findViewById(R.id.insurance_fragment);
        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.dialouge_insurance);
                dialog.show();
            }
        });

        petrol=view.findViewById(R.id.petrol_fragment);
        petrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.dialouge_petrol);
                dialog.show();
            }
        });

        ////services column/////

        return view;
    }

}
