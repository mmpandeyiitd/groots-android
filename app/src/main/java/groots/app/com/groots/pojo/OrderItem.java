package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by aakash on 10/11/16.
 */

public class OrderItem {



    @SerializedName("subscribedProductId")
    @Expose
    public Integer subscribedProductId;

    @SerializedName("deliveredQty")
    @Expose
    public Double deliveredQty;

    @SerializedName("baseProductId")
    @Expose
    public Integer baseProductId;

    @SerializedName("unitPrice")
    @Expose
    public Double unitPrice;



    //@SerializedName("productQty")
    //@Expose
    //public Double productQty;

    @SerializedName("productQty")
    @Expose
    public Double productQty;




    @SerializedName("price")
    @Expose
    public Double itemOrderPrice;

    @SerializedName("product")
    @Expose
    public  Product product;


}
