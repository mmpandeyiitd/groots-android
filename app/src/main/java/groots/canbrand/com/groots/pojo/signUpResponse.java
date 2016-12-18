package groots.canbrand.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aakash on 16/12/16.
 */

public class signUpResponse {


    @SerializedName("status")
    @Expose
    public Integer statu;

    @SerializedName("msg")
    @Expose
    public String message;

}
