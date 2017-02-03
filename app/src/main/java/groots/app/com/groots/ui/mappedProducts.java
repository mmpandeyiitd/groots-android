package groots.app.com.groots.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;

import groots.app.com.groots.R;
import groots.app.com.groots.adapter.mappedProductList_Adapter;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.interfaces.API_Interface;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.HttpResponseofProducts;
import groots.app.com.groots.pojo.Product;
import groots.app.com.groots.pojo.allProduct;
import groots.app.com.groots.utilz.Http_Urls;
import groots.app.com.groots.utilz.Utilz;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by aakash on 16/12/16.
 */
public class mappedProducts extends Fragment {

    AutoCompleteTextView search_text;
    LinearLayout search_icon;
    CoordinatorLayout cdLanding;
    String searched_text;
    public boolean backflag = false;
    RelativeLayout blankLayout , loadermain;
    mappedProductList_Adapter adapter;
    ArrayList<Product> productListDocDatas = new ArrayList<>();
    int offsetValue = 0;
    int offsetval = 0;
    RecyclerView detail_recycler_view;
    Button addproducts_button;
    DbHelper dbHelper;

    RecyclerView recycle;
    public boolean loadingMoreforall = true;
    UpdateCart updateCart;
    Context context;
    boolean flag = true;
    public boolean loadingMoreforselected = true;
    public boolean loadingMoreforsearch = true;

    ArrayList<allProduct> searchedproducts = new ArrayList<>();
    ArrayList<allProduct> allproducts = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_list, container, false);




        context = getActivity();








        // View view=inflater.inflate(R.layout.product_list,null);

        cdLanding = (CoordinatorLayout) rootView.findViewById(R.id.cdLanding);


        search_text = (AutoCompleteTextView) rootView.findViewById(R.id.search_text);
        addproducts_button = (Button) rootView.findViewById(R.id.addproducts_button);
        search_icon = (LinearLayout) rootView.findViewById(R.id.search_icon);
        //search_icon.setOnClickListener(this);
        blankLayout = (RelativeLayout) rootView.findViewById(R.id.blank_layout);
        loadermain = (RelativeLayout) rootView.findViewById(R.id.loadermain);
        recycle = (RecyclerView) rootView.findViewById(R.id.detail_recycler_view);
        recycle.setHasFixedSize(true);
        recycle.setNestedScrollingEnabled(false);

        dbHelper = DbHelper.getInstance(context);
        dbHelper.createDb(false);

        //callProductListingAPI(offsetValue);
        callallproductAPI(offsetValue);



        recycle.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {

                //listfooter.animate().translationY(listfooter.getHeight()).setInterpolator(new AccelerateInterpolator(2));
            }

            @Override
            public void onShow() {
                //listfooter.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

            }
        });

        addproducts_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Array pro;
                // Integer subss = new Product().subscribedProductId;
                //ArrayList<Integer> subs = new ArrayList<>(subss);



                // ArrayList<Integer> subs = new ArrayList<>();
                // hashMap.put(i,selected.get(i).subscribedProductId);



                callselectedProductsAPI(offsetValue);

            }
        });


        recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                Utilz.count = layoutManager.findFirstCompletelyVisibleItemPosition();
                // Log.e("count", String.valueOf(offsetValue));
                if (loadingMoreforall) {
                    //position starts at 0

                    if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                        callallproductAPI(offsetValue);
                    }
                }
              /* else if (loadingMoreforselected) {
                   //position starts at 0

                   if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                       callselectedProductsAPI(offsetValue);
                   }
               }*/
               else if (loadingMoreforsearch){
                   if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {

                       HashMap hashMap = new HashMap();
                       hashMap.put("query",searched_text);

                       callsearchtextAPI(hashMap,offsetval);
                   }

               }
            }
        });

        //search_icon.setOnClickListener();




        search_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (dbHelper.getTotalSearchList() > 0){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, dbHelper.getSearchListdata());
                    search_text.setAdapter(adapter);
                    search_text.setThreshold(1);


                }
                return false;
            }
        });


        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String searched_tex = search_text.getText().toString();

               if (searched_tex.trim().contains(" ")){
                  searched_tex = searched_tex.replace(" ","* *");
                searched_text = "*"+searched_tex+"*";
               }
                else if (!(searched_tex.trim().contains(" "))){
                   searched_text ="*"+searched_tex+"*";


               }





                int searched_text_length = searched_tex.length();
                HashMap hashMap = new HashMap();

                if (searched_tex != null && searched_text_length >= 2) {

                    dbHelper.insertSearchListdata(searched_tex);




                   // hashMap.put("mapped", "true");
                    hashMap.put("query", searched_text);
                    callsearchtextAPI(hashMap , offsetval);


                }
                else if (searched_text == null || searched_text_length < 2 ){
                    Toast.makeText(context,"Please enter something to search.",Toast.LENGTH_SHORT ).show();
                    callallproductAPI(offsetValue);
                }




            }


        });
        return rootView;
    }

    /*private ArrayList<Product> getselectedproducts() {

        ArrayList<Product> selectproducts= new ArrayList<>();
        Product p = new Product();




return null;
    }*/

    public void callsearchtextAPI(HashMap hashMap , final int offset){



        offsetValue = offset;
        int row = 10;

        hashMap.put("rows", row);
        hashMap.put("start", offset);
        hashMap.put("mapped","true");

        if (offset == 0) {
         //   loadermain.setVisibility(View.VISIBLE);
            //loadermainfooter.setVisibility(View.INVISIBLE);
        }


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);

        apiInterface.getallproductslistingresponse(Utilz.apikey,Utilz.app_version, Utilz.config_version, AuthToken,hashMap, new Callback<HttpResponseofProducts<allProduct>>(){

            @Override

            public void success (HttpResponseofProducts httpResponse , Response response ){




                String statu = httpResponse.status;
                String stat = statu.substring(0,1);
                int status = Integer.parseInt(stat);

                if (status == 5){



                    String msg = httpResponse.errors;
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                }


                else if (status == 4) {

                    String msg = httpResponse.errors;
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if (status == 2){

                    loadingMoreforall = false;

                    /* if (searchedproducts.size() == 0) {
                         searchedproducts = (ArrayList<Product>) httpResponse.data.responseData.docs;
                     }*/



                    blankLayout.setVisibility(View.GONE);
                    recycle.setVisibility(View.VISIBLE);


                    if (httpResponse.data.size() == 0 || httpResponse.data == null){

                        if (offsetValue == 10) {
                            blankLayout.setVisibility(View.VISIBLE);
                            recycle.setVisibility(View.GONE);

                            // ((LinearLayout) findViewById(R.id.listfooter)).setVisibility(View.GONE);

                        }
                        loadingMoreforsearch = false;


                    }
                    else {
                        blankLayout.setVisibility(View.GONE);
                        recycle.setVisibility(View.VISIBLE);


                        Utilz.count = searchedproducts.size();

                        if (Utilz.count != 0){
                           searchedproducts.clear();
                        }
                        for (int i = 0; i < httpResponse.data.size(); i++) {
                            searchedproducts.add((allProduct) httpResponse.data.get(i));
                        }
                    }


                    if (flag == true) {

                        recycle.setLayoutManager(new LinearLayoutManager(context));
                        recycle.setHasFixedSize(true);
                        recycle.setNestedScrollingEnabled(false);
                        recycle.setAdapter(new mappedProductList_Adapter(searchedproducts,mappedProducts.this, context, true));
                        recycle.scrollToPosition(Utilz.count);

                    }






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
        offsetValue = offsetValue + row;
    }




    public void callallproductAPI(final int offset){

        offsetValue = offset;
        int row = 10;
        HashMap hashMap = new HashMap();
        hashMap.put("rows", row);
        hashMap.put("start", offset);
        hashMap.put("mapped","true");



        if (offset == 0) {
          //  loadermain.setVisibility(View.VISIBLE);
            //loadermainfooter.setVisibility(View.INVISIBLE);
        }


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);


        apiInterface.getallproductslistingresponse(Utilz.apikey,Utilz.app_version, Utilz.config_version, AuthToken,hashMap, new Callback<HttpResponseofProducts<allProduct>>(){



            public void success (HttpResponseofProducts httpResponse , Response response ){

                loadermain.setVisibility(View.INVISIBLE);




                String statu = httpResponse.status;
                String stat = statu.substring(0,1);
                int status = Integer.parseInt(stat);
                // int status = Integer.parseInt(stat);


                if (status == 5){



                    String msg = httpResponse.errors.toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                }


                else if (status == 4) {

                    String msg = httpResponse.errors.toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if (status == 2){

                    blankLayout.setVisibility(View.GONE);
                    recycle.setVisibility(View.VISIBLE);




                    if (httpResponse.data.size() == 0 || httpResponse.data == null){

                        if (offsetValue == 10) {
                            blankLayout.setVisibility(View.VISIBLE);
                            recycle.setVisibility(View.GONE);

                            // ((LinearLayout) findViewById(R.id.listfooter)).setVisibility(View.GONE);

                        }
                        loadingMoreforall = false;


                    }
                    else{
                        blankLayout.setVisibility(View.GONE);
                        recycle.setVisibility(View.VISIBLE);
                        Utilz.count = allproducts.size();

                        if (Utilz.count == 0 ){
                            allproducts.clear();
                        }

                        for (int i = 0; i < httpResponse.data.size(); i++) {
                            allproducts.add((allProduct) httpResponse.data.get(i));
                        }



                    }



                   /* if (allproducts.size() == 0) {
                        ((RelativeLayout) findViewById(R.id.blank_layout)).setVisibility(View.GONE);
                        recycle.setVisibility(View.VISIBLE);



                        //allproducts = (ArrayList<Product>) httpResponse.data.responseData.docs;
                    }*/




                    if (flag == true) {

                        recycle.setLayoutManager(new LinearLayoutManager(context));
                        recycle.setHasFixedSize(true);
                        recycle.setNestedScrollingEnabled(false);
                        recycle.setAdapter(new mappedProductList_Adapter(allproducts,mappedProducts.this, context, true));
                        recycle.scrollToPosition(Utilz.count);
                    }
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
        // offsetValue = offsetValue + row;

        offsetValue = offsetValue + row;


    }



    public void callselectedProductsAPI(final int offset){

        offsetValue = offset;
        int row = 10;

        HashMap hashMap = new HashMap();
        ArrayList<allProduct> selected = ((mappedProductList_Adapter) adapter).getSelectedProducts();


        Product products = new Product();
        ArrayList<allProduct> subs =  new ArrayList<>(products.subscribedProductId);


        for (int i=0 ; i<= selected.size();i++) {



            subs.add(selected.get(i));




        }
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Http_Urls.sBaseUrl).setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);

        // HashMap hashMap = new HashMap();
        // hashMap.putAll();


        apiInterface.getselectedproductsresponse(Utilz.apikey,Utilz.app_version, Utilz.config_version,"application/json", AuthToken,subs, new Callback<HttpResponse>(){


            public void success(HttpResponse httpresponse , Response response){
                int status = httpresponse.status;

                if (status == -1){

                    String msg = httpresponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();




                }
                else if (status == 0){

                    String msg = httpresponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();



                }
                else if(status == 1){



                    // String msg = httpresponse.errors.get(0).toString();
                    Toast.makeText(context,"Products has been mapped successfully.",Toast.LENGTH_LONG).show();
                   /* Snackbar snackbar = Snackbar.make(cdLanding,"Products has been mapped successfully", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();*/


                    Intent intent = new Intent(context, Landing_Update.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();




                }




            }

            public void failure(RetrofitError error){


                Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();


            }



        });



    }







    /*private void callProductListingAPI(final int offset) {

       *//* Log.e("data",String.valueOf(offset));*//*
        offsetValue = offset;

        HashMap hashMap = new HashMap();

        hashMap.put("limit", 1000);
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

                    String msg = httpResponse.errors.get(0).toString();
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









                    if (productListDocDatas.size() == 0)
                        productListDocDatas = (ArrayList<Product>) httpResponse.data.responseData.docs;
                    else {
                        Utilz.count = productListDocDatas.size();
                        for (int i = 0; i < httpResponse.data.responseData.docs.size(); i++) {
                            productListDocDatas.add((Product)httpResponse.data.responseData.docs.get(i));
                        }
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
    }*/









}

