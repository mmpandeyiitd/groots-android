package groots.canbrand.com.groots.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.utilz.Utilz;

public class Splash extends AppCompatActivity implements AnimationListener, OnClickListener{


    ImageView ivGroots,ivCallLogin;
    AlphaAnimation alphaAnimation;
    KenBurnsView kbv;
    Animation animationmoveup, animationmovebt;
    LinearLayout llUserName, llPassword;
    //Button btnSignIn;
    EditText etLogin, etPassword;
    CoordinatorLayout cdLogin;
    Context context;
    TextView tvForgetPass;
   // RippleView btnSignIn;
   LayoutRipple btnSignIn;

    String storePhoneNo="1234567899";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

      /*  btnSignIn = (RippleView)findViewById(R.id.btnSignIn);
        btnSignIn.setRippleDuration(200);*/

        btnSignIn = (LayoutRipple) findViewById(R.id.btnSignIn);
        setOriginRiple(btnSignIn);

        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(false);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        // set the transparent color of the status bar, 20% darker
        tintManager.setTintColor(Color.parseColor("#20000000"));
//        toolbars=(Toolbar)findViewById(R.id.toolbars);
//        toolbars.setBackgroundColor(getResources().getColor(android.R.color.transparent));

       /* LayoutRipple linearLogin = (LayoutRipple) findViewById(R.id.btnSignIn);
        setOriginRiple(linearLogin);*/

        context=Splash.this;
        cdLogin=(CoordinatorLayout)findViewById(R.id.cdLogin);
        ivGroots=(ImageView)findViewById(R.id.ivGroots);
        ivCallLogin=(ImageView)findViewById(R.id.ivCallLogin);
        kbv = (KenBurnsView) findViewById(R.id.image);
        llUserName=(LinearLayout)findViewById(R.id.llUserName);
        llPassword=(LinearLayout)findViewById(R.id.llPassword);
       // btnSignIn=(Button)findViewById(R.id.btnSignIn);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etLogin=(EditText)findViewById(R.id.etLogin);
        tvForgetPass=(TextView)findViewById(R.id.tvForgetPass);
        tvForgetPass.setOnClickListener(this);

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
                ((View) findViewById(R.id.viewBlur)).setVisibility(View.VISIBLE);
                llUserName.setVisibility(View.VISIBLE);
                llPassword.setVisibility(View.VISIBLE);
                btnSignIn.setVisibility(View.VISIBLE);
                ivCallLogin.setVisibility(View.VISIBLE);
                tvForgetPass.setVisibility(View.VISIBLE);
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
            public void onTransitionStart(Transition transition) { }
            @Override
            public void onTransitionEnd(Transition transition) { }
        });


        btnSignIn.setOnClickListener(this);
        ivCallLogin.setOnClickListener(this);
    }

    private void setOriginRiple(LayoutRipple linearLogin) {

        btnSignIn.post(new Runnable() {

            @Override
            public void run() {
                btnSignIn.setRippleColor(Color.parseColor("#142C16"));
                btnSignIn.setRippleSpeed(30);
            }
        });
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

               /* btnSignIn.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {*/
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
                           /* if (!utilz.isInternetConnected(context)) {

                                Snackbar snackbar = Snackbar.make(cdLogin, "Please check the internet connection", Snackbar.LENGTH_SHORT);
                                snackbar.setActionTextColor(Color.WHITE);
                                View snackbarView = snackbar.getView();
                                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                snackbar.show();
                            } else {*/
                                Intent i = new Intent(Splash.this, Landing_UI.class);
                                startActivity(i);
                                finish();
                           // }
                        }

                  // }

                /*});*/








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

        final Dialog dialog = new Dialog(Splash.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_forgot_password);
        dialog.setCancelable(true);

        final CoordinatorLayout cdForgetPwd=(CoordinatorLayout)dialog.findViewById(R.id.cdForgetPwd);

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
                }
                else
                {
                    dialog.cancel();
                }

            }
        });

        dialog.show();
    }




}
