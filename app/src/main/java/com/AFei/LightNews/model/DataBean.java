package com.AFei.LightNews.model;

import com.google.gson.annotations.SerializedName;

//二级实体类
public class DataBean<T>
{
    @SerializedName("stat")
    private int code;
    @SerializedName("data")
    private T t;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public T getT()
    {
        return t;
    }

    public void setT(T t)
    {
        this.t = t;
    }
}
