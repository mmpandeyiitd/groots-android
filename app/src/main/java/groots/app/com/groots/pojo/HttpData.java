package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 12/4/16.
 */
public class HttpData<T> {

    @SerializedName("responseHeader")
    @Expose
    public HttpResponseHeaderData responseHeader;
    @SerializedName("response")
    @Expose
    public HttpResponseData<T> responseData;
    @SerializedName("error")
    @Expose
    public HttpError error;
}
