package com.morningtel.onekilo.mappois;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mobstat.StatService;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.myview.MyMapView;

public class MappoisActivity extends BaseActivity {
	
	MyMapView bmapView=null;
	LinearLayout nav_back_layout=null;
	TextView nav_title=null;
	
	private Button button=null;
	
	OneKiloApplication app=null;
	private MapController mMapController=null;
	private MyOverlay mOverlay=null;
	private PopupOverlay pop=null;
	private ArrayList<OverlayItem> mItems=null;
	private OverlayItem mCurItem=null;
	
	private ArrayList<String> geo_list=null; 
	
	public class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int arg0) {
			// TODO Auto-generated method stub
			CommonUtils.showCustomToast(MappoisActivity.this, "网络连接失败，请检查您的网络连接是否通畅");
		}

		@Override
		public void onGetPermissionState(int arg0) {
			// TODO Auto-generated method stub
			if(arg0!=0) {
				
			}
			CommonUtils.showCustomToast(MappoisActivity.this, "地图引擎未获取到足够的权限");
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		OneKiloApplication app=(OneKiloApplication)this.getApplication();
        if (app.mBMapManager==null) {
            app.mBMapManager=new BMapManager(this);
            app.mBMapManager.init(((OneKiloApplication) getApplication()).strKey,new MyGeneralListener());
        }
		
		setContentView(R.layout.activity_mappois);
		
		geo_list=getIntent().getStringArrayListExtra("geo_list");
		
		init();
	}
	
	public void init() {
		
		button =new Button(this);
        button.setBackgroundResource(R.drawable.popup);
		
        nav_title=(TextView) findViewById(R.id.nav_title);
		nav_title.setText(getIntent().getExtras().getString("hotName"));
		nav_back_layout=(LinearLayout) findViewById(R.id.nav_back_layout);
		nav_back_layout.setVisibility(View.VISIBLE);
		nav_back_layout.setOnClickListener(new LinearLayout.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}});
		
        bmapView=(MyMapView)findViewById(R.id.bmapView);
        mMapController=bmapView.getController();
        mMapController.enableClick(true);
        mMapController.setZoom(16);
        mMapController.setCenter(new GeoPoint((int) (Double.parseDouble(geo_list.get(0).split(",")[1])*1E6), (int) (Double.parseDouble(geo_list.get(0).split(",")[0])*1E6)));
        bmapView.setBuiltInZoomControls(true);
        
        initOverlay();
	}
	
	public void initOverlay(){
         mOverlay=new MyOverlay(getResources().getDrawable(R.drawable.icon_gcoding), bmapView);
         for(int i=0;i<geo_list.size();i++) {
        	 GeoPoint p1=new GeoPoint((int)(Double.parseDouble(geo_list.get(i).split(",")[1])*1E6),(int)(Double.parseDouble(geo_list.get(i).split(",")[0])*1E6));
             OverlayItem item1=new OverlayItem(p1,"覆盖物1","");
             item1.setMarker(getResources().getDrawable(R.drawable.icon_gcoding));
             
             mOverlay.addItem(item1);
         }
         
         mItems=new ArrayList<OverlayItem>();
         mItems.addAll(mOverlay.getAllItem());
         bmapView.getOverlays().add(mOverlay);
         bmapView.refresh();
         
         PopupClickListener popListener=new PopupClickListener(){
			@Override
			public void onClickedPopup(int index) {
				pop.hidePop();
				GeoPoint p=new GeoPoint(mCurItem.getPoint().getLatitudeE6()+5000, mCurItem.getPoint().getLongitudeE6()+5000);
				mCurItem.setGeoPoint(p);
				mOverlay.updateItem(mCurItem);
				bmapView.refresh();
			}
         };
         pop=new PopupOverlay(bmapView,popListener);
    }
	
	public class MyOverlay extends ItemizedOverlay {

		public MyOverlay(Drawable defaultMarker, MapView mapView) {
			super(defaultMarker, mapView);
		}
		

		@Override
		public boolean onTap(int index) {
			OverlayItem item = getItem(index);
			mCurItem = item ;
			button.setText("这是一个系统控件");
			GeoPoint pt = new GeoPoint((int) (Double.parseDouble(geo_list.get(index).split(",")[1]) * 1E6)+3000,
					(int) (Double.parseDouble(geo_list.get(index).split(",")[0]) * 1E6));
			// 弹出自定义View
			pop.showPopup(button, pt, 32);
			return true;
		}
		
		@Override
		public boolean onTap(GeoPoint pt , MapView mMapView){
			if (pop != null){
                pop.hidePop();
                mMapView.removeView(button);
			}
			return false;
		}
    	
    }
	
	/**
     * 清除所有Overlay
     * @param view
     */
    public void clearOverlay(View view){
    	mOverlay.removeAll();
    	if (pop != null){
            pop.hidePop();
    	}
    	bmapView.removeView(button);
    	bmapView.refresh();
    }
    /**
     * 重新添加Overlay
     * @param view
     */
    public void resetOverlay(View view){
    	clearOverlay(null);
    	//重新add overlay
    	mOverlay.addItem(mItems);
    	bmapView.refresh();
    }
   
    @Override
    protected void onPause() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
    	 */
    	bmapView.onPause();
        super.onPause();
        StatService.onPause(this);
    }
    
    @Override
    protected void onResume() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
    	 */
    	bmapView.onResume();
        super.onResume();
        StatService.onResume(this);
    }
    
    @Override
    protected void onDestroy() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
    	 */
    	bmapView.destroy();
        super.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	bmapView.onSaveInstanceState(outState);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	bmapView.onRestoreInstanceState(savedInstanceState);
    }
}
