package groots.canbrand.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aakash on 24/11/16.
 */

public class OrderFeedback {


    /*@SerializedName("statusCode")
    @Expose
    public Integer statuscode;

    @SerializedName("msg")
    @Expose
    public String Message;*/

    @SerializedName("orderId")
    @Expose
    public String orderId;

    @SerializedName("feedbackStatus")
    @Expose
    public Boolean feedbackStatus;


    @SerializedName("deliveryDate")
    @Expose
    public String deliveryDate;




}
