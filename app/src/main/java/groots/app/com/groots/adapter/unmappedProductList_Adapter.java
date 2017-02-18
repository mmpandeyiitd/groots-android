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
import java.util.HashMap;

import groots.app.com.groots.R;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.pojo.RetailerProduct;
import groots.app.com.groots.ui.UnmappedProducts;
import groots.app.com.groots.utilz.Utilz;

/**
 * Created by aakash on 26/12/16.
 */

public class unmappedProductList_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<RetailerProduct> allproducts;
    ArrayList<RetailerProduct> selectedProducts = new ArrayList<>();
    HashMap hash = new HashMap();


    Fragment fragment;
    Context context;
    View view;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOTER = 2;


    UpdateCart updateCart;
    DbHelper dbHelper;
    boolean show_footer;




    public unmappedProductList_Adapter(ArrayList<RetailerProduct> allproducts,HashMap hash ,Context context, Fragment fragment, boolean f){


        this.allproducts = allproducts;
        this.context = context;
        this.fragment = fragment;
        this.hash = hash;
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
            unmappedProductList_Adapter.DataObjectHolder dataObjectHolder = new unmappedProductList_Adapter.DataObjectHolder(view);
            return dataObjectHolder;
        }
        if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pregress_bar, parent, false);
            return new unmappedProductList_Adapter.FooterViewHolder(v);
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



          /*  if (allproducts.get(position).isMapped == true){
                holder.checkedTextView.setChecked(true);
                allproducts.get(position).setstatus(true);
            }else{
                holder.checkedTextView.setChecked(false);
                allproducts.get(position).setstatus(false);

            }
*/
            holder.itemView.setTag(position);
            holder.checkedTextView.setTag(position);

            if ((hash.containsKey(allproducts.get(position).subscribedProductId))  ){
                holder.checkedTextView.setChecked(true);
            }
            else {

                holder.checkedTextView.setChecked(false);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkedTextView.isChecked()){
                        holder.checkedTextView.setChecked(false);
                        dbHelper.insertunmaptomapdata(allproducts.get(position).subscribedProductId,"false",allproducts.get(position).retailer_id);


                        hash.remove(allproducts.get(position).subscribedProductId);


                       /* Utilz.count = position;

                        int clickedPos = (int) v.getTag();
                        boolean stat = allproducts.get(clickedPos).getstatusunmap();

                        if (stat == true) {

                            allproducts.get(clickedPos).setstatusunmap(false);
                        }*/

                    }
                    else{
                        holder.checkedTextView.setChecked(true);
                        dbHelper.insertunmaptomapdata(allproducts.get(position).subscribedProductId,"true",allproducts.get(position).retailer_id);

                        hash.put(allproducts.get(position).subscribedProductId,true);


                       /* Utilz.count = position;
                        int clickedPos = (int) v.getTag();
                        boolean stat = allproducts.get(clickedPos).getstatusunmap();

                        if (stat == false) {

                            allproducts.get(clickedPos).setstatusunmap(true);
                        }*/
                    }
                   //notifyDataSetChanged();




                }
            });


            holder.checkedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.checkedTextView.isChecked() == true){
                        holder.checkedTextView.setChecked(true);
                        dbHelper.insertunmaptomapdata(allproducts.get(position).subscribedProductId,"true",allproducts.get(position).retailer_id);
                        hash.put(allproducts.get(position).subscribedProductId , true);



                        /*selectedProducts.add(allproducts.get(position));

                        Utilz.count = position;
                        int clickedPos = (int) view.getTag();
                        boolean stat = allproducts.get(clickedPos).getstatusunmap();

                        if (stat == false) {

                            allproducts.get(clickedPos).setstatusunmap(true);
                        }*/


                    }
                    else{
                        holder.checkedTextView.setChecked(false);
                        dbHelper.insertunmaptomapdata(allproducts.get(position).subscribedProductId,"false",allproducts.get(position).retailer_id);
                        hash.remove(allproducts.get(position).subscribedProductId);
                        /*selectedProducts.remove(allproducts.get(position));

                        Utilz.count = position;

                        int clickedPos = (int) view.getTag();
                        boolean stat = allproducts.get(clickedPos).getstatusunmap();

                        if (stat == true) {

                            allproducts.get(clickedPos).setstatusunmap(false);
                        }*/


                    }

                  //  notifyDataSetChanged();
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

            if (((UnmappedProducts) fragment).loadingMoreforall  == false) {

                footerHolder.tvloadmore.setVisibility(View.VISIBLE);
                footerHolder.progressBar.setVisibility(View.GONE);
            }
            else if (((UnmappedProducts) fragment).loadingMoreforsearch == false){
                footerHolder.tvloadmore.setVisibility(View.VISIBLE);
                footerHolder.progressBar.setVisibility(View.GONE);
            }





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
    public ArrayList<RetailerProduct> getSelectedProducts(){
        return selectedProducts;
    }




}
