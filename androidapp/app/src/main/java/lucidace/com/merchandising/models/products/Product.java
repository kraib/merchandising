package lucidace.com.merchandising.models.products;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kraiba on 17/03/16.
 */
public class Product extends RealmObject implements Serializable {
    @PrimaryKey
    private int product_id;

    private String product_name;
    private String price;
    private String description;
    @Ignore
    private String expiry;
    private String date_created_at;
    private String photo;
    private String category_id;
    private String sos;
    private String competitorA;
    private String competitorB;

    @Ignore
    private List<Product> competitors = new ArrayList<Product>();

    public Product(){

    }
    public Product(String name) {
        this.product_name = name;
        this.price = price;

    }

    public List<Product> getCompetitors() {
        return competitors;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getExpiry() {
        return expiry;
    }

    public String getSos() {
        return sos;
    }

    public void setSos(String sos) {
        this.sos = sos;
    }

    public String getCompetitorA() {
        return competitorA;
    }

    public void setCompetitorA(String competitorA) {
        this.competitorA = competitorA;
    }

    public String getCompetitorB() {
        return competitorB;
    }

    public void setCompetitorB(String competitorB) {
        this.competitorB = competitorB;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDate_created_at() {
        return date_created_at;
    }

    public void setDate_created_at(String date_created_at) {
        this.date_created_at = date_created_at;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
