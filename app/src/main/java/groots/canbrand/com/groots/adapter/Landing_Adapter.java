package groots.canbrand.com.groots.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

    public Landing_Adapter(ArrayList<ProductListDocData> productListData, Context context) {
        this.productListData=productListData;
        this.context=context;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
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
            itemView.setOnClickListener(this);
            txtMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Button Clicked!",Toast.LENGTH_SHORT).show();
                }
            });

            txtPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Button Clicked!",Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
    @Override
    public Landing_Adapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context)
                .inflate(R.layout.landing_card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(Landing_Adapter.DataObjectHolder holder, int position) {
        holder.textItemName.setText(productListData.get(position).title);
        holder.textItemdesc.setText(productListData.get(position).description);
        holder.textItemPrice.setText(""+productListData.get(position).storeOfferPrice);
        holder.txtCount.setText(""+productListData.get(position).getItemCount());

      /*  Picasso.with(context).load(productListData.get(position).defaultThumbUrl)
                .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.imgItemIcon);

        Picasso.with(context).load(productListData.get(position).)) .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image).into(childViewHolder.imgItemIcon);
*/

       /* if(position>4) {

            Animation animation = AnimationUtils.loadAnimation(context, (position > -1) ? R.anim.up_from_bottom : R.anim.bottom_from_up);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }*/


        // If the bound /*view wasn't previously displayed on screen, it's animated
        /*if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.pull_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;*/

        if (position > lastPosition) {
            holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
           // Animator animator = AnimatorInflater.loadAnimator(context, R.animator.rotate_animation);
           // animator.setTarget(holder.itemView);
            //animator.start();
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return productListData.size();
    }


    }
