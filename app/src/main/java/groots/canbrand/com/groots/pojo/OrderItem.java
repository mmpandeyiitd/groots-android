package groots.canbrand.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;



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
