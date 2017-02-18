package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.ObjectConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aakash on 16/2/17.
 */

public class HttpResponseObject<T> {

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
    public String errors;

    @SerializedName("data")
    @Expose
    public T data;
}
