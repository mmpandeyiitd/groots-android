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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;

import groots.app.com.groots.R;
import groots.app.com.groots.adapter.Detail_Adapter;
import groots.app.com.groots.adapter.Landing_Adapter;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.utilz.Constants;
import groots.app.com.groots.utilz.Utilz;

public class mapping extends AppCompatActivity implements View.OnClickListener {


    private ShowcaseView showcaseView;
    private int caseshow;
    private Target t1,t2,t3;




    NavigationView navigationView;
    DrawerLayout drawer;
    DbHelper dbHelper;
    String shownav = "true";

    CoordinatorLayout cdmapping;
    String cust_support_no, order_support_no;
    RelativeLayout navOrder, navHelp, navContact, navRate, navLogout, navAbout, navorderHis , navaddOrder,navAllProducts;

    Context context;
    Utilz utilz;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);



       Intent intent = getIntent();
         shownav = intent.getStringExtra("showNav");


        if (shownav != null ){
            shownav = "false";
        }
        else
        {
            shownav = "true";
        }





        t1 = new ViewTarget(R.id.tabs,this);
        showcaseView = new ShowcaseView.Builder(this).setTarget(Target.NONE).setContentTitle("map products").setContentText("Tabs for mapping your products.").setOnClickListener(this).build();
        showcaseView.setButtonText("Map Products");



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);

        dbHelper = DbHelper.getInstance(context);
        dbHelper.createDb(false);

        cdmapping = (CoordinatorLayout) findViewById(R.id.main_content);









        navOrder = (RelativeLayout) findViewById(R.id.pending_menu);
        navHelp = (RelativeLayout) findViewById(R.id.help_menu);
        navContact = (RelativeLayout) findViewById(R.id.contact_menu);
        navorderHis = (RelativeLayout) findViewById(R.id.orderHis_menu);

        navRate = (RelativeLayout) findViewById(R.id.rate_menu);
        navLogout = (RelativeLayout) findViewById(R.id.about_menu);
        navAbout = (RelativeLayout) findViewById(R.id.logout_menu);
        navaddOrder = (RelativeLayout)findViewById(R.id.addOrder_menu);
        navaddOrder.setVisibility(View.GONE);

        navOrder.setOnClickListener(this);
        navHelp.setOnClickListener(this);
        navAllProducts = (RelativeLayout) findViewById(R.id.allproducts_menu);
        navAllProducts.setOnClickListener(this);
        navContact.setOnClickListener(this);
        navorderHis.setOnClickListener(this);
        navRate.setOnClickListener(this);
        navLogout.setOnClickListener(this);
        navAbout.setOnClickListener(this);


        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.putString("Check", "true");
        editor.commit();

        ArrayList<String> ContactNumbers  = dbHelper.selectfromcontactnumbers();
        cust_support_no = ContactNumbers.get(0);
        order_support_no = ContactNumbers.get(1);

        utilz = new Utilz();




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
       // ((TextView) findViewById(R.id.tooltext)).setText(prefs.getString("Retailer_Name", null));






/*if (!(shownav.equals("false")) || shownav.isEmpty() || shownav.length() == 0) {*/

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
        actionBar.setTitle("");
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
//}

        if (shownav.equals("false")){
            navigationView.setVisibility(View.GONE);
            headerView.setVisibility(View.GONE);
            navAbout.setVisibility(View.GONE);
            navaddOrder.setVisibility(View.GONE);
            navAllProducts.setVisibility(View.GONE);
            navContact.setVisibility(View.GONE);
            navHelp.setVisibility(View.GONE);
            navLogout.setVisibility(View.GONE);
            navorderHis.setVisibility(View.GONE);
            navRate.setVisibility(View.GONE);
            navOrder.setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(false);
            drawer.setEnabled(false);


           // drawer.setVisibility(View.GONE);
            //actionBar.hide();

        }
        else if (shownav.equals("true")) {
            navigationView.setVisibility(View.VISIBLE);
            headerView.setVisibility(View.VISIBLE);
            navAbout.setVisibility(View.VISIBLE);
          //  navaddOrder.setVisibility(View.VISIBLE);
            navAllProducts.setVisibility(View.VISIBLE);
            navContact.setVisibility(View.VISIBLE);
            navHelp.setVisibility(View.VISIBLE);
            navLogout.setVisibility(View.VISIBLE);
            navorderHis.setVisibility(View.VISIBLE);
            navRate.setVisibility(View.VISIBLE);
            //navOrder.setVisibility(View.VISIBLE);
            toggle.setDrawerIndicatorEnabled(true);
            drawer.setEnabled(true);


        }


       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapping, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.product_list, container, false);



           // UnmappedProducts all = new UnmappedProducts();


















          //  TextView textView = (TextView) rootView.findViewById(R.id.section_label);
         //   textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }








    public void onClick(View view) {

        switch(caseshow){
            case 0:
                showcaseView.setShowcase(t1,true);
                showcaseView.setContentTitle("map products");
                showcaseView.setContentText("You can map products from here.");
                break;
            case 1:
                showcaseView.hide();
                break;


        }
        caseshow++;



        mapping.ShowDialog showdialog = new mapping.ShowDialog(this);
        //Landing_Update.ShowDialog showdialog = new Landing_Update.ShowDialog(this);
        switch (view.getId()) {


            case R.id.pending_menu:

                drawer.closeDrawer(GravityCompat.START);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(mapping.this, Checkout_Ui.class);
                        startActivity(intent);
                    }
                };
                new android.os.Handler().postDelayed(runnable, 300);

                break;


            /*case R.id.allproducts_menu:

                drawer.closeDrawer(GravityCompat.START);
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        Intent inten = new Intent(context , mapping.class);
                        startActivity(inten);

                    }
                };
                new android.os.Handler().postDelayed(runnable , 300);
                break;*/

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

                        Intent intent = new Intent(mapping.this, WebPage.class);
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

                        Intent intent = new Intent(mapping.this,History.class);
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



            default:
                break;
        }
        // drawer.closeDrawer(GravityCompat.START);

    }

    private void logoutPopUp() {
        final Dialog logoutdialog = new Dialog(mapping.this);
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
                Intent i = new Intent(mapping.this, Splash.class);
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


    private void makeaCall(String phn) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(mapping.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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











    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                UnmappedProducts unmapp = new UnmappedProducts();
                return unmapp;
                //break;
                case 1:
                    mappedProducts mapp = new mappedProducts();
                    return mapp;
                    //return PlaceholderFragment.newInstance(position + 1);
                  //  break;
            }

           return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    //UnmappedProducts all = new UnmappedProducts();
                    return "Unmapped";


                case 1:
                    return "Mapped";
                /*case 2:
                    return "SECTION 3";*/
            }
            return null;
        }
    }
}
