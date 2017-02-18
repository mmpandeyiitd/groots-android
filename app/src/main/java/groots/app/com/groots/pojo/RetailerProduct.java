package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aakash on 21/1/17.
 */

public class RetailerProduct {
    @SerializedName("retailerId")
    @Expose
    public Integer retailer_id;

    @SerializedName("status")
    @Expose
    public boolean isMapped ;

    @SerializedName("subscProdId")
    @Expose
    public Integer subscribedProductId;

    @SerializedName("product")
    @Expose
    public Items product;






}
