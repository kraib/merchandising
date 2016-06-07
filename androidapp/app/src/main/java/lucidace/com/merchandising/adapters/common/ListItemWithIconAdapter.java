package lucidace.com.merchandising.adapters.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import lucidace.com.merchandising.R;
import lucidace.com.merchandising.models.common.BasicListItem;

public class ListItemWithIconAdapter extends BaseAdapter {

    private List<BasicListItem> objects = new ArrayList<BasicListItem>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ListItemWithIconAdapter(Context context, List<BasicListItem> objects) {
        this.context = context;
        this.objects=objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public BasicListItem getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_with_icon, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((BasicListItem)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(BasicListItem object, ViewHolder holder) {
        //TODO implement
        holder.title.setText(object.getTitle());
        holder.listImageValue.setImageResource(object.getImageResource());
        holder.description.setText(object.getDescription());
    }

    protected class ViewHolder {

    private ImageView listImageValue;
    private TextView title;
    private TextView description;

        public ViewHolder(View view) {

            listImageValue = (ImageView) view.findViewById(R.id.list_image_value);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
        }
    }
}
