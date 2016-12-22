package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 12/4/16.
 */
public class HttpResponseData<T> {


    @SerializedName("numFound")
    @Expose
    public Integer numFound;
    @SerializedName("start")
    @Expose
    public Integer start;
    @SerializedName("docs")
    @Expose
    public List<T> docs = new ArrayList<T>();
}
