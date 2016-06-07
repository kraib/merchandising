package lucidace.com.merchandising.adapters.products;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lucidace.com.merchandising.R;
import lucidace.com.merchandising.app.EndPoints;
import lucidace.com.merchandising.models.products.ProductCategory;

public class ProductCategoryListAdapter extends BaseAdapter {

    private static final String TAG = ProductCategoryListAdapter.class.getSimpleName();
    private List<ProductCategory> objects = new ArrayList<ProductCategory>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ProductCategoryListAdapter(Context context, List<ProductCategory> objects) {
        this.context = context;
        this.objects=objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ProductCategory getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_with_circular_image, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ProductCategory)getItem(position), (ViewHolder) convertView.getTag(),parent.getContext());
        return convertView;
    }

    private void initializeViews(final ProductCategory object, final ViewHolder holder, final Context context) {
        holder.title.setText(object.getCategory_name());
        holder.description.setText(object.getDescription());
        try {
            Log.i(TAG,EndPoints.BASE_URL+object.getPhoto());
            Picasso.with(context)
                    .load(object.getPhoto())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.image, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Log.v("Picasso","Could not fetch image");
                            //Try again online if cache failed
                            Picasso.with(context)
                                    .load(EndPoints.BASE_URL+object.getPhoto())

                                    .into(holder.image, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso","Could not fetch image");
                                        }
                                    });
                        }
                    });


        }catch (NullPointerException e){

        }
//        holder.image.setImageResource(object.getPhoto());
    }

    protected class ViewHolder {

    private CircleImageView image;
    private TextView title;
    private TextView description;

        public ViewHolder(View view) {

            image = (CircleImageView) view.findViewById(R.id.image);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
        }
    }
}
