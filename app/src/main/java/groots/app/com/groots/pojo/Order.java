package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aakash on 3/11/16.
 */

public class Order {



    /*@SerializedName("subscribed_product_id")
    @Expose
    public Integer subscribedProductId;

    @SerializedName("id")
    @Expose
    public String id;*/




    @SerializedName("invoiceNo")
    @Expose
    public String invoiceNo;


    @SerializedName("shippingCharges")
    @Expose
    public Double shippingCharges;

    @SerializedName("total")
    @Expose
    public Double total;


    @SerializedName("deliveryDate")
    @Expose
    public String deliveryDate;

    @SerializedName("orderId")
    @Expose
    public String orderId;

    @SerializedName("orderNumber")
    @Expose
    public String orderNumber;

    @SerializedName("orderType")
    @Expose
    public String orderType;

    @SerializedName("status")
    @Expose
    public String Status;

    @SerializedName("paymentStatus")
    @Expose
    public String paymentStatus;

    @SerializedName("totalPayableAmount")
    @Expose
    public Double totalPayableAmount;

    @SerializedName("orderItems")
    @Expose
    public  List<OrderItem> orderItems ;

    @SerializedName("orderFeedback")
    @Expose
    public  List<OrderFeedback> orderFeedbacks ;

    public Payment payment;

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    private int itemCount;



}
