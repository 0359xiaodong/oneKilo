package com.morningtel.onekilo.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.baidu.mapapi.map.*;

public class MyMapView extends MapView {
	
	//��������ͼ�㣬���ͼ��ʹ��
	public static PopupOverlay pop=null;

	public MyMapView(Context arg0, AttributeSet arg1, int arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	public MyMapView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public MyMapView(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event){
		if(!super.onTouchEvent(event)) {
			//��������
			if(pop!=null&&event.getAction()==MotionEvent.ACTION_UP)
				pop.hidePop();
		}
		return true;
	}

}
