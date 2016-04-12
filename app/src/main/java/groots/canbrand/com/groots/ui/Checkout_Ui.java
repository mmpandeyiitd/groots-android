package groots.canbrand.com.groots.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import groots.canbrand.com.groots.adapter.Checkout_Adapter;
import groots.canbrand.com.groots.model.LandingInfo;
import groots.canbrand.com.groots.R;

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
              Intent intent =new Intent(Checkout_Ui.this,Thank_You_UI.class);
              startActivity(intent);
              overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
              break;
          case R.id.backbtn:
              finish();
              break;
          default:
              break;

      }
    }
}
