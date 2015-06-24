package com.vslimit.baseandroid.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.vslimit.baseandroid.app.R;
import com.vslimit.baseandroid.app.model.Images;
import com.vslimit.baseandroid.app.model.Market;
import com.vslimit.baseandroid.app.utils.DTFormatUtil;
import com.vslimit.baseandroid.app.utils.VolleyTools;
import com.vslimit.baseandroid.app.widgets.CustomProgressDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vslimit on 15/3/4.
 */
public class ListArrayAdapter extends BaseArrayAdapter<Market> {

    private final Context context;
    private final ArrayList<Market> modelsArrayList;
    private List<Images> imagesList;
    private CustomProgressDialog pd;

    public ListArrayAdapter(Context context, ArrayList<Market> modelsArrayList) {
        super(context, R.layout.list_item, modelsArrayList);
        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView communtiyNameView = (TextView) rowView.findViewById(R.id.community_name);
        TextView titleView = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.profile_image);
        TextView distanceView = (TextView) rowView.findViewById(R.id.distance);
        TextView flagView = (TextView) rowView.findViewById(R.id.flag);
        TextView dateView = (TextView) rowView.findViewById(R.id.date);
        TextView userNameView = (TextView) rowView.findViewById(R.id.name);
        TextView priceView = (TextView) rowView.findViewById(R.id.price);


        Market market = modelsArrayList.get(position);

        flagView.setVisibility(market.isCan_barter() ? View.VISIBLE : View.GONE);

        imagesList = market.getImages();
        GridView gridView = (GridView) rowView.findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(context));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "test", Toast.LENGTH_LONG).show();
                String url = (String) view.getTag();
                showPopUp(view, url);
            }
        });

//        VolleyTools.loadImageView(market.getAvatar(), imageView);
        imageLoader.displayImage(market.getAvatar(), imageView, options, animateFirstListener);

        communtiyNameView.setText("小区:" + market.getCommunity_name());
        titleView.setText(market.getTitle());
//        distanceView.setText("距离"+ StringUtil.gps2km(lat,lng,market.getLat(),market.getLng())+"公里");
        userNameView.setText(market.getNickname());
        dateView.setText(DTFormatUtil.time2Str(market.getCreate_time()));
        priceView.setText("￥" + market.getPrice() + "元");

        rowView.setTag(market.getUrl());
        return rowView;
    }

    private class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            Log.d("count:::", String.valueOf(imagesList.size()));
            return imagesList.size();
        }

        @Override
        public Object getItem(int position) {
            return imagesList.get(position).getSmall();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //定义一个ImageView,显示在GridView里
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setFocusable(false);
                imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;
            }
//            VolleyTools.loadImageView(imagesList.get(position).getSmall(), imageView);
            imageLoader.displayImage(imagesList.get(position).getSmall(), imageView, options, animateFirstListener);
            imageView.setTag(imagesList.get(position).getOriginal());
            return imageView;
        }
    }

    private PopupWindow popupWindow;

    private void showPopUp(View v, String url) {
        final CustomProgressDialog pd = CustomProgressDialog.createDialog(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popView = inflater.inflate(R.layout.image_pop, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ImageView imageView = (ImageView) popView.findViewById(R.id.img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        pd.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
            }
        }, 1000 * 5);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        ImageLoader.getInstance().displayImage(url, imageView, options, animateFirstListener);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }




}
