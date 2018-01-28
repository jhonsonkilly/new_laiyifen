package com.lyfen.android.hybird;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lxs on 2016/8/31.
 */
public class UrlEntity implements Parcelable {

    @Override
    public String toString() {
        return "UrlEntity{" +
                "url='" + url + '\'' +
                ", mpId='" + mpId + '\'' +
                ", navCategoryIds='" + navCategoryIds + '\'' +
                ", navCategoryNames='" + navCategoryNames + '\'' +
                ", keyword='" + keyword + '\'' +
                ", brandIds='" + brandIds + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pic='" + pic + '\'' +
                ", category_name='" + category_name + '\'' +
                ", category_id='" + category_id + '\'' +
                ", param='" + param + '\'' +
                '}';
    }

    public String url;
    public String mpId;
    public String navCategoryIds;
    public String navCategoryNames;
    public String keyword;
    public String brandIds;
    public String title;
    public String description;
    public String pic;


    public String category_name;
    public String category_id;
    public String param;
    public String isHidden;

    public String params;
    public String refresh;
    public String forceBack;
    public String isReload;
    public int index;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.mpId);
        dest.writeString(this.navCategoryIds);
        dest.writeString(this.navCategoryNames);
        dest.writeString(this.keyword);
        dest.writeString(this.brandIds);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.pic);
        dest.writeString(this.category_name);
        dest.writeString(this.category_id);
        dest.writeString(this.param);
        dest.writeString(this.isHidden);
        dest.writeString(this.refresh);
        dest.writeString(this.forceBack);
        dest.writeString(this.isReload);
        dest.writeInt(this.index);

    }

    public UrlEntity() {
    }

    protected UrlEntity(Parcel in) {
        this.url = in.readString();
        this.mpId = in.readString();
        this.navCategoryIds = in.readString();
        this.navCategoryNames = in.readString();
        this.keyword = in.readString();
        this.brandIds = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.pic = in.readString();
        this.category_name = in.readString();
        this.category_id = in.readString();
        this.param = in.readString();
        this.isHidden = in.readString();
        this.refresh = in.readString();
        this.forceBack = in.readString();
        this.isReload = in.readString();
        this.index = in.readInt();
    }

    public static final Parcelable.Creator<UrlEntity> CREATOR = new Parcelable.Creator<UrlEntity>() {
        @Override
        public UrlEntity createFromParcel(Parcel source) {
            return new UrlEntity(source);
        }

        @Override
        public UrlEntity[] newArray(int size) {
            return new UrlEntity[size];
        }
    };
}


