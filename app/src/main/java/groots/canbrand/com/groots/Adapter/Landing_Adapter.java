package groots.canbrand.com.groots.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import groots.canbrand.com.groots.Landing_UI;
import groots.canbrand.com.groots.Model.LandingInfo;
import groots.canbrand.com.groots.R;

/**
 * Created by Administrator on 04-04-2016.
 */
public class Landing_Adapter extends RecyclerView.Adapter<Landing_Adapter
        .DataObjectHolder> {

    ArrayList<LandingInfo> dummyValue;
    Context context;

    public Landing_Adapter(ArrayList<LandingInfo> dummyValue, Context context) {
        this.dummyValue=dummyValue;
        this.context=context;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView textItemName;
        TextView textItemdesc;
        TextView textItemPrice;
        ImageView imgItemIcon;
        TextView txtCount;

        public DataObjectHolder(View itemView) {
            super(itemView);
            textItemName = (TextView) itemView.findViewById(R.id.textItemName);
            textItemdesc = (TextView) itemView.findViewById(R.id.textItemdesc);
            textItemPrice=(TextView)itemView.findViewById(R.id.textItemPrice);
            imgItemIcon=(ImageView)itemView.findViewById(R.id.imgItemIcon);
            txtCount=(TextView)itemView.findViewById(R.id.txtCount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
    @Override
    public Landing_Adapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.landing_card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(Landing_Adapter.DataObjectHolder holder, int position) {
        holder.textItemName.setText(dummyValue.get(position).getItemName());
        holder.textItemdesc.setText(dummyValue.get(position).getItemDesc());
        holder.textItemPrice.setText(dummyValue.get(position).getItemprice());
        holder.txtCount.setText(dummyValue.get(position).getItemcount());
       holder.imgItemIcon.setImageResource(dummyValue.get(position).getImageitem());

    }

    @Override
    public int getItemCount() {
        return dummyValue.size();
    }


    }
