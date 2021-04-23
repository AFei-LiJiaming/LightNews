package com.AFei.LightNews.entity;



public class Comment {
    private String name;
    private String commentTime;
    private int imageType;
    private Long commentCount;
    private String comment_unique_key;
    private String content;
    private int acclaimCount;
    private int acclaimStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAcclaimCount() {
        return acclaimCount;
    }

    public void setAcclaimCount(int acclaimCount) {
        this.acclaimCount = acclaimCount;
    }

    public int getAcclaimStatus() {
        return acclaimStatus;
    }

    public void setAcclaimStatus(int acclaimStatus) {
        this.acclaimStatus = acclaimStatus;
    }

    public String getComment_unique_key() {
        return comment_unique_key;
    }

    public void setComment_unique_key(String comment_unique_key) {
        this.comment_unique_key = comment_unique_key;
    }
}
