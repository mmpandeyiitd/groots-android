package groots.app.com.groots.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import groots.app.com.groots.R;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.pojo.allProduct;
import groots.app.com.groots.ui.UnmappedProducts;
import groots.app.com.groots.ui.mappedProducts;

/**
 * Created by aakash on 26/12/16.
 */

public class mappedProductList_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<allProduct> allproducts;
    ArrayList<allProduct> selectedProducts = new ArrayList<>();

    Context context;
    Fragment fragment;
    View view;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOTER = 2;


    UpdateCart updateCart;
    DbHelper dbHelper;
    boolean show_footer;




    public mappedProductList_Adapter(ArrayList<allProduct> allproducts, Fragment fragment, Context context, boolean f){


        this.allproducts = allproducts;
        this.context = context;
        this.fragment = fragment;
        this.updateCart = updateCart;
        this.show_footer = f;
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
        CheckBox checkedTextView;
        TextView textRup;


        public DataObjectHolder(final View itemView) {
            super(itemView);
            textItemName = (TextView) itemView.findViewById(R.id.textItemName);
            //textItemdesc = (TextView) itemView.findViewById(R.id.textItemdesc);
            textItemPrice = (TextView) itemView.findViewById(R.id.textItemPrice);
            textRup = (TextView) itemView.findViewById(R.id.textRupee);
            checkedTextView =(CheckBox) itemView.findViewById(R.id.checkbox1);
            //checkedTextView.setOnClickListener((View.OnClickListener)this);


        }

        public void clearAnimation() {
            view.clearAnimation();
        }
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.product_list_card_view, parent, false);
            mappedProductList_Adapter.DataObjectHolder dataObjectHolder = new mappedProductList_Adapter.DataObjectHolder(view);
            return dataObjectHolder;
        }
        if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pregress_bar, parent, false);
            return new mappedProductList_Adapter.FooterViewHolder(v);
        }
        return null;
    }




    public void onBindViewHolder(RecyclerView.ViewHolder mholder, final int position) {
        if (mholder instanceof DataObjectHolder) {
            final DataObjectHolder holder = (DataObjectHolder) mholder;
            final DbHelper dbHelper = DbHelper.getInstance(context);
            dbHelper.createDb(false);

            // String desc = allproducts.get(position).product.description;

            int packsize = allproducts.get(position).product.packSize;


            holder.textItemName.setText(allproducts.get(position).product.title);

            holder.textItemPrice.setText("" + allproducts.get(position).product.storeOfferPrice + "/" + packsize + allproducts.get(position).product.packUnit);



            if (allproducts.get(position).isMapped == true){
                holder.checkedTextView.setChecked(true);
            }else{
                holder.checkedTextView.setChecked(false);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkedTextView.isChecked()){
                        holder.checkedTextView.setChecked(false);
                        selectedProducts.remove(allproducts.get(position));
                    }
                    else{
                        holder.checkedTextView.setChecked(true);
                        selectedProducts.add(allproducts.get(position));
                    }





                }
            });


            int pack_size = allproducts.get(position).product.packSize;




           /* holder.checkedTextView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                    if(holder.checkedTextView.isChecked()){


                    }
                    else if(!(holder.checkedTextView.isChecked())){

                    }




                }
            });*/


        } else if (mholder instanceof FooterViewHolder) {
            final FooterViewHolder footerHolder = (FooterViewHolder) mholder;
            if (((mappedProducts) fragment).loadingMoreforall  == false) {
                footerHolder.tvloadmore.setVisibility(View.VISIBLE);
                footerHolder.progressBar.setVisibility(View.GONE);
            }
            else if (((mappedProducts) fragment).loadingMoreforsearch == false){
                footerHolder.tvloadmore.setVisibility(View.VISIBLE);
                footerHolder.progressBar.setVisibility(View.GONE);
            }
            /*if (((UnmappedProducts) context).loadingMoreforall == false) {
                footerHolder.tvloadmore.setVisibility(View.VISIBLE);
                footerHolder.progressBar.setVisibility(View.GONE);
            }*/


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
            return allproducts.size() + 1; //+1 is for the footer as it's an extra item
        } else {
            return allproducts.size();
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
        return position == allproducts.size();
    }

    public void hideFooter() {
        show_footer = false;
    }

    public void setShow_footer() {
        show_footer = true;

    }
    public ArrayList<allProduct> getSelectedProducts(){
        return selectedProducts;
    }




}

