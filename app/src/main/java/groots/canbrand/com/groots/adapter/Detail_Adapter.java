package groots.canbrand.com.groots.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import groots.canbrand.com.groots.model.LandingInfo;
import groots.canbrand.com.groots.R;

/**
 * Created by Administrator on 04-04-2016.
 */
public class Detail_Adapter extends RecyclerView.Adapter<Detail_Adapter
        .ViewHolder> {


    ArrayList<LandingInfo> dummyValue;
    Context context;
    int lastPosition = -1;

    public Detail_Adapter(ArrayList<LandingInfo> dummyValue, Context context) {
        this.context = context;
        this.dummyValue = dummyValue;
    }

    @Override
    public Detail_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.detail_adapter_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(dummyValue.get(position).getItemName()!=null) {
            holder.textItemName.setText(dummyValue.get(position).getItemName());
        }
        else
            holder.textItemName.setVisibility(View.INVISIBLE);
        if(dummyValue.get(position).getItemquantity()!=null)
        holder.textItemQuan.setText(dummyValue.get(position).getItemquantity());
        else
            holder.textItemQuan.setVisibility(View.INVISIBLE);
        if(dummyValue.get(position).getItemprice()!=null)
        holder.itemPrice.setText(dummyValue.get(position).getItemprice());
        else
            holder.itemPrice.setVisibility(View.INVISIBLE);
        if(dummyValue.get(position).getItemDesc()!=null)
        holder.itemdesc.setText(dummyValue.get(position).getItemDesc());
        else
            holder.itemdesc.setVisibility(View.INVISIBLE);
        if(dummyValue.get(position).getItemquantity()!=null)
        holder.itemquantity.setText(dummyValue.get(position).getItemquantity());
        else
            holder.itemquantity.setVisibility(View.INVISIBLE);
        if (dummyValue.get(position).getItemdia()!=null)
        holder.itemdia.setText(dummyValue.get(position).getItemdia());
        else
            holder.itemdia.setVisibility(View.INVISIBLE);
        if(dummyValue.get(position).getItemcolor()!=null)
        holder.itemcolor.setText(dummyValue.get(position).getItemcolor());
        else
            holder.itemcolor.setVisibility(View.INVISIBLE);
        if(dummyValue.get(position).getItemgrade()!=null)
        holder.itemdgrade.setText(dummyValue.get(position).getItemgrade());
        else
            holder.itemdgrade.setVisibility(View.INVISIBLE);
        if(dummyValue.get(position).getItemquantity()!=null)
        holder.selectedquantity.setText(dummyValue.get(position).getItemquantity());
        else
            holder.selectedquantity.setVisibility(View.INVISIBLE);
        if(dummyValue.get(position).getImageitem()!=0)
        holder.iconImage.setImageResource(dummyValue.get(position).getImageitem());
        else
            holder.iconImage.setVisibility(View.INVISIBLE);

     /* if (position > lastPosition) {
            holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
            lastPosition = position;
        }*/


    }

    @Override
    public int getItemCount() {
        return dummyValue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textItemName, textItemQuan, itemPrice, itemdesc, itemquantity, itemdia, itemcolor, itemdgrade,
                selectedquantity;
        ImageView iconImage, txtMinus, txtPlus;
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
            txtMinus = (ImageView) itemView.findViewById(R.id.txtMinus);
            txtPlus = (ImageView) itemView.findViewById(R.id.txtPlus);

        }
    }
}
