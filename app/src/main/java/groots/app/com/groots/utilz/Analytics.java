package groots.app.com.groots.utilz;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import groots.app.com.groots.R;

/**
 * Created by manmohan on 10/1/17.
 */

public class Analytics {

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    public static void sendScreenName(String screenName, Applicationclass application){
        // Obtain the shared Tracker instance.

        Tracker mTracker = application.getDefaultTracker();

        Log.e("Analytics", "Setting screen name: " + screenName);
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public static void sendEvent(String category, String action, Applicationclass application){
        // Obtain the shared Tracker instance.

        Tracker mTracker = application.getDefaultTracker();

        Log.e("Analytics", "sending event: " + action);

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .build());
    }


}
