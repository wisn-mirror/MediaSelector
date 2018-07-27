package com.donkingliang.imageselector.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.imageselector.R;
import com.donkingliang.imageselector.entry.MediaInfo;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<FrameLayout> viewList = new ArrayList<>(4);
    List<MediaInfo> mImgList;
    private OnItemClickListener mListener;

    public ImagePagerAdapter(Context context, List<MediaInfo> imgList) {
        this.mContext = context;
        createImageViews();
        mImgList = imgList;
    }

    static class ViewHolder {
        public FrameLayout frameLayout;
        public PhotoView imageView;
        public VideoView videoView;

        public ViewHolder(FrameLayout view) {
            this.frameLayout = view;
            imageView = view.findViewById(R.id.photoView);
            videoView = view.findViewById(R.id.videoView);
        }
    }

    private void createImageViews() {
        for (int i = 0; i < 4; i++) {
//            ViewHolder viewHolder= new ViewHolder();
//            viewHolder.imageView.setAdjustViewBounds(true);
//            viewList.add(viewHolder);
            viewList.add((FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.adapter_media_preview_item, null, false));
        }
    }

    @Override
    public int getCount() {
        return mImgList == null ? 0 : mImgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof FrameLayout) {
            FrameLayout view = (FrameLayout) object;
            PhotoView photoView = view.findViewById(R.id.photoView);
            final VideoView videoView = view.findViewById(R.id.videoView);
            videoView.stopPlayback();
            photoView.setImageDrawable(null);
            viewList.add(view);
            container.removeView(view);
        }

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final FrameLayout currentView = viewList.remove(0);
        final MediaInfo image = mImgList.get(position);
        container.addView(currentView);
        PhotoView photoView = currentView.findViewById(R.id.photoView);
        final VideoView videoView = currentView.findViewById(R.id.videoView);

        if (!image.isVideo()) {
            photoView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with(mContext).load(new File(image.getPath()))
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(photoView);
        } else {
            videoView.setVisibility(View.VISIBLE);
            photoView.setVisibility(View.GONE);
            try {
                videoView.setVideoPath(image.getPath());
                videoView.requestFocus();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        if (mp.isPlaying()) {
                            mp.stop();
                            mp.release();
                            mp = new MediaPlayer();
                        }
                        mp.setVolume(0f, 0f);
                        mp.setLooping(true);
                        mp.start();
                    }
                });
                videoView.setFocusable(false);
                videoView.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        currentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position, image);
                }
            }
        });
        return currentView;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, MediaInfo image);
    }

}
