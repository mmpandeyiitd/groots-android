

/**
 * Created by aakash on 22/11/16.
 */

package groots.app.com.groots.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import groots.app.com.groots.R;
import groots.app.com.groots.databases.dbHelp;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.model.UpdateCartClass;

/**
 * Created by Administrator on 07-04-2016.
 */

public class Checkout_Update_Order_Adapter extends RecyclerView.Adapter<Checkout_Update_Order_Adapter.CartHolder>{


    ArrayList<UpdateCartClass>            cartClasses;
    Context                         context;
    int                             lastPosition=-1;
    android.os.Handler              handler=new android.os.Handler();
    Runnable                        runnable;
    UpdateCart                      updateCart;
    dbHelp dbHelp;
    Double previousCount;

    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.noimage)
            .showImageForEmptyUri(R.drawable.noimage)
            .showImageOnFail(R.drawable.noimage)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();


    public Checkout_Update_Order_Adapter(ArrayList<UpdateCartClass> cartClasses, Context context, UpdateCart updateCart) {
        this.cartClasses = cartClasses;
        this.context = context;
        this.updateCart = updateCart;
        dbHelp = new dbHelp(context);
        dbHelp.createDb(false);
        Collections.reverse(cartClasses);

    }


    @Override
    public Checkout_Update_Order_Adapter.CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkout_update_order_adapter, parent, false);
        CartHolder dataObjectHolder = new CartHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final Checkout_Update_Order_Adapter.CartHolder holder, final int position) {

        holder.textItemName.setText(cartClasses.get(position).product_name);
        holder.textItemdesc.setText(cartClasses.get(position).product_description);

        holder.textItemPrice.setText("" + cartClasses.get(position).unit_price+"/"+cartClasses.get(position).packSize+cartClasses.get(position).packUnit);
        holder.txtCount.setText("" + cartClasses.get(position).product_qty);
        // holder.imgItemIcon.setImageResource(cartClasses.get(position).getImageitem());
        if(!cartClasses.get(position).product_image.equals(null)) {
           /* Picasso.with(context).load(cartClasses.get(position).product_image)
                    .into(holder.imgItemIcon);*/
            imageLoader.displayImage(cartClasses.get(position).product_image,holder.imgItemIcon,options);
        }


        if(cartClasses.get(position).product_qty * Float.parseFloat(cartClasses.get(position).packSize)>=1000) {
            holder.selectedquan.setText(cartClasses.get(position).product_qty * Float.parseFloat(cartClasses.get(position).packSize)/1000.00+"K"+
                    cartClasses.get(position).packUnit);
        }else
        {

            holder.selectedquan.setText(String.valueOf(cartClasses.get(position).product_qty * Float.parseFloat(cartClasses.get(position).packSize)+cartClasses.get(position).packUnit));
        }

      /*  if (position > lastPosition) {
            holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
            lastPosition = position;
        }*/
        holder.imagecross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makeDialog(position);
                notifyDataSetChanged();
            }
        });


        if (cartClasses.get(position).product_qty > 0) {
            holder.txtCount.setText("" + cartClasses.get(position).product_qty);
            dbHelp.updateProductQty(cartClasses.get(position).product_qty, cartClasses.get(position).unit_price, cartClasses.get(position).subscribe_prod_id);
            updateCart.updateCart();
        } else {
            holder.txtCount.setText("0");
            updateCart.updateCart();
        }

        holder.txtPlus.setTag(position);
        holder.txtMinus.setTag(position);
        holder.txtCount.setTag(position);

        holder.txtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cartClasses.get(position).product_qty >= 999) {
                    Toast.makeText(context, "Sorry, you can't add more these item.", Toast.LENGTH_SHORT).show();
                } else {
                    int clickedPos = (int) view.getTag();
                    Double previousCount = cartClasses.get(clickedPos).product_qty;

                    previousCount++;

                    cartClasses.get(clickedPos).product_qty = previousCount;

                    if(previousCount * Integer.parseInt(cartClasses.get(position).packSize)>=1000) {
                        holder.selectedquan.setText( previousCount * Float.parseFloat(cartClasses.get(position).packSize)/1000+"K"+cartClasses.get(position).packUnit);
                    }else
                    {
                        holder.selectedquan.setText( previousCount * Float.parseFloat(cartClasses.get(position).packSize)+cartClasses.get(position).packUnit);

                    }

                   /* HashMap hash = new HashMap();
                    hash.put("what","update");*/

                    dbHelp.insertUpdateCartData(cartClasses.get(position).subscribe_prod_id,
                            cartClasses.get(position).base_product_id,
                            cartClasses.get(position).store_id,
                            cartClasses.get(position).toString(),
                            cartClasses.get(position).product_description,
                            "abcde", cartClasses.get(position).product_qty,
                            cartClasses.get(position).unit_price,cartClasses.get(position).packSize,cartClasses.get(position).packUnit);

                    notifyDataSetChanged();

                }
            }
        });

        holder.txtMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPos = (int) view.getTag();
                Double previousCount = cartClasses.get(clickedPos).product_qty;

                if(previousCount * Double.parseDouble(cartClasses.get(position).packSize)>=1000) {
                    holder.selectedquan.setText( previousCount * Float.parseFloat(cartClasses.get(position).packSize)/1000+"K"+
                            cartClasses.get(position).packUnit);
                }else
                {
                    holder.selectedquan.setText( previousCount * Float.parseFloat(cartClasses.get(position).packSize)+cartClasses.get(position).packUnit);
                }
                previousCount = cartClasses.get(clickedPos).product_qty;
                if (previousCount > 0) {
                    previousCount--;
                    cartClasses.get(clickedPos).product_qty = previousCount;


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

        TextView textItemName,selectedquan;
        TextView textItemdesc;
        TextView textItemPrice;
        ImageView imgItemIcon;
        TextView txtCount;
        ImageButton txtMinus, txtPlus;
        LinearLayout imagecross;
        public CartHolder(View itemView) {
            super(itemView);
            textItemName = (TextView) itemView.findViewById(R.id.textItemName);
            textItemdesc = (TextView) itemView.findViewById(R.id.textItemdesc);
            textItemPrice = (TextView) itemView.findViewById(R.id.textItemPrice);
            imgItemIcon = (ImageView) itemView.findViewById(R.id.imgItemIcon);
            txtCount = (TextView) itemView.findViewById(R.id.txtCount);
            imagecross = (LinearLayout)itemView.findViewById(R.id.imagecross);
            txtMinus = (ImageButton) itemView.findViewById(R.id.txtMinus);
            txtPlus = (ImageButton) itemView.findViewById(R.id.txtPlus);
            selectedquan=(TextView)itemView.findViewById(R.id.selectedquan);

        }
    }

    private void makeDialog(final int position) {
        notifyItemRemoved(position);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to remove this item ?");
        alertDialogBuilder.setCancelable(false);
        // notifyDataSetChanged();
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dbHelp.deleteRecords(cartClasses.get(position).subscribe_prod_id, cartClasses.get(position).base_product_id);
                cartClasses.remove(position);
                updateCart.updateCart();
                notifyItemRemoved(position);


            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (cartClasses.get(position).product_qty == 0) {
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
                handler.postDelayed(runnable, 1500);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
