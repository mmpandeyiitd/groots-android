package groots.canbrand.com.groots.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.utilz.Utilz;

/**
 * Created by aakash on 23/11/16.
 */
public class RateUs extends AppCompatActivity implements View.OnClickListener {

    RatingBar ratingBar;
    TextView order_id,date;
    Button ratebutton;
    float value;
    ImageView makecall;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateus);

        Intent i = getIntent();
        final String o_id = i.getStringExtra("ID");
        String date = i.getStringExtra("date");
        ((TextView)findViewById(R.id.order_id)).setText(o_id);
        ((TextView)findViewById(R.id.rateus_date)).setText(date);




        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            public void onRatingChanged(RatingBar ratingBar,float rating,boolean fromUser){

                 value = rating;

            }



        });







        ratebutton = (Button) findViewById(R.id.ratebutton);
        ratebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (value == 5.0){
                    Intent intent = new Intent(RateUs.this, feedback.class);

                    intent.putExtra("value", value);
                    intent.putExtra("o_id",o_id);

                    fivestar_popup();


                }
                else {
                    Intent intent = new Intent(RateUs.this, feedback.class);
                    intent.putExtra("value", value).toString();
                    intent.putExtra("o_id",o_id);

                    startActivity(intent);
                }


            }


        });


        ((ImageView) findViewById(R.id.makecall)).setOnClickListener(this);



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
            ((LinearLayout) findViewById(R.id.orderSupport)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    makeAcall("+91-11-3958-9893");
                    dismiss();
                }
            });
            ((LinearLayout) findViewById(R.id.custsupport)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeAcall("+91-11-3958-8984");
                    dismiss();
                }
            });
        }
    }

    private void makeAcall(String phn) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(RateUs.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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


    //@Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.makecall:
                RateUs.ShowDialog showdialog = new RateUs.ShowDialog(this);
                showdialog.show();
                break;

        }
    }



    private void fivestar_popup(){
        final Dialog fivestar = new Dialog(RateUs.this);
        fivestar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        fivestar.setContentView(R.layout.five_star_layout);
        fivestar.setCancelable(true);
        TextView start_app = (TextView) fivestar.findViewById(R.id.start_app);
        //TextView cancel = (TextView) logoutdialog.findViewById(R.id.cancel);
        start_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                editor.putString("Check", null);
                editor.commit();

                //Utilz.count = 0;
                // File cache = getCacheDir();
                //dbHelper.deleterec();
                // File appDir = new File(cache.getParent());
              /*  if (appDir.exists()) {
                    String[] children = appDir.list();

                    for (String s : children) {

                        File f = new File(appDir, s);
                        if (deleteDir(f))
                            System.out.println("delete" + f.getPath());
                    }
                }*/
                Intent i = new Intent(RateUs.this, Landing_Update.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                //finish();
            }
        });


        fivestar.show();




    }




}
