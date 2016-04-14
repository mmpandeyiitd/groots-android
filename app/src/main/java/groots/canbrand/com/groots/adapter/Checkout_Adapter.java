package groots.canbrand.com.groots.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.pojo.LandingInfo;

/**
 * Created by Administrator on 07-04-2016.
 */
public class Checkout_Adapter extends RecyclerView.Adapter<Checkout_Adapter
        .CartHolder>{

    ArrayList<LandingInfo> dummyValue;
    Context context;
    int lastPosition=-1;
    public Checkout_Adapter(ArrayList<LandingInfo> dummyValue, Context context) {
        this.dummyValue=dummyValue;
        this.context=context;
    }

    @Override
    public Checkout_Adapter.CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.checkout_adapter, parent, false);

        CartHolder dataObjectHolder = new CartHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(Checkout_Adapter.CartHolder holder, int position) {

        holder.textItemName.setText(dummyValue.get(position).getItemName());
        holder.textItemdesc.setText(dummyValue.get(position).getItemDesc());
        holder.textItemPrice.setText(dummyValue.get(position).getItemprice());
        holder.txtCount.setText(dummyValue.get(position).getItemcount());
        holder.imgItemIcon.setImageResource(dummyValue.get(position).getImageitem());

        if (position > lastPosition) {
            holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return dummyValue.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {

        TextView textItemName;
        TextView textItemdesc;
        TextView textItemPrice;
        ImageView imgItemIcon;
        TextView txtCount;

        public CartHolder(View itemView) {
            super(itemView);
            textItemName = (TextView) itemView.findViewById(R.id.textItemName);
            textItemdesc = (TextView) itemView.findViewById(R.id.textItemdesc);
            textItemPrice=(TextView)itemView.findViewById(R.id.textItemPrice);
            imgItemIcon=(ImageView)itemView.findViewById(R.id.imgItemIcon);
            txtCount=(TextView)itemView.findViewById(R.id.txtCount);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
    }
}
