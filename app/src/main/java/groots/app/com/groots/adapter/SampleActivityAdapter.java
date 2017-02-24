package groots.app.com.groots.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import groots.app.com.groots.R;
import groots.app.com.groots.databases.DbHelper;
import groots.app.com.groots.pojo.Items;
import groots.app.com.groots.pojo.Product;

/**
 * Created by aakash on 8/2/17.
 */

public class SampleActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Items> sampleProducts;
    Context context;
    boolean show_footer;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOTER = 2;
    View view ;


    public SampleActivityAdapter(ArrayList<Items> sampleProducts,Context context , boolean flag){

        this.context = context;
        this.sampleProducts = sampleProducts;
        this.show_footer = flag;


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
        TextView textItemPriceMin,textItemPriceMax;
        TextView textRup;
        ImageView imgItemIcon;

        public DataObjectHolder(final View itemView ){

            super(itemView);
            textItemName = (TextView) itemView.findViewById(R.id.textItemName);
            textItemdesc = (TextView) itemView.findViewById(R.id.textItemdesc);
            textItemPriceMin = (TextView) itemView.findViewById(R.id.textItemPriceMin);
            textItemPriceMax = (TextView) itemView.findViewById(R.id.textItemPriceMax);
            textRup = (TextView) itemView.findViewById(R.id.textRupee);
            imgItemIcon = (ImageView) itemView.findViewById(R.id.imgItemIcon);

        }

        public void clearAnimation() {
            view.clearAnimation();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = null;
        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.sample_card_view, parent, false);
            SampleActivityAdapter.DataObjectHolder dataObjectHolder = new SampleActivityAdapter.DataObjectHolder(view);
            return dataObjectHolder;
        }
        if (viewType == TYPE_FOOTER) {
            View v = null;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pregress_bar, parent, false);
            return new SampleActivityAdapter.FooterViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mholder, final int position) {
        if (mholder instanceof SampleActivityAdapter.DataObjectHolder) {
            final SampleActivityAdapter.DataObjectHolder holder = (SampleActivityAdapter.DataObjectHolder) mholder;
            final DbHelper dbHelper = DbHelper.getInstance(context);
            dbHelper.createDb(false);


           // BaseActivity.imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseCont‌​ext()));




            holder.textItemName.setText(sampleProducts.get(position).title);
            holder.textItemdesc.setText(sampleProducts.get(position).description);
            holder.textItemPriceMin.setText(sampleProducts.get(position).minPrice.toString());
            holder.textItemPriceMax.setText(sampleProducts.get(position).maxPrice.toString());
            if (!sampleProducts.get(position).thumbUrl.equals(null)) {
           /* Picasso.with(context).load(orderItems.get(position).thumbUrl.get(0))
                    .into(holder.imgItemIcon);*/


                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.init(ImageLoaderConfiguration.createDefault(context));
                //ImageLoader imageLoader = ImageLoader.getInstance();

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
                imageLoader.displayImage(sampleProducts.get(position).thumbUrl.get(0), holder.imgItemIcon, options);
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
            return sampleProducts.size() + 1; //+1 is for the footer as it's an extra item
        } else {
            return sampleProducts.size();
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
        return position == sampleProducts.size();
    }

    public void hideFooter() {
        show_footer = false;
    }

    public void setShow_footer() {
        show_footer = true;

    }

}
