

/**
 * Created by aakash on 22/11/16.
 */

package groots.app.com.groots.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import groots.app.com.groots.adapter.Checkout_Update_Order_Adapter;
import groots.app.com.groots.R;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.databases.dbHelp;
import groots.app.com.groots.interfaces.API_Interface;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.model.UpdateCartClass;
import groots.app.com.groots.pojo.AddOrderParent;
import groots.app.com.groots.pojo.DateTimePojo;
import groots.app.com.groots.pojo.HttpResponse;
import groots.app.com.groots.pojo.UpdateOrderParent;
import groots.app.com.groots.pojo.user_profile;
import groots.app.com.groots.utilz.ContainerHolderSingleton;
import groots.app.com.groots.utilz.Http_Urls;
import groots.app.com.groots.utilz.Utilz;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class Checkout_Update_Order_Ui extends AppCompatActivity implements View.OnClickListener, UpdateCart {


    LinearLayout list_main_footer_;
    Context context;
    Checkout_Update_Order_Adapter mAdapter;
    RecyclerView mRecyclerView;
    //Double shippingcharge;
    DbHelper dbHelper;
    Double shippingCharges , total_am;

    ArrayList<user_profile> retailerdetails = new ArrayList<>();
    String shippingAmount,minOrderPrice;
    Float min_orderprice;
    String cust_support_no, order_support_no;
    ArrayList<UpdateCartClass> cartClasses;
    TextView shipping_amount;
    TextView subtxtamount_main, shippingtxtamount_main;

    dbHelp dbHelp;
    TextView txtamount_main;
    String datee;
    UpdateCart updateCart;
    RelativeLayout loaderlayout;
    String data, textcomment, date;
    Dialog dialog;
    Utilz util;
    DecimalFormat df;
    CoordinatorLayout cdcheckout;
    Calendar newCalendar;
    private static final String  screenName = "order-update-checkout";
    private ContainerHolder containerHolder;
    private Container container;
    private DataLayer dataLayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_update_order_ui);
        context = Checkout_Update_Order_Ui.this;




        shippingtxtamount_main = (TextView) findViewById(R.id.shippingtxtamount_main);
        subtxtamount_main = (TextView) findViewById(R.id.subtxtamount_main);

        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);



        dbHelper = DbHelper.getInstance(context);
        dbHelper.createDb(false);

        ArrayList<String> ContactNumbers  = dbHelper.selectfromcontactnumbers();
        cust_support_no = ContactNumbers.get(0);
        order_support_no = ContactNumbers.get(1);




        Intent intent = getIntent();
        String dt = intent.getStringExtra("datee");
        String ship = intent.getStringExtra("shipping");
         shippingCharges = Double.parseDouble(ship);

        ((TextView) findViewById(R.id.txtdate)).setText("Order Summary - " + dt.substring(0,10).trim());



        callretailerdetailsAPI();






        containerHolder = ContainerHolderSingleton.getContainerHolder();
        container = containerHolder.getContainer();
        dataLayer = TagManager.getInstance(this).getDataLayer();
        dataLayer.push(DataLayer.mapOf("event", "openScreen", "screenName", screenName));
        dataLayer.push(DataLayer.mapOf("event", "screenVisible", "screenName", screenName));

        updateCart = this;
        dbHelp = new dbHelp(context);
        dbHelp.createDb(false);
        newCalendar = Calendar.getInstance();

        txtamount_main = (TextView) findViewById(R.id.txtamount_main);
        list_main_footer_ = (LinearLayout) findViewById(R.id.list_main_footer_);
        loaderlayout = (RelativeLayout) findViewById(R.id.loaderxml);
        loaderlayout.setOnClickListener(this);
       // shipping_amount = (TextView) findViewById(R.id.shipping_amount);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        //  ((TextView)findViewById(R.id.txtdate)).setText(new SimpleDateFormat("dd MMM yyyy").format(Calendar.getInstance().getTime()));
        util=new Utilz();
       /* calling date time Api.........................*/
        cdcheckout = (CoordinatorLayout) findViewById(R.id.cdcheckout);
        if (!util.isInternetConnected(Checkout_Update_Order_Ui.this)) {
            Snackbar snackbar = Snackbar.make(cdcheckout, "Please check the internet connection.", Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();
        } else
            CallDateTimeApi();



        mRecyclerView = (RecyclerView) findViewById(R.id.checkout_recycle);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        SlideInLeftAnimator slideInRightAnimationAdapter = new SlideInLeftAnimator();
        slideInRightAnimationAdapter.setInterpolator(new OvershootInterpolator());
        slideInRightAnimationAdapter.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(slideInRightAnimationAdapter);
        cartClasses = dbHelp.updateorder();

        if (cartClasses.size() > 0) {
            mAdapter = new Checkout_Update_Order_Adapter(cartClasses, this, updateCart ,shippingCharges);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }



        float priceinDb = dbHelp.fetchTotalUpdateCartAmount();
        total_am = priceinDb + shippingCharges;

        txtamount_main.setText("" + df.format(total_am));
        subtxtamount_main.setText("" + priceinDb);

        /*if (Float.compare(priceinDb ,min_orderprice ) > 0){


            shipping_amount.setText(shippingcharge.toString());



        }
*/

        ((ImageView) findViewById(R.id.makecall)).setOnClickListener(this);
        ((TextView) findViewById(R.id.checkouticon_checkout)).setOnClickListener(this);
        ((TextView) findViewById(R.id.checkoutback)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.backbtn)).setOnClickListener(this);
    }

    private void CallDateTimeApi() {


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
                    //   Toast.makeText(Checkout_Update_Order_Ui.this, addOrderParent.getMsg(), Toast.LENGTH_SHORT).show();
                   /* loaderlayout.setVisibility(View.INVISIBLE);*/
                    Snackbar snackbar = Snackbar.make(cdcheckout, "Oops! Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else if (status == -1) {
                    loaderlayout.setVisibility(View.INVISIBLE);
                    Snackbar snackbar = Snackbar.make(cdcheckout, "Oops! Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else if (status == 1) {
                    date = dateTimePojo.getData().getCurrentDateTime();
                    //  Toast.makeText(context, date, Toast.LENGTH_SHORT).show();
                   /* try {
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

                           // ((TextView) findViewById(R.id.datetxt)).setText("(Orders placed between 2AM to 9AM might not be processed)");
                            ((TextView)dialog.findViewById(R.id.datetxtd)).setText(date.substring(0,10).trim());
                            data = dateFormatter.parse(date.substring(0,10).trim()).toString();
                        } else {

                            float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics());
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.headerdate);
                            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) pixels);
                            layout.setLayoutParams(parms);
                            ((TextView) findViewById(R.id.datetxt)).setVisibility(View.GONE);
                            //((TextView) findViewById(R.id.txtdate)).setText("Order Summary - " + (date.substring(0,10).trim()));


                            newCalendar.setTime(dateFormatter.parse(date.substring(0,10).trim()));
                            newCalendar.add(Calendar.DATE, 1);
                            ((TextView) dialog.findViewById(R.id.datetxtd)).setText(dateFormatter.format(newCalendar.getTime()).toString());
                            data = dateFormatter.format(newCalendar.getTime());

                        }
                    } catch (Exception e) {
//                        Log.e("Exception is ",e.getCause().toString());
                    }*/
                }

            }

            @Override
            public void failure(RetrofitError error) {
                loaderlayout.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdcheckout, "Oops! Something went wrong. Please try again.", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

            }
        });
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

                    makeAcall(order_support_no);
                    dismiss();
                }
            });
            ((LinearLayout) findViewById(R.id.custsupport)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeAcall(cust_support_no);
                    dismiss();
                }
            });
        }
    }


    private void makeAcall(String phn) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(Checkout_Update_Order_Ui.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 9);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phn));
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + "9999999999"));
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {



       /* startActivity(new Intent(Checkout_Update_Order_Ui.this , UpdateOrder.class));
        finish();*/
        updateCart.updateCart();

        dbHelper.deleteOrderHeadData();
        super.onBackPressed();

      //  updateCart.updateCart();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.makecall:
                ShowDialog showdialog = new ShowDialog(this);
                showdialog.show();
                break;

            case R.id.checkouticon_checkout:
             /* Intent intent =new Intent(Checkout_Update_Order_Ui.this,Thank_You_UI.class);
              startActivity(intent);
              overridePendingTransition(R.anim.from_middle, R.anim.to_middle);*/
                if (cartClasses.size() > 0) {

                    makedialogfrag();

                } else {
                    final CoordinatorLayout cdcheckout = (CoordinatorLayout) findViewById(R.id.cdcheckout);
                    Snackbar snackbar = Snackbar.make(cdcheckout, "Please Add Something in Cart !", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }
                break;

            case R.id.backbtn:
                 /* Intent inte = new Intent(Checkout_Update_Order_Ui.this , UpdateOrder.class);
                startActivity(inte);
                finish();*/


                onBackPressed();
                //dbHelp.deleterec();
                break;

            case R.id.checkoutback:
                onBackPressed();
                break;
            default:
                break;

        }

    }




    public void callUpdateOrderAPI(String comment, String d) {

        Intent intent = getIntent();
        String orderI = intent.getStringExtra("orderId");




        int orderId = Integer.parseInt(orderI);


        loaderlayout.bringToFront();
        loaderlayout.setVisibility(View.VISIBLE);
        dialog.dismiss();
        HashMap hashmap = new HashMap();
        Double total = 0.00;
        for (int count = 0; count < cartClasses.size(); count++) {
           // hashmap.put("data[product_details][" + count + "][order_id]" , orderId );
            hashmap.put("data[product_details][" + count + "][subscribed_product_id]", cartClasses.get(count).subscribe_prod_id);
            hashmap.put("data[product_details][" + count + "][base_product_id]", cartClasses.get(count).base_product_id);
            hashmap.put("data[product_details][" + count + "][store_id]", cartClasses.get(count).store_id);
            hashmap.put("data[product_details][" + count + "][store_front_id]", cartClasses.get(count).store_id);
            hashmap.put("data[product_details][" + count + "][product_name]", cartClasses.get(count).product_name);
            hashmap.put("data[product_details][" + count + "][product_qty]", cartClasses.get(count).product_qty);
            hashmap.put("data[product_details][" + count + "][unit_price]", cartClasses.get(count).unit_price);
            hashmap.put("data[product_details][" + count + "][tax]", 0);
            Double item_price =  cartClasses.get(count).product_qty * cartClasses.get(count).unit_price ;
            hashmap.put("data[product_details][" + count + "][price]", item_price);
            total = cartClasses.get(count).total_unit_price + total;

        }

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);

       // hashmap.put("data[order_prefix]", "GRT");
       // hashmap.put("data[order_type]", "normal_payment");
        hashmap.put("data[shipping_charges]",shippingCharges);
       // hashmap.put("data[coupon_code]", "");
        hashmap.put("data[total]", dbHelp.fetchTotalUpdateCartAmount());
        hashmap.put("data[total_payable_amount]", dbHelper.fetchTotalAmountOrderHead());
       /* hashmap.put("data[buyer_email]", 1);
        hashmap.put("data[discount_amt]", 0);
        hashmap.put("data[user_id]", prefs.getString("User_Id", null));
        hashmap.put("data[cart_id]", 1);
        hashmap.put("data[total_shipping_charges]", 0);
        hashmap.put("data[total_tax]", 0);*/
        hashmap.put("data[comment]", comment);
        hashmap.put("data[delivery_date]", d);
        hashmap.put("data[order_id]",orderId);



        final CoordinatorLayout cdcheckout = (CoordinatorLayout) findViewById(R.id.cdcheckout);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);

        apiInterface.getUpdateOrderResponce(Utilz.apikey, Utilz.app_version, Utilz.config_version, AuthToken, hashmap, new Callback<UpdateOrderParent>() {
            @Override
            public void success(UpdateOrderParent updateOrderParent, Response response) {

                String status = updateOrderParent.getStatus();

//                String order_no=addOrderParent.getData().getOrderNo();
                //  int order_id=addOrderParent.getData().getOrderId();
                if (status.equals("0")) {




                    loaderlayout.setVisibility(View.INVISIBLE);
                    //String msg = (String) updateOrderParent.error.get(0);
                    Snackbar snackbar = Snackbar.make(cdcheckout, "something went wrong", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) (snackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
                    //tv.setText(msg);
                    tv.setLines(3);
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else if (status.equals("-1")) {
                    loaderlayout.setVisibility(View.INVISIBLE);
                    String msg = (String) updateOrderParent.getErrors().get(0);
                    Snackbar snackbar = Snackbar.make(cdcheckout,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else if (status.equals("1")) {

                    loaderlayout.setVisibility(View.INVISIBLE);
                    dbHelp.deleterec();
                    dbHelper.deleteOrderHeadData();
                    Intent intent = new Intent(Checkout_Update_Order_Ui.this, Thank_You_UI.class);
                    intent.putExtra("order_status","update");
                    startActivity(intent);
                    overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                    finish();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                loaderlayout.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdcheckout, "Oops! Something went wrong. Please try again.", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

            }
        });
    }


    @Override
    public void updateCart() {


        callretailerdetailsAPI();

        float priceinDb = dbHelp.fetchTotalUpdateCartAmount();
        if (priceinDb > 0) {

            ArrayList<UpdateCartClass> cartClasses = dbHelp.updateorder();
            mRecyclerView.setVisibility(View.VISIBLE);
            total_am = priceinDb + shippingCharges;
            txtamount_main.setText("" + df.format(total_am));

            subtxtamount_main.setText("" + priceinDb);

            ((LinearLayout) findViewById(R.id.llEmptyCart)).setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.list_main_footer_)).setVisibility(View.VISIBLE);
        } else {
            txtamount_main.setText(shippingCharges.toString());
            subtxtamount_main.setText("0.0");
            ((LinearLayout) findViewById(R.id.llEmptyCart)).setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.list_main_footer_)).setVisibility(View.GONE);
        }
        dbHelper.insertOrderHeadData(shippingCharges , total_am ,priceinDb);

    }

    @Override
    public void updateTotalAmnt(int childCount) {

    }

    private void makedialogfrag() {

        if (!util.isInternetConnected(Checkout_Update_Order_Ui.this)) {
            Snackbar snackbar = Snackbar.make(cdcheckout, "Please check the internet connection.", Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();
        } else
            CallDateTimeApi();
        // Toast.makeText(this,date.substring(0,4).trim()+date.substring(5,7).trim()+date.substring(8,10).trim(),Toast.LENGTH_LONG).show();

        dialog = new Dialog(Checkout_Update_Order_Ui.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.datepickerdialog);
        dialog.setCancelable(true);

        final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        //  ImageButton btnclik = (ImageButton) dialog.findViewById(R.id.btnclik);
        final CoordinatorLayout cdDatePicker = (CoordinatorLayout) dialog.findViewById(R.id.cdDatePicker);

        final EditText commentbox = (EditText) dialog.findViewById(R.id.commentbox);
        TextView textdone = (TextView) dialog.findViewById(R.id.textdone);
        TextView textcancel = (TextView) dialog.findViewById(R.id.textcancel);
        final TextView txtdate = ((TextView) dialog.findViewById(R.id.datetxtd));



        Intent intent = getIntent();
        String dt = intent.getStringExtra("datee");

        txtdate.setText(dt.substring(0,10).trim());
         data = dt.substring(0,10).trim();
        datee = date.substring(0,10).trim();




        try {

            newCalendar.setTime(dateFormatter.parse(dt.substring(0, 10).trim()));

           /* Date d1 = formatter.parse(date.substring(11).trim());
            Date d2 = formatter.parse("00:00:00");
            Date d3 = formatter.parse("02:00:00");


            if (d1.before(d3) & d1.after(d2)) {


                newCalendar.setTime(dateFormatter.parse(date.substring(0, 10).trim()));
            }
            else
            {
                newCalendar.add(Calendar.DATE, 1);
                newCalendar.setTime(dateFormatter.parse(date.substring(0, 10).trim()));
            }*/
        }catch (Exception e)
        {

        }



        ((LinearLayout) dialog.findViewById(R.id.main_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* try {


                    DatePickerDialog fromDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        //    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);;

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                            try {

                                if(Integer.parseInt(date.substring(0,4).trim())>year||Integer.parseInt(date.substring(5,7).trim())>dayOfMonth||Integer.parseInt(date.substring(8,10).trim())>monthOfYear)
                                {
                                    return;
                                }
                                else {
                                    newCalendar.set(year, monthOfYear, dayOfMonth);
                                    data = dateFormatter.format(newCalendar.getTime());
                                    txtdate.setText(dateFormatter.format(newCalendar.getTime()));
                                }
                            }
                            catch(Exception e)
                            {

                            }

                        }
                    }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    fromDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());

                    fromDatePickerDialog.show();
                }catch(Exception e)
                {

                }*/
            }
        });

        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                textcomment = null;
                data = null;
            }
        });
        textcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        textdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (data == null) {
                    Snackbar snackbar = Snackbar.make(cdDatePicker, "Please choose your delivery date.", Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        if (datee.equals(data)) {


                            if (!util.isInternetConnected(Checkout_Update_Order_Ui.this)) {
                                Snackbar snackbar = Snackbar.make(cdcheckout, "Please check the internet connection.", Snackbar.LENGTH_SHORT);
                                snackbar.setActionTextColor(Color.WHITE);
                                View snackbarView = snackbar.getView();
                                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                snackbar.show();
                            } else {
                            /*System.out.println(data);
                            System.out.println(commentbox.getText().toString());*/
                                callUpdateOrderAPI(commentbox.getText().toString(), data);


                            }
                        }
                    } catch (Exception e) {

                         e.printStackTrace();
                         // Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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
                    Snackbar snackbar = Snackbar.make(cdcheckout,msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                } else if (status == 0) {

                    String msg = httpResponse.errors.get(0).toString();
                    Snackbar snackbar = Snackbar.make(cdcheckout,msg, Snackbar.LENGTH_SHORT);
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
                    // Double outstandingAmount = retailerdetails.get(0).outstandingAmount;
                    // Double shippingCharge = retailerdetails.get(0).shippingCharge;
                    Double min_order_price;
                    if (retailerdetails.get(0).minOrderPrice == null){
                        min_order_price = 0.0;

                    }
                    else
                    {
                        min_order_price = retailerdetails.get(0).minOrderPrice;
                    }

                    minOrderPrice = min_order_price.toString();
                    min_orderprice =Float.parseFloat(minOrderPrice);



                    float priceinDb = dbHelp.fetchTotalUpdateCartAmount();
                   Double shippingcharges = retailerdetails.get(0).shippingCharge;






                         if (Float.compare(priceinDb ,min_orderprice ) < 0){


                             //  shipping_amount.setText(shippingcharge.toString());
                             shippingtxtamount_main.setText(shippingcharges.toString());
                           shippingCharges = shippingcharges;

                             //shippingtxtamount_main.setText(shippingCharges.toString());



                         }
                         else if (min_order_price == 0.0){
                             shippingtxtamount_main.setText(shippingcharges.toString());
                             shippingCharges = shippingcharges;



                         }

                         else {
                             shippingtxtamount_main.setText("0.0");
                             shippingCharges = 0.0;
                         }





                   /* if (retailerdetails.get(0).shippingCharge == null){
                        shippingcharge = 0.0 ;
                    }
                    else {
                        shippingcharge = retailerdetails.get(0).shippingCharge;

                    }*/

                    //Double shipping_amount = retailerdetails.get(0).shippingCharge;


                    // shipping_amount.setText(shippingcharge.toString());
                   // shipping_amount.setText("0.0");



                    /*else if (min_order_price != 0.0){

                    }*/

                    shippingAmount = shippingCharges.toString();



                    total_am = priceinDb + shippingCharges;
                    txtamount_main.setText("" + df.format(total_am));
                    subtxtamount_main.setText("" + priceinDb);




                    dbHelper.insertOrderHeadData(shippingCharges , total_am ,priceinDb);








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
                    }
*/
                    // if (httpResponse.data.responseData.docs.size() != 0 ){



                    //}



                }









            }









            @Override
            public void failure(RetrofitError error) {
                //progressMobile.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdcheckout, "Oops! Something went wrong.Please try again later !...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

            }

        });





    }

}
