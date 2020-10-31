package com.autoaegis.autoaegisproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsuranceAdapter extends RecyclerView.Adapter<InsuranceAdapter.ViewHolder>{
    private List<InsuranceModel> insuranceModelList;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    int value=0;

    public InsuranceAdapter(List<InsuranceModel> insuranceModelList) {
        this.insuranceModelList =insuranceModelList ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout,parent,false);


        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String resource=insuranceModelList.get(position).getProduct_image();
        String title=insuranceModelList.get(position).getProduct_title();
        String price=insuranceModelList.get(position).getProduct_price();
        holder.setData(resource,title,price);
    }



    @Override
    public int getItemCount() {
        return insuranceModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView product_image;
        private TextView product_title;
        private TextView product_price;
        private Button add_to_cart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image=itemView.findViewById(R.id.image_layout);
            product_price=itemView.findViewById(R.id.price_layout);
            product_title=itemView.findViewById(R.id.title_layout);
            add_to_cart=itemView.findViewById(R.id.button_layout);

        }

        private void setData(final String resource, final String title, final String price){
            if (!resource.equals("null")) {
                Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.question_mark)).into(product_image);
            }
            product_title.setText(title.toUpperCase());
            product_price.setText("Rs. "+price+"/-");
            add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { final String product_id=firebaseFirestore.collection("USERS").document(firebaseAuth.getCurrentUser().getUid()).collection("USER_CART").document().getId();
                    Map<Object,String> product_added_id=new HashMap<>();
                    product_added_id.put("product_id","insurance_order_no"+value);
                    product_added_id.put("product_image",resource);
                    product_added_id.put("product_price",price);
                    product_added_id.put("product_title",title);
                    firebaseFirestore.collection("USERS").document(firebaseAuth.getCurrentUser().getUid()).collection("USER_CART").document("insurance_order_no"+value).set(product_added_id).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                add_to_cart.setText("ADDED");
                                value=value+1;
                                Toast.makeText(itemView.getContext(),"added to cart",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }
}
