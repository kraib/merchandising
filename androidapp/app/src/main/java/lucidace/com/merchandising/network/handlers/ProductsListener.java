package lucidace.com.merchandising.network.handlers;

import java.util.List;

import lucidace.com.merchandising.models.products.ProductCategory;

/**
 * Created by kraiba on 16/05/16.
 */
public interface ProductsListener {
    void productsCategoryReceived(List<ProductCategory> productCategories);
    void productsReceived(String messages);
    void networkFailure(String localizedMessage);
    void emptyDataSet(String localizedMessage);
}
