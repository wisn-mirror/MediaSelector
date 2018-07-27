package com.donkingliang.imageselector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.imageselector.R;
import com.donkingliang.imageselector.entry.MediaInfo;

import java.io.File;
import java.util.ArrayList;
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MediaInfo> mImages;
    private LayoutInflater mInflater;
    private boolean canAdd=true;
    private int maxCount=9;

    public ImageAdapter(Context context) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public ArrayList<MediaInfo> getImages() {
        return mImages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_media_item_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position == (getItemCount() - 1)&&canAdd) {
            holder.ivImage.setImageResource(R.drawable.icon_add_failure);
            holder.tv_video_duration.setVisibility(View.GONE);
            holder.iv_delete.setVisibility(View.GONE);
            holder.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(AddSelectListener!=null){
                        AddSelectListener.add(getImages());
                    }
                }
            });
            return;
        }
        final MediaInfo mediaInfo = mImages.get(position);
        Glide.with(mContext).load(new File(mediaInfo.getPath()))
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(holder.ivImage);
        if (mediaInfo.isVideo()) {
            holder.tv_video_duration.setVisibility(mediaInfo.isVideo() ? View.VISIBLE : View.GONE);
            holder.tv_video_duration.setText(mediaInfo.duration);
        } else {
            holder.tv_video_duration.setVisibility(View.GONE);
        }
        holder.iv_delete.setVisibility(View.VISIBLE);
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImages.remove(position);
                refresh(mImages);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mImages == null || mImages.size() == 0) {
            if (canAdd) {
                return 1;
            } else {
                return 0;
            }
        } else if (mImages.size() < maxCount) {
            if (canAdd) {
                return mImages.size() + 1;
            } else {
                return mImages.size();
            }
        } else {
            canAdd=false;
            return mImages.size();
        }
    }

    public void refresh(ArrayList<MediaInfo> images) {
        mImages = images;
        canAdd=true;
        if(mImages!=null&&mImages.size()>0){
            if(mImages.size()==1&&mImages.get(0).isVideo()){
                canAdd=false;
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage, iv_delete;
        TextView tv_video_duration;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            tv_video_duration = itemView.findViewById(R.id.tv_video_duration);
        }
    }
    public AddSelectListener AddSelectListener;

    public void setAddSelect(AddSelectListener AddSelectListener) {
        this.AddSelectListener = AddSelectListener;
    }

    public  interface  AddSelectListener{
        void add(ArrayList<MediaInfo>  select);
   }
}
