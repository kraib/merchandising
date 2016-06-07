package lucidace.com.merchandising.models.branches;




import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kraiba on 16/05/16.
 */

public class Branch extends RealmObject implements Serializable {
    @PrimaryKey
    private String branch_id;
    private String branch_name;
    private String location;
    private String phone_number;
    private String email;
    private String description;
    private String date_created_at;
    private String photo;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
