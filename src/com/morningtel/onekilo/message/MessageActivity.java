package com.morningtel.onekilo.message;

import java.util.HashMap;
import java.util.LinkedList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.bundle.LauncherActivity;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.common.Conn;
import com.morningtel.onekilo.hot.WebInfoActivity;
import com.morningtel.onekilo.hot.WebInfoTabActivity;
import com.morningtel.onekilo.jpush.MyReceiver;
import com.morningtel.onekilo.model.Hot;
import com.morningtel.onekilo.model.JPushMessageModel;
import com.morningtel.onekilo.model.JsonParse;
import com.morningtel.onekilo.model.MessageStatusModel;
import com.morningtel.onekilo.sign.SignActivity;

public class MessageActivity extends BaseActivity {
	
	LinearLayout nav_back_layout=null;
	TextView nav_title=null;
	
	ListView message_listview=null;
	MessageAdapter adapter=null;
	
	LinkedList<MessageStatusModel> model_list=null;
	boolean isLoad=false;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message);
		
		model_list=Conn.getInstance(MessageActivity.this).getMessageStatusModelList();
		
		init();
	}
	
	BroadcastReceiver receiver=new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals(MyReceiver.MESSAGE_TAG)&&!isLoad) {
				String messge_result=intent.getExtras().getString("messge_result");
				String message_content=intent.getExtras().getString("message_content");
				JPushMessageModel temp_model=JsonParse.getJPushMessageModel(MessageActivity.this, messge_result, message_content);
				if(temp_model!=null) {
					checkContainsId(temp_model);
				}
			}
		}};
	
	private void init() {
		nav_back_layout=(LinearLayout) findViewById(R.id.nav_back_layout);
		nav_back_layout.setVisibility(View.GONE);
		nav_title=(TextView) findViewById(R.id.nav_title);
		nav_title.setText("消息");
		nav_title.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				
			}});
		message_listview=(ListView) findViewById(R.id.message_listview);
		message_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch(model_list.get(position).getMsgType()) {
				case 1:
					Hot hot=Conn.getInstance(MessageActivity.this).getHotModel(model_list.get(position).getId());
					if(hot!=null) {
						if(hot.getViewType()==Hot.CODE_VIEWTYPE&&android.os.Build.VERSION.SDK_INT<15) {
							CommonUtils.showCustomToast(MessageActivity.this, "您的系统版本过低，暂时不支持"+hot.getTabs().get(0).getName()+"功能");
							return ;
						}
						Intent intent=null;
						Bundle bundle=new Bundle();
						switch(hot.getViewType()) {
						case Hot.WEBVIEW_VIEWTYPE:
							intent=new Intent(MessageActivity.this, WebInfoActivity.class);
							bundle.putInt("tabId", hot.getTabs().get(0).getId());
							bundle.putString("hotName", hot.getHotName());
							bundle.putInt("needBar", hot.getTabs().get(0).getNeedBar());
							bundle.putString("api", hot.getTabs().get(0).getApi());
							bundle.putInt("btnType", hot.getBtnType());
							bundle.putSerializable("menu", hot.getMenus());
							break;
						case Hot.TABWEBVIEW_VIEWTYPE:
							intent=new Intent(MessageActivity.this, WebInfoTabActivity.class);
							bundle.putSerializable("tabs", hot.getTabs());
							bundle.putString("hotName", hot.getHotName());
							bundle.putSerializable("menu", hot.getMenus());
							bundle.putInt("btnType", hot.getBtnType());
							break;
						case Hot.LOCATION_VIEWTYPE:
							intent=new Intent(MessageActivity.this, SignActivity.class);
							bundle.putString("api", hot.getTabs().get(0).getApi());
							bundle.putString("hotName", hot.getTabs().get(0).getName());
							break;
						case Hot.VOICE_VIEWTYPE:
							break;
						case Hot.CODE_VIEWTYPE:
							intent=new Intent(MessageActivity.this, LauncherActivity.class);
							bundle.putString("api", hot.getTabs().get(0).getApi());
							bundle.putString("hotName", hot.getTabs().get(0).getName());
							break;
						}
						intent.putExtras(bundle);
						startActivity(intent);
					}
					break;
				case 2:
					Intent intent=new Intent(MessageActivity.this, WebInfoActivity.class);
					Bundle bundle=new Bundle();
					bundle.putInt("tabId", 0);
					bundle.putString("hotName", "写死的测试2");
					bundle.putInt("needBar", 0);
					bundle.putString("api", ((OneKiloApplication) getApplication()).webUrl+"/wuye/singletpc.html?id="+model_list.get(position).getAction()+"&token="+((OneKiloApplication) getApplication()).user.getToken());
					bundle.putInt("btnType", 0);
					bundle.putSerializable("menu", null);
					intent.putExtras(bundle);
					startActivity(intent);
					break;
				case 3:
					Intent intent3=new Intent(MessageActivity.this, WebInfoActivity.class);
					Bundle bundle3=new Bundle();
					bundle3.putInt("tabId", 0);
					bundle3.putString("hotName", "写死的测试3");
					bundle3.putInt("needBar", 0);
					bundle3.putString("api", model_list.get(position).getAction());
					bundle3.putInt("btnType", 0);
					bundle3.putSerializable("menu", null);
					intent3.putExtras(bundle3);
					startActivity(intent3);
					break;
				}
			}
		});
		adapter=new MessageAdapter(model_list, MessageActivity.this);
		message_listview.setAdapter(adapter);
		loadNoReadMessage();
		
		IntentFilter filter=new IntentFilter();
		filter.addAction(MyReceiver.MESSAGE_TAG);
		registerReceiver(receiver, filter);
		
	}
	
	private void loadNoReadMessage() {
		
		isLoad=true;
		
		final Handler handler=new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				isLoad=false;
				super.handleMessage(msg);
				if(msg.obj==null) {
					CommonUtils.showCustomToast(MessageActivity.this, "网络异常，请稍后再试");
				}
				else {
					String str=msg.obj.toString();
					if(CommonUtils.convertNull(str).equals("")) {
						CommonUtils.showCustomToast(MessageActivity.this, "网络异常，请稍后再试");
					}
					else {
						LinkedList<MessageStatusModel> model_list_temp=JsonParse.getMessageStatusModelList(str, MessageActivity.this);
						model_list.clear();
						model_list.addAll(model_list_temp);
						seqList();
						adapter.notifyDataSetChanged();
					}
				}
			}
		};
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message m=new Message();
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("token", CommonUtils.getLoginUser(MessageActivity.this).getToken());
				String result=CommonUtils.getWebData(map, ((OneKiloApplication) getApplicationContext()).webUrl+"message_loadNoReadMessage.do");
				m.obj=result;
				handler.sendMessage(m);
			}}).start();
	}
	
	/**
	 * 列表排序
	 */
	public void seqList() {
		int[] time_temp=new int[model_list.size()];
		HashMap<Integer, MessageStatusModel> time=new HashMap<Integer, MessageStatusModel>();
		for(int i=0;i<model_list.size();i++) {
			time.put(model_list.get(i).getSendDate(), model_list.get(i));
			time_temp[i]=model_list.get(i).getSendDate();
		}
		LinkedList<MessageStatusModel>  temp_list=new LinkedList<MessageStatusModel>();
		for(int j=0;j<time_temp.length;j++) {
			int pos=0;
			int max=0;
			for(int i=0;i<time_temp.length;i++) {
				if(time_temp[i]>max) {
					max=time_temp[i];
					pos=i;
				}
			}
			time_temp[pos]=-1;
			temp_list.add(time.get(max));
			model_list.clear();
			model_list.addAll(temp_list);
		}
		for(int i=0;i<model_list.size();i++) {
			System.out.println(model_list.get(i).getSendDate()+" "+model_list.get(i).getSendDate());			
		}
	}

	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
	/**
	 * 判断是否
	 * @param temp_model
	 */
	public void checkContainsId(JPushMessageModel temp_model) {
		boolean isContain=false;
		for(int i=0;i<model_list.size();i++) {
			if(model_list.get(i).getId()==temp_model.getId()) {
				if(model_list.get(i).getSendDate()<temp_model.getSendDate()) {
					int temp_noreadcount=model_list.get(i).getNoReadCount()+1;
					model_list.get(i).setNoReadCount(temp_noreadcount);
					model_list.get(i).setContent(temp_model.getContent());
					model_list.get(i).setSendDate(temp_model.getSendDate());
					model_list.get(i).setId(temp_model.getId());
					model_list.get(i).setAction(temp_model.getAction());
					model_list.get(i).setIcon(temp_model.getImageUrl());
					model_list.get(i).setMsgType(temp_model.getMsgType());
					model_list.get(i).setTitle(temp_model.getTitle());
					Conn.getInstance(MessageActivity.this).updateNoReadCount(temp_model.getId(), temp_noreadcount, temp_model.getSendDate(), temp_model.getImageUrl(), temp_model.getContent(), temp_model.getMsgType(), temp_model.getAction(), temp_model.getTitle());
				}				
				isContain=true;		
				break;
			}
		}
		if(!isContain) {
			getMessageUrl(temp_model);
		}
		else {
			seqList();
			adapter.notifyDataSetChanged();
		}		
	}
	
	public void getMessageUrl(final JPushMessageModel model_new) {
		MessageStatusModel model=new MessageStatusModel();
		model.setAction(model_new.getAction());
		model.setContent(model_new.getContent());
		model.setIcon(model_new.getImageUrl());
		model.setId(model_new.getId());
		model.setNoReadCount(1);
		model.setSendDate(model_new.getSendDate());
		model.setTitle(model_new.getTitle());
		model_list.add(0, model);
		Conn.getInstance(MessageActivity.this).insertModel(model);
		seqList();
		adapter.notifyDataSetChanged();
	}
}
