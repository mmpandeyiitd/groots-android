package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by can on 11/4/16.
 */
public class LoginData1 {

    private String user_id;
    private String retailer_name;
    private String name;
    @SerializedName("registration_status")
    @Expose
    public String regStatus;

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return user_id;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.user_id = userId;
    }

    /**
     *
     * @return
     * The retailerName
     */
    public String getRetailerName() {
        return retailer_name;
    }

    /**
     *
     * @param retailerName
     * The retailer_name
     */
    public void setRetailerName(String retailerName) {
        this.retailer_name = retailerName;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }
}
