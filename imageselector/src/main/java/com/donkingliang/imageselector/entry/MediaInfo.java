package com.donkingliang.imageselector.entry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *图片实体类
 */
public class MediaInfo implements Parcelable {

    private String path;
    private long time;
    private String name;
    private String mimeType;
    private boolean isVideo;
    public String duration;


    public MediaInfo(String path, long time, String name, String mimeType) {
        this.path = path;
        this.time = time;
        this.name = name;
        this.mimeType = mimeType;
    }
    public MediaInfo(String path, long time, String name, String mimeType, boolean isVideo,String duration) {
        this.path = path;
        this.time = time;
        this.name = name;
        this.mimeType = mimeType;
        this.isVideo=isVideo;
        this.duration=duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isGif(){
        return "image/gif".equals(mimeType);
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeLong(this.time);
        dest.writeString(this.name);
        dest.writeString(this.mimeType);
    }

    protected MediaInfo(Parcel in) {
        this.path = in.readString();
        this.time = in.readLong();
        this.name = in.readString();
        this.mimeType = in.readString();
    }

    public static final Parcelable.Creator<MediaInfo> CREATOR = new Parcelable.Creator<MediaInfo>() {
        @Override
        public MediaInfo createFromParcel(Parcel source) {
            return new MediaInfo(source);
        }

        @Override
        public MediaInfo[] newArray(int size) {
            return new MediaInfo[size];
        }
    };

    @Override
    public String toString() {
//        return "MediaInfo{" +
//                "path='" + path + '\'' +
//                ", time=" + time +
//                ", name='" + name + '\'' +
//                ", mimeType='" + mimeType + '\'' +
//                '}';
        return  "path='" + path ;
    }
}
