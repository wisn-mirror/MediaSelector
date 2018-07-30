package com.donkingliang.imageselector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.imageselector.adapter.ImageAdapter;
import com.donkingliang.imageselector.entry.MediaInfo;
import com.donkingliang.imageselector.utils.ImageSelector;

import java.util.ArrayList;

/**
 * Created by Wisn on 2018/7/26 下午4:55.
 */
public class PublishNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<MediaInfo> selectImages;
    public int currentStatus = PrivateOrPublicActivity.PUBLIC;
    public static final int PUBLIC_Static = 100;
    private RecyclerView rvImage;
    private ImageAdapter mAdapter;
    private LinearLayout ll_public;
    private TextView tv_public_status;

    private TextView tv_left;
    private TextView tv_right;
    private TextView tv_title;

    public static void start(ArrayList<MediaInfo> media, Activity activity) {
        Intent intent = new Intent(activity, PublishNewsActivity.class);
        intent.putParcelableArrayListExtra(ImageSelector.SELECT_RESULT, media);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoselect_activity_publishnews);
        Intent intent = getIntent();
        selectImages = intent.getParcelableArrayListExtra(ImageSelector.SELECT_RESULT);
        rvImage = findViewById(R.id.rv_image);
        ll_public = findViewById(R.id.ll_public);
        tv_public_status = findViewById(R.id.tv_public_status);

        tv_left = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        tv_title = findViewById(R.id.tv_title);
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        ll_public.setOnClickListener(this);
        rvImage.setLayoutManager(new GridLayoutManager(this, 4));
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
    public void onClick(View v) {
        if (v == ll_public) {
            PrivateOrPublicActivity.start(this,PUBLIC_Static,currentStatus);
        } else if (v == tv_right) {
            Toast.makeText(this, "发布", Toast.LENGTH_SHORT).show();
        } else if (v == tv_left) {
            PublishNewsActivity.this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.REQUEST_CODE && data != null) {
            ArrayList<MediaInfo> selectImages = data.getParcelableArrayListExtra(ImageSelector.SELECT_RESULT);
            mAdapter.refresh(selectImages);
            return;
        } else if (requestCode == PUBLIC_Static && data != null) {
            currentStatus = data.getIntExtra(PrivateOrPublicActivity.PrivateOrPublic, PrivateOrPublicActivity.PUBLIC);
            if (currentStatus == PrivateOrPublicActivity.PUBLIC) {
                tv_public_status.setText("公开");
            } else if (currentStatus == PrivateOrPublicActivity.PRIVATE) {
                tv_public_status.setText("私密");
            }
            return;
        }else{
            this.finish();

        }
    }


}
