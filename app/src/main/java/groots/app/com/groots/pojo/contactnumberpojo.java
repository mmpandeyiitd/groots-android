package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aakash on 11/1/17.
 */

public class contactnumberpojo {


    @SerializedName("order_support")
    @Expose
    public String orderSupportNumber;

    @SerializedName("customer_support")
    @Expose
    public String custSupportNumber;

    @SerializedName("sales_contact")
    @Expose
    public String salesSupportNumber;



}



