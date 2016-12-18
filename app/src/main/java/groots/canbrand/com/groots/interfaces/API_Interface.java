package groots.canbrand.com.groots.interfaces;

import java.util.Map;

import groots.canbrand.com.groots.pojo.AddOrderParent;
import groots.canbrand.com.groots.pojo.DateTimePojo;
import groots.canbrand.com.groots.pojo.ForgetPwdData;
import groots.canbrand.com.groots.pojo.Order;
import groots.canbrand.com.groots.pojo.LoginData;

import groots.canbrand.com.groots.pojo.HttpResponse;
import groots.canbrand.com.groots.pojo.OrderFeedback;
import groots.canbrand.com.groots.pojo.Product;
//import groots.canbrand.com.groots.pojo.ProductListDocData;
import groots.canbrand.com.groots.pojo.SubmitFeedback;
import groots.canbrand.com.groots.pojo.signUpResponse;
import groots.canbrand.com.groots.pojo.user_profile;
import groots.canbrand.com.groots.pojo.user_profile;
import retrofit.Callback;
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
    @POST("/index.php/api/productlist")
    void getproductListingResponse(@Header("API_KEY") String apikey,
                              @Header("APP_VERSION") String appversion,
                              @Header("CONFIG_VERSION") String config,
                              @Header("AUTH_TOKEN") String auth,
                              @FieldMap Map<String,String> alldata, Callback<HttpResponse<Product>> cb);


    @GET("/index.php/api/orders")
    void getorderListingResponse(@Header("API_KEY") String apikey,
                                 @Header("APP_VERSION") String appversion,
                                 @Header("CONFIG_VERSION") String config,
                                 @Header("AUTH_TOKEN") String auth,
                                 @QueryMap Map<String,String> alldata, Callback<HttpResponse<Order>> cb);






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


    @FormUrlEncoded
    @POST("/index.php/api/signUp")
    void getsignupresponse(@Header("API_KEY") String apikey,
                                   @Header("APP_VERSION") String appversion,
                                   @Header("CONFIG_VERSION") String config,

                                   @FieldMap Map<String,String> alldata, Callback<HttpResponse<signUpResponse>> cb);


    @GET("/index.php/api/user_profile")
    void getretailerdetailsresponse(@Header("API_KEY") String apikey,
                                  @Header("APP_VERSION") String appversion,
                                  @Header("CONFIG_VERSION") String config,
                                  @Header("AUTH_TOKEN") String auth,
                                  Callback<HttpResponse<user_profile>> cb);




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
