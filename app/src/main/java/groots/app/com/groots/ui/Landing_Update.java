package groots.app.com.groots.ui;

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
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import groots.app.com.groots.R;
import groots.app.com.groots.adapter.Detail_Adapter;
import groots.app.com.groots.adapter.Landing_Adapter;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.interfaces.API_Interface;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.model.CartClass;
import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.Order;
import groots.app.com.groots.pojo.OrderItem;
import groots.app.com.groots.pojo.Product;
import groots.app.com.groots.pojo.user_profile;
import groots.app.com.groots.utilz.Analytics;
import groots.app.com.groots.utilz.Applicationclass;
import groots.app.com.groots.utilz.ContainerHolderSingleton;
import groots.app.com.groots.utilz.Http_Urls;
import groots.app.com.groots.utilz.Utilz;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class Landing_Update extends AppCompatActivity implements View.OnClickListener, UpdateCart {




    private ShowcaseView showcaseView;
    private int caseshow = 0;
    private Target t1,t2,t3;


    Order order;
    List<OrderItem> orderItems;
    Product item;
    boolean flag = true;
    public boolean backflag = false;
    ArrayList<user_profile> retailerdetails = new ArrayList<>();
    NavigationView navigationView;
    RelativeLayout navOrder, navHelp, navContact, navRate, navLogout, navAbout, navorderHis , navaddOrder,navAllProducts;
    CoordinatorLayout cdLanding;

    ArrayList<Product> productListDocDatas = new ArrayList<>();
    int offsetValue = 1;
    RecyclerView detail_recycler_view;
    Context context;
    RelativeLayout loadermain;
    String shippingAmount;
    String cust_support_no, order_support_no;
    DrawerLayout drawer;
    DbHelper  dbHelper;
    LinearLayout listicon, callicon;

    UpdateCart updateCart;
    SwipeRefreshLayout swipe_refresh_layout;
    LinearLayoutManager linearLayoutManager;
    public boolean loadingMore = true;
    TextView txtamount_detail, txtCart_detail,shipping_amount;
    LinearLayout checkouticon;
    ImageView callimage;
    Utilz utilz;
    LinearLayout listfooter;
    private static final String  screenName = "product-list";
    private ContainerHolder containerHolder;
    private Container container;
    private DataLayer dataLayer;
    Applicationclass application;

    /*int i,j ;
    Double a[];
    int b[];
    int arr[];
    int x;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_design_landing_update);

      /*  t1 = new ViewTarget(R.id.listicon,this);
        showcaseView = new ShowcaseView.Builder(this).setTarget(Target.NONE).setOnClickListener(this).setContentTitle("List Buttton")
                .setContentText("On click this button the grid view appears.").build();

        showcaseView.setButtonText("Grid View");*/




        cdLanding = (CoordinatorLayout) findViewById(R.id.cdLanding);

        // analytics
        application = (Applicationclass) getApplication();
        Analytics.sendScreenName(screenName, application);


        containerHolder = ContainerHolderSingleton.getContainerHolder();
        container = containerHolder.getContainer();
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);


        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //swipe_refresh_layout.setVisibility(View.VISIBLE);

                detail_recycler_view.setAdapter(null);
                productListDocDatas.clear();
                Utilz.count = 0;
                offsetValue = 1;
                callProductListingAPI(offsetValue);
                loadingMore = true;



            }
        });





      //  callretailerdetailsAPI();








         Intent inten = getIntent();

        String message = inten.getStringExtra("message");









//        Log.v("HashMapTest",bpIdQuantityMap.get());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        loadermain = (RelativeLayout) findViewById(R.id.loadermain);
        loadermain.setOnClickListener(this);

       // callicon = (LinearLayout) findViewById(R.id.callicon);
        //callicon.setOnClickListener(this);
        listicon = (LinearLayout) findViewById(R.id.listicon);
        listicon.setOnClickListener(this);
        //callimage = (ImageView) findViewById(R.id.callimage);

        updateCart = this;
        context = Landing_Update.this;









        if (message != null) {

            if (message.equals("four")) {








                Snackbar snackbar = Snackbar.make(cdLanding, "Our customer support team is looking into the matter to address your grievances.We look forward to serve you better.", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();


            } else if (message.equals("five")) {


                Snackbar snackbar = Snackbar.make(cdLanding, "Thanks for the feedback. We look forward to serve you again.", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();


            }
        }




      /*  ..........................UIL Integration.......................................*/
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);



        /*.................................RecyclerView.........................*/
        detail_recycler_view = (RecyclerView) findViewById(R.id.detail_recycler_view);
        detail_recycler_view.setHasFixedSize(true);
        detail_recycler_view.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(context);



       /* .................Product API.................*/

        utilz = new Utilz();
        if (!utilz.isInternetConnected(context)) {
            Snackbar snackbar = Snackbar.make(cdLanding, "Please check the internet connection", Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();


        } else
            callProductListingAPI(offsetValue);


     /*   .................DataBase Implementation.......................*/

        dbHelper = DbHelper.getInstance(context);
        dbHelper.createDb(false);

        dbHelper.deleteOrderHeadData();

       // navOrder = (RelativeLayout) findViewById(R.id.pending_menu);
        navHelp = (RelativeLayout) findViewById(R.id.help_menu);
        navContact = (RelativeLayout) findViewById(R.id.contact_menu);
        navorderHis = (RelativeLayout) findViewById(R.id.orderHis_menu);

        navRate = (RelativeLayout) findViewById(R.id.rate_menu);
        navLogout = (RelativeLayout) findViewById(R.id.about_menu);
        navAbout = (RelativeLayout) findViewById(R.id.logout_menu);
        navaddOrder = (RelativeLayout)findViewById(R.id.addOrder_menu);
       // navaddOrder.setVisibility(View.GONE);


        //navOrder.setOnClickListener(this);
        navHelp.setOnClickListener(this);
        navAllProducts = (RelativeLayout) findViewById(R.id.allproducts_menu);
        navAllProducts.setOnClickListener(this);
        navContact.setOnClickListener(this);
        navorderHis.setOnClickListener(this);
        navRate.setOnClickListener(this);
        navLogout.setOnClickListener(this);
        navAbout.setOnClickListener(this);
        navaddOrder.setOnClickListener(this);

        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.putString("Check", "true");
        editor.commit();

        ArrayList<String> ContactNumbers  = dbHelper.selectfromcontactnumbers();
          cust_support_no = ContactNumbers.get(0);
          order_support_no = ContactNumbers.get(1);



       /* ...............................Navigation Drawer........................................*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {


                int itemInCart = dbHelper.getTotalRow();
                /*if (itemInCart > 0) {
                    navOrder.setVisibility(View.VISIBLE);
                } else
                    navOrder.setVisibility(View.GONE);*/

            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_landing__ui);
        // RelativeLayout rl_header = (RelativeLayout) headerView.findViewById(R.id.parentlayout);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

        String userName = prefs.getString("UserName", null);
        String name = "";
        if (userName.contains(" ")) {
            String fname = userName.substring(0, userName.indexOf(" "));
            String lname = userName.substring(userName.indexOf(" "), userName.length());
            name = fname.substring(0,1) + lname.substring(1,2);

        } else {
            name = userName.substring(0,1);
        }
        editor.putString("Name", name.toUpperCase().trim());
        editor.commit();

        TextView imageViewheader = (TextView) headerView.findViewById(R.id.imageViewheader);
        TextView txtViewName = (TextView) headerView.findViewById(R.id.txtViewName);
        imageViewheader.setText(prefs.getString("Name", null));
        txtViewName.setText(prefs.getString("UserName", null).substring(0, 1).toUpperCase() + prefs.getString("UserName", null).substring(1));
        ((TextView) findViewById(R.id.tooltext)).setText(prefs.getString("Retailer_Name", null));

        // navigationView.setNavigationItemSelectedListener(this);
       /* .....................ToolBar/ActionBar..............................*/
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        txtamount_detail = (TextView) findViewById(R.id.txtamount_detail);
       // shipping_amount = (TextView) findViewById(R.id.shipping_amount);
        txtCart_detail = (TextView) findViewById(R.id.txtCart_detail);
        listfooter  = (LinearLayout) findViewById(R.id.listfooter);
       /* int itemInCart = dbHelper.getTotalRow();
        if (itemInCart > 0) {
            listfooter.setVisibility(View.VISIBLE);
        } else {
            listfooter.setVisibility(View.GONE);
        }*/

        checkouticon = (LinearLayout) findViewById(R.id.checkouticon);
        checkouticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int itemInCart = dbHelper.getTotalRow();
                if (itemInCart > 0) {
                    Intent intent = new Intent(Landing_Update.this, Checkout_Ui.class);
                   // intent.putExtra("shipping",shippingAmount);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Oops! No item in cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        detail_recycler_view.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {

              //  listfooter.animate().translationY(listfooter.getHeight()).setInterpolator(new AccelerateInterpolator(2));
            }

            @Override
            public void onShow() {
               // listfooter.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

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

        swipe_refresh_layout.setRefreshing(false);



       /* Log.e("data",String.valueOf(offset));*/
        offsetValue = offset;

        HashMap hashMap = new HashMap();

        hashMap.put("limit", 10);
        hashMap.put("page", offset);
        hashMap.put("sort[title]","asc");

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

        apiInterface.getproductListingResponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, AuthToken, hashMap, new Callback<HttpResponse<Product>>() {

            @Override
            public void success(HttpResponse httpResponse, Response response) {
                loadermain.setVisibility(View.INVISIBLE);

                //  progressDialog.dismiss();
                int status = httpResponse.status;

                if (status == -1) {

                     String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                } else if (status == 0) {

                    String msg = httpResponse.msg;
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                    if (offsetValue == 2) {
                        ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.VISIBLE);
                        detail_recycler_view.setVisibility(View.GONE);
                        // ((LinearLayout) findViewById(R.id.listfooter)).setVisibility(View.GONE);

                    }



                } else if (status == 1) {

                    backflag = false;
                    ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.GONE);
                    detail_recycler_view.setVisibility(View.VISIBLE);
                    int itemInCart = dbHelper.getTotalRow();
                    if (itemInCart > 0) {
                        listfooter.setVisibility(View.VISIBLE);
                    } else {
                        listfooter.setVisibility(View.GONE);
                    }

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

                    if (httpResponse.data.responseData.docs.size() == 0) {

                        if (offsetValue == 2) {
                            ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.VISIBLE);
                            detail_recycler_view.setVisibility(View.GONE);
                            // ((LinearLayout) findViewById(R.id.listfooter)).setVisibility(View.GONE);

                        }
                        loadingMore = false;
                    } else {
                        ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.GONE);
                        detail_recycler_view.setVisibility(View.VISIBLE);

                    }

                    if (productListDocDatas.size() == 0)
                        productListDocDatas = (ArrayList<Product>) httpResponse.data.responseData.docs;
                    else {
                        Utilz.count = productListDocDatas.size();
                        for (int i = 0; i < httpResponse.data.responseData.docs.size(); i++) {
                            productListDocDatas.add((Product)httpResponse.data.responseData.docs.get(i));
                        }
                    }


                    if (flag == true) {

                        ArrayList<CartClass> cartClasses = dbHelper.getProductQty();
                        if (cartClasses != null && cartClasses.size() > 0 && httpResponse != null) {
                            for (int i = 0; i < productListDocDatas.size(); i++) {
                                for (int j = 0; j < cartClasses.size(); j++) {
                                    if (productListDocDatas.get(i).subscribedProductId == cartClasses.get(j).subscribe_prod_id) {
                                        productListDocDatas.get(i).setItemCount(cartClasses.get(j).product_qty);
                                        break;
                                    } else
                                        productListDocDatas.get(i).setItemCount(0.0);
                                }
                            }
                        } else if (cartClasses != null && cartClasses.size() == 0) {
                            for (int i = 0; i < productListDocDatas.size(); i++) {
                                productListDocDatas.get(i).setItemCount(0.0);
                            }
                        }
                        detail_recycler_view.setLayoutManager(new LinearLayoutManager(context));
                        detail_recycler_view.setHasFixedSize(true);
                        detail_recycler_view.setAdapter(new Landing_Adapter(productListDocDatas, context, updateCart, true));
                        detail_recycler_view.scrollToPosition(Utilz.count);


                    } else {

                        ArrayList<CartClass> cartClasses = dbHelper.getProductQty();
                        if (cartClasses != null && cartClasses.size() > 0 && httpResponse != null) {
                            for (int i = 0; i < productListDocDatas.size(); i++) {
                                for (int j = 0; j < cartClasses.size(); j++) {
                                    if (productListDocDatas.get(i).subscribedProductId == cartClasses.get(j).subscribe_prod_id) {
                                        productListDocDatas.get(i).setItemCount(cartClasses.get(j).product_qty);
                                        break;
                                    } else
                                        productListDocDatas.get(i).setItemCount(0.0);
                                }
                            }
                        } else if (cartClasses != null && cartClasses.size() == 0) {
                            for (int i = 0; i < productListDocDatas.size(); i++) {
                                productListDocDatas.get(i).setItemCount(0.0);
                            }
                        }



                        detail_recycler_view.setHasFixedSize(true);
                        detail_recycler_view.setLayoutManager(new LinearLayoutManager(context));
                        detail_recycler_view.setNestedScrollingEnabled(false);
                        detail_recycler_view.setAdapter(new Detail_Adapter(productListDocDatas, context, updateCart, true));
                        detail_recycler_view.scrollToPosition(Utilz.count);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

           /* SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
            editor.putString("Check", "name");
            editor.commit();*/
            super.onBackPressed();
        }
    }


    private void makeaCall(String phn) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(Landing_Update.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
        ShowDialog showdialog = new ShowDialog(this);

        /*switch (caseshow){
            case 0 :
                showcaseView.setShowcase(t1,true);
                showcaseView.setContentTitle("GRid View");
                showcaseView.setContentText("it shows products in grid view");
                break;
            case 1 :
                showcaseView.hide();
                break;
        }
        caseshow++;*/



        switch (view.getId()) {


            /*case R.id.pending_menu:

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

                        Intent intent = new Intent(context,History.class);
                        intent.putExtra("Name","Order History");
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


            case R.id.addOrder_menu:

                drawer.closeDrawer(GravityCompat.START);
                runnable = new Runnable() {
                    @Override
                    public void run() {


                    }
                };


                new android.os.Handler().postDelayed(runnable, 300);
                break;

            case R.id.listicon:

                if (flag == false) {
                    flag = true;

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Landing_Update.this);
                    detail_recycler_view.setLayoutManager(linearLayoutManager);

                    //  Landing_Adapter mAdapter = ;
                    detail_recycler_view.setAdapter(new Landing_Adapter(productListDocDatas, context, updateCart, true));
                    detail_recycler_view.scrollToPosition(Utilz.count);
                   // callimage.setImageResource(R.drawable.refresh);
                   // Log.e("Offset Value",String.valueOf(productListDocDatas.size())+String.valueOf(offsetValue)+String.valueOf(loadingMore));
                    if(offsetValue==3 && loadingMore==false & productListDocDatas.size()<5)
                    {
                        Log.e("Offset Value in inner",String.valueOf(offsetValue));
                        listfooter.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

                    }
                    //  mAdapter.hideFooter();
                    // mAdapter.notifyDataSetChanged();
                    ((ImageView) findViewById(R.id.listimage)).setImageResource(R.drawable.new_design_expanded_view);

                } else {
                    flag = false;

                    detail_recycler_view.setLayoutManager(new LinearLayoutManager(Landing_Update.this));
                    detail_recycler_view.smoothScrollToPosition(Utilz.count);
                    detail_recycler_view.setNestedScrollingEnabled(false);
                    // Detail_Adapter mAdapter = ;
                    detail_recycler_view.setAdapter(new Detail_Adapter(productListDocDatas, context, updateCart, true));
                    //  mAdapter.notifyDataSetChanged();
                   // callimage.setImageResource(R.drawable.call);
                    detail_recycler_view.scrollToPosition(Utilz.count);
                    ((ImageView) findViewById(R.id.listimage)).setImageResource(R.drawable.new_design_list_view);
                }
                break;

           /* case R.id.callicon:
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
        final Dialog logoutdialog = new Dialog(Landing_Update.this);
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
                Intent i = new Intent(Landing_Update.this, Splash.class);
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

            ((RelativeLayout) findViewById(R.id.rlCartDetail)).setBackgroundResource(R.drawable.new_design_cart_xhdpi);
        } else {

            listfooter.setVisibility(View.INVISIBLE);
            txtCart_detail.setText("");
            txtamount_detail.setText("0");
            ((RelativeLayout) findViewById(R.id.rlCartDetail)).setBackgroundResource(R.drawable.new_design_cart_xhdpi);
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
        super.onResume();

        detail_recycler_view.setAdapter(null);
        productListDocDatas.clear();
        Utilz.count = 0;
        offsetValue = 1;
        callProductListingAPI(offsetValue);
        loadingMore = true;



        // Always call the superclass method first
        //    ArrayList<ProductListDocData> productListDocDatas=productListData;
       /* Intent intent = getIntent();

        HashMap<Integer, Double> bpIdQuantityMap = (HashMap<Integer, Double>) intent.getSerializableExtra("map");



        if (bpIdQuantityMap != null & productListDocDatas.size() > 0 ){






            //ArrayList<Product> productListUpdated = new ArrayList<>();


            for (j=0;j<=productListDocDatas.size();j++) {



                a[j] = bpIdQuantityMap.get(productListDocDatas.get(j).subscribedProductId);


                for ( Integer key : bpIdQuantityMap.keySet() ) {
                    for (x=0;x<=bpIdQuantityMap.size();x++) {
                        arr[x] = key;
                    }
                }



                b[j] =a[j].intValue();
                for(x=0;x<=bpIdQuantityMap.size();x++) {

                    if (productListDocDatas.get(j).subscribedProductId == arr[x]) {
                        productListDocDatas.get(j).setItemCount(b[j]);
                    }
                }
                //productListDocDatas.setItemCount().
            }


            dbHelper.insertCartData(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId,
                    productListDocDatas.get(position).storeId, productListDocDatas.get(position).title,
                    productListDocDatas.get(position).description,
                    productListDocDatas.get(position).thumbUrl.get(0), productListDocDatas.get(position).getItemCount(),
                    productListDocDatas.get(position).storeOfferPrice,productListDocDatas.get(position).packSize.toString(),productListDocDatas.get(position).packUnit);




        }*/







        ArrayList<CartClass> cartClasses = dbHelper.getProductQty();
        if (cartClasses != null && cartClasses.size() > 0 && productListDocDatas != null) {
            for (int i = 0; i < productListDocDatas.size(); i++) {
                for (int j = 0; j < cartClasses.size(); j++) {
                    if (productListDocDatas.get(i).subscribedProductId == cartClasses.get(j).subscribe_prod_id) {
                        productListDocDatas.get(i).setItemCount(cartClasses.get(j).product_qty);
                        break;
                    } else
                        productListDocDatas.get(i).setItemCount(0.0);
                }
            }
        } else if (cartClasses != null && cartClasses.size() == 0) {
            for (int i = 0; i < productListDocDatas.size(); i++) {
                productListDocDatas.get(i).setItemCount(0.0);
            }
        }
        if (flag == true & backflag == false & productListDocDatas.size() != 0) {
            Landing_Adapter mAdapter = new Landing_Adapter(productListDocDatas, context, updateCart, true);
            detail_recycler_view.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else if (flag == false & backflag == false & productListDocDatas.size() != 0) {
            Detail_Adapter detailAdapter = new Detail_Adapter(productListDocDatas, context, updateCart, true);
            detail_recycler_view.setLayoutManager(new LinearLayoutManager(context));
            detail_recycler_view.setNestedScrollingEnabled(false);
            detail_recycler_view.setAdapter(detailAdapter);
            //  detailAdapter.notifyDataSetChanged();
        }
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







}
