package groots.app.com.groots.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import groots.app.com.groots.R;
import groots.app.com.groots.adapter.historyList_Adapter;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.databases.dbHelp;
import groots.app.com.groots.interfaces.API_Interface;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.pojo.DateTimePojo;
import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.Order;
import groots.app.com.groots.pojo.OrderItem;
import groots.app.com.groots.pojo.Product;
import groots.app.com.groots.utilz.ContainerHolderSingleton;
import groots.app.com.groots.utilz.Http_Urls;
import groots.app.com.groots.utilz.Utilz;
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
    Double shippingcharges;
    RelativeLayout navOrder, navHelp, navContact, navRate, navLogout, navAbout, navHome,navAllProducts,navorderHis;
    String cust_support_no, order_support_no;
    CoordinatorLayout cdLanding;
    ArrayList<Order> productListDocDatas = new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();
    Context context;
    RelativeLayout loadermain;
    DrawerLayout drawer;
    String date;
    DbHelper dbHelper;
    TextView cancel_yes ,cancel_no;
    dbHelp dbHelp;
    String add;
    Utilz util;
    TextView shippingtxtamount_main, subtxtamount_main;
    Dialog dialog;
    LinearLayout listicon, callicon;
    int offsetValue = 0;
    int or_id;
    static int position;
    RecyclerView detail_recycler_view;
    UpdateCart updateCart;
    LinearLayoutManager linearLayoutManager;
    public boolean loadingMore = true;
    TextView txtamount_detail, txtCart_detail , updateicon_checkout ,cancelbutton,download_invoice, total_amount;
    LinearLayout checkouticon , navback;
    ImageView callimage;
    Utilz utilz;
    LinearLayout listfooter;
    HashMap<Integer,Double> bpIdQuantityMap = new HashMap<Integer,Double>();
    private static final String  screenName = "order-update-view";
    private ContainerHolder containerHolder;
    private Container container;
    private DataLayer dataLayer;


    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_design_history_list);

        containerHolder = ContainerHolderSingleton.getContainerHolder();
        container = containerHolder.getContainer();
        dataLayer = TagManager.getInstance(this).getDataLayer();
        dataLayer.push(DataLayer.mapOf("event", "openScreen", "screenName", screenName));
        dataLayer.push(DataLayer.mapOf("event", "screenVisible", "screenName", screenName));

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
        cancelbutton = (TextView) findViewById(R.id.cancelicon_checkout);
        cancelbutton.setOnClickListener(this);
        cancelbutton.setVisibility(View.GONE);
        //download_invoice = (TextView) findViewById(R.id.download_invoice);
      //  download_invoice.setOnClickListener(this);
       // download_invoice.setVisibility(View.GONE);
        updateicon_checkout = (TextView) findViewById(R.id.updateicon_checkout);
        updateicon_checkout.setOnClickListener(this);
        updateicon_checkout.setVisibility(View.GONE);


        shippingtxtamount_main = (TextView) findViewById(R.id.shippingtxtamount_main);
        subtxtamount_main = (TextView) findViewById(R.id.subtxtamount_main);





        add = "same activity";
            CallDateTimeApi( add );











       TextView total_amount = (TextView) findViewById(R.id.total_amount);
        total_amount.setText(t_am);

        //callicon = (LinearLayout) findViewById(R.id.callicon);
        //callicon.setOnClickListener(this);
        //listicon = (LinearLayout) findViewById(R.id.listicon);
        //listicon.setOnClickListener(this);
       // callimage = (ImageView) findViewById(R.id.callimage);

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


        dbHelper =  DbHelper.getInstance(context);
        dbHelper.createDb(false);

        dbHelp = new dbHelp(context);
        dbHelp.createDb(false);

       // navOrder = (RelativeLayout) findViewById(R.id.pending_menu);
        navHelp = (RelativeLayout) findViewById(R.id.help_menu);
        navContact = (RelativeLayout) findViewById(R.id.contact_menu);
        navHome = (RelativeLayout) findViewById(R.id.addOrder_menu);
        navorderHis = (RelativeLayout) findViewById(R.id.orderHis_menu);
        navRate = (RelativeLayout) findViewById(R.id.rate_menu);
        navLogout = (RelativeLayout) findViewById(R.id.about_menu);
        navAbout = (RelativeLayout) findViewById(R.id.logout_menu);
        navback = (LinearLayout) findViewById(R.id.backbtn);

        navAllProducts = (RelativeLayout) findViewById(R.id.allproducts_menu);
        navAllProducts.setOnClickListener(this);

      //  navOrder.setOnClickListener(this);
        navHelp.setOnClickListener(this);
        navContact.setOnClickListener(this);
        navHome.setOnClickListener(this);
        navorderHis.setOnClickListener(this);
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


        ArrayList<String> ContactNumbers  = dbHelper.selectfromcontactnumbers();
        cust_support_no = ContactNumbers.get(0);
        order_support_no = ContactNumbers.get(1);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {


                int itemInCart = dbHelper.getTotalRow();
               /* if (itemInCart > 0) {
                    navOrder.setVisibility(View.VISIBLE);
                } else
                    navOrder.setVisibility(View.GONE);*/

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


      /*  String dlvrDate = intent.getStringExtra("datee");
        String status = intent.getStringExtra("Status");
        //Date today = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       // Date date_today = new Date();
        Date dt = Calendar.getInstance().getTime();


        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.HOUR, 23);
       Date d_t = c.getTime();



        String str_today = dateFormat.format(d_t);
        Date delv_date = null;
        Date date_today = null;
        try{
            delv_date =   dateFormat.parse(dlvrDate);
            date_today = dateFormat.parse(str_today);
        }
        catch (java.text.ParseException e){
            System.out.println("exception in formattind deliver date");
        }*/
        // new Date(dlvrDate);

        //Integer i = date_today.compareTo(delv_date);







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

        if (offset == 0) {
            loadermain.setVisibility(View.VISIBLE);
            //loadermainfooter.setVisibility(View.INVISIBLE);
        }
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);

        apiInterface.getorderitemListingResponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, AuthToken, hashMap, new Callback<HttpResponse<Order>>() {

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

                   /* if (offsetValue == 10) {
                        ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.VISIBLE);
                        detail_recycler_view.setVisibility(View.GONE);
                        // ((LinearLayout) findViewById(R.id.listfooter)).setVisibility(View.GONE);

                    }*/


                } else if (status == 1) {

                   // backflag = false;
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
                         shippingcharges = order.shippingCharges;
                        Double subtotal = order.total;
                        shippingtxtamount_main.setText(shippingcharges.toString());
                        subtxtamount_main.setText(subtotal.toString());

                        List<OrderItem> orderItems = order.orderItems;
                        for(OrderItem item:orderItems ){
                            bpIdQuantityMap.put(item.subscribedProductId, item.productQty );
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



                        //HashMap<Integer, Double> bpIdQuantityMap = (HashMap<Integer, Double>) intent.getSerializableExtra("map");













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
        offsetValue = offsetValue + row;
        //offsetValue++;
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






















          /*  case R.id.pending_menu:

                drawer.closeDrawer(GravityCompat.START);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(context, Checkout_Ui.class);
                        startActivity(intent);
                    }
                };
                new android.os.Handler().postDelayed(runnable, 300);

                break;*/
            case R.id.allproducts_menu:

                drawer.closeDrawer(GravityCompat.START);
               Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Intent inten = new Intent(context , mapping.class);
                        startActivity(inten);

                    }
                };
                new android.os.Handler().postDelayed(runnable , 300);
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

            case R.id.orderHis_menu:

                drawer.closeDrawer(GravityCompat.START);
                runnable = new Runnable() {
                    @Override
                    public void run() {

                        onBackPressed();

                    }
                };


                new android.os.Handler().postDelayed(runnable, 300);
                break;




            case R.id.addOrder_menu:
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



           /* case R.id.cancelicon_checkout:

                final String o_id = intent.getStringExtra("OrderId");
                HashMap hashmap = new HashMap();
                hashmap.put("orderId" , o_id );

                History.callHistoryListingAPI(hashmap );*/








           // int itemInDb = dbHelp.getTotalRow();

            case R.id.updateicon_checkout:







                 add = "new activity";
                CallDateTimeApi(add);


                String datee = date ;
              //  System.out.println(date);

                //String str_today = dateFormat.format(date_today);

                break;


            case R.id.cancelicon_checkout:

                runnable = new Runnable() {
                    @Override
                    public void run() {




                        canceldialog();








                    }
                };
                new android.os.Handler().postDelayed(runnable, 300);

                break;

          /*  case R.id.download_invoice:

                runnable = new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = getIntent();

                        final String o_i = intent.getStringExtra("OrderId");




                       // callemailinvoiceAPI(o_i);








                    }
                };
                new android.os.Handler().postDelayed(runnable, 300);

                break;
*/





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



    //@Override
    private void callcancelorderAPI(HashMap hashmap)
    {
       /* RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Http_Urls.sBaseUrl ).setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
       API_Interface apiInterface = restAdapter.create(API_Interface.class);*/



        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);


        apiInterface.getpatchorderresponse(Utilz.apikey , Utilz.app_version,Utilz.config_version,AuthToken,hashmap,new Callback<HttpResponse>(){

            @Override
            public void success (HttpResponse httpResponse ,Response response ){
               int status =  httpResponse.status;
                if (status == -1){
                    Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if(status == 0){

                    Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if(status == 1)  {

                    dialog.dismiss();















                }







            }
            @Override
            public void failure(RetrofitError error) {
                //loaderlayout.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdLanding, " Something went wrong. Please try again.", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

            }


    });



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
            ((TextView) findViewById(R.id.customer_support)).setText(cust_support_no);
            ((TextView) findViewById(R.id.ordering_support)).setText(order_support_no);
            ((LinearLayout) findViewById(R.id.orderSupport)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    makeaCall(order_support_no);
                    dismiss();
                }
            });
            ((LinearLayout) findViewById(R.id.custsupport)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeaCall(cust_support_no);
                    dismiss();
                }
            });
        }
    }



    void canceldialog(){

        dialog = new Dialog(historyList.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.new_design_cancel_order_dialog);

        TextView cancel_yes =  (TextView) dialog.findViewById(R.id.cancel_yes);
        //cancel_yes.setOnClickListener(this);
        TextView cancel_no = (TextView) dialog.findViewById(R.id.cancel_no);


        dialog.setCancelable(true);






       dialog.show();






        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });






        cancel_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = getIntent();

                final String o_i = intent.getStringExtra("OrderId");
                //String order_i;
                HashMap hashmap = new HashMap();
                hashmap.put("orderId",o_i);


                callcancelorderAPI(hashmap);
                dialog.dismiss();
                Toast.makeText(historyList.this,"Your order has been cancelled successfully.", Toast.LENGTH_SHORT).show();
                Intent inten = new Intent(historyList.this, History.class);
                inten.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //inten.putExtra("show cancel snackbar","yes");
                startActivity(inten);




                // callcancelorderAPI();

            }
        });


        cancel_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


    }




    public void callemailinvoiceAPI(String orderId){

        HashMap hashmap = new HashMap();
        hashmap.put("order_id",orderId);


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Http_Urls.sBaseUrl).setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME",MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken",null);

        apiInterface.getemailinvoiceresponse(Utilz.apikey , Utilz.app_version , Utilz.config_version , AuthToken ,hashmap, new Callback<HttpResponse>(){

            @Override
            public void success (HttpResponse httpResponse , Response response){


                int status = httpResponse.status;

                if (status == -1){


                    Snackbar snackbar =  Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later.",Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if (status == 0){

                    Snackbar snackbar =  Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later.",Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if (status == 1){






                    Toast.makeText(context,"Your invoice has been emailed to you. Please check your email.",Toast.LENGTH_SHORT).show();





                }




            }

            @Override
            public void failure (RetrofitError error){


                Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong. Please try again.", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();





            }



        });










    }




    private void CallDateTimeApi(final String add) {


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);

        apiInterface.getTime(Utilz.apikey, Utilz.app_version, Utilz.config_version, AuthToken, new Callback<DateTimePojo>() {
            @Override
            public void success(DateTimePojo dateTimePojo, Response response) {

                int status = dateTimePojo.getStatus();
//
                if (status == 0) {
                    //   Toast.makeText(Checkout_Ui.this, addOrderParent.getMsg(), Toast.LENGTH_SHORT).show();
                   /* loaderlayout.setVisibility(View.INVISIBLE);*/
                    Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else if (status == -1) {
                    //loaderlayout.setVisibility(View.INVISIBLE);
                    Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else if (status == 1) {
                    date = dateTimePojo.getData().getCurrentDateTime();


                    if (add == "same activity") {

                        Intent intent = getIntent();

                        final String o_i = intent.getStringExtra("OrderId");


                        String dlvrDate = intent.getStringExtra("datee");
                        String statu = intent.getStringExtra("Status");
                        //Date today = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date delv_date = null;
                        Date date_today = null;
                        try {
                            delv_date = dateFormat.parse(dlvrDate);
                            date_today = dateFormat.parse(date);
                        } catch (java.text.ParseException e) {
                            System.out.println("exception in formatting deliver date");
                        }

                        if (statu.equals("Delivered")){
                         //   download_invoice.setVisibility(View.VISIBLE);
                            updateicon_checkout.setVisibility(View.GONE);
                            cancelbutton.setVisibility(View.GONE);

                        }


                        if (delv_date != null && (date_today.compareTo(delv_date) == 0) && (statu.equals("Pending"))) {
                            updateicon_checkout.setVisibility(View.VISIBLE);
                           // download_invoice.setVisibility(View.GONE);

                        }
                        if (delv_date != null && (date_today.compareTo(delv_date) == 0 ) && (statu.equals("Pending"))){
                            cancelbutton.setVisibility(View.VISIBLE);
                          //  download_invoice.setVisibility(View.GONE);
                        }



                    }
                    else if (add == "new activity"){

Intent inten = getIntent();

                        final String o_i = inten.getStringExtra("OrderId");

                        String dlvrDate = inten.getStringExtra("datee");
                        String stat = inten.getStringExtra("Status");
                        //Date today = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        Date delv_date = null;
                        Date date_today = null;
                        try{
                            delv_date =   dateFormat.parse(dlvrDate);
                            date_today = dateFormat.parse(date);
                        }
                        catch (java.text.ParseException e){
                            System.out.println("exception in formatting deliver date");
                        }








                        // dbHelp.deleterec();

                        // int y;


                        List<OrderItem> orderItems = productListDocDatas.get(i).orderItems;

                    /*if ( bpIdQuantityMap.keySet().contains(orderItems.get(i).product.subscribedProductId)) {

                        Double a = bpIdQuantityMap.get(orderItems.get(i).product.subscribedProductId);
                        ///   y = a.intValue();


                        productListDocDatas.get(i).setItemCount(a.intValue());*/




                        dbHelp.deleterec();

                        for (int j = 0; j < orderItems.size(); j++) {

                           /* HashMap hash = new HashMap();
                            hash.put("what", "insert");*/
                            if (orderItems.get(j).baseProductId == null){
                                orderItems.get(j).baseProductId = 0 ;
                            }
                            if (orderItems.get(j).product.storeId == null){
                                orderItems.get(j).product.storeId = 0;

                            }
                            if (orderItems.get(j).product.description == null){
                                orderItems.get(j).product.description = "null";
                            }


                            dbHelp.insertUpdateCartData( orderItems.get(j).subscribedProductId,orderItems.get(j).baseProductId ,
                                    orderItems.get(j).product.storeId, orderItems.get(j).product.title,
                                    orderItems.get(j).product.description,
                                    orderItems.get(j).product.thumbUrl.get(0), orderItems.get(j).productQty,
                                    orderItems.get(j).unitPrice, orderItems.get(j).product.packSize.toString(), orderItems.get(j).product.packUnit);
                        }








                        if (stat.equals("Delivered")){
                         //   download_invoice.setVisibility(View.VISIBLE);
                            updateicon_checkout.setVisibility(View.GONE);
                            cancelbutton.setVisibility(View.GONE);

                        }








                        // new Date(dlvrDate);

                        //Integer i = date_today.compareTo(delv_date);

                        if (delv_date != null && ( date_today.compareTo(delv_date) == 0 ) && (stat.equals("Pending"))) {




//                  Double Quan = bpIdQuantityMap.get(orderItems.get(position).subscribedProductId);

                            //Integer  a = bpIdQuantityMap.get(orderItems.get(position).productQty);


                            // int s_id = orderItems.get(position).product.subscribedProductId;
                            //      double quan = orderItems.get(position).productQty;

                            updateicon_checkout.setVisibility(View.VISIBLE);


                                  /* updateicon_checkout.setOnClickListener(new View.OnClickListener() {
                                                                              @Override
                                                                              public void onClick(View view) {*/


                            Intent intent = new Intent(context, UpdateOrder.class);
                           String shippingcharge = shippingcharges.toString();


                            intent.putExtra("map", bpIdQuantityMap);
                            intent.putExtra("orderId", o_i);
                            intent.putExtra("datee",date);
                            intent.putExtra("shipping",shippingcharge);


                                                                                  //intent.putExtra("Name", "About Groots");
                            startActivity(intent);
                                                                         /*     }
                                                                          });*/



                           //     }


                            //new android.os.Handler().postDelayed(runnable, 300);
                        }










                    }










                   // ((TextView) findViewById(R.id.txtdate)).setText("Order Summary - " + date.substring(0,10).trim());
                    //  ((TextView)dialog.findViewById(R.id.datetxtd)).setText(date.substring(0,10).trim());



                    // String dat = date.substring(0,11);


                   /* try {
                        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
                        Date d = f.parse(date);
                        DateFormat dat = new SimpleDateFormat("yyyy/MM/dd");
                        DateFormat time = new SimpleDateFormat("hh:mm:ss");
                        System.out.println("Date: " + dat.format(d));
                        System.out.println("Time: " + time.format(d));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/





                    //  Toast.makeText(context, date, Toast.LENGTH_SHORT).show();
                    /*try {
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                        Date d1 = formatter.parse(date.substring(11).trim());
                        Date d2 = formatter.parse("00:00:00");
                        Date d3 = formatter.parse("02:00:00");

                      //  Log.e("Incoming date",date.substring(0,10));.



                            final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");


                        if (d1.before(d3) & d1.after(d2)) {

                            float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, getResources().getDisplayMetrics());
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.headerdate);
                            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) pixels);
                            layout.setLayoutParams(parms);
                            ((TextView) findViewById(R.id.txtdate)).setText("Order Summary - " + date.substring(0,10).trim());
                           // ((TextView) findViewById(R.id.datetxt)).setText("(Orders placed between 2AM to 9AM might not be processed)");
                            ((TextView)dialog.findViewById(R.id.datetxtd)).setText(date.substring(0,10).trim());
                            data = dateFormatter.parse(date.substring(0,10).trim()).toString();
                        } else {

                            float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics());
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.headerdate);
                            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) pixels);
                            layout.setLayoutParams(parms);
                            ((TextView) findViewById(R.id.datetxt)).setVisibility(View.GONE);
                            ((TextView) findViewById(R.id.txtdate)).setText("Order Summary - " + (date.substring(0,10).trim()));


                            newCalendar.setTime(dateFormatter.parse(date.substring(0,10).trim()));
                            newCalendar.add(Calendar.DATE, 1);
                            ((TextView) dialog.findViewById(R.id.datetxtd)).setText(dateFormatter.format(newCalendar.getTime()).toString());
                            data = dateFormatter.format(newCalendar.getTime());

                        }
                    } catch (Exception e) {
                       // Log.e("Exception is ",e.getCause().toString());
                    }*/
                }

            }

            @Override
            public void failure(RetrofitError error) {
                //loaderlayout.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong. Please try again.", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

            }
        });


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





