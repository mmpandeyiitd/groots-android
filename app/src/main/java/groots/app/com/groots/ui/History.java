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

import groots.app.com.groots.R;
//import groots.canbrand.com.groots.adapter.Detail_Adapter;
import groots.app.com.groots.adapter.History_Adapter;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.interfaces.API_Interface;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.pojo.Order;
import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.user_profile;
import groots.app.com.groots.utilz.ContainerHolderSingleton;
import groots.app.com.groots.utilz.Http_Urls;
import groots.app.com.groots.utilz.Utilz;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;


/**
 * Created by aakash on 28/10/16.
 */

public class History extends AppCompatActivity implements View.OnClickListener     {
    boolean flag = true;
    public boolean backflag = false;
    NavigationView navigationView;

    ArrayList<user_profile> retailerdetails = new ArrayList<>();


    RelativeLayout navOrder,navHelp,navContact,navRate,navLogout,navAbout,navHome,navAllProducts,navorderHis,navProfile ;
    CoordinatorLayout cdLanding;
    ArrayList<Order> historyListDocDatas = new ArrayList<Order>();
    String cust_support_no, order_support_no;
    Context context;
    RelativeLayout loadermain;
    DrawerLayout drawer;
    DbHelper dbHelper;
    TextView client_name_id,sales_name_id,/*collection_name_id*/daily_date_id,outstanding_rupees_id;
    LinearLayout listicon, callicon;
    int offsetValue = 0;
    RecyclerView detail_recycler_view;
    UpdateCart updateCart;
    LinearLayoutManager linearLayoutManager;
    public boolean loadingMore = true;
    TextView txtamount_detail, txtCart_detail;
    RelativeLayout navforward;
    ImageView callimage;
    Utilz utilz;
    LinearLayout listfooter;
    private static final String  screenName = "order-history";
    private ContainerHolder containerHolder;
    private Container container;
    private DataLayer dataLayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_design_history);


       /* Intent intent = getIntent();
        String get = intent.getStringExtra("show cancel snackbar");
        if (get.equals("yes")){
            Snackbar snackbar = Snackbar.make(cdLanding, "Your order has been successfully cancelled.", Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();
        }*/


        //setContentView(R.layout.history_card_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        loadermain = (RelativeLayout) findViewById(R.id.loadermain);
        loadermain.setOnClickListener(this);


        client_name_id = (TextView) findViewById(R.id.client_name_id);
        sales_name_id = (TextView) findViewById(R.id.sales_name_id);
        //collection_name_id = (TextView) findViewById(R.id.collection_name_id);
        daily_date_id = (TextView) findViewById(R.id.daily_date_id);
        outstanding_rupees_id = (TextView) findViewById(R.id.outstanding_rupees_id);


        callretailerdetailsAPI();



        context = History.this;

        containerHolder = ContainerHolderSingleton.getContainerHolder();
        container = containerHolder.getContainer();
        dataLayer = TagManager.getInstance(this).getDataLayer();
        dataLayer.push(DataLayer.mapOf("event", "openScreen", "screenName", screenName));
        dataLayer.push(DataLayer.mapOf("event", "screenVisible", "screenName", screenName));


        /*  ..........................UIL Integration.......................................*/
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        /*.................................RecyclerView.........................*/
        detail_recycler_view = (RecyclerView) findViewById(R.id.detail_recycler_view);
        detail_recycler_view.setHasFixedSize(true);
        detail_recycler_view.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(context);

        /* .................history API.................*/
        cdLanding = (CoordinatorLayout) findViewById(R.id.cdLanding);
        utilz = new Utilz();
        if (!utilz.isInternetConnected(context)) {
            Snackbar snackbar = Snackbar.make(cdLanding, "Please check the internet connection", Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();


        } else
            callHistoryListingAPI(offsetValue);


    /*   .................DataBase Implementation.......................*/

        dbHelper = DbHelper.getInstance(context);
        dbHelper.createDb(false);








       // navOrder = (RelativeLayout) findViewById(R.id.pending_menu);
        navHelp = (RelativeLayout) findViewById(R.id.help_menu);
        navContact = (RelativeLayout) findViewById(R.id.contact_menu);
        navProfile = (RelativeLayout) findViewById(R.id.profile_menu);
        navHome = (RelativeLayout) findViewById(R.id.addOrder_menu);

        navRate = (RelativeLayout) findViewById(R.id.rate_menu);
        navLogout = (RelativeLayout) findViewById(R.id.about_menu);
        navAbout = (RelativeLayout) findViewById(R.id.logout_menu);

        navorderHis = (RelativeLayout) findViewById(R.id.orderHis_menu);
        //navforward = (RelativeLayout) findViewById(R.id.forward);
        navAllProducts = (RelativeLayout) findViewById(R.id.allproducts_menu);
        navAllProducts.setOnClickListener(this);

        //navOrder.setOnClickListener(this);
        navHelp.setOnClickListener(this);
        navContact.setOnClickListener(this);
        navProfile.setOnClickListener(this);
        navHome.setOnClickListener(this);
        navorderHis.setOnClickListener(this);
        navRate.setOnClickListener(this);
        navLogout.setOnClickListener(this);
        navAbout.setOnClickListener(this);
       // navforward.setOnClickListener(this);

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
        //((TextView) findViewById(R.id.headername)).setText(prefs.getString("Retailer_Name", null));

        /* .....................ToolBar/ActionBar..............................*/
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listfooter  = (LinearLayout) findViewById(R.id.listfooter);



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
                        callHistoryListingAPI(offsetValue);
                    }
                }
            }
        });


    }

    public void callHistoryListingAPI(final int offset) {

       /* Log.e("data",String.valueOf(offset));*/
        offsetValue = offset;
        int row = 10;

        HashMap hashMap = new HashMap();

        hashMap.put("rows", row);
        hashMap.put("start", offset);
       //hashMap.put("sort[title]","asc");
        //hashMap.put("")

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

        apiInterface.getorderListingResponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, AuthToken, hashMap , new Callback<HttpResponse<Order>>() {

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

                    String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
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


                    ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.GONE);
                    detail_recycler_view.setVisibility(View.VISIBLE);


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
                        Utilz.count = historyListDocDatas.size();
                        for (int i = 0; i < httpResponse.data.responseData.docs.size(); i++) {
                            historyListDocDatas.add((Order) httpResponse.data.responseData.docs.get(i));
                        }

                    }


                    /*if (historyListDocDatas.size() == 0){
                        historyListDocDatas = (ArrayList<Order>) httpResponse.data.responseData.docs;
                    }*/


                    /*else {
                        Utilz.count = historyListDocDatas.size();
                        for (int i = 0; i < httpResponse.data.responseData.docs.size(); i++) {
                            historyListDocDatas.add((Order)httpResponse.data.responseData.docs.get(i));
                        }
                    }*/


                    if (flag == true) {

                        //ArrayList<CartClass> cartClasses = dbHelper.getProductQty();

                        detail_recycler_view.setLayoutManager(new LinearLayoutManager(context));
                        detail_recycler_view.setHasFixedSize(true);
                        detail_recycler_view.setNestedScrollingEnabled(false);
                        detail_recycler_view.setAdapter(new History_Adapter(historyListDocDatas, context, updateCart, true));
                        detail_recycler_view.scrollToPosition(Utilz.count);


                    } else {

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
        //   Log.e("No of Page ", String.valueOf(offsetValue));
    }



    private void makeaCall(String phn) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(History.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
        History.ShowDialog showdialog = new History.ShowDialog(this);
        switch (view.getId()) {


           /* case R.id.pending_menu:

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

            case R.id.profile_menu :
                drawer.closeDrawer(GravityCompat.START);

                Intent in = new Intent(History.this,FillRetailerDetails.class);
                in.putExtra("fromWhere","Inside");
                startActivity(in);
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

                    }
                };


                new android.os.Handler().postDelayed(runnable, 300);
                break;


            case R.id.logout_menu:
                //  Toast.makeText(this,"Logout Menu Pressed",Toast.LENGTH_SHORT).show();
                logoutPopUp();
                drawer.closeDrawer(GravityCompat.START);
                break;


            /*case R.id.forward:

                runnable = new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(context, history_ui.class);
                        intent.putExtra("Name", "Order History");
                        startActivity(intent);
                    }
                };


                new android.os.Handler().postDelayed(runnable, 300);
                break;*/





        }
    }

    private void logoutPopUp() {
        final Dialog logoutdialog = new Dialog(History.this);
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
                Intent i = new Intent(History.this, Splash.class);
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









    public void resumeAPI() {

        if (!utilz.isInternetConnected(context)) {
            Snackbar snackbar = Snackbar.make(cdLanding, "Please check the internet connection", Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();

        } else if (historyListDocDatas.size() > 0) {

            detail_recycler_view.setAdapter(null);
            historyListDocDatas.clear();
            Utilz.count = 0;
            offsetValue = 1;
            callHistoryListingAPI(offsetValue);
            loadingMore = true;

            //  Log.e("Duplicate data Firing", "Resume API");
        }
    }






    void callretailerdetailsAPI(){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);
        apiInterface.getretailerdetailsresponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, AuthToken, new Callback<HttpResponse<user_profile>>(){
            @Override
            public void success ( HttpResponse httpResponse , Response response ){


                int status = httpResponse.status;

                if (status == -1){

                    String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                } else if (status == 0) {

                    String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if (status == 1){


                    if (retailerdetails.size() == 0) {
                        retailerdetails = (ArrayList<user_profile>) httpResponse.data.responseData.docs;
                    }



                    String retailerName = retailerdetails.get(0).retailerName;
                    String salesRepName = retailerdetails.get(0).salesRepName;
                    String outstandingDate = retailerdetails.get(0).outstandingDate;
                    //String collectionRepName = retailerdetails.get(0).collectionRepName;
                    Double outstandingAmount = retailerdetails.get(0).outstandingAmount;




                    client_name_id.setText(retailerName);


                    sales_name_id.setText(salesRepName);
                    //collection_name_id.setText(collectionRepName);
                    daily_date_id.setText(outstandingDate);
                    outstanding_rupees_id.setText(outstandingAmount.toString());


                   /* if (Status == true){
                        //String orderId =
                        //orderfeedback.orderId;
                        Intent i = new Intent(Splash.this, RateUs.class);
                        i.putExtra("ID",O_id);
                        i.putExtra("date",datee);
                        startActivity(i);
                        finish();

                    }
                    else {


                        Intent i = new Intent(Splash.this, Landing_Update.class);
                        startActivity(i);
                        finish();
                    }*/

                    // if (httpResponse.data.responseData.docs.size() != 0 ){



                    //}



                }









            }









            @Override
            public void failure(RetrofitError error) {
                //progressMobile.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

            }

        });





    }



    @Override
    public void onResume() {
        super.onResume();



        /*if (flag == true & backflag == false & historyListDocDatas.size() != 0) {
            History_Adapter mAdapter = new History_Adapter(historyListDocDatas, context, updateCart, true);
            detail_recycler_view.setAdapter(mAdapter);
            detail_recycler_view.setNestedScrollingEnabled(false);
            mAdapter.notifyDataSetChanged();
        } else if (flag == false & backflag == false & historyListDocDatas.size() != 0) {
            //Detail_Adapter detailAdapter = new Detail_Adapter(historyListDocDatas, context, updateCart, true);
            //detail_recycler_view.setLayoutManager(new LinearLayoutManager(context));
            detail_recycler_view.setNestedScrollingEnabled(false);
            //detail_recycler_view.setAdapter(detailAdapter);
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



}
