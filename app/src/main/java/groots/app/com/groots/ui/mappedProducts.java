package groots.app.com.groots.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import groots.app.com.groots.R;
import groots.app.com.groots.adapter.mappedProductList_Adapter;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.interfaces.API_Interface;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.model.MappingClass;
import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.HttpResponseofProducts;
import groots.app.com.groots.pojo.Product;
import groots.app.com.groots.pojo.RetailerProduct;
import groots.app.com.groots.pojo.RetailerProducts;
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
    boolean allProducts;
    HashMap hash = new HashMap();
    String registrationStatus;
    public boolean backflag = false;
    RelativeLayout blankLayout , loadermain;
    mappedProductList_Adapter adapter;
    ArrayList<Product> productListDocDatas = new ArrayList<>();
    int offsetValue = 0;
    int offsetval = 0;
    String from;
    RecyclerView detail_recycler_view;
    Button addproducts_button;
    DbHelper dbHelper;
    RelativeLayout gotofina;
    TextView textgotofinal;
    ImageView goToFinalPage,save;


    RecyclerView recycle;
    public boolean loadingMoreforall1 = true;
    UpdateCart updateCart;
    Context context;
    boolean flag = true;
    String fromWhere,showNav;


    public boolean loadingMoreforselected = true;
    public boolean loadingMoreforsearch1 = true;

    ArrayList<RetailerProduct> searchedproducts = new ArrayList<>();
    ArrayList<RetailerProduct> allproducts = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_design_product_list, container, false);

        /*fromWhere = getArguments().getString("fromWhere");
        showNav = getArguments().getString("showNav");*/
        fromWhere = getActivity().getIntent().getStringExtra("fromWhere");
        showNav = getActivity().getIntent().getStringExtra("showNav");
        from = getActivity().getIntent().getStringExtra("from");






        context = getActivity();



        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        /*textgotofinal = (TextView) rootView.findViewById(R.id.textgotofinal);




        goToFinalPage = (ImageView) rootView.findViewById(R.id.goToFinalStep);*/
        save = (ImageView) rootView.findViewById(R.id.save);




       // gotofina = (RelativeLayout) rootView.findViewById(R.id.relgoToFinalStep);


        /*gotofina.setVisibility(View.GONE);


        goToFinalPage.setVisibility(View.GONE);
        textgotofinal.setVisibility(View.GONE);*/

      /*  if (showNav.equals("false")){
           *//* gotofina.setVisibility(View.VISIBLE);


            goToFinalPage.setVisibility(View.VISIBLE);
            textgotofinal.setVisibility(View.VISIBLE);*//*
        }
*/





        // View view=inflater.inflate(R.layout.product_list,null);

        cdLanding = (CoordinatorLayout) rootView.findViewById(R.id.cdLanding);


        search_text = (AutoCompleteTextView) rootView.findViewById(R.id.search_text);
       // addproducts_button = (Button) rootView.findViewById(R.id.addproducts_button);
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





/*

        goToFinalPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(context,FillRetailerDetails.class);
                startActivity(intent);
            }
        });
*/



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


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


                if (allProducts == true) {
                    LinearLayoutManager layoutManager1 = (LinearLayoutManager) recyclerView.getLayoutManager();
                    Utilz.count1 = layoutManager1.findFirstCompletelyVisibleItemPosition();
                    // Log.e("count", String.valueOf(offsetValue));
                    if (loadingMoreforall1) {
                        //position starts at 0

                        if (layoutManager1.findLastCompletelyVisibleItemPosition() == layoutManager1.getItemCount() - 1) {
                            callallproductAPI(offsetValue);
                        }
                    }
                }
                else if (allProducts == false){
                    LinearLayoutManager layoutManager2 = (LinearLayoutManager) recyclerView.getLayoutManager();
                    Utilz.count2 = layoutManager2.findFirstCompletelyVisibleItemPosition();
                    if (loadingMoreforsearch1){

                        if (layoutManager2.findLastCompletelyVisibleItemPosition() == layoutManager2.getItemCount() - 1) {

                            HashMap hashMap = new HashMap();
                            hashMap.put("query",searched_text);

                            callsearchtextAPI(hashMap,offsetval);
                        }

                    }
                }
              /* else if (loadingMoreforselected) {
                   //position starts at 0

                   if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                       callselectedProductsAPI(offsetValue);
                   }
               }*/
              /* else if (loadingMoreforsearch){
                   if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {

                       HashMap hashMap = new HashMap();
                       hashMap.put("query",searched_text);

                       callsearchtextAPI(hashMap,offsetval);
                   }

               }*/
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


                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
                    Toast.makeText(context,"Please enter something to search or more than 1 letter.",Toast.LENGTH_SHORT ).show();


                    recycle.setAdapter(null);
                    allproducts.clear();
                    Utilz.count1 = 0;
                    offsetValue = 0;

                    callallproductAPI(offsetValue);
                    loadingMoreforall1 = true;
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

        allProducts = false;

        if (offset == 0) {
          // loadermain.setVisibility(View.VISIBLE);
            //loadermainfooter.setVisibility(View.INVISIBLE);
        }


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);

        apiInterface.getallproductslistingresponse(Utilz.apikey,Utilz.app_version, Utilz.config_version, AuthToken,hashMap, new Callback<HttpResponseofProducts<RetailerProduct>>(){

            @Override

            public void success (HttpResponseofProducts httpResponse , Response response ){


                loadermain.setVisibility(View.INVISIBLE);

                String statu = httpResponse.status;
                String stat = statu.substring(0,1);
                int status = Integer.parseInt(stat);

                if (status == 5){



                    String msg = httpResponse.errors.msg;
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                }


                else if (status == 4) {

                    String msg = httpResponse.errors.msg;
                    Snackbar snackbar = Snackbar.make(cdLanding,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if (status == 2){

                    loadermain.setVisibility(View.INVISIBLE);
                    loadingMoreforall1 = false;

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
                        loadingMoreforsearch1 = false;


                    }
                    else {
                        blankLayout.setVisibility(View.GONE);
                        recycle.setVisibility(View.VISIBLE);


                        Utilz.count2 = searchedproducts.size();

                        if (Utilz.count2 != 0){
                           searchedproducts.clear();
                        }
                        for (int i = 0; i < httpResponse.data.size(); i++) {
                            searchedproducts.add((RetailerProduct) httpResponse.data.get(i));
                        }
                    }


                    if (flag == true) {

                        recycle.setLayoutManager(new LinearLayoutManager(context));
                        recycle.setHasFixedSize(true);
                        recycle.setNestedScrollingEnabled(false);
                        recycle.setAdapter(new mappedProductList_Adapter(searchedproducts,hash,mappedProducts.this, context, true));
                        recycle.scrollToPosition(Utilz.count2);

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

        allProducts = true;



        if (offset == 0) {
            loadermain.setVisibility(View.VISIBLE);
            //loadermainfooter.setVisibility(View.INVISIBLE);
        }


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);


        apiInterface.getallproductslistingresponse(Utilz.apikey,Utilz.app_version, Utilz.config_version, AuthToken,hashMap, new Callback<HttpResponseofProducts<RetailerProduct>>(){



            public void success (HttpResponseofProducts httpResponse , Response response ){






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

                    loadermain.setVisibility(View.INVISIBLE);

                    blankLayout.setVisibility(View.GONE);
                    recycle.setVisibility(View.VISIBLE);




                    if (httpResponse.data == null || httpResponse.data.size() == 0  ){

                        if (offsetValue == 10) {
                            blankLayout.setVisibility(View.VISIBLE);
                            recycle.setVisibility(View.GONE);

                            // ((LinearLayout) findViewById(R.id.listfooter)).setVisibility(View.GONE);

                        }
                        loadingMoreforall1 = false;


                    }
                    else{
                        blankLayout.setVisibility(View.GONE);
                        recycle.setVisibility(View.VISIBLE);
                        Utilz.count1 = allproducts.size();

                       /* if (Utilz.count == 0 ){
                            allproducts.clear();
                        }*/

                        for (int i = 0; i < httpResponse.data.size(); i++) {
                            allproducts.add((RetailerProduct) httpResponse.data.get(i));
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
                        recycle.setAdapter(new mappedProductList_Adapter(allproducts,hash,mappedProducts.this, context, true));
                        recycle.scrollToPosition(Utilz.count1);
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
       // ArrayList<RetailerProduct> selected = ((mappedProductList_Adapter) adapter).getSelectedProducts();
        ArrayList<MappingClass> selected = dbHelper.getmaptounmapdata();

        RetailerProducts retailerPr = new RetailerProducts();



        ArrayList<RetailerProduct> sel = new ArrayList<>();


        if (selected.size() == 0){
            Toast.makeText(context,"Please unmap some products.",Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i=0 ; i< selected.size();i++) {

            RetailerProduct addprod  = new RetailerProduct();

            addprod.subscribedProductId = selected.get(i).subscProdId;
            addprod.retailer_id = selected.get(i).retailerId;
            addprod.isMapped = Boolean.parseBoolean(selected.get(i).status);



            sel.add(addprod);

        }
        retailerPr.retailerProds = sel;
        for (int i=0;i<=retailerPr.retailerProds.size();i++) {



        }








        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Http_Urls.sBaseUrl).setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);

        // HashMap hashMap = new HashMap();
        // hashMap.putAll();


        apiInterface.getselectedproductsresponse(Utilz.apikey,Utilz.app_version, Utilz.config_version,"application/json", AuthToken,retailerPr, new Callback<HttpResponseofProducts>(){


            public void success(HttpResponseofProducts httpresponse , Response response){
                //int status = httpresponse.status;

                String statu = httpresponse.status;
                String stat = statu.substring(0,1);
                int status = Integer.parseInt(stat);

                if (status == 5){

                  //  String msg = httpresponse.error_object.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,"Something went wrong.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();




                }
                else if (status == 4){

                   // String msg = httpresponse.error_object.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,"Something went wrong.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();



                }
                else if(status == 2){

/*
                    SharedPreferences prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", context.MODE_PRIVATE);


                    registrationStatus = prefs.getString("registrationStatus",null);

                    if (!registrationStatus.equals("Complete")){

                        callchangeRegStatusAPI();
                    }*/





                    // String msg = httpresponse.error_object.get(0).toString();
                    Toast.makeText(context,"Products has been unmapped successfully.",Toast.LENGTH_LONG).show();
                   /* Snackbar snackbar = Snackbar.make(cdLanding,"Products has been mapped successfully", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();*/


                    Intent intent = new Intent(context, mapping.class);
                   // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


                    if (fromWhere != null) {
                        if (fromWhere.equals("sample")) {
                            intent.putExtra("fromWhere", "sample");
                        }
                    }
                    if (showNav != null) {
                        if (showNav.equals("false")) {
                            intent.putExtra("showNav", "false");
                            // goToFinalPage.setVisibility(View.GONE);
                        }
                    }
                    if (from != null) {
                        intent.putExtra("from", from);
                    }
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




    /*void callchangeRegStatusAPI(){
        HashMap hashm = new HashMap();
        hashm.put("makeActive","yes");


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Http_Urls.sBaseUrl).setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);


        apiInterface.getChangeRegStatusResponse(Utilz.apikey, Utilz.app_version, Utilz.config_version, AuthToken,hashm, new Callback<HttpResponse>() {
            @Override
            public void success(HttpResponse httpResponse, Response response) {

                int status = httpResponse.status;

                if (status == -1){

                    Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if  (status == 0){
                    Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();


                }
                else if  (status == 1){

                    Toast.makeText(context,"You have done your complete registration.",Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("MY_PREFS_NAME", context.MODE_PRIVATE).edit();
                    editor.putString("registrationStatus","Complete");
                    editor.commit();



                }



            }

            @Override
            public void failure(RetrofitError error) {

                Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();


            }
        });




    }*/







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

