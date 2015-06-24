package com.vslimit.baseandroid.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vslimit.baseandroid.app.R;
import com.vslimit.baseandroid.app.widgets.CustomProgressDialog;

/**
 * Created by vslimit on 15/4/5.
 */
public class BaseFragment extends Fragment {
    protected String linkUrl(int typeId, int resId) {
        return getString(R.string.http) + getString(typeId) + getString(resId);
    }

    protected ImageLoader imageLoader = ImageLoader.getInstance();


    protected String linkUrl(int resId) {
        return linkUrl(R.string.api, resId);
    }

    protected String linkH5Url(int resId) {
        return linkUrl(R.string.h5, resId);
    }

    protected final int SHOW = 0;
    protected final int HIDE = 1;
    CustomProgressDialog pd;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {//定义一个Handler，用于处理下载线程与UI间通讯
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case SHOW:
                        pd.show();//显示进度对话框
                        break;
                    case HIDE:
                        pd.hide();//隐藏进度对话框，不可使用dismiss()、cancel(),否则再次调用show()时，显示的对话框小圆圈不会动。
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != pd) {
            pd.dismiss();
        }
    }
}
