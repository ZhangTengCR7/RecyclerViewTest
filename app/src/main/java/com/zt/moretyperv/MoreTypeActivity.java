package com.zt.moretyperv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zt.BaseActivity;
import com.zt.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangteng on 2017/12/8.
 */

public class MoreTypeActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {

    @Bind(R.id.toolBar)
    Toolbar toolbar;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<MoreTypeBean> list = new ArrayList<>();
    private MoreTypeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moretype);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle("列表");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //initData();
        adapter = new MoreTypeAdapter(list);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        refreshLayout.setEnableAutoLoadmore(true);
        refreshLayout.autoRefresh();//自动刷新
    }

    private void initData() {
        MoreTypeBean bean;
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            bean = new MoreTypeBean(random.nextInt(3), "" + i);
            list.add(bean);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        list.clear();
        initData();
        adapter.notifyDataSetChanged();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        initData();
        adapter.notifyDataSetChanged();
        refreshlayout.finishLoadmore();
    }
}
