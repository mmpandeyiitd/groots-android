package groots.canbrand.com.groots.utilz;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewLatoRegular extends TextView {

	public TextViewLatoRegular(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TextViewLatoRegular(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextViewLatoRegular(Context context) {
		super(context);
		init();
	}

	public void init() {
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
				"Lato-Regular.ttf");
		setTypeface(tf);		

	}
	


}
