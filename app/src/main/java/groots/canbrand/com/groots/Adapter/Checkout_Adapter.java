package groots.canbrand.com.groots.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import groots.canbrand.com.groots.Model.LandingInfo;
import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.ui.Checkout_Ui;

/**
 * Created by Administrator on 07-04-2016.
 */
public class Checkout_Adapter extends RecyclerView.Adapter<Checkout_Adapter
        .CartHolder>{

    ArrayList<LandingInfo> dummyValue;
    Context context;
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

        holder.textNameCart.setText(dummyValue.get(position).getItemName());
        holder.textPriceCart.setText(dummyValue.get(position).getItemprice());
        holder.imgIconCart.setImageResource(dummyValue.get(position).getImageitem());

    }

    @Override
    public int getItemCount() {
        return dummyValue.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        ImageView imgIconCart,imagecross;
        TextView textNameCart,textPriceCart;
        public CartHolder(View itemView) {
            super(itemView);
            imagecross=(ImageView)itemView.findViewById(R.id.imagecross);
            imgIconCart=(ImageView)itemView.findViewById(R.id.imgIconCart);
            textNameCart=(TextView)itemView.findViewById(R.id.textNameCart);
            textPriceCart=(TextView)itemView.findViewById(R.id.textPriceCart);


        }
    }
}
