package groots.app.com.groots.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import groots.app.com.groots.R;
import groots.app.com.groots.interfaces.API_Interface;
import groots.app.com.groots.pojo.HttpResponseObject;
import groots.app.com.groots.pojo.HttpResponseofProducts;
import groots.app.com.groots.pojo.LoginData;
import groots.app.com.groots.pojo.otpResponse;
import groots.app.com.groots.pojo.user_profile;
import groots.app.com.groots.utilz.Applicationclass;
import groots.app.com.groots.utilz.Http_Urls;
import groots.app.com.groots.utilz.TagManagerEvent;
import groots.app.com.groots.utilz.Utilz;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by aakash on 16/12/16.
 */

public class signUpPage extends AppCompatActivity  {

    LinearLayout backbtn , cdsignUp;
    user_profile retailer = new user_profile();
    otpResponse otpRespons = new otpResponse();
   // ArrayList<user_profile> retailerdetails = new ArrayList<>();

    user_profile retailerdetails = new user_profile();


   // List<user_profile> retailer = new ArrayList<>();


    String otp;
    String user_id;
    EditText Name , cName , designation , contactNo , email_id , new_password,confirm_password;
    String new_pass,confirm_pass,email,compName,contact;
    Button btnSignU;
    Dialog dialog;
    Context context;
    PasswordValidator passwordValidator;


    SharedPreferences prefs;

    android.os.Handler handler = new android.os.Handler();
    Runnable runnable;
    private static final long TIMEOUT_FOR_CONTAINER_OPEN_MILLISECONDS = 4000;
    private static final String CONTAINER_ID = "GTM-K43QXDL";
    private static final String  screenName = "signup";
    private TagManager tagManager;
    private Tracker mTracker;
    Applicationclass application;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

backbtn = (LinearLayout) findViewById(R.id.backbtn);
        new_password = (EditText) findViewById(R.id.new_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
      // Name = (EditText) findViewById(R.id.Name);

        cName = (EditText) findViewById(R.id.cName);
       // designation = (EditText) findViewById(R.id.Designation);
        contactNo = (EditText) findViewById(R.id.Contact);
        email_id = (EditText) findViewById(R.id.email_id);
        btnSignU = (Button) findViewById(R.id.btnSubmit);

        cdsignUp = (LinearLayout) findViewById(R.id.cdsignUp);


        passwordValidator = new PasswordValidator();

        context = signUpPage.this;



        ((LinearLayout)findViewById(R.id.backbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });


        btnSignU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               new_pass = new_password.getText().toString();
                 confirm_pass = confirm_password.getText().toString();

                int length_new_pass = new_pass.length();





               // String namee = Name.getText().toString();

                 compName = cName.getText().toString();
               // String designationn = designation.getText().toString();

                 contact = contactNo.getText().toString().replace("+91","");

                 email = email_id.getText().toString();



                retailer.email_id = email;
                retailer.orgName = compName;
                retailer.contactNo = contact;
                retailer.newPassword = new_pass;
                retailer.confirmPassword = confirm_pass;




                Utilz utilz = new Utilz();

                if (new_pass.length() <= 0 || compName.length() <= 0 || confirm_pass.length() <= 0 || contact.length() <= 2){
                    Snackbar snackbar = Snackbar.make(cdsignUp, "OrganisationName ,Password and ContactNo cannot be empty.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
               else if (email.length() > 0 ) {
                     if (!utilz.isValidEmail1(email)) {
                        Snackbar snackbar = Snackbar.make(cdsignUp, "Please provide a valid email id", Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();
                    }

                   else if ( !(contact.length() >= 8 && contact.length() <= 13)){
                        Snackbar snackbar = Snackbar.make(cdsignUp, "Please provide a valid contact number.", Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();



                    }
                     else if (!new_pass.equals(confirm_pass)){

                         Snackbar snackbar = Snackbar.make(cdsignUp, "Passwords don't match.", Snackbar.LENGTH_SHORT);
                         snackbar.setActionTextColor(Color.WHITE);
                         View snackbarView = snackbar.getView();
                         snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                         snackbar.show();


                     }
                     else if (!(new_pass.contains("@")) && !(new_pass.contains("#")) && !(new_pass.contains("$")) && !(new_pass.contains("-")) && !(new_pass.contains("_"))){

                         Snackbar snackbar = Snackbar.make(cdsignUp, "Password should contain atleast 1 special character from @#$_- ", Snackbar.LENGTH_SHORT);
                         snackbar.setActionTextColor(Color.WHITE);
                         View snackbarView = snackbar.getView();
                         snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                         snackbar.show();

                     }
                     else if (!(passwordValidator.validate(new_pass))){
                         Snackbar snackbar = Snackbar.make(cdsignUp, "Password should contain 1 uppercase,1 lowercase ,1 character from #$_-% , 1 numeric \n character and atleast 6 characters long.", Snackbar.LENGTH_LONG);
                         snackbar.setActionTextColor(Color.WHITE);

                         View snackbarView = snackbar.getView();

                         TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                         textView.setMaxLines(3);
                         snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                         snackbar.show();

                     }

                     /*else if (!(new_pass.matches("[0-9A-Za-z@#$-_]"))|| !(new_pass.matches("[0-9]")) || !(new_pass.matches("[A-Za-z]")) || !(new_pass.matches("[@#$_-]")) ){
                         Snackbar snackbar = Snackbar.make(cdsignUp, "Password should be alphanumeric and should contain atleast 1 special character from @#$_- ", Snackbar.LENGTH_SHORT);
                         snackbar.setActionTextColor(Color.WHITE);
                         View snackbarView = snackbar.getView();
                         snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                         snackbar.show();

                     }*/

                    else {



                         callSignUpAPI();
                     }


                }
                else if ( !(contact.length() >= 8 && contact.length() <= 13)){
                    Snackbar snackbar = Snackbar.make(cdsignUp, "Please provide a valid contact number.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();



                }

                else if (!(new_pass.equals(confirm_pass)) ){

                    Snackbar snackbar = Snackbar.make(cdsignUp, "Passwords don't match.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }

                else if (!(new_pass.contains("@")) && !(new_pass.contains("#")) && !(new_pass.contains("$")) && !(new_pass.contains("-")) && !(new_pass.contains("_"))){

                    Snackbar snackbar = Snackbar.make(cdsignUp, "Password should contain atleast 1 special character from @#$_- ", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                }



                else if (!(passwordValidator.validate(new_pass))){
                    Snackbar snackbar = Snackbar.make(cdsignUp, "Password should contain 1 uppercase,1 lowercase ,1 character from #$_-% , 1 numeric \n character and atleast 6 characters long.", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setMaxLines(3);

                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                }

               /* Pattern p = Pattern.compile("[a-zA-Z0-9@#$_-]");    || !(new_pass.matches("[0-9]")) || !(new_pass.matches("[A-Za-z]")) || !(new_pass.matches("[@#$_-]"))*/



               /* else if (!(new_pass.matches("[a-z]"))  ){
                    Snackbar snackbar = Snackbar.make(cdsignUp, "Password should be alphanumeric and should contain atleast 1 special character from @#$_- ", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                }
*/


                else {



                    /*if (utilz.isInternetConnected(context)){



                        Snackbar snackbar = Snackbar.make(cdsignUp, "Please check the internet connection", Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();
                    }

                    else {*/
                       /* HashMap h = new HashMap();



                        h.put("confirmPassword", confirm_pass);
                        h.put("newPassword", new_pass);
                        h.put("orgName", compName);
                        h.put("contactNo", contact);
                        h.put("emailId", email);*/


                        callSignUpAPI();
                    //}
                }


            }
        });





    }



    void callOtpCheckAPI(String otp){

        HashMap hash = new HashMap();
        hash.put("userId",user_id);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);

apiInterface.getOtpcheckResponse(Utilz.apikey, Utilz.app_version, Utilz.config_version,hash,otp,new Callback<HttpResponseofProducts>(){
   @Override
    public void success(HttpResponseofProducts httpResponse ,Response response){


       String stat = httpResponse.status;
       int status = Integer.parseInt(stat.substring(0,1));


       if (status == 5) {
           // btnSignIn.setText("Sign In");
           //String msg = httpResponse.errors.toString();
           Snackbar snackbar = Snackbar.make(cdsignUp, "Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);
           snackbar.setActionTextColor(Color.WHITE);
           View snackbarView = snackbar.getView();
           snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
           snackbar.show();

       } else if (status == 4) {
           // btnSignIn.setText("Sign In");
          // String msg = httpResponse.errors.toString();
           Snackbar snackbar = Snackbar.make(cdsignUp, "Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);
           snackbar.setActionTextColor(Color.WHITE);
           View snackbarView = snackbar.getView();
           snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
           snackbar.show();

       } else if (status == 2) {

          // Toast.makeText(context,"Your registration is complete. Fill your basic details here.",Toast.LENGTH_LONG).show();

        HashMap hashMap = new HashMap();
                       // hashMap.put("email",email);
                        hashMap.put("contact",contact);
                        hashMap.put("password",confirm_pass);

                          callLoginAPI(hashMap);
           Intent intent = new Intent(signUpPage.this,SampleActivity.class);
           startActivity(intent);
           finish();


       }









   }
    @Override
    public void failure (RetrofitError e){

        Snackbar snackbar = Snackbar.make(cdsignUp, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();

    }


});

    }





    void callSignUpAPI(){





        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);




      /*  SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);*/


        apiInterface.getsignupresponse(Utilz.apikey, Utilz.app_version, Utilz.config_version,"application/json",retailer, new Callback<HttpResponseObject<user_profile>>() {
            @Override
            public void success(HttpResponseObject httpResponse, Response response) {

                // progressMobile.setVisibility(View.INVISIBLE);

                // btnSignIn.setEnabled(true);


                if (httpResponse != null) {
                    String statu = httpResponse.status;
                    int status = Integer.parseInt(statu.substring(0,1));
                    if (status == 5) {
                        // btnSignIn.setText("Sign In");
                        //String msg = httpResponse.errors.toString();
                        Snackbar snackbar = Snackbar.make(cdsignUp, "Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    } else if (status == 4) {
                        // btnSignIn.setText("Sign In");
                       // String msg = httpResponse.errors.toString();
                        Snackbar snackbar = Snackbar.make(cdsignUp, "Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    } else if (status == 2) {


                        retailerdetails =(user_profile) httpResponse.data;
                         user_id = retailerdetails.user_id;








                      /*  HashMap hashMap = new HashMap();
                       // hashMap.put("email",email);
                        hashMap.put("contact",contact);
                        hashMap.put("password",confirm_pass);

                          callLoginAPI(hashMap);*/

                     //   Toast.makeText(context,"Your registration is complete. Sample of Products is here.",Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(signUpPage.this,SampleActivity.class);
//                        startActivity(intent);
//                        finish();

                         dialog = new Dialog(signUpPage.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.otp_activity_dialog);
                        dialog.show();
                        TextView otpDone = (TextView) dialog.findViewById(R.id.otp_done);


//int a =enterOtp.getSelectionStart();
                        // enterOtp.setSelection(a+2);



                        otpDone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EditText enterOtp = (EditText) dialog.findViewById(R.id.enterOtp);


                                otp = enterOtp.getText().toString().trim();

                                if (otp.length() != 4 ){

                                    Toast.makeText(context,"OTP is 4 digits long.",Toast.LENGTH_LONG).show();
                                }
                                else if (otp.length() == 4){

                                 otpRespons.otp = otp;

                                    callOtpCheckAPI(otp);
                                }
                            }
                        });







                      /*  Intent inte = new Intent(signUpPage.this, Splash.class);

                        inte.putExtra("messge", "show");
                        startActivity(inte);
                        finish();*/




                            /*if (submitFeedback.size() == 0) {
                                submitFeedback = (ArrayList<SubmitFeedback>) httpResponse;
                            }*/

                        // int statuscode = submitFeedback.get(0).Status;

                           /* if (statuscode == 1) {*/


                        // }


                    }

                               /* editor.putString("Retailer_Name", loginData.getData().getRetailerName());
                                editor.putString("UserName", loginData.getData().getName());
                                editor.putString("User_Id", loginData.getData().getUserId());
                                editor.commit();*/
                    //   btnSignIn.setText("Success");

                    //HashMap hashMap = new HashMap();
                    //hashMap.put("email", strEmail);

                    //callfeedbackresponseAPI();

                                /*Intent i = new Intent(Splash.this, Landing_Update.class);
                                startActivity(i);
                                finish();*/
                    // Toast.makeText(Splash.this,"You have logged in successfully!", Toast.LENGTH_SHORT).show();
                    else {
                        //    btnSignIn.setText("Sign In");
                        Snackbar snackbar = Snackbar.make(cdsignUp, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();
                    }


                }



                else {
                    //btnSignIn.setEnabled(true);
                    // btnSignIn.setText("Sign In");
                    Snackbar snackbar = Snackbar.make(cdsignUp, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }


            }








            @Override
            public void failure(RetrofitError error){
            // progressMobile.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdsignUp, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();
            }

        });


    }









    void callLoginAPI(HashMap hashMap) {
     /*   TagManagerEvent.pushOpenScreenEvent(context, screenName);
        tagManager.getInstance(this).getDataLayer().pushEvent("openScreen", DataLayer.mapOf("screenName", screenName));

        tagManager.getInstance(this).getDataLayer().push("abc", "gogroots");*/

       // progressMobile.setVisibility(View.VISIBLE);

        //isLoadingDone = false;
        //  btnSignIn.setText("Loading..");


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);


        apiInterface.getloginResponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, hashMap, new Callback<LoginData>() {
            @Override
            public void success(LoginData loginData, Response response) {

               // progressMobile.setVisibility(View.INVISIBLE);

               // btnSignIn.setEnabled(true);

                if (loginData != null) {

                  int  status = loginData.getStatus();

                    if (status == -1) {
                        // btnSignIn.setText("Sign In");
                       Toast.makeText(context,"nothing",Toast.LENGTH_LONG).show();

                    } else if (status == 0) {
                        // btnSignIn.setText("Sign In");
                        Toast.makeText(context,"nothing",Toast.LENGTH_LONG).show();

                    } else if (status == 1) {
                        if (loginData.getData() != null) {

                            SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                            String authToken = "";
                            for (Header header : response.getHeaders()) {
                                if (header.getName().equals("AUTH_TOKEN"))
                                    authToken = header.getValue();
                                editor.putString("AuthToken", authToken);
                                editor.commit();
                            }

                            editor.putString("Retailer_Name", loginData.getData().getRetailerName());
                            editor.putString("UserName", loginData.getData().getName());
                            editor.putString("User_Id", loginData.getData().getUserId());
                            editor.putString("registrationStatus",loginData.getData().regStatus);
                            editor.putString("Check", "name");

                            editor.commit();



                            //   btnSignIn.setText("Success");

                            //HashMap hashMap = new HashMap();
                            //hashMap.put("email", strEmail);




                            // callfeedbackresponseAPI();

                            /*Intent i = new Intent(Splash.this, Landing_Update.class);
                            startActivity(i);
                            finish();*/

                            Toast.makeText(signUpPage.this,"You have logged in successfully!", Toast.LENGTH_LONG).show();
                        } else {
                            //    btnSignIn.setText("Sign In");
                            Toast.makeText(context,"nothing",Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                   // btnSignIn.setEnabled(true);
                    // btnSignIn.setText("Sign In");
                    Toast.makeText(context,"nothing",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
               // progressMobile.setVisibility(View.INVISIBLE);
                Toast.makeText(context,"nothing",Toast.LENGTH_LONG).show();
               // btnSignIn.setEnabled(true);
                //  btnSignIn.setText("Sign In");
            }
        });
    }








    public class PasswordValidator{

        private Pattern pattern;
        private Matcher matcher;

        private static final String PASSWORD_PATTERN =
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$_-]).{5,20})";

        public PasswordValidator(){
            pattern = Pattern.compile(PASSWORD_PATTERN);
        }

        /**
         * Validate password with regular expression
         * @param password password for validation
         * @return true valid password, false invalid password
         */
        public boolean validate(final String password){

            matcher = pattern.matcher(password);
            return matcher.matches();

        }
    }





}
