package com.samanvay.gramody.FrameJava;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.samanvay.gramody.Adapters.BusinessFrameProductsRV_Adapter;
import com.samanvay.gramody.DetailedProduct;
import com.samanvay.gramody.Dialog.LoadingDialog;
import com.samanvay.gramody.Interface.JsonInterface;
import com.samanvay.gramody.Model.highestAvgPriceVarietyGrade;
import com.samanvay.gramody.Model.JsonResponse;
import com.samanvay.gramody.Model.business;
import com.samanvay.gramody.Model.posts;
import com.samanvay.gramody.Model.products;
import com.samanvay.gramody.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BusinessJava extends Fragment {
    TextView userOption,userShopNumber,userFirmName,userMarketName;
    RecyclerView productsRV;
    List<products> productsList;
    BusinessFrameProductsRV_Adapter productsRVAdapter;
    List<posts> postsList;
    int adapterPosition;
    LoadingDialog loadingDialog;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.buisness_frame_layout,container,false);
        userOption=view.findViewById(R.id.UserOption_BusinessFragment);
        userFirmName=view.findViewById(R.id.userFirmName_BusinessFragment);
        userMarketName=view.findViewById(R.id.userMarketName_BusinessFragment);
        userShopNumber=view.findViewById(R.id.userShopNumber_BusinessFragment);
        productsRV=view.findViewById(R.id.Business_products_RV_Business_Fragment);
        loadingDialog=new LoadingDialog(requireActivity());
        loadingDialog.startLoadingDialog();

        Retrofit jsonRetrofit=new Retrofit.Builder()
                .baseUrl("https://api.gramoday.net:8082/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonInterface jsonInterface=jsonRetrofit.create(JsonInterface.class);
        Call<JsonResponse> jsonResponseCall=jsonInterface.getData();
        jsonResponseCall.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if(!response.isSuccessful()){
                    return;
                }
                Log.d("call","jason call is successful");
                JsonResponse jsonResponse=response.body();
                assert jsonResponse != null;
                business businessModel=jsonResponse.getBusiness();
                if(businessModel!=null) {
                    userOption.setText(businessModel.getUserOption());
                    userFirmName.setText(businessModel.getFirmName());
                    userMarketName.setText(businessModel.getMarketStdName());
                    userShopNumber.setText(businessModel.getMandiShopnum());

                    productsList=new ArrayList<>(Arrays.asList(jsonResponse.getProducts()));
                    postsList=new ArrayList<>(Arrays.asList(productsList.get(0).getPosts()));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (productsList!=null){
                                fillProductsRV();
                            }
                        }
                    }, 1000);


                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.d("response", "onResponse: failed "+t.getMessage());
            }
        });

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fillProductsRV() {
        productsRVAdapter=new BusinessFrameProductsRV_Adapter(requireActivity().getApplicationContext(),productsList);
        productsRV.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        productsRV.setAdapter(productsRVAdapter);
        productsRVAdapter.notifyDataSetChanged();
        loadingDialog.dismissDialog();
        productsRVAdapter.setOnItemClickListener(new BusinessFrameProductsRV_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                adapterPosition=position;
                SaveSharedPreferences();
                startActivity(new Intent(getActivity(), DetailedProduct.class));
            }
        });


    }
    private void SaveSharedPreferences() {
        SharedPreferences sharedPreferences=requireActivity().getSharedPreferences("Shared Preferences",MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(postsList);
        String json2=gson.toJson(productsList);
        editor.putString("postsList",json);
        editor.putString("productsList",json2);
        editor.putInt("positionProduct",adapterPosition);
        editor.apply();

    }
}
