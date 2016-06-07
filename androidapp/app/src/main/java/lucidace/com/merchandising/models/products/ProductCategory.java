package lucidace.com.merchandising.models.products;

import android.graphics.Bitmap;
import android.net.Uri;


import org.parceler.Parcel;
import org.parceler.ParcelProperty;
import org.parceler.ParcelPropertyConverter;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;



/**
 * Created by kraiba on 17/03/16.
 */
public class ProductCategory extends RealmObject implements Serializable  {
    @PrimaryKey
    private int category_id;

    private String category_name;

    private String description;
    private String date_created_at;
    private String photo;
    @Ignore
    private String brands;
    @Ignore
    private String why;
    @Ignore
    private String feedback;


    private RealmList<Product> products;

    @Ignore
    private List<String> gallery = new ArrayList<String>();

    public  ProductCategory(){

    }

    public ProductCategory(String name, String description, int Photo) {
        this.category_name = name;
        this.description = description;
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public List<Product> getProducts() {
        return products;
    }



    public void setProducts(RealmList<Product> products) {
        this.products = products;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }


    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDate_created_at() {
        return date_created_at;
    }

    public void setDate_created_at(String date_created_at) {
        this.date_created_at = date_created_at;
    }


    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
