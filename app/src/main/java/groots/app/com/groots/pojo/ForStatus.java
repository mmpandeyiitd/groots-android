package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aakash on 7/2/17.
 */

public class ForStatus {



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

    public boolean getstatusunmap(){
        return statusUnmap;

    }

    public void setstatusunmap(boolean statusUnmap){
        this.statusUnmap = statusUnmap;
    }

    public boolean statusUnmap = false;

    public boolean getstatusmap(){
        return statusmap;

    }

    public void setstatusmap(boolean statusmap){
        this.statusmap = statusmap;
    }

    public boolean statusmap = true;

}
