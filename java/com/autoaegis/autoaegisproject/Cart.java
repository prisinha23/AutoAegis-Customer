package com.autoaegis.autoaegisproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cart extends AppCompatActivity {

    private RecyclerView cartRecyclerView;

    private List<CartModel>cartModelList;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private Button total_price;
    private ImageView remove;
    private Button pay_out;
    private TextView clear_cart;

    int sum=0;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        cartRecyclerView=findViewById(R.id.cart_list);
        total_price=findViewById(R.id.cart_total_amount);
        remove=findViewById(R.id.clear_cart);
        pay_out=findViewById(R.id.cart_payout);
        clear_cart=findViewById(R.id.clear_cart_text);

        firebaseAuth=FirebaseAuth.getInstance();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Cart.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartRecyclerView.setLayoutManager(linearLayoutManager);

        cartModelList=new ArrayList<CartModel>();


        final CartAdapter cartAdapter=new CartAdapter(cartModelList);
        cartRecyclerView.setAdapter(cartAdapter);

        firebaseFirestore=FirebaseFirestore.getInstance();


        firebaseFirestore.collection("USERS").document(firebaseAuth.getCurrentUser().getUid()).collection("USER_CART").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        cartModelList.add(new CartModel(documentSnapshot.get("product_image").toString(),documentSnapshot.get("product_title").toString(), (String) documentSnapshot.get("product_price")));
                    }
                    cartAdapter.notifyDataSetChanged();
                }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(Cart.this,error,Toast.LENGTH_SHORT).show();
                }
            }
        });

        firebaseFirestore.collection("USERS").document(firebaseAuth.getCurrentUser().getUid()).collection("USER_CART").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

              for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                  Map<String,Object>map=(Map<String, Object>)documentSnapshot.getData();
                  Object price=map.get("product_price");
                  int pvalue=Integer.parseInt(String.valueOf(price));
                  sum +=pvalue;

                  total_price.setText("Rs "+Integer.toString(sum)+"/-");
              }
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("USERS").document(firebaseAuth.getCurrentUser().getUid()).collection("USER_CART").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            remove.setVisibility(View.GONE);
                            clear_cart.setText("CART IS EMPTY");
                            for (QueryDocumentSnapshot documentSnapshot :task.getResult()){
                                Map<String,Object> map=(Map<String, Object>)documentSnapshot.getData();
                                String uid= (String) map.get("product_id");
                                firebaseFirestore.collection("USERS").document(firebaseAuth.getCurrentUser().getUid()).collection("USER_CART").document(uid).delete();
                                Toast.makeText(Cart.this,"Removed all From Cart",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
        pay_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent payment=new Intent(Cart.this,Payment.class);
                payment.putExtra("total_amount",Integer.toString(sum));
                startActivity(payment);
                finish();
            }
        });
    }
}
