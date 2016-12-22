package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 12/4/16.
 */
public class HttpResponseHeaderData {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("QTime")
    @Expose
    public Integer QTime;
    @SerializedName("params")
    @Expose
    public ProductListParamterData params;

}
