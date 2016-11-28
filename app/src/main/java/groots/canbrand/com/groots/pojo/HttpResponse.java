package groots.canbrand.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 12/4/16.
 */
public class HttpResponse<T> {

    @SerializedName("status")
    @Expose
    public Integer status;

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("errors")
    @Expose
    public List<String> errors = new ArrayList<String>();

    @SerializedName("count")
    @Expose
    public Long count;

    @SerializedName("data")
    @Expose
    public HttpData<T> data;
}
