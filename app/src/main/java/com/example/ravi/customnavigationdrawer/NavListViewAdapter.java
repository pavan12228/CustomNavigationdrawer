package com.example.ravi.customnavigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravi on 01-02-2017.
 */

public class NavListViewAdapter extends BaseAdapter {


    List<Pojo> pojoList=new ArrayList<>();
    LayoutInflater layoutInflater;
    Context mContext;

    public NavListViewAdapter(Context mContext, List<Pojo> pojoList) {
        this.mContext = mContext;
        this.pojoList = pojoList;
    }

    @Override
    public int getCount() {
        return pojoList.size();
    }

    @Override
    public Object getItem(int i) {
        return pojoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

   public static class ViewHolder {
        TextView listLabel;
        ImageView listIcon;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
            if (layoutInflater==null){
                layoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
        if (view == null) {
            view=layoutInflater.inflate(R.layout.list_single_item,null);
        }
              ViewHolder viewHolder=new ViewHolder();
        viewHolder.listLabel= (TextView) view.findViewById(R.id.nav_textview);
         viewHolder.listIcon= (ImageView) view.findViewById(R.id.image);
                          Pojo pojo=  pojoList.get(i);
        viewHolder.listLabel.setText(pojo.getNmae());
         imageLoader.displayImage(pojo.getImage(),viewHolder.listIcon);
        return view;
    }

}
