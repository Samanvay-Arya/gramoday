package com.samanvay.gramody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.samanvay.gramody.Dialog.LoadingDialog;
import com.samanvay.gramody.FrameJava.BusinessJava;
import com.samanvay.gramody.FrameJava.ReviewJava;
import com.samanvay.gramody.Interface.JsonInterface;
import com.samanvay.gramody.Model.JsonResponse;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Arrays;

import io.requestly.rqinterceptor.api.RQCollector;
import io.requestly.rqinterceptor.api.RQInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ImageView back,userPhoto;
    TextView userName,userAddress,userLanguage,userBusiness,userReview;
    FloatingActionButton share;
    Button connect,contact;
    View VReview,VBusiness;
    FrameLayout frameLayout;
    Fragment reviewFragment=new ReviewJava();
    Fragment businessFragment=new BusinessJava();
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // view initialization
         back=findViewById(R.id.imageView_Back_MainActivity);
         userPhoto=findViewById(R.id.UserPhoto_MainActivity);
         userName=findViewById(R.id.UserName_MainActivity);
         userAddress=findViewById(R.id.UserAddress_MainActivity);
         userLanguage=findViewById(R.id.UserLanguage_MainActivity);
         userBusiness=findViewById(R.id.UserBusiness_TV_MainActivity);
         userReview=findViewById(R.id.UserReview_TV_MainActivity);
         share=findViewById(R.id.UserShare_FAB_MainActivity);
         connect=findViewById(R.id.UserConnet_Button_MainActivity);
         contact=findViewById(R.id.UserContact_Button_MainActivity);
         frameLayout=findViewById(R.id.framLayoutMainActivity);
//         userName.setText("Name");
         VReview=findViewById(R.id.UserReview_View_MainActivity);
         VBusiness=findViewById(R.id.UserBusiness_View_MainActivity);
         loadingDialog=new LoadingDialog(MainActivity.this);
         // ...
        loadingDialog.startLoadingDialog();


        Uri uri=getIntent().getData();
        if (uri!=null){
            Toast.makeText(this, ""+uri, Toast.LENGTH_SHORT).show();
        }

//        fillData();
        startAsyncTask();
        userBusiness.setOnClickListener(view -> businessClick());
        userReview.setOnClickListener(view -> reviewClick());




    }

    private void fillData() {
        Retrofit jsonRetrofit=new Retrofit.Builder()
                .baseUrl("https://api.gramoday.net:8082/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonInterface jsonInterface=jsonRetrofit.create(JsonInterface.class);
        Call<JsonResponse> jsonResponseCall=jsonInterface.getData();
        jsonResponseCall.enqueue(new Callback<JsonResponse>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if(!response.isSuccessful()){
                    return;
                }
                Log.d("response", "onResponse: Post successful ");
                Log.d("call","jason call is successful");
                   JsonResponse jsonResponse=response.body();
                    assert jsonResponse != null;
                    if(jsonResponse.getName() !=null){
                       userName.setText(jsonResponse.getName());
//                       userName.setText("Name");
                   }
                    if(jsonResponse.getLoclevel2Name() !=null && jsonResponse.getLoclevel3Name()!=null){
                        userAddress.setText(String.format("%s, %s", jsonResponse.getLoclevel3Name(), jsonResponse.getLoclevel2Name()));
                    }
                    if(jsonResponse.getLanguage().equals("en")){
                        userLanguage.setText("Speaks English");
                    }
                    loadingDialog.dismissDialog();
                businessClick();

            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.d("response", "onResponse: failed "+t.getMessage());
                loadingDialog.dismissDialog();

            }
        });
    }


    private void businessClick() {

        getSupportFragmentManager().beginTransaction().replace(R.id.framLayoutMainActivity,businessFragment).commit();
        VBusiness.setBackgroundResource(R.color.teal_700);
        VReview.setBackgroundResource(R.color.teal_200);

    }

    private void reviewClick() {

        getSupportFragmentManager().beginTransaction().replace(R.id.framLayoutMainActivity,reviewFragment).commit();
        VBusiness.setBackgroundResource(R.color.teal_200);
        VReview.setBackgroundResource(R.color.teal_700);


    }
    public void startAsyncTask(){
        AsyncTaskFetchData taskFetchData=new AsyncTaskFetchData(this);
        taskFetchData.execute(0);
    }


    private static class AsyncTaskFetchData extends AsyncTask<Integer,Integer,String>{
        private final WeakReference<MainActivity> activityWeakReference;
        AsyncTaskFetchData(MainActivity activity){
            activityWeakReference=new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            MainActivity activity=activityWeakReference.get();

            activity.fillData();
            return "Finished";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }




        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }




}