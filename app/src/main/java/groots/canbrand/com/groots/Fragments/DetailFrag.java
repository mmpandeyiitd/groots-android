package groots.canbrand.com.groots.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;

import groots.canbrand.com.groots.Adapter.Detail_Adapter;
import groots.canbrand.com.groots.Model.LandingInfo;
import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.Ui.HidingScrollListener;


public class DetailFrag extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        final LinearLayout listfooter=(LinearLayout)view.findViewById(R.id.listfooter);

        ArrayList<LandingInfo> dummyValue = new ArrayList<>();
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.", "45", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "10kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.", "15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.", "45", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "10kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.", "15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.", "45", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "10kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.", "15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10kg"));
        dummyValue.add(new LandingInfo("Nasik Onion", "Grade A Onion Sourced From Nasik.", "45", "0", R.drawable.onion, "Nasik Onion", "5cm", "Red", "A", "10kg"));
        dummyValue.add(new LandingInfo("Big Potato", "Grade A Potato Sourced From India.", "15", "0", R.drawable.potato, "Nasik Onion", "5cm", "Red", "A", "10kg"));


        RecyclerView detail_recycler_view = (RecyclerView) view.findViewById(R.id.detail_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        detail_recycler_view.setHasFixedSize(true);
        detail_recycler_view.setLayoutManager(linearLayoutManager);
        Detail_Adapter mAdapter = new Detail_Adapter(dummyValue, getActivity());
        detail_recycler_view.setAdapter(mAdapter);

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
