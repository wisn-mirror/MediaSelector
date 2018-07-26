package com.donkingliang.imageselector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.donkingliang.imageselector.entry.MediaInfo;
import com.donkingliang.imageselector.utils.BlurBehind;
import com.donkingliang.imageselector.utils.ImageSelector;

import java.util.ArrayList;

/**
 * Created by Wisn on 2018/7/26 下午3:08.
 */
public class SelectMediaActivity extends Activity implements View.OnClickListener{
    public static final int REQUEST_CODE = 0x00000011;

    private ImageView iv_cancel;
    private LinearLayout ll_shooting;
    private LinearLayout ll_album;

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
        if (requestCode == REQUEST_CODE && data != null) {
            ArrayList<MediaInfo> selectImages = data.getParcelableArrayListExtra(ImageSelector.SELECT_RESULT);
            ArrayList<String> images = new ArrayList<>();
            for (MediaInfo image : selectImages) {
                images.add(image.getPath());
            }

        }
        this.finish();
    }
    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if(v==iv_cancel){
            this.finish();
        }else if(v==ll_album){

        }else if(v==ll_shooting){

        }
    }
}
