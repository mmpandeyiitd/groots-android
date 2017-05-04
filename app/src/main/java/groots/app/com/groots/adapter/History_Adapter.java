package groots.app.com.groots.adapter;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.R;
import groots.app.com.groots.model.CartClass;
import groots.app.com.groots.pojo.Order;
import groots.app.com.groots.ui.History;
import groots.app.com.groots.ui.Landing_Update;
import groots.app.com.groots.ui.historyList;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by aakash on 3/11/16.
 */

public class History_Adapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>  {





    ArrayList<Order> historyListData;
    Context context;
    View view = null;
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
        dbHelper =  DbHelper.getInstance(context);
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
        LinearLayout first_half_order,second_half_payment,chequeStatus;


        TextView inv_no_id,trans_no_id,paid_amount_id,mode_payment_id,cheque_status_id;

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
           // first_half_order = (LinearLayout) itemView.findViewById(R.id.first_half_id);
            //second_half_payment =(LinearLayout) itemView.findViewById(R.id.second_half_id);
            chequeStatus =(LinearLayout) itemView.findViewById(R.id.chequeStatus);
            chequeStatus.setVisibility(View.GONE);

            inv_no_id = (TextView) itemView.findViewById(R.id.inv_no_id);
            trans_no_id =(TextView) itemView.findViewById(R.id.trans_no_id);
            paid_amount_id = (TextView) itemView.findViewById(R.id.paid_amount_id);
            mode_payment_id = (TextView) itemView.findViewById(R.id.mode_payment_id);
            cheque_status_id = (TextView) itemView.findViewById(R.id.cheque_status_id);

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
view = null;
        if (viewType == TYPE_ITEM) {

            //view = null;
            view = LayoutInflater.from(context).inflate(R.layout.new_design_history_card_view, parent, false);
            History_Adapter.DataObjectHolder dataObjectHolder = new History_Adapter.DataObjectHolder(view);
            return dataObjectHolder;
        }


        if (viewType == TYPE_FOOTER) {
            View v = null ;
             v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pregress_bar, parent, false);
            return new FooterViewHolder(v);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mholder, final int position) {
        if (mholder instanceof DataObjectHolder) {
            final DataObjectHolder holder = (DataObjectHolder) mholder;
            final DbHelper dbHelper = DbHelper.getInstance(context);
            dbHelper.createDb(false);






            //int i=0;
         if (historyListData.get(position).invoiceNo == null){
             holder.date_id.setText(historyListData.get(position).payment.paymentDate);
         }
            else {
             holder.date_id.setText(historyListData.get(position).deliveryDate.substring(0, 10));
         }

            if (historyListData.get(position).invoiceNo == null){
                holder.textItemPric.setText("--");
                holder.status.setText("--");
                holder.inv_no_id.setText("--");

            }
            else{



                holder.status.setText(historyListData.get(position).Status);

                /*if (historyListData.get(position).Status.equals("Delivered")){

                    showNotification(view);



                }*/
                if (historyListData.get(position).Status .equals("Cancelled") ){
                    holder.textItemPric.setText("0.00");
                }else{

                    holder.textItemPric.setText(historyListData.get(position).totalPayableAmount.toString());
                }

                holder.inv_no_id.setText(historyListData.get(position).invoiceNo);



            }











            if (historyListData.get(position).payment == null ) {
             holder.trans_no_id.setText("--");


                holder.paid_amount_id.setText("--");

                holder.mode_payment_id.setText("--");
                holder.chequeStatus.setVisibility(View.GONE);
                //holder.cheque_status_id.setText("NULL");


            }
            else {
                holder.trans_no_id.setText(historyListData.get(position).payment.refNo);
                holder.chequeStatus.setVisibility(View.GONE);


                holder.paid_amount_id.setText(historyListData.get(position).payment.amountPaid.toString());


                holder.mode_payment_id.setText(historyListData.get(position).payment.modeofPayment);

                if (historyListData.get(position).payment.modeofPayment .equals("Cheque") ) {


                    holder.chequeStatus.setVisibility(View.VISIBLE);
                    holder.cheque_status_id.setVisibility(View.VISIBLE);

                    //  System.out.println(historyListData.get(position).payment.chequeStatus);


                    holder.cheque_status_id.setText(historyListData.get(position).payment.chequeStatus);


                }



            }





            //Payment payment = new Payment();




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



            /*holder.date_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //String o_I = historyListData.get(position).orderId;
                    Intent intent = new Intent(context, historyList.class);



                    context.startActivity(intent);






                }

            });*/


            if (historyListData.get(position).invoiceNo != null) {





                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        //String o_I = historyListData.get(position).orderId;


                        String or_id = historyListData.get(position).orderId.toString();
                        String total = holder.textItemPric.getText().toString();
                        String datee = historyListData.get(position).deliveryDate.substring(0, 10);
                        String Stat = historyListData.get(position).Status;
                        Intent intent = new Intent(context, historyList.class);


                        intent.putExtra("OrderId", or_id);
                        intent.putExtra("totalpayableamount", total);
                        intent.putExtra("datee", datee);
                        intent.putExtra("Status", Stat);

                        context.startActivity(intent);


                    }

                });
            }
            else {
                holder.itemView.setOnClickListener(null);

            }




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

    /*public void showNotification(View view) {
        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, History.class), 0);
        // Resources r = getResources();
        //Resources t = Resources.getSystem();
        Notification notification = new NotificationCompat.Builder(context)
                .setTicker("Groots Notification")
                .setSmallIcon(R.drawable.logoo)
                .setContentTitle("Gogroots")
                .setContentText("Your last order has been delivered.")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }*/




}