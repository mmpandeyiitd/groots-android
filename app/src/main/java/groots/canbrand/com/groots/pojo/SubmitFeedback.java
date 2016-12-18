package groots.canbrand.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aakash on 29/11/16.
 */

public class SubmitFeedback {



     @SerializedName("status")
    @Expose
    public Integer Status;

    @SerializedName("msg")
    @Expose
    public String message;
}
