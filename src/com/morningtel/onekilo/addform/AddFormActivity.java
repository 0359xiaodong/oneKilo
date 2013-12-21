package com.morningtel.onekilo.addform;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.lidroid.xutils.BitmapUtils;
import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.BitmapHelp;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.model.AddForm;
import com.morningtel.onekilo.model.ImageItemModel;
import com.morningtel.onekilo.myview.MyMapView;

public class AddFormActivity extends BaseActivity {
	
	TextView nav_title=null;
	LinearLayout nav_back_layout=null;
	LinearLayout nav_add_layout=null;
	
	EditText fatie_title=null;
	EditText fatie_text=null;
	LinearLayout temp_image_layout=null;
	LinearLayout fatie_menu=null;
	ImageView fatie_tianjiatupian=null;
	MyMapView bmapView=null;
	TextView nav_add_text=null;
	ImageView nav_add_image=null;
	
	//上传图片
	ArrayList<ImageItemModel> uploadImages=null;
		
	AddForm af=null;
	
	public static BitmapUtils bitmapUtils;
	
	OneKiloApplication app=null;
	
	private LocationClient mLocClient;
	public MyLocationListenner myListener=null;
	private MapController mMapController=null;
	MKMapViewListener mMapListener=null;
	TextView pop_local_name=null;
	PopupOverlay pop=null;
	LocationData locData=null;
	//定位图层
	LocationOverlay myLocationOverlay=null;
	
	public class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int arg0) {
			// TODO Auto-generated method stub
			showCustomToast("网络连接失败，请检查您的网络连接是否通畅");
		}

		@Override
		public void onGetPermissionState(int arg0) {
			// TODO Auto-generated method stub
			if(arg0!=0) {
				
			}
			showCustomToast("地图引擎未获取到足够的权限");
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		app=(OneKiloApplication)this.getApplication();
        if (app.mBMapManager==null) {
            app.mBMapManager=new BMapManager(this);
            app.mBMapManager.init(((OneKiloApplication) getApplication()).strKey, new MyGeneralListener());
        }
		
		setContentView(R.layout.activity_addform);
		
		af=getIntent().getExtras().getParcelable("AddForm");
		
		uploadImages=new ArrayList<ImageItemModel>();
		
		myListener=new MyLocationListenner();
        locData=new LocationData();
        mLocClient=new LocationClient(getApplicationContext());
        mLocClient.setAK(((OneKiloApplication) getApplication()).strKey);
        mLocClient.registerLocationListener(myListener);
        
        bitmapUtils = BitmapHelp.getBitmapUtils(getApplicationContext());
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		
		init();
	}
	
	public void init() {
		nav_back_layout=(LinearLayout) findViewById(R.id.nav_back_layout);
		nav_back_layout.setVisibility(View.VISIBLE);
		nav_back_layout.setOnClickListener(new LinearLayout.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}});
		nav_add_layout=(LinearLayout) findViewById(R.id.nav_add_layout);
		nav_add_layout.setVisibility(View.VISIBLE);
		nav_add_layout.setOnClickListener(new LinearLayout.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				uploadForm();
				finish();
			}});
		nav_title=(TextView) findViewById(R.id.nav_title);
		nav_title.setText(getIntent().getExtras().getString("hotName"));
		nav_add_text=(TextView) findViewById(R.id.nav_add_text);
		nav_add_text.setText(af.getBtnName());
		nav_add_text.setVisibility(View.VISIBLE);
		nav_add_image=(ImageView) findViewById(R.id.nav_add_image);
		nav_add_image.setVisibility(View.INVISIBLE);
		
		fatie_title=(EditText) findViewById(R.id.fatie_title);
		if(CommonUtils.convertNull(af.getNeedTitle()).equals("")) {
			fatie_title.setVisibility(View.GONE);
		}
		else {
			fatie_title.setHint(af.getNeedTitle());
		}
		fatie_text=(EditText) findViewById(R.id.fatie_text);
		if(CommonUtils.convertNull(af.getNeedContent()).equals("")) {
			fatie_text.setVisibility(View.GONE);
		}
		else {
			fatie_text.setHint(af.getNeedContent());
		}
		temp_image_layout=(LinearLayout) findViewById(R.id.temp_image_layout);
		fatie_menu=(LinearLayout) findViewById(R.id.fatie_menu);
		if(CommonUtils.convertNull(af.getNeedImage()).equals("")) {
			fatie_menu.setVisibility(View.GONE);
		}
		fatie_tianjiatupian=(ImageView) findViewById(R.id.fatie_tianjiatupian);
		fatie_tianjiatupian.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] path=new String[uploadImages.size()];
				for(int i=0;i<uploadImages.size();i++) {
					path[i]=uploadImages.get(i).imagePath;
				}
				Intent intent=new Intent(AddFormActivity.this, ImageChoiceActivity.class);
				Bundle bundle=new Bundle();
				bundle.putStringArray("uploadImage", path);
				intent.putExtras(bundle);
				startActivityForResult(intent, 203);
			}});
		bmapView=(MyMapView) findViewById(R.id.bmapView);
		mMapController=bmapView.getController();
		mMapController.enableClick(true);
		mMapController.setZoom(16);
		mMapController.setCenter(new GeoPoint((int) (32.047443*1E6), (int) (118.79065*1E6)));
		createPop();
		bmapView.regMapViewListener(app.mBMapManager, new MKMapViewListener(){

			@Override
			public void onClickMapPoi(MapPoi arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetCurrentMap(Bitmap arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onMapAnimationFinish() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onMapLoadFinish() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onMapMoveFinish() {
				// TODO Auto-generated method stub
				
			}});
		setLocationOption();
		//定位图层初始化
		myLocationOverlay=new LocationOverlay(bmapView);
		//设置定位数据
	    myLocationOverlay.setData(locData);
	    //添加定位图层
	    bmapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		//修改定位数据后刷新图层生效
		bmapView.refresh();
		if(CommonUtils.convertNull(af.getNeedMap()).equals("")) {
			bmapView.setVisibility(View.GONE);
		}
	}
	
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if(location==null) {
				return;
			}
			locData.latitude=location.getLatitude();
            locData.longitude=location.getLongitude();
            //如果不显示定位精度圈，将accuracy赋值为0即可
            locData.accuracy=location.getRadius();
            // 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
            locData.direction=location.getDerect();
            //更新定位数据
            myLocationOverlay.setData(locData);
            //更新图层数据执行刷新后生效
            bmapView.refresh();
            //移动到定位点
            mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
            myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
            
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			if(arg0==null) {
				return;
			}
		}
		
	}
	
	/**
	 * 开启定位服务
	 */
	private void setLocationOption() {
		LocationClientOption option=new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setScanSpan(120000);
		option.setPriority(LocationClientOption.NetWorkFirst);
		option.setPoiNumber(30);
		option.disableCache(true);		
		mLocClient.setLocOption(option);		
		
		mLocClient.start();
		mLocClient.requestLocation();
	}
	
	public void createPop() {
		View view=LayoutInflater.from(AddFormActivity.this).inflate(R.layout.pop_view, null);
		pop_local_name=(TextView) view.findViewById(R.id.pop_local_name);
		PopupClickListener pop_lis=new PopupClickListener() {
			
			@Override
			public void onClickedPopup(int arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		pop=new PopupOverlay(bmapView, pop_lis);
		MyMapView.pop=pop;
	}
	
	/**
	 * 继承MyLocationOverlay重写dispatchTap实现点击处理
	 * @author r17171709
	 *
	 */
  	public class LocationOverlay extends MyLocationOverlay{

  		public LocationOverlay(MapView mapView) {
  			super(mapView);
  			// TODO Auto-generated constructor stub
  		}
  		
  		@Override
  		protected boolean dispatchTap() {
  			// TODO Auto-generated method stub
  			//处理点击事件,弹出泡泡
  			pop_local_name.setBackgroundResource(R.drawable.popup);
  			pop_local_name.setText("我的位置");
			pop.showPopup(getBitmapFromView(pop_local_name),
					new GeoPoint((int)(locData.latitude*1e6), (int)(locData.longitude*1e6)), 20);
  			return true;
  		}
  		
  	}
  	
  	/**
	 * 从view 得到图片
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		bmapView.onResume();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		bmapView.onPause();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mLocClient.stop();
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
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode==RESULT_OK&&requestCode==203) {
			uploadImages.clear();
			Bundle bundle=data.getExtras();
			ArrayList<ImageItemModel> model_list=bundle.getParcelableArrayList("upload");
			uploadImages.addAll(model_list);
			temp_image_layout.removeAllViews();
			for(int i=0;i<model_list.size();i++) {
				View view=LayoutInflater.from(AddFormActivity.this).inflate(R.layout.choice_image_view, null);
				final int currentNum=i;
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					}});
				ImageView choice_image_commit=(ImageView) view.findViewById(R.id.choice_image_commit);
				bitmapUtils.display(choice_image_commit, model_list.get(i).imagePath);
				temp_image_layout.addView(view);
			}
		}
	}
    
    private void uploadForm() {
    	final Handler handler=new Handler() {
    		@Override
    		public void handleMessage(Message msg) {
    			// TODO Auto-generated method stub
    			super.handleMessage(msg);
    			//String result=msg.obj.toString();
    		}
    	};
    	
    	new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message m=new Message();
				HashMap<String, String> map=new HashMap<String, String>();
				if(!CommonUtils.convertNull(af.getNeedTitle()).equals("")) {
					map.put("title", fatie_title.getText().toString());
				}
				if(!CommonUtils.convertNull(af.getNeedContent()).equals("")) {
					map.put("content", fatie_text.getText().toString());
				}
				if(!CommonUtils.convertNull(af.getNeedMap()).equals("")) {
					map.put("mapGeo", ""+locData.longitude+","+locData.latitude);
				}
				else if(!CommonUtils.convertNull(af.getNeedGeo()).equals("")) {
					map.put("geo", ""+locData.longitude+","+locData.latitude);
				}
				if(uploadImages.size()>0) {
					String[] imageFile=new String[uploadImages.size()];
					for(int i=0;i<uploadImages.size();i++) {
						imageFile[i]=uploadImages.get(i).imagePath;
					}
					if(af.getOtherParam().equals("")) {
						m.obj=CommonUtils.post(af.getApi()+"?token="+CommonUtils.getLoginUser(AddFormActivity.this).getToken(), map, imageFile, "image");
					}
					else {
						m.obj=CommonUtils.post(af.getApi()+"?token="+CommonUtils.getLoginUser(AddFormActivity.this).getToken()+"&"+af.getOtherParam(), map, imageFile, "image");
					}
				}
				else {
					if(af.getOtherParam().equals("")) {
						m.obj=CommonUtils.getWebData(map, af.getApi()+"?token="+CommonUtils.getLoginUser(AddFormActivity.this).getToken());
					}
					else {
						m.obj=CommonUtils.getWebData(map, af.getApi()+"?token="+CommonUtils.getLoginUser(AddFormActivity.this).getToken()+"&"+af.getOtherParam());
					}
				}
				handler.sendMessage(m);
			}}).start();
    }

}
