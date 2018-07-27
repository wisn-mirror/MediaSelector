package com.donkingliang.imageselector;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.os.EnvironmentCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.donkingliang.imageselector.entry.MediaInfo;
import com.donkingliang.imageselector.utils.BlurBehind;
import com.donkingliang.imageselector.utils.ImageSelector;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Wisn on 2018/7/26 下午3:08.
 */
public class SelectMediaActivity extends Activity implements View.OnClickListener {

    private ImageView iv_cancel;
    private LinearLayout ll_shooting;
    private LinearLayout ll_album;

    private static final int PERMISSION_CAMERA_REQUEST_CODE = 0x00000012;
    private static final int CAMERA_REQUEST_CODE = 0x00000010;

    private String mPhotoPath;

    public static void start(final Activity context) {
        BlurBehind.getInstance().execute(context, new BlurBehind.OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                context.startActivity(new Intent(context, SelectMediaActivity.class));
                context.overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_selectmedia);
        BlurBehind.getInstance()
                .withAlpha(1000)
                .withFilterColor(Color.parseColor("#000000"))
                .setBackground(this);
        iv_cancel = findViewById(R.id.iv_cancel);
        ll_shooting = findViewById(R.id.ll_shooting);
        ll_album = findViewById(R.id.ll_album);
        iv_cancel.setOnClickListener(this);
        ll_shooting.setOnClickListener(this);
        ll_album.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.REQUEST_CODE && data != null) {
            ArrayList<MediaInfo> selectImages = data.getParcelableArrayListExtra(ImageSelector.SELECT_RESULT);
            PublishNewsActivity.start(selectImages, this);
        } else if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<MediaInfo> selectImages = new ArrayList<>();
                selectImages.add(new MediaInfo(mPhotoPath, 0, null, null,0));
                PublishNewsActivity.start(selectImages, this);
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v == iv_cancel) {
            this.finish();
        } else if (v == ll_album) {
            ImageSelector.builder()
                    .useCamera(true) // 设置是否使用拍照
                    .setSingle(false)  //设置是否单选
                    .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                    .start(this, ImageSelector.REQUEST_CODE); // 打开相册
        } else if (v == ll_shooting) {
            checkPermissionAndCamera();
        }
    }

    /**
     * 检查权限并拍照。
     */
    private void checkPermissionAndCamera() {
        int hasCameraPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.CAMERA);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有调起相机拍照。
            openCamera();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(SelectMediaActivity.this,
                    new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST_CODE);
        }
    }

    /**
     * 调起相机拍照
     */
    private void openCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                mPhotoPath = photoFile.getAbsolutePath();
                //通过FileProvider创建一个content类型的Uri
                Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = String.format("JPEG_%s.jpg", timeStamp);
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File tempFile = new File(storageDir, imageFileName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }


}
