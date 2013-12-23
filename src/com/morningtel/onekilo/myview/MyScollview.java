package com.morningtel.onekilo.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScollview extends ScrollView {
	
	GestureDetector gestureDetector;

	public MyScollview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public MyScollview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public MyScollview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		gestureDetector=new GestureDetector(new Yscroll());
		setFadingEdgeLength(0);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.onInterceptTouchEvent(ev) && gestureDetector.onTouchEvent(ev);  
	}
	
	class Yscroll extends SimpleOnGestureListener{

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			//控制手指滑动的距离
			if (Math.abs(distanceY)>=Math.abs(distanceX)) {
				return true;
			}
				return false;
			}
	}
}
