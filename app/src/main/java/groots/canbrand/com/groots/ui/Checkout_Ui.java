package groots.canbrand.com.groots.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;

import groots.canbrand.com.groots.Adapter.Checkout_Adapter;
import groots.canbrand.com.groots.Adapter.Landing_Adapter;
import groots.canbrand.com.groots.Model.LandingInfo;
import groots.canbrand.com.groots.R;

public class Checkout_Ui extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout__ui);
        ArrayList<LandingInfo> dummyValue=new ArrayList<>();
        dummyValue.add(new LandingInfo("Nasik Onion","45/kg",R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato","15/kg",R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion","45/kg",R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato","15/kg",R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion","45/kg",R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato","15/kg",R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion","45/kg",R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato","15/kg",R.drawable.potato));

        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.checkout_recycle);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Checkout_Adapter mAdapter = new Checkout_Adapter(dummyValue,this);
        mRecyclerView.setAdapter(mAdapter);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);


        }

       /* mRecyclerView.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                list_main_footer_.animate().translationY(list_main_footer_.getHeight()).setInterpolator(new AccelerateInterpolator(2));
                viewId.bringToFront();

            }

            @Override
            public void onShow() {
                list_main_footer_.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                viewId.bringToFront();
            }
        });*/

    }
}
