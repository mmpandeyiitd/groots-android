package groots.canbrand.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 11/4/16.
 */

public class LoginData {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = new ArrayList<Object>();
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private LoginData1 data;

    /**
     *
     * @return
     * The status
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The errors
     */
    public List<Object> getErrors() {
        return errors;
    }

    /**
     *
     * @param errors
     * The errors
     */
    public void setErrors(List<Object> errors) {
        this.errors = errors;
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
     * The data
     */
    public LoginData1 getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(LoginData1 data) {
        this.data = data;
    }

}
