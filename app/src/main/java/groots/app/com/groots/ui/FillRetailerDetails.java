package groots.app.com.groots.ui;

import android.animation.StateListAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
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
import groots.app.com.groots.databases.DbHelper;
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

    LinearLayout btnBack ;
    View view1,view2,view3,view4,view5,view6,view7,view8,view9,view10,view11,view12,view13,view14;
    int count1,count2,count3,count4,count5,count6,count7,count8,count9,count10,count11,count12;
    ImageView submit;
    DbHelper dbHelper;
    String registrationStatus;
    Context context;
    String addressDelivery,tanNumber,personNamee,pan_number,City,State,alternate_email,pin_code,web_site;
    String spinPayMode,spinPayFreq,statee,spinBusType,gradeType;
    Utilz utilz;
    TextView cName,contact,personName,logout,hide_company,hide_contact,hide_email,hide_alt_email,hide_del_address,hide_city,hide_state,hide_pin_code,hide_payment_mode,hide_payment_freq,hide_website,hide_tan_no,hide_pan_no,hide_business_type;
    LinearLayout cdsignup;
    int pos,pos1,pos2,pos3;


    EditText deliveryAddress,tanNo,city,alternateEmail,pinCode,webSite,panNo,email;
    Spinner spinnerPaymentMode,spinnerPaymentFreq,spinnerState,spinnerBusinessType;


    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_design_retailer_details);

        context = FillRetailerDetails.this;

        logout = (TextView) findViewById(R.id.logout);

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
        email = (EditText) findViewById(R.id.email_id);
        spinnerBusinessType = (Spinner) findViewById(R.id.spinner_business_type);


        view1 = (View) findViewById(R.id.view1);
        view2 = (View) findViewById(R.id.view2);
        view3 = (View) findViewById(R.id.view3);
        view4 = (View) findViewById(R.id.view4);
        view5 = (View) findViewById(R.id.view5);
        view6 = (View) findViewById(R.id.view6);
        view7 = (View) findViewById(R.id.view7);
        view8 = (View) findViewById(R.id.view8);
        view9 = (View) findViewById(R.id.view9);
        view10 = (View) findViewById(R.id.view10);
        view11= (View) findViewById(R.id.view11);
        view12 = (View) findViewById(R.id.view12);
        view13 = (View) findViewById(R.id.view13);
        view14 = (View) findViewById(R.id.view14);

        hide_company = (TextView) findViewById(R.id.hide_company);
        hide_contact = (TextView) findViewById(R.id.hide_contact);
        hide_email = (TextView) findViewById(R.id.hide_email);
        hide_alt_email = (TextView) findViewById(R.id.hide_alt_email);
        hide_del_address = (TextView) findViewById(R.id.hide_del_address);
        hide_city = (TextView) findViewById(R.id.hide_city);
        hide_state = (TextView) findViewById(R.id.hide_state);
        hide_pin_code = (TextView) findViewById(R.id.hide_pin_code);
        hide_payment_mode = (TextView) findViewById(R.id.hide_payment_mode);
        hide_payment_freq = (TextView) findViewById(R.id.hide_payment_freq);
        hide_website = (TextView) findViewById(R.id.hide_website);
        hide_tan_no = (TextView) findViewById(R.id.hide_tan_no);
        hide_pan_no = (TextView) findViewById(R.id.hide_pan_no);
        hide_business_type = (TextView) findViewById(R.id.hide_business_type);


        hide_company.setVisibility(View.GONE);
        hide_contact.setVisibility(View.GONE);
        hide_email.setVisibility(View.GONE);
        hide_alt_email.setVisibility(View.GONE);
        hide_del_address.setVisibility(View.GONE);
        hide_city.setVisibility(View.GONE);
        hide_pin_code.setVisibility(View.GONE);
        hide_website.setVisibility(View.GONE);
        hide_pan_no.setVisibility(View.GONE);
        hide_tan_no.setVisibility(View.GONE);
       // hide_business_type.setVisibility(View.GONE);


        spinnerPaymentMode = (Spinner) findViewById(R.id.spinner_payment_mode);
        spinnerPaymentFreq = (Spinner) findViewById(R.id.spinner_payment_frequency);
        tanNo = (EditText) findViewById(R.id.tan_no);
        personName =(TextView) findViewById(R.id.cName);
        btnBack =(LinearLayout) findViewById(R.id.btnBack);
        submit = (ImageView) findViewById(R.id.submit);


        utilz = new Utilz();

        dbHelper = DbHelper.getInstance(context);
        dbHelper.createDb(false);
















        callretailerdetailsAPI();



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutPopUp();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            onBackPressed();

            }
        });








        cName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                hide_company.setVisibility(View.GONE);




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hide_company.setVisibility(View.VISIBLE);
                if (count1 == 0) {
                    hide_company.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                    count1++;
                }

                view1.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
               // hide_company.setVisibility(View.VISIBLE);
                view1.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

                if (cName.getText().length() == 0){
                    hide_company.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down));
                    hide_company.setVisibility(View.INVISIBLE);
                    count1 = 0;
                    view1.setBackgroundColor(getResources().getColor(R.color.newhintcolor));

                }


            }
        });


        contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                hide_contact.setVisibility(View.GONE);




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hide_contact.setVisibility(View.VISIBLE);
                if (count2 == 0) {
                    hide_contact.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                    count2++;
                }
                view2.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //hide_contact.setVisibility(View.VISIBLE);
                view2.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));
                if (contact.getText().length() == 0){
                    hide_contact.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down));
                    contact.setVisibility(View.INVISIBLE);
                    count2 = 0;
                    view2.setBackgroundColor(getResources().getColor(R.color.newhintcolor));

                }


            }
        });


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                hide_email.setVisibility(View.GONE);




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hide_email.setVisibility(View.VISIBLE);
                if (count3 == 0) {
                    hide_email.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                    count3++;
                }
                view3.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //hide_email.setVisibility(View.VISIBLE);
                view3.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));
                if (email.getText().length() == 0){
                    hide_email.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down));
                    hide_email.setVisibility(View.INVISIBLE);
                    count3 = 0;
                    view3.setBackgroundColor(getResources().getColor(R.color.newhintcolor));

                }


            }
        });


        alternateEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                hide_alt_email.setVisibility(View.GONE);




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hide_alt_email.setVisibility(View.VISIBLE);
                if (count4 == 0) {
                    hide_alt_email.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                    count4++;
                }
                view4.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //hide_alt_email.setVisibility(View.VISIBLE);
                view4.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

                if (alternateEmail.getText().length() == 0){
                    hide_alt_email.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down));
                    hide_alt_email.setVisibility(View.INVISIBLE);
                    count4 = 0;
                    view4.setBackgroundColor(getResources().getColor(R.color.newhintcolor));

                }


            }
        });


        deliveryAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                hide_del_address.setVisibility(View.GONE);




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hide_del_address.setVisibility(View.VISIBLE);
                if (count5 == 0) {
                    hide_del_address.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                    count5++;
                }
                view5.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
               // hide_del_address.setVisibility(View.VISIBLE);
                view5.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

                if (deliveryAddress.getText().length() == 0){
                    hide_del_address.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down));
                    hide_del_address.setVisibility(View.INVISIBLE);
                    count5 = 0;
                    view5.setBackgroundColor(getResources().getColor(R.color.newhintcolor));

                }


            }
        });


        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                hide_city.setVisibility(View.GONE);




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hide_city.setVisibility(View.VISIBLE);
                if (count6 == 0) {
                    hide_city.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                    count6++;
                }

                view6.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
               // hide_city.setVisibility(View.VISIBLE);
                view6.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

                if (city.getText().length() == 0){
                    hide_city.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down));
                    hide_city.setVisibility(View.INVISIBLE);
                    count6 = 0;
                    view6.setBackgroundColor(getResources().getColor(R.color.newhintcolor));

                }


            }
        });


        pinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                hide_pin_code.setVisibility(View.GONE);




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hide_pin_code.setVisibility(View.VISIBLE);
                if (count7 == 0) {
                    hide_pin_code.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                    count7++;
                }
                view8.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                hide_pin_code.setVisibility(View.VISIBLE);
                view8.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

                if (pinCode.getText().length() == 0){
                    hide_pin_code.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down));
                    hide_pin_code.setVisibility(View.INVISIBLE);
                    count7 = 0;
                    view7.setBackgroundColor(getResources().getColor(R.color.newhintcolor));

                }


            }
        });


        webSite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                hide_website.setVisibility(View.GONE);




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hide_website.setVisibility(View.VISIBLE);
                if (count8 == 0) {
                    hide_website.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                    count8++;
                }
                view11.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                hide_website.setVisibility(View.VISIBLE);
                view11.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));
                if (webSite.getText().length() == 0){
                    hide_website.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down));
                    hide_website.setVisibility(View.INVISIBLE);
                    count8 = 0;
                    view11.setBackgroundColor(getResources().getColor(R.color.newhintcolor));

                }


            }
        });


        tanNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                hide_tan_no.setVisibility(View.GONE);




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hide_tan_no.setVisibility(View.VISIBLE);
                if (count9 == 0) {
                    hide_tan_no.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                    count9++;
                }
                view12.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                hide_tan_no.setVisibility(View.VISIBLE);
                view12.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

                if (tanNo.getText().length() == 0){
                    hide_tan_no.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down));
                    hide_tan_no.setVisibility(View.INVISIBLE);
                    count9 = 0;
                    view12.setBackgroundColor(getResources().getColor(R.color.newhintcolor));

                }


            }
        });


        panNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                hide_pan_no.setVisibility(View.GONE);




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hide_pan_no.setVisibility(View.VISIBLE);
                if (count10 == 0) {
                    hide_pan_no.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                    count10++;
                }
                view13.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                hide_pan_no.setVisibility(View.VISIBLE);
                view13.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));
                if (panNo.getText().length() == 0){
                    hide_pan_no.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down));
                    hide_pan_no.setVisibility(View.INVISIBLE);
                    count10 = 0;
                    view13.setBackgroundColor(getResources().getColor(R.color.newhintcolor));

                }


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


        List<String> cate = new ArrayList<String>();
        cate.add("Select Business Type");
        cate.add("Retailer");
        cate.add("Hotel");
        cate.add("Restaurant");
        cate.add("Caterer");
        cate.add("Processing Unit");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterFreq = new ArrayAdapter<String>(this, R.layout.spinner_item, categorie);
        ArrayAdapter<String> dataAdapterState = new ArrayAdapter<String>(this, R.layout.spinner_item, categor);
        ArrayAdapter<String> dataAdapterMode = new ArrayAdapter<String>(this, R.layout.spinner_item, categories);
        ArrayAdapter<String> dataAdapterBusi = new ArrayAdapter<String>(this,R.layout.spinner_item,cate);

        spinnerState.getBackground().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_ATOP );
        spinnerState.setPrompt("Select State");

        spinnerPaymentFreq.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        spinnerPaymentFreq.setPrompt("Select Frequency");
        spinnerPaymentMode.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        spinnerPaymentMode.setPrompt("Select Payment Mode");


        spinnerBusinessType.getBackground().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_ATOP);
        spinnerBusinessType.setPrompt("Select Business Type");

        // Drop down layout style - list view with radio button
        dataAdapterMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterFreq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterBusi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerPaymentMode.setAdapter(dataAdapterMode);
        spinnerPaymentFreq.setAdapter(dataAdapterFreq);
        spinnerState.setAdapter(dataAdapterState);
        spinnerBusinessType.setAdapter(dataAdapterBusi);



        submit.setOnClickListener(new View.OnClickListener() {
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
                spinBusType = spinnerBusinessType.getSelectedItem().toString();
                if (spinBusType.equals("Retailer")){
                    gradeType = "A" ;
                }
                else if (spinBusType.equals("Hotel") || spinBusType.equals("Restaurant")){
                    gradeType = "B";
                }
                else if  (spinBusType.equals("Caterer") || spinBusType.equals("Processing Unit")){
                    gradeType = "C";
                }



                if (spinPayFreq == "Select Frequency"){

                    Toast.makeText(context,"Please Select Frequency",Toast.LENGTH_LONG).show();

                }
                else if (addressDelivery.length() == 0 || City.length() == 0 || pin_code.length() == 0 ){
                    Toast.makeText(context , "Address,City or Pincode can't be empty.",Toast.LENGTH_SHORT).show();

                }
                else if (statee == "Select State"){
                    Toast.makeText(context,"Please select state.",Toast.LENGTH_LONG).show();
                }
                else if (spinPayMode == "Select Mode"){
                    Toast.makeText(context,"Please Select Mode",Toast.LENGTH_LONG).show();

                }
                else if (spinBusType == "Select Business Type"){
                    Toast.makeText(context,"Please Select Business Type",Toast.LENGTH_LONG).show();

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



    private void logoutPopUp() {
        final Dialog logoutdialog = new Dialog(FillRetailerDetails.this);
        logoutdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logoutdialog.setContentView(R.layout.logout_layout);
        logoutdialog.setCancelable(true);
        TextView logout = (TextView) logoutdialog.findViewById(R.id.logout);
        TextView cancel = (TextView) logoutdialog.findViewById(R.id.cancel);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                editor.putString("Check", null);
                editor.commit();

                Utilz.count = 0;
                // File cache = getCacheDir();
                dbHelper.deleterec();
                // File appDir = new File(cache.getParent());
              /*  if (appDir.exists()) {
                    String[] children = appDir.list();

                    for (String s : children) {

                        File f = new File(appDir, s);
                        if (deleteDir(f))
                            System.out.println("delete" + f.getPath());
                    }
                }*/
                Intent i = new Intent(FillRetailerDetails.this, Splash.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutdialog.dismiss();
            }
        });
        logoutdialog.show();
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
        hashmap.put("retailerGradeType",gradeType);
        hashmap.put("businessType",spinBusType);


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


                   /* Toast.makeText(context,"Your information has been saved.",Toast.LENGTH_SHORT).show();
                    SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);


                    registrationStatus = prefs.getString("registrationStatus",null);

                    if (!registrationStatus.equals("Complete")){

                        callchangeRegStatusAPI();
                    }
*/

                    Intent intent = new Intent(FillRetailerDetails.this,mapping.class);

                    intent.putExtra("showNav","false");

                    intent.putExtra("fromWhere","sample");
                   // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


                    startActivity(intent);
                    //finish();

                }

            }

            @Override
            public void failure(RetrofitError error) {


                Toast.makeText(context,"Something went wrong.",Toast.LENGTH_LONG);


            }
        });



    }






   /* void callchangeRegStatusAPI(){
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




    }*/






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

                 /*   String msg = httpResponse.error_object.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();*/

                } else if (status == 0) {


                    Toast.makeText(context,"Internal Server Error.Please try again later.",Toast.LENGTH_LONG).show();
                   /* String msg = httpResponse.error_object.get(0).toString();
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
                    String retailerGrade = retailerdetails.get(0).retailerGradeType;
                    String busType = retailerdetails.get(0).businessType;



                    if (state != null) {
                        if (state.equals("Delhi")) {
                            pos = 1;
                        } else if (state.equals("Haryana")) {
                            pos = 2;
                        } else if (state.equals("Uttar Pradesh")) {
                            pos = 3;

                        }
                    }




                    if (busType != null){
                        if (busType.equals("Retailer")){
                            pos3 = 1;
                        }
                        else if (busType.equals("Hotel")){
                            pos3 = 2;
                        }
                        else if (busType.equals("Restaurant")){
                            pos3 = 3;
                        }
                        else if (busType.equals("Caterer")){
                            pos3 = 4;
                        }
                        else if (busType.equals("Processing Unit")){
                            pos3 = 5;
                        }


                    }



                    if (paymentMode != null) {

                        if (paymentMode.equals("online")) {
                            pos1 = 1;
                        } else if (paymentMode.equals("offline")) {
                            pos1 = 2;
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
                    if (email_i != null) {
                        email.setText(email_i);
                    }
                    if (address != null) {
                        deliveryAddress.setText(address);
                    }
                    if (tanNoo != null) {
                        tanNo.setText(tanNoo);
                    }
                    if (cityy != null) {
                        city.setText(cityy);
                    }
                    if (alternateEmaill != null) {
                        alternateEmail.setText(alternateEmaill);
                    }
                    if (pincode != null) {
                        pinCode.setText(pincode);
                    }
                    if (website != null) {
                        webSite.setText(website);
                    }
                    if (panNoo != null) {
                        panNo.setText(panNoo);
                    }


                    spinnerPaymentFreq.setSelection(pos2);
                    spinnerPaymentMode.setSelection(pos1);
                    spinnerState.setSelection(pos);
                    spinnerBusinessType.setSelection(pos3);





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
