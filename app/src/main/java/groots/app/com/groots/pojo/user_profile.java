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

    @SerializedName("mobileNo")
    @Expose
    public String contactNo;

    @SerializedName("name")
    @Expose
    public String orgName;

    @SerializedName("email")
    @Expose
    public String email_id;

    @SerializedName("password")
    @Expose
    public String newPassword;

    @SerializedName("confirmPassword")
    @Expose
    public String confirmPassword;

    @SerializedName("address")
    @Expose
    public String addresss;

    @SerializedName("city")
    @Expose
    public String city;

    @SerializedName("state")
    @Expose
    public String statee;

    @SerializedName("pincode")
    @Expose
    public String pincode;

    @SerializedName("website")
    @Expose
    public String website;

    @SerializedName("paymentMode")
    @Expose
    public String paymentMode;

    @SerializedName("paymentFreq")
    @Expose
    public String paymentFreq;

    @SerializedName("alternateEmail")
    @Expose
    public String alternateEmail;

    @SerializedName("tanNo")
    @Expose
    public String tanNo;

    @SerializedName("panNo")
    @Expose
    public String panNo;

    @SerializedName("id")
    @Expose
    public String user_id;


    @SerializedName("contact")
    @Expose
    public String contactNoo;

    /*@SerializedName("retailerName")
    @Expose
    public String orgNamee;*/



}
