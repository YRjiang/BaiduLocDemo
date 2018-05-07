package baidumapsdk.demo.indoorview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.baidu.baidulocationdemo.R;
import com.baidu.location.Utils.LocationUtils;
import com.baidu.mapapi.SDKInitializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-3-22.
 */

public class GeoCoderActivity extends Activity implements LocationUtils.OnResultMapListener {

    private LocationUtils mLocationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化地图SDK<最好放置在Application中>
        Log.d("Mytest", "onCreate: Mytest - GPS x");

        SDKInitializer.initialize(getApplicationContext());
        // 创建定位管理信息对象
        mLocationUtils = new LocationUtils(getApplicationContext());
        setContentView(R.layout.activity_geocoder);
        // 开启定位
        mLocationUtils.startLocation();
        mLocationUtils.registerOnResult(this);

        mLocationUtils.getLocation("广安", "邓小平故居");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 存储地址信息
    private Map<String, Object> resultMap = new HashMap<String, Object>();

    @Override
    public void onReverseGeoCodeResult(Map<String, Object> map) {
        resultMap = map;
        Log.i("data", "result 1 ====>" + resultMap.toString());
    }

    @Override
    public void onGeoCodeResult(Map<String, Object> map) {
        Log.i("data", "result 2 ====>" + map.toString());
    }

}
