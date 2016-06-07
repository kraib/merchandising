package lucidace.com.merchandising.network.handlers;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import lucidace.com.merchandising.app.EndPoints;
import lucidace.com.merchandising.app.MyApplication;
import lucidace.com.merchandising.models.products.ProductCategory;
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
public class ProductsHandler {

    private static final String TAG =ProductsHandler.class.getSimpleName() ;
    ProductsListener productsListener;
    ProductsService gservice;
    String token;



    public ProductsHandler(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create(CustomGsonParser.returnCustomParser()))
                .baseUrl(EndPoints.BASE_URL).build();

        token=MyApplication.getInstance().getToken();
        gservice = retrofit.create(ProductsService.class);
    }

    public void getProductCategories(final ProductsListener productsListener){
        Call<List<ProductCategory>> call =gservice.getCategories("Bearer "+token);
        call.enqueue(new Callback<List<ProductCategory>>() {
            @Override
            public void onResponse(Call<List<ProductCategory>> call, Response<List<ProductCategory>> response) {
                if (response.body().isEmpty()) {
                    productsListener.emptyDataSet("No Data Received");
                } else {

                    //Ream Sync
                    ProductsDataController.with(MyApplication.getInstance()).clearAll();
                    Realm realm = ProductsDataController.with(MyApplication.getInstance()).getRealm();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    productsListener.productsCategoryReceived(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<ProductCategory>> call, Throwable t) {
                productsListener.networkFailure("Failed");
            }
        });
    }
}
