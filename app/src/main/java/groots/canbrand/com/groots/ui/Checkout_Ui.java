package groots.canbrand.com.groots.ui;

import android.Manifest;
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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.okhttp.OkHttpClient;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import groots.canbrand.com.groots.adapter.Checkout_Adapter;
import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.databases.DbHelper;
import groots.canbrand.com.groots.interfaces.API_Interface;
import groots.canbrand.com.groots.interfaces.UpdateCart;
import groots.canbrand.com.groots.model.CartClass;
import groots.canbrand.com.groots.pojo.AddOrderParent;
import groots.canbrand.com.groots.pojo.LandingInfo;
import groots.canbrand.com.groots.utilz.Http_Urls;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class Checkout_Ui extends AppCompatActivity implements View.OnClickListener, UpdateCart {


    LinearLayout list_main_footer_;
    Context context;
    Checkout_Adapter mAdapter;
    RecyclerView mRecyclerView;
    ArrayList<CartClass> cartClasses;
    DbHelper dbHelper;
    TextView txtamount_main;
    UpdateCart updateCart;
    ProgressBar progressLanding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout__ui);
        context=Checkout_Ui.this;
        updateCart=this;
        dbHelper=new DbHelper(context);
        dbHelper.createDb(false);

        txtamount_main=(TextView)findViewById(R.id.txtamount_main);
        list_main_footer_ = (LinearLayout) findViewById(R.id.list_main_footer_);

        progressLanding=(ProgressBar)findViewById(R.id.progressCheckout);
        progressLanding.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.checkout_recycle);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        SlideInLeftAnimator slideInRightAnimationAdapter=new SlideInLeftAnimator();
        slideInRightAnimationAdapter.setInterpolator(new OvershootInterpolator());
        slideInRightAnimationAdapter.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(slideInRightAnimationAdapter);
        cartClasses=dbHelper.order();

        if(cartClasses.size()>0) {
            mAdapter = new Checkout_Adapter(cartClasses, this, updateCart);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        mRecyclerView.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                list_main_footer_.animate().translationY(list_main_footer_.getHeight()).setInterpolator(new AccelerateInterpolator(2));
            }

            @Override
            public void onShow() {
                list_main_footer_.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
            }
        });


        float priceinDb=dbHelper.fetchTotalCartAmount();
        txtamount_main.setText(""+priceinDb);

        ((ImageView) findViewById(R.id.makecall)).setOnClickListener(this);
        ((TextView)findViewById(R.id.checkouticon_checkout)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.backbtn)).setOnClickListener(this);
    }

    private void makeAcall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(Checkout_Ui.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 9);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "9999999999"));
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + "9999999999"));
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.makecall:
                makeAcall();
                break;

            case R.id.checkouticon_checkout:
             /* Intent intent =new Intent(Checkout_Ui.this,Thank_You_UI.class);
              startActivity(intent);
              overridePendingTransition(R.anim.from_middle, R.anim.to_middle);*/
                if (cartClasses.size() > 0) {
                    callAddOrderAPI();
                } else {
                    final CoordinatorLayout cdcheckout = (CoordinatorLayout) findViewById(R.id.cdcheckout);
                    Snackbar snackbar = Snackbar.make(cdcheckout, "Please Add Something in Cart !", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }
                break;

            case R.id.backbtn:
                onBackPressed();
                break;

            default:
                break;

        }
    }

    private void callAddOrderAPI() {
        progressLanding.bringToFront();
        progressLanding.setVisibility(View.VISIBLE);

        HashMap hashmap = new HashMap();
        float total = 0;
        for (int count = 0; count < cartClasses.size(); count++) {
            hashmap.put("data[product_details][" + count + "][subscribed_product_id]", cartClasses.get(count).subscribe_prod_id);
            hashmap.put("data[product_details][" + count + "][base_product_id]", cartClasses.get(count).base_product_id);
            hashmap.put("data[product_details][" + count + "][store_id]", cartClasses.get(count).store_id);
            hashmap.put("data[product_details][" + count + "][store_front_id]", cartClasses.get(count).store_id);
            hashmap.put("data[product_details][" + count + "][product_name]", cartClasses.get(count).product_name);
            hashmap.put("data[product_details][" + count + "][product_qty]", cartClasses.get(count).product_qty);
            hashmap.put("data[product_details][" + count + "][unit_price]", cartClasses.get(count).unit_price);
            hashmap.put("data[product_details][" + count + "][tax]", 0);
            total = cartClasses.get(count).total_unit_price + total;

        }


        hashmap.put("data[order_prefix]", "Test");
        hashmap.put("data[order_type]", "normal_payment");
        hashmap.put("data[shipping_charges]", 22);
        hashmap.put("data[coupon_code]", "COP3258");
        hashmap.put("data[total]", dbHelper.fetchTotalCartAmount());
        hashmap.put("data[total_payable_amount]", dbHelper.fetchTotalCartAmount());
        hashmap.put("data[buyer_email]", 1);
        hashmap.put("data[discount_amt]", 0);
        hashmap.put("data[user_id]", 2);
        hashmap.put("data[cart_id]", 1);
        hashmap.put("data[total_shipping_charges]", 0);
        hashmap.put("data[total_tax]", 0);


        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);

        final CoordinatorLayout cdcheckout = (CoordinatorLayout) findViewById(R.id.cdcheckout);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);

        apiInterface.getAddOrderResponce("andapikey", "1.0", "1.0", AuthToken, hashmap, new Callback<AddOrderParent>() {
            @Override
            public void success(AddOrderParent addOrderParent, Response response) {

                String status = addOrderParent.getStatus();
//                String order_no=addOrderParent.getData().getOrderNo();
                //  int order_id=addOrderParent.getData().getOrderId();
                if (status.equals(0)) {
                    progressLanding.setVisibility(View.INVISIBLE);
                    Snackbar snackbar = Snackbar.make(cdcheckout, addOrderParent.getMsg(), Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }else  if (status.equals(-1)) {
                    progressLanding.setVisibility(View.INVISIBLE);
                    Snackbar snackbar = Snackbar.make(cdcheckout, addOrderParent.getMsg(), Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }  else  if (status.equals(1))
                {
                    progressLanding.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(Checkout_Ui.this, Thank_You_UI.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                    finish();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                progressLanding.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdcheckout, error.toString(), Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();
                // Toast.makeText(Checkout_Ui.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updateCart() {

        float priceinDb=dbHelper.fetchTotalCartAmount();
        if(priceinDb>0) {
           // ArrayList<CartClass> cartClasses=dbHelper.order();
            mRecyclerView.setVisibility(View.VISIBLE);
            txtamount_main.setText("" + priceinDb);
            ((LinearLayout)findViewById(R.id.llEmptyCart)).setVisibility(View.GONE);
            ((LinearLayout)findViewById(R.id.list_main_footer_)).setVisibility(View.VISIBLE);
        } else {
            txtamount_main.setText("0");
            ((LinearLayout)findViewById(R.id.llEmptyCart)).setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            ((LinearLayout)findViewById(R.id.list_main_footer_)).setVisibility(View.GONE);
        }

    }

    @Override
    public void updateTotalAmnt(int childCount) {

    }
}
