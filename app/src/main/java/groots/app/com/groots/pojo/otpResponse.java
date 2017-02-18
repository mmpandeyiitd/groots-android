package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aakash on 14/2/17.
 */

public class otpResponse {

    @SerializedName("otp")
    @Expose
    public String otp;
}
