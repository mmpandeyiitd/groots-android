package groots.app.com.groots.interfaces;

import java.util.ArrayList;
import java.util.Map;

import groots.app.com.groots.pojo.AddOrderParent;
import groots.app.com.groots.pojo.DateTimePojo;
import groots.app.com.groots.pojo.ForgetPwdData;
import groots.app.com.groots.pojo.HttpResponseObject;
import groots.app.com.groots.pojo.HttpResponseofProducts;
import groots.app.com.groots.pojo.Items;
import groots.app.com.groots.pojo.Order;
import groots.app.com.groots.pojo.LoginData;

import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.OrderFeedback;
import groots.app.com.groots.pojo.Product;
//import groots.canbrand.com.groots.pojo.ProductListDocData;
import groots.app.com.groots.pojo.RetailerProduct;
import groots.app.com.groots.pojo.RetailerProducts;
import groots.app.com.groots.pojo.SubmitFeedback;
import groots.app.com.groots.pojo.UpdateOrderParent;
import groots.app.com.groots.pojo.contactnumberpojo;
import groots.app.com.groots.pojo.otpResponse;
import groots.app.com.groots.pojo.signUpResponse;
import groots.app.com.groots.pojo.updateAppResponsePojo;
import groots.app.com.groots.pojo.user_profile;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.QueryMap;


/**
 * Created by can on 11/4/16.
 */
public interface API_Interface {

    @FormUrlEncoded
    @POST("/index.php/api/userlogin")
    void getloginResponse(@Header("API_KEY") String apikey,
                          @Header("APP_VERSION") String appversion,
                          @Header("CONFIG_VERSION") String config,
                          @FieldMap Map<String,String> alldata, Callback<LoginData> cb);

    @FormUrlEncoded
    @POST("/index.php/api/forgotPassword")
    void getForgetPwdResponse(@Header("API_KEY") String apikey,
                          @Header("APP_VERSION") String appversion,
                          @Header("CONFIG_VERSION") String config,
                          @FieldMap Map<String,String> alldata, Callback<ForgetPwdData> cb);

    @FormUrlEncoded
    @POST("/index.php/api/addorder")
    void getAddOrderResponce(@Header("API_KEY") String apikey,
                             @Header("APP_VERSION") String appversion,
                             @Header("CONFIG_VERSION") String config,
                             @Header("AUTH_TOKEN")String authtoken,
                             @FieldMap Map<String,String> alldata, Callback<AddOrderParent> cb);
    @FormUrlEncoded
    @POST("/index.php/api/makeRetailerActive")
    void getChangeRegStatusResponse(@Header("API_KEY") String apikey,
                                    @Header("APP_VERSION") String appversion,
                                    @Header("CONFIG_VERSION") String config,
                                    @Header("AUTH_TOKEN")String authtoken,
                                    @FieldMap Map<String,String> alldata, Callback<HttpResponse> cb);


    @FormUrlEncoded
    @POST("/index.php/api/updateOrder")
    void getUpdateOrderResponce(@Header("API_KEY") String apikey,
                             @Header("APP_VERSION") String appversion,
                             @Header("CONFIG_VERSION") String config,
                             @Header("AUTH_TOKEN")String authtoken,
                             @FieldMap Map<String,String> alldata, Callback<UpdateOrderParent> cb);



    @FormUrlEncoded
    @POST("/index.php/api/productlist")
    void getproductListingResponse(@Header("API_KEY") String apikey,
                              @Header("APP_VERSION") String appversion,
                              @Header("CONFIG_VERSION") String config,
                              @Header("AUTH_TOKEN") String auth,
                              @FieldMap Map<String,String> alldata, Callback<HttpResponse<Product>> cb);


    @POST("/cucumber/data/v1/retailer/product")
     void getselectedproductsresponse(@Header("API_KEY") String apikey,
                                      @Header("APP_VERSION") String appversion,
                                      @Header("CONFIG_VERSION") String config,
                                      @Header("Content-Type") String ContentType,
                                      @Header("AUTH_TOKEN") String auth,
                                      @Body RetailerProducts retailerProds , Callback<HttpResponseofProducts> cb);



    @POST("/cucumber/data/v1/retailer/register")
    void getsignupresponse(@Header("API_KEY") String apikey,
                           @Header("APP_VERSION") String appversion,
                           @Header("CONFIG_VERSION") String config,
                           @Header("Content-Type") String ContentType,
                           @Body user_profile retailer, Callback<HttpResponseObject<user_profile>> cb);

    @POST("/cucumber/data/v1/otp/validate")
    void getOtpcheckResponse(@Header("API_KEY") String apikey,
                             @Header("APP_VERSION") String appversion,
                             @Header("CONFIG_VERSION") String config,
                             @QueryMap Map<String,String> alldata,
                             @Body String otp, Callback<HttpResponseofProducts> cb);



    @GET("/cucumber/data/v1/product/sample")
    void getSampleProductsResponse(@Header("API_KEY") String apikey,
                                   @Header("APP_VERSION") String app_version,
                                   @Header("CONFIG_VERSION") String config,
                                   @Header("AUTH_TOKEN") String auth,
                                   @QueryMap Map<String,String> alldata,Callback<HttpResponseofProducts<Items>> cb);


    @GET("/index.php/api/orders")
    void getorderListingResponse(@Header("API_KEY") String apikey,
                                 @Header("APP_VERSION") String appversion,
                                 @Header("CONFIG_VERSION") String config,
                                 @Header("AUTH_TOKEN") String auth,
                                 @QueryMap Map<String,String> alldata, Callback<HttpResponse<Order>> cb);




    /*@GET("/index.php/api/search")
    void getsearchlistingresponse(@Header("API_KEY") String apikey,
                                 @Header("APP_VERSION") String appversion,
                                 @Header("CONFIG_VERSION") String config,
                                 @Header("AUTH_TOKEN") String auth,
                                 @QueryMap Map<String,String> alldata, Callback<HttpResponseofProducts<RetailerProduct>> cb);*/




    @GET("/cucumber/data/v1/retailer/product")
    void getallproductslistingresponse(@Header("API_KEY") String apikey,
                                  @Header("APP_VERSION") String appversion,
                                  @Header("CONFIG_VERSION") String config,
                                  @Header("AUTH_TOKEN") String auth,
                                  @QueryMap Map<String,String> alldata, Callback<HttpResponseofProducts<RetailerProduct>> cb);




    @FormUrlEncoded
    @POST("/index.php/api/fillRetailerDetails")
    void getFillRetailerDetails(@Header("API_KEY") String apikey,
                                @Header("APP_VERSION") String appversion,
                                @Header("CONFIG_VERSION") String config,
                                @Header("AUTH_TOKEN") String auth,
                                @FieldMap Map<String,String> alldata,Callback<HttpResponse> cb);






    //@FormUrlEncoded
    @GET("/index.php/api/orderdetails")
    void getorderitemListingResponse(@Header("API_KEY") String apikey,
                                 @Header("APP_VERSION") String appversion,
                                 @Header("CONFIG_VERSION") String config,
                                 @Header("AUTH_TOKEN") String auth,
                                 @QueryMap Map<String,String> alldata, Callback<HttpResponse<Order>> cb);


    @GET("/index.php/api/checkFeedback")
    void getcheckfeedbackresponse(@Header("API_KEY") String apikey,
                                     @Header("APP_VERSION") String appversion,
                                     @Header("CONFIG_VERSION") String config,
                                     @Header("AUTH_TOKEN") String auth,
                                     Callback<HttpResponse<OrderFeedback>> cb);

    @FormUrlEncoded
    @POST("/index.php/api/submitFeedback")
    void getsubmitfeedbackresponse(@Header("API_KEY") String apikey,
                          @Header("APP_VERSION") String appversion,
                          @Header("CONFIG_VERSION") String config,
                                   @Header("AUTH_TOKEN")String authtoken,
                          @FieldMap Map<String,String> alldata, Callback<HttpResponse<SubmitFeedback>> cb);





    @GET("/index.php/api/user_profile")
    void getretailerdetailsresponse(@Header("API_KEY") String apikey,
                                  @Header("APP_VERSION") String appversion,
                                  @Header("CONFIG_VERSION") String config,
                                  @Header("AUTH_TOKEN") String auth,
                                  Callback<HttpResponse<user_profile>> cb);

    @FormUrlEncoded
    @POST("/index.php/api/partialUpdateOrder")
    void getpatchorderresponse(@Header("API_KEY") String apikey,
                               @Header("APP_VERSION") String appversion,
                               @Header("CONFIG_VERSION") String config,
                               @Header("AUTH_TOKEN") String auth,
                               @FieldMap Map<String,String> alldata , Callback<HttpResponse> cb);


    @GET("/index.php/api/checkAppUpdate")
    void getappupdateresponse(@Header("API_KEY") String apikey,
                              @Header("APP_VERSION") String appversion,
                              @Header("CONFIG_VERSION") String config,
                              @Header("AUTH_TOKEN") String auth,
                              Callback<HttpResponse<updateAppResponsePojo>> cb);


    @GET("/index.php/api/getcontactnumbers")
    void getcontactnumberresponse(@Header("API_KEY") String apikey,
                                  @Header("APP_VERSION") String appversion,
                                  @Header("CONFIG_VERSION") String config,
                                  @Header("AUTH_TOKEN") String auth,
                                  Callback<HttpResponse<contactnumberpojo>> cb);

    @GET("/index.php/api/salesContactNo")
    void getsalesnoresponse(@Header("API_KEY") String apikey,
                            @Header("APP_VERSION") String appversion,
                            @Header("CONFIG_VERSION") String config,
                            Callback<HttpResponse<contactnumberpojo>> cb);



    @GET("/index.php/api/emailInvoice")
    void getemailinvoiceresponse(@Header("API_KEY") String apikey,
                                 @Header("APP_VERSION") String appversion,
                                 @Header("CONFIG_VERSION") String config,
                                 @Header("AUTH_TOKEN") String auth,
                                @QueryMap Map<String,String> alldata,Callback<HttpResponse> cb);






  /*  @GET("/index.php/api/serverDatetime")
    void getTime(@Header("API_KEY") String apikey,
                     @Header("APP_VERSION") String appversion,
                     @Header("CONFIG_VERSION") String config,
                     @Header("AUTH_TOKEN") String auth,Callback<DateTimeChild> cb);*/

    @GET("/index.php/api/serverDatetime")
    void getTime(@Header("API_KEY") String apikey, @Header("APP_VERSION") String appversion,
                 @Header("CONFIG_VERSION") String config, @Header("AUTH_TOKEN") String auth,
                 Callback<DateTimePojo> callback);
}
