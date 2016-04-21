package groots.canbrand.com.groots.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import groots.canbrand.com.groots.databases.DbHelper;
import groots.canbrand.com.groots.interfaces.UpdateCart;


import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.model.CartClass;
import groots.canbrand.com.groots.pojo.ProductListDocData;
import groots.canbrand.com.groots.utilz.Utilz;

/**
 * Created by Administrator on 04-04-2016.
 */
public class Detail_Adapter extends RecyclerView.Adapter<Detail_Adapter
        .ViewHolderDetail> {


    ArrayList<ProductListDocData> productListDocDatas;
    Context context;
    int lastPosition = -1;
    UpdateCart updateCart;
    DbHelper dbHelper;
    ArrayList<CartClass> cartClasses;


    public Detail_Adapter(ArrayList<ProductListDocData> productListDocDatas, Context context, UpdateCart updateCart) {
        this.context = context;
        this.productListDocDatas = productListDocDatas;
        this.updateCart = updateCart;
        dbHelper = new DbHelper(context);
        dbHelper.createDb(false);
        cartClasses=dbHelper.order();

    }

    @Override

    public Detail_Adapter.ViewHolderDetail onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.detail_adapter_layout, parent, false);
        ViewHolderDetail holder = new ViewHolderDetail(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final Detail_Adapter.ViewHolderDetail holder, final int position) {

        final DbHelper dbHelper = new DbHelper(context);
        dbHelper.createDb(false);


        if (productListDocDatas.get(position).title != null || productListDocDatas.get(position).packSize > 0)
            holder.textItemName.setText(productListDocDatas.get(position).title + " " +
                    productListDocDatas.get(position).packSize + " " + productListDocDatas.get(position).packUnit);
        else holder.textItemName.setVisibility(View.INVISIBLE);


        if (productListDocDatas.get(position).storeOfferPrice > 0)
            holder.itemPrice.setText("" + productListDocDatas.get(position).storeOfferPrice);
        else holder.itemPrice.setVisibility(View.GONE);

        if (productListDocDatas.get(position).description != null)
            holder.itemdesc.setText(productListDocDatas.get(position).description);
        else holder.itemdesc.setVisibility(View.GONE);

        // if(productListDocDatas.get(position).packSize>0)
        holder.itemquantity.setText(productListDocDatas.get(position).packSize + " " + productListDocDatas.get(position).packUnit);
        //  else holder.itemquantity.setVisibility(View.GONE);

        if (productListDocDatas.get(position).diameter > 0) {
            holder.itemdia.setText("Diameter : " + productListDocDatas.get(position).diameter);
            holder.viewDiameterRgt.setVisibility(View.VISIBLE);
        } else {
            holder.itemdia.setVisibility(View.GONE);
            holder.viewDiameterRgt.setVisibility(View.GONE);
        }

        if (productListDocDatas.get(position).color.length() > 0) {
            holder.itemcolor.setText("Color : " + productListDocDatas.get(position).color);
        } else {
            holder.itemcolor.setVisibility(View.GONE);
        }

        if (productListDocDatas.get(position).grade.length() > 0) {
            holder.itemdgrade.setText("Grade : " + productListDocDatas.get(position).grade);
            holder.viewColorRgt.setVisibility(View.VISIBLE);
        } else {
            holder.itemdgrade.setVisibility(View.GONE);
            holder.viewColorRgt.setVisibility(View.GONE);
        }

        //if(productListDocDatas.get(position).getItemquantity()!=null)
        holder.selectedquantity.setText(productListDocDatas.get(position).getItemCount() * productListDocDatas.get(position).packSize
                + " " + productListDocDatas.get(position).packUnit);
        //else  holder.selectedquantity.setVisibility(View.INVISIBLE);


        holder.txtCount.setTag(position);
        holder.txtPlus.setTag(position);
        holder.txtMinus.setTag(position);

        if (productListDocDatas.get(position).getItemCount() > 0) {
            holder.txtCount.setText(productListDocDatas.get(position).getItemCount() + "");
            dbHelper.updateProductQty(productListDocDatas.get(position).getItemCount(), productListDocDatas.get(position).storeOfferPrice, productListDocDatas.get(position).subscribedProductId);
            updateCart.updateCart();
        } else {

            if(!cartClasses.contains(productListDocDatas.get(position)))
            {
                holder.txtCount.setText("0");
                updateCart.updateCart();
            }

        }


        holder.txtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);

                Utilz.count = position;
                int clickedPos = (int) view.getTag();
                //  int previousCount = productListDocDatas.get(clickedPos).getItemCount();
                int previousCount = Integer.parseInt(holder.txtCount.getText().toString().trim());
                previousCount++;
                productListDocDatas.get(clickedPos).setItemCount(previousCount);

                dbHelper.insertCartData(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId,
                        productListDocDatas.get(position).storeId, productListDocDatas.get(position).title,
                        productListDocDatas.get(position).description,
                        "abcde", productListDocDatas.get(position).getItemCount(),
                        productListDocDatas.get(position).storeOfferPrice);

                notifyDataSetChanged();

            }
        });

        holder.txtMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPos = (int) view.getTag();
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);

                // int previousCount = productListDocDatas.get(clickedPos).getItemCount();
                int previousCount = Integer.parseInt(holder.txtCount.getText().toString().trim());
                if (previousCount > 0) {
                    previousCount--;

                    productListDocDatas.get(clickedPos).setItemCount(previousCount);
                    if (previousCount == 0)
                        dbHelper.deleteRecords(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId);
                    //                    else if(previousCount>0)
                    //                        dbHelper.updateProductQty(productListDocDatas.get(position).getItemCount(), productListDocDatas.get(position).subscribedProductId);
                }
                notifyDataSetChanged();
            }
        });


        holder.txtCount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {


                    int clickedPos = (int) v.getTag();
                    int previousCount = Integer.parseInt(holder.txtCount.getText().toString().trim());

                    productListDocDatas.get(clickedPos).setItemCount(previousCount);
                    dbHelper.insertCartData(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId,
                            productListDocDatas.get(position).storeId, productListDocDatas.get(position).title,
                            productListDocDatas.get(position).description,
                            "abcde", productListDocDatas.get(position).getItemCount(),
                            productListDocDatas.get(position).storeOfferPrice);

                    if (previousCount == 0)
                        dbHelper.deleteRecords(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId);

                    holder.txtCount.setText(productListDocDatas.get(position).getItemCount() + "");
                    notifyDataSetChanged();
                    return false;
                }
                return false;

            }
        });

    }


    @Override
    public int getItemCount() {
        return productListDocDatas.size();
    }

    public class ViewHolderDetail extends RecyclerView.ViewHolder {
        TextView textItemName, textItemQuan, itemPrice, itemdesc, itemquantity, itemdia, itemcolor, itemdgrade,
                selectedquantity;

        ImageView iconImage, txtMinus, txtPlus;
        EditText txtCount;
        View viewColorRgt, viewDiameterRgt;

        public ViewHolderDetail(View itemView) {
            super(itemView);

            textItemName = (TextView) itemView.findViewById(R.id.textItemName);
            //  textItemQuan = (TextView) itemView.findViewById(R.id.textItemQuan);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            itemdesc = (TextView) itemView.findViewById(R.id.itemdesc);
            itemquantity = (TextView) itemView.findViewById(R.id.itemquantity);
            itemdia = (TextView) itemView.findViewById(R.id.itemdia);
            itemcolor = (TextView) itemView.findViewById(R.id.itemcolor);
            itemdgrade = (TextView) itemView.findViewById(R.id.itemdgrade);
            selectedquantity = (TextView) itemView.findViewById(R.id.selectedquantity);
            iconImage = (ImageView) itemView.findViewById(R.id.iconImage);
            txtCount = (EditText) itemView.findViewById(R.id.txtCount);
            txtMinus = (ImageView) itemView.findViewById(R.id.txtMinusDetail);
            txtPlus = (ImageView) itemView.findViewById(R.id.txtPlusDetail);
            viewDiameterRgt = itemView.findViewById(R.id.viewDiameterRgt);
            viewColorRgt = itemView.findViewById(R.id.viewColorRgt);
        }
    }
}
