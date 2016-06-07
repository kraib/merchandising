package lucidace.com.merchandising.adapters.branches;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import lucidace.com.merchandising.R;
import lucidace.com.merchandising.app.EndPoints;
import lucidace.com.merchandising.models.branches.Branch;
import lucidace.com.merchandising.models.branches.SuperMarket;

import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class BrandsListAdapter extends BaseAdapter {

    private String TAG = BrandsListAdapter.class.getSimpleName();
    private List<Branch> objects = new ArrayList<Branch>();

    private Context context;
    private LayoutInflater layoutInflater;

    public BrandsListAdapter(Context context, List<Branch> objects) {
        this.context = context;
        this.objects=objects;
        this.layoutInflater = LayoutInflater.from(context);
        Log.i(TAG,"Size"+objects.size());
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Branch getItem(int position) {
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
        initializeViews((Branch)getItem(position), (ViewHolder) convertView.getTag(),parent.getContext());
        return convertView;
    }

    private void initializeViews(final Branch object, final ViewHolder holder, final Context context) {
        holder.title.setText(object.getBranch_name());
        holder.description.setText(object.getDescription());
        try {
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

//        holder.image.setImageResource(object.getImage());
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
