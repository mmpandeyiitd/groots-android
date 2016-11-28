package groots.canbrand.com.groots.ui;

import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import groots.canbrand.com.groots.R;

//import static groots.canbrand.com.groots.R.id.editText1;


/**
 * Created by aakash on 24/11/16.
 */

public class feedback extends AppCompatActivity {




    Button feedback_submit_button;
    //RadioGroup late_delivery_group,behaviour_group,incomplete_order_group,quality_product_group,pricing_group,other_group;
    CheckBox late_delivery,delivery_boy_behave,incomplete_order,quality_product,pricing,other;
    EditText editText1,editText2,editText3,editText4,editText5,comment;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        Intent i = getIntent();
        String orderId = i.getStringExtra("o_id");
        String rateValue =i.getStringExtra("value");

        feedback_submit_button = (Button) findViewById(R.id.feedback_submit_btn);
        feedback_submit_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent (feedback.this,feedback.class);
                //intent.putExtra("rating",value);
                startActivity(intent);


            }


        });
        final EditText editText1 = (EditText) findViewById(R.id.editText1);
        editText1.setVisibility(View.GONE);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        editText2.setVisibility(View.GONE);
        EditText editText3 = (EditText) findViewById(R.id.editText3);
        editText3.setVisibility(View.GONE);
        EditText editText4 = (EditText) findViewById(R.id.editText4);
        editText4.setVisibility(View.GONE);
        EditText editText5 = (EditText) findViewById(R.id.editText5);
        editText5.setVisibility(View.VISIBLE);
        EditText comment = (EditText) findViewById(R.id.comment);


        /*late_delivery.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        delivery_boy_behave.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        incomplete_order.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        quality_product.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        pricing.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        other.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);*/



       /* final RadioGroup late_delivery_group = (RadioGroup) findViewById(R.id.late_delivery_group);
        late_delivery_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                    //late_delivery.setChecked(true);
                    editText1.setVisibility(View.VISIBLE);

            }
        });*/



    }
}
