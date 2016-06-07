package lucidace.com.merchandising.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import lucidace.com.merchandising.models.products.ProductCategory;

/**
 * Created by kraiba on 16/05/16.
 */
public class ProductsDataController {

    private static final String TAG =ProductsDataController.class.getSimpleName() ;
    private static ProductsDataController instance;
    private final Realm realm;

    public ProductsDataController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static ProductsDataController with(Fragment fragment) {

        if (instance == null) {
            instance = new ProductsDataController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static ProductsDataController with(Activity activity) {

        if (instance == null) {
            instance = new ProductsDataController(activity.getApplication());
        }
        return instance;
    }

    public static ProductsDataController with(Application application) {

        if (instance == null) {
            instance = new ProductsDataController(application);
        }
        return instance;
    }

    public static ProductsDataController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(ProductCategory.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<ProductCategory> getCategories() {

        return realm.where(ProductCategory.class).findAll();
    }

    //query a single item with the given id
    public ProductCategory getCategory(String id) {


        return realm.where(ProductCategory.class).equalTo("category_id", Integer.parseInt(id)).findFirst();
    }

    //check if Book.class is empty
    public boolean hasCategories() {

        return !realm.allObjects(ProductCategory.class).isEmpty();
    }


}
