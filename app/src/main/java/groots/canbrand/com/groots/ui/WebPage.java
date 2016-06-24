package groots.canbrand.com.groots.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import groots.canbrand.com.groots.R;

public class WebPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

            Intent i=getIntent();
            Bundle bundle=i.getExtras();
            ((TextView)findViewById(R.id.headername)).setText(bundle.getString("Name"));
            WebView webView = (WebView) findViewById(R.id.webView1);
            webView.getSettings().setJavaScriptEnabled(true);


        ((LinearLayout)findViewById(R.id.backbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
            webView.setWebViewClient(new WebViewClient());

        if(bundle.getString("Name").equals("About Groots"))
        {
            webView.loadUrl("file:///android_asset/grootsweb.html");
        }
        else
            webView.loadUrl("http://gogroots.com/#about");



    }
}
