package groots.app.com.groots.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.R;
import groots.app.com.groots.model.CartClass;
import groots.app.com.groots.pojo.Order;
import groots.app.com.groots.pojo.OrderItem;
import groots.app.com.groots.pojo.Product;
import groots.app.com.groots.ui.historyList;


/**
 * Created by aakash on 28/10/16.
 */

public class historyList_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Order order;
    List<OrderItem> orderItems;
    Product item;

    public Map<Integer,Double> map = new HashMap<Integer,Double>() ;
    //ArrayList<Product> productListDat;

    Context context;
    View view;
    int lastPosition = -1;
    //var dlvr;
    UpdateCart updateCart;

    DbHelper dbHelper;
    ArrayList<CartClass> cartClasses;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOTER = 2;
    //public static final int TYPE_UPDATE = 3;
    boolean show_footer;

    public historyList_Adapter(ArrayList<Order> orders, Context context, UpdateCart updateCart, boolean f) {
        this.order = orders.get(0);
        this.orderItems = this.order.orderItems;
        //this.orderItems = orderItems;
        this.context = context;
        this.updateCart = updateCart;
        dbHelper = new DbHelper(context);
        dbHelper.createDb(false);
        cartClasses = dbHelper.order();
        this.show_footer = f;
        //this.count=i;
        // notifyDataSetChanged();
    }


    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView tvloadmore;
        ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            this.tvloadmore = (TextView) itemView.findViewById(R.id.tvloadmore);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }

        public void clearAnimation() {
            view.clearAnimation();
        }
    }


    /*class UpdateHolder extends RecyclerView.ViewHolder {
        TextView total_amount;
        TextView updateicon_checkout;

        public UpdateHolder(View itemview){
            super(itemview);
            this.total_amount = (TextView) itemview.findViewById(R.id.total_amount);
            updateicon_checkout = (TextView) itemview.findViewById(R.id.updateicon_checkout);

        }
    }*/

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView textItemNam;
        TextView orderedQty;
        TextView deliveredQty;
        //TextView textItemdes;
        TextView textItemPri;
        ImageView imgItemIc;
        //EditText txtCount;
        //ImageButton txtPlus, txtMinus;
        // LinearLayout imagecross;

        public DataObjectHolder(final View itemView) {
            super(itemView);
            textItemNam = (TextView) itemView.findViewById(R.id.textItemNam);
            //textItemdes = (TextView) itemView.findViewById(R.id.textItemdes);
            orderedQty = (TextView) itemView.findViewById(R.id.selectedquan);
            textItemPri = (TextView) itemView.findViewById(R.id.textItemPri);
            imgItemIc = (ImageView) itemView.findViewById(R.id.imgItemIc);
            deliveredQty = (TextView) itemView.findViewById(R.id.deliveredquan);
            //imagecross = (LinearLayout)itemView.findViewById(R.id.imagecross);
            // txtCount = (EditText) itemView.findViewById(R.id.txtCount);
            //txtMinus = (ImageButton) itemView.findViewById(R.id.txtMinus);
            //txtPlus = (ImageButton) itemView.findViewById(R.id.txtPlus);
        }

        public void clearAnimation() {
            view.clearAnimation();
        }

    }

/*
    private void makeDialog(final int position) {
        notifyItemRemoved(position);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to remove this item ?");
        alertDialogBuilder.setCancelable(false);
        // notifyDataSetChanged();
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dbHelper.deleteRecords(cartClasses.get(position).subscribe_prod_id, cartClasses.get(position).base_product_id);
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
    }*/




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.history_list_card_view, parent, false);
            DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
            return dataObjectHolder;
        }
        if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pregress_bar, parent, false);
            return new FooterViewHolder(v);
        }
        /*if (viewType == TYPE_UPDATE){
            view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_history_list, parent, false);
            UpdateHolder updateHolder = new UpdateHolder(view);
            return updateHolder;

        }*/

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mholder, final int position) {

        if (mholder instanceof DataObjectHolder) {
            final DataObjectHolder holder = (DataObjectHolder) mholder;
            final DbHelper dbHelper = new DbHelper(context);
            dbHelper.createDb(false);
             Double dlvr = orderItems.get(position).deliveredQty;

            holder.textItemNam.setText(orderItems.get(position).product.title);
            holder.textItemPri.setText( orderItems.get(position).itemOrderPrice.toString() );
            holder.orderedQty.setText(orderItems.get(position).productQty.toString() + orderItems.get(position).product.packUnit);
            if(dlvr != null){
                holder.deliveredQty.setText(orderItems.get(position).deliveredQty.toString()+ orderItems.get(position).product.packUnit);
            }
            else
            holder.deliveredQty.setText("null");









           //Integer a =

           //Double b = orderItems.get(position).productQty;


            map.put(orderItems.get(position).subscribedProductId , orderItems.get(position).productQty );






            //holder.textItemdesc.setText(orderItems.get(position).description);
            /*int pack_size = orderItems.get(position).packSize;
            if (pack_size <= 1) {
                holder.textItemPrice.setText("" + orderItems.get(position).storeOfferPrice + "/" +pack_size+ orderItems.get(position).packUnit);
            } else {
                holder.textItemPrice.setText("" + orderItems.get(position).storeOfferPrice + "/" + pack_size + orderItems.get(position).packUnit);
            }

            holder.txtCount.setText("" + orderItems.get(position).getItemCount());*/

            if (!orderItems.get(position).product.thumbUrl.equals(null)) {
           /* Picasso.with(context).load(orderItems.get(position).thumbUrl.get(0))
                    .into(holder.imgItemIcon);*/


                ImageLoader imageLoader = ImageLoader.getInstance();

                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.noimage)
                        .showImageOnFail(R.drawable.noimage)
                    /*.showImageOnLoading(R.drawable.map_placeholder)
                    .showImageForEmptyUri(R.drawable.map_placeholder)
                    .showImageOnFail(R.drawable.map_placeholder)*/
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .considerExifParams(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();
                imageLoader.displayImage(orderItems.get(position).product.thumbUrl.get(0), holder.imgItemIc, options);
            }


      /*  Picasso.with(context).load(orderItems.get(position).defaultThumbUrl)
                .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.imgItemIcon);

        Picasso.with(context).load(orderItems.get(position).)) .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image).into(childViewHolder.imgItemIcon);
*/
          /*  if (position > lastPosition) {
                holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                lastPosition = position;
            }*/

            //  holder.itemView.clearAnimation();

 /*           if (orderItems.get(position).getItemCount() > 0) {
                holder.txtCount.setText("" + orderItems.get(position).getItemCount());
                dbHelper.updateProductQty(orderItems.get(position).getItemCount(), orderItems.get(position).storeOfferPrice, orderItems.get(position).subscribedProductId);
                updateCart.updateCart();
            } else {
                if (!cartClasses.contains(orderItems.get(position))) {
                    holder.txtCount.setText("0");
                    updateCart.updateCart();
                }

            }


            holder.txtPlus.setTag(position);
            holder.txtMinus.setTag(position);
            holder.txtCount.setTag(position);


            holder.txtPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Integer.parseInt(holder.txtCount.getText().toString().trim()) >= 999) {
                        Toast.makeText(context, "Sorry, you can't add more these item.", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Utilz.count = position;
                        int clickedPos = (int) view.getTag();
                        int previousCount = orderItems.get(clickedPos).getItemCount();

                        previousCount++;

                        orderItems.get(clickedPos).setItemCount(previousCount);


                        dbHelper.insertCartData(orderItems.get(position).subscribedProductId, orderItems.get(position).baseProductId,
                                orderItems.get(position).storeId, orderItems.get(position).title,
                                orderItems.get(position).description,
                                orderItems.get(position).thumbUrl.get(0), orderItems.get(position).getItemCount(),
                                orderItems.get(position).storeOfferPrice,orderItems.get(position).packSize.toString(),orderItems.get(position).packUnit);

                        notifyDataSetChanged();

                    }
                }
            });

            holder.txtMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Utilz.count = position;
                    int clickedPos = (int) view.getTag();
                    int previousCount = orderItems.get(clickedPos).getItemCount();
                    if (previousCount > 0) {
                        previousCount--;

                        orderItems.get(clickedPos).setItemCount(previousCount);
                        if (previousCount == 0)
                            dbHelper.deleteRecords(orderItems.get(position).subscribedProductId, orderItems.get(position).baseProductId);
//                        else if(previousCount>0)
//                        dbHelper.updateProductQty(orderItems.get(position).getItemCount(), orderItems.get(position).subscribedProductId);
                    }
                    notifyDataSetChanged();
                }
            });


            holder.txtCount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        if (holder.txtCount.getText().toString().trim().length() == 0) {
                            holder.txtCount.setText("0");
                        }
                        int clickedPos = (int) v.getTag();
                        int previousCount = Integer.parseInt(holder.txtCount.getText().toString().trim());

                        orderItems.get(clickedPos).setItemCount(previousCount);
                        dbHelper.insertCartData(orderItems.get(position).subscribedProductId, orderItems.get(position).baseProductId,
                                orderItems.get(position).storeId, orderItems.get(position).title,
                                orderItems.get(position).description,
                                orderItems.get(position).thumbUrl.get(0), orderItems.get(position).getItemCount(),
                                orderItems.get(position).storeOfferPrice, orderItems.get(position).packSize.toString(),orderItems.get(position).packUnit);

                        if (previousCount == 0)
                            dbHelper.deleteRecords(orderItems.get(position).subscribedProductId, orderItems.get(position).baseProductId);

                        holder.txtCount.setText(orderItems.get(position).getItemCount() + "");
                        notifyDataSetChanged();
                        return false;
                    }
                    return false;
                }
            });
  */
        } else if (mholder instanceof FooterViewHolder) {
            final FooterViewHolder footerHolder = (FooterViewHolder) mholder;

            if (((historyList) context).loadingMore == false) {
                footerHolder.tvloadmore.setVisibility(View.VISIBLE);
                footerHolder.progressBar.setVisibility(View.GONE);
            }





            /*footerHolder.progressBar.setVisibility(View.VISIBLE);
            try {
                Thread.sleep(5000);
                footerHolder.progressBar.setVisibility(View.INVISIBLE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
          /*  Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    footerHolder.progressBar.setVisibility(View.INVISIBLE);
                    show_footer=false;
                    notifyDataSetChanged();

                }
            };
            new android.os.Handler().postDelayed(runnable, 900);
*/

        }
       /* final DataObjectHolder holder = (DataObjectHolder) mholder;

        holder.imagecross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makeDialog(position);
                notifyDataSetChanged();
            }
        });*/





    }



    /*public void setView (RecyclerView.ViewHolder mholder, final int position) {

        if (mholder instanceof UpdateHolder) {
            final UpdateHolder holder = (UpdateHolder) mholder;
            final DbHelper dbHelper = new DbHelper(context);
            dbHelper.createDb(false);

            holder.total_amount.setText(orderList.get(position).totalPayableAmount.toString());


        }

    }*/
    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (show_footer) {
           /* ((FooterViewHolder) holder).clearAnimation();
            holder.itemView.clearAnimation();*/
        } else {
          /*  ((DataObjectHolder) holder).clearAnimation();
            holder.itemView.clearAnimation();*/
        }
    }

    @Override
    public int getItemCount() {

        if (show_footer) {
            return orderItems.size() + 1; //+1 is for the footer as it's an extra item
        } else {
            return orderItems.size();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (show_footer) {
            if (isPositionFooter(position)) {

                return TYPE_FOOTER;
            }

        } else {
            return TYPE_ITEM;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position) {
        //if position == arr_userid.size then we need to show footerView otherwise we'll get indexOutOfBoundsException
        return position == orderItems.size();
    }

    public void hideFooter() {
        show_footer = false;
    }

    public void setShow_footer() {
        show_footer = true;

    }

    public void setMap(HashMap<Integer, Double> map){
        this.map = map;
    }

    public Map<Integer, Double> getMap(){
        return this.map;
    }



}

