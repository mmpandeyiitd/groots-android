package groots.canbrand.com.groots.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by can on 8/4/16.
 */
public class OtpFirst {

    private String OTP;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The OTP
     */
    public String getOTP() {
        return OTP;
    }

    /**
     *
     * @param OTP
     * The OTP
     */
    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
