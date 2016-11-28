package groots.canbrand.com.groots.utilz;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewLatoLight extends TextView {

	public TextViewLatoLight(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TextViewLatoLight(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextViewLatoLight(Context context) {
		super(context);
		init();
	}

	public void init() {
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
				"Lato-Light.ttf");
		setTypeface(tf);		

	}
	


}
