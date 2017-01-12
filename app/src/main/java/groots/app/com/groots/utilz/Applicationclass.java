package groots.app.com.groots.utilz;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.Timer;
import java.util.TimerTask;

import groots.app.com.groots.ui.Landing_Update;

/**
 * Created by Administrator on 01-06-2016.
 */
public class Applicationclass extends Application {

    private Timer mActivityTransitionTimer;
    private TimerTask mActivityTransitionTimerTask;
    public boolean wasInBackground;
    private final long MAX_ACTIVITY_TRANSITION_TIME_MS = 670;
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(),"SERIF"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
        sAnalytics = GoogleAnalytics.getInstance(this);
        sAnalytics.setLocalDispatchPeriod(1);
    }

    public void startActivityTransitionTimer() {
        this.mActivityTransitionTimer = new Timer();
        this.mActivityTransitionTimerTask = new TimerTask() {
            public void run() {
                Applicationclass.this.wasInBackground = true;
/*
                SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
*/

                Log.e("startActivityTransition", "App terminated");

            }
        };
        this.mActivityTransitionTimer.schedule(mActivityTransitionTimerTask,
                MAX_ACTIVITY_TRANSITION_TIME_MS);
    }

    public void stopActivityTransitionTimer() {
        if (this.mActivityTransitionTimerTask != null) {
            this.mActivityTransitionTimerTask.cancel();
            //  Log.e("stopActivityTimer","App terminated");
        }

        if (this.mActivityTransitionTimer != null) {
            // Log.e("mActivityTransition","App terminated");
            this.mActivityTransitionTimer.cancel();
        }
        // Log.e("TransitionTimerf","App terminated");
        this.wasInBackground = false;
    }

    private final class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

            Applicationclass myApp = (Applicationclass) activity.getApplication();
            if (myApp.wasInBackground) {
                Log.e("Activity Relaunched", activity.getLocalClassName());
                if(activity.getLocalClassName().equals("ui.Landing_Update"))
                {
                    ((Landing_Update)activity).resumeAPI();
                    ((Landing_Update)activity).backflag=true;
                }
            }
            myApp.stopActivityTransitionTimer();
           /* if(activity.getLocalClassName().equals("ui.Landing_Update"))
            ((Landing_Update)getApplicationContext()).resumeAPI();*/
            Log.e("onActivityResumed:", activity.getLocalClassName());


        }

        @Override
        public void onActivityPaused(Activity activity) {
            ((Applicationclass) activity.getApplication()).startActivityTransitionTimer();
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            //sTracker = sAnalytics.newTracker(R.xml.global_tracker);
            sTracker = sAnalytics.newTracker("UA-89168953-1");
        }

        return sTracker;
    }
}
