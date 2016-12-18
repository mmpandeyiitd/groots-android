package groots.canbrand.com.groots.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.interfaces.API_Interface;
import groots.canbrand.com.groots.pojo.HttpResponse;
import groots.canbrand.com.groots.pojo.SubmitFeedback;
import groots.canbrand.com.groots.utilz.Http_Urls;
import groots.canbrand.com.groots.utilz.Utilz;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by aakash on 23/11/16.
 */
public class RateUs extends AppCompatActivity implements View.OnClickListener {


    LinearLayout cdLogin;



    HashMap<Integer,String> categoryidcommentMap = new HashMap<Integer,String>();

    ArrayList<Map<String,String>> feedback = new ArrayList<Map<String,String>>();
    Map<String,String> finalmap = new HashMap<>();
    JSONArray feedbackArr = new JSONArray();
    //List<Map<Integer,String>> listOfMaps = new ArrayList<Map>();



    //ArrayList<Map<String, String>> feedback = new ArrayList<Map<String, String>>();


    ArrayList<SubmitFeedback> submitFeedback = new ArrayList<>();


    Button feedback_submit_button;
    String late_del , boy_bhv ,incomp_or ,qua_pro ,pric , oth;
    //RadioGroup late_delivery_group,behaviour_group,incomplete_order_group,quality_product_group,pricing_group,other_group;
    ToggleButton late_delivery,delivery_boy_behave,incomplete_order,quality_product,pricing,other;
    TextView msgFiveStarId,msgLessStarId;
    EditText /*editText1,editText2,editText3,editText4,editText5,*/comment;


    RatingBar ratingBar;
    TextView order_id,date;
    Button ratebutton;
    Float value;
    ImageView makecall;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateus);


        cdLogin = (LinearLayout) findViewById(R.id.cdLogin);

        cdLogin.setVisibility(View.GONE);


        final EditText comment = (EditText) findViewById(R.id.comment);

        msgFiveStarId = (TextView) findViewById(R.id.msgOnFiveId);
       // msgLessStarId = (TextView) findViewById(R.id.msgOnLessFiveId);
        msgFiveStarId.setVisibility(View.GONE);
       // msgLessStarId.setVisibility(View.GONE);

        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.putString("Check", "true");
        editor.commit();

        Intent i = getIntent();
        final String o_id = i.getStringExtra("ID");
        String date = i.getStringExtra("date");
        ((TextView)findViewById(R.id.order_id)).setText(o_id);
        ((TextView)findViewById(R.id.rateus_date)).setText(date);



        ratebutton = (Button) findViewById(R.id.ratebutton);
        ratebutton.setVisibility(View.GONE);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            public void onRatingChanged(RatingBar ratingBar,float rating,boolean fromUser){

                 value = rating;


                if (value == 5.0){


                    msgFiveStarId.setVisibility(View.VISIBLE);


                    cdLogin.setVisibility(View.GONE);
                    ratebutton.setVisibility(View.VISIBLE);


                    ratebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(RateUs.this, Landing_Update.class);



                            //msgFiveStarId.setVisibility(View.VISIBLE);


                            HashMap mp = new HashMap();
                /**/
                            mp.put("orderId", o_id);
                            mp.put("rating", value);

                            // intent.putExtra("value", value.toString());
                            // intent.putExtra("o_id",o_id);

                            //fivestar_popup();

                            callsubmitfeedbackAPI(mp);


                           /* Snackbar snackba = Snackbar.make(cdLogin, "Thanks for the feeback. We look forward to serve you again.", Snackbar.LENGTH_SHORT);
                            snackba.setActionTextColor(Color.WHITE);
                            View snackbarView = snackba.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            snackba.show();*/

                            intent.putExtra("message","five");

                            startActivity(intent);


                           finish();

                        }
                    });


                }
                else {

                    //msgFiveStarId.setVisibility(View.GONE);

msgFiveStarId.setVisibility(View.GONE);
                    cdLogin.setVisibility(View.VISIBLE);
                    ratebutton.setVisibility(View.GONE);
                    Intent intent = new Intent(RateUs.this, feedback.class);
                    intent.putExtra("value", value.toString());
                    intent.putExtra("o_id",o_id);

                    //startActivity(intent);
                }



            }



        });








       /* ratebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (value == 5.0){



                    cdLogin.setVisibility(View.GONE);
                    ratebutton.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(RateUs.this, feedback.class);

                    HashMap mp = new HashMap();
                *//**//*
                    mp.put("orderId", o_id);
                    mp.put("rating", value);

                   // intent.putExtra("value", value.toString());
                   // intent.putExtra("o_id",o_id);

                    fivestar_popup();

                    callsubmitfeedbackAPI(mp);


                }
                else {


                    cdLogin.setVisibility(View.VISIBLE);
                    ratebutton.setVisibility(View.GONE);
                    Intent intent = new Intent(RateUs.this, feedback.class);
                    intent.putExtra("value", value.toString());
                    intent.putExtra("o_id",o_id);

                    //startActivity(intent);
                }


            }


        });*/


        ((ImageView) findViewById(R.id.makecall)).setOnClickListener(this);






        late_delivery = (ToggleButton) findViewById(R.id.late_delivery);


        late_delivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                         if (isChecked) {

late_delivery.setBackgroundColor(getResources().getColor(R.color.textGreen));


                                                             //editText1.setVisibility(View.VISIBLE);
                                                         } else {

                                                             late_delivery.setBackgroundColor(getResources().getColor(R.color.textPrimaryLight));
                                                             // editText1.setVisibility(View.GONE);

                                                         }


                                                     }
                                                 }
        );

        delivery_boy_behave = (ToggleButton) findViewById(R.id.delivery_boy_behave);


        delivery_boy_behave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                           @Override
                                                           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                               if (isChecked) {

                                                                   delivery_boy_behave.setBackgroundColor(getResources().getColor(R.color.textGreen));
                                                                  // editText2.setVisibility(View.VISIBLE);

                                                               } else {
                                                                   delivery_boy_behave.setBackgroundColor(getResources().getColor(R.color.textPrimaryLight));

                                                                   //editText2.setVisibility(View.GONE);

                                                               }

                                                           }
                                                       }
        );


        incomplete_order = (ToggleButton) findViewById(R.id.incomplete_order);


        incomplete_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                        @Override
                                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                            if (isChecked) {

                                                                incomplete_order.setBackgroundColor(getResources().getColor(R.color.textGreen));
                                                                //editText3.setVisibility(View.VISIBLE);

                                                            } else {
                                                                incomplete_order.setBackgroundColor(getResources().getColor(R.color.textPrimaryLight));

                                                                //editText3.setVisibility(View.GONE);

                                                            }

                                                        }
                                                    }
        );


        quality_product = (ToggleButton) findViewById(R.id.quality_product);


        quality_product.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                       @Override
                                                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                           if (isChecked) {

                                                               quality_product.setBackgroundColor(getResources().getColor(R.color.textGreen));
                                                               //editText4.setVisibility(View.VISIBLE);

                                                           } else {
                                                               quality_product.setBackgroundColor(getResources().getColor(R.color.textPrimaryLight));

                                                               //editText4.setVisibility(View.GONE);

                                                           }
                                                       }
                                                   }
        );


        pricing = (ToggleButton) findViewById(R.id.pricing);


        pricing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                   if (isChecked) {

                                                       pricing.setBackgroundColor(getResources().getColor(R.color.textGreen));
                                                       //editText5.setVisibility(View.VISIBLE);

                                                   } else {

                                                       pricing.setBackgroundColor(getResources().getColor(R.color.textPrimaryLight));

                                                       //editText5.setVisibility(View.GONE);

                                                   }


                                               }
                                           }
        );

        other = (ToggleButton) findViewById(R.id.other);


        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                 if (isChecked) {
                                                     other.setBackgroundColor(getResources().getColor(R.color.textGreen));

                                                 }
                                                 else{
                                                     other.setBackgroundColor(getResources().getColor(R.color.textPrimaryLight));


                                                 }

                                             }
                                         }
        );







        feedback_submit_button = (Button) findViewById(R.id.feedback_submit_btn);

        feedback_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] text = new String[6];
                /*text[0] = editText1.getText().toString();
                text[4] = editText5.getText().toString();
                text[3] = editText4.getText().toString();
                text[2] = editText3.getText().toString();
                text[1] = editText2.getText().toString();*/





                text[5] = comment.getText().toString();





                    if (late_delivery.isChecked()) {

                        JSONObject js = new JSONObject();
                        try {
                            js.put("feedbackId", Integer.toString(1));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        feedbackArr.put(js);
                        Map<String, String> finalmap = new HashMap<>();


                        //finalmap.put("feedbackId", Integer.toString(1));


                        //feedback.add(finalmap);


                    }

                    if (delivery_boy_behave.isChecked()) {
                        JSONObject js = new JSONObject();
                        try {
                            js.put("feedbackId", Integer.toString(2));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        feedbackArr.put(js);

                        Map<String, String> finalmap = new HashMap<>();


                        //finalmap.put("feedbackId", Integer.toString(2));

                        //feedback.add(finalmap);


                    }

                    if (incomplete_order.isChecked()) {

                        JSONObject js = new JSONObject();
                        try {
                            js.put("feedbackId", Integer.toString(3));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        feedbackArr.put(js);
                        Map<String, String> finalmap = new HashMap<>();


                        //finalmap.put("feedbackId", Integer.toString(3));

                        //feedback.add(finalmap);


                    }

                    if (quality_product.isChecked()) {

                        JSONObject js = new JSONObject();
                        try {
                            js.put("feedbackId", Integer.toString(4));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        feedbackArr.put(js);
                        Map<String, String> finalmap = new HashMap<>();


                        /*finalmap.put("feedbackId", Integer.toString(4));

                        feedback.add(finalmap);*/


                    }

                    if (pricing.isChecked()) {
                        JSONObject js = new JSONObject();
                        try {
                            js.put("feedbackId", Integer.toString(5));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        feedbackArr.put(js);
                        Map<String, String> finalmap = new HashMap<>();


                        //finalmap.put("feedbackId", Integer.toString(5));
//
                        //feedback.add(finalmap);


                    }


                    if (other.isChecked()) {
                        JSONObject js = new JSONObject();
                        try {
                            js.put("feedbackId", Integer.toString(6));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        feedbackArr.put(js);
                        Map<String, String> finalmap = new HashMap<>();


                        //finalmap.put("feedbackId", Integer.toString(6));

                        //feedback.add(finalmap);


                    }


                    if (!(text[5] == null || text[5].isEmpty())) {


                        // Map<String, String> finalmap = new HashMap<>();

                        finalmap.put("comment", text[5]);

                        //feedback.add(finalmap);
                    }


                    // System.out.println(finalmap.toString());


                    //ashumap.put("",)

                HashMap m = new HashMap();
                //JSONArray feedbackArr = new JSONArray(feedback);

                /**/
                    m.put("orderId", o_id);
                    m.put("rating", value);
                    m.put("comment", text[5]);
                    m.put("feedback", feedbackArr);
                /*HashMap<String,String> map = new HashMap<String, String>();
                map.put("1", text1);
                map.put("2", text2);
                map.put("3", text3);
                map.put("4", text4);
                map.put("5", text5);
                map.put("6", text6);*/

                    // System.out.println(finalmap.toString());
                    System.out.println(m.toString());




               // msgLessStarId.setVisibility(View.VISIBLE);


                    callsubmitfeedbackAPI(m);


               /* Intent intent = new Intent(feedback.this, feedback.class);


                //intent.putExtra("rating",value);
                startActivity(intent);
*/

            }


        });













    }







    private class ShowDialog extends Dialog {


        public ShowDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.phone_dialog);
            ((LinearLayout) findViewById(R.id.orderSupport)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    makeAcall("+91-11-3958-9893");
                    dismiss();
                }
            });
            ((LinearLayout) findViewById(R.id.custsupport)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeAcall("+91-11-3958-8984");
                    dismiss();
                }
            });
        }
    }



    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
            editor.putString("Check", "name");
            editor.commit();
            super.onBackPressed();
        }
    }







    private void makeAcall(String phn) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(RateUs.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 9);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phn));
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + "9999999999"));
            startActivity(intent);
        }
    }


    //@Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.makecall:
                RateUs.ShowDialog showdialog = new RateUs.ShowDialog(this);
                showdialog.show();
                break;

        }
    }



    private void fivestar_popup(){
        final Dialog fivestar = new Dialog(RateUs.this);
        fivestar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        fivestar.setContentView(R.layout.five_star_layout);
        fivestar.setCancelable(true);
        TextView start_app = (TextView) fivestar.findViewById(R.id.start_app);
        //TextView cancel = (TextView) logoutdialog.findViewById(R.id.cancel);
        start_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                editor.putString("Check", null);
                editor.commit();

                //Utilz.count = 0;
                // File cache = getCacheDir();
                //dbHelper.deleterec();
                // File appDir = new File(cache.getParent());
              /*  if (appDir.exists()) {
                    String[] children = appDir.list();

                    for (String s : children) {

                        File f = new File(appDir, s);
                        if (deleteDir(f))
                            System.out.println("delete" + f.getPath());
                    }
                }*/
                Intent i = new Intent(RateUs.this, Landing_Update.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });


        fivestar.show();




    }


    void callsubmitfeedbackAPI(HashMap hashMap) {
        //progressMobile.setVisibility(View.VISIBLE);

        //isLoadingDone = false;
        //  btnSignIn.setText("Loading..");


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);


        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);


        apiInterface.getsubmitfeedbackresponse("andapikey", "1.0", "1.0",AuthToken, hashMap, new Callback<HttpResponse<SubmitFeedback>>() {
            @Override
            public void success(HttpResponse httpResponse, Response response) {

                // progressMobile.setVisibility(View.INVISIBLE);

                // btnSignIn.setEnabled(true);
                int status = httpResponse.status;


                // int status = http.getStatus();

                if (status == -1) {
                    // btnSignIn.setText("Sign In");
                    String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLogin, msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                } else if (status == 0) {
                    // btnSignIn.setText("Sign In");
                    String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLogin, msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                } else if (status == 1) {
                        /*if (submitFeedback.size() == 0) {
                            submitFeedback = (ArrayList<SubmitFeedback>) httpResponse;
                        }*/

                    // int statuscode = submitFeedback.get(0).Status;

                       /* if (statuscode == 1) {*/

                    if (!(value == 5.0)) {
                        //lessstar_popup();
                        Intent inten = new Intent(RateUs.this , Landing_Update.class);

                       /* Snackbar snackba = Snackbar.make(cdLogin, "Our customer support team is looking into the matter to address your greviences.We will look forward to serve you better.", Snackbar.LENGTH_SHORT);
                        snackba.setActionTextColor(Color.WHITE);
                        View snackbarView = snackba.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackba.show();*/
                        inten.putExtra("message","four");
                        startActivity(inten);

                       finish();


                    }




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
                    Snackbar snackbar = Snackbar.make(cdLogin, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }


            }


            @Override
            public void failure(RetrofitError error) {
                // progressMobile.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdLogin, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();
                // btnSignIn.setEnabled(true);
                //  btnSignIn.setText("Sign In");
            }
        });
    }




    private void lessstar_popup(){
        final Dialog lessstar = new Dialog(RateUs.this);
        lessstar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        lessstar.setContentView(R.layout.less_star_layout);
        lessstar.setCancelable(true);
        TextView start_app = (TextView) lessstar.findViewById(R.id.start_app);
        //TextView cancel = (TextView) logoutdialog.findViewById(R.id.cancel);
        start_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                editor.putString("Check", null);
                editor.commit();

                //Utilz.count = 0;
                // File cache = getCacheDir();
                //dbHelper.deleterec();
                // File appDir = new File(cache.getParent());
              /*  if (appDir.exists()) {
                    String[] children = appDir.list();

                    for (String s : children) {

                        File f = new File(appDir, s);
                        if (deleteDir(f))
                            System.out.println("delete" + f.getPath());
                    }
                }*/
                Intent i = new Intent(RateUs.this, Landing_Update.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });


        lessstar.show();




    }




}
