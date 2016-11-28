package groots.canbrand.com.groots.utilz;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewLatoBold extends TextView {

	public TextViewLatoBold(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TextViewLatoBold(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextViewLatoBold(Context context) {
		super(context);
		init();
	}

	public void init() {
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
				"Lato-Bold.ttf");
		setTypeface(tf);		

	}
	


}
