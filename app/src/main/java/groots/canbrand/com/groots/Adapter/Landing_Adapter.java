package groots.canbrand.com.groots.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import groots.canbrand.com.groots.R;

/**
 * Created by Administrator on 04-04-2016.
 */
public class Landing_Adapter extends RecyclerView.Adapter<Landing_Adapter
        .DataObjectHolder> {

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
    @Override
    public Landing_Adapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(Landing_Adapter.DataObjectHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    }
