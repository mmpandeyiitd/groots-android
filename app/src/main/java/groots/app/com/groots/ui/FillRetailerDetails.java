package groots.app.com.groots.ui;

import android.animation.StateListAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import groots.app.com.groots.R;
import groots.app.com.groots.interfaces.API_Interface;
import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.HttpResponseofProducts;
import groots.app.com.groots.pojo.user_profile;
import groots.app.com.groots.utilz.Http_Urls;
import groots.app.com.groots.utilz.Utilz;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by aakash on 10/2/17.
 */

public class FillRetailerDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {



    ArrayList<user_profile> retailerdetails = new ArrayList<>();

    Button btnBack , btnNext;
    String registrationStatus;
    Context context;
    String addressDelivery,tanNumber,personNamee,pan_number,City,State,alternate_email,pin_code,web_site;
    String spinPayMode,spinPayFreq,statee;
    Utilz utilz;
    TextView cName,contact,email,personName;
    LinearLayout cdsignup;
    int pos,pos1,pos2;


    EditText deliveryAddress,tanNo,city,alternateEmail,pinCode,webSite,panNo;
    Spinner spinnerPaymentMode,spinnerPaymentFreq,spinnerState;


    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.retailer_details);

        context = FillRetailerDetails.this;

        deliveryAddress = (EditText) findViewById(R.id.delivery_add);
        city =(EditText) findViewById(R.id.city);
       spinnerState = (Spinner) findViewById(R.id.spinner_state);
        alternateEmail = (EditText) findViewById(R.id.alt_email);
        pinCode = (EditText) findViewById(R.id.pincode);
        webSite =(EditText) findViewById(R.id.website);
        panNo = (EditText) findViewById(R.id.pan_no);
        cdsignup = (LinearLayout) findViewById(R.id.cdsignUp);
        cName = (TextView) findViewById(R.id.cName);
        contact = (TextView)findViewById(R.id.Contact);
        email = (TextView) findViewById(R.id.email_id);








        spinnerPaymentMode = (Spinner) findViewById(R.id.spinner_payment_mode);
        spinnerPaymentFreq = (Spinner) findViewById(R.id.spinner_payment_frequency);
        tanNo = (EditText) findViewById(R.id.tan_no);
        personName =(TextView) findViewById(R.id.cName);
        btnBack =(Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);


        utilz = new Utilz();


        callretailerdetailsAPI();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            onBackPressed();

            }
        });






        //spinnerPaymentMode.setOnItemClickListener();

        List<String> categor = new ArrayList<String>();
        categor.add("Select State");
        categor.add("Delhi");
        categor.add("Haryana");
        categor.add("Uttar Pradesh");





        List<String> categorie = new ArrayList<String>();
        categorie.add("Select Frequency");
        categorie.add("Daily");
       /* categorie.add("Weekly");
        categorie.add("Monthly");*/


        List<String> categories = new ArrayList<String>();
        categories.add("Select Mode");
        categories.add("Online");
        categories.add("Offline");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterFreq = new ArrayAdapter<String>(this, R.layout.spinner_item, categorie);
        ArrayAdapter<String> dataAdapterState = new ArrayAdapter<String>(this, R.layout.spinner_item, categor);
        ArrayAdapter<String> dataAdapterMode = new ArrayAdapter<String>(this, R.layout.spinner_item, categories);

        spinnerState.getBackground().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_ATOP );
        spinnerState.setPrompt("Select State");

        spinnerPaymentFreq.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        spinnerPaymentFreq.setPrompt("Select Frequency");
        spinnerPaymentMode.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        spinnerPaymentMode.setPrompt("Select Payment Mode");

        // Drop down layout style - list view with radio button
        dataAdapterMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterFreq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerPaymentMode.setAdapter(dataAdapterMode);
        spinnerPaymentFreq.setAdapter(dataAdapterFreq);
        spinnerState.setAdapter(dataAdapterState);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addressDelivery = deliveryAddress.getText().toString();
                tanNumber = tanNo.getText().toString();
                personNamee = personName.getText().toString();

                pan_number = panNo.getText().toString();
                City = city.getText().toString();

                alternate_email = alternateEmail.getText().toString();
                pin_code = pinCode.getText().toString();
                web_site = webSite.getText().toString();

                 spinPayMode = spinnerPaymentMode.getSelectedItem().toString();
                 spinPayFreq = spinnerPaymentFreq.getSelectedItem().toString();
                  statee = spinnerState.getSelectedItem().toString();

                if (spinPayFreq == "Select Frequency"){

                    Toast.makeText(context,"Please Select Frequency",Toast.LENGTH_LONG).show();

                }
                else if (addressDelivery.length() == 0 || /*personNamee.length() == 0 ||*/ tanNumber.length() == 0){
                    Toast.makeText(context , "Fields can't be empty.",Toast.LENGTH_SHORT).show();

                }
                else if (statee == "Select State"){
                    Toast.makeText(context,"Please select state.",Toast.LENGTH_LONG).show();
                }
                else if (spinPayMode == "Select Mode"){
                    Toast.makeText(context,"Please Select Mode",Toast.LENGTH_LONG).show();

                }
                else {

                    if (utilz.isInternetConnected(context)){
                        callFillRetailerDetailsAPI();



                    }
                    else {
                        Toast.makeText(context,"Please check your internet connection.",Toast.LENGTH_LONG).show();


                    }
                }


            }
        });


    }

    public void callFillRetailerDetailsAPI(){

        HashMap hashmap = new HashMap();
        hashmap.put("address",addressDelivery);
        hashmap.put("tanNo",tanNumber);
        hashmap.put("personName",personNamee);
        hashmap.put("modeOfPayment",spinPayMode);
        hashmap.put("modeOfFreq",spinPayFreq);
        hashmap.put("panNo",pan_number);
        hashmap.put("website",web_site);
        hashmap.put("alternateEmail",alternate_email);
        hashmap.put("city",City);
        hashmap.put("state",statee);
        hashmap.put("pinCode",pin_code);
        hashmap.put("retailerGradeType","D");


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Http_Urls.sBaseUrl).setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);

        apiInterface.getFillRetailerDetails(Utilz.apikey, Utilz.app_version, Utilz.config_version, AuthToken,hashmap, new Callback<HttpResponse>() {
            @Override
            public void success(HttpResponse httpResponse, Response response) {


                int status = httpResponse.status;
               // int status = Integer.parseInt(stat.substring(0,1));

                if (status == -1){

                    Toast.makeText(context,"Internal Server Error.Please try again later.",Toast.LENGTH_LONG).show();

                }
                else if (status == 0){
                    Toast.makeText(context,"Internal Server Error.Please try again later.",Toast.LENGTH_LONG).show();

                }
                else if (status == 1){


                    Toast.makeText(context,"Your information has been saved.",Toast.LENGTH_SHORT).show();
                    SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);


                    registrationStatus = prefs.getString("registrationStatus",null);

                    if (!registrationStatus.equals("Complete")){

                        callchangeRegStatusAPI();
                    }


                    Intent intent = new Intent(FillRetailerDetails.this,Landing_Update.class);
                   // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


                    startActivity(intent);
                    finish();

                }

            }

            @Override
            public void failure(RetrofitError error) {


                Toast.makeText(context,"Something went wrong.",Toast.LENGTH_LONG);


            }
        });



    }






    void callchangeRegStatusAPI(){
        HashMap hashm = new HashMap();
        hashm.put("makeActive","yes");


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Http_Urls.sBaseUrl).setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);


        apiInterface.getChangeRegStatusResponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, AuthToken,hashm, new Callback<HttpResponse>() {
            @Override
            public void success(HttpResponse httpResponse, Response response) {

                int status = httpResponse.status;

                if (status == -1){

                    Snackbar snackbar = Snackbar.make(cdsignup, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if  (status == 0){
                    Snackbar snackbar = Snackbar.make(cdsignup, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if  (status == 1){

                    Toast.makeText(context,"You have done your complete registration.",Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", context.MODE_PRIVATE).edit();
                    editor.putString("registrationStatus","Complete");
                    editor.commit();



                }



            }

            @Override
            public void failure(RetrofitError error) {

                Snackbar snackbar = Snackbar.make(cdsignup, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();


            }
        });




    }






    void callretailerdetailsAPI(){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);
        apiInterface.getretailerdetailsresponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, AuthToken, new Callback<HttpResponse<user_profile>>(){
            @Override
            public void success ( HttpResponse httpResponse , Response response ){


                int status = httpResponse.status;

                if (status == -1){


                    Toast.makeText(context,"Internal Server Error.Please try again later.",Toast.LENGTH_LONG).show();

                 /*   String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();*/

                } else if (status == 0) {


                    Toast.makeText(context,"Internal Server Error.Please try again later.",Toast.LENGTH_LONG).show();
                   /* String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();*/


                }
                else if (status == 1){


                    if (retailerdetails.size() == 0) {
                        retailerdetails = (ArrayList<user_profile>) httpResponse.data.responseData.docs;
                    }


                    String orgName = retailerdetails.get(0).retailerName;
                    String contac = retailerdetails.get(0).contactNoo;
                    String email_i = retailerdetails.get(0).email_id;
                    String tanNoo = retailerdetails.get(0).tanNo;
                    String panNoo = retailerdetails.get(0).panNo;
                    String cityy = retailerdetails.get(0).city;
                    String state = retailerdetails.get(0).statee;
                    String address = retailerdetails.get(0).addresss;
                    String paymentMode = retailerdetails.get(0).paymentMode;
                    String paymentFreq = retailerdetails.get(0).paymentFreq;
                    String alternateEmaill = retailerdetails.get(0).alternateEmail;
                    String website = retailerdetails.get(0).website;
                    String pincode = retailerdetails.get(0).pincode;



                    if (state != null) {
                        if (state.equals("Delhi")) {
                            pos = 1;
                        } else if (state.equals("Haryana")) {
                            pos = 2;
                        } else if (state.equals("Uttar Pradesh")) {
                            pos = 3;

                        }
                    }



                    if (paymentMode != null) {

                        if (paymentMode.equals("online")) {
                            pos1 = 1;
                        } else if (paymentMode.equals("offline")) {
                            pos2 = 2;
                        }
                    }

                    if (paymentFreq!= null) {

                        if (paymentFreq.equals("daily")) {
                            pos2 = 1;
                        }
                       /* if (paymentFreq.equals("weekly")) {
                            pos2 = 2;
                        }
                        if (paymentFreq.equals("monthly")) {
                            pos2 = 3;
                        }*/
                    }





                    cName.setText(orgName);
                    contact.setText(contac);

                      email.setText(email_i);
                    deliveryAddress.setText(address);
                    tanNo.setText(tanNoo);
                    city.setText(cityy);
                    alternateEmail.setText(alternateEmaill);
                    pinCode.setText(pincode);
                    webSite.setText(website);
                    panNo.setText(panNoo);


                    spinnerPaymentFreq.setSelection(pos2);
                    spinnerPaymentMode.setSelection(pos1);
                    spinnerState.setSelection(pos);





                   /* if (Status == true){
                        //String orderId =
                        //orderfeedback.orderId;
                        Intent i = new Intent(Splash.this, RateUs.class);
                        i.putExtra("ID",O_id);
                        i.putExtra("date",datee);
                        startActivity(i);
                        finish();

                    }
                    else {


                        Intent i = new Intent(Splash.this, Landing_Update.class);
                        startActivity(i);
                        finish();
                    }*/

                    // if (httpResponse.data.responseData.docs.size() != 0 ){



                    //}



                }









            }









            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(context,"Internal Server Error.Please try again later.",Toast.LENGTH_LONG).show();

                //progressMobile.setVisibility(View.INVISIBLE);
               /* Snackbar snackbar = Snackbar.make(, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();
*/
            }

        });





    }











    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void onBackPressed(){
        super.onBackPressed();


    }

}
