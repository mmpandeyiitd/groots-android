package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aakash on 21/1/17.
 */

public class Items {

    @SerializedName("subscProdId")
    @Expose
    public Integer subscribedProductId;


    @SerializedName("baseProdId")
    @Expose
    public Integer baseProductId;


    @SerializedName("storeOfferPrice")
    @Expose
    public Double storeOfferPrice;

    @SerializedName("packSize")
    @Expose
    public Integer packSize;
    @SerializedName("packUnit")
    @Expose
    public String packUnit;

    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("thumbUrl")
    @Expose
    public List<String> thumbUrl = new ArrayList<String>();

    public Double getItemCount() {
        return itemCount;
    }

    public void setItemCount(Double itemCount) {
        this.itemCount = itemCount;
    }

    private Double itemCount = 0.0;



}
