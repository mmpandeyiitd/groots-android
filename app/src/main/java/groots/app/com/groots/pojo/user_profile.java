package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aakash on 5/12/16.
 */

public class user_profile {


    @SerializedName("minOrderPrice")
    @Expose
    public Double minOrderPrice;

    @SerializedName("shippingCharge")
    @Expose
    public Double shippingCharge;

    @SerializedName("retailerName")
    @Expose
    public String retailerName;

    @SerializedName("salesRepName")
    @Expose
    public String salesRepName;

    @SerializedName("collectionRepName")
    @Expose
    public String collectionRepName;

    @SerializedName("outstandingDate")
    @Expose
    public String outstandingDate;

    @SerializedName("outstandingAmount")
    @Expose
    public Double outstandingAmount;


}
