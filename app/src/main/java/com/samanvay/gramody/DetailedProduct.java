package com.samanvay.gramody;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samanvay.gramody.Dialog.LoadingDialog;
import com.samanvay.gramody.Model.posts;
import com.samanvay.gramody.Model.products;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailedProduct extends AppCompatActivity {
    List<products> productsList;
    List<posts> postsList;
    int adapterPosition;
    ImageView PImage;
    TextView Name,Address,Price;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_product);


        PImage=findViewById(R.id.imageView_DetailedProduct);
        Name=findViewById(R.id.textView_Name_DetailedProduct);
        Address=findViewById(R.id.textview_Address_DetailedProduct);
        Price=findViewById(R.id.textView_price_DetailedProduct);

        LoadSharedPreferences();
        if (postsList!=null&& productsList!=null){
            Glide.with(PImage.getContext()).load("https://gramoday-images-public.s3.ap-south-1.amazonaws.com/commodity/"+productsList.get(adapterPosition).getPicUrl()).into(PImage);
            Name.setText(postsList.get(0).getCmdtyStdName()+" "+postsList.get(0).getMarketStdName());
            Address.setText(postsList.get(0).getMarketStdName()+", "+postsList.get(0).getLoclevel3Name()+", "+postsList.get(0).getLoclevel2ShortName()+postsList.get(0).getCreatedAt().substring(0,10).replace('-','/'));
            Price.setText("\u20B9"+postsList.get(0).getComputed().getHighestAvgPriceVarietyGrade().getMinPrice()+"-"+postsList.get(0).getComputed().getHighestAvgPriceVarietyGrade().getMaxPrice()+"/1"+postsList.get(0).getRawReportPriceUnit());

        }

    }
    private void LoadSharedPreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences("Shared Preferences",MODE_PRIVATE);
        Gson gson=new Gson();
        String jsonString=sharedPreferences.getString("postsList","");
        String jsonString2=sharedPreferences.getString("productsList","");
        adapterPosition=sharedPreferences.getInt("positionProduct",0);
        Type type2=new TypeToken<ArrayList<products>>() {}.getType();
        Type type=new TypeToken<ArrayList<posts>>() {}.getType();

        postsList=gson.fromJson(jsonString,type);
        productsList=gson.fromJson(jsonString2,type2);
    }
}