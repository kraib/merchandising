package lucidace.com.merchandising.models.branches;

import java.io.Serializable;

/**
 * Created by kraiba on 17/03/16.
 */
public class SuperMarket implements Serializable {
    private String name;
    private String description;
    private int image;

    public SuperMarket(){

    }

    public SuperMarket(String name, String description, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
