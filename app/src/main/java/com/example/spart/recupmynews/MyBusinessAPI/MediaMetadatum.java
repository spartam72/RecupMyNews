package com.example.spart.recupmynews.MyBusinessAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MediaMetadatum implements Serializable {


    @SerializedName("url")
    @Expose
    private String mUrl;
    public String getUrl() {
        return mUrl;
    }
    public String getFormattedUrl() {
        return getUrl().replace("\\", "");
    }

    @SerializedName("format")
    @Expose
    private String mFormat;
    public String getFormat() {
        return mFormat;
    }

    @SerializedName("height")
    @Expose
    private int mHeight;
    public int getHeight() {
        return mHeight;
    }

    @SerializedName("width")
    @Expose
    private int mWidth;
    public int getWidth() {
        return mWidth;
    }

    @SerializedName("type")
    @Expose
    private String mType;
    public String getType() {
        return mType;
    }

    @SerializedName("subtype")
    @Expose
    private String mSubType;
    public String getSubType() {
        return mSubType;
    }

    @SerializedName("caption")
    @Expose
    private String mCaption;
    public String getCaption() {
        return mCaption;
    }

    @SerializedName("copyright")
    @Expose
    private String mCopyright;
    public String getCopyright() {
        return mCopyright;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setFormat(String format) {
        mFormat = format;
    }

}