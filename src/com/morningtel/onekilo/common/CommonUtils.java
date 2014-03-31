package com.morningtel.onekilo.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.model.User;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class CommonUtils {
	public static String getWebData(HashMap<String, String> map, String url) {
		ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
		Iterator<Entry<String, String>> it=map.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> entry=it.next();
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		HttpPost post=new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));//设置post参数 并设置编码格式
			post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			DefaultHttpClient client=new DefaultHttpClient();
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			HttpResponse resp=client.execute(post);
			if(resp.getStatusLine().getStatusCode()==200) {
				return EntityUtils.toString(resp.getEntity());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static String convertNull(String returnValue) {
        try {
            returnValue = (returnValue==null||(returnValue!=null&&returnValue.equals("null")))?"":returnValue;
        } catch (Exception e) {
            returnValue = "";
        }
        return returnValue;
    }
	
	/**
	 * 获取屏幕密度信息
	 * @param context
	 * @return
	 */
	public static float getDisplayParams(Context context) {
		DisplayMetrics dm=new DisplayMetrics();
		dm=context.getApplicationContext().getResources().getDisplayMetrics();
		return dm.density;
	}
	
	/**
	 * 获取用户对象
	 * @param context
	 * @return
	 */
	public static User getLoginUser(Context context) {
		if(((OneKiloApplication) context.getApplicationContext()).user==null) {
			return Conn.getInstance(context).getLoginUser();
		}
		else {
			return ((OneKiloApplication) context.getApplicationContext()).user;
		}
	}
	
	/**
	 * 自定义弹出框
	 * @param context
	 * @param str
	 */
	public static void showCustomToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 设置登陆状态
	 * @param context
	 * @param token
	 * @param flag
	 */
	public static void setLoginState(Context context, String token, boolean flag) {
		SharedPreferences sp=context.getSharedPreferences("onekilo", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		editor.putBoolean(token, flag);
		editor.commit();
	};
	
	/**
	 * 判断是否已经下线
	 * @param context
	 * @param token
	 * @return
	 */
	public static boolean isAlreadyLoginOut(Context context, String token) {
		SharedPreferences sp=context.getSharedPreferences("onekilo", Activity.MODE_PRIVATE);
		return sp.getBoolean(token, false);
	}
	
	public static String getimageUploadPath(String srcPath, int pos) {  
        BitmapFactory.Options newOpts=new BitmapFactory.Options();  
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds=true;  
        Bitmap bitmap=BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空            
        newOpts.inJustDecodeBounds=false;  
        int w=newOpts.outWidth;  
        int h=newOpts.outHeight;  
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
        float hh=800f;//这里设置高度为800f  
        float ww=480f;//这里设置宽度为480f  
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be=1;//be=1表示不缩放  
        if (w>h&&w>ww) {//如果宽度大的话根据宽度固定大小缩放  
            be=(int) (newOpts.outWidth/ww);  
        } 
        else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
            be=(int) (newOpts.outHeight / hh);  
        }  
        if (be<=0)  
            be=1;  
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        Bitmap bitmap_new=BitmapFactory.decodeFile(srcPath, newOpts);  

        Matrix m=new Matrix(); 
        m.setRotate(getExifOrientation(srcPath), bitmap_new.getWidth()/2, bitmap_new.getHeight()/2);
        Bitmap bitmap_file=Bitmap.createBitmap(bitmap_new, 0, 0, bitmap_new.getWidth(), bitmap_new.getHeight(), m, true);
        
        File uploadFile=new File(Environment.getExternalStorageDirectory().getPath()+"/onekilo/temp/temp_"+pos+".jpg");
        try {
        	if(!uploadFile.exists()) {
            	uploadFile.createNewFile();			
    		}
    		FileOutputStream fos=new FileOutputStream(uploadFile);
    		bitmap_file.compress(Bitmap.CompressFormat.JPEG, 30, fos);
    		fos.flush();
    		fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        
        if(bitmap!=null&&!bitmap.isRecycled()) {
        	bitmap.recycle();
        	bitmap=null;
        }
        if(bitmap_new!=null&&!bitmap_new.isRecycled()) {
        	bitmap_new.recycle();
        	bitmap_new=null;
        }
        if(bitmap_file!=null&&!bitmap_file.isRecycled()) {
        	bitmap_file.recycle();
        	bitmap_file=null;
        }
        return uploadFile.getAbsolutePath();
    } 
	
	/**
     * 根据图片的文件信息,返回图片的角度;
     * @param filepath
     * @return
     */
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {

        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
 
            }
        }
        return degree;
    }
    
    /**
     * 保存服务列表更新时间
     * @param context
     * @param updateTime
     */
    public static void saveHotUpdateTime(Context context, String updateTime) {
    	SharedPreferences sp=context.getSharedPreferences("onekilo", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		editor.putString(((OneKiloApplication) context.getApplicationContext()).user.getId()+"_hot", updateTime);
		editor.commit();
    }
    
    /**
     * 获取服务列表更新时间
     * @param context
     * @return
     */
    public static String getHotUpdateTime(Context context) {
    	SharedPreferences sp=context.getSharedPreferences("onekilo", Activity.MODE_PRIVATE);
    	return sp.getString(((OneKiloApplication) context.getApplicationContext()).user.getId()+"_hot", "-1");
    }
    
    /**
     * 获取圈子列表更新时间
     * @param context
     * @param updateTime
     */
    public static void saveCommunicateUpdateTime(Context context, String updateTime) {
    	SharedPreferences sp=context.getSharedPreferences("onekilo", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		editor.putString(((OneKiloApplication) context.getApplicationContext()).user.getId()+"_com", updateTime);
		editor.commit();
    }
    
    /**
     * 获取服务列表更新时间
     * @param context
     * @return
     */
    public static String getCommunicateUpdateTime(Context context) {
    	SharedPreferences sp=context.getSharedPreferences("onekilo", Activity.MODE_PRIVATE);
    	return sp.getString(((OneKiloApplication) context.getApplicationContext()).user.getId()+"_com", "-1");
    }
}
