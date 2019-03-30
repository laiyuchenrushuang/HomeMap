package com.example.laiji.homemap;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MainActivity extends BaseActivity {
    private static final String TAG = "[lylog]";

    public LYGPSManager mLygpsManager;

    public LocationClient mLocationClient = null;

    public LaiyuView mLaiyuView;
    double longitude;
    double latitude;
    TextView mText;
    double viewW , viewH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = findViewById(R.id.text);
        mLaiyuView = findViewById(R.id.laiyuview);
        viewW = getWindowManager().getDefaultDisplay().getWidth();
        viewH = getWindowManager().getDefaultDisplay().getHeight();
        Log.d(TAG, "w: "+viewW+ " h:"+viewH);


        initView();
    }

    private void initView() {
        // initGPSManager();

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {

                longitude = location.getLongitude();//jingdu
                latitude = location.getLatitude();//weidu
                String addr = location.getAddrStr();    //获取详细地址信息
                String country = location.getCountry();    //获取国家
                String province = location.getProvince();    //获取省份
                String city = location.getCity();    //获取城市
                String district = location.getDistrict();    //获取区县
                String street = location.getStreet();    //获取街道信息
                //showToast(addr +" "+country+" "+province+" "+city+" "+district+" "+street+" "+latitude);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mText.setText("经度 =" + longitude + "  纬度 =" + latitude);
                        mLaiyuView.setXY(longitude, latitude);
                        LYPoint p = new LYPoint();
                        p.x = (float) (viewW-Math.abs(latitude-30.496829)*viewW/0.000050);
                        p.y = (float) (Math.abs(104.029511-longitude)*viewH/0.000370);
                        if (Utils.mListpoint.size() <= 10) {
                            showToast(" x ="+p.x + "  y="+p.y);
                            Utils.mListpoint.add(p);
                        }else{
                            showToast(" has ten point  ");
                            mLaiyuView.invalidate();
                        }

                    }
                });

            }

        });

        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void initGPSManager() {
        mLygpsManager = new LYGPSManager(this);
    }

}