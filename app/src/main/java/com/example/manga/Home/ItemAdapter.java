package com.example.manga.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manga.R;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<Item> itemList;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_layout, parent, false);
        }

        Item item = itemList.get(position);

        ImageView itemImage = convertView.findViewById(R.id.item_image);
        TextView itemText = convertView.findViewById(R.id.item_title);

        itemImage.setImageResource(item.getImageResId());
        itemText.setText(item.getName());

        return convertView;
    }
}
