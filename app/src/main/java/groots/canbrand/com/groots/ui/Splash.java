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
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.okhttp.OkHttpClient;
import java.util.HashMap;
import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.interfaces.API_Interface;
import groots.canbrand.com.groots.pojo.ForgetPwdData;
import groots.canbrand.com.groots.pojo.LoginData;
import groots.canbrand.com.groots.utilz.Http_Urls;
import groots.canbrand.com.groots.utilz.Utilz;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Response;


public class Splash extends AppCompatActivity implements AnimationListener, OnClickListener{



    ImageView ivGroots,ivCallLogin;
    AlphaAnimation alphaAnimation;
    KenBurnsView kbv;
    Animation animationmoveup, animationmovebt;
    LinearLayout llUserName, llPassword;
    EditText etLogin, etPassword;
    CoordinatorLayout cdLogin, cdForgetPwd;
    TextView tvForgetPass;
    RelativeLayout progressMobile;

    Button btnSignIn;
    String storePhoneNo="1234567899";
    View viewUser, viewPass;
    Dialog dialog;
    Context context;

    int status;
    private int mProgress;
    boolean isLoadingDone = false;
    SharedPreferences prefs;

    android.os.Handler  handler=new android.os.Handler();
    Runnable            runnable;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnSignIn.setEnabled(true);
        btnSignIn.setText("Sign In");
        progressMobile=(RelativeLayout)findViewById(R.id.progressMobile);
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
        ivGroots = (ImageView) findViewById(R.id.ivGroots);
        ivCallLogin = (ImageView) findViewById(R.id.ivCallLogin);
        kbv = (KenBurnsView) findViewById(R.id.image);
        llUserName = (LinearLayout) findViewById(R.id.llUserName);
        llPassword = (LinearLayout) findViewById(R.id.llPassword);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etLogin = (EditText) findViewById(R.id.etLogin);
        viewUser = findViewById(R.id.viewUser);
        viewPass = findViewById(R.id.viewPass);
        tvForgetPass = (TextView) findViewById(R.id.tvForgetPass);
        tvForgetPass.setOnClickListener(this);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        if (prefs.getString("AuthToken", null) != null & prefs.getString("Check",null)!=null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Splash.this, Landing_UI.class);
                    startActivity(i);
                    finish();
                }
            };
            handler.postDelayed(runnable, 2200);
        } else {
            if (bundle != null) {

                    moveup();
                    moveupTextField();
                    String sender = bundle.getString("sender");

                    // Toast.makeText(this,"logout",Toast.LENGTH_LONG).show();
                    kbv.setImageResource(R.drawable.bck_blur);
                    llUserName.setVisibility(View.VISIBLE);
                    llPassword.setVisibility(View.VISIBLE);
                    btnSignIn.setVisibility(View.VISIBLE);
                    ivCallLogin.setVisibility(View.VISIBLE);
                    tvForgetPass.setVisibility(View.VISIBLE);
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
                        kbv.setImageResource(R.drawable.bck_blur);
                        llUserName.setVisibility(View.VISIBLE);
                        llPassword.setVisibility(View.VISIBLE);
                        btnSignIn.setVisibility(View.VISIBLE);
                        ivCallLogin.setVisibility(View.VISIBLE);
                        tvForgetPass.setVisibility(View.VISIBLE);
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
    }




    protected void moveup() {
        animationmoveup= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.moveup);
        animationmoveup.setAnimationListener(this);
        animationmoveup.setFillAfter(true);
        animationmoveup.setRepeatCount(-1);
        animationmoveup.setRepeatMode(Animation.REVERSE);
        ivGroots.startAnimation(animationmoveup);

    }


    protected void moveupTextField() {
        animationmovebt= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movebottomtocenter);
        animationmovebt.setAnimationListener(this);
        animationmovebt.setFillAfter(true);
        animationmovebt.setRepeatCount(-1);
        animationmovebt.setRepeatMode(Animation.REVERSE);
        llUserName.startAnimation(animationmovebt);
        llPassword.startAnimation(animationmovebt);
        btnSignIn.startAnimation(animationmovebt);
        tvForgetPass.startAnimation(animationmovebt);
        viewUser.startAnimation(animationmovebt);
        viewPass.startAnimation(animationmovebt);
    }


    @Override
    public void onAnimationStart(Animation animation) {   }

    @Override
    public void onAnimationEnd(Animation animation) { }

    @Override
    public void onAnimationRepeat(Animation animation) {  }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ivCallLogin:

                makeCall();
                break;

            case R.id.btnSignIn:



                Utilz utilz = new Utilz();

                String strEmail = etLogin.getText().toString();
                String strPwd = etPassword.getText().toString();

                if (strEmail.length() <= 0) {
                    Snackbar snackbar = Snackbar.make(cdLogin, "Please enter email", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else if (!utilz.isValidEmail1(strEmail)) {
                    Snackbar snackbar = Snackbar.make(cdLogin, "Please enter a valid email", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else if (strPwd.length() <= 0) {
                    Snackbar snackbar = Snackbar.make(cdLogin, "Please enter password", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else if (strPwd.length() <= 3) {
                    Snackbar snackbar = Snackbar.make(cdLogin, "Please enter password with minimum 4 character long", Snackbar.LENGTH_SHORT);
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

                    }  else {

                        btnSignIn.setEnabled(false);

                        HashMap hashMap=new HashMap();
                        hashMap.put("email",  strEmail);
                        hashMap.put("password", strPwd);

                        callLoginAPI(hashMap);
                    }

                }



                break;

            case R.id.tvForgetPass:

                showForgetPwdDialog();

                break;
        }
    }

    void makeCall() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(Splash.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 9);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + storePhoneNo));
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + storePhoneNo));
            startActivity(intent);
        }

    }


    void showForgetPwdDialog(){

        dialog = new Dialog(Splash.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_forgot_password);
        dialog.setCancelable(true);

        cdForgetPwd=(CoordinatorLayout)dialog.findViewById(R.id.cdForgetPwd);

        LinearLayout submitforgot=(LinearLayout)dialog.findViewById(R.id.submitforgot);
        submitforgot.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Utilz utilz=new Utilz();
                String strEmail=((EditText)dialog.findViewById(R.id.etForgetPwd)).getText().toString();

                if(strEmail==null || strEmail.trim().length()<=0){
                    Snackbar snackbar = Snackbar.make(cdForgetPwd, "Please enter email", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }else
                if (!utilz.isValidEmail1(strEmail)) {
                    Snackbar snackbar = Snackbar.make(cdForgetPwd, "Please enter a valid email", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }else
                {
                    HashMap hashMap=new HashMap();
                    hashMap.put("email", strEmail);
                    callForgetPasswordAPI(hashMap);

                }

            }
        });

        dialog.show();
    }




    void callLoginAPI(HashMap hashMap){
        progressMobile.setVisibility(View.VISIBLE);

        isLoadingDone = false;
      //  btnSignIn.setText("Loading..");


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);


        apiInterface.getloginResponse("andapikey", "1.0", "1.0", hashMap, new Callback<LoginData>() {
            @Override
            public void success(LoginData loginData, Response response) {

                progressMobile.setVisibility(View.INVISIBLE);

                btnSignIn.setEnabled(true);

                if(loginData!=null){

                    status=loginData.getStatus();

                    if(status==-1){
                       // btnSignIn.setText("Sign In");
                        String msg=loginData.getMsg();
                        Snackbar snackbar = Snackbar.make(cdLogin, msg, Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    }else
                    if(status==0){
                       // btnSignIn.setText("Sign In");
                        String msg=loginData.getMsg();
                        Snackbar snackbar = Snackbar.make(cdLogin, msg, Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    }else if(status==1)
                    {
                        if(loginData.getData()!=null) {

                            SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                            String authToken="";
                            for (Header header : response.getHeaders()) {
                                if (header.getName().equals("AUTH_TOKEN"))
                                    authToken = header.getValue();
                                editor.putString("AuthToken",authToken);
                                editor.commit();
                            }

                            editor.putString("Retailer_Name",loginData.getData().getRetailerName());
                            editor.putString("UserName",loginData.getData().getName());
                            editor.commit();
                         //   btnSignIn.setText("Success");

                            Intent i = new Intent(Splash.this, Landing_UI.class);
                            startActivity(i);
                            finish();
                            Toast.makeText(Splash.this,"You have logged in successfully!", Toast.LENGTH_SHORT).show();
                        }else
                        {
                        //    btnSignIn.setText("Sign In");
                            Snackbar snackbar = Snackbar.make(cdLogin, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                            snackbar.setActionTextColor(Color.WHITE);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        }
                    }
                }
                else {
                    btnSignIn.setEnabled(true);
                   // btnSignIn.setText("Sign In");
                    Snackbar snackbar = Snackbar.make(cdLogin, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressMobile.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdLogin, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();
                btnSignIn.setEnabled(true);
              //  btnSignIn.setText("Sign In");
            }
        });
    }


    void callForgetPasswordAPI(HashMap hashMap){

        progressMobile.setVisibility(View.VISIBLE);
        progressMobile.bringToFront();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);

        apiInterface.getForgetPwdResponse("andapikey", "1.0", "1.0", hashMap, new Callback<ForgetPwdData>() {
            @Override
            public void success(ForgetPwdData forgetPwdData, Response response) {
                if (forgetPwdData != null) {

                    progressMobile.setVisibility(View.INVISIBLE);
                    status = forgetPwdData.getStatus();

                    if (status == -1) {
                        String msg = forgetPwdData.getMsg();
                        Snackbar snackbar = Snackbar.make(cdForgetPwd, msg, Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    } else if (status == 0) {
                        String msg = forgetPwdData.getMsg();
                        Snackbar snackbar = Snackbar.make(cdForgetPwd, msg, Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    } else if (status == 1) {
                        String msg = forgetPwdData.getMsg();
                        Snackbar snackbar = Snackbar.make(cdForgetPwd, msg, Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    }
                } else {
                    Snackbar snackbar = Snackbar.make(cdForgetPwd, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
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