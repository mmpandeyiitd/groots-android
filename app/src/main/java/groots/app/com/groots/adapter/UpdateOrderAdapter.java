
/**
 * Created by aakash on 18/11/16.
 */

package groots.app.com.groots.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import groots.app.com.groots.databases.dbHelp;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.R;
import groots.app.com.groots.model.UpdateCartClass;
import groots.app.com.groots.pojo.Product;
import groots.app.com.groots.ui.UpdateOrder;
import groots.app.com.groots.utilz.Utilz;


public class UpdateOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<Product> productListData;
    Context context;
    View view = null;
    int lastPosition = -1;
    UpdateCart updateCart;
    dbHelp dbHelp;
    ArrayList<UpdateCartClass> cartClasses;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOTER = 2;
    boolean show_footer;

    public UpdateOrderAdapter(ArrayList<Product> productListData, Context context, UpdateCart updateCart, boolean f) {
        this.productListData = productListData;
        this.context = context;
        this.updateCart = updateCart;
        dbHelp = new dbHelp(context);
        dbHelp.createDb(false);
        cartClasses = dbHelp.updateorder();
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
        TextView textItemName;
        TextView textItemdesc;
        TextView textItemPrice;
        ImageView imgItemIcon;
        TextView textRup;
        EditText txtCount;
        ImageButton txtPlus, txtMinus;

        public DataObjectHolder(final View itemView) {
            super(itemView);
            textItemName = (TextView) itemView.findViewById(R.id.textItemName);
            textItemdesc = (TextView) itemView.findViewById(R.id.textItemdesc);
            textItemPrice = (TextView) itemView.findViewById(R.id.textItemPrice);
            imgItemIcon = (ImageView) itemView.findViewById(R.id.imgItemIcon);
            textRup = (TextView) itemView.findViewById(R.id.textRupee);
            txtCount = (EditText) itemView.findViewById(R.id.txtCount);
            txtMinus = (ImageButton) itemView.findViewById(R.id.txtMinus);
            txtPlus = (ImageButton) itemView.findViewById(R.id.txtPlus);
        }

        public void clearAnimation() {
            view.clearAnimation();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.landing_card_view_row, parent, false);
            DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
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
            final dbHelp dbHelp = new dbHelp(context);
            dbHelp.createDb(false);

            holder.txtPlus.setEnabled(true);
            holder.txtMinus.setEnabled(true);
            holder.txtCount.setEnabled(true);

            holder.textRup.setVisibility(View.VISIBLE);

            holder.textItemName.setText(productListData.get(position).title);
            holder.textItemdesc.setText(productListData.get(position).description);
            int pack_size = productListData.get(position).packSize;

            if (productListData.get(position).outOfStock == true ){


                holder.textRup.setVisibility(View.GONE);
                holder.textItemPrice.setText("Out of Stock");
                holder.txtPlus.setEnabled(false);
                holder.txtMinus.setEnabled(false);
                holder.txtCount.setEnabled(false);

            }
           else {
                holder.txtPlus.setEnabled(true);
                holder.txtMinus.setEnabled(true);
                holder.txtCount.setEnabled(true);
                holder.textRup.setVisibility(View.VISIBLE);

                if (pack_size <= 1) {
                    holder.textItemPrice.setText("" + productListData.get(position).storeOfferPrice + "/" + pack_size + productListData.get(position).packUnit);
                } else {
                    holder.textItemPrice.setText("" + productListData.get(position).storeOfferPrice + "/" + pack_size + productListData.get(position).packUnit);
                }
           }
            holder.txtCount.setText("" + productListData.get(position).getItemCount());

            if (!productListData.get(position).thumbUrl.equals(null)) {
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
                imageLoader.displayImage(productListData.get(position).thumbUrl.get(0), holder.imgItemIcon, options);
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


           /* if (productListData.get(position).getItemCount() == null){

            }*/

            if (productListData.get(position).getItemCount() > 0) {

                    holder.txtCount.setText("" + productListData.get(position).getItemCount());
                    dbHelp.updateProductQty(productListData.get(position).getItemCount(), productListData.get(position).storeOfferPrice, productListData.get(position).subscribedProductId);
                    updateCart.updateCart();






            }




            else {
                if (!cartClasses.contains(productListData.get(position))) {
                    holder.txtCount.setText("0");
                    updateCart.updateCart();
                }
          /*  holder.txtCount.setText("0");
            updateCart.updateCart();*/
            }


            holder.txtPlus.setTag(position);
            holder.txtMinus.setTag(position);
            holder.txtCount.setTag(position);


            holder.txtPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Double.parseDouble(holder.txtCount.getText().toString().trim()) >= 999.0) {
                        Toast.makeText(context, "Sorry, you can't add more these item.", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Utilz.count = position;
                        int clickedPos = (int) view.getTag();
                        Double previousCount = productListData.get(clickedPos).getItemCount();

                        previousCount++;

                        productListData.get(clickedPos).setItemCount(previousCount);
                       /* HashMap hash = new HashMap();
                        hash.put("what","update");*/


                        dbHelp.insertUpdateCartData(productListData.get(position).subscribedProductId, productListData.get(position).baseProductId,
                                productListData.get(position).storeId, productListData.get(position).title,
                                productListData.get(position).description,
                                productListData.get(position).thumbUrl.get(0), productListData.get(position).getItemCount(),
                                productListData.get(position).storeOfferPrice,productListData.get(position).packSize.toString(),productListData.get(position).packUnit);

                        notifyDataSetChanged();

                    }
                }
            });

            holder.txtMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Utilz.count = position;
                    int clickedPos = (int) view.getTag();
                    Double previousCount = productListData.get(clickedPos).getItemCount();
                    if (previousCount > 0) {
                        previousCount--;

                        productListData.get(clickedPos).setItemCount(previousCount);

                       /* HashMap hash = new HashMap();
                        hash.put("what","update");*/

    try {
        dbHelp.insertUpdateCartData(productListData.get(position).subscribedProductId, productListData.get(position).baseProductId,
                productListData.get(position).storeId, productListData.get(position).title,
                productListData.get(position).description,
                productListData.get(position).thumbUrl.get(0), productListData.get(position).getItemCount(),
                productListData.get(position).storeOfferPrice, productListData.get(position).packSize.toString(), productListData.get(position).packUnit);
    }
    catch (Exception e){
        /*Snackbar snackbar = Snackbar.make(view, "Oops! Something went wrong.Please try again later.", Snackbar.LENGTH_SHORT);

        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show()*/;


    }
                        if (previousCount == 0)
                            dbHelp.deleteRecords(productListData.get(position).subscribedProductId, productListData.get(position).baseProductId);


                        notifyDataSetChanged();
//                        else if(previousCount>0)
//                        dbHelper.updateProductQty(orderItems.get(position).getItemCount(), orderItems.get(position).subscribedProductId);
                    }

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
                        Double previousCount = Double.parseDouble(holder.txtCount.getText().toString().trim());

                        productListData.get(clickedPos).setItemCount(previousCount);

                       /* HashMap hash = new HashMap();
                        hash.put("what","update");*/


                        dbHelp.insertUpdateCartData(productListData.get(position).subscribedProductId, productListData.get(position).baseProductId,
                                productListData.get(position).storeId, productListData.get(position).title,
                                productListData.get(position).description,
                                productListData.get(position).thumbUrl.get(0), productListData.get(position).getItemCount(),
                                productListData.get(position).storeOfferPrice, productListData.get(position).packSize.toString(),productListData.get(position).packUnit);

                        if (previousCount == 0)
                            dbHelp.deleteRecords(productListData.get(position).subscribedProductId, productListData.get(position).baseProductId);

                        holder.txtCount.setText(productListData.get(position).getItemCount() + "");
                        notifyDataSetChanged();
                        return false;
                    }
                    return false;
                }
            });
        } else if (mholder instanceof FooterViewHolder) {
            final FooterViewHolder footerHolder = (FooterViewHolder) mholder;

            if (((UpdateOrder) context).loadingMore == false) {
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
            return productListData.size() + 1; //+1 is for the footer as it's an extra item
        } else {
            return productListData.size();
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
        return position == productListData.size();
    }

    public void hideFooter() {
        show_footer = false;
    }

    public void setShow_footer() {
        show_footer = true;

    }

}

