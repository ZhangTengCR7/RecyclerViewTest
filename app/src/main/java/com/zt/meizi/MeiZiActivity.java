package com.zt.meizi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.itheima.loopviewpager.LoopViewPager;
import com.itheima.loopviewpager.listener.OnItemClickListener;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.RequestMethod;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zt.BaseActivity;
import com.zt.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by zhangteng on 2017/12/15.
 */

public class MeiZiActivity extends BaseActivity {

    @Bind(R.id.meiziRecyclerView)
    RecyclerView meiziRecyclerView;
    @Bind(R.id.meiRefreshLayout)
    SmartRefreshLayout meiRefreshLayout;
    @Bind(R.id.floatBtn)
    FloatingActionButton actionButton;
    private MeiziAdapter meiziAdapter;
    private List<Meizi> list = new ArrayList<>();
    private int page = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi);
        ButterKnife.bind(this);
        initView();
        addHeadView();
    }

    private void addHeadView() {
        View view = getLayoutInflater().inflate(R.layout.headview, null);
        LoopViewPager loopViewPager = (LoopViewPager) view.findViewById(R.id.loopViewPager);
        loopViewPager.setImgData(imgListString());
        loopViewPager.setTitleData(titleListString());
        loopViewPager.setImgLength(imgListString().size());
        loopViewPager.start();

        loopViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Toast.makeText(MeiZiActivity.this, i % 5 + "", Toast.LENGTH_SHORT).show();

            }
        });
        meiziAdapter.setHeadView(view);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meiziRecyclerView.scrollToPosition(0);
            }
        });
    }

    private void initView() {
        meiziRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        meiziAdapter = new MeiziAdapter(list, this);
        meiziRecyclerView.setAdapter(meiziAdapter);
        meiRefreshLayout.autoRefresh();
        meiRefreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        meiRefreshLayout.setEnableAutoLoadmore(true);

        meiRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                requestData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                list.clear();
                page = 0;
                requestData();
            }
        });
    }

    private void requestData() {
        Request request = ItheimaHttp.newRequest("福利/10/" + page, RequestMethod.GET);
        ItheimaHttp.send(request, new HttpResponseListener<String>() {
            @Override
            public void onResponse(String s) {
                meiRefreshLayout.finishRefresh();
                meiRefreshLayout.finishLoadmore();
                parseData(s);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                super.onFailure(call, e);
                meiRefreshLayout.finishRefresh();
                meiRefreshLayout.finishLoadmore();
            }
        });
    }

    private void parseData(String s) {
        String jsonData = null;
        try {
            JSONObject jsonObject = new JSONObject(s);
            jsonData = jsonObject.getString("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<Meizi> tempList = GsonUtils.fromJson(jsonData, new TypeToken<List<Meizi>>() {
        }.getType());
        for (Meizi meizi : tempList) {
            meizi.setMsgNum(new Random().nextInt(100));
        }
        list.addAll(tempList);
        meiziAdapter.notifyDataSetChanged();
        page++;
    }

    private List<String> imgListString() {
        List<String> imageData = new ArrayList<>();
        imageData.add("http://d.hiphotos.baidu.com/image/h%3D200/sign=72b32dc4b719ebc4df787199b227cf79/58ee3d6d55fbb2fb48944ab34b4a20a44723dcd7.jpg");
        imageData.add("http://pic.4j4j.cn/upload/pic/20130815/31e652fe2d.jpg");
        imageData.add("http://pic.4j4j.cn/upload/pic/20130815/5e604404fe.jpg");
        imageData.add("http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg");
        imageData.add("http://d.hiphotos.baidu.com/image/pic/item/54fbb2fb43166d22dc28839a442309f79052d265.jpg");
        return imageData;
    }

    private List<String> titleListString() {
        List<String> titleData = new ArrayList<>();
        titleData.add("1、在这里等着你");
        titleData.add("2、在你身边");
        titleData.add("3、打电话给你就是想说声“嗨”");
        titleData.add("4、不介意你对我大喊大叫");
        titleData.add("5、期待你总是尽全力");
        return titleData;
    }
}
