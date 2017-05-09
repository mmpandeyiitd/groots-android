package groots.app.com.groots.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import groots.app.com.groots.utilz.Constants;


import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import groots.app.com.groots.R;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.interfaces.API_Interface;
import groots.app.com.groots.pojo.ForgetPwdData;
import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.LoginData;
import groots.app.com.groots.pojo.OrderFeedback;
import groots.app.com.groots.pojo.contactnumberpojo;
import groots.app.com.groots.pojo.updateAppResponsePojo;
import groots.app.com.groots.utilz.Analytics;
import groots.app.com.groots.utilz.Applicationclass;
import groots.app.com.groots.utilz.ContainerHolderSingleton;
import groots.app.com.groots.utilz.Http_Urls;
import groots.app.com.groots.utilz.TagManagerEvent;
import groots.app.com.groots.utilz.Utilz;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Response;


public class Splash extends AppCompatActivity implements AnimationListener, OnClickListener {

    ImageView ivGroots, ivCallLogin;
    ArrayList<OrderFeedback> orderFeedback = new ArrayList<>();
    ArrayList<contactnumberpojo> contactnumbers = new ArrayList<>();
    ArrayList<contactnumberpojo> salescontactnumbers = new ArrayList<>();
    String cust_support,order_support,sales_contact_number ;
    ArrayList<updateAppResponsePojo> updateappResponsePojos = new ArrayList<>();
    AlphaAnimation alphaAnimation;
    int count1 = 0,count2 = 0;

    String registrationStatus;
    //updateAppResponsePojo updateappResponsePojo;
    KenBurnsView kbv;
    Animation animationmoveup, animationmovebt;
    LinearLayout llUserName, llPassword,condition_pass;
    EditText etPassword;
    CoordinatorLayout cdLanding;
    AutoCompleteTextView etLogin;
    CoordinatorLayout cdLogin, cdForgetPwd ,feedbackfail;
    TextView tvForgetPass , tvSignUp;
    LinearLayout tvSignup;
    RelativeLayout progressMobile;
    DbHelper dbHelper;
    Button btnSignIn;
    View viewUser, viewPass;
    TextView hide_comp,hide_pass;

    Dialog dialog;
    Context context;

    int status;
    boolean isLoadingDone = false;
    SharedPreferences prefs;

    android.os.Handler handler = new android.os.Handler();
    Runnable runnable;
    private static final long TIMEOUT_FOR_CONTAINER_OPEN_MILLISECONDS = 4000;
    private static final String CONTAINER_ID = "GTM-K43QXDL";
    private static final String  screenName = "splash";
    private TagManager tagManager;
    private Tracker mTracker;
    Applicationclass application;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_design_splash);

      /*  SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.putString("Check", null);
        editor.commit();*/





        // analytics
        application = (Applicationclass) getApplication();
        //Analytics.sendScreenName(screenName, application);

        callsalesnumberAPI();

        // google tag manage invocation code

        tagManager = TagManager.getInstance(this);



        // Modify the log level of the logger to print out not only
        // warning and error messages, but also verbose, debug, info messages.
        tagManager.setVerboseLoggingEnabled(true);

        PendingResult<ContainerHolder> pending =
                tagManager.loadContainerPreferNonDefault(CONTAINER_ID,
                        R.raw.gtm_k43qxdl_v6);

        // The onResult method will be called as soon as one of the following happens:
        //     1. a saved container is loaded
        //     2. if there is no saved container, a network container is loaded
        //     3. the 2-second timeout occurs
        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {
                ContainerHolderSingleton.setContainerHolder(containerHolder);
                Container container = containerHolder.getContainer();
                if (!containerHolder.getStatus().isSuccess()) {
                    Log.e("Groots-Analytics", "failure loading container");
                    //displayErrorToUser(R.string.load_error);
                    return;
                }
                ContainerLoadedCallback.registerCallbacksForContainer(container);
                containerHolder.setContainerAvailableListener(new ContainerLoadedCallback());
                //startMainActivity();
            }
        }, TIMEOUT_FOR_CONTAINER_OPEN_MILLISECONDS, TimeUnit.MILLISECONDS);


        Intent inte = getIntent();


       String msg =  inte.getStringExtra("messge");




        //btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setEnabled(true);
        btnSignIn.setText("Sign In");
        progressMobile = (RelativeLayout) findViewById(R.id.progressMobile);
        condition_pass = (LinearLayout) findViewById(R.id.condition_pass);
        condition_pass.setVisibility(View.GONE);
        progressMobile.setVisibility(View.INVISIBLE);

        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(false);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        // set the transparent color of the status bar, 20% darker
        tintManager.setTintColor(Color.parseColor("#20000000"));
        prefs = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
//        toolbars=(Toolbar)findViewById(R.id.toolbars);
//        toolbars.setBackgroundColor(getResources().getColor(android.R.color.transparent));


        context = Splash.this;
        cdLogin = (CoordinatorLayout) findViewById(R.id.cdLogin);
        //feedbackfail = (CoordinatorLayout) findViewById(feedbackfail);
        ivGroots = (ImageView) findViewById(R.id.ivGroots);
        ivCallLogin = (ImageView) findViewById(R.id.ivCallLogin);
        kbv = (KenBurnsView) findViewById(R.id.image);
        llUserName = (LinearLayout) findViewById(R.id.llUserName);
        llPassword = (LinearLayout) findViewById(R.id.llPassword);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etLogin = (AutoCompleteTextView) findViewById(R.id.etLogin);
        hide_pass = (TextView) findViewById(R.id.hide_pass);
        hide_comp = (TextView) findViewById(R.id.hide_email);







        viewUser = findViewById(R.id.viewUser);
        viewPass = findViewById(R.id.viewPass);
        tvForgetPass = (TextView) findViewById(R.id.tvForgetPas);
        tvForgetPass.setOnClickListener(this);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        tvSignup = (LinearLayout) findViewById(R.id.tvsignup);
        tvSignUp.setOnClickListener(this);

        dbHelper = DbHelper.getInstance(context);
        dbHelper.createDb(false);
        /*ArrayList<String> ContactNumbers  = dbHelper.selectfromcontactnumbers();
        cust_support_no = ContactNumbers.get(0);
        order_support_no = ContactNumbers.get(1);*/
        hide_pass.setVisibility(View.GONE);
        hide_comp.setVisibility(View.GONE);


        etLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                hide_comp.setVisibility(View.INVISIBLE);




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hide_comp.setVisibility(View.VISIBLE);

                if (count2 == 0) {
                    hide_comp.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                    count2++;
                }
                viewUser.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
               // hide_comp.setVisibility(View.VISIBLE);
                viewUser.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));


                if (etLogin.getText().length() == 0){
                    hide_comp.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down));
                    hide_comp.setVisibility(View.INVISIBLE);
                    count2 = 0;
                    viewUser.setBackgroundColor(getResources().getColor(R.color.newhintcolor));

                }


            }
        });




        etPassword.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                hide_pass.setVisibility(View.INVISIBLE);




            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hide_pass.setVisibility(View.VISIBLE);

                if (count1 == 0) {
                    hide_pass.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                    count1++;
                }
                viewPass.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                hide_pass.setVisibility(View.VISIBLE);
                //hide_pass.startAnimation(AnimationUtils.loadAnimation(context,android.R.anim.fade_in));
                viewPass.setBackgroundColor(getResources().getColor(R.color.resendotpcolor));

                if (etPassword.getText().length() == 0){
                    hide_pass.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down));
                    hide_pass.setVisibility(View.INVISIBLE);
                    count1 = 0;
                    viewPass.setBackgroundColor(getResources().getColor(R.color.newhintcolor));

                }


            }
        });




        if (msg != null )
        {
            if (msg .equals("show")){
                Snackbar snackbar = Snackbar.make(cdLogin, "Your data has been submitted successfully. We will get back to you in next 24 hours.", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();



            }





        }





        etLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (dbHelper.getTotalmail() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Splash.this, android.R.layout.simple_list_item_1, dbHelper.getMaillist());
                    etLogin.setAdapter(adapter);
                    etLogin.setThreshold(0);
                }
                return false;
            }
        });


      /*  Intent i = getIntent();
        Bundle bundle = i.getExtras();
*/
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);




        if (prefs.getString("AuthToken", null) != null & prefs.getString("Check", null) != null) {

            registrationStatus = prefs.getString("registrationStatus",null);

            /*if (prefs.getString("registrationStatus","Complete") != null) {*/
                runnable = new Runnable() {
                    @Override
                    public void run() {





                        callupdateappAPI();
                        // callfeedbackresponseAPI();
                    /*Intent i = new Intent(Splash.this, Landing_Update.class);
                    startActivity(i);
                    finish();*/


                    }
                };
                handler.postDelayed(runnable, 2200);
           /* }
            else {
                Intent inten = new Intent(Splash.this,SampleActivity.class);

                startActivity(inte);
                finish();
            }*/

        } else {
            if (prefs.getString("AuthToken", null) != null & prefs.getString("Check", null) == null) {

                moveup();
                moveupTextField();
                //      String sender = bundle.getString("sender");

                // Toast.makeText(this,"logout",Toast.LENGTH_LONG).show();
                kbv.setImageResource(R.color.newbackgroundcolor);
                llUserName.setVisibility(View.VISIBLE);
                llPassword.setVisibility(View.VISIBLE);
                btnSignIn.setVisibility(View.VISIBLE);
                ivCallLogin.setVisibility(View.VISIBLE);
                tvForgetPass.setVisibility(View.VISIBLE);
                tvSignUp.setVisibility(View.VISIBLE);
                tvSignup.setVisibility(View.VISIBLE);
                viewUser.setVisibility(View.VISIBLE);
                viewPass.setVisibility(View.VISIBLE);




            } else {


                AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
                RandomTransitionGenerator generator = new RandomTransitionGenerator(20000, ACCELERATE_DECELERATE);
                kbv.setTransitionGenerator(generator);

                alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                alphaAnimation.setDuration(3000);
                alphaAnimation.setStartOffset(100);
                ivGroots.startAnimation(alphaAnimation);
                alphaAnimation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        moveup();
                        moveupTextField();
                        //((View) findViewById(R.id.viewBlur)).setVisibility(View.VISIBLE);
                        kbv.setImageResource(R.color.newbackgroundcolor);
                        llUserName.setVisibility(View.VISIBLE);
                        llPassword.setVisibility(View.VISIBLE);
                        btnSignIn.setVisibility(View.VISIBLE);
                        ivCallLogin.setVisibility(View.VISIBLE);
                        tvForgetPass.setVisibility(View.VISIBLE);
                        tvSignUp.setVisibility(View.VISIBLE);
                        tvSignup.setVisibility(View.VISIBLE);
                        viewUser.setVisibility(View.VISIBLE);
                        viewPass.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }

                    @Override
                    public void onAnimationStart(Animation arg0) {
                    }
                });


                kbv.setTransitionListener(new KenBurnsView.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) {
                    }

                    @Override
                    public void onTransitionEnd(Transition transition) {
                    }
                });

            }
            btnSignIn.setOnClickListener(this);
            ivCallLogin.setOnClickListener(this);
        }

       TagManagerEvent.pushOpenScreenEvent(context, screenName);
        //tagManager.getInstance(this).getDataLayer().pushEvent("openScreen", DataLayer.mapOf("screenName", screenName));

        //tagManager.getInstance(this).getDataLayer().push("abc", "gogroots");
        //.Object test3 = tagManager.getInstance(this).getDataLayer().get("abc");
    }


    protected void moveup() {
        animationmoveup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.moveup);
        animationmoveup.setAnimationListener(this);
        animationmoveup.setFillAfter(true);
        animationmoveup.setRepeatCount(-1);
        animationmoveup.setRepeatMode(Animation.REVERSE);
        ivGroots.startAnimation(animationmoveup);

    }


    protected void moveupTextField() {
        animationmovebt = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movebottomtocenter);
        animationmovebt.setAnimationListener(this);
        animationmovebt.setFillAfter(true);
        animationmovebt.setRepeatCount(-1);
        animationmovebt.setRepeatMode(Animation.REVERSE);
        llUserName.startAnimation(animationmovebt);
        llPassword.startAnimation(animationmovebt);
        btnSignIn.startAnimation(animationmovebt);
        tvForgetPass.startAnimation(animationmovebt);
        tvSignUp.startAnimation(animationmovebt);
        tvSignup.startAnimation(animationmovebt);
        viewUser.startAnimation(animationmovebt);
        viewPass.startAnimation(animationmovebt);
    }


    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ivCallLogin:

              //  ShowDialog showdialog = new ShowDialog(this);
               // showdialog.show();
		//Analytics.sendEvent(Constants.SplashCategory, "Call", application);
                
                 makeCall(sales_contact_number);
                break;

            case R.id.btnSignIn:

                //Analytics.sendEvent(Constants.SplashCategory, "SignIn", application);
                Utilz utilz = new Utilz();


                String strPwd = etPassword.getText().toString();

                if (etLogin.getText().toString().length() > 0 ) {


                    if (etLogin.getText().toString().matches("[+0-9]+")) {

                        String strContact = etLogin.getText().toString();


                        if (!((strContact.length()) == 10)) {
                            Snackbar snackbar = Snackbar.make(cdLogin, "Please provide 10 digit contact no", Snackbar.LENGTH_SHORT);
                            snackbar.setActionTextColor(Color.WHITE);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        } else if (!utilz.isValidMobile(strContact)) {
                            Snackbar snackbar = Snackbar.make(cdLogin, "Please provide a valid contact ", Snackbar.LENGTH_SHORT);
                            snackbar.setActionTextColor(Color.WHITE);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        } else if (strPwd.length() <= 0) {
                            Snackbar snackbar = Snackbar.make(cdLogin, "Please provide your password", Snackbar.LENGTH_SHORT);
                            snackbar.setActionTextColor(Color.WHITE);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        } else if (strPwd.length() <= 3) {
                            Snackbar snackbar = Snackbar.make(cdLogin, "Password must be at least 4 characters.", Snackbar.LENGTH_SHORT);
                            snackbar.setActionTextColor(Color.WHITE);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        } else {
                            if (!utilz.isInternetConnected(context)) {

                                Snackbar snackbar = Snackbar.make(cdLogin, "Please check the internet connection", Snackbar.LENGTH_SHORT);
                                snackbar.setActionTextColor(Color.WHITE);
                                View snackbarView = snackbar.getView();
                                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                snackbar.show();

                            } else {

                                btnSignIn.setEnabled(false);
                                dbHelper.insertMailData(strContact);
                                HashMap hashMap = new HashMap();
                                hashMap.put("contact", strContact);
                                hashMap.put("password", strPwd);

                                callLoginAPI(hashMap);
                            }

                        }


                    } else {

                        String strEmail = etLogin.getText().toString();

                        if (strEmail.length() <= 0) {
                            Snackbar snackbar = Snackbar.make(cdLogin, "Please provide your email id", Snackbar.LENGTH_SHORT);
                            snackbar.setActionTextColor(Color.WHITE);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        } else if (!utilz.isValidEmail1(strEmail)) {
                            Snackbar snackbar = Snackbar.make(cdLogin, "Please provide a valid email id", Snackbar.LENGTH_SHORT);
                            snackbar.setActionTextColor(Color.WHITE);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        } else if (strPwd.length() <= 0) {
                            Snackbar snackbar = Snackbar.make(cdLogin, "Please provide your password", Snackbar.LENGTH_SHORT);
                            snackbar.setActionTextColor(Color.WHITE);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        } else if (strPwd.length() <= 3) {
                            Snackbar snackbar = Snackbar.make(cdLogin, "Password must be at least 4 characters.", Snackbar.LENGTH_SHORT);
                            snackbar.setActionTextColor(Color.WHITE);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        } else {
                            if (!utilz.isInternetConnected(context)) {

                                Snackbar snackbar = Snackbar.make(cdLogin, "Please check the internet connection", Snackbar.LENGTH_SHORT);
                                snackbar.setActionTextColor(Color.WHITE);
                                View snackbarView = snackbar.getView();
                                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                snackbar.show();

                            } else {

                                btnSignIn.setEnabled(false);
                                dbHelper.insertMailData(strEmail);
                                HashMap hashMap = new HashMap();
                                hashMap.put("email", strEmail);
                                hashMap.put("password", strPwd);

                                callLoginAPI(hashMap);
                            }

                        }


                    }
                }
                else {

                    Snackbar snackbar = Snackbar.make(cdLogin, "Please provide your email id or contact no", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }






                break;

            case R.id.tvForgetPas:
                //Analytics.sendEvent(Constants.SplashCategory, "ForgetPwdDialog", application);
                showForgetPwdDialog();

                break;


            case R.id.tvSignUp:
                //Analytics.sendEvent(Constants.SplashCategory, "SignUp", application);
                Intent i = new Intent (Splash.this , signUpPage.class);
                i.putExtra("salesNo",sales_contact_number);

                startActivity(i);



        }
    }


    void makeCall(String phoneNo) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(Splash.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 9);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNo));
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNo));
            startActivity(intent);
        }

    }


    void showForgetPwdDialog() {

        dialog = new Dialog(Splash.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_forgot_password);
        dialog.setCancelable(true);

        cdForgetPwd = (CoordinatorLayout) dialog.findViewById(R.id.cdForgetPwd);

        LinearLayout submitforgot = (LinearLayout) dialog.findViewById(R.id.submitforgot);
        submitforgot.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //Analytics.sendEvent(Constants.SplashCategory, "ForgetPwdSubmit", application);
                Utilz utilz = new Utilz();
                String strEmail = ((EditText) dialog.findViewById(R.id.etForgetPwd)).getText().toString();
                ((EditText) dialog.findViewById(R.id.etForgetPwd)).requestFocus();

                if (strEmail == null || strEmail.trim().length() <= 0) {
                    Snackbar snackbar = Snackbar.make(cdForgetPwd, "Please provide your email id", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else if (!utilz.isValidEmail1(strEmail)) {
                    Snackbar snackbar = Snackbar.make(cdForgetPwd, "Please provide a valid email id", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else {
                    HashMap hashMap = new HashMap();
                    hashMap.put("email", strEmail);
                    callForgetPasswordAPI(hashMap);

                }

            }
        });

        dialog.show();
    }


    void callfeedbackresponseAPI(){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);
        apiInterface.getcheckfeedbackresponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, AuthToken, new Callback<HttpResponse<OrderFeedback>>(){
            @Override
               public void success ( HttpResponse httpResponse , Response response ){


               int status = httpResponse.status;

                if (status == -1){

                    String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLogin,msg, Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                } else if (status == 0) {

                    String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLogin,msg, Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if (status == 1){


                   // Toast.makeText(Splash.this,"feedback response", Toast.LENGTH_LONG).show();


                    if (orderFeedback.size() == 0) {
                        orderFeedback = (ArrayList<OrderFeedback>) httpResponse.data.responseData.docs;
                    }

                          boolean Status = orderFeedback.get(0).feedbackStatus;
                          String O_id = orderFeedback.get(0).orderId;
                          String datee = orderFeedback.get(0).deliveryDate;

                    if (Status == true){
                       //String orderId =
                        //orderfeedback.orderId;
                        Intent i = new Intent(Splash.this, RateUs.class);
                        i.putExtra("ID",O_id);
                        i.putExtra("date",datee);
                        startActivity(i);
                        finish();

                    }
                    else {

                        callcontactnumberAPI();





                    }

                   // if (httpResponse.data.responseData.docs.size() != 0 ){



                    //}



                }









            }









            @Override
            public void failure(RetrofitError error) {
                progressMobile.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdLogin, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

            }

        });





    }


    void callupdateappAPI(){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);


       apiInterface.getappupdateresponse(Utilz.apikey,Utilz.app_version,Utilz.config_version,AuthToken,new Callback<HttpResponse<updateAppResponsePojo>>(){
          @Override
           public void success(HttpResponse httpResponse , Response response){


              int status = httpResponse.status;
              if (status == -1){
                  Snackbar snackbar = Snackbar.make(cdLogin,"Oops! Something went wrong.Please try again later.",Snackbar.LENGTH_LONG);
                  snackbar.setActionTextColor(Color.WHITE);
                  View snackbarView = snackbar.getView();
                  snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                  snackbar.show();


              }
              else if (status == 0){

                  Snackbar snackbar = Snackbar.make(cdLogin,"Oops! Something went wrong.Please try again later.",Snackbar.LENGTH_LONG);
                  snackbar.setActionTextColor(Color.WHITE);
                  View snackbarView = snackbar.getView();
                  snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                  snackbar.show();




              }
              else if (status == 1){


                 // Toast.makeText(Splash.this,"update app", Toast.LENGTH_LONG).show();

                  if (updateappResponsePojos.size() == 0){

                      updateappResponsePojos = (ArrayList<updateAppResponsePojo>)  httpResponse.data.responseData.docs;
                  }

                boolean requestForceUpdate = updateappResponsePojos.get(0).forceUpdate;
                  boolean requestRecUpdate = updateappResponsePojos.get(0).recommendedUpdate;



                  if (requestForceUpdate == true  ){

                      final String appLink = updateappResponsePojos.get(0).appLink;

                       dialog = new Dialog(Splash.this);
                      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                      dialog.setCancelable(true);


                      dialog.setContentView(R.layout.force_update_dialog);



                      dialog.show();

                      TextView force_update_yes = (TextView) dialog.findViewById(R.id.force_update_yes);
                      TextView force_update_quit = (TextView) dialog.findViewById(R.id.force_update_quit);

                      force_update_yes.setOnClickListener(new OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              dialog.dismiss();
                              Uri uri = Uri.parse(appLink);
                              Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                              startActivity(intent);
                          }
                      });

                      force_update_quit.setOnClickListener(new OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              dialog.dismiss();
                              /*Intent intent = new Intent(Intent.ACTION_MAIN);
                              intent.addCategory(Intent.CATEGORY_HOME);
                              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                              startActivity(intent);*/
                              AppExit();



                             /* finish();
                              System.exit(0);*/

                          }
                      });






                  }
                  else if (requestForceUpdate == false && requestRecUpdate == true ){

                      final String appLink = updateappResponsePojos.get(0).appLink;


                      dialog = new Dialog(Splash.this);
                      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                      dialog.setContentView(R.layout.recomm_update_dialog);

                      dialog.setCancelable(true);
                       dialog.show();

                      TextView rec_update_yes = (TextView) dialog.findViewById(R.id.rec_update_yes);
                      TextView rec_update_quit = (TextView) dialog.findViewById(R.id.rec_update_quit);


                      rec_update_yes.setOnClickListener(new OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              dialog.dismiss();

                              Uri uri = Uri.parse(appLink);
                              Intent intent = new Intent(Intent.ACTION_VIEW ,uri);
                              startActivity(intent);


                          }
                      });

                      rec_update_quit.setOnClickListener(new OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              dialog.dismiss();

                              if (registrationStatus.equals("Complete")) {
                                  callfeedbackresponseAPI();
                              }
                              else{
                                  Intent inte = new Intent(Splash.this,Welcome.class);
                                  Toast.makeText(context,"Please fill your details first.",Toast.LENGTH_LONG).show();
                                  startActivity(inte);
                                  finish();
                              }


                          }
                      });





                  }

                  else if (requestForceUpdate == false && requestRecUpdate == false ){





                      if (registrationStatus.equals("Complete")) {
                          callfeedbackresponseAPI();
                      }
                      else{
                          Intent inte = new Intent(Splash.this,Welcome.class);
                          Toast.makeText(context,"Please fill your details first.",Toast.LENGTH_LONG).show();
                          startActivity(inte);
                          finish();
                      }



                  }








              }

           }

           @Override
           public void failure (RetrofitError error){

               Snackbar snackbar = Snackbar.make(cdLogin, "Oops! .Please try again later !...", Snackbar.LENGTH_LONG);
               snackbar.setActionTextColor(Color.WHITE);
               View snackbarView = snackbar.getView();
               snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
               snackbar.show();



           }

       });




    }


    void callLoginAPI(HashMap hashMap) {
        TagManagerEvent.pushOpenScreenEvent(context, screenName);
        tagManager.getInstance(this).getDataLayer().pushEvent("openScreen", DataLayer.mapOf("screenName", screenName));

        tagManager.getInstance(this).getDataLayer().push("abc", "gogroots");

        progressMobile.setVisibility(View.VISIBLE);

        isLoadingDone = false;
        //  btnSignIn.setText("Loading..");


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);


        apiInterface.getloginResponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, hashMap, new Callback<LoginData>() {
            @Override
            public void success(LoginData loginData, Response response) {

                progressMobile.setVisibility(View.INVISIBLE);

                btnSignIn.setEnabled(true);

                if (loginData != null) {

                    status = loginData.getStatus();

                    if (status == -1) {

                        condition_pass.setVisibility(View.VISIBLE);

                        // btnSignIn.setText("Sign In");
                        String msg = loginData.getErrors().get(0).toString();
                        Snackbar snackbar = Snackbar.make(cdLogin, msg, Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    } else if (status == 0) {
                        // btnSignIn.setText("Sign In");
                       // String msg = loginData.getErrors().get(0).toString();
                        String msg = "invalid email/mobile or password.";
                        condition_pass.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar.make(cdLogin, msg, Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

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

                            registrationStatus = loginData.getData().regStatus;




                            //   btnSignIn.setText("Success");

                            //HashMap hashMap = new HashMap();
                            //hashMap.put("email", strEmail);


                           callupdateappAPI();

                           // callfeedbackresponseAPI();

                            /*Intent i = new Intent(Splash.this, Landing_Update.class);
                            startActivity(i);
                            finish();*/

                             Toast.makeText(Splash.this,"You have logged in successfully!", Toast.LENGTH_LONG).show();
                        } else {
                            //    btnSignIn.setText("Sign In");
                            Snackbar snackbar = Snackbar.make(cdLogin, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_LONG);
                            snackbar.setActionTextColor(Color.WHITE);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        }
                    }
                } else {
                    btnSignIn.setEnabled(true);
                    // btnSignIn.setText("Sign In");
                    Snackbar snackbar = Snackbar.make(cdLogin, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressMobile.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdLogin, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();
                btnSignIn.setEnabled(true);
                //  btnSignIn.setText("Sign In");
            }
        });
    }


    void callForgetPasswordAPI(HashMap hashMap) {

        progressMobile.setVisibility(View.VISIBLE);
        progressMobile.bringToFront();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);

        apiInterface.getForgetPwdResponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, hashMap, new Callback<ForgetPwdData>() {
            @Override
            public void success(ForgetPwdData forgetPwdData, Response response) {
                if (forgetPwdData != null) {

                    progressMobile.setVisibility(View.INVISIBLE);
                    status = forgetPwdData.getStatus();

                    if (status == 1) {
                        String msg = forgetPwdData.getData().getMessage();
                        Snackbar snackbar = Snackbar.make(cdForgetPwd, "Success! Please check your email", Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    } else if (status == 0) {

                        String msg = forgetPwdData.getErrors().get(0).toString();
                        Snackbar snackbar = Snackbar.make(cdForgetPwd, msg, Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    } else if (status == 1) {
                        //    String msg = forgetPwdData.getMsg();
                        Snackbar snackbar = Snackbar.make(cdForgetPwd, "Success! Please check your email", Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    }
                } else {
                    Toast.makeText(Splash.this, status, Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar.make(cdForgetPwd, "Oops! Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressMobile.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdForgetPwd, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

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

            /*setContentView(R.layout.phone_dialog);
            ((TextView) findViewById(R.id.customer_support)).setText(cust_support_no);
            ((TextView) findViewById(R.id.ordering_support)).setText(order_support_no);
            ((LinearLayout) findViewById(R.id.orderSupport)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    makeCall("+91-11-3958-9893");
                    dismiss();
                }
            });
            ((LinearLayout) findViewById(R.id.custsupport)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeCall("+91-11-3958-9892");
                    dismiss();
                }
            });*/

        }


   /* public void start(final ProcessButton button) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isLoadingDone) {
                    mProgress += 10;
                    button.setProgress(mProgress);
                    handler.postDelayed(this, 100);
                } else {
//                    mListener.onComplete();
                }
            }
        }, 500);
    }*/
    }


    public void AppExit()
    {

        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);



    }


    private static class ContainerLoadedCallback implements ContainerHolder.ContainerAvailableListener {
        @Override
        public void onContainerAvailable(ContainerHolder containerHolder, String containerVersion) {
            // We load each container when it becomes available.
            Container container = containerHolder.getContainer();
            registerCallbacksForContainer(container);
        }

        public static void registerCallbacksForContainer(Container container) {
            // Register two custom function call macros to the container.
            container.registerFunctionCallMacroCallback("increment", new CustomMacroCallback());
            container.registerFunctionCallMacroCallback("mod", new CustomMacroCallback());
            // Register a custom function call tag to the container.
            container.registerFunctionCallTagCallback("custom_tag", new CustomTagCallback());
        }
    }

    private static class CustomMacroCallback implements Container.FunctionCallMacroCallback {
        private int numCalls;

        @Override
        public Object getValue(String name, Map<String, Object> parameters) {
            if ("increment".equals(name)) {
                return ++numCalls;
            } else if ("mod".equals(name)) {
                return (Long) parameters.get("key1") % Integer.valueOf((String) parameters.get("key2"));
            } else {
                throw new IllegalArgumentException("Custom macro name: " + name + " is not supported.");
            }
        }
    }

    private static class CustomTagCallback implements Container.FunctionCallTagCallback {
        @Override
        public void execute(String tagName, Map<String, Object> parameters) {
            // The code for firing this custom tag.
            Log.i("CuteAnimals", "Custom function call tag :" + tagName + " is fired.");
        }
    }



    void callsalesnumberAPI(){


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        apiInterface.getsalesnoresponse(Utilz.apikey ,Utilz.app_version,Utilz.config_version ,new Callback<HttpResponse<contactnumberpojo>>(){

            @Override
            public void success(HttpResponse httpResponse , Response response){

                int status = httpResponse.status;

                if (status == -1){

                    String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLogin,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                } else if (status == 0) {

                    // String msg = httpResponse.error_object.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLogin,"Something went wrong. Please try again later.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if (status == 1){

                    if (salescontactnumbers.size() == 0 ){


                        salescontactnumbers = (ArrayList<contactnumberpojo>) httpResponse.data.responseData.docs;

                    }
                    sales_contact_number = salescontactnumbers.get(0).salesSupportNumber;








                }




            }

            @Override
            public void failure(RetrofitError error){



                Snackbar snackbar = Snackbar.make(cdLogin, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();


            }


        });



    }

    void callcontactnumberAPI(){


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Http_Urls.sBaseUrl).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);
        apiInterface.getcontactnumberresponse(Utilz.apikey ,Utilz.app_version,Utilz.config_version , AuthToken , new Callback<HttpResponse<contactnumberpojo>>(){


            @Override
            public void success ( HttpResponse httpResponse , Response response ){


                int status = httpResponse.status;

                if (status == -1){

                    String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLogin,msg, Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                } else if (status == 0) {

                   // String msg = httpResponse.error_object.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLogin,"Something went wrong. Please try again later.", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if (status == 1){

                  //  Toast.makeText(Splash.this,"contact number", Toast.LENGTH_LONG).show();


                    if (contactnumbers.size() == 0 ){


                        contactnumbers = (ArrayList<contactnumberpojo>) httpResponse.data.responseData.docs;



                    }

                    cust_support = contactnumbers.get(0).custSupportNumber;
                    order_support = contactnumbers.get(0).orderSupportNumber;

                    dbHelper.insertcontactnumbersdata(cust_support , order_support);



                    Intent i = new Intent(Splash.this, Landing_Update.class);
                    startActivity(i);
                    finish();











                }









            }









            @Override
            public void failure(RetrofitError error) {
                //progressMobile.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdLogin, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

            }




        });




    }



}