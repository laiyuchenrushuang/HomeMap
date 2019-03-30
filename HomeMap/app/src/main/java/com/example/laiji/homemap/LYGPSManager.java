package com.example.laiji.homemap;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.List;

class LYGPSManager {

    private static final String TAG = "[lylog]";
    public LocationManager mLocationManager;
    public Location mLocation;

    public static final String[] GPS_PERMISSION = {android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION};
    public static final int requestCode = 100;

    public LYGPSManager(final BaseActivity activity) {
        mLocationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "LYGPSManager: permision is deny");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d(TAG, "LYGPSManager: requestPermissions");
                activity.requestPermissions(GPS_PERMISSION,requestCode);
            }
        }
        String bestProvider = mLocationManager.getBestProvider(getCriteria(), true);
        mLocation = mLocationManager.getLastKnownLocation(bestProvider);

        int runTimeNum = 0;
        List<String> providers = mLocationManager.getProviders(true);
        providers.add(bestProvider);

        do {
            for (String provider:providers) {
                mLocationManager.requestLocationUpdates(provider, 500, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        activity.showToast("onLocationChanged");
                        mLocation=location;
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                        //activity.showToast("onStatusChanged");
                    }

                    @Override
                    public void onProviderEnabled(String s) {
                        //activity.showToast("onProviderEnabled");
                    }

                    @Override
                    public void onProviderDisabled(String s) {
                        //activity.showToast("onProviderDisabled");
                    }
                });
            }
            Log.d(TAG, "LYGPSManager: while");
            runTimeNum++;
        } while (null != mLocation);

        if (null == mLocation) {
            activity.showToast( "location is null ");
        } else {
            activity.showToast("get location is ok runTimeNum ="+runTimeNum);
            runTimeNum = 0;
        }
    }

    private Criteria getCriteria() {
        Criteria criteria=new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        //设置是否需要方位信息
        criteria.setBearingRequired(false);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }
}
