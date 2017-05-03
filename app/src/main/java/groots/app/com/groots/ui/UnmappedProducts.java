package groots.app.com.groots.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import groots.app.com.groots.R;
import groots.app.com.groots.adapter.unmappedProductList_Adapter;
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

import static groots.app.com.groots.ui.historyList.position;

/**
 * Created by aakash on 16/12/16.
 */
public class UnmappedProducts extends Fragment {

    AutoCompleteTextView search_text;
    LinearLayout search_icon;
    CoordinatorLayout cdLanding;
    String searched_text;
    boolean allProducts;
    HashMap hash = new HashMap();
    int ret_id;
    public boolean backflag = false;
    String registrationStatus;
    RelativeLayout blankLayout , loadermain;
    unmappedProductList_Adapter adapter;
    ArrayList<Product> productListDocDatas = new ArrayList<>();
    int offsetValue = 0;
    int offsetval = 0;
    RecyclerView detail_recycler_view;
    RelativeLayout gotofina;
    Button addproducts_button;
    DbHelper dbHelper;
    TextView textgotofinal;
    String fromWhere,showNav;
    ImageView goToFinalPage,save;
    RelativeLayout saveit;
    RecyclerView recycle;
    public boolean loadingMoreforall = true;
    UpdateCart updateCart;
    Context context;
    boolean flag = true;
    public boolean loadingMoreforselected = true;
    public boolean loadingMoreforsearch = true;

    ArrayList<RetailerProduct> searchedproducts = new ArrayList<>();
    ArrayList<RetailerProduct> allproducts = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_design_product_list, container, false);

/*
         fromWhere = this.getArguments().getString("fromWhere");
        showNav = this.getArguments().getString("showNav");*/
         fromWhere = getActivity().getIntent().getStringExtra("fromWhere");
         showNav = getActivity().getIntent().getStringExtra("showNav");


        if (fromWhere != null){

            fromWhere = "sample";
        }
        else {
            fromWhere = "home";
        }



        if (showNav != null ){
            showNav = "false";
        }
        else
        {
            showNav = "true";
        }





       context = getActivity();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



      /* textgotofinal = (TextView) rootView.findViewById(R.id.textgotofinal);
       goToFinalPage = (ImageView) rootView.findViewById(R.id.goToFinalStep);
        gotofina = (RelativeLayout) rootView.findViewById(R.id.relgoToFinalStep);*/

        save = (ImageView) rootView.findViewById(R.id.save);


        saveit = (RelativeLayout) rootView.findViewById(R.id.saveit);
       // saveit.getLayoutParams().height = 56;







       /* if (showNav != null) {
            if (showNav.equals("true")) {

*//*
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 0);
                saveit.setLayoutParams(params);*//*


            }
        }*/



       /* gotofina.setVisibility(View.GONE);


        goToFinalPage.setVisibility(View.GONE);
        textgotofinal.setVisibility(View.GONE);*/

       /* if (showNav.equals("false")){
           *//* gotofina.setVisibility(View.VISIBLE);


            goToFinalPage.setVisibility(View.VISIBLE);
            textgotofinal.setVisibility(View.VISIBLE);*//*
        }*/



       // View view=inflater.inflate(R.layout.product_list,null);

       cdLanding = (CoordinatorLayout) rootView.findViewById(R.id.cdLanding);
       //ArrayList<RetailerProduct> adapte =((unmappedProductList_Adapter) adapter).getSelectedProducts();


       search_text = (AutoCompleteTextView) rootView.findViewById(R.id.search_text);
        //search_text.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

     //  addproducts_button = (Button) rootView.findViewById(R.id.addproducts_button);
       search_icon = (LinearLayout) rootView.findViewById(R.id.search_icon);
       //search_icon.setOnClickListener(this);
       blankLayout = (RelativeLayout) rootView.findViewById(R.id.blank_layout);
       loadermain = (RelativeLayout) rootView.findViewById(R.id.loadermain);
       recycle = (RecyclerView) rootView.findViewById(R.id.detail_recycler_view);
      recycle.setHasFixedSize(true);
       recycle.setNestedScrollingEnabled(false);

       dbHelper = DbHelper.getInstance(context);
       dbHelper.createDb(false);
        dbHelper.deletemaptounmapdata();
        dbHelper.deleteunmaptomapdata();

       //callProductListingAPI(offsetValue);
       callallproductAPI(offsetValue);



       recycle.setOnScrollListener(new HidingScrollListener() {
           @Override
           public void onHide() {

               //listfooter.animate().translationY(listfooter.getHeight()).setInterpolator(new AccelerateInterpolator(2));
           }

           @Override
           public void onShow() {
             //  listfooter.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

           }
       });


       /* goToFinalPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                Intent intent = new Intent(context,FillRetailerDetails.class);
                startActivity(intent);
            }
        });*/


       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {



              /* if (showNav != null) {
                   if (showNav.equals("false")) {

*/

                       //Intent inte = new Intent(context,mappedProducts.class);




                      /* Fragment someFragment = new mappedProducts();
                       FragmentTransaction transaction = getFragmentManager().beginTransaction();
                       transaction.replace(R.id.container, someFragment ); // give your fragment container id in first parameter
                       transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                       transaction.commit();*/

                      /* mappedProducts nextFrag= new mappedProducts();
                       UnmappedProducts.this.getFragmentManager().beginTransaction()
                               .replace(R.id.container, nextFrag)
                               .addToBackStack(null)
                               .commit();*/



/*

// Create fragment and give it an argument specifying the article it should show
                       mappedProducts newFragment = new mappedProducts();
                       Bundle args = new Bundle();
                       args.putInt(mappedProducts.ARG_POSITION, position);
                       newFragment.setArguments(args);

                       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                       transaction.replace(R.id.fragment_container, newFragment);
                       transaction.addToBackStack(null);

// Commit the transaction
                       transaction.commit();
*/








                      /* Fragment fragment = new mappedProducts();
                       FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                       fragmentTransaction.replace(R.id.container, fragment);
                       fragmentTransaction.addToBackStack(null);
                       fragmentTransaction.commit();*/
             /*      }
               }

               else {*/


                   getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


                   callselectedProductsAPI(offsetValue);




              // }
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
                   if (loadingMoreforall) {
                       //position starts at 0

                       if (layoutManager1.findLastCompletelyVisibleItemPosition() == layoutManager1.getItemCount() - 1) {
                           callallproductAPI(offsetValue);
                       }
                   }

               }
               else if (allProducts == false){

                   LinearLayoutManager layoutManager2 = (LinearLayoutManager) recyclerView.getLayoutManager();
                   Utilz.count2 = layoutManager2.findFirstCompletelyVisibleItemPosition();
                   if (loadingMoreforsearch){
                       if (layoutManager2.findLastCompletelyVisibleItemPosition() == layoutManager2.getItemCount() - 1) {
                           loadermain.setVisibility(View.INVISIBLE);

                           HashMap hashMap = new HashMap();
                           hashMap.put("mapped", "false");
                           hashMap.put("query", searched_text);
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
               /*else if (loadingMoreforsearch){
                   if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                       loadermain.setVisibility(View.INVISIBLE);

                       HashMap hashMap = new HashMap();
                       hashMap.put("mapped", "false");
                       hashMap.put("query", searched_text);
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




                  // hashMap.put("mapped", "false");
                   hashMap.put("query", searched_text);
                   callsearchtextAPI(hashMap,offsetval);


               }
               else if (searched_text == null || searched_text_length < 2 ){
                   Toast.makeText(context,"Please enter something to search or more than one letter.",Toast.LENGTH_SHORT ).show();
                   recycle.setAdapter(null);
                   allproducts.clear();
                   Utilz.count1 = 0;
                   offsetValue = 0;

                   callallproductAPI(offsetValue);
                   loadingMoreforall = true;



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






    public void callsearchtextAPI(HashMap hashMap , final int offset ){


        offsetValue = offset;
        int row = 10;

        hashMap.put("rows", row);
        hashMap.put("start", offset);
        hashMap.put("mapped","false");


        allProducts = false;

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


                     loadingMoreforall = false;

                    /* if (searchedproducts.size() == 0) {
                         searchedproducts = (ArrayList<Product>) httpResponse.data.responseData.docs;
                     }*/

                     blankLayout.setVisibility(View.GONE);
                     recycle.setVisibility(View.VISIBLE);


                     if (httpResponse.data.size() == 0 || httpResponse.data == null){

                         loadermain.setVisibility(View.INVISIBLE);

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
                         recycle.setAdapter(new unmappedProductList_Adapter(searchedproducts,hash, context, UnmappedProducts.this, true));
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
        final HashMap hashMap = new HashMap();
        hashMap.put("rows", row);
        hashMap.put("start", offset);
        hashMap.put("mapped","false");

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
                    loadermain.setVisibility(View.INVISIBLE);

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
                        Utilz.count1 = allproducts.size();

                       /* if (Utilz.count == 0 ){
                            allproducts.clear();
                        }*/
                        for (int i = 0; i < httpResponse.data.size(); i++) {
                            allproducts.add((RetailerProduct) httpResponse.data.get(i));

                        }
                       //ret_id = allproducts.get(0).retailer_id;








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
                            recycle.setAdapter(new unmappedProductList_Adapter(allproducts,hash, context,UnmappedProducts.this, true));
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

        RetailerProducts retailerPr = new RetailerProducts();

       //ArrayList<RetailerProduct> selected = ((unmappedProductList_Adapter) adapter).getSelectedProducts();

        ArrayList<MappingClass> selected = dbHelper.getunmaptomapdata();
ArrayList<RetailerProduct> sel = new ArrayList<>();


        if (selected.size() == 0){
            Toast.makeText(context,"Please map some products.",Toast.LENGTH_SHORT).show();
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







        //Boolean status = selected.get(i).status;
        /*for (int i=0;i<selected.size();i++) {
            boolean stat = selected.get(i).status;
            selected.get(i).status= Boolean.parseBoolean(selected.get(i).status);


        }*/


/*RetailerProduct alll = new RetailerProduct();
        Product products = new Product();
        ArrayList<RetailerProduct> subs = new ArrayList<>();



        for (int i=0 ; i< selected.size();i++) {



           subs.add(sel.get(i));
            //Boolean boolean1 = Boolean.valueOf("true");




        }*/
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Http_Urls.sBaseUrl).setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);

       // HashMap hashMap = new HashMap();
       // hashMap.putAll();


        apiInterface.getselectedproductsresponse(Utilz.apikey,Utilz.app_version, Utilz.config_version,"application/json", AuthToken,retailerPr, new Callback<HttpResponseofProducts>(){


            @Override
            public void success(HttpResponseofProducts httpresponse , Response response){
                String statu = httpresponse.status;
                String stat = statu.substring(0,1);
                int status = Integer.parseInt(stat);

                if (status == 5){

                   // String msg = httpresponse.error_object.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,"Something went wrong.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();




                }
                else if (status == 4){

                    //String msg = httpresponse.error_object.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdLanding,"Something went wrong.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();



                }
                else if(status == 2){


                    if (showNav.equals("false")){

                        Toast.makeText(context,"Your information has been saved.",Toast.LENGTH_SHORT).show();
                        SharedPreferences prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", context.MODE_PRIVATE);


                        registrationStatus = prefs.getString("registrationStatus",null);

                        if (!registrationStatus.equals("Complete")){

                            callchangeRegStatusAPI();
                        }


                        Intent intent = new Intent(UnmappedProducts.this.getActivity(),signup_thankyou.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }


                    else if (showNav.equals("true")){


                        Toast.makeText(context, "Products has been mapped successfully.", Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(context, mapping.class);

                        if (fromWhere != null) {
                            if (fromWhere.equals("sample")) {
                                intent.putExtra("fromWhere", "sample");
                            }
                        }
                        if (showNav != null) {
                            if (showNav.equals("false")) {
                                intent.putExtra("showNav", "false");
                            }
                        }

                        startActivity(intent);
                        getActivity().finish();

                    }


                }




            }

            @Override
            public void failure(RetrofitError error){


                Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();


            }



        });



    }










    void callchangeRegStatusAPI(){
        HashMap hashm = new HashMap();
        hashm.put("makeActive","yes");


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Http_Urls.sBaseUrl).setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);
        SharedPreferences prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", context.MODE_PRIVATE);
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




    }






   /* void callchangeRegStatusAPI(){
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
