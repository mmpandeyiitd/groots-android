package groots.app.com.groots.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 06-05-2016.
 */
public class DateTimeChild {

    private String current_date_time;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The currentDateTime
     */
    public String getCurrentDateTime() {
        return current_date_time;
    }

    /**
     *
     * @param currentDateTime
     * The current_date_time
     */
    public void setCurrentDateTime(String currentDateTime) {
        this.current_date_time = currentDateTime;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
