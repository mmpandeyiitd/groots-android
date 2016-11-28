package groots.canbrand.com.groots.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.adapter.historyList_Adapter;
import groots.canbrand.com.groots.databases.DbHelper;
import groots.canbrand.com.groots.interfaces.API_Interface;
import groots.canbrand.com.groots.interfaces.UpdateCart;
import groots.canbrand.com.groots.pojo.HttpResponse;
import groots.canbrand.com.groots.pojo.Order;
import groots.canbrand.com.groots.pojo.OrderItem;
import groots.canbrand.com.groots.pojo.Product;
import groots.canbrand.com.groots.utilz.Http_Urls;
import groots.canbrand.com.groots.utilz.Utilz;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class historyList extends AppCompatActivity implements View.OnClickListener ,UpdateCart {


    Order order;
    List<OrderItem> orderItems;
    Product item;

    boolean flag = true;
    public boolean backflag = false;
    NavigationView navigationView;
    RelativeLayout navOrder, navHelp, navContact, navRate, navLogout, navAbout, navHome;
    CoordinatorLayout cdLanding;
    ArrayList<Order> productListDocDatas = new ArrayList<>();
    Context context;
    RelativeLayout loadermain;
    DrawerLayout drawer;
    DbHelper dbHelper;
    LinearLayout listicon, callicon;
    int offsetValue = 0;
    int or_id;
    static int position;
    RecyclerView detail_recycler_view;
    UpdateCart updateCart;
    LinearLayoutManager linearLayoutManager;
    public boolean loadingMore = true;
    TextView txtamount_detail, txtCart_detail , updateicon_checkout , total_amount;
    LinearLayout checkouticon , navback;
    ImageView callimage;
    Utilz utilz;
    LinearLayout listfooter;
    HashMap<Integer,Double> bpIdQuantityMap = new HashMap<Integer,Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);


        //this.order = orders.get(0);
//        this.orderItems = this.order.orderItems;


//        int s_id = orderItems.get(position).product.subscribedProductId;
  //      double quan = orderItems.get(position).productQty;

        Intent intent = getIntent();

        String o_id = intent.getStringExtra("OrderId");
        String t_am = intent.getStringExtra("totalpayableamount");
        //String dlvrDate = intent.getStringExtra("datee");
        //double text = Double.parseDouble(t_am);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        loadermain = (RelativeLayout) findViewById(R.id.loadermain);
        loadermain.setOnClickListener(this);
        updateicon_checkout = (TextView) findViewById(R.id.updateicon_checkout);
        updateicon_checkout.setOnClickListener(this);
        updateicon_checkout.setVisibility(View.INVISIBLE);



        String dlvrDate = intent.getStringExtra("datee");
        String status = intent.getStringExtra("Status");
        //Date today = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date_today = new Date();
        String str_today = dateFormat.format(date_today);
        Date delv_date = null;
        try{
            delv_date =   dateFormat.parse(dlvrDate);
        }
        catch (java.text.ParseException e){
            System.out.println("exception in formattind deliver date");
        }
        // new Date(dlvrDate);

        //Integer i = date_today.compareTo(delv_date);

        if (delv_date != null && ( date_today.compareTo(delv_date) < 0 ) && (status.equals("Pending"))) {

            updateicon_checkout.setVisibility(View.VISIBLE);
        }

       TextView total_amount = (TextView) findViewById(R.id.total_amount);
        total_amount.setText(t_am);

        //callicon = (LinearLayout) findViewById(R.id.callicon);
        //callicon.setOnClickListener(this);
        //listicon = (LinearLayout) findViewById(R.id.listicon);
        //listicon.setOnClickListener(this);
        callimage = (ImageView) findViewById(R.id.callimage);

        updateCart = this;

        context = historyList.this;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        /*.................................RecyclerView.........................*/
        detail_recycler_view = (RecyclerView) findViewById(R.id.detail_recycler_view);
        detail_recycler_view.setHasFixedSize(true);
        detail_recycler_view.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(context);


        /* .................Product API.................*/
        cdLanding = (CoordinatorLayout) findViewById(R.id.cdLanding);
        utilz = new Utilz();
        if (!utilz.isInternetConnected(context)) {
            Snackbar snackbar = Snackbar.make(cdLanding, "Please check the internet connection", Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();


        } else
            callProductListingAPI(offsetValue);


        dbHelper = new DbHelper(context);
        dbHelper.createDb(false);


        navOrder = (RelativeLayout) findViewById(R.id.pending_menu);
        navHelp = (RelativeLayout) findViewById(R.id.help_menu);
        navContact = (RelativeLayout) findViewById(R.id.contact_menu);
        navHome = (RelativeLayout) findViewById(R.id.Home_menu);
        navRate = (RelativeLayout) findViewById(R.id.rate_menu);
        navLogout = (RelativeLayout) findViewById(R.id.about_menu);
        navAbout = (RelativeLayout) findViewById(R.id.logout_menu);
        navback = (LinearLayout) findViewById(R.id.backbtn);

        navOrder.setOnClickListener(this);
        navHelp.setOnClickListener(this);
        navContact.setOnClickListener(this);
        navHome.setOnClickListener(this);
        navRate.setOnClickListener(this);
        navLogout.setOnClickListener(this);
        navAbout.setOnClickListener(this);
        navback.setOnClickListener(this);


        ((LinearLayout)findViewById(R.id.backbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

                //onResume();
            }
        });


        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.putString("Check", "true");
        editor.commit();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {


                int itemInCart = dbHelper.getTotalRow();
                if (itemInCart > 0) {
                    navOrder.setVisibility(View.VISIBLE);
                } else
                    navOrder.setVisibility(View.GONE);

            }
        };


        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_landing__ui);


        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

        String userName = prefs.getString("UserName", null);
        String name = "";
        if (userName.contains(" ")) {
            String fname = userName.substring(0, userName.indexOf(" "));
            String lname = userName.substring(userName.indexOf(" "), userName.length());
            name = fname.substring(0, 1) + lname.substring(1, 2);

        } else {
            name = userName.substring(0, 1);
        }
        editor.putString("Name", name.toUpperCase().trim());
        editor.commit();

        TextView imageViewheader = (TextView) headerView.findViewById(R.id.imageViewheader);
        TextView txtViewName = (TextView) headerView.findViewById(R.id.txtViewName);
        imageViewheader.setText(prefs.getString("Name", null));
        txtViewName.setText(prefs.getString("UserName", null).substring(0, 1).toUpperCase() + prefs.getString("UserName", null).substring(1));
        //((TextView) findViewById(R.id.tooltext)).setText(prefs.getString("Retailer_Name", null));


        /* .....................ToolBar/ActionBar..............................*/
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        txtamount_detail = (TextView) findViewById(R.id.txtamount_detail);
        txtCart_detail = (TextView) findViewById(R.id.txtCart_detail);
        listfooter = (LinearLayout) findViewById(R.id.listfooter);


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


        detail_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                Utilz.count = layoutManager.findFirstCompletelyVisibleItemPosition();
             /*   Log.e("count", String.valueOf(offsetValue));*/
                if (loadingMore) {
                    //position starts at 0
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                        callProductListingAPI(offsetValue);
                    }
                }
            }
        });

    }


    private void callProductListingAPI(final int offset) {

        Intent intent = getIntent();

        String o_id = intent.getStringExtra("OrderId");
        //String t_am = intent.getStringExtra("totalpayableamount");


       /* Log.e("data",String.valueOf(offset));*/
        offsetValue = offset;
        int orderidd = Integer.parseInt(o_id);
        int row = 10;

        HashMap hashMap = new HashMap();

        hashMap.put("rows", row);
        hashMap.put("start", offset);
        hashMap.put("sort[title]", "asc");
        hashMap.put("order_id",orderidd);

        Log.e("Sending Data", hashMap.toString());

        if (offset == 1) {
            loadermain.setVisibility(View.VISIBLE);
            //loadermainfooter.setVisibility(View.INVISIBLE);
        }
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);

        apiInterface.getorderitemListingResponse("andapikey", "1.0", "1.0", AuthToken, hashMap, new Callback<HttpResponse<Order>>() {

            @Override
            public void success(HttpResponse httpResponse, Response response) {
                loadermain.setVisibility(View.INVISIBLE);

                //  progressDialog.dismiss();
                int status = httpResponse.status;

                if (status == -1) {

                    String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding, msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                } else if (status == 0) {

                    String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding, msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                    if (offsetValue == 10) {
                        ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.VISIBLE);
                        detail_recycler_view.setVisibility(View.GONE);
                        // ((LinearLayout) findViewById(R.id.listfooter)).setVisibility(View.GONE);

                    }


                } else if (status == 1) {

                    backflag = false;
                    ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.GONE);
                    detail_recycler_view.setVisibility(View.VISIBLE);
                    /*int itemInCart = dbHelper.getTotalRow();
                    if (itemInCart > 0) {
                        listfooter.setVisibility(View.VISIBLE);
                    } else {
                        listfooter.setVisibility(View.GONE);
                    }*/

                    //  Log.e("incoming data",productListData.data.response.docs.toString());
             /*       ArrayList<CartClass> cartClasses = dbHelper.getProductQty();

                    for (int i = 0; i < productListDocDatas.size(); i++) {

                        for (int j = 0; j < cartClasses.size(); j++) {

                            if (productListDocDatas.get(i).subscribedProductId == cartClasses.get(j).subscribe_prod_id) {

                                productListDocDatas.get(i).setItemCount(cartClasses.get(j).product_qty);
                            }
                        }
                    }*/
                    //  Log.e("incoming data",productListData.data.response.docs.toString());

                    if (httpResponse.data.responseData.docs == null || httpResponse.data.responseData.docs.size() == 0) {

                        if (offsetValue == 10) {
                            ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.VISIBLE);
                            detail_recycler_view.setVisibility(View.GONE);
                            // ((LinearLayout) findViewById(R.id.listfooter)).setVisibility(View.GONE);

                        }
                        loadingMore = false;
                    } else {
                        ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.GONE);
                        detail_recycler_view.setVisibility(View.VISIBLE);
                        Utilz.count = productListDocDatas.size();
                        for (int i = 0; i < httpResponse.data.responseData.docs.size(); i++) {
                            productListDocDatas.add((Order) httpResponse.data.responseData.docs.get(i));
                        }

                        Order order = productListDocDatas.get(0);
                        List<OrderItem> orderItems = order.orderItems;
                        for(OrderItem item:orderItems ){
                            bpIdQuantityMap.put(item.subscribedProductId, item.productQty);
                        }
                    }



                    /*if (productListDocDatas.size() == 0)
                        productListDocDatas = (ArrayList<Order>) httpResponse.data.responseData.docs;*/
                    /*else {
                        Utilz.count = productListDocDatas.size();
                        for (int i = 0; i < httpResponse.data.responseData.docs.size(); i++) {
                            productListDocDatas.add((Order)httpResponse.data.responseData.docs.get(i));
                        }
                    }*/


                    if (flag == true) {

                        /*ArrayList<CartClass> cartClasses = dbHelper.getProductQty();
                        if (cartClasses != null && cartClasses.size() > 0 && httpResponse != null) {
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
                        }*/
                        detail_recycler_view.setLayoutManager(new LinearLayoutManager(context));
                        detail_recycler_view.setHasFixedSize(true);
                        detail_recycler_view.setNestedScrollingEnabled(false);
                        historyList_Adapter historyList_adapter = new historyList_Adapter(productListDocDatas, context, updateCart, true);
                        //bpIdQuantityMap = historyList_adapter.getMap();
                        detail_recycler_view.setAdapter(historyList_adapter);
                        detail_recycler_view.scrollToPosition(Utilz.count);


                    } else {
                       /* detail_recycler_view.setHasFixedSize(true);
                        detail_recycler_view.setLayoutManager(new LinearLayoutManager(context));
                        detail_recycler_view.setNestedScrollingEnabled(false);
                        detail_recycler_view.setAdapter(new historyList_Detail_Adapter(productListDocDatas, context, updateCart, true));
                        detail_recycler_view.scrollToPosition(Utilz.count);*/
                    }


                }


            }


            @Override
            public void failure(RetrofitError error) {

                loadermain.setVisibility(View.INVISIBLE);

                Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);
                Log.e("Error", String.valueOf(error.getCause()));
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

            }
        });

        offsetValue++;
        //   Log.e("No of Page ", String.valueOf(offsetValue));
    }


    @Override
    public void onBackPressed() {

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {*/

        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.putString("Check", "name");
        editor.commit();
        super.onBackPressed();
    //}
    }


    private void makeaCall(String phn) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(historyList.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 9);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phn));
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phn));
            startActivity(intent);
        }

    }



    @Override
    public void onClick(View view) {

        Intent intent = getIntent();

        historyList.ShowDialog showdialog = new historyList.ShowDialog(this);
        switch (view.getId()) {



            case R.id.pending_menu:

                drawer.closeDrawer(GravityCompat.START);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(context, Checkout_Ui.class);
                        startActivity(intent);
                    }
                };
                new android.os.Handler().postDelayed(runnable, 300);

                break;

            case R.id.help_menu:

                drawer.closeDrawer(GravityCompat.START);
                runnable = new Runnable() {
                    @Override
                    public void run() {

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                                Uri.fromParts("mailto", "letstalk@gogroots.com", null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                                "Please Share your thoughts...");
                        startActivity(Intent
                                .createChooser(emailIntent, "Send email..."));
                    }
                };
                new android.os.Handler().postDelayed(runnable, 300);

                break;

            case R.id.contact_menu:
                drawer.closeDrawer(GravityCompat.START);

                showdialog.show();

                break;

            case R.id.rate_menu:

                drawer.closeDrawer(GravityCompat.START);
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (utilz.isInternetConnected(context)) {
                            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());


                            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            // To count with Play market backstack, After pressing back button,
                            // to taken back to our application, we need to add following flags to intent.
                            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                    Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            try {
                                startActivity(goToMarket);
                            } catch (ActivityNotFoundException e) {
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("https://play.google.com/store/apps/details?id=groots.canbrand.com.groots\n" +
                                                "https://goo.gl/7eJwzP" + context.getPackageName())));
                            }
                        } else
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();

                    }
                };
                new android.os.Handler().postDelayed(runnable, 300);

                break;

            case R.id.about_menu:

                drawer.closeDrawer(GravityCompat.START);
                runnable = new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(context, WebPage.class);
                        intent.putExtra("Name", "About Groots");
                        startActivity(intent);
                    }
                };
                new android.os.Handler().postDelayed(runnable, 300);

                break;


            case R.id.Home_menu:
                drawer.closeDrawer(GravityCompat.START);
                runnable = new Runnable() {
                    @Override
                    public void run() {

                       onBackPressed();
                        finish();
                        //finish();
                        Intent intent = new Intent(context, Landing_Update.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //intent.putExtra("Name", "About Groots");
                        startActivity(intent);
                    }
                };


                new android.os.Handler().postDelayed(runnable, 300);
                break;

            case R.id.logout_menu:
                //  Toast.makeText(this,"Logout Menu Pressed",Toast.LENGTH_SHORT).show();
                logoutPopUp();
                drawer.closeDrawer(GravityCompat.START);
                break;




            case R.id.updateicon_checkout:


                String dlvrDate = intent.getStringExtra("datee");
                String status = intent.getStringExtra("Status");
                //Date today = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date_today = new Date();
                String str_today = dateFormat.format(date_today);
                Date delv_date = null;
                try{
                    delv_date =   dateFormat.parse(dlvrDate);
                }
                catch (java.text.ParseException e){
                    System.out.println("exception in formattind deliver date");
                }
                // new Date(dlvrDate);

                //Integer i = date_today.compareTo(delv_date);

                if (delv_date != null && ( date_today.compareTo(delv_date) < 0 ) && (status.equals("Pending"))) {




//                  Double Quan = bpIdQuantityMap.get(orderItems.get(position).subscribedProductId);

                   //Integer  a = bpIdQuantityMap.get(orderItems.get(position).productQty);


                   // int s_id = orderItems.get(position).product.subscribedProductId;
                    //      double quan = orderItems.get(position).productQty;

                    updateicon_checkout.setVisibility(View.VISIBLE);
                     runnable = new Runnable() {
                         @Override
                         public void run() {

                             Intent intent = new Intent(context, UpdateOrder.class);





                             intent.putExtra("map",bpIdQuantityMap);

                             //intent.putExtra("Name", "About Groots");
                             startActivity(intent);
                         }
                     };

                     new android.os.Handler().postDelayed(runnable, 300);
                 }

                break;


            /*case R.id.backbtn:

                runnable = new Runnable() {
                    @Override
                    public void run() {

                           onResume();
                        Intent intent = new Intent(context, History.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //intent.putExtra("Name", "About Groots");
                        startActivity(intent);
                    }
                };
                new android.os.Handler().postDelayed(runnable, 300);

                break;*/





            /*case R.id.listicon:

                if (flag == false) {
                    flag = true;

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(historyList.this);
                    detail_recycler_view.setLayoutManager(linearLayoutManager);

                    //  historyList_Adapter mAdapter = ;
                    detail_recycler_view.setAdapter(new historyList_Adapter(productListDocDatas, context, updateCart, true));
                    detail_recycler_view.scrollToPosition(Utilz.count);
                    callimage.setImageResource(R.drawable.refresh);
                    // Log.e("Offset Value",String.valueOf(productListDocDatas.size())+String.valueOf(offsetValue)+String.valueOf(loadingMore));
                    if (offsetValue == 3 && loadingMore == false & productListDocDatas.size() < 5) {
                        Log.e("Offset Value in inner", String.valueOf(offsetValue));
                        listfooter.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

                    }
                    //  mAdapter.hideFooter();
                    // mAdapter.notifyDataSetChanged();
                    ((ImageView) findViewById(R.id.listimage)).setImageResource(R.drawable.expanded_list_view);

                } else {
                    flag = false;

                    detail_recycler_view.setLayoutManager(new LinearLayoutManager(historyList.this));
                    detail_recycler_view.smoothScrollToPosition(Utilz.count);
                    detail_recycler_view.setNestedScrollingEnabled(false);
                    // Detail_Adapter mAdapter = ;
                    detail_recycler_view.setAdapter(new historyList_Detail_Adapter(productListDocDatas, context, updateCart, true));
                    //  mAdapter.notifyDataSetChanged();
                    callimage.setImageResource(R.drawable.call);
                    detail_recycler_view.scrollToPosition(Utilz.count);
                    ((ImageView) findViewById(R.id.listimage)).setImageResource(R.drawable.list_view);
                }
                break;*/

            /*case R.id.callicon:
                if (flag == true) {

                    if (!utilz.isInternetConnected(context)) {
                        Snackbar snackbar = Snackbar.make(cdLanding, "Please check the internet connection", Snackbar.LENGTH_SHORT);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    } else {

                        detail_recycler_view.setAdapter(null);
                        productListDocDatas.clear();
                        Utilz.count = 0;
                        offsetValue = 1;
                        callProductListingAPI(offsetValue);
                        loadingMore = true;
                    }

                    //  detail_recycler_view.scrollToPosition(0);

                } else {

                    showdialog.show();
                }
                break;*/
            default:
                break;
        }
        // drawer.closeDrawer(GravityCompat.START);

    }


    private void logoutPopUp() {
        final Dialog logoutdialog = new Dialog(historyList.this);
        logoutdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logoutdialog.setContentView(R.layout.logout_layout);
        logoutdialog.setCancelable(true);
        TextView logout = (TextView) logoutdialog.findViewById(R.id.logout);
        TextView cancel = (TextView) logoutdialog.findViewById(R.id.cancel);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                editor.putString("Check", null);
                editor.commit();

                Utilz.count = 0;
                // File cache = getCacheDir();
                dbHelper.deleterec();
                // File appDir = new File(cache.getParent());
              /*  if (appDir.exists()) {
                    String[] children = appDir.list();

                    for (String s : children) {

                        File f = new File(appDir, s);
                        if (deleteDir(f))
                            System.out.println("delete" + f.getPath());
                    }
                }*/
                Intent i = new Intent(historyList.this, Splash.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutdialog.dismiss();
            }
        });
        logoutdialog.show();
    }


    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    @Override
    public void updateCart() {

        int itemInDb = dbHelper.getTotalRow();
        float priceinDb = dbHelper.fetchTotalCartAmount();

        // Log.e("Incoming price",String.valueOf(dbHelper.fetchTotalCartAmount()));
        if (itemInDb > 0) {
            listfooter.setVisibility(View.VISIBLE);
            txtCart_detail.setText("" + itemInDb);
            txtamount_detail.setText("" + priceinDb);

            if (txtCart_detail.getText().length() == 1) {
                txtCart_detail.setTextSize(11);
            } else if (txtCart_detail.getText().length() == 2) {
                txtCart_detail.setTextSize(10);
            } else if (txtCart_detail.getText().length() == 3) {
                txtCart_detail.setTextSize(8);
                txtCart_detail.setText("99+");
            }

            ((RelativeLayout) findViewById(R.id.rlCartDetail)).setBackgroundResource(R.drawable.cart);
        } else {

            listfooter.setVisibility(View.INVISIBLE);
            txtCart_detail.setText("");
            txtamount_detail.setText("0");
            ((RelativeLayout) findViewById(R.id.rlCartDetail)).setBackgroundResource(R.drawable.blank_cart);
        }
    }

    @Override
    public void updateTotalAmnt(int childCount) {

    }

    public void resumeAPI() {

        if (!utilz.isInternetConnected(context)) {
            Snackbar snackbar = Snackbar.make(cdLanding, "Please check the internet connection", Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();

        } else if (productListDocDatas.size() > 0) {

            detail_recycler_view.setAdapter(null);
            productListDocDatas.clear();
            Utilz.count = 0;
            offsetValue = 1;
            callProductListingAPI(offsetValue);
            loadingMore = true;

            //  Log.e("Duplicate data Firing", "Resume API");
        }
    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        //    ArrayList<ProductListDocData> productListDocDatas=productListData;
       /* ArrayList<CartClass> cartClasses = dbHelper.getProductQty();
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
        }*/
        if (flag == true & backflag == false & productListDocDatas.size() != 0) {
            historyList_Adapter mAdapter = new historyList_Adapter(productListDocDatas, context, updateCart, true);
            detail_recycler_view.setAdapter(mAdapter);
           // bpIdQuantityMap = mAdapter.getMap();
            mAdapter.notifyDataSetChanged();
        } /*else if (flag == false & backflag == false & productListDocDatas.size() != 0) {
            historyList_Detail_Adapter historyListdetailAdapter = new historyList_Detail_Adapter(productListDocDatas, context, updateCart, true);
            detail_recycler_view.setLayoutManager(new LinearLayoutManager(context));
            detail_recycler_view.setNestedScrollingEnabled(false);
            detail_recycler_view.setAdapter(historyListdetailAdapter);
            //  detailAdapter.notifyDataSetChanged();
        }*/
    }

    private class ShowDialog extends Dialog {


        public ShowDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.phone_dialog);
            ((LinearLayout) findViewById(R.id.orderSupport)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    makeaCall("+91-11-3958-9893");
                    dismiss();
                }
            });
            ((LinearLayout) findViewById(R.id.custsupport)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeaCall("+91-11-3958-8984");
                    dismiss();
                }
            });
        }
    }


}




















        /*Intent i=getIntent();
        Bundle bundle=i.getExtras();
        ((TextView)findViewById(R.id.headername)).setText(bundle.getString("Name"));
        WebView webView = (WebView) findViewById(R.id.webView2);
        webView.getSettings().setJavaScriptEnabled(true);


        ((LinearLayout)findViewById(R.id.backbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        webView.setWebViewClient(new WebViewClient());



        if(bundle.getString("Name").equals("Order History"))
        {



























        }
        else
            webView.loadUrl("http://gogroots.com/#contact");*/





