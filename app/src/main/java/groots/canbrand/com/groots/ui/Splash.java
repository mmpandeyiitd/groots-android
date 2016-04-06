package groots.canbrand.com.groots.Ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;

import org.w3c.dom.Text;

import groots.canbrand.com.groots.R;

public class Splash extends AppCompatActivity {


    ImageView ivGroots;
    AlphaAnimation animation1;
    KenBurnsView kbv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivGroots=(ImageView)findViewById(R.id.ivGroots);
        kbv = (KenBurnsView) findViewById(R.id.image);

        animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(3000);
        animation1.setStartOffset(5000);

        ivGroots.startAnimation(animation1);
        //animation1 AnimationListener
        animation1.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
               // kbv.pause();
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });

        kbv.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }
            @Override
            public void onTransitionEnd(Transition transition) {
//                Intent i=new Intent(Splash.this, Landing_UI.class);
//                startActivity(i);
                Toast.makeText(Splash.this, "End", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
