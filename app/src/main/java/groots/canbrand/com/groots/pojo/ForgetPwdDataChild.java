package groots.canbrand.com.groots.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 25-04-2016.
 */
public class ForgetPwdDataChild {


    private String message;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
