package com.AFei.base.base;


public interface BaseView
{
    /**
     * send Object to View
     * @param obj
     */
    void showData(Object obj);

    /**
     * send error msg to View
     * @param msg
     */
    void showError(String msg);
}
