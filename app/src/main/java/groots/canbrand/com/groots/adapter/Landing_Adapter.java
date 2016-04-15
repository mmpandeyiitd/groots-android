package groots.canbrand.com.groots.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import groots.canbrand.com.groots.databases.DbHelper;
import groots.canbrand.com.groots.interfaces.UpdateCart;
import groots.canbrand.com.groots.R;
import groots.canbrand.com.groots.pojo.ProductListDocData;

/**
 * Created by Administrator on 04-04-2016.
 */
public class Landing_Adapter extends RecyclerView.Adapter<Landing_Adapter
        .DataObjectHolder> {


    ArrayList<ProductListDocData> productListData;
    Context context;
    View view;
    int lastPosition =-1;
    UpdateCart updateCart;

    public Landing_Adapter(ArrayList<ProductListDocData> productListData, Context context, UpdateCart updateCart) {
        this.productListData=productListData;
        this.context=context;
        this.updateCart=updateCart;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder
    {
        TextView textItemName;
        TextView textItemdesc;
        TextView textItemPrice;
        ImageView imgItemIcon;
        TextView txtCount;
        ImageView txtPlus,txtMinus;

        public DataObjectHolder(final View itemView) {
            super(itemView);
            textItemName = (TextView) itemView.findViewById(R.id.textItemName);
            textItemdesc = (TextView) itemView.findViewById(R.id.textItemdesc);
            textItemPrice=(TextView)itemView.findViewById(R.id.textItemPrice);
            imgItemIcon=(ImageView)itemView.findViewById(R.id.imgItemIcon);
            txtCount=(TextView)itemView.findViewById(R.id.txtCount);
            txtMinus=(ImageView)itemView.findViewById(R.id.txtMinus);
            txtPlus=(ImageView)itemView.findViewById(R.id.txtPlus);
        }
    }


    @Override
    public Landing_Adapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.landing_card_view_row, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(Landing_Adapter.DataObjectHolder holder, final int position) {

        final DbHelper dbHelper=new DbHelper(context);
        dbHelper.createDb(false);

        holder.textItemName.setText(productListData.get(position).title);
        holder.textItemdesc.setText(productListData.get(position).description);
        holder.textItemPrice.setText(""+productListData.get(position).storeOfferPrice);

        holder.txtCount.setText(""+productListData.get(position).getItemCount());

      /*  Picasso.with(context).load(productListData.get(position).defaultThumbUrl)
                .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.imgItemIcon);

        Picasso.with(context).load(productListData.get(position).)) .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image).into(childViewHolder.imgItemIcon);
*/
        if (position > lastPosition) {
            holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
            lastPosition = position;
        }

        if (productListData.get(position).getItemCount() > 0) {
            holder.txtCount.setText("" + productListData.get(position).getItemCount());
            dbHelper.updateProductQty( productListData.get(position).getItemCount(), productListData.get(position).subscribedProductId);
            updateCart.updateCart();
        }else
            holder.txtCount.setText("0");


        holder.txtPlus.setTag(position);
        holder.txtMinus.setTag(position);
        holder.txtCount.setTag(position);

        holder.txtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int clickedPos = (int) view.getTag();
                int previousCount = productListData.get(clickedPos).getItemCount();

                previousCount++;

                productListData.get(clickedPos).setItemCount(previousCount);

                dbHelper.insertCartData(productListData.get(position).subscribedProductId, productListData.get(position).baseProductId,
                        productListData.get(position).storeId, productListData.get(position).title,
                        "abcde",productListData.get(position).getItemCount(),
                        productListData.get(position).storeOfferPrice);

                notifyDataSetChanged();

            }
        });

        holder.txtMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPos = (int) view.getTag();
                int previousCount = productListData.get(clickedPos).getItemCount();
                if (previousCount > 0) {
                    previousCount--;

                    productListData.get(clickedPos).setItemCount(previousCount);
                    if (previousCount == 0)
                        dbHelper.deleteRecords(productListData.get(position).subscribedProductId, productListData.get(position).baseProductId);
                        else if(previousCount>0)
                        dbHelper.updateProductQty(productListData.get(position).getItemCount(), productListData.get(position).subscribedProductId);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productListData.size();
    }


    }
