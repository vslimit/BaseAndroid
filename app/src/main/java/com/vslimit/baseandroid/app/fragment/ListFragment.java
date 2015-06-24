package com.vslimit.baseandroid.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.vslimit.baseandroid.app.R;
import com.vslimit.baseandroid.app.activity.web.CommonWebViewActivity;
import com.vslimit.baseandroid.app.adapter.ListArrayAdapter;
import com.vslimit.baseandroid.app.model.ListModel;
import com.vslimit.baseandroid.app.model.Market;
import com.vslimit.baseandroid.app.model.MarketListModelResult;
import com.vslimit.baseandroid.app.utils.FastJsonRequest;
import com.vslimit.baseandroid.app.widgets.CustomProgressDialog;

import java.util.ArrayList;

/**
 * Created by vslimit on 15/3/8.
 */
public class ListFragment extends BaseFragment {


    private PullToRefreshListView myListView;

    private ArrayList<Market> list = new ArrayList<Market>();
    private ListArrayAdapter adapter;

    private Integer currentPage = 1;
    private Integer pageSize = 1;
    private Integer count = 0;
    private ListModel<Market> model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, null);

        pd = CustomProgressDialog.createDialog(getActivity());
        myListView = (PullToRefreshListView) v.findViewById(R.id.list);

        myListView.setMode(PullToRefreshBase.Mode.BOTH);

//        new GetDataTask().execute(url);
        adapter = new ListArrayAdapter(getActivity(), list);
        initData();
        Log.d("List.size", String.valueOf(list.size()));
        myListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            // 下拉Pulling Down
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // 下拉的时候数据重置
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                currentPage = 1;
                list.clear();
                initData();
            }

            // 上拉Pulling Up
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉的时候添加选项
                currentPage++;
                initData();
            }

        });

        myListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent intent = new Intent(getActivity(), CommonWebViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", (String) view.getTag());
                        bundle.putString("title", "test");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

        // Add an end-of-list listener
        myListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
//                new GetDataTask().execute(url);
                Log.d("COUNT::::", count.toString());
                Toast.makeText(getActivity(), R.string.end, Toast.LENGTH_SHORT).show();
            }
        });

        ListView actualListView = myListView.getRefreshableView();
        registerForContextMenu(actualListView);
        actualListView.setAdapter(adapter);
        return v;
    }


    /* (non-Javadoc)
     * @see android.app.Fragment#onDestroy()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /* (non-Javadoc)
     * @see android.app.Fragment#onLowMemory()
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /* (non-Javadoc)
     * @see android.app.Fragment#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    private void initData() {
        String url = linkUrl(R.string.c2c_list_url);
        url += ("?page=" + currentPage + "&page_size=" + pageSize);

        Log.d("URL:::", url);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        handler.sendEmptyMessage(SHOW);
        Log.d("URL:::", url);

        FastJsonRequest<MarketListModelResult> fastJson = new FastJsonRequest<MarketListModelResult>(url, MarketListModelResult.class,
                new Response.Listener<MarketListModelResult>() {
                    public void onResponse(MarketListModelResult result) {
                        if (0 == result.getError_code()) {
                            model = result.getData();
                            list.addAll(model.getList());
                            count = result.getData().getTotal();
                            adapter.notifyDataSetChanged();
                            myListView.onRefreshComplete();
                        } else {
                            Toast.makeText(getActivity(), result.getTip(), Toast.LENGTH_LONG).show();
                        }

                        handler.sendEmptyMessage(HIDE);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError arg0) {
            }
        });
        requestQueue.add(fastJson);
    }


}
