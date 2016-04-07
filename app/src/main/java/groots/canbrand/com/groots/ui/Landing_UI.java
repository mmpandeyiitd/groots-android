package groots.canbrand.com.groots.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.ActionBar;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.design.widget.NavigationView;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import groots.canbrand.com.groots.Fragments.DetailFrag;
import groots.canbrand.com.groots.Fragments.MainFrag;



import groots.canbrand.com.groots.R;



public class Landing_UI extends AppCompatActivity
        implements View.OnClickListener {

    boolean flag=false;
    NavigationView navigationView;
    RelativeLayout navOrder,navHelp,navContact,navRate,navLogout,navAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing__ui);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);

        navOrder=(RelativeLayout)findViewById(R.id.pending_menu);
        navHelp=(RelativeLayout)findViewById(R.id.help_menu);
        navContact=(RelativeLayout)findViewById(R.id.contact_menu);
        navRate=(RelativeLayout)findViewById(R.id.rate_menu);
        navLogout=(RelativeLayout)findViewById(R.id.about_menu);
        navAbout=(RelativeLayout)findViewById(R.id.logout_menu);

        navOrder.setOnClickListener(this);
        navHelp.setOnClickListener(this);
        navContact.setOnClickListener(this);
        navRate.setOnClickListener(this);
        navLogout.setOnClickListener(this);
        navAbout.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutForAllFrags, new MainFrag()).commitAllowingStateLoss();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
       // navigationView.setNavigationItemSelectedListener(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

           actionBar.setTitle("");
           actionBar.setHomeAsUpIndicator(R.drawable.menu);
            actionBar.setDisplayHomeAsUpEnabled(true);


        }
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
            if(flag==false) {
                flag=true;
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutForAllFrags, new DetailFrag()).commitAllowingStateLoss();
                item.setIcon(R.drawable.list_view);
            }
            else
            {
                flag=false;
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutForAllFrags, new MainFrag()).commitAllowingStateLoss();
                item.setIcon(R.drawable.expanded_list_view);
            }


        }
        else if(id==R.id.phone)
        {
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
        }
    }

/*    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

      *//*  if (id == R.id.pending_menu) {
            // Handle the camera action
        } else if (id == R.id.help_menu) {

        } else if (id == R.id.contact_menu) {

        } else if (id == R.id.rate_menu) {

        } else if (id == R.id.about_menu) {

        } else if (id == R.id.logout_menu) {

        }
*//*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

    @Override
    public void onClick(View view) {

       switch (view.getId())
       {
           case R.id.pending_menu:
              Toast.makeText(this,"Pending Menu Pressed",Toast.LENGTH_SHORT).show();
               break;
           case R.id.help_menu:
               Toast.makeText(this,"Help Menu Pressed",Toast.LENGTH_SHORT).show();
               break;
           case R.id.contact_menu:
               Toast.makeText(this,"Contact Menu Pressed",Toast.LENGTH_SHORT).show();
               break;
           case R.id.rate_menu:
               Toast.makeText(this,"Rate Menu Pressed",Toast.LENGTH_SHORT).show();
               break;
           case R.id.about_menu:
               Toast.makeText(this,"About Menu Pressed",Toast.LENGTH_SHORT).show();
               break;
           case R.id.logout_menu:
               Toast.makeText(this,"Logout Menu Pressed",Toast.LENGTH_SHORT).show();
               break;
           default:
               break;
       }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
}