package groots.canbrand.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 12/4/16.
 */
public class ProductListErrData {


    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("code")
    @Expose
    public Integer code;
}
