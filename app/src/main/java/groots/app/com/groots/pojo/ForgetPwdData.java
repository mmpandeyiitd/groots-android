package groots.app.com.groots.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by can on 12/4/16.
 */
public class ForgetPwdData {

    private Integer status;
    private String msg;
    private List<Object> errors = new ArrayList<Object>();
    private ForgetPwdDataChild data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     * The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     *
     * @return
     * The error_object
     */
    public List<Object> getErrors() {
        return errors;
    }

    /**
     *
     * @param errors
     * The error_object
     */
    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    /**
     *
     * @return
     * The data
     */
    public ForgetPwdDataChild getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(ForgetPwdDataChild data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
