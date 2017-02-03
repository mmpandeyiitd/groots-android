package groots.app.com.groots.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import groots.app.com.groots.R;

public class Thank_You_UI extends AppCompatActivity {
    TextView first_line , second_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank__you__ui);

        Button backbtn;
        backbtn=(Button)findViewById(R.id.backbtn);
        first_line = (TextView) findViewById(R.id.first_line);
        second_line = (TextView) findViewById(R.id.second_line);
        Intent inten = getIntent();
        String order_status = inten.getStringExtra("order_status");

        if (order_status.equals("place")){
            first_line.setText("  THANK YOU FOR");
            second_line.setText("  ORDERING WITH US!");

        }
        else if (order_status.equals("update")){
            first_line.setText("  YOUR ORDER HAS");
            second_line.setText("  BEEN UPDATED!");



        }

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Thank_You_UI.this,Landing_Update.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

              //  onBackPressed();
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
