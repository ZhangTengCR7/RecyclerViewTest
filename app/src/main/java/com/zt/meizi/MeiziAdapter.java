package com.zt.meizi;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zt.R;

import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

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
        final int realPosition;
        if (getItemViewType(position) == HEAD) {
            return;
        } else {
            if (headView == null) {
                realPosition = position;
                GlideUtils.loadFitCenter(context, list.get(realPosition).getUrl(), holder.iv, R.drawable.placeholder);
                holder.badge.setBadgeNumber(list.get(realPosition).getMsgNum());
            } else {
                realPosition = position - 1;
                GlideUtils.loadFitCenter(context, list.get(realPosition).getUrl(), holder.iv, R.drawable.placeholder);
                holder.badge.setBadgeNumber(list.get(realPosition).getMsgNum());
            }
            holder.badge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    list.get(realPosition).setMsgNum(0);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return headView != null ? list.size() + 1 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        Badge badge;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == headView) {
                return;
            }
            iv = itemView.findViewById(R.id.iv);
            badge = new QBadgeView(context).bindTarget(iv);
            badge.setBadgeGravity(Gravity.TOP | Gravity.END);
            badge.setGravityOffset(0f, true);

        }
    }
}
