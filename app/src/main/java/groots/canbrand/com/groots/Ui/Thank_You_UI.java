package groots.canbrand.com.groots.Ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import groots.canbrand.com.groots.R;

public class Thank_You_UI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank__you__ui);
        Button backbtn;
        backbtn=(Button)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Thank_You_UI.this,"Button Clicked!",Toast.LENGTH_LONG).show();
            }
        });
    }
}
