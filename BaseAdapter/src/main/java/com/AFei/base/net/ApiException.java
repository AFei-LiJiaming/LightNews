package com.AFei.base.net;

//服务器返回报错
public class ApiException extends Throwable
{


    private int code;
    private String msg;


    public ApiException(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}
