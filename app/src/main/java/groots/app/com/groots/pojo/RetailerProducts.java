package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aakash on 3/2/17.
 */

public class RetailerProducts  {

    @SerializedName("retailerProds")
    @Expose
    public List<RetailerProduct> retailerProds;

}
