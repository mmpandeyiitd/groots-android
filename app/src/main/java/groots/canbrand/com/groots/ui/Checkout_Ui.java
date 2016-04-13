package groots.canbrand.com.groots.ui;

import android.Manifest;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;

import groots.canbrand.com.groots.adapter.Checkout_Adapter;
import groots.canbrand.com.groots.model.LandingInfo;
import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.interfaces.API_Interface;
import groots.canbrand.com.groots.pojo.AddOrderParent;
import groots.canbrand.com.groots.utilz.Http_Urls;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class Checkout_Ui extends AppCompatActivity implements View.OnClickListener {

    LinearLayout list_main_footer_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout__ui);
        ArrayList<LandingInfo> dummyValue = new ArrayList<>();
        list_main_footer_ = (LinearLayout) findViewById(R.id.list_main_footer_);


        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.", "45/kg", "0", R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.", "15/kg", "0", R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.", "45/kg", "0", R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.", "15/kg", "0", R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.", "45/kg", "0", R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.", "15/kg", "0", R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.", "45/kg", "0", R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.", "15/kg", "0", R.drawable.potato));


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.checkout_recycle);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Checkout_Adapter mAdapter = new Checkout_Adapter(dummyValue, this);
        mRecyclerView.setAdapter(mAdapter);
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

        ((ImageView) findViewById(R.id.makecall)).setOnClickListener(this);
        ((TextView)findViewById(R.id.checkouticon_main)).setOnClickListener(this);
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
      switch (view.getId())
      {
        case  R.id.makecall:
            makeAcall();
          break;
          case R.id.checkouticon_main:
             /* Intent intent =new Intent(Checkout_Ui.this,Thank_You_UI.class);
              startActivity(intent);
              overridePendingTransition(R.anim.from_middle, R.anim.to_middle);*/
              callAddOrderAPI();
              break;
          case R.id.backbtn:
              finish();
              break;
          default:
              break;

      }
    }

    private void callAddOrderAPI() {

        HashMap hashmap = new HashMap();
        hashmap.put("data[order_prefix]", "Test");
        hashmap.put("data[order_type]", "normal_payment");
        hashmap.put("data[shipping_charges]", 22);
        hashmap.put("data[coupon_code]", "COP3258");
        hashmap.put("data[total]", 80);
        hashmap.put("data[total_payable_amount]", 80);
        hashmap.put("data[buyer_email]", 1);
        hashmap.put("data[discount_amt]", 0);
        hashmap.put("data[product_details][0][subscribed_product_id]", 2);
        hashmap.put("data[product_details][0][base_product_id]", 1);
        hashmap.put("data[product_details][0][store_id]", 4);
        hashmap.put("data[product_details][0][store_front_id]", 1);
        hashmap.put("data[product_details][0][product_name]", "test1");
        hashmap.put("data[product_details][0][product_qty]", 1);
        hashmap.put("data[product_details][0][unit_price]", 80);
        hashmap.put("data[user_id]", 2);
        hashmap.put("data[cart_id]", 1);
        hashmap.put("data[total_shipping_charges]", 0);
        hashmap.put("data[total_tax]", 0);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);


        final CoordinatorLayout cdcheckout=(CoordinatorLayout)findViewById(R.id.cdcheckout);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        apiInterface.getAddOrderResponce("andapikey", "1.0", "1.0",AuthToken, hashmap, new Callback<AddOrderParent>() {
            @Override
            public void success(AddOrderParent addOrderParent, Response response) {

                String status=addOrderParent.getStatus();
//                String order_no=addOrderParent.getData().getOrderNo();
              //  int order_id=addOrderParent.getData().getOrderId();
                Toast.makeText(Checkout_Ui.this,"status "+status,Toast.LENGTH_SHORT).show();

                       Intent intent =new Intent(Checkout_Ui.this,Thank_You_UI.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar snackbar = Snackbar.make(cdcheckout, error.toString(), Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();
               // Toast.makeText(Checkout_Ui.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
