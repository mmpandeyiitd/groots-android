package groots.canbrand.com.groots.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.databases.DbHelper;
import groots.canbrand.com.groots.fragments.DetailFrag;
import groots.canbrand.com.groots.fragments.MainFrag;
import groots.canbrand.com.groots.interfaces.API_Interface;
import groots.canbrand.com.groots.pojo.ProductListData;
import groots.canbrand.com.groots.pojo.ProductListDocData;
import groots.canbrand.com.groots.utilz.Http_Urls;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;


public class Landing_UI extends AppCompatActivity
        implements View.OnClickListener {



    boolean flag = false;
    NavigationView navigationView;
    RelativeLayout navOrder, navHelp, navContact, navRate, navLogout, navAbout;
    CoordinatorLayout cdLanding;
    ArrayList<ProductListDocData> productListDocDatas;
    public static Context context;
    RelativeLayout loadermain;
    DrawerLayout drawer;
    DbHelper dbHelper;
    SmoothActionBarDrawerToggle     mDrawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing__ui);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);

        loadermain=(RelativeLayout) findViewById(R.id.loadermain);

        context = Landing_UI.this;
        productListDocDatas = new ArrayList<>();
        dbHelper = new DbHelper(context);
        dbHelper.createDb(false);
        cdLanding = (CoordinatorLayout) findViewById(R.id.cdLanding);
        navOrder = (RelativeLayout) findViewById(R.id.pending_menu);
        navHelp = (RelativeLayout) findViewById(R.id.help_menu);
        navContact = (RelativeLayout) findViewById(R.id.contact_menu);
        navRate = (RelativeLayout) findViewById(R.id.rate_menu);
        navLogout = (RelativeLayout) findViewById(R.id.about_menu);
        navAbout = (RelativeLayout) findViewById(R.id.logout_menu);

        navOrder.setOnClickListener(this);
        navHelp.setOnClickListener(this);
        navContact.setOnClickListener(this);
        navRate.setOnClickListener(this);
        navLogout.setOnClickListener(this);
        navAbout.setOnClickListener(this);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//
//
//                int itemInCart = dbHelper.getTotalRow();
//                if (itemInCart > 0) {
//                    navOrder.setVisibility(View.VISIBLE);
//                } else
//                    navOrder.setVisibility(View.GONE);
//
//            }
//        };
//
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();


              mDrawerToggle = new SmoothActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(mDrawerToggle);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_landing__ui);
        RelativeLayout rl_header = (RelativeLayout) headerView.findViewById(R.id.parentlayout);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userName = bundle.getString("USERNAME");
            String name = "";
            if (userName.contains(" ")) {
                String fname = userName.substring(0, userName.indexOf(" "));
                String lname = userName.substring(userName.indexOf(" "), userName.length());
                name = fname.substring(0, 1).concat(lname.substring(0, 1));

            } else {
                name = userName.substring(0, 1);
            }

            SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
            editor.putString("Name", name);
            editor.putString("UserName", userName);
            editor.commit();

        }
        TextView imageViewheader = (TextView) headerView.findViewById(R.id.imageViewheader);
        TextView txtViewName = (TextView) headerView.findViewById(R.id.txtViewName);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

        imageViewheader.setText(prefs.getString("Name", null));
        txtViewName.setText(prefs.getString("UserName", null));

        // navigationView.setNavigationItemSelectedListener(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        HashMap hashMap=new HashMap();
        hashMap.put("abc", "abc");
        callProductListingAPI(hashMap);
    }

   @Override
    protected void onResume() {
        super.onResume();

      /*  if(productListDocDatas.size()>0) {
            ArrayList<CartClass> cartClasses = dbHelper.getProductQty();
            if (cartClasses != null && cartClasses.size() > 0 && productListDocDatas != null) {
                for (int i = 0; i < productListDocDatas.size(); i++) {

                    for (int j = 0; j < cartClasses.size(); j++) {

                        if (productListDocDatas.get(i).subscribedProductId == cartClasses.get(j).subscribe_prod_id) {
                            productListDocDatas.get(i).setItemCount(cartClasses.get(j).product_qty);
                        }
                        else productListDocDatas.get(i).setItemCount(0);
                    }
                }
            }
        }
        else {
            HashMap hashMap = new HashMap();
            hashMap.put("abc", "abc");
            callProductListingAPI(hashMap);
        }*/


       if(MainFrag.mRecyclerView!=null)
           MainFrag.mRecyclerView.removeAllViews();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing__ui, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.show) {
            if (flag == false) {
                flag = true;

                FragmentManager manager = getFragmentManager();
                DetailFrag detailFrag = new DetailFrag(productListDocDatas);
                MainFrag mainFrag = new MainFrag();

                manager.beginTransaction().setCustomAnimations(R.animator.fadein, R.animator.fadeout, R.animator.fadeout, R.animator.fadein)
                        .replace(R.id.frameLayoutForAllFrags, detailFrag, "loadingFragment").remove(mainFrag).commit();

                // getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutForAllFrags, detailFrag).commitAllowingStateLoss();
                item.setIcon(R.drawable.list_view);
            } else {
                FragmentManager manager = getFragmentManager();
                MainFrag mainFrag = new MainFrag(productListDocDatas);
                flag = false;
                manager.beginTransaction().setCustomAnimations(R.animator.fadein, R.animator.fadeout, R.animator.fadeout, R.animator.fadein)
                        .replace(R.id.frameLayoutForAllFrags, mainFrag, "loadingFragment").commit();
                //  getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutForAllFrags, new MainFrag()).commitAllowingStateLoss();
                item.setIcon(R.drawable.expanded_list_view);
            }


        } else if (id == R.id.phone) {
            makeaCall();
        }

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    private void makeaCall() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(Landing_UI.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 9);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "9999999999"));
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + "9999999999"));
            startActivity(intent);
        }

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.pending_menu:

                mDrawerToggle.runWhenIdle(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, Checkout_Ui.class);
                        startActivity(intent);
                      //  overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                });
                drawer.closeDrawers();

//                Intent intent = new Intent(context, Checkout_Ui.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//                drawer.closeDrawer(GravityCompat.START);

                break;

            case R.id.help_menu:
                Toast.makeText(this, "Help Menu Pressed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.contact_menu:
                Toast.makeText(this, "Contact Menu Pressed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rate_menu:
                Toast.makeText(this, "Rate Menu Pressed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.about_menu:
                Toast.makeText(this, "About Menu Pressed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout_menu:
                //  Toast.makeText(this,"Logout Menu Pressed",Toast.LENGTH_SHORT).show();
                logoutPopUp();
                break;
            default:
                break;
        }
        // drawer.closeDrawer(GravityCompat.START);

    }

    private class SmoothActionBarDrawerToggle extends ActionBarDrawerToggle {

        private Runnable runnable;

        public SmoothActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
            if (runnable != null && newState == DrawerLayout.STATE_IDLE) {
                runnable.run();
                runnable = null;
            }
            int itemInCart = dbHelper.getTotalRow();
                if (itemInCart > 0) {
                    navOrder.setVisibility(View.VISIBLE);
                } else
                    navOrder.setVisibility(View.GONE);

        }

        public void runWhenIdle(Runnable runnable) {
            this.runnable = runnable;
        }
    }

    private void logoutPopUp() {
        final Dialog logoutdialog = new Dialog(Landing_UI.this);
        logoutdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logoutdialog.setContentView(R.layout.logout_layout);
        logoutdialog.setCancelable(true);
        TextView logout = (TextView) logoutdialog.findViewById(R.id.logout);
        TextView cancel = (TextView) logoutdialog.findViewById(R.id.cancel);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
                SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                editor.putString("Check", "name");
                editor.commit();


                File cache = getCacheDir();
                File appDir = new File(cache.getParent());
                if (appDir.exists()) {
                    String[] children = appDir.list();

                    for (String s : children) {

                        File f = new File(appDir, s);
                        if (deleteDir(f))
                            System.out.println("delete" + f.getPath());
                    }
                }
                Intent i = new Intent(Landing_UI.this, Splash.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("sender", "logout");
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


    void callProductListingAPI(HashMap hashMap) {

        //progressDialog.show();
        loadermain.setVisibility(View.VISIBLE);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Http_Urls.sBaseUrl)
                .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
        API_Interface apiInterface = restAdapter.create(API_Interface.class);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String AuthToken = prefs.getString("AuthToken", null);

        apiInterface.getproductListingResponse("andapikey", "1.0", "1.0", AuthToken, hashMap, new Callback<ProductListData>() {

            @Override
            public void success(ProductListData productListData, Response response) {

                loadermain.setVisibility(View.INVISIBLE);
              //  progressDialog.dismiss();
                    int status=productListData.status;


                if (status == -1) {

                    String msg = productListData.msg;
                    Snackbar snackbar = Snackbar.make(cdLanding, msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                } else if (status == 0) {

                    String msg = productListData.msg;
                    Snackbar snackbar = Snackbar.make(cdLanding, msg, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                } else if (status == 1) {

                    String msg = productListData.msg;

                    productListDocDatas = (ArrayList<ProductListDocData>) productListData.data.response.docs;

                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.frameLayoutForAllFrags, new MainFrag(productListDocDatas)).commitAllowingStateLoss();


                }

            }

            @Override
            public void failure(RetrofitError error) {
                //progressDialog.dismiss();
                loadermain.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(cdLanding, "Oops! Some Techincal Error...", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

            }
        });
    }


}
