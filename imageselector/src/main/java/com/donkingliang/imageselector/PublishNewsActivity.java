package com.donkingliang.imageselector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.donkingliang.imageselector.adapter.ImageAdapter;

import java.util.ArrayList;

/**
 * Created by Wisn on 2018/7/26 下午4:55.
 */
public class PublishNewsActivity extends AppCompatActivity {
    public static void start(ArrayList<String> media){

    }

    private RecyclerView rvImage;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishnews);

        rvImage = findViewById(R.id.rv_image);
        rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new ImageAdapter(this);
        rvImage.setAdapter(mAdapter);
    }
}
