package com.zt.moretyperv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zt.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangteng on 2017/12/8.
 */

public class MoreTypeAdapter extends RecyclerView.Adapter {

    public static final int TYPE_ONE = 0;
    public static final int TYPE_TWO = 1;
    public static final int TYPE_THREE = 2;

    private List<MoreTypeBean> list;

    public MoreTypeAdapter(List<MoreTypeBean> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        int type = list.get(position).getType();
        switch (type) {
            case TYPE_ONE:
                return TYPE_ONE;
            case TYPE_TWO:
                return TYPE_TWO;
            default:
                return TYPE_THREE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one, null);
            return new ViewHolderOne(view);
        } else if (viewType == TYPE_TWO) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_two, null);
            return new ViewHolderTwo(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_three, null);
            return new ViewHolderThree(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        @Bind(R.id.titleOne)
        TextView title;

        public ViewHolderOne(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }

    static class ViewHolderTwo extends RecyclerView.ViewHolder {
        @Bind(R.id.titleTwo)
        TextView title;

        public ViewHolderTwo(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }

    static class ViewHolderThree extends RecyclerView.ViewHolder {
        @Bind(R.id.titleThree)
        TextView title;

        public ViewHolderThree(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }

}
