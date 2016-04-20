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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

import groots.canbrand.com.groots.adapter.Detail_Adapter;

import groots.canbrand.com.groots.adapter.Landing_Adapter;
import groots.canbrand.com.groots.databases.DbHelper;
import groots.canbrand.com.groots.interfaces.UpdateCart;
import groots.canbrand.com.groots.model.CartClass;
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
    RecyclerView detail_recycler_view;


    ArrayList<ProductListDocData> productListDocDatas;


    public  DetailFrag(){

    }
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

                int itemInCart=dbHelper.getTotalRow();
                if(itemInCart>0) {
                    Intent intent = new Intent(getActivity(), Checkout_Ui.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(context, "Oops! No item in cart", Toast.LENGTH_SHORT).show();
                }
            }
        });


        detail_recycler_view = (RecyclerView) view.findViewById(R.id.detail_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        detail_recycler_view.setLayoutManager(linearLayoutManager);
/*
        ArrayList<CartClass> cartClasses = dbHelper.getProductQty();

        for (int i = 0; i < productListDocDatas.size(); i++) {

            for (int j = 0; j < cartClasses.size(); j++) {

                if (productListDocDatas.get(i).subscribedProductId == cartClasses.get(j).subscribe_prod_id) {
                    productListDocDatas.get(i).setItemCount(cartClasses.get(j).product_qty);
                }
            }
        }

        Detail_Adapter mAdapter = new Detail_Adapter(productListDocDatas, context, updateCart);
        detail_recycler_view.setAdapter(mAdapter);*/
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


    public void onResume() {
        super.onResume();
        //    ArrayList<ProductListDocData> productListDocDatas=productListData;
        ArrayList<CartClass> cartClasses = dbHelper.getProductQty();
        if (cartClasses != null && cartClasses.size() > 0 && productListDocDatas != null) {
            for (int i = 0; i < productListDocDatas.size(); i++) {
                for (int j = 0; j < cartClasses.size(); j++) {
                    if (productListDocDatas.get(i).subscribedProductId == cartClasses.get(j).subscribe_prod_id) {
                        productListDocDatas.get(i).setItemCount(cartClasses.get(j).product_qty);
                        break;
                    } else
                        productListDocDatas.get(i).setItemCount(0);
                }
            }
        } else if (cartClasses != null && cartClasses.size() == 0) {
            for (int i = 0; i < productListDocDatas.size(); i++) {
                productListDocDatas.get(i).setItemCount(0);
            }
        }
        Detail_Adapter mAdapter = new Detail_Adapter(productListDocDatas, context, updateCart);
        detail_recycler_view.setAdapter(mAdapter);

    }



    @Override
    public void updateCart() {
        int itemInDb=dbHelper.getTotalRow();
        float priceinDb=dbHelper.fetchTotalCartAmount();
        if(itemInDb>0){
            txtCart_detail.setText(""+itemInDb);
            txtamount_detail.setText(""+priceinDb);
            ((RelativeLayout)getActivity().findViewById(R.id.rlCartDetail)).setBackgroundResource(R.drawable.cart);
        }else {
            txtCart_detail.setText("");
            txtamount_detail.setText("0");
            ((RelativeLayout)getActivity().findViewById(R.id.rlCartDetail)).setBackgroundResource(R.drawable.blank_cart);
        }
    }

    @Override
    public void updateTotalAmnt(int childCount) {

    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
