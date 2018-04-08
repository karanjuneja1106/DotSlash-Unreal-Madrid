package com.buddy.campus.campusbuddy;

import android.net.Uri;

import java.util.UUID;

/**
 * Created by tanishka on 7/4/18.
 */

public class SellItem {
    public String getmOwner() {
        return mOwner;
    }

    public void setmOwner(String mOwner) {
        this.mOwner = mOwner;
    }

    private String mOwner;

    public String getmCaption() {
        return mCaption;
    }

    private String mCaption;

    public String getmId() {
        return mId;
    }

    private String mId;
    public String getmUrl() {
        return mUrl;
    }

    private String mUrl;
    public SellItem(){
        mId= UUID.randomUUID().toString();
    }
    @Override
    public String toString() {
        return  mCaption;
    }

    public void setmCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
