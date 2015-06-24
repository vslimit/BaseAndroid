package com.vslimit.baseandroid.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.vslimit.baseandroid.app.R;
import com.vslimit.baseandroid.app.widgets.CustomProgressDialog;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vslimit on 15/4/25.
 */
public class BaseArrayAdapter<T> extends ArrayAdapter {

    protected Context context;
    protected CustomProgressDialog pd;

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    protected ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    //配置图片加载及显示选项
    DisplayImageOptions options;


    public BaseArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    public BaseArrayAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public BaseArrayAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    public BaseArrayAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public BaseArrayAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);

//        options = new DisplayImageOptions.Builder()
//                .showStubImage(R.drawable.ic_launcher)    //在ImageView加载过程中显示图片
//                .showImageForEmptyUri(R.drawable.ic_launcher)  //image连接地址为空时
//                .showImageOnFail(R.drawable.ic_launcher)  //image加载失败
//                .cacheInMemory(true)  //加载图片时会在内存中加载缓存
//                .cacheOnDisc(true)   //加载图片时会在磁盘中加载缓存
//                .bitmapConfig(Bitmap.Config.ARGB_8888)
//                .displayer(new RoundedBitmapDisplayer(5))  //设置用户加载图片task(这里是圆角图片显示)
//                .build();

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public BaseArrayAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    protected String linkUrl(int typeId, int resId) {
        return context.getString(R.string.http) + context.getString(typeId) + context.getString(resId);
    }

    protected String linkUrl(int resId) {
        return linkUrl(R.string.api, resId);
    }

    protected String linkH5Url(int resId) {
        return linkUrl(R.string.h5, resId);
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
