package groots.canbrand.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 12/4/16.
 */
public class ProductListData1 {

    @SerializedName("responseHeader")
    @Expose
    public ProductListResponseHeaderData responseHeader;
    @SerializedName("response")
    @Expose
    public ProductListResponseData response;
    @SerializedName("error")
    @Expose
    public ProductListErrData error;
}
