package com.autoaegis.autoaegisproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartModel> cartModelList;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public CartAdapter(List<CartModel> cartModelList) {
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cart,parent,false);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String resource=cartModelList.get(position).getProduct_image();
        String title=cartModelList.get(position).getProduct_title();
        String price=cartModelList.get(position).getProduct_price();
        holder.setData(resource,title,price);
    }


    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView product_image;
        private TextView product_title;
        private TextView product_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image=itemView.findViewById(R.id.image_layout);
            product_price=itemView.findViewById(R.id.price_layout);
            product_title=itemView.findViewById(R.id.title_layout);

        }

        private void setData(String resource, String title, final String price){
            if (!resource.equals("null")) {
                Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.question_mark)).into(product_image);
            }
            product_price.setText(price);
            product_title.setText(title.toUpperCase());

        }
    }
}
