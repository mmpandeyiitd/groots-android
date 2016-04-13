package groots.canbrand.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 13-04-2016.
 */
public class AddOrderChild {
    @SerializedName("order_id")
    @Expose
    private Integer order_id;
    @SerializedName("order_no")
    @Expose
    private String order_no;

    /**
     *
     * @return
     * The orderId
     */
    public Integer getOrderId() {
        return order_id;
    }

    /**
     *
     * @param orderId
     * The order_id
     */
    public void setOrderId(Integer orderId) {
        this.order_id = orderId;
    }

    /**
     *
     * @return
     * The orderNo
     */
    public String getOrderNo() {
        return order_no;
    }

    /**
     *
     * @param orderNo
     * The order_no
     */
    public void setOrderNo(String orderNo) {
        this.order_no = orderNo;
    }
}
