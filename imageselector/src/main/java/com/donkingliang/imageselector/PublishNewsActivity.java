package com.donkingliang.imageselector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.donkingliang.imageselector.adapter.ImageAdapter;
import com.donkingliang.imageselector.entry.MediaInfo;
import com.donkingliang.imageselector.utils.ImageSelector;

import java.util.ArrayList;

/**
 * Created by Wisn on 2018/7/26 下午4:55.
 */
public class PublishNewsActivity extends AppCompatActivity {

    private ArrayList<MediaInfo> selectImages;

    public static void start(ArrayList<MediaInfo> media, Activity activity) {
        Intent intent = new Intent(activity, PublishNewsActivity.class);
        intent.putParcelableArrayListExtra(ImageSelector.SELECT_RESULT, media);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    private RecyclerView rvImage;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishnews);
        Intent intent = getIntent();
        selectImages = intent.getParcelableArrayListExtra(ImageSelector.SELECT_RESULT);
        rvImage = findViewById(R.id.rv_image);
        rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new ImageAdapter(this);
        rvImage.setAdapter(mAdapter);
        mAdapter.refresh(selectImages);
        mAdapter.setAddSelect(new ImageAdapter.AddSelectListener() {
            @Override
            public void add(ArrayList<MediaInfo> select) {
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(select)
                        .start(PublishNewsActivity.this, ImageSelector.REQUEST_CODE); // 打开相册
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.REQUEST_CODE && data != null) {
            ArrayList<MediaInfo> selectImages = data.getParcelableArrayListExtra(ImageSelector.SELECT_RESULT);
            mAdapter.refresh(selectImages);
            return ;
         }
        this.finish();
    }
}
