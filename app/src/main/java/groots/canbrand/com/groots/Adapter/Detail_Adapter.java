package groots.canbrand.com.groots.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import groots.canbrand.com.groots.Model.LandingInfo;
import groots.canbrand.com.groots.R;

/**
 * Created by Administrator on 04-04-2016.
 */
public class Detail_Adapter extends RecyclerView.Adapter<Detail_Adapter
        .ViewHolder> {


    ArrayList<LandingInfo> dummyValue;
    Context context;

    public Detail_Adapter(ArrayList<LandingInfo> dummyValue, Context context) {
        this.context = context;
        this.dummyValue = dummyValue;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.detail_adapter_layout, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textItemName.setText(dummyValue.get(position).getItemName());
        holder.textItemQuan.setText(dummyValue.get(position).getItemquantity());
        holder.itemPrice.setText(dummyValue.get(position).getItemprice());
        holder.itemdesc.setText(dummyValue.get(position).getItemDesc());
        holder.itemquantity.setText(dummyValue.get(position).getItemquantity());
        holder.itemdia.setText(dummyValue.get(position).getItemdia());
        holder.itemcolor.setText(dummyValue.get(position).getItemcolor());
        holder.itemdgrade.setText(dummyValue.get(position).getItemgrade());
        holder.selectedquantity.setText(dummyValue.get(position).getItemquantity());
        holder.iconImage.setImageResource(dummyValue.get(position).getImageitem());
        holder.txtMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Button Clicked!",Toast.LENGTH_LONG).show();
            }
        });
        holder.txtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Button Clicked!",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dummyValue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textItemName, textItemQuan, itemPrice, itemdesc, itemquantity, itemdia, itemcolor, itemdgrade,
                selectedquantity,txtMinus,txtPlus;
        ImageView iconImage;
        EditText txtCount;

        public ViewHolder(View itemView) {
            super(itemView);
            textItemName = (TextView) itemView.findViewById(R.id.textItemName);
            textItemQuan = (TextView) itemView.findViewById(R.id.textItemQuan);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            itemdesc = (TextView) itemView.findViewById(R.id.itemdesc);
            itemquantity = (TextView) itemView.findViewById(R.id.itemquantity);
            itemdia = (TextView) itemView.findViewById(R.id.itemdia);
            itemcolor = (TextView) itemView.findViewById(R.id.itemcolor);
            itemdgrade = (TextView) itemView.findViewById(R.id.itemdgrade);
            selectedquantity = (TextView) itemView.findViewById(R.id.selectedquantity);
            iconImage = (ImageView) itemView.findViewById(R.id.iconImage);
            txtCount = (EditText) itemView.findViewById(R.id.txtCount);
            txtMinus=(TextView)itemView.findViewById(R.id.txtMinus);
            txtPlus=(TextView)itemView.findViewById(R.id.txtPlus);

        }
    }
}
