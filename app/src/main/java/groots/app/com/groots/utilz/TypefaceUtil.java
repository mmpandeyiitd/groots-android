package groots.app.com.groots.utilz;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 13-06-2016.
 */
public class TypefaceUtil {

    /**
     * Using reflection to override default typeface
     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN
     * @param context to work with assets
     * @param defaultFontNameToOverride for example "monospace"
    // * @param customFontFileNameInAssets file name of the font from assets
     */
    public static void overrideFont(Context context, String defaultFontNameToOverride) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(),"Lato-Regular.ttf");

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
            Log.e(" set font" , " instead of " + defaultFontNameToOverride);
        } catch (Exception e) {
            Log.e("not set font" , " instead of " + defaultFontNameToOverride);
        }
    }
}