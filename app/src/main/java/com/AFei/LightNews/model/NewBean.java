package com.AFei.LightNews.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


@Entity
public class NewBean implements Parcelable
{
    /**
     * uniquekey : 5ad83ce73b7ee8fa0e61d459df644927
     * title : 奥运冠军马琳退役后的生活丰富多彩, 也为我国的减肥事业做了贡献
     * date : 2018-08-01 22:15
     * category : 头条
     * author_name : 泰安头条
     * url : http://mini.eastday.com/mobile/180801221548711.html
     * thumbnail_pic_s : http://06.imgmini.eastday.com/mobile/20180801/20180801221548_0fe6871fa03e4481e98a633f760e9c6a_4_mwpm_03200403.jpg
     * thumbnail_pic_s02 : http://06.imgmini.eastday.com/mobile/20180801/20180801221548_0fe6871fa03e4481e98a633f760e9c6a_2_mwpm_03200403.jpg
     * thumbnail_pic_s03 : http://06.imgmini.eastday.com/mobile/20180801/20180801221548_0fe6871fa03e4481e98a633f760e9c6a_1_mwpm_03200403.jpg
     */
    @Id
    private String uniquekey;
    private String title;
    private String date;
    private String category;
    @SerializedName("author_name")
    private String authorName;
    private String url;
    @SerializedName("thumbnail_pic_s")
    private String thumbnailPicS;
    @SerializedName("thumbnail_pic_s02")
    private String thumbnailPicS02;
    @SerializedName("thumbnail_pic_s03")
    private String thumbnailPicS03;

    @Generated(hash = 1203650336)
    public NewBean(String uniquekey, String title, String date, String category, String authorName, String url, String thumbnailPicS,
                   String thumbnailPicS02, String thumbnailPicS03)
    {
        this.uniquekey = uniquekey;
        this.title = title;
        this.date = date;
        this.category = category;
        this.authorName = authorName;
        this.url = url;
        this.thumbnailPicS = thumbnailPicS;
        this.thumbnailPicS02 = thumbnailPicS02;
        this.thumbnailPicS03 = thumbnailPicS03;
    }

    @Generated(hash = 1213378749)
    public NewBean()
    {
    }

    protected NewBean(Parcel in)
    {
        uniquekey = in.readString();
        title = in.readString();
        date = in.readString();
        category = in.readString();
        authorName = in.readString();
        url = in.readString();
        thumbnailPicS = in.readString();
        thumbnailPicS02 = in.readString();
        thumbnailPicS03 = in.readString();
    }

    public static final Creator<NewBean> CREATOR = new Creator<NewBean>()
    {
        @Override
        public NewBean createFromParcel(Parcel in)
        {
            return new NewBean(in);
        }

        @Override
        public NewBean[] newArray(int size)
        {
            return new NewBean[size];
        }
    };

    public String getUniquekey()
    {
        return this.uniquekey;
    }

    public void setUniquekey(String uniquekey)
    {
        this.uniquekey = uniquekey;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDate()
    {
        return this.date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getCategory()
    {
        return this.category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getAuthorName()
    {
        return this.authorName;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    public String getUrl()
    {
        return this.url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getThumbnailPicS()
    {
        return this.thumbnailPicS;
    }

    public void setThumbnailPicS(String thumbnailPicS)
    {
        this.thumbnailPicS = thumbnailPicS;
    }

    public String getThumbnailPicS02()
    {
        return this.thumbnailPicS02;
    }

    public void setThumbnailPicS02(String thumbnailPicS02)
    {
        this.thumbnailPicS02 = thumbnailPicS02;
    }

    public String getThumbnailPicS03()
    {
        return this.thumbnailPicS03;
    }

    public void setThumbnailPicS03(String thumbnailPicS03)
    {
        this.thumbnailPicS03 = thumbnailPicS03;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(uniquekey);
        parcel.writeString(title);
        parcel.writeString(date);
        parcel.writeString(category);
        parcel.writeString(authorName);
        parcel.writeString(url);
        parcel.writeString(thumbnailPicS);
        parcel.writeString(thumbnailPicS02);
        parcel.writeString(thumbnailPicS03);
    }
}
