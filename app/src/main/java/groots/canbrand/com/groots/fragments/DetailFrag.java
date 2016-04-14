package groots.canbrand.com.groots.fragments;

import android.app.Fragment;
import android.content.Context;
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
import groots.canbrand.com.groots.databases.DbHelper;
import groots.canbrand.com.groots.interfaces.UpdateCart;
import groots.canbrand.com.groots.model.CartClass;
import groots.canbrand.com.groots.model.LandingInfo;
import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.pojo.ProductListDocData;
import groots.canbrand.com.groots.ui.Checkout_Ui;
import groots.canbrand.com.groots.ui.HidingScrollListener;
import groots.canbrand.com.groots.ui.Landing_UI;


public class DetailFrag extends Fragment implements UpdateCart{

    TextView txtamount_detail,txtCart_detail;
    ImageView checkouticon;
    DbHelper dbHelper;
    Context context;
    UpdateCart updateCart;

    ArrayList<ProductListDocData> productListDocDatas;


    public DetailFrag(ArrayList<ProductListDocData> productListDocDatas){
        this.productListDocDatas=productListDocDatas;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        this.context= Landing_UI.context;
        dbHelper=new DbHelper(context);
        dbHelper.createDb(false);
        updateCart=this;

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


        RecyclerView detail_recycler_view = (RecyclerView) view.findViewById(R.id.detail_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        detail_recycler_view.setLayoutManager(linearLayoutManager);

        ArrayList<CartClass> cartClasses = dbHelper.getProductQty();

        for (int i = 0; i < productListDocDatas.size(); i++) {

            for (int j = 0; j < cartClasses.size(); j++) {

                if (productListDocDatas.get(i).subscribedProductId == cartClasses.get(j).subscribe_prod_id) {
                    productListDocDatas.get(i).setItemCount(cartClasses.get(j).product_qty);
                }
            }
        }

        Detail_Adapter mAdapter = new Detail_Adapter(productListDocDatas, context, updateCart);
        detail_recycler_view.setAdapter(mAdapter);
        detail_recycler_view.setHasFixedSize(true);


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

        int itemInDb=dbHelper.getTotalRow();
        float priceinDb=dbHelper.fetchTotalCartAmount();
        if(itemInDb>0){
            txtCart_detail.setText(""+itemInDb);
            txtamount_detail.setText(""+priceinDb);
        }

        return view;

    }


    @Override
    public void updateCart() {
        int itemInDb=dbHelper.getTotalRow();
        float priceinDb=dbHelper.fetchTotalCartAmount();
        if(itemInDb>0){
            txtCart_detail.setText(""+itemInDb);
            txtamount_detail.setText(""+priceinDb);
        }
    }

    @Override
    public void updateTotalAmnt(int childCount) {

    }
}
