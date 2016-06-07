package lucidace.com.merchandising.adapters.products;

/**
 * Created by kraiba on 17/03/16.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import lucidace.com.merchandising.R;
import lucidace.com.merchandising.models.products.Product;


public class SurveyResultsAdapter extends RecyclerView.Adapter<SurveyResultsAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<Product> mData;

    private String type;
    public void add(Product a, int position) {
        position = position == -1 ? getItemCount() : position;
        mData.add(position, a);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        if (position < getItemCount()) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final View mView;
        private final TextView textView1;
        private final TextView textView2;

        public SimpleViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.name);
            textView1 = (TextView) view.findViewById(R.id.textView);
            textView2 = (TextView) view.findViewById(R.id.textView2);

        }
    }

    public SurveyResultsAdapter(Context context, List<Product> data,String type) {
        this.type=type;
        mContext = context;
        if (data != null)
            mData = new ArrayList<Product>(data);
        else mData = new ArrayList<Product>();
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_survery_result, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        holder.name.setText(mData.get(position).getProduct_name());
        if(type.equals("competitor")){
            holder.textView1.setText(mData.get(position).getCompetitorA());
            holder.textView2.setText(mData.get(position).getCompetitorB());
        }
        else{
            holder.textView1.setText(mData.get(position).getSos());
            holder.textView2.setText(mData.get(position).getPrice());
        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}