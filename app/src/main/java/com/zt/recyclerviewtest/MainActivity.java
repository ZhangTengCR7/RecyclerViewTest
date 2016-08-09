package com.zt.recyclerviewtest;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener{


    private RecyclerView recyclerView;
    private MyAdapter myAdapter;


    private SwipeRefreshLayout swipeRefreshLayout;
    List<Person> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView= (RecyclerView) findViewById(R.id.recycler);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        myAdapter=new MyAdapter(getData());
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<5;i++){
                            list.add(new Person(i,"婉婉"+i,911));
                        }
                        myAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
    }

    private List<Person> getData(){

        Person person=null;
        for (int i=0;i<20;i++){
            person=new Person(i,"张腾"+i,1372077630+i);
            list.add(person);
        }
        return list;
    }


    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(MainActivity.this,position+"....",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClickListener(View view, int position) {
        Toast.makeText(MainActivity.this,position+"***",Toast.LENGTH_SHORT).show();
    }
}
