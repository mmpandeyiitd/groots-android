package groots.canbrand.com.groots.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import groots.canbrand.com.groots.adapter.Detail_Adapter;
import groots.canbrand.com.groots.model.LandingInfo;
import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.ui.Checkout_Ui;
import groots.canbrand.com.groots.ui.HidingScrollListener;


public class DetailFrag extends Fragment {

    TextView txtamount_detail,txtCart_detail;
    ImageView checkouticon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        final LinearLayout listfooter=(LinearLayout)view.findViewById(R.id.listfooter);
        txtamount_detail=(TextView)view.findViewById(R.id.txtamount_detail);
        txtCart_detail=(TextView)view.findViewById(R.id.txtCart_detail);

        checkouticon=(ImageView)view.findViewById(R.id.checkouticon);
        checkouticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), Checkout_Ui.class);
                startActivity(intent);
            }
        });




        ArrayList<LandingInfo> dummyValue = new ArrayList<>();
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.","445", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "900 kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.","1", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "900 kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.","485", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "900 kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.","15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.","45", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.","15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.","45", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.","15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.","445", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "900 kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.","1", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "900 kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.","485", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "900 kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.","15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.","45", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.","15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.","45", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.","15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.","445", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "900 kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.","1", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "900 kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.","485", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "900 kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.","15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.","45", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.","15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.","45", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "10 kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.","15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10 kg"));



        RecyclerView detail_recycler_view = (RecyclerView) view.findViewById(R.id.detail_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        detail_recycler_view.setLayoutManager(linearLayoutManager);
        detail_recycler_view.setHasFixedSize(true);
        Detail_Adapter mAdapter = new Detail_Adapter(dummyValue, getActivity());
        detail_recycler_view.setAdapter(mAdapter);
        detail_recycler_view.smoothScrollToPosition(0);

        detail_recycler_view.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                listfooter.animate().translationY(listfooter.getHeight()).setInterpolator(new AccelerateInterpolator(2));
            }

            @Override
            public void onShow() {
                listfooter.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

            }
        });


        return view;

    }


}
