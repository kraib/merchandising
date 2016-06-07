package lucidace.com.merchandising.network.handlers;

import android.util.Base64;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import lucidace.com.merchandising.app.Config;
import lucidace.com.merchandising.app.EndPoints;
import lucidace.com.merchandising.app.MyApplication;
import lucidace.com.merchandising.models.branches.BranchPojo;
import lucidace.com.merchandising.models.common.User;
import lucidace.com.merchandising.network.services.ProductsService;
import lucidace.com.merchandising.realm.ProductsDataController;
import lucidace.com.merchandising.utils.CustomGsonParser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kraiba on 16/05/16.
 */
public class AuthHandler {
    private static final String TAG =AuthHandler.class.getSimpleName() ;
    AuthListener authListener;
    ProductsService gservice;
    public AuthHandler(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create(CustomGsonParser.returnCustomParser()))
                .baseUrl(EndPoints.BASE_URL).build();
        gservice = retrofit.create(ProductsService.class);
    }

    public void login(String username, String password, final AuthListener authListener){
        String token ="Bearer "+ Base64.encodeToString((username + ":" + password).getBytes(),
                Base64.NO_WRAP);

        Call<List<User>> call =gservice.login(token);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.code()== Config.NETWORK_SUCCESS_CODE) {
                    authListener.loginSuccessFull(response.body().get(0));
                }
                else{
                    authListener.loginFailed();
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                authListener.networkFailed();
            }
        });

    }


}
