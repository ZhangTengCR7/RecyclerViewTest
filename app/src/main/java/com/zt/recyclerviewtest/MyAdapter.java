package com.zt.recyclerviewtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2016/8/4.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Person> list;

    public MyAdapter(List<Person> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Person person=list.get(i);
        viewHolder.id.setText(person.getId()+"");
        viewHolder.name.setText(person.getName());
        viewHolder.num.setText(person.getNum()+"");
        if (listener!=null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(view,i);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClickListener(view,i);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView id;

        TextView name;

        TextView num;
        public ViewHolder(View itemView) {
            super(itemView);
            id= (TextView) itemView.findViewById(R.id.id);
            name= (TextView) itemView.findViewById(R.id.name);
            num= (TextView) itemView.findViewById(R.id.phone);
        }
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClickListener(View view,int position);
        void onItemLongClickListener(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.listener=onItemClickListener;
    }


}
