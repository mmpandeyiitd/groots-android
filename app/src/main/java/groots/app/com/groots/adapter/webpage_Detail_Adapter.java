package groots.app.com.groots.adapter;

/**
 * Created by aakash on 28/10/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.R;
import groots.app.com.groots.model.CartClass;
import groots.app.com.groots.pojo.Product;
import groots.app.com.groots.ui.historyList;
import groots.app.com.groots.utilz.Utilz;


public class webpage_Detail_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<Product> productListDocDatas;
    Context context;
    /*int lastPosition = -1;*/
    UpdateCart updateCart;
    DbHelper dbHelper;
    ArrayList<CartClass> cartClasses;
    /*  boolean flag = false;*/
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOTER = 2;
    boolean show_footer;


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


    public webpage_Detail_Adapter(ArrayList<Product> productListDocDatas, Context context, UpdateCart updateCart,boolean flag) {
        this.context = context;
        this.productListDocDatas = productListDocDatas;
        this.updateCart = updateCart;
        dbHelper = new DbHelper(context);
        dbHelper.createDb(false);
        cartClasses = dbHelper.order();
        this.show_footer=flag;

    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_adapter_layout, parent, false);
            //  ViewHolderDetail dataObjectHolder = new ViewHolderDetail(view);
            return new ViewHolderDetail(view);
        }
        else if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pregress_bar, parent, false);
            return new FooterViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mholder, final int position) {

        if (mholder instanceof ViewHolderDetail) {
            final ViewHolderDetail holder = (ViewHolderDetail) mholder;
          /*  final DbHelper dbHelper = new DbHelper(context);
            dbHelper.createDb(false);*/



            if (productListDocDatas.get(position).title != null || productListDocDatas.get(position).packSize > 0)
                holder.textItemName.setText(productListDocDatas.get(position).title + " " +
                        productListDocDatas.get(position).packSize + " " + productListDocDatas.get(position).packUnit);
            else holder.textItemName.setVisibility(View.GONE);


            if (productListDocDatas.get(position).storeOfferPrice > 0)
                holder.itemPrice.setText("" + productListDocDatas.get(position).storeOfferPrice);
            else holder.itemPrice.setVisibility(View.GONE);

            if (productListDocDatas.get(position).description != null)
                holder.itemdesc.setText(productListDocDatas.get(position).description);
            else holder.itemdesc.setVisibility(View.GONE);

            // if(productListDocDatas.get(position).packSize>0)
            if (productListDocDatas.get(position).packSize <= 1) {
                holder.itemquantity.setText(productListDocDatas.get(position).packUnit);
            } else
                holder.itemquantity.setText(productListDocDatas.get(position).packSize+productListDocDatas.get(position).packUnit);
            //  else holder.itemquantity.setVisibility(View.GONE);
            if(!productListDocDatas.get(position).diameter.equals("")) {
                holder.itemdia.setVisibility(View.VISIBLE);
                holder.itemdia.setText("Diameter : " + productListDocDatas.get(position).diameter);
            }
            else {
                holder.itemdia.setVisibility(View.GONE);
            }

            if (!productListDocDatas.get(position).color.equals("")) {
                holder.itemcolor.setVisibility(View.VISIBLE);

                holder.itemcolor.setText("Color : " + productListDocDatas.get(position).color);
                if(productListDocDatas.get(position).diameter.length()>0 || productListDocDatas.get(position).color.length()>0) {
                    holder.viewColorRgt.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.viewColorRgt.setVisibility(View.GONE);
                }
            } else
            {
                holder.itemcolor.setVisibility(View.GONE);
                holder.viewColorRgt.setVisibility(View.GONE);
            }

            if (productListDocDatas.get(position).grade.length() > 0) {
                holder.itemdgrade.setText("Grade : " + productListDocDatas.get(position).grade);
                holder.itemdgrade.setVisibility(View.VISIBLE);
                if(productListDocDatas.get(position).grade.length() > 0 &(productListDocDatas.get(position).diameter.length()>0 || productListDocDatas.get(position).color.length()>0 ) ) {
                    holder.viewgrade.setVisibility(View.VISIBLE);
                }else
                {
                    holder.viewgrade.setVisibility(View.GONE);
                }
            } else {
                holder.itemdgrade.setVisibility(View.GONE);
                holder.viewgrade.setVisibility(View.GONE);
                //  holder.viewColorRgt.setVisibility(View.GONE);
            }
            if (productListDocDatas.get(position).length> 0) {
                holder.itemdia.setVisibility(View.VISIBLE);
                holder.itemdia.setText("Length : " + productListDocDatas.get(position).length+productListDocDatas.get(position).length_unit);

            } else if(productListDocDatas.get(position).length> 0 & productListDocDatas.get(position).diameter.length()>0) {
                holder.itemdia.setVisibility(View.GONE);
                //  holder.viewlen.setVisibility(View.GONE);
            }

            //if(productListDocDatas.get(position).getItemquantity()!=null)
            if(productListDocDatas.get(position).getItemCount() * productListDocDatas.get(position).packSize>=1000) {
                holder.selectedquantity.setText(productListDocDatas.get(position).getItemCount() *(float)productListDocDatas.get(position).packSize/1000+"K"+
                        productListDocDatas.get(position).packUnit);
            }else
            {
                holder.selectedquantity.setText(productListDocDatas.get(position).getItemCount() * productListDocDatas.get(position).packSize+
                        " " + productListDocDatas.get(position).packUnit);
            }

            //else  holder.selectedquantity.setVisibility(View.INVISIBLE);
            if(productListDocDatas.get(position).thumbUrl.size()>1) {
                //  Toast.makeText(context,productListDocDatas.get(position).mediaUrl.get(0),Toast.LENGTH_SHORT);
                imageLoader.displayImage(productListDocDatas.get(position).thumbUrl.get(1),holder.iconImage,options);
            }
            else
            {
                imageLoader.displayImage(productListDocDatas.get(position).thumbUrl.get(0),holder.iconImage,options);
            }


            holder.txtCount.setTag(position);
            holder.txtPlus.setTag(position);
            holder.txtMinus.setTag(position);

            if (productListDocDatas.get(position).getItemCount() > 0) {

                holder.txtCount.setText(productListDocDatas.get(position).getItemCount() + "");
                dbHelper.updateProductQty(productListDocDatas.get(position).getItemCount(), productListDocDatas.get(position).storeOfferPrice, productListDocDatas.get(position).subscribedProductId);
                holder.selectedquantity.setTextColor(Color.parseColor("#2D2D2D"));
                holder.txtselectedquan.setTextColor(Color.parseColor("#2D2D2D"));
                updateCart.updateCart();
            } else {

                if (!cartClasses.contains(productListDocDatas.get(position))) {
                    holder.txtCount.setText("0");
                    updateCart.updateCart();
                    holder.selectedquantity.setTextColor(Color.LTGRAY);
                    holder.txtselectedquan.setTextColor(Color.LTGRAY);
                }

            }


            holder.txtPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Integer.parseInt(holder.txtCount.getText().toString().trim()) >= 999) {
                        Toast.makeText(context, "Sorry, you can't add more these item.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (holder.txtCount.getText().toString().trim().length() == 0) {
                            holder.txtCount.setText("0");
                        }
                        //   Toast.makeText(context,holder.txtCount.getText().toString().trim(),Toast.LENGTH_SHORT).show();
                        // ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);

                        Utilz.count = position;
                        int clickedPos = (int) view.getTag();
                        //  int previousCount = productListDocDatas.get(clickedPos).getItemCount();
                        holder.selectedquantity.setTextColor(Color.parseColor("#2D2D2D"));
                        holder.txtselectedquan.setTextColor(Color.parseColor("#2D2D2D"));
                        int previousCount = Integer.parseInt(holder.txtCount.getText().toString().trim());
                        previousCount++;
                        productListDocDatas.get(clickedPos).setItemCount(previousCount);

                        if (productListDocDatas.get(position).thumbUrl.size()>1) {


                            dbHelper.insertCartData(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId,
                                    productListDocDatas.get(position).storeId, productListDocDatas.get(position).title,
                                    productListDocDatas.get(position).description,
                                    productListDocDatas.get(position).thumbUrl.get(1), productListDocDatas.get(position).getItemCount(),
                                    productListDocDatas.get(position).storeOfferPrice,productListDocDatas.get(position).packSize.toString(),productListDocDatas.get(position).packUnit);

                        } else {

                            dbHelper.insertCartData(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId,
                                    productListDocDatas.get(position).storeId, productListDocDatas.get(position).title,
                                    productListDocDatas.get(position).description,
                                    productListDocDatas.get(position).thumbUrl.get(0), productListDocDatas.get(position).getItemCount(),
                                    productListDocDatas.get(position).storeOfferPrice,productListDocDatas.get(position).packSize.toString(),productListDocDatas.get(position).packUnit);
                        }

                        notifyDataSetChanged();

                    }
                }
            });

            holder.txtMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPos = (int) view.getTag();
                    ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);

                    if (holder.txtCount.getText().toString().trim().length() == 0) {
                        holder.txtCount.setText("0");
                        productListDocDatas.get(clickedPos).setItemCount(0);
                        dbHelper.deleteRecords(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId);
                        holder.selectedquantity.setTextColor(Color.LTGRAY);
                        holder.txtselectedquan.setTextColor(Color.LTGRAY);

                    } else {// int previousCount = productListDocDatas.get(clickedPos).getItemCount();
                        int previousCount = Integer.parseInt(holder.txtCount.getText().toString().trim());
                        if (previousCount > 0) {
                            previousCount--;

                            productListDocDatas.get(clickedPos).setItemCount(previousCount);
                            if (previousCount == 0) {
                                dbHelper.deleteRecords(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId);
                                holder.selectedquantity.setTextColor(Color.LTGRAY);
                                holder.txtselectedquan.setTextColor(Color.LTGRAY);
                            } //                    else if(previousCount>0)
                            //                        dbHelper.updateProductQty(productListDocDatas.get(position).getItemCount(), productListDocDatas.get(position).subscribedProductId);
                        }
                    }
                    notifyDataSetChanged();
                }
            });


      /*  holder.txtCount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                flag=true;
                return false;
            }
        });*/


            holder.txtCount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        if (holder.txtCount.getText().toString().trim().length() == 0) {
                            holder.txtCount.setText("0");
                        }
                        int clickedPos = (int) v.getTag();
                        int previousCount = Integer.parseInt(holder.txtCount.getText().toString().trim());

                        productListDocDatas.get(clickedPos).setItemCount(previousCount);
                        if (productListDocDatas.get(position).thumbUrl.size()>1) {

                            dbHelper.insertCartData(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId,
                                    productListDocDatas.get(position).storeId, productListDocDatas.get(position).title,
                                    productListDocDatas.get(position).description,
                                    productListDocDatas.get(position).thumbUrl.get(1), productListDocDatas.get(position).getItemCount(),
                                    productListDocDatas.get(position).storeOfferPrice, productListDocDatas.get(position).packSize.toString(), productListDocDatas.get(position).packUnit);
                        }
                        else
                        {
                            dbHelper.insertCartData(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId,
                                    productListDocDatas.get(position).storeId, productListDocDatas.get(position).title,
                                    productListDocDatas.get(position).description,
                                    productListDocDatas.get(position).thumbUrl.get(0), productListDocDatas.get(position).getItemCount(),
                                    productListDocDatas.get(position).storeOfferPrice, productListDocDatas.get(position).packSize.toString(), productListDocDatas.get(position).packUnit);
                        }
                        if (previousCount == 0)
                            dbHelper.deleteRecords(productListDocDatas.get(position).subscribedProductId, productListDocDatas.get(position).baseProductId);

                        holder.txtCount.setText(productListDocDatas.get(position).getItemCount() + "");
                        notifyDataSetChanged();
                        return false;
                    }
                    return false;

                }
            });
            return;
        } else if (mholder instanceof FooterViewHolder) {
            final FooterViewHolder footerHolder = (FooterViewHolder) mholder;

            if (((historyList) context).loadingMore == false) {
                footerHolder.tvloadmore.setVisibility(View.VISIBLE);
                footerHolder.progressBar.setVisibility(View.GONE);
            }
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

    @Override
    public int getItemCount() {

        if (show_footer) {
            return productListDocDatas.size() + 1; //+1 is for the footer as it's an extra item
        } else {
            return productListDocDatas.size();
        }

    }

    private boolean isPositionFooter(int position) {
        //if position == arr_userid.size then we need to show footerView otherwise we'll get indexOutOfBoundsException
        return position == productListDocDatas.size();
    }

    public void hideFooter() {
        show_footer = false;
    }
    public void setShow_footer()
    {
        show_footer=true;

    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView tvloadmore;
        ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            this.tvloadmore = (TextView) itemView.findViewById(R.id.tvloadmore);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }


    }

    public class ViewHolderDetail extends RecyclerView.ViewHolder {
        TextView textItemName, textItemQuan, itemPrice, itemdesc, itemquantity, itemdia, itemcolor, itemdgrade, txtselectedquan,
                selectedquantity,itemlength;

        ImageView iconImage;
        ImageButton txtMinus, txtPlus;
        EditText txtCount;
        View viewColorRgt,viewlen,viewgrade;

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
            txtMinus = (ImageButton) itemView.findViewById(R.id.txtMinusDetail);
            txtPlus = (ImageButton) itemView.findViewById(R.id.txtPlusDetail);
            viewgrade=itemView.findViewById(R.id.viewgrade);
            viewColorRgt = itemView.findViewById(R.id.viewColorRgt);
            txtselectedquan = (TextView) itemView.findViewById(R.id.txtselectedquan);
            //  itemlength=(TextView)itemView.findViewById(R.id.itemlength);
            //  viewlen=itemView.findViewById(R.id.viewlen);

        }
    }
}

