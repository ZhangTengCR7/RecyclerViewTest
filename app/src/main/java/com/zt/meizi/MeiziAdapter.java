package com.zt.meizi;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zt.R;

import java.util.List;

/**
 * Created by zhangteng on 2017/12/15.
 */

public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.ViewHolder> {

    private final int NORMAL = 0;
    private final int HEAD = 1;
    private List<Meizi> list;
    private Context context;
    private View headView;

    public MeiziAdapter(List<Meizi> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setHeadView(View headView) {
        this.headView = headView;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == HEAD
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (headView == null) {
            return NORMAL;
        }
        if (position == 0) {
            return HEAD;
        } else {
            return NORMAL;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        if (viewType == HEAD) {
            holder = new ViewHolder(headView);
        } else {
            holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizi, parent, false));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == HEAD) {
            return;
        } else {
            if (headView == null) {
                GlideUtils.loadFitCenter(context, list.get(position).getUrl(), holder.iv, R.drawable.placeholder);
            } else {
                GlideUtils.loadFitCenter(context, list.get(position - 1).getUrl(), holder.iv, R.drawable.placeholder);
            }

        }


    }

    @Override
    public int getItemCount() {
        return headView != null ? list.size() + 1 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == headView) {
                return;
            }
            iv = itemView.findViewById(R.id.iv);
        }
    }
}
