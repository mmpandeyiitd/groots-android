package groots.canbrand.com.groots.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import groots.canbrand.com.groots.databases.DbHelper;
import groots.canbrand.com.groots.interfaces.UpdateCart;


import groots.canbrand.com.groots.R;
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

            public Detail_Adapter(ArrayList<ProductListDocData> productListDocDatas, Context context, UpdateCart updateCart) {
                this.context = context;
                this.productListDocDatas = productListDocDatas;
                this.updateCart=updateCart;
            }

            @Override

            public Detail_Adapter.ViewHolderDetail onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context)
                        .inflate(R.layout.detail_adapter_layout, parent, false);
                ViewHolderDetail holder = new ViewHolderDetail(view);

                return holder;
            }

            @Override
            public void onBindViewHolder(Detail_Adapter.ViewHolderDetail holder, final int position) {

                final DbHelper dbHelper=new DbHelper(context);
                dbHelper.createDb(false);


                if(productListDocDatas.get(position).title!=null||productListDocDatas.get(position).packSize>0)
                holder.textItemName.setText(productListDocDatas.get(position).title+" "+
                        productListDocDatas.get(position).packSize+" " +productListDocDatas.get(position).packUnit);
                else holder.textItemName.setVisibility(View.INVISIBLE);


                if(productListDocDatas.get(position).storeOfferPrice>0)
                holder.itemPrice.setText(""+productListDocDatas.get(position).storeOfferPrice);
                else  holder.itemPrice.setVisibility(View.GONE);

                if(productListDocDatas.get(position).description!=null)
                holder.itemdesc.setText(productListDocDatas.get(position).description);
                else  holder.itemdesc.setVisibility(View.GONE);

               // if(productListDocDatas.get(position).packSize>0)
                holder.itemquantity.setText(productListDocDatas.get(position).packSize+" "+productListDocDatas.get(position).packUnit);
              //  else holder.itemquantity.setVisibility(View.GONE);

               if (productListDocDatas.get(position).diameter>0) {
                   holder.itemdia.setText("Diameter : " + productListDocDatas.get(position).diameter);
                   holder.viewDiameterRgt.setVisibility(View.VISIBLE);
               }else {
                   holder.itemdia.setVisibility(View.GONE);
                   holder.viewDiameterRgt.setVisibility(View.GONE);
               }

                if(productListDocDatas.get(position).color.length()>0) {
                    holder.itemcolor.setText("Color : " + productListDocDatas.get(position).color);
                }else {
                    holder.itemcolor.setVisibility(View.GONE);
                }

                if(productListDocDatas.get(position).grade.length()>0) {
                    holder.itemdgrade.setText("Grade : " + productListDocDatas.get(position).grade);
                    holder.viewColorRgt.setVisibility(View.VISIBLE);
                }else{
                    holder.itemdgrade.setVisibility(View.GONE);
                    holder.viewColorRgt.setVisibility(View.GONE);
                }

                //if(productListDocDatas.get(position).getItemquantity()!=null)
                holder.selectedquantity.setText(productListDocDatas.get(position).getItemCount()*productListDocDatas.get(position).packSize
                        +" "+productListDocDatas.get(position).packUnit);
                //else  holder.selectedquantity.setVisibility(View.INVISIBLE);


                holder.txtCount.setTag(position);
                holder.txtPlus.setTag(position);
                holder.txtMinus.setTag(position);


                if(productListDocDatas.get(position).getItemCount()>0) {
                    holder.txtCount.setText(productListDocDatas.get(position).getItemCount() + "");
                    dbHelper.updateProductQty(productListDocDatas.get(position).getItemCount(),productListDocDatas.get(position).storeOfferPrice, productListDocDatas.get(position).subscribedProductId);
                    updateCart.updateCart();
                }
                else {
                    holder.txtCount.setText(""+0);
                    updateCart.updateCart();
                }



                holder.txtPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Utilz.count=position;
                        int clickedPos = (int) view.getTag();
                        int previousCount = productListDocDatas.get(clickedPos).getItemCount();

                        previousCount++;

                        productListDocDatas.get(clickedPos).setItemCount(previousCount);

                        dbHelper.insertCartData(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId,
                                productListDocDatas.get(position).storeId, productListDocDatas.get(position).title,
                                productListDocDatas.get(position).description,
                                "abcde",productListDocDatas.get(position).getItemCount(),
                                productListDocDatas.get(position).storeOfferPrice);

                        notifyDataSetChanged();

                    }
                });

                holder.txtMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int clickedPos = (int) view.getTag();
                        int previousCount = productListDocDatas.get(clickedPos).getItemCount();
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
                View viewColorRgt,viewDiameterRgt;

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
                    viewDiameterRgt=itemView.findViewById(R.id.viewDiameterRgt);
                    viewColorRgt=itemView.findViewById(R.id.viewColorRgt);
                }
            }
}
