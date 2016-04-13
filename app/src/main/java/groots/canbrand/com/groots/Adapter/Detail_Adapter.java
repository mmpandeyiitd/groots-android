package groots.canbrand.com.groots.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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
        .ViewHolderDetail> {


    ArrayList<LandingInfo> dummyValue;
    Context context;
    int lastPosition = -1;

    public Detail_Adapter(ArrayList<LandingInfo> dummyValue, Context context) {
        this.context = context;
        this.dummyValue = dummyValue;
    }

    @Override
    public Detail_Adapter.ViewHolderDetail onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.detail_adapter_layout, parent, false);
        ViewHolderDetail holder = new ViewHolderDetail(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Detail_Adapter.ViewHolderDetail holder, int position) {

            holder.textItemName.setText(dummyValue.get(position).getItemName());
       /* holder.textItemQuan.setText(dummyValue.get(position).getItemquantity());
        else
            holder.textItemQuan.setVisibility(View.INVISIBLE);*/

        holder.itemPrice.setText(dummyValue.get(position).getItemprice());
        holder.itemdesc.setText(dummyValue.get(position).getItemDesc());
        holder.itemquantity.setText(dummyValue.get(position).getItemquantity());
        holder.itemdia.setText(dummyValue.get(position).getItemdia());
        holder.itemcolor.setText(dummyValue.get(position).getItemcolor());
        holder.itemdgrade.setText(dummyValue.get(position).getItemgrade());
        holder.selectedquantity.setText(dummyValue.get(position).getItemquantity());
        holder.iconImage.setImageResource(dummyValue.get(position).getImageitem());

     /* if (position > lastPosition) {
            holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
            lastPosition = position;
        }*/


    }

    @Override
    public int getItemCount() {
        return dummyValue.size();
    }

    public class ViewHolderDetail extends RecyclerView.ViewHolder {
        TextView textItemName, textItemQuan, itemPrice, itemdesc, itemquantity, itemdia, itemcolor, itemdgrade,
                selectedquantity;
        ImageView iconImage, txtMinus, txtPlus;
        EditText txtCount;

        public ViewHolderDetail(View itemView) {
            super(itemView);

            textItemName = (TextView) itemView.findViewById(R.id.textItemName);
          //  textItemQuan = (TextView) itemView.findViewById(R.id.textItemQuan);
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
