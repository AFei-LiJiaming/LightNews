package com.AFei.LightNews.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class RecentNewsBean {
    @Id
    private Long id;
    private String uniquekey;
    private String title;
    private String date;
    private String category;

    private String authorName;
    private String url;

    private String thumbnailPicS;

    private String thumbnailPicS02;

    private String thumbnailPicS03;

    @Generated(hash = 1217460969)
    public RecentNewsBean(Long id, String uniquekey, String title, String date,
            String category, String authorName, String url, String thumbnailPicS,
            String thumbnailPicS02, String thumbnailPicS03) {
        this.id = id;
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

    @Generated(hash = 1013083743)
    public RecentNewsBean() {
    }

    public String getUniquekey() {
        return this.uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailPicS() {
        return this.thumbnailPicS;
    }

    public void setThumbnailPicS(String thumbnailPicS) {
        this.thumbnailPicS = thumbnailPicS;
    }

    public String getThumbnailPicS02() {
        return this.thumbnailPicS02;
    }

    public void setThumbnailPicS02(String thumbnailPicS02) {
        this.thumbnailPicS02 = thumbnailPicS02;
    }

    public String getThumbnailPicS03() {
        return this.thumbnailPicS03;
    }

    public void setThumbnailPicS03(String thumbnailPicS03) {
        this.thumbnailPicS03 = thumbnailPicS03;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
