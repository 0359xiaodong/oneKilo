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
			post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));//����post���� �����ñ����ʽ
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
	 * ��ȡ��Ļ�ܶ���Ϣ
	 * @param context
	 * @return
	 */
	public static float getDisplayParams(Context context) {
		DisplayMetrics dm=new DisplayMetrics();
		dm=context.getApplicationContext().getResources().getDisplayMetrics();
		return dm.density;
	}
	
	/**
	 * ��ȡ�û�����
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
	 * �Զ��嵯����
	 * @param context
	 * @param str
	 */
	public static void showCustomToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * ���õ�½״̬
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
	 * �ж��Ƿ��Ѿ�����
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
        //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
        newOpts.inJustDecodeBounds=true;  
        Bitmap bitmap=BitmapFactory.decodeFile(srcPath,newOpts);//��ʱ����bmΪ��            
        newOpts.inJustDecodeBounds=false;  
        int w=newOpts.outWidth;  
        int h=newOpts.outHeight;  
        //���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ  
        float hh=800f;//�������ø߶�Ϊ800f  
        float ww=480f;//�������ÿ��Ϊ480f  
        //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��  
        int be=1;//be=1��ʾ������  
        if (w>h&&w>ww) {//�����ȴ�Ļ����ݿ�ȹ̶���С����  
            be=(int) (newOpts.outWidth/ww);  
        } 
        else if (w < h && h > hh) {//����߶ȸߵĻ����ݿ�ȹ̶���С����  
            be=(int) (newOpts.outHeight / hh);  
        }  
        if (be<=0)  
            be=1;  
        newOpts.inSampleSize = be;//�������ű���  
        //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
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
     * ����ͼƬ���ļ���Ϣ,����ͼƬ�ĽǶ�;
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
     * ��������б����ʱ��
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
     * ��ȡ�����б����ʱ��
     * @param context
     * @return
     */
    public static String getHotUpdateTime(Context context) {
    	SharedPreferences sp=context.getSharedPreferences("onekilo", Activity.MODE_PRIVATE);
    	return sp.getString(((OneKiloApplication) context.getApplicationContext()).user.getId()+"_hot", "-1");
    }
    
    /**
     * ��ȡȦ���б����ʱ��
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
     * ��ȡ�����б����ʱ��
     * @param context
     * @return
     */
    public static String getCommunicateUpdateTime(Context context) {
    	SharedPreferences sp=context.getSharedPreferences("onekilo", Activity.MODE_PRIVATE);
    	return sp.getString(((OneKiloApplication) context.getApplicationContext()).user.getId()+"_com", "-1");
    }
}
