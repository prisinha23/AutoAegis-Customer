package com.autoaegis.autoaegisproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TyreFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private RecyclerView tyreRecyclerView;

    private List<TyreModel>tyreModelList;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private ImageView my_cart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_tyre,container,false);

        firebaseAuth=FirebaseAuth.getInstance();
        tyreRecyclerView=view.findViewById(R.id.tyre_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tyreRecyclerView.setLayoutManager(linearLayoutManager);

       tyreModelList=new ArrayList<TyreModel>();

        final TyreAdapter tyreAdapter=new TyreAdapter(tyreModelList);
        tyreRecyclerView.setAdapter(tyreAdapter);

        firebaseFirestore=FirebaseFirestore.getInstance();

        firebaseFirestore.collection("TyreServices").orderBy("product_price", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        tyreModelList.add(new TyreModel(documentSnapshot.get("product_image").toString(),documentSnapshot.get("product_title").toString(),documentSnapshot.get("product_price").toString()));
                    }
                    tyreAdapter.notifyDataSetChanged();
                }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                }
            }
        });

        Spinner spinner=view.findViewById(R.id.location_select);
        ArrayAdapter<CharSequence> locationadapter=ArrayAdapter.createFromResource(getContext(),R.array.locations,android.R.layout.simple_spinner_item);
        locationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(locationadapter);
        spinner.setOnItemSelectedListener(this);

        my_cart=view.findViewById(R.id.my_cart);
        my_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart=new Intent(getActivity(),Cart.class);
                startActivity(cart);
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String location=adapterView.getItemAtPosition(i).toString();
        if (!location.equals("select your location")) {
            firebaseFirestore.collection("USERS").document(firebaseAuth.getCurrentUser().getUid()).update("location", location.toUpperCase());
            Toast.makeText(getContext(), "Your location has been set to:" + location, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
