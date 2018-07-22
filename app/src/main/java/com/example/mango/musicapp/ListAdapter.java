package com.example.mango.musicapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<List> {
    public ListAdapter(Context context, int resource, java.util.List<List> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        List temp = getItem(position);
        if(listView == null)
        {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.layout_list, parent, false);
        }

        TextView listTitle = (TextView) listView.findViewById(R.id.listTitle);
        TextView listSubTitle = (TextView) listView.findViewById(R.id.listSubTitle);
        ImageView listImageLeft = (ImageView) listView.findViewById(R.id.listImageLeft);
        ImageView listImageRight = (ImageView) listView.findViewById(R.id.listImageRight);


        listTitle.setText(temp.getListTile());
        listSubTitle.setText(temp.getListSubTitle());
        listImageRight.setImageResource(temp.getListImageResourceRight());
        listImageLeft.setImageResource(temp.getListImageResourceLeft());

        return listView;
    }
}
