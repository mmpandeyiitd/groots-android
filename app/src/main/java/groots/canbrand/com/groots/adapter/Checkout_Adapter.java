package groots.canbrand.com.groots.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.databases.DbHelper;
import groots.canbrand.com.groots.interfaces.UpdateCart;
import groots.canbrand.com.groots.model.CartClass;
import groots.canbrand.com.groots.pojo.LandingInfo;

/**
 * Created by Administrator on 07-04-2016.
 */
public class Checkout_Adapter extends RecyclerView.Adapter<Checkout_Adapter
        .CartHolder> {

    ArrayList<CartClass> cartClasses;
    Context context;
    int lastPosition = -1;
    android.os.Handler handler = new android.os.Handler();
    Runnable runnable;
    DbHelper dbHelper;
    UpdateCart updateCart;

    public Checkout_Adapter(ArrayList<CartClass> cartClasses, Context context, UpdateCart updateCart) {
        this.cartClasses = cartClasses;
        this.context = context;
        dbHelper = new DbHelper(context);
        this.updateCart= this.updateCart;
    }

    @Override
    public Checkout_Adapter.CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkout_adapter, parent, false);

        CartHolder dataObjectHolder = new CartHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final Checkout_Adapter.CartHolder holder, final int position) {

        holder.textItemName.setText(cartClasses.get(position).product_name);
        holder.textItemdesc.setText(cartClasses.get(position).product_description);
        holder.textItemPrice.setText("" + cartClasses.get(position).total_unit_price);
        holder.txtCount.setText("" + cartClasses.get(position).product_qty);


        // holder.imgItemIcon.setImageResource(cartClasses.get(position).getImageitem());

        if (position > lastPosition) {
            holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
            lastPosition = position;
        }
        holder.imagecross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                makeDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartClasses.size();

    }

    public class CartHolder extends RecyclerView.ViewHolder {

        TextView textItemName;
        TextView textItemdesc;
        TextView textItemPrice;
        ImageView imgItemIcon;
        TextView txtCount;
        ImageView imagecross;

        public CartHolder(View itemView) {
            super(itemView);
            textItemName = (TextView) itemView.findViewById(R.id.textItemName);
            textItemdesc = (TextView) itemView.findViewById(R.id.textItemdesc);
            textItemPrice = (TextView) itemView.findViewById(R.id.textItemPrice);
            imgItemIcon = (ImageView) itemView.findViewById(R.id.imgItemIcon);
            txtCount = (TextView) itemView.findViewById(R.id.txtCount);
            imagecross = (ImageView) itemView.findViewById(R.id.imagecross);

        }
    }

    private void makeDialog(final int position) {
        notifyItemRemoved(position);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to remove this item ?");
        // notifyDataSetChanged();
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                notifyItemRemoved(position);

            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                notifyDataSetChanged();
            }
        });

        alertDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                };
                handler.postDelayed(runnable, 1500);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
