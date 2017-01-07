package groots.app.com.groots.interfaces;

import java.util.Map;

import groots.app.com.groots.pojo.AddOrderParent;
import groots.app.com.groots.pojo.DateTimePojo;
import groots.app.com.groots.pojo.ForgetPwdData;
import groots.app.com.groots.pojo.Order;
import groots.app.com.groots.pojo.LoginData;

import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.OrderFeedback;
import groots.app.com.groots.pojo.Product;
//import groots.canbrand.com.groots.pojo.ProductListDocData;
import groots.app.com.groots.pojo.SubmitFeedback;
import groots.app.com.groots.pojo.UpdateOrderParent;
import groots.app.com.groots.pojo.signUpResponse;
import groots.app.com.groots.pojo.updateAppResponsePojo;
import groots.app.com.groots.pojo.user_profile;
import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
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


    @GET("/index.php/api/orders")
    void getorderListingResponse(@Header("API_KEY") String apikey,
                                 @Header("APP_VERSION") String appversion,
                                 @Header("CONFIG_VERSION") String config,
                                 @Header("AUTH_TOKEN") String auth,
                                 @QueryMap Map<String,String> alldata, Callback<HttpResponse<Order>> cb);




    @GET("/index.php/api/search")
    void getsearchlistingresponse(@Header("API_KEY") String apikey,
                                 @Header("APP_VERSION") String appversion,
                                 @Header("CONFIG_VERSION") String config,
                                 @Header("AUTH_TOKEN") String auth,
                                 @QueryMap Map<String,String> alldata, Callback<HttpResponse<Product>> cb);




    @GET("/index.php/api/allproducts")
    void getallproductslistingresponse(@Header("API_KEY") String apikey,
                                  @Header("APP_VERSION") String appversion,
                                  @Header("CONFIG_VERSION") String config,
                                  @Header("AUTH_TOKEN") String auth,
                                  @QueryMap Map<String,String> alldata, Callback<HttpResponse<Product>> cb);






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
