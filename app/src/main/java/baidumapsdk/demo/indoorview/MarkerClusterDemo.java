package baidumapsdk.demo.indoorview;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.baidulocationdemo.R;
import com.baidu.location.clusterutil.clustering.ClusterItem;
import com.baidu.location.clusterutil.clustering.ClusterManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-4-2.
 */

public class MarkerClusterDemo extends Activity implements BaiduMap.OnMapLoadedCallback {

    MapView mMapView;
    BaiduMap mBaiduMap;
    MapStatus ms;
    private ClusterManager<MyItem> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_cluster_demo);
        mMapView = (MapView) findViewById(R.id.bmapView);
        ms = new MapStatus.Builder().target(new LatLng(39.914935, 116.403119)).zoom(8).build();
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
        // 定义点聚合管理类ClusterManager
        mClusterManager = new ClusterManager<MyItem>(this, mBaiduMap);
        // 添加Marker点
        addMarkers();
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);

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
        super.onDestroy();
    }

    /**
     * 向地图添加Marker点
     */
    public void addMarkers() {
        // 添加Marker点
        LatLng llA = new LatLng(39.963175, 116.400244);
        LatLng llB = new LatLng(39.942821, 116.369199);
        LatLng llC = new LatLng(39.939723, 116.425541);
        LatLng llD = new LatLng(39.906965, 116.401394);
        LatLng llE = new LatLng(39.956965, 116.331394);
        LatLng llF = new LatLng(39.886965, 116.441394);
        LatLng llG = new LatLng(39.996965, 116.411394);
        LatLng llJ = new LatLng(39.799999, 116.353535);
        LatLng llO = new LatLng(39.963179, 116.400244);
        LatLng llP = new LatLng(39.963175, 116.400248);
        LatLng llQ = new LatLng(39.963171, 116.400244);
        LatLng llX = new LatLng(39.963183, 116.400244);
        LatLng llY = new LatLng(39.963187, 116.400248);
        LatLng llZ = new LatLng(39.963239, 116.400244);

        LatLng llH = new LatLng(26.112512, 119.270088);
        LatLng llI = new LatLng(26.101010, 119.442727);
        LatLng llK = new LatLng(26.111010, 119.272727);
        LatLng llL = new LatLng(26.798889, 119.353535);
        LatLng llM = new LatLng(26.101010, 119.270897);
        LatLng llN = new LatLng(29.799999, 118.353535);
        LatLng ll1 = new LatLng(26.111111, 118.353535);
        LatLng ll2 = new LatLng(29.222222, 118.353535);
        LatLng ll3 = new LatLng(29.333333, 118.353535);
        LatLng ll4 = new LatLng(29.444444, 118.353535);
        LatLng ll5 = new LatLng(29.555555, 118.353535);

        List<MyItem> items = new ArrayList<MyItem>();
        items.add(new MyItem(llA));
        items.add(new MyItem(llB));
        items.add(new MyItem(llC));
        items.add(new MyItem(llD));
        items.add(new MyItem(llE));
        items.add(new MyItem(llF));
        items.add(new MyItem(llG));
        items.add(new MyItem(llH));
        items.add(new MyItem(llI));
        items.add(new MyItem(llJ));
        items.add(new MyItem(llK));
        items.add(new MyItem(llL));
        items.add(new MyItem(llM));
        items.add(new MyItem(llN));
        items.add(new MyItem(llO));
        items.add(new MyItem(llP));
        items.add(new MyItem(llQ));
        items.add(new MyItem(llX));
        items.add(new MyItem(llY));
        items.add(new MyItem(llZ));

        items.add(new MyItem(ll1));
        items.add(new MyItem(ll2));
        items.add(new MyItem(ll3));
        items.add(new MyItem(ll4));
        items.add(new MyItem(ll5));

        mClusterManager.addItems(items);

    }

    /**
     * 每个Marker点，包含Marker点坐标以及图标
     */
    public class MyItem implements ClusterItem {
        private final LatLng mPosition;

        public MyItem(LatLng latLng) {
            mPosition = latLng;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public BitmapDescriptor getBitmapDescriptor() {
            if (mPosition.longitude == 118.353535){
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark);
            }else {
                return BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_gcoding);
            }
        }
    }

    @Override
    public void onMapLoaded() {
        // TODO Auto-generated method stub
        ms = new MapStatus.Builder().zoom(9).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
    }

}
