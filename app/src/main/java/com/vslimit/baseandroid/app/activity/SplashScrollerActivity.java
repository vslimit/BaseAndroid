package com.vslimit.baseandroid.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.vslimit.baseandroid.app.R;
import com.vslimit.baseandroid.app.utils.PreferenceUtils;
import com.vslimit.baseandroid.app.widgets.MyScrollLayout;

public class SplashScrollerActivity extends BaseActivity implements View.OnClickListener {
    private MyScrollLayout mScrollLayout;		// 滑动视图
    private ImageView[] mImageViews;			// 点图片
    private int mViewCount;						// 视图个数
    private int currentPosition = 0;			// 当前位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_scroller);
        findViews();
        init();
    }

    private void findViews() {
        mScrollLayout = (MyScrollLayout) findViewById(R.id.ScrollLayout);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llayout);
        mViewCount = mScrollLayout.getChildCount();
        mImageViews = new ImageView[mViewCount - 1]; // 最后一个view是黑屏过度，所以- 1
        for (int i = 0; i < (mViewCount - 1); i++) {
            mImageViews[i] = (ImageView) linearLayout.getChildAt(i);
            mImageViews[i].setEnabled(true);
            mImageViews[i].setTag(i);
            mImageViews[i].setOnClickListener(this);
        }
    }

    private void init() {
        mImageViews[currentPosition].setEnabled(false);
        mScrollLayout.SetOnViewChangeListener(new MyScrollLayout.OnViewChangeListener() {

            @Override
            public void OnViewChange(int index) {
                if (index == mViewCount - 1) {
                    // 记录滚屏已经播放过，以后不再播放
                    PreferenceUtils.set(SplashScrollerActivity.this, PreferenceUtils.FUNCTION_SCROLLER_PLAYED, true);
                    startActivity(new Intent(SplashScrollerActivity.this, BaseTabActivity.class));
                    finish();
                }
                setCurPoint(index);
            }
        });
    }

    /**
     * 设置位置显示
     * @param index
     */
    private void setCurPoint(int index) {
        if (index < 0 || index > mViewCount - 2 || currentPosition == index) {
            return;
        }
        mImageViews[currentPosition].setEnabled(true);
        mImageViews[index].setEnabled(false);
        currentPosition = index;
    }

    @Override
    public void onClick(View v) {
        int pos = (Integer) (v.getTag());
        setCurPoint(pos);
        mScrollLayout.snapToScreen(pos);
    }

}