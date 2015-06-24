package com.vslimit.baseandroid.app.activity.web;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.vslimit.baseandroid.app.R;
import com.vslimit.baseandroid.app.activity.BaseActivity;
import com.vslimit.baseandroid.app.utils.StringUtil;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_web_view)
public class CommonWebViewActivity extends BaseActivity {

    /**
     * Called when the activity is first created.
     */
    @InjectView(R.id.webView)
    WebView wv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        String url = getIntent().getExtras().getString("url");
        if(StringUtil.blank(url)) {
            url = linkH5Url(R.string.media_url);
        }
        String title = getIntent().getExtras().getString("title");
        if (!StringUtil.blank(title)) {
            getActionBar().setTitle(title);
        }
//        String url = linkH5Url(R.string.test_url);
        init();
        loadurl(wv, url);
    }

    public void init() {//初始化
        wv.getSettings().setJavaScriptEnabled(true);//可用JS
        wv.addJavascriptInterface(new AndroidBridge(), "yl");
        wv.setScrollBarStyle(0);//滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                loadurl(view, url);//载入网页
                return true;
            }//重写点击动作,用webview载入

        });
        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {//载入进度改变而触发
                if (progress == 100) {
                    handler.sendEmptyMessage(HIDE);//如果全部载入,隐藏进度对话框
                }
                super.onProgressChanged(view, progress);
            }
        });

        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setLoadWithOverviewMode(true);

    }

    public void loadurl(final WebView view, final String url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(SHOW);
                view.loadUrl(url);//载入网页
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_guide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
//            case R.id.daohang:
//                Intent intent = new Intent(CommonWebViewActivity.this, MainActivity.class);
//                startActivity(intent);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class AndroidBridge {
        @JavascriptInterface
        public void toast(final String ret) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CommonWebViewActivity.this, ret, Toast.LENGTH_LONG).show();
                }
            });
        }

        @JavascriptInterface
        public void title(final String title) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getActionBar().setTitle(title);
                }
            });
        }

        @JavascriptInterface
        public void accessToken(final String accessToken) {
            handler.post(new Runnable() {
                @Override
                public void run() {
//                    PreferenceUtils.savePreference(PreferenceUtils.ACCESS_TOKEN, accessToken);
                }
            });
        }

        @JavascriptInterface
        public void transActivity(final String title,final String url) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(CommonWebViewActivity.this, CommonWebViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    bundle.putString("title", title);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }

}
