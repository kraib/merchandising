package lucidace.com.merchandising.network.handlers;

import java.util.List;

import io.realm.Realm;
import lucidace.com.merchandising.app.EndPoints;
import lucidace.com.merchandising.app.MyApplication;
import lucidace.com.merchandising.models.branches.BranchPojo;
import lucidace.com.merchandising.models.products.ProductCategory;
import lucidace.com.merchandising.network.services.ProductsService;
import lucidace.com.merchandising.realm.BranchDataController;
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
public class BranchHandler {

    private static final String TAG =BranchHandler.class.getSimpleName() ;
    BranchListener branchListener;
    ProductsService gservice;
    String token;



    public BranchHandler(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create(CustomGsonParser.returnCustomParser()))
                .baseUrl(EndPoints.BASE_URL).build();

        token= MyApplication.getInstance().getToken();
        gservice = retrofit.create(ProductsService.class);
    }

    public void getMyBranches(final BranchListener branchListener){
        Call<List<BranchPojo>> call =gservice.getMyBranches("Bearer "+token);
        call.enqueue(new Callback<List<BranchPojo>>() {
            @Override
            public void onResponse(Call<List<BranchPojo>> call, Response<List<BranchPojo>> response) {
                if (response.body().isEmpty()) {
                    branchListener.emptyDataSet("No Data Received");
                } else {
                    //Ream Sync
                    BranchDataController.with(MyApplication.getInstance()).clearAll();
                    Realm realm = ProductsDataController.with(MyApplication.getInstance()).getRealm();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    branchListener.productsBranchesReceived(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<BranchPojo>> call, Throwable t) {
                branchListener.networkFailure("Failed");
            }
        });
    }

}
