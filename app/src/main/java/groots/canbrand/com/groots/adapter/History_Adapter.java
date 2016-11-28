package groots.canbrand.com.groots.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import groots.canbrand.com.groots.databases.DbHelper;
import groots.canbrand.com.groots.interfaces.UpdateCart;
import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.model.CartClass;
import groots.canbrand.com.groots.pojo.Order;
import groots.canbrand.com.groots.pojo.Product;
import groots.canbrand.com.groots.ui.History;
import groots.canbrand.com.groots.ui.Landing_Update;
import groots.canbrand.com.groots.ui.historyList;
import groots.canbrand.com.groots.utilz.Utilz;
/**
 * Created by aakash on 3/11/16.
 */

public class History_Adapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>  {





    ArrayList<Order> historyListData;
    Context context;
    View view;
    int lastPosition = -1;
    UpdateCart updateCart;
    DbHelper dbHelper;
    ArrayList<CartClass> cartClasses;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOTER = 2;
    boolean show_footer;


    public History_Adapter(ArrayList<Order> historyListData, Context context, UpdateCart updateCart, boolean f) {
        this.historyListData = historyListData;
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


    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView date_id;
        TextView textRupee;
        TextView textItemPric;
        TextView status;
       // TextView o_i ;
        //ImageView imgItemIcon;
        //EditText txtCount;
        //ImageButton forward;

        public DataObjectHolder(final View itemView) {
            super(itemView);
            date_id = (TextView) itemView.findViewById(R.id.date_id);
            textRupee = (TextView) itemView.findViewById(R.id.textRupee);
            textItemPric = (TextView) itemView.findViewById(R.id.textItemPric);
            status = (TextView) itemView.findViewById(R.id.stat);
           // o_i = (TextView) itemView.findViewById(R.id.o_i);
            //imgItemIcon = (ImageView) itemView.findViewById(R.id.imgItemIcon);
            //txtCount = (EditText) itemView.findViewById(R.id.txtCount);
            //forward = (ImageButton) itemView.findViewById(R.id.forward_image);
            //txtPlus = (ImageButton) itemView.findViewById(R.id.txtPlus);
        }

        public void clearAnimation() {
            view.clearAnimation();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.history_card_view, parent, false);
            History_Adapter.DataObjectHolder dataObjectHolder = new History_Adapter.DataObjectHolder(view);
            return dataObjectHolder;
        }
        if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pregress_bar, parent, false);
            return new FooterViewHolder(v);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mholder, final int position) {
        if (mholder instanceof DataObjectHolder) {
            final DataObjectHolder holder = (DataObjectHolder) mholder;
            final DbHelper dbHelper = new DbHelper(context);
            dbHelper.createDb(false);
            //int i=0;

            holder.date_id.setText(historyListData.get(position).deliveryDate.substring(0,11));
            holder.textItemPric.setText(historyListData.get(position).totalPayableAmount.toString());
            holder.status.setText(historyListData.get(position).Status);
           //String o_I = holder.o_I.getText(historyListData.get(text).orderId);

            //holder.textItemdesc.setText(orderItems.get(position).description);
            //int pack_size = orderItems.get(position).packSize;
            //if (pack_size <= 1) {
            //  holder.textItemPrice.setText("" + orderItems.get(position).storeOfferPrice + "/" +pack_size+ orderItems.get(position).packUnit);
            //} else {
            //  holder.textItemPrice.setText("" + orderItems.get(position).storeOfferPrice + "/" + pack_size + orderItems.get(position).packUnit);
            //}

            //holder.txtCount.setText("" + orderItems.get(position).getItemCount());

            //if (!orderItems.get(position).thumbUrl.equals(null)) {
           /* Picasso.with(context).load(orderItems.get(position).thumbUrl.get(0))
                    .into(holder.imgItemIcon);*/


            // ImageLoader imageLoader = ImageLoader.getInstance();

            //DisplayImageOptions options = new DisplayImageOptions.Builder()
            //      .showImageOnLoading(R.drawable.noimage)
            //    .showImageOnFail(R.drawable.noimage)
                    /*.showImageOnLoading(R.drawable.map_placeholder)
                    .showImageForEmptyUri(R.drawable.map_placeholder)
                    .showImageOnFail(R.drawable.map_placeholder)*/
            //  .cacheInMemory(true)
            // .cacheOnDisk(true)
            //.considerExifParams(true)
            //.bitmapConfig(Bitmap.Config.RGB_565)
            //.build();
            //imageLoader.displayImage(orderItems.get(position).thumbUrl.get(0), holder.imgItemIcon, options);
            //}

            //holder.forward.setTag(position);




            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //String o_I = historyListData.get(position).orderId;


                    String or_id = historyListData.get(position).orderId.toString();
                    String total = holder.textItemPric.getText().toString();
                    String datee = historyListData.get(position).deliveryDate.substring(0,10);
                    String Stat = historyListData.get(position).Status;
                        Intent intent = new Intent(context, historyList.class);


                        intent.putExtra("OrderId", or_id);
                    intent.putExtra("totalpayableamount",total);
                    intent.putExtra("datee",datee);
                    intent.putExtra("Status",Stat);

                        context.startActivity(intent);







                }

            });


        }
        else if (mholder instanceof FooterViewHolder) {
            final FooterViewHolder footerHolder = (FooterViewHolder) mholder;

            if (((History) context).loadingMore == false) {
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



    }


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
            return historyListData.size() + 1; //+1 is for the footer as it's an extra item
        } else {
            return historyListData.size();
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
        return position == historyListData.size();
    }

    public void hideFooter() {
        show_footer = false;
    }

    public void setShow_footer() {
        show_footer = true;

    }




}