package com.vslimit.baseandroid.app.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.vslimit.baseandroid.app.R;
import com.vslimit.baseandroid.app.fragment.ListFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vslimit on 15/5/14.
 */
public class BaseTabActivity extends BaseActivity {

    GridView toolbarGrid;
    //    WebView wv;
//    MyWebviewFragment wvf;
//    BadgeView badge;

    int[] menu_toolbar_image_array = {R.drawable.active,
            R.drawable.message,
            R.drawable.contacts, R.drawable.follow,
            R.drawable.more};
    //    int[] init_menu_toolbar_image_array = {R.drawable.active_select,
//            R.drawable.message,
//            R.drawable.contacts, R.drawable.follow,
//            R.drawable.more};
    int[] menu_toolbar_select_image_array = {R.drawable.active_select,
            R.drawable.message_select,
            R.drawable.contacts_select, R.drawable.follow_select,
            R.drawable.more_select};

    //    int[] url_array = {
//            R.string.active_url,
//            R.string.message_url,
//            R.string.contact_url,
//            R.string.follow_url,
//            R.string.more_url
//    };
    Fragment[] fragment_array = {
            new ListFragment(),
            new ListFragment(),
            new ListFragment(),
            new ListFragment(),
            new ListFragment()
    };


    String[] menu_toolbar_name_array = new String[5];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);
        init();
//        initBadge(toolbarGrid);
//        WindowManager wm = this.getWindowManager();
//
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        FrameLayout fm = (FrameLayout)findViewById(R.id.fragment_container);
//        fm.set
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fm.getLayoutParams();
//        params.height -= toolbarGrid.getHeight();
//        fm.setLayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,height - toolbarGrid.getHeight());
//        toolbarGrid.getHeight();
//        fm.setLayoutParams(params);
        changeFragment(new ListFragment());
    }

    private void changeFragment(Fragment f) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, f);
        transaction.commitAllowingStateLoss();
    }

    private void init() {
        menu_toolbar_name_array[0] = getResources().getString(
                R.string.active);
        menu_toolbar_name_array[1] = getResources().getString(
                R.string.message);
        menu_toolbar_name_array[2] = getResources().getString(
                R.string.contact);
        menu_toolbar_name_array[3] = getResources().getString(
                R.string.follow);
        menu_toolbar_name_array[4] = getResources().getString(
                R.string.more);

        toolbarGrid = (GridView) findViewById(R.id.GridView_toolbar);
        toolbarGrid.getBackground().setAlpha(200);

        toolbarGrid.setNumColumns(5);
        toolbarGrid.setGravity(Gravity.CENTER);
        toolbarGrid.setVerticalSpacing(10);
        toolbarGrid.setHorizontalSpacing(10);
        toolbarGrid.setAdapter(getMenuAdapter(menu_toolbar_name_array,
                menu_toolbar_image_array));
        toolbarGrid.setSelection(1);
        toolbarGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
//                initBadge(toolbarGrid);
                initMenu(toolbarGrid);
            }
        }, 500);
        toolbarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
//                wvf.updateUrl(linkUrl(url_array[arg2]));
                changeFragment(fragment_array[arg2]);
                setMenuSelect(arg1);
            }
        });
    }

//    private void initBadge(GridView gridView) {
//        View view = gridView.getChildAt(1);
//        TextView bt = (TextView) view.findViewById(R.id.txtCount);
//        bt.setVisibility(View.VISIBLE);
//
//        ImageView image = (ImageView) view.findViewById(R.id.item_image);
//        TextView tv = (TextView) view.findViewById(R.id.item_text);
//    }

    private void initMenu(GridView gridView) {
        View view = gridView.getChildAt(0);
        ImageView image = (ImageView) view.findViewById(R.id.item_image);
        TextView tv = (TextView) view.findViewById(R.id.item_text);
        image.setImageResource(menu_toolbar_select_image_array[0]);
//        tv.setTextColor(R.color.bottom_blue);
        tv.setTextColor(getResources().getColor(R.color.bottom_blue));

    }

    private void setMenuSelect(View selectView) {
        for (int i = 0; i < toolbarGrid.getChildCount(); i++) {
            View view = toolbarGrid.getChildAt(i);
            ImageView image = (ImageView) view.findViewById(R.id.item_image);
            TextView tv = (TextView) view.findViewById(R.id.item_text);

            if (view.equals(selectView)) {
                image.setImageResource(menu_toolbar_select_image_array[i]);
                tv.setTextColor(getResources().getColor(R.color.bottom_blue));
            } else {
                image.setImageResource(menu_toolbar_image_array[i]);
                tv.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    /**
     * Adapter
     *
     * @param menuNameArray
     * @param imageResourceArray
     * @return SimpleAdapter
     */
    private SimpleAdapter getMenuAdapter(String[] menuNameArray,
                                         int[] imageResourceArray) {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < menuNameArray.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", imageResourceArray[i]);
            map.put("itemText", menuNameArray[i]);
            data.add(map);
        }
        SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
                R.layout.tab_item_menu, new String[]{"itemImage", "itemText"},
                new int[]{R.id.item_image, R.id.item_text});
        return simperAdapter;
    }

}
