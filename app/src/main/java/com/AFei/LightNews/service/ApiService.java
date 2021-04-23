package com.AFei.LightNews.service;

import com.AFei.LightNews.model.BaseBean;
import com.AFei.LightNews.model.CommentBean;
import com.AFei.LightNews.model.DataBean;
import com.AFei.LightNews.model.NewBean;
import com.AFei.LightNews.model.UserInfoBean;
import com.AFei.LightNews.model.UserTailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

//接口文档


public interface ApiService
{
    @Headers({"url_name:other"})
    @GET("/news/news/content/other")
    Observable<BaseBean<DataBean<List<NewBean>>>> getNewsList(@Query("type") String type, @Query("userUniqueKey") String userId, @Query("recommendType") int index);

    @Headers({"url_name:other"})
    @GET("/news/news/content/top")
    Observable<BaseBean<DataBean<List<NewBean>>>> getMyNewsList(@Query("userUniqueKey") String userId, @Query("recommendType") int index);

    @Headers({"url_name:other"})
    @GET("/news/news/distaste")
    Observable<BaseBean<DataBean<List<UserInfoBean>>>> postEnjoy(@Query("newsUniqueKey") String newUid,@Query("userUniqueKey") String userId,@Query("feedbackContent") String feedbackContent);

    @Headers({"url_name:other"})
    @GET("/news/news/history")
    Observable<BaseBean<DataBean<List<UserTailBean>>>>  postHistory(@Query("userUniqueKey") String UID, @Query("newsUniqueKey") String newsID,@Query("newsTitle") String title,@Query("newsType") String type,@Query("newsUrl") String url);

    @Headers({"url_name:other"})
    @GET("/news/news/register")
    Observable<BaseBean<DataBean<List <UserInfoBean>>>> postNewUser(@Query("uniqueKey") String UID,@Query("userName") String name,@Query("imageType") int imageType,@Query("latitude") double latitude,@Query("longitude") double longitude);//

    @Headers({"url_name:other"})
    @POST("/news/news/comment/update")
    @FormUrlEncoded
    Observable<BaseBean<DataBean<List<UserTailBean>>>> postReview(@Field("newsUniqueKey") String newsId, @Field("commentUniqueKey") String reviewId, @Field("composeType") String reviewType, @Field("content") String reviewContent, @Field("userUniqueKey") String UID, @Field("commentTime") String commentTime, @Field("ip") String ip);

    @Headers({"url_name:other"})
    @GET("/news/news/comment/get")
    Observable<BaseBean<DataBean<List<CommentBean>>>> getReviewList(@Query("newsUniqueKey") String newsUid,@Query("userUniqueKey") String userId);

    @Headers({"url_name:other"})
    @GET("/news/news/acclaim")
    Observable<BaseBean<DataBean<List<UserTailBean>>>> postAcclaim(@Query("typeId")String typeId,@Query("userUniqueKey") String userId,@Query("acclaimType") int type,@Query("value") int value);

    @Headers({"url_name:other"})
    @GET("/news/news/collect")
    Observable<BaseBean<DataBean<List<UserTailBean>>>> postCollect(@Query("newsUniqueKey") String newsId,@Query("userUniqueKey")String userId,@Query("value") int value);

}

