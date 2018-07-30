package com.donkingliang.imageselector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Wisn on 2018/7/27 下午3:34.
 */
public class PrivateOrPublicActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int PUBLIC = 1;
    public static final int PRIVATE = 2;
    public static final int PUBLIC_PART = 3;
    public static final int PUBLIC_UNCONTACT = 4;
    public static final String PrivateOrPublic = "PrivateOrPublic";
    public int currentStatus = PUBLIC;
    private LinearLayout ll_public;
    private ImageView iv_public;
    private LinearLayout ll_private;
    private ImageView iv_private;
    private TextView tv_left;
    private TextView tv_right;
    private TextView tv_title;

    public static void start(Activity mContext, int RequstCode, int selectPrivateOrPublic) {
        Intent intent = new Intent(mContext, PrivateOrPublicActivity.class);
        intent.putExtra(PrivateOrPublicActivity.PrivateOrPublic, selectPrivateOrPublic);
        mContext.startActivityForResult(intent, RequstCode);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privateorpublic);
        ll_public = findViewById(R.id.ll_public);
        iv_public = findViewById(R.id.iv_public);
        ll_private = findViewById(R.id.ll_private);
        iv_private = findViewById(R.id.iv_private);
        tv_left = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("是否公开");
        tv_right.setText("完成");
        ll_public.setOnClickListener(this);
        ll_private.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        currentStatus = getIntent().getIntExtra(PrivateOrPublicActivity.PrivateOrPublic, PrivateOrPublicActivity.PUBLIC);
        if (currentStatus == PrivateOrPublicActivity.PUBLIC) {
            onClick(ll_public);
        } else if (currentStatus == PrivateOrPublicActivity.PRIVATE) {
            onClick(ll_private);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == ll_public) {
            iv_public.setImageResource(R.drawable.icon_image_select);
            iv_private.setImageResource(R.drawable.icon_image_un_select);
            currentStatus = PUBLIC;
        } else if (v == ll_private) {
            iv_public.setImageResource(R.drawable.icon_image_un_select);
            iv_private.setImageResource(R.drawable.icon_image_select);
            currentStatus = PRIVATE;
        } else if (v == tv_left) {
            this.finish();
        } else if (v == tv_right) {
            Intent intent = new Intent();
            intent.putExtra(PrivateOrPublic, currentStatus);
            setResult(RESULT_OK, intent);
            this.finish();
        }
    }
}
