package groots.canbrand.com.groots.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import groots.canbrand.com.groots.Adapter.Detail_Adapter;
import groots.canbrand.com.groots.Adapter.Landing_Adapter;
import groots.canbrand.com.groots.Model.LandingInfo;
import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.ui.Checkout_Ui;
import groots.canbrand.com.groots.ui.HidingScrollListener;


public class MainFrag extends Fragment {

    LinearLayout list_main_footer_;
    TextView txtCart_main,txtamount_main;
    ImageView checkouticon_main;
    View viewId;


    public MainFrag() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_main, container, false);
        ArrayList<LandingInfo> dummyValue=new ArrayList<>();
        list_main_footer_=(LinearLayout)view.findViewById(R.id.list_main_footer_);
        viewId=(View)view.findViewById(R.id.viewid);


        txtCart_main=(TextView)view.findViewById(R.id.txtCart_main);
        checkouticon_main=(ImageView)view.findViewById(R.id.checkouticon_main);
        txtamount_main=(TextView)view.findViewById(R.id.txtamount_main);
        checkouticon_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), Checkout_Ui.class);
                startActivity(intent);
            }
        });

        dummyValue.add(new LandingInfo("Nasik Onion","Grade A Onion Sourced From Nasik.","45/kg","0",R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato","Grade A Potato Sourced From India.","15/kg","0",R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion","Grade A Onion Sourced From Nasik.","45/kg","0",R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato","Grade A Potato Sourced From India.","15/kg","0",R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion","Grade A Onion Sourced From Nasik.","45/kg","0",R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato","Grade A Potato Sourced From India.","15/kg","0",R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion","Grade A Onion Sourced From Nasik.","45/kg","0",R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato","Grade A Potato Sourced From India.","15/kg","0",R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion","Grade A Onion Sourced From Nasik.","45/kg","0",R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato","Grade A Potato Sourced From India.","15/kg","0",R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion","Grade A Onion Sourced From Nasik.","45/kg","0",R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato","Grade A Potato Sourced From India.","15/kg","0",R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion","Grade A Onion Sourced From Nasik.","45/kg","0",R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato","Grade A Potato Sourced From India.","15/kg","0",R.drawable.potato));
        dummyValue.add(new LandingInfo("Nasik Onion","Grade A Onion Sourced From Nasik.","45/kg","0",R.drawable.onion));
        dummyValue.add(new LandingInfo("Big Potato","Grade A Potato Sourced From India.","15/kg","0",R.drawable.potato));

        RecyclerView mRecyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Landing_Adapter mAdapter = new Landing_Adapter(dummyValue,getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnScrollListener(new HidingScrollListener() {
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
        });

        return view;
    }

   /* @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return AnimationUtils.loadAnimation(getActivity(),
                enter ? android.R.anim.fade_in : android.R.anim.fade_out);
    }
*/


}
