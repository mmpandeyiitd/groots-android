package groots.app.com.groots.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;

import groots.app.com.groots.R;
import groots.app.com.groots.adapter.Detail_Adapter;
import groots.app.com.groots.adapter.History_Adapter;
import groots.app.com.groots.adapter.Landing_Adapter;
import groots.app.com.groots.adapter.SampleActivityAdapter;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.interfaces.API_Interface;
import groots.app.com.groots.model.CartClass;
import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.HttpResponseofProducts;
import groots.app.com.groots.pojo.Items;
import groots.app.com.groots.pojo.Product;
import groots.app.com.groots.pojo.RetailerProduct;
import groots.app.com.groots.utilz.Http_Urls;
import groots.app.com.groots.utilz.Utilz;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by aakash on 8/2/17.
 */

public class SampleActivity extends AppCompatActivity implements View.OnClickListener{

    private ShowcaseView showcaseView;
    private int caseshow;
    private Target t1;

    CoordinatorLayout cdLanding;
    boolean flag = true;
   public boolean loadingmore = true;

    Context context;
    LinearLayoutManager linearLayoutManager;
    RelativeLayout loaderMain,loaderMainFooter,blankLayout;
    RecyclerView recycle;
    int offsetValue = 0;
    ArrayList<Items> sampleProducts = new ArrayList<>();
    ArrayList<Product> productListDocDatas = new ArrayList<>();


    TextView logout;
    ImageView nextActivity;
    LinearLayout backActivity;
    DbHelper dbHelper;


    protected void onCreate(Bundle savedInstanceState){

     super.onCreate(savedInstanceState);
        setContentView(R.layout.new_design_sample_activity);

       // t1 = new ViewTarget(R.id.detail_recycler_view,this);



//      showcaseView = new ShowcaseView.Builder(this).setTarget(Target.NONE).setContentText("Price List").setContentTitle("Sample").setOnClickListener(this).build();
//
//
//        showcaseView.setButtonText("Sample");



        cdLanding = (CoordinatorLayout) findViewById(R.id.cdLanding);
        backActivity = (LinearLayout) findViewById(R.id.back_btn);
        nextActivity = (ImageView) findViewById(R.id.goToStep2);
        logout = (TextView) findViewById(R.id.logout_button);
        loaderMain = (RelativeLayout) findViewById(R.id.loadermain);
        loaderMain.setVisibility(View.INVISIBLE);
        loaderMainFooter = (RelativeLayout) findViewById(R.id.loadermainfooter);
        loaderMainFooter.setVisibility(View.INVISIBLE);

        blankLayout = (RelativeLayout) findViewById(R.id.blank_layout);
        blankLayout.setVisibility(View.INVISIBLE);
        recycle = (RecyclerView) findViewById(R.id.detail_recycler_view);



        recycle.setHasFixedSize(true);
        recycle.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(context);



        context = SampleActivity.this;

        callSampleProductsAPI(offsetValue);
        //callProductListingAPI(offsetValue);


        dbHelper = DbHelper.getInstance(context);
        dbHelper.createDb(false);



        recycle.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {

               // listfooter.animate().translationY(listfooter.getHeight()).setInterpolator(new AccelerateInterpolator(2));
            }

            @Override
            public void onShow() {
                //listfooter.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

            }
        });

        recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                Utilz.count = layoutManager.findFirstCompletelyVisibleItemPosition();
             /*   Log.e("count", String.valueOf(offsetValue));*/
                if (loadingmore) {
                    //position starts at 0
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                        callSampleProductsAPI(offsetValue);
                    }
                }
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                logoutPopUp();




            }
        });

        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SampleActivity.this,FillRetailerDetails.class);

               /* intent.putExtra("showNav","false");

                intent.putExtra("fromWhere","sample");*/
                intent.putExtra("fromWhere","Outside");
                startActivity(intent);

            }
        });



















    }



    private void logoutPopUp() {
        final Dialog logoutdialog = new Dialog(SampleActivity.this);
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
                Intent i = new Intent(SampleActivity.this, Splash.class);
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



    public void callSampleProductsAPI(final int offset){

        int row  = 10;
        offsetValue = offset;




        HashMap hash = new HashMap();
        hash.put("start",offset);
        hash.put("rows",row);



        if (offset == 0){
            loaderMain.setVisibility(View.VISIBLE);
        }


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Http_Urls.sBaseUrl).setLogLevel(RestAdapter.LogLevel.FULL).build();



        API_Interface apiInterface = restAdapter.create(API_Interface.class);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME",MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken",null);


        apiInterface.getSampleProductsResponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, AuthToken, hash, new Callback<HttpResponseofProducts<Items>>() {

            public void success(HttpResponseofProducts httpResponse , Response response){




                //recycle.setVisibility(View.INVISIBLE);
              //  loaderMain.setVisibility(View.INVISIBLE);

                String stat = httpResponse.status;
                int status = Integer.parseInt(stat.substring(0,1));

                if (status == 5){

                    Snackbar snackbar = Snackbar.make(cdLanding,"Something went wrong. Please try again later.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if (status == 4){

                    Snackbar snackbar = Snackbar.make(cdLanding,"Something went wrong. Please try again later.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                }
                else if (status == 2){



                    loaderMain.setVisibility(View.INVISIBLE);
                    blankLayout.setVisibility(View.GONE);
                    recycle.setVisibility(View.VISIBLE);


                    if (httpResponse.data.size() == 0 || httpResponse.data == null){

                        loaderMain.setVisibility(View.INVISIBLE);

                        if (offsetValue == 10) {
                            blankLayout.setVisibility(View.VISIBLE);
                            recycle.setVisibility(View.GONE);

                            // ((LinearLayout) findViewById(R.id.listfooter)).setVisibility(View.GONE);

                        }
                        loadingmore = false;



                    }
                    else {
                        blankLayout.setVisibility(View.GONE);
                        recycle.setVisibility(View.VISIBLE);


                        Utilz.count = sampleProducts.size();

                        /*if (Utilz.count != 0){
                            sampleProducts.clear();
                        }*/
                        for (int i = 0; i < httpResponse.data.size(); i++) {
                            sampleProducts.add((Items) httpResponse.data.get(i));
                        }
                    }



                    if (flag == true) {

                        //ArrayList<CartClass> cartClasses = dbHelper.getProductQty();

                        recycle.setLayoutManager(new LinearLayoutManager(context));
                        recycle.setHasFixedSize(true);
                        recycle.setNestedScrollingEnabled(false);
                        recycle.setAdapter(new SampleActivityAdapter(sampleProducts, context, true));
                        recycle.scrollToPosition(Utilz.count);


                    } else {

                    }




                }




            }

            public void failure(RetrofitError e){


                Snackbar snackbar = Snackbar.make(cdLanding,"Something went wrong. Please try again later.", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

            }

        });

        offsetValue = offsetValue +row;













    }











  /*  private void callProductListingAPI(final int offset) {

       *//* Log.e("data",String.valueOf(offset));*//*
        offsetValue = offset;

        HashMap hashMap = new HashMap();

        hashMap.put("limit", 10);
        hashMap.put("page", offset);
        hashMap.put("sort[title]","asc");

        Log.e("Sending Data", hashMap.toString());

        if (offset == 1) {
            loaderMain.setVisibility(View.VISIBLE);
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
                loaderMain.setVisibility(View.INVISIBLE);

                //  progressDialog.dismiss();
                int status = httpResponse.status;

                if (status == -1) {

                    String msg = httpResponse.error_object.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                } else if (status == 0) {

                    String msg = httpResponse.error_object.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                    if (offsetValue == 2) {
                        ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.VISIBLE);
                        recycle.setVisibility(View.GONE);
                        // ((LinearLayout) findViewById(R.id.listfooter)).setVisibility(View.GONE);

                    }



                } else if (status == 1) {

                   // backflag = false;
                    ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.GONE);
                    recycle.setVisibility(View.VISIBLE);


                    //  Log.e("incoming data",productListData.data.response.docs.toString());
             *//*       ArrayList<CartClass> cartClasses = dbHelper.getProductQty();

                    for (int i = 0; i < productListDocDatas.size(); i++) {

                        for (int j = 0; j < cartClasses.size(); j++) {

                            if (productListDocDatas.get(i).subscribedProductId == cartClasses.get(j).subscribe_prod_id) {

                                productListDocDatas.get(i).setItemCount(cartClasses.get(j).product_qty);
                            }
                        }
                    }*//*
                    //  Log.e("incoming data",productListData.data.response.docs.toString());

                    if (httpResponse.data.responseData.docs.size() == 0) {

                        if (offsetValue == 2) {
                            ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.VISIBLE);
                            recycle.setVisibility(View.GONE);
                            // ((LinearLayout) findViewById(R.id.listfooter)).setVisibility(View.GONE);

                        }
                       // loadingMore = false;
                    } else {
                        ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.GONE);
                        recycle.setVisibility(View.VISIBLE);

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



                        recycle.setLayoutManager(new LinearLayoutManager(context));
                        recycle.setHasFixedSize(true);
                        recycle.setAdapter(new SampleActivityAdapter(productListDocDatas, context, true));
                        recycle.scrollToPosition(Utilz.count);


                    }

                }


            }


            @Override
            public void failure(RetrofitError error) {

                loaderMain.setVisibility(View.INVISIBLE);

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
    }*/


















    @Override
    public void onClick(View view) {


        /*switch(caseshow){
            case 0:
                showcaseView.setShowcase(t1,true);
                showcaseView.setContentTitle("Sample");
                showcaseView.setContentText("This is the sample of our products with there prices");
                break;
            case 1:
                showcaseView.hide();
                break;


        }
        caseshow++;*/

    }
}
