package baidumapsdk.demo.indoorview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.baidulocationdemo.R;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * Created by Administrator on 2018-3-22.
 */

public class GeoCoderDemoActivity extends Activity implements OnGetGeoCoderResultListener {

    /**
     * 地理编码查询接口
     * */
    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    BaiduMap mBaiduMap = null;
    MapView mMapView = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        Log.d("Mytest", "onCreate: Mytest - GPS s");
        CharSequence titleLable = "地理编码功能";
        setTitle(titleLable);

        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        // 初始化搜索模块，注册事件监听
        /**
         * public static GeoCoder newInstance()
         * 新建地理编码查询
         * @return 地理编码查询对象
         * */
        mSearch = GeoCoder.newInstance();
        /**
         * public void setOnGetGeoCodeResultListener(OnGetGeoCoderResultListener listener)
         * 设置查询结果监听者
         * @param listener - 监听者
         *
         * 需要实现OnGetGeoCoderResultListener的
         * onGetGeoCodeResult(GeoCodeResult result)和onGetReverseGeoCodeResult(ReverseGeoCodeResult result)
         * 方法
         * */
        mSearch.setOnGetGeoCodeResultListener(this);
    }

    /**
     * 发起搜索
     *
     * @param v
     */
    public void SearchButtonProcess(View v) {
        /**
         *反编码
         * */
        if (v.getId() == R.id.reversegeocode) {
            EditText lat = (EditText) findViewById(R.id.lat);
            EditText lon = (EditText) findViewById(R.id.lon);
            LatLng ptCenter = new LatLng((Float.valueOf(lat.getText().toString())),
                    (Float.valueOf(lon.getText().toString())));
            /**
             * public boolean reverseGeoCode(ReverseGeoCodeOption option)
             * 发起反地理编码请求(经纬度->地址信息)
             * @param option - 请求参数
             * @return 成功发起检索返回true,失败返回false
             *
             * public ReverseGeoCodeOption location(LatLng location)
             * 设置反地理编码位置坐标
             * @param location - 位置坐标
             * @return 该反地理编码请求参数对象
             * */
            mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));

            /**
             * 编码
             * */
        } else if (v.getId() == R.id.geocode) {
            EditText editCity = (EditText) findViewById(R.id.city);
            EditText editGeoCodeKey = (EditText) findViewById(R.id.geocodekey);
            /**
             * public boolean geocode(GeoCodeOption option)
             * 发起地理编码(地址信息->经纬度)请求
             * @param option - 请求参数
             * @return 成功发起检索返回true , 失败返回false
             * */
            mSearch.geocode(new GeoCodeOption().city(editCity.getText().toString())
                    .address(editGeoCodeKey.getText().toString()));
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();

        //释放该地理编码查询对象
        mSearch.destroy();

        super.onDestroy();
    }

    //////////////////OnGetGeoCoderResultListener//////////////////
    /**
     * void onGetGeoCodeResult(GeoCodeResult result)
     * 地理编码查询结果回调函数
     * @param result - 地理编码查询结果
     * */
    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(GeoCoderDemoActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        mBaiduMap.clear();
        /**
         * public final Overlay addOverlay(OverlayOptions options)
         * 向地图添加一个 Overlay
         * @param options - 将要添加的 overlay 选项
         * @return 被添加的 overlay 对象
         *
         * public MarkerOptions position(LatLng position)
         * 设置 marker 覆盖物的位置坐标、
         * @param position - marker 覆盖物的位置坐标
         * @return 该 Marker 选项对象
         *
         * public MarkerOptions icon(BitmapDescriptor icon)
         * 设置 Marker 覆盖物的图标，相同图案的 icon 的 marker 最好使用同一个 BitmapDescriptor 对象以节省内存空间。
         * @param icon - Marker 覆盖物的图标
         * @return 该 Marker 选项对象
         *
         * GeoCodeResult:地理编码结果
         * public LatLng getLocation()
         * 获取位置信息
         * @return 位置信息
         * */
        mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark)));

        /**
         * public final void setMapStatus(MapStatusUpdate update)
         * 改变地图状态
         * @param update - 地图状态将要发生的变化
         *
         * public static MapStatusUpdate newLatLng(LatLng latLng)
         * 设置地图新中心点
         * @param latLng - 地图新中心点
         * @return 返回构造的 MapStatusUpdate 对象，如果latLng为 null则返回空。
         * */
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));

        String strInfo = String.format("纬度：%f 经度：%f",result.getLocation().latitude, result.getLocation().longitude);
        Toast.makeText(GeoCoderDemoActivity.this, strInfo, Toast.LENGTH_LONG).show();
    }

    /**
     * void onGetReverseGeoCodeResult(ReverseGeoCodeResult result)
     * 反地理编码查询结果回调函数
     * @param result - 反地理编码查询结果
     * */
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(GeoCoderDemoActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        mBaiduMap.clear();
        /**
         * ReverseGeoCodeResult:反 Geo Code 结果
         * */
        mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark)));

        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));

        Toast.makeText(GeoCoderDemoActivity.this, result.getAddress(),Toast.LENGTH_LONG).show();

    }

}
