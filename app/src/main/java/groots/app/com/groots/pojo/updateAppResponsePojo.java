package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aakash on 6/1/17.
 */

public class updateAppResponsePojo {


    @SerializedName("forceUpdate")
    @Expose
    public Boolean forceUpdate;

    @SerializedName("recommended")
    @Expose
    public Boolean recommendedUpdate;



    @SerializedName("link")
    @Expose
    public String appLink;

    @SerializedName("latestAppVersion")
    @Expose
    public String latestAppVersion;



}
