package com.samanvay.gramody.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.samanvay.gramody.Model.posts;
import com.samanvay.gramody.Model.products;
import com.samanvay.gramody.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BusinessFrameProductsRV_Adapter extends RecyclerView.Adapter<BusinessFrameProductsRV_Adapter.VH> {
    Context context;
    List<products> productsList;
    List<posts> postsList=new ArrayList<>();
    private static BusinessFrameProductsRV_Adapter.OnItemClickListener listener;





    public BusinessFrameProductsRV_Adapter(Context context, List<products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_card_design,parent,false);

        return new VH(view);
    }

    @SuppressLint({"SetTextI18n", "PrivateResource"})
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        Glide.with(holder.pImage.getContext()).load("https://gramoday-images-public.s3.ap-south-1.amazonaws.com/commodity/"+productsList.get(position).getPicUrl()).into(holder.pImage);
        postsList= Arrays.asList(productsList.get(position).getPosts());
        holder.pName.setText(postsList.get(0).getCmdtyStdName()+" "+postsList.get(0).getMarketStdName());
        holder.pAddress.setText(postsList.get(0).getMarketStdName()+", "+postsList.get(0).getLoclevel3Name()+", "+postsList.get(0).getLoclevel2ShortName()+"\n "
                +postsList.get(0).getCreatedAt().substring(0,10).replace('-','/'));
        holder.pPrice.setText("\u20B9"+postsList.get(0).getComputed().getHighestAvgPriceVarietyGrade().getMinPrice()+"-"+postsList.get(0).getComputed().getHighestAvgPriceVarietyGrade().getMaxPrice()+"/1"+postsList.get(0).getRawReportPriceUnit());

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.photo_anim));
//        holder.pUpdate.setText(.getGradName());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView pName,pAddress,pPrice,pUpdate;
        ImageView pImage;
        Button pShare,pInterested;
        CardView cardView;
        public VH(@NonNull View itemView) {
            super(itemView);

            pName=itemView.findViewById(R.id.textView_Name_product_RV);
            pAddress=itemView.findViewById(R.id.textview_Address_product_RV);
            pPrice=itemView.findViewById(R.id.textView_price__product_RV);
            pUpdate=itemView.findViewById(R.id.textview_update_product_RV);
            pImage=itemView.findViewById(R.id.imageView_product_RV);
            pShare=itemView.findViewById(R.id.ProductShare_Button_ProductRV);
            pInterested=itemView.findViewById(R.id.ProductInterested_Button_ProductRV);
            cardView=itemView.findViewById(R.id.cardView_Rpduct_card_design);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION&& listener!=null){
                        listener.onItemClick(position);

                    }
                }
            });



        }
    }
    public interface OnItemClickListener{
        void onItemClick( int position);
    }
    public void setOnItemClickListener(BusinessFrameProductsRV_Adapter.OnItemClickListener listener){
        BusinessFrameProductsRV_Adapter.listener=listener;
    }


}
