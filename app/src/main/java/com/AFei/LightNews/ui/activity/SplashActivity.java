package com.AFei.LightNews.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.Blinger.base.base.BaseActivity;
import com.Blinger.base.base.BaseView;
import com.Blinger.base.utils.LogUtils;
import com.Blinger.base.utils.StringUtils;
import com.AFei.LightNews.R;
import com.AFei.LightNews.config.Config;
import com.AFei.LightNews.config.Constant;
import com.AFei.LightNews.model.UserInfoBean;
import com.AFei.LightNews.presenter.SplashPresenter;
import com.AFei.LightNews.utils.GainLocation;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;



public class SplashActivity extends BaseActivity<SplashPresenter> implements BaseView {
    private double latitude = 0.0;
    private double longitude = 0.0;
    public static String location;
    private String uuid;
    private int type;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;



    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        super.initView(savedInstanceState);
        //关闭导航栏
        sharedPreferences = getSharedPreferences("init", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        final View decorView = getWindow().getDecorView();
        final int uiOption = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOption);
        RxPermissions permiss = new RxPermissions(this);
        permiss.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        delay();
                    }
                });

    }

    /*延迟启动App*/
    private void delay() {
        boolean isFirstStart = sharedPreferences.getBoolean("firstStart", true);
//        LogUtils.d(Constant.debugName, "" + isFirstStart);

        if (isFirstStart) {
            gainLatitudeLongitude();
            location = StringUtils.splitString(GainLocation.getAddress(this,latitude,longitude))+"的网友";
//            LogUtils.d(Constant.debugName+"位置",location);
//
//            LogUtils.d(Constant.debugName, "第一次进入");
            uuid = UUID.randomUUID().toString();
            type = (int) (1 + Math.random() * (7 - 1 + 1));

            //editor.putBoolean("firstStart", false);
            editor.putString("uuid", uuid);
            editor.putString("name",location);
            editor.putInt("type",type);
            editor.putInt("typeSize", 2);
            editor.putInt("recommend_type", 0);


//            LogUtils.d(Constant.debugName, "firstStart:" + sharedPreferences.getBoolean("firstStart", true));
//            LogUtils.d(Constant.debugName, "uuid:" + sharedPreferences.getString("uuid", ""));
//            LogUtils.d(Constant.debugName, "type:" + sharedPreferences.getInt("type", 0));

            mPresenter.postNewUser(uuid, location,type,latitude,longitude);
        }


        Observable.timer(Config.LONG_SPLASH_DELAY, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        doFinish();
                    }
                });
    }

    private void gainLatitudeLongitude() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        } else {
            LocationListener locationListener = new LocationListener() {

                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {

                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {

                }

                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        LogUtils.e(Constant.debugName + "Map", "Location changed : Lat: "
                                + location.getLatitude() + " Lng: "
                                + location.getLongitude());
                    }
                }
            };
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null){
                latitude = location.getLatitude(); //经度
                longitude = location.getLongitude(); //纬度
            }
        }
    }

    @Override
    public void showData(Object obj) {
        if (obj instanceof List){
            List<UserInfoBean> list = (List<UserInfoBean>) obj;
//            LogUtils.d(Constant.debugName + "register",list.get(0).getInfo());
            editor.putBoolean("firstStart", false);
            editor.apply();
        }
    }

    @Override
    public void showError(String msg) {
        LogUtils.e(Constant.debugName,msg);
    }
}
