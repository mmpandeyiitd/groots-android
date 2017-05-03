package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aakash on 21/1/17.
 */

public class HttpResponseofProducts<T> {

    @SerializedName("totalCount")
    @Expose
    public Integer numFound;

    @SerializedName("statusCode")
    @Expose
    public String status;

    @SerializedName("version")
    @Expose
    public String version;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("error")
    @Expose
    public error_object errors  ;

    @SerializedName("data")
    @Expose
    public List<T> data = new ArrayList<T>();

   /* @SerializedName("data")
    @Expose
    public Object dataObject;*/


}
