package groots.app.com.groots.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.squareup.okhttp.OkHttpClient;

import java.util.HashMap;

import groots.app.com.groots.R;
import groots.app.com.groots.interfaces.API_Interface;
import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.signUpResponse;
import groots.app.com.groots.utilz.Http_Urls;
import groots.app.com.groots.utilz.Utilz;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by aakash on 16/12/16.
 */

public class signUpPage extends AppCompatActivity  {

    LinearLayout backbtn , cdsignUp;
    EditText Name , cName , designation , contactNo , email_id;
    Button btnSignU;
    Context context;





    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

backbtn = (LinearLayout) findViewById(R.id.backbtn);
       Name = (EditText) findViewById(R.id.Name);

        cName = (EditText) findViewById(R.id.cName);
        designation = (EditText) findViewById(R.id.Designation);
        contactNo = (EditText) findViewById(R.id.Contact);
        email_id = (EditText) findViewById(R.id.email_id);
        btnSignU = (Button) findViewById(R.id.btnSubmit);

        cdsignUp = (LinearLayout) findViewById(R.id.cdsignUp);



        ((LinearLayout)findViewById(R.id.backbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });


        btnSignU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String namee = Name.getText().toString();

                String compName = cName.getText().toString();
                String designationn = designation.getText().toString();

                String contact = contactNo.getText().toString();

                String email = email_id.getText().toString();


                Utilz utilz = new Utilz();

                if (namee.length() <= 0 || compName.length() <= 0 || designationn.length() <= 0 || contact.length() <= 2){
                    Snackbar snackbar = Snackbar.make(cdsignUp, "Name ,OrganisationName ,Designation and ContactNo cannot be empty.", Snackbar.LENGTH_SHORT);
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

                    else {

                         HashMap h = new HashMap();


                         h.put("name", namee);
                         h.put("orgName", compName);
                         h.put("designation", designationn);
                         h.put("contactNo", contact);
                         h.put("emailId", email);


                         callSignUpAPI(h);
                     }


                }
                else if ( !(contact.length() >= 8 && contact.length() <= 13)){
                    Snackbar snackbar = Snackbar.make(cdsignUp, "Please provide a valid contact number.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();



                }


                else {



                    /*if (utilz.isInternetConnected(context)){



                        Snackbar snackbar = Snackbar.make(cdsignUp, "Please check the internet connection", Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();
                    }

                    else {*/
                        HashMap h = new HashMap();


                        h.put("name", namee);
                        h.put("orgName", compName);
                        h.put("designation", designationn);
                        h.put("contactNo", contact);
                        h.put("emailId", email);


                        callSignUpAPI(h);
                    //}
                }


            }
        });





    }





    void callSignUpAPI(HashMap hashMap){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);




      /*  SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);*/


        apiInterface.getsignupresponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, hashMap, new Callback<HttpResponse<signUpResponse>>() {
            @Override
            public void success(HttpResponse httpResponse, Response response) {

                // progressMobile.setVisibility(View.INVISIBLE);

                // btnSignIn.setEnabled(true);


                if (httpResponse != null) {
                    int status = httpResponse.status;
                    if (status == -1) {
                        // btnSignIn.setText("Sign In");
                        String msg = httpResponse.errors.get(0).toString();
                        Snackbar snackbar = Snackbar.make(cdsignUp, msg, Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    } else if (status == 0) {
                        // btnSignIn.setText("Sign In");
                        String msg = httpResponse.errors.get(0).toString();
                        Snackbar snackbar = Snackbar.make(cdsignUp, msg, Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    } else if (status == 1) {


                        Intent inte = new Intent(signUpPage.this, Splash.class);

                        inte.putExtra("messge", "show");
                        startActivity(inte);
                        finish();




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





}
