package groots.app.com.groots.utilz;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import groots.app.com.groots.R;

/**
 * Created by Administrator on 19-04-2016.
 */
public class Loader extends ImageView {

    public Loader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Loader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Loader(Context context) {
        super(context);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.anim_list);
        final AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
        post(new Runnable(){
            public void run(){
                frameAnimation.start();
            }
        });
    }
}
