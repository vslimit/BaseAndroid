package com.vslimit.baseandroid.app.widgets;

/**
 * Created by vslimit on 15/5/14.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 自定义滑动视图
 * @author Administrator
 *
 */
public class MyScrollLayout extends ViewGroup {
    private VelocityTracker mVelocityTracker;		// 用于判断甩动手势
    private static final int SNAP_VELOCITY = 600;	// 滑动距离
    private Scroller mScroller;						// 滑动控制器
    private int mCurScreen;							// 当前屏幕
    private int mDefaultScreen = 0;					// 默认屏幕
    private float mLastMotionX;

    private OnViewChangeListener mOnViewChangeListener;

    public MyScrollLayout(Context context) {
        super(context);
        init(context);
    }

    public MyScrollLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // 初始化变量
    private void init(Context context) {
        mCurScreen = mDefaultScreen;
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childLeft = 0;
            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View childView = getChildAt(i);	// 得到孩子
                if (childView.getVisibility() != View.GONE) {
                    final int childWidth = childView.getMeasuredWidth();	// 获取view测量的宽度
                    childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
                    childLeft += childWidth;
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
        scrollTo(mCurScreen * width, 0);		// 设置滚动视图的位置
    }

    // 滑动到目标位置
    public void snapToDestination() {
        final int screenWidth = getWidth();
        final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
        snapToScreen(destScreen);
    }

    // 滑动到屏幕
    public void snapToScreen(int whichScreen) {
        // 获得有效的页面
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        if (getScrollX() != (whichScreen * getWidth())) {
            final int delta = whichScreen * getWidth() - getScrollX();
            mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);

            mCurScreen = whichScreen;
            invalidate();		// 重绘布局
            if (mOnViewChangeListener != null) {
                mOnViewChangeListener.OnViewChange(mCurScreen);
            }
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float x = event.getX();

        switch (action) {
            case MotionEvent.ACTION_DOWN:	// 手指按下动作
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();	// 得到一个新的甩动手势
                    mVelocityTracker.addMovement(event);
                }
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (mLastMotionX - x);
                if(IsCanMove(deltaX)) {
                    if(mVelocityTracker != null) {
                        mVelocityTracker.addMovement(event);
                    }
                    mLastMotionX = x;
                    scrollBy(deltaX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:	// 手指抬起
                int velocityX = 0;
                if (mVelocityTracker != null) {
                    mVelocityTracker.addMovement(event);
                    mVelocityTracker.computeCurrentVelocity(1000);
                    velocityX = (int) mVelocityTracker.getXVelocity();
                }
                if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
                    // 往左移动
                    snapToScreen(mCurScreen - 1);
                } else if (velocityX < - SNAP_VELOCITY && mCurScreen < getChildCount() - 1) {
                    // 往右移动
                    snapToScreen(mCurScreen + 1);
                } else {
                    snapToDestination();
                }

                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
        }
        return true;
    }

    /**
     * 判断是否可以移动
     * @param deltaX
     * @return
     */
    private boolean IsCanMove(int deltaX) {
        if (getScrollX() <= 0 && deltaX < 0) {
            return false;
        }
        if (getScrollX() >= (getChildCount() - 1) * getWidth() && deltaX > 0) {
            return false;
        }
        return true;
    }

    public void SetOnViewChangeListener(OnViewChangeListener listener) {
        mOnViewChangeListener = listener;
    }

    // 接口
    public interface OnViewChangeListener {
        public void OnViewChange(int View);
    }

}

