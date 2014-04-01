package com.morningtel.onekilo.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.common.Conn;
import com.morningtel.onekilo.service.MusicService;

public class JsonParse {
	
	/**
	 * 鉴权
	 * @param str
	 * @return
	 */
	public static boolean checkPermission(String str, Context context) {
		
		try {
			JSONObject obj=new JSONObject(str);
			int status=obj.getInt("status");
			if(status==1) {
				return true;
			}
			
			try {
				CommonUtils.showCustomToast(context, new String(obj.getString("data").getBytes("iso-8859-1"), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	} 

	/**
	 * 获取服务列表
	 * @param str
	 * @return
	 */
	public static ArrayList<Hot> getServiceList(String str, Context context) {
		ArrayList<Hot> hot_list=new ArrayList<Hot>();
		try {
			JSONObject obj=new JSONObject(str);
			int status=obj.getInt("status");
			//无返回时
			if(status==0) {
				return null;
			}
			//无更新数据时
			else if(status==1) {
				return Conn.getInstance(context).getHotModelList();
			}
			//有更新返回时
			Conn.getInstance(context).deleteHotModel();
			CommonUtils.saveHotUpdateTime(context, ""+status);
			JSONArray data_array=obj.getJSONArray("data");
			for(int i=0;i<data_array.length();i++) {
				JSONObject hot_obj=data_array.getJSONObject(i);
				Hot hot=getHot(hot_obj, context);
				hot_list.add(hot);
				Conn.getInstance(context).insertModel(hot);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		return hot_list;
	}
	
	/**
	 * 获取圈子列表
	 * @param str
	 * @return
	 */
	public static ArrayList<Group> getCommunicateList(String str, Context context) {
		ArrayList<Group> group_list=new ArrayList<Group>();		
		try {
			JSONObject obj=new JSONObject(str);
			int status=obj.getInt("status");
			//无返回时
			if(status==0) {
				return null;
			}
			//无更新数据时
			else if(status==1) {
				return Conn.getInstance(context).getGroupModelList();
			}
			//有更新返回时
			Conn.getInstance(context).deleteGroup();
			CommonUtils.saveCommunicateUpdateTime(context, ""+status);
			JSONArray data_array=obj.getJSONArray("data");
			for(int i=0;i<data_array.length();i++) {
				JSONObject group_obj=data_array.getJSONObject(i);
				Group group=getGroup(group_obj, context);
				group_list.add(group);
				Conn.getInstance(context).insertModel(group);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return group_list;
	}
	
	private static Tab getTab(JSONObject tabs_obj) {
		Tab tab=new Tab();
		try {
			tab.setBelongId(tabs_obj.getString("belongId"));
			tab.setId(tabs_obj.getInt("id"));
			tab.setNeedGeo(tabs_obj.getInt("needGeo"));
			tab.setApi(tabs_obj.getString("api"));
			tab.setNeedSearch(tabs_obj.getInt("needSearch"));
			tab.setNeedBar(tabs_obj.getInt("needBar"));
			tab.setOrderNum(tabs_obj.getInt("orderNum"));
			tab.setViewType(tabs_obj.getInt("viewType"));
			tab.setName(new String(tabs_obj.getString("name").getBytes("iso-8859-1"), "utf-8"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tab;
	}
	
	private static AddForm getAddForm(JSONObject addForm_obj, boolean isFormat) {
		AddForm af=new AddForm();
		try {
			af.setNeedMap(addForm_obj.getString("needMap"));
			if(isFormat) {
				af.setNeedTitle(new String(addForm_obj.getString("needTitle").getBytes("iso-8859-1"), "utf-8"));
				af.setNeedContent(new String(addForm_obj.getString("needContent").getBytes("iso-8859-1"), "utf-8"));
				af.setBtnName(new String(addForm_obj.getString("btnName").getBytes("iso-8859-1"), "utf-8"));
			}
			else {
				af.setNeedTitle(addForm_obj.getString("needTitle"));
				af.setNeedContent(addForm_obj.getString("needContent"));
				af.setBtnName(addForm_obj.getString("btnName"));
			}
			af.setNeedImage(addForm_obj.getString("needImage"));
			af.setNeedGeo(addForm_obj.getString("needGeo"));
			af.setNeedAnonymity(addForm_obj.getString("needAnonymity"));
			af.setApi(addForm_obj.getString("api"));
			af.setOtherParam(addForm_obj.getString("otherParam"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return af;
	}
	
	private static Menu getMenu(JSONObject menu_obj) {
		Menu menu=new Menu();
		try {
			menu.setBelongId(menu_obj.getString("belongId"));
			menu.setMenuType(menu_obj.getInt("menuType"));
			menu.setId(menu_obj.getInt("id"));
			menu.setApi(menu_obj.getString("api"));
			menu.setOrderNum(menu_obj.getInt("orderNum"));
			menu.setUserAuth(menu_obj.getInt("userAuth"));
			menu.setName(new String(menu_obj.getString("name").getBytes("iso-8859-1"), "utf-8"));
			if(!CommonUtils.convertNull(menu_obj.getString("addForm")).equals("")) {
				JSONObject addForm_obj=new JSONObject(menu_obj.getString("addForm"));
				AddForm af=getAddForm(addForm_obj, true);
				menu.setAddForm(af);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return menu;
	}
	
	private static Hot getHot(JSONObject hot_obj, Context context) {
		Hot hot=new Hot();
		try {
			hot.setId(hot_obj.getInt("id"));
			hot.setHotName(new String(hot_obj.getString("hotName").getBytes("iso-8859-1"), "utf-8"));
			hot.setHotType(hot_obj.getInt("hotType"));
			hot.setHotTo(hot_obj.getInt("hotTo"));
			hot.setHotFor(hot_obj.getInt("hotFor"));
			hot.setMessageIconUrl(hot_obj.getString("messageIconUrl"));
			hot.setHotForgender(hot_obj.getInt("hotForgender"));
			hot.setHotForCity(hot_obj.getInt("hotForCity"));
			hot.setIconFileName(hot_obj.getString("iconFileName"));
			hot.setOrderNum(hot_obj.getInt("orderNum"));
			hot.setIconContentType(hot_obj.getString("iconContentType"));
			hot.setHotTopic(hot_obj.getString("hotTopic"));
			hot.setHotMore(hot_obj.getInt("hotMore"));
			hot.setIconUrl(hot_obj.getString("iconUrl"));			
			hot.setMessageUrl(hot_obj.getString("messageUrl"));
			hot.setHotDesc(hot_obj.getString("hotDesc"));
			hot.setViewType(hot_obj.getInt("viewType"));
			hot.setNeedBar(hot_obj.getInt("needBar"));
			hot.setBtnType(hot_obj.getInt("btnType"));
			hot.setLongPressType(hot_obj.getInt("longPressType"));
			JSONArray menus_array=hot_obj.getJSONArray("menus");
			ArrayList<Menu> menus=new ArrayList<Menu>();
			for(int j=0;j<menus_array.length();j++) {
				JSONObject menu_obj=menus_array.getJSONObject(j);
				Menu menu=getMenu(menu_obj)	;				
				menus.add(menu);
			}
			hot.setMenus(menus);
			ArrayList<Tab> tabs=new ArrayList<Tab>();
			JSONArray tabs_array=hot_obj.getJSONArray("tabs");
			for(int j=0;j<tabs_array.length();j++) {
				JSONObject tabs_obj=tabs_array.getJSONObject(j);
				Tab tab=getTab(tabs_obj);
				tabs.add(tab);
			}
			hot.setTabs(tabs);
			JSONObject image_obj=hot_obj.getJSONObject("imageUrl");
			if(CommonUtils.getDisplayParams(context)>=2.0) {
				hot.setImageUrl(image_obj.getString("maxImg"));
			}
			else if(CommonUtils.getDisplayParams(context)>=1.5) {
				hot.setImageUrl(image_obj.getString("middleImg"));
			}
			else {
				hot.setImageUrl(image_obj.getString("smallImg"));
			}
			if(hot_obj.getInt("longPressType")!=0) {
				JSONObject long_tabs_obj=hot_obj.getJSONObject("longPressTab");
				Tab long_tab=getTab(long_tabs_obj);
				hot.setLongPressTab(long_tab);
			}			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return hot;
	}
	
	private static Group getGroup(JSONObject group_obj, Context context) {
		Group group=new Group();
		try {
			group.setRootId(group_obj.getString("rootId"));
			group.setIcon(group_obj.getString("icon"));
			group.setUserAuth(group_obj.getInt("userAuth"));
			group.setIconFileName(group_obj.getString("iconFileName"));
			group.setIconContentType(group_obj.getString("iconContentType"));
			group.setName(new String(group_obj.getString("name").getBytes("iso-8859-1"), "utf-8"));
			group.setIconUrl(group_obj.getString("iconUrl"));
			group.setGroupType(group_obj.getInt("groupType"));
			group.setId(group_obj.getString("id"));
			group.setParentId(group_obj.getString("parentId"));
			group.setMinType(group_obj.getInt("minType"));
			group.setCreateDate(group_obj.getInt("createDate"));
			group.setDescription(new String(CommonUtils.convertNull(group_obj.getString("description")).getBytes("iso-8859-1"), "utf-8"));
			group.setBtnType(group_obj.getInt("btnType"));
			group.setViewType(group_obj.getInt("viewType"));
			JSONArray menus_array=group_obj.getJSONArray("menus");
			ArrayList<Menu> menus=new ArrayList<Menu>();
			for(int j=0;j<menus_array.length();j++) {
				JSONObject menu_obj=menus_array.getJSONObject(j);
				Menu menu=getMenu(menu_obj)	;				
				menus.add(menu);
			}
			group.setMenus(menus);
			ArrayList<Tab> tabs=new ArrayList<Tab>();
			JSONArray tabs_array=group_obj.getJSONArray("tabs");
			for(int j=0;j<tabs_array.length();j++) {
				JSONObject tabs_obj=tabs_array.getJSONObject(j);
				Tab tab=getTab(tabs_obj);
				tabs.add(tab);
			}
			group.setTabs(tabs);
			JSONObject image_obj=group_obj.getJSONObject("imageUrl");
			if(CommonUtils.getDisplayParams(context)>=2.0) {
				group.setImageUrl(image_obj.getString("maxImg"));
			}
			else if(CommonUtils.getDisplayParams(context)>=1.5) {
				group.setImageUrl(image_obj.getString("middleImg"));
			}
			else {
				group.setImageUrl(image_obj.getString("smallImg"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return group;
	}
	
	/**
	 * 用户登陆
	 * @param str
	 * @return
	 */
	public static User getLoginUser(String str, Context context) {
		User user=new User();
		try {
			JSONObject obj=new JSONObject(str);
			if(!checkPermission(str, context)) {
				return null;
			}
			JSONObject data_obj=obj.getJSONObject("data");
			user.setAccount(new String(data_obj.getString("account").getBytes("iso-8859-1"), "utf-8"));
			user.setStatus(data_obj.getInt("ustatus"));
			user.setNickName(new String(data_obj.getString("nickName").getBytes("iso-8859-1"), "utf-8"));
			user.setLastTime(data_obj.getInt("lastTime"));
			user.setName(new String(data_obj.getString("name").getBytes("iso-8859-1"), "utf-8"));
			user.setUserType(data_obj.getInt("userType"));
			user.setId(data_obj.getString("id"));
			user.setToken(data_obj.getString("token"));
			user.setAddress(new String(data_obj.getString("address").getBytes("iso-8859-1"), "utf-8"));
			if(!CommonUtils.convertNull(data_obj.getString("imageUrl")).equals("")) {
				JSONObject image_obj=data_obj.getJSONObject("imageUrl");
				if(CommonUtils.getDisplayParams(context)>=2.0) {
					user.setImageUrl(image_obj.getString("maxImg"));
				}
				else if(CommonUtils.getDisplayParams(context)>=1.5) {
					user.setImageUrl(image_obj.getString("middleImg"));
				}
				else {
					user.setImageUrl(image_obj.getString("smallImg"));
				}
			}			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return user;
	}
	
	/**
	 * 获取网页部分点击对象
	 * @param str
	 * @return
	 */
	public static AddForm getWebAddForm(String str, boolean isFormat) {
		try {
			JSONObject obj=new JSONObject(str);
			return getAddForm(obj, isFormat);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * poi位置信息
	 * @param str
	 * @return
	 */
	public static ArrayList<String> getGeoList(String str) {
		ArrayList<String> geo_list=new ArrayList<String>();
		try {
			JSONArray array=new JSONArray(str);
			for(int i=0;i<array.length();i++) {
				JSONObject geo_obj=array.getJSONObject(i);
				geo_list.add(geo_obj.getString("geo"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return geo_list;
	}
	
	/**
	 * 判断签到是否成功
	 * @param str
	 * @param context
	 * @return
	 */
	public static String isSignOK(String str, Context context) {
		try {
			JSONObject obj=new JSONObject(str);
			if(!checkPermission(str, context)) {
				return "0:网络异常，请稍后再试";
			}
			JSONObject data_obj=obj.getJSONObject("data");
			//语音提示
			Intent intent=new Intent(context, MusicService.class);
			Bundle bundle=new Bundle();
			if(data_obj.getString("midname").equals("dayu20mi_shibai")) {
				bundle.putInt("music", R.raw.dayu20mi_shibai);
			}			
			else if(data_obj.getString("midname").equals("qiandaochenggong")) {
				bundle.putInt("music", R.raw.qiandaochenggong);
			}
			else if(data_obj.getString("midname").equals("xiaoyu30fenzhong_shibai")) {
				bundle.putInt("music", R.raw.xiaoyu30fenzhong_shibai);
			}
			intent.putExtras(bundle);
			context.startService(intent);
			
			if(data_obj.getInt("result")==1) {
				return "1:"+new String(data_obj.getString("message").getBytes("iso-8859-1"), "utf-8");
			}
			else if(data_obj.getInt("result")==0) {
				return "0:"+new String(data_obj.getString("message").getBytes("iso-8859-1"), "utf-8");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "0:网络异常，请稍后再试";
	} 
	
	/**
	 * 获取消息列表
	 * @param value
	 * @param context
	 * @return
	 */
	public static LinkedList<MessageStatusModel> getMessageStatusModelList(String value, Context context) {
		int databaseNum=Conn.getInstance(context).getMessageStatusModelList().size();
		try {
			JSONObject obj=new JSONObject(value);
			JSONArray array=new JSONArray(obj.getString("data"));
			for(int i=0;i<array.length();i++) {
				MessageStatusModel model=new MessageStatusModel();
				JSONObject data_obj=array.getJSONObject(i);
				model.setAction(data_obj.getString("action"));
				model.setFromId(data_obj.getString("fromId"));
				String temp=new String(data_obj.getString("fromName").getBytes("iso-8859-1"), "utf-8");
				model.setFromName(temp);
				model.setIcon(data_obj.getString("imageUrl"));
				model.setId(data_obj.getInt("id"));
				model.setNoReadCount(data_obj.getInt("noReadCount"));
				model.setSendDate(data_obj.getInt("sendDate"));
				model.setToId(data_obj.getString("toId"));
				model.setMsgId(data_obj.getString("msgId"));
				model.setTitle(new String(data_obj.getString("title").getBytes("iso-8859-1"), "utf-8"));
				model.setContent(new String(data_obj.getString("content").getBytes("iso-8859-1"), "utf-8"));
				model.setMsgType(data_obj.getInt("msgType"));
				model.setMsgMinType(data_obj.getInt("msgMinType"));
				if(databaseNum==0) {
					Conn.getInstance(context).insertModel(model);
				}	
				else {
					if(Conn.getInstance(context).isMessageExists(Integer.parseInt(data_obj.getString("id")))) {
						Conn.getInstance(context).updateNoReadCount(Integer.parseInt(data_obj.getString("id")), data_obj.getInt("noReadCount"), data_obj.getInt("sendDate"), data_obj.getString("imageUrl"), new String(data_obj.getString("content").getBytes("iso-8859-1"), "utf-8"), data_obj.getInt("msgType"), data_obj.getString("action"), new String(data_obj.getString("title").getBytes("iso-8859-1"), "utf-8"));
					}
					else {
						Conn.getInstance(context).insertModel(model);
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Conn.getInstance(context).getMessageStatusModelList();
	}
	
	/**
	 * 极光推送消息传递
	 * @param str
	 * @return
	 */
	public static JPushMessageModel getJPushMessageModel(Context context, String str, String content) {
		JPushMessageModel model=new JPushMessageModel();
		try {
			JSONObject obj=new JSONObject(str);
			model.setContent(content);
			model.setId(obj.getInt("id"));
			model.setAction(obj.getString("action"));
			model.setMsgType(obj.getInt("msgType"));
			model.setSendDate(obj.getInt("sendDate"));
			model.setImageUrl(obj.getString("imageUrl"));
			model.setTitle(obj.getString("title"));
			return model;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
