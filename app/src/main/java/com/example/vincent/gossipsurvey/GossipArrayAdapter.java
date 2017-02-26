package com.example.vincent.gossipsurvey;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 2/26/2017.
 */

public class GossipArrayAdapter extends ArrayAdapter {

    private List<String> list;
    private int resource, headerResource;

    public GossipArrayAdapter(Context context, int resource, int headerResource, List<String> list){
        super(context,resource, list);
        this.list = (ArrayList) list;
        this.resource = resource;
        this.headerResource = headerResource;
    }
    public GossipArrayAdapter(Context context, int resource,  List<String> list){
        super(context,resource, list);
        this.list = (ArrayList) list;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        return createViewFromResource(position, convertView, parent, resource);
    }

    public String getItem(int position){
        return list.get(position);
    }

    public View createViewFromResource(int position, View convertView, ViewGroup parent, int resource){
        View view;
        LayoutInflater mInflater = (LayoutInflater)  getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView text, rightText;
        ImageView img;
        if(convertView == null)
            view = mInflater.inflate(resource,parent,false);
        else
            view = convertView;
        switch(resource) {
            case R.layout.list_item:
                text = (TextView) view.findViewById(R.id.nameLabel);
                img = (ImageView) view.findViewById(R.id.profileImage);
                Log.d("setText", "" + getItem(position));
                text.setText(getItem(position));
                return view;
            case R.layout.list_survey_item:
                return view;



        }
        return view;
    }
}
