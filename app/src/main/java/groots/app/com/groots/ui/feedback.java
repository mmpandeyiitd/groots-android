package groots.app.com.groots.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import groots.app.com.groots.R;
import groots.app.com.groots.interfaces.API_Interface;
import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.SubmitFeedback;
import groots.app.com.groots.utilz.Http_Urls;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

//import static groots.canbrand.com.groots.R.id.editText1;


/**
 * Created by aakash on 24/11/16.
 */

public class feedback extends AppCompatActivity {

    CoordinatorLayout cdLogin;


    //Map map = new HashMap();


    HashMap<Integer,String> categoryidcommentMap = new HashMap<Integer,String>();

    ArrayList<Map<String,String>> feedback = new ArrayList<Map<String,String>>();
    Map<String,String> ashumap = new HashMap<>();

    //List<Map<Integer,String>> listOfMaps = new ArrayList<Map>();



    //ArrayList<Map<String, String>> feedback = new ArrayList<Map<String, String>>();


    ArrayList<SubmitFeedback> submitFeedback = new ArrayList<>();




    Button feedback_submit_button;
    String late_del , boy_bhv ,incomp_or ,qua_pro ,pric , oth;
    //RadioGroup late_delivery_group,behaviour_group,incomplete_order_group,quality_product_group,pricing_group,other_group;
    CheckBox late_delivery,delivery_boy_behave,incomplete_order,quality_product,pricing,other;
    EditText /*editText1,editText2,editText3,editText4,editText5,*/comment;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        cdLogin = (CoordinatorLayout) findViewById(R.id.cdLogin);

/*
        final EditText editText1 = (EditText) findViewById(R.id.editText1);
        editText1.setVisibility(View.GONE);
        final EditText editText2 = (EditText) findViewById(R.id.editText2);
        editText2.setVisibility(View.GONE);
        final EditText editText3 = (EditText) findViewById(R.id.editText3);
        editText3.setVisibility(View.GONE);
        final EditText editText4 = (EditText) findViewById(R.id.editText4);
        editText4.setVisibility(View.GONE);
        final EditText editText5 = (EditText) findViewById(R.id.editText5);
        editText5.setVisibility(View.GONE);*/
        final EditText comment = (EditText) findViewById(R.id.comment);


        Intent i = getIntent();
        final String orderId = i.getStringExtra("o_id");
        final String rateValue = i.getStringExtra("value");

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


                text[0] = comment.getText().toString();


                for (int i = 0; i < text.length; i++) {

                    Map<String, String> ashumap = new HashMap<>();


                    if (!(text[i] == null || text[i].isEmpty())) {
                        ashumap.put("feedbackId", Integer.toString(i + 1));
                        ashumap.put("comment", text[i]);
                        feedback.add(ashumap);
                    }


                }


                //ashumap.put("",)


                HashMap m = new HashMap();
                /**/
                m.put("orderId", orderId);
                m.put("rating", rateValue);
                m.put("feedback", feedback);
                /*HashMap<String,String> map = new HashMap<String, String>();
                map.put("1", text1);
                map.put("2", text2);
                map.put("3", text3);
                map.put("4", text4);
                map.put("5", text5);
                map.put("6", text6);*/


                callsubmitfeedbackAPI(m);


               /* Intent intent = new Intent(feedback.this, feedback.class);


                //intent.putExtra("rating",value);
                startActivity(intent);
*/

            }


        });



        /*late_delivery.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        delivery_boy_behave.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        incomplete_order.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        quality_product.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        pricing.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        other.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);*/

        late_delivery = (CheckBox) findViewById(R.id.late_delivery);


        late_delivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                         /*if (isChecked) {

                                                             editText1.setVisibility(View.VISIBLE);
                                                         } else {
                                                             editText1.setVisibility(View.GONE);

                                                         }*/


                                                     }
                                                 }
        );

        delivery_boy_behave = (CheckBox) findViewById(R.id.delivery_boy_behave);


        delivery_boy_behave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                           @Override
                                                           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              /* if (isChecked) {
                                                                   editText2.setVisibility(View.VISIBLE);

                                                               } else {
                                                                   editText2.setVisibility(View.GONE);

                                                               }*/

                                                           }
                                                       }
        );


        incomplete_order = (CheckBox) findViewById(R.id.incomplete_order);


        incomplete_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                        @Override
                                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                           /* if (isChecked) {
                                                                editText3.setVisibility(View.VISIBLE);

                                                            } else {
                                                                editText3.setVisibility(View.GONE);

                                                            }*/

                                                        }
                                                    }
        );


        quality_product = (CheckBox) findViewById(R.id.quality_product);


        quality_product.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                       @Override
                                                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                           /*if (isChecked) {
                                                               editText4.setVisibility(View.VISIBLE);

                                                           } else {
                                                               editText4.setVisibility(View.GONE);

                                                           }*/
                                                       }
                                                   }
        );


        pricing = (CheckBox) findViewById(R.id.pricing);


        pricing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                  /* if (isChecked) {
                                                       editText5.setVisibility(View.VISIBLE);

                                                   } else {
                                                       editText5.setVisibility(View.GONE);

                                                   }*/


                                               }
                                           }
        );

        other = (CheckBox) findViewById(R.id.other);


        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                 if (isChecked) {
                                                 }

                                             }
                                         }
        );
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


                            lessstar_popup();





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
        final Dialog lessstar = new Dialog(feedback.this);
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
                Intent i = new Intent(feedback.this, Landing_Update.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                //finish();
            }
        });


        lessstar.show();




    }



}

