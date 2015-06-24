package com.vslimit.baseandroid.app.utils;

import android.widget.ImageView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vslimit.baseandroid.app.MainApplication;
import com.vslimit.baseandroid.app.R;

/**
 * Created by vslimit on 15/3/2.
 */
public class VolleyTools {

//    public static String toString(String url, Context context) {
//        final String tag_string_req = "string_req";
//        final CustomProgressDialog dialog = CustomProgressDialog.createDialog(context);
////        String ret = null;
//        dialog.setMessage("正在加载中...");
//        dialog.show();
//        String ret = null;
//        StringRequest strReq = new StringRequest(Request.Method.GET,
//                url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                dialog.dismiss();
//                ret = response;
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                dialog.dismiss();
//            }
//        });
//
//        YzApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
//    }

    public static void loadNetworkImageView(String imageUrl, NetworkImageView networkImageView) {
        ImageLoader imageLoader = MainApplication.getInstance().getImageLoader();
        networkImageView.setTag("url");
        networkImageView.setImageUrl(imageUrl, imageLoader);
    }

    public static void loadImageView(String imageUrl, ImageView imageView) {
        if (!StringUtil.blank(imageUrl)) {
            ImageLoader imageLoader = MainApplication.getInstance().getImageLoader();
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                    R.drawable.ic_launcher, R.drawable.ic_launcher);
            imageLoader.get(imageUrl, listener);
        }
    }

     public static void loadImageView(String imageUrl, ImageView imageView,int resId) {
        ImageLoader imageLoader = MainApplication.getInstance().getImageLoader();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                resId, resId);
        imageLoader.get(imageUrl, listener);
    }


}
