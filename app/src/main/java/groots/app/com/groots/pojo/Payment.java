package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aakash on 9/12/16.
 */

public class Payment {


    @SerializedName("date")
    @Expose
    public String paymentDate;



    @SerializedName("referenceNo")
    @Expose
    public String refNo;

    @SerializedName("amountPaid")
    @Expose
    public Double amountPaid;

    @SerializedName("modeOfPayment")
    @Expose
    public String modeofPayment;

    @SerializedName("chequeStatus")
    @Expose
    public String chequeStatus;

    @SerializedName("chequeNo")
    @Expose
    public String chequeNo;




}
