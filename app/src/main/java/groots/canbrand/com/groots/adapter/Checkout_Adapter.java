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
public class Checkout_Adapter extends RecyclerView.Adapter<Checkout_Adapter.CartHolder>{


    ArrayList<CartClass>            cartClasses;
    Context                         context;
    int                             lastPosition=-1;
    android.os.Handler              handler=new android.os.Handler();
    Runnable                        runnable;
    UpdateCart                      updateCart;
    DbHelper                        dbHelper;
    int previousCount;



    public Checkout_Adapter(ArrayList<CartClass> cartClasses, Context context, UpdateCart updateCart) {
        this.cartClasses=cartClasses;
        this.context=context;
        this.updateCart=updateCart;
        dbHelper=new DbHelper(context);
        dbHelper.createDb(false);
    }

    @Override
    public Checkout_Adapter.CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkout_adapter, parent, false);
        CartHolder dataObjectHolder = new CartHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(Checkout_Adapter.CartHolder holder, final int position) {

        holder.textItemName.setText(cartClasses.get(position).product_name);
        holder.textItemdesc.setText(cartClasses.get(position).product_description);
        holder.textItemPrice.setText(""+cartClasses.get(position).unit_price);
        holder.txtCount.setText(""+cartClasses.get(position).product_qty);
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

        if (cartClasses.get(position).product_qty > 0) {
            holder.txtCount.setText("" + cartClasses.get(position).product_qty);
            dbHelper.updateProductQty(cartClasses.get(position).product_qty,cartClasses.get(position).unit_price, cartClasses.get(position).subscribe_prod_id);
            updateCart.updateCart();
        }else {
            holder.txtCount.setText("0");
            updateCart.updateCart();
        }

        holder.txtPlus.setTag(position);
        holder.txtMinus.setTag(position);
        holder.txtCount.setTag(position);

        holder.txtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int clickedPos = (int) view.getTag();
                int previousCount = cartClasses.get(clickedPos).product_qty;

                previousCount++;

                cartClasses.get(clickedPos).product_qty=previousCount;

                dbHelper.insertCartData(cartClasses.get(position).subscribe_prod_id,
                        cartClasses.get(position).base_product_id,
                        cartClasses.get(position).store_id,
                        cartClasses.get(position).toString(),
                        cartClasses.get(position).product_description,
                        "abcde",cartClasses.get(position).product_qty,
                        cartClasses.get(position).unit_price);

                notifyDataSetChanged();

            }
        });

        holder.txtMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPos = (int) view.getTag();
                previousCount = cartClasses.get(clickedPos).product_qty;
                if (previousCount > 0) {
                    previousCount--;
                    cartClasses.get(clickedPos).product_qty=previousCount;


                    if (previousCount == 0) {
                        makeDialog(clickedPos);
                    }

                }
                notifyDataSetChanged();
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
        ImageView imagecross,txtMinus, txtPlus;

        public CartHolder(View itemView) {
            super(itemView);
            textItemName = (TextView) itemView.findViewById(R.id.textItemName);
            textItemdesc = (TextView) itemView.findViewById(R.id.textItemdesc);
            textItemPrice=(TextView)itemView.findViewById(R.id.textItemPrice);
            imgItemIcon=(ImageView)itemView.findViewById(R.id.imgItemIcon);
            txtCount=(TextView)itemView.findViewById(R.id.txtCount);
            imagecross=(ImageView)itemView.findViewById(R.id.imagecross);
            txtMinus=(ImageView)itemView.findViewById(R.id.txtMinus);
            txtPlus=(ImageView)itemView.findViewById(R.id.txtPlus);
        }
    }

    private void makeDialog(final int position) {
        notifyItemRemoved(position);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");
       // notifyDataSetChanged();
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                    Log.e("Item in array",String.valueOf(position));
                dbHelper.deleteRecords(cartClasses.get(position).subscribe_prod_id, cartClasses.get(position).base_product_id);
                cartClasses.remove(position);
                notifyItemRemoved(position);


            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(cartClasses.get(position).product_qty==0) {
                    previousCount++;
                    cartClasses.get(position).product_qty = previousCount;
                }
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
                handler.postDelayed(runnable,1500);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
