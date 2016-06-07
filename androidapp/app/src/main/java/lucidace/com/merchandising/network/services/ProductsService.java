package lucidace.com.merchandising.network.services;

import java.util.List;

import lucidace.com.merchandising.models.branches.BranchPojo;
import lucidace.com.merchandising.models.common.User;
import lucidace.com.merchandising.models.products.ProductCategory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by kraiba on 16/05/16.
 */
public interface ProductsService {

    @GET("/index.php?r=V1/product-category")
    Call<List<ProductCategory>> getCategories(@Header("Authorization") String authorization);

    @GET("/index.php?r=V1/branch/mine")
    Call<List<BranchPojo>> getMyBranches (@Header("Authorization") String authorization);

    @GET("/index.php?r=V1/login/my-account")
    Call<List<User>> login (@Header("Authorization") String authorization);
}
