package com.donkingliang.imageselectdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.donkingliang.imageselector.adapter.ImageAdapter;
import com.donkingliang.imageselector.SelectMediaActivity;
import com.donkingliang.imageselector.entry.MediaInfo;
import com.donkingliang.imageselector.utils.ImageSelector;

import java.util.ArrayList;

import static com.donkingliang.imageselector.utils.ImageSelector.REQUEST_CODE;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        rvImage = findViewById(R.id.rv_image);
//
//        findViewById(R.id.btn_single).setOnClickListener(this);
//        findViewById(R.id.btn_limit).setOnClickListener(this);
//        findViewById(R.id.btn_unlimited).setOnClickListener(this);
//        findViewById(R.id.btn_clip).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
//        imageView = findViewById(R.id.imageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            ArrayList<MediaInfo> selectImages = data.getParcelableArrayListExtra(ImageSelector.SELECT_RESULT);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*  case R.id.btn_single:
                //单选
//                ImageSelectorUtils.openPhoto(MainActivity.this, REQUEST_CODE, true, 0);
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .start(this, REQUEST_CODE); // 打开相册
                break;

            case R.id.btn_limit:
                //多选(最多9张)
//                ImageSelectorUtils.openPhoto(MainActivity.this, REQUEST_CODE, false, 9);
//                ImageSelector.builder().setSingle(true).start(this,REQUEST_CODE);
//                ImageSelectorUtils.openPhoto(MainActivity.this, REQUEST_CODE, false, 9, mAdapter.getImages()); // 把已选的传入。
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(this, REQUEST_CODE); // 打开相册
                break;

            case R.id.btn_unlimited:
                //多选(不限数量)
//                ImageSelectorUtils.openPhoto(MainActivity.this, REQUEST_CODE);
//                ImageSelectorUtils.openPhoto(MainActivity.this, REQUEST_CODE, mAdapter.getImages()); // 把已选的传入。
                //或者
//                ImageSelectorUtils.openPhoto(MainActivity.this, REQUEST_CODE, false, 0);
//                ImageSelectorUtils.openPhoto(MainActivity.this, REQUEST_CODE, false, 0, mAdapter.getImages()); // 把已选的传入。

                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(0) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(this, REQUEST_CODE); // 打开相册
                break;

            case R.id.btn_clip:
                //单选并剪裁
//                ImageSelectorUtils.openPhotoAndClip(MainActivity.this, REQUEST_CODE);
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setCrop(true)  // 设置是否使用图片剪切功能。
                        .setSingle(true)  //设置是否单选
                        .start(this, REQUEST_CODE); // 打开相册
                break;*/
            case R.id.button:
                //单选并剪裁
//                MediaInfoModel.loadImageForSDCard(this, new MediaInfoModel.DataCallback() {
//                    @Override
//                    public void onSuccess(ArrayList<Folder> folders) {
//
//                    }
//                });
//                ImageSelector.builder()
//                        .useCamera(true) // 设置是否使用拍照
//                        .setCrop(true)  // 设置是否使用图片剪切功能。
//                        .setSingle(true)  //设置是否单选
//                        .useContainsVideo(true)  //设置是否单选
//                        .start(this, REQUEST_CODE); // 打开相册
              /*  String  path="/storage/emulated/0/DCIM/Camera/VID_20180726_133031.mp4";
                Bitmap videoThumbnail = ImageUtil.getVideoThumbnail(path, 100, 200, MediaStore.Images.Thumbnails.MINI_KIND);
                imageView.setImageBitmap(videoThumbnail);*/

                        SelectMediaActivity.start(MainActivity.this);


                break;
        }
    }
}
