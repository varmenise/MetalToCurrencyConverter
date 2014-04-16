package com.valentina.converter_metals.app;

import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import java.util.List;
import android.content.Context;
import android.app.Activity;
import android.widget.Spinner;




/**
 * Created by valentinaarmenise on 04/04/2014.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    List<MetalObject> rowItems;

    public CustomAdapter(Context context, List<MetalObject> items) {
        this.context = context;
        this.rowItems = items;
    }

    public CustomAdapter() {

    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.text);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        MetalObject rowItem = (MetalObject) getItem(position);

        holder.txtDesc.setText(rowItem.getMetal());
        holder.txtTitle.setText(String.valueOf(rowItem.getCurrencyValue()));
        holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}
