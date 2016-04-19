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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import groots.canbrand.com.groots.adapter.Landing_Adapter;

import groots.canbrand.com.groots.databases.DbHelper;
import groots.canbrand.com.groots.interfaces.UpdateCart;
import groots.canbrand.com.groots.model.CartClass;

import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.pojo.ProductListDocData;
import groots.canbrand.com.groots.ui.Checkout_Ui;
import groots.canbrand.com.groots.ui.HidingScrollListener;
import groots.canbrand.com.groots.ui.Landing_UI;


public class MainFrag extends Fragment implements UpdateCart {


    LinearLayout list_main_footer_;
    TextView txtCart_main,txtamount_main;
    ImageView checkouticon_main;
    View viewId;
    public static RecyclerView mRecyclerView;

    ArrayList<ProductListDocData> productListData;
    UpdateCart updateCart;

    Context context;


    public MainFrag() {

    }

    public MainFrag(ArrayList<ProductListDocData> productListData){
        this.productListData=productListData;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_main, container, false);
        list_main_footer_=(LinearLayout)view.findViewById(R.id.list_main_footer_);
        viewId=(View)view.findViewById(R.id.viewid);
        context= Landing_UI.context;
        final DbHelper dbHelper=new DbHelper(context);
        dbHelper.createDb(false);
        updateCart=this;

        mRecyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);
        txtCart_main=(TextView)view.findViewById(R.id.txtCart_main);
        checkouticon_main=(ImageView)view.findViewById(R.id.checkouticon_main);
        txtamount_main=(TextView)view.findViewById(R.id.txtamount_main);
        checkouticon_main.setOnClickListener(new View.OnClickListener() {
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

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<CartClass> cartClasses = dbHelper.getProductQty();
        if(cartClasses!=null && cartClasses.size()>0 && productListData!=null){
            for (int i = 0; i < productListData.size(); i++) {

                for (int j = 0; j < cartClasses.size(); j++) {

                    if (productListData.get(i).subscribedProductId == cartClasses.get(j).subscribe_prod_id) {
                        productListData.get(i).setItemCount(cartClasses.get(j).product_qty);
                    }
                }
            }
        }


        Landing_Adapter mAdapter = new Landing_Adapter(productListData,context, updateCart);
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

        int itemInDb=dbHelper.getTotalRow();
        float priceinDb=dbHelper.fetchTotalCartAmount();
        if(itemInDb>0){
            txtCart_main.setText(""+itemInDb);
            txtamount_main.setText(""+priceinDb);
        }
        return view;
    }




    @Override
    public void updateCart() {
        DbHelper dbHelper=new DbHelper(context);
        dbHelper.createDb(false);
        int itemInDb=dbHelper.getTotalRow();
        float priceinDb=dbHelper.fetchTotalCartAmount();
        if(itemInDb>0){
            txtCart_main.setText(""+itemInDb);
            txtamount_main.setText(""+priceinDb);
            ((RelativeLayout)getActivity().findViewById(R.id.rlCartMain)).setBackgroundResource(R.drawable.cart);
        }else {
            txtCart_main.setText("");
            txtamount_main.setText("0");
            ((RelativeLayout)getActivity().findViewById(R.id.rlCartMain)).setBackgroundResource(R.drawable.blank_cart);
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
