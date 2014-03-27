package com.morningtel.onekilo.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.morningtel.onekilo.model.Group;
import com.morningtel.onekilo.model.Hot;
import com.morningtel.onekilo.model.MessageStatusModel;
import com.morningtel.onekilo.model.SubjectModel;
import com.morningtel.onekilo.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conn extends SQLiteOpenHelper {
	
	final static String DATABASE_NAME="onekilo";
	final static int DATABASE_VERSION=1;
	
	final static String _ID="_id";
	
	//��滺�����
	final static String SUBJECT_TOPIC="subject_topic";
	final static String SUBJECT_INFO="subject_info";
	
	//�����б������
	final static String HOT_TOPIC="hot_topic";
	final static String HOT_INFO="hot_info";
	final static String HOT_ID="hot_id";
	
	//�����б������
	final static String GROUP_TOPIC="group_topic";
	final static String GROUP_INFO="group_info";
	final static String GROUP_ID="group_id";
	
	//��Ϣ�������
	final static String MESSAGE_TOPIC="message_topic";
	final static String MESSAGE_ID="message_id";
	final static String MESSAGE_INFO="message_info";
	
	//�û��������
	final static String USER_TOPIC="subject_topic";
	final static String USER_INFO="subject_info";
	
	Context context=null;
	
	static Conn conn=null;
	
	public static Conn getInstance(Context context) {
		if(conn==null) {
			conn=new Conn(context);
		}
		return conn;
	}

	private Conn(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table if not exists "+SUBJECT_TOPIC+"("+_ID+" integer primary key autoincrement not null, "+SUBJECT_INFO+" blob)");
		db.execSQL("create table if not exists "+MESSAGE_TOPIC+"("+_ID+" integer primary key autoincrement not null, "+MESSAGE_INFO+" blob, "+MESSAGE_ID+" integer)");
		db.execSQL("create table if not exists "+HOT_TOPIC+"("+_ID+" integer primary key autoincrement not null, "+HOT_INFO+" blob, "+HOT_ID+" integer)");	
		db.execSQL("create table if not exists "+GROUP_TOPIC+"("+_ID+" integer primary key autoincrement not null, "+GROUP_INFO+" blob, "+GROUP_ID+" integer)");	
		db.execSQL("create table if not exists "+USER_TOPIC+"("+_ID+" integer primary key autoincrement not null, "+USER_INFO+" blob)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * �����󱣴浽���ݿ���
	 * @param model
	 */
	public void insertModel(Object obj) {
		synchronized (this) {
			ContentValues values=null;
			if(obj instanceof SubjectModel) {
				byte[] bytes=serialize((SubjectModel) obj);
				values=new ContentValues(1);
				values.put(SUBJECT_INFO, bytes);
			}
			else if(obj instanceof MessageStatusModel) {
				byte[] bytes=serialize((MessageStatusModel) obj);
				values=new ContentValues(2);
				values.put(MESSAGE_INFO, bytes);
				values.put(MESSAGE_ID, ((MessageStatusModel) obj).getFromId());
			}
			else if(obj instanceof Hot) {
				byte[] bytes=serialize((Hot) obj);
				values=new ContentValues(2);
				values.put(HOT_INFO, bytes);
				values.put(HOT_ID, ((Hot) obj).getId());
			}
			else if(obj instanceof Group) {
				byte[] bytes=serialize((Group) obj);
				values=new ContentValues(2);
				values.put(GROUP_INFO, bytes);
				values.put(GROUP_ID, ((Group) obj).getId());
			}
			else if(obj instanceof User) {
				byte[] bytes=serialize((User) obj);
				values=new ContentValues(1);
				values.put(USER_INFO, bytes);
			}
			SQLiteDatabase db=this.getWritableDatabase();
			db.beginTransaction();
			if(obj instanceof SubjectModel) {
				db.insert(SUBJECT_TOPIC, null, values);
			}
			else if(obj instanceof MessageStatusModel) {
				db.insert(MESSAGE_TOPIC, null, values);
			}
			else if(obj instanceof Hot) {
				db.insert(HOT_TOPIC, null, values);
			}
			else if(obj instanceof Group) {
				db.insert(GROUP_TOPIC, null, values);
			}
			else if(obj instanceof User) {
				db.insert(USER_TOPIC, null, values);
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			db.close();	
		}			
	}
	
	/**
	 * ɾ��SubjectModel
	 */
	public void deleteSubjectModel() {
		synchronized (this) {
			SQLiteDatabase db=this.getWritableDatabase();
			db.delete(SUBJECT_TOPIC, null, null);
			db.close();
		}		
	}
	
	/**
	 * ɾ��MessageStatusModel
	 */
	public void deleteMessageStatusModel() {
		synchronized (this) {
			SQLiteDatabase db=this.getWritableDatabase();
			db.delete(MESSAGE_TOPIC, null, null);
			db.close();
		}
	}
	
	/**
	 * ɾ��deleteHotModel
	 */
	public void deleteHotModel() {
		synchronized (this) {
			SQLiteDatabase db=this.getWritableDatabase();
			db.delete(HOT_TOPIC, null, null);
			db.close();
		}
	}
	
	/**
	 * ɾ��Ȧ��
	 */
	public void deleteGroup() {
		synchronized (this) {
			SQLiteDatabase db=this.getWritableDatabase();
			db.delete(GROUP_TOPIC, null, null);
			db.close();
		}
	}
	
	/**
	 * ɾ��user
	 */
	public void deleteUser() {
		synchronized (this) {
			SQLiteDatabase db=this.getWritableDatabase();
			db.delete(USER_TOPIC, null, null);
			db.close();
		}		
	}
	
	/**
	 * ��ȡȫ��SubjectModel
	 * @return
	 */
	public ArrayList<SubjectModel> getSubjectModelList() {
		synchronized (this) {
			ArrayList<SubjectModel> model_list=new ArrayList<SubjectModel>();
			SQLiteDatabase db=this.getReadableDatabase();
			Cursor cs=db.query(SUBJECT_TOPIC, null, null, null, null, null, null, null);
			cs.moveToFirst();
			for(int i=0;i<cs.getCount();i++) {
				cs.moveToPosition(i);
				model_list.add(deserializeSubjectModel(cs.getBlob(1)));
			}
			cs.close();
			db.close();
			return model_list;
		}		
	}
	
	/**
	 * ��ȡȫ��MessageStatusModel
	 * @return
	 */
	public LinkedList<MessageStatusModel> getMessageStatusModelList() {
		synchronized (this) {
			LinkedList<MessageStatusModel> model_list=new LinkedList<MessageStatusModel>();
			SQLiteDatabase db=this.getReadableDatabase();
			Cursor cs=db.query(MESSAGE_TOPIC, null, null, null, null, null, null, null);
			cs.moveToFirst();
			for(int i=0;i<cs.getCount();i++) {
				cs.moveToPosition(i);
				model_list.add(deserializeMessageStatusModel(cs.getBlob(1)));
			}
			cs.close();
			db.close();
			return model_list;
		}		
	}
	
	/**
	 * ��ȡȫ�������б�
	 * @return
	 */
	public ArrayList<Hot> getHotModelList() {
		synchronized (this) {
			ArrayList<Hot> model_list=new ArrayList<Hot>();
			SQLiteDatabase db=this.getReadableDatabase();
			Cursor cs=db.query(HOT_TOPIC, null, null, null, null, null, null, null);
			cs.moveToFirst();
			for(int i=0;i<cs.getCount();i++) {
				cs.moveToPosition(i);
				model_list.add(deserializeHotModel(cs.getBlob(1)));
			}
			cs.close();
			db.close();
			return model_list;
		}		
	}
	
	/**
	 * ��ȡȫ��Ȧ���б�
	 * @return
	 */
	public ArrayList<Group> getGroupModelList() {
		synchronized (this) {
			ArrayList<Group> model_list=new ArrayList<Group>();
			SQLiteDatabase db=this.getReadableDatabase();
			Cursor cs=db.query(GROUP_TOPIC, null, null, null, null, null, null, null);
			cs.moveToFirst();
			for(int i=0;i<cs.getCount();i++) {
				cs.moveToPosition(i);
				model_list.add(deserializeGroupModel(cs.getBlob(1)));
			}
			cs.close();
			db.close();
			return model_list;
		}		
	}
	
	/**
	 * ��ȡ��¼����
	 * @return
	 */
	public User getLoginUser() {
		synchronized (this) {
			SQLiteDatabase db=this.getReadableDatabase();
			Cursor cs=db.query(USER_TOPIC, null, null, null, null, null, null, null);
			cs.moveToFirst();		
			User user=null;
			if(cs.getCount()>0) {
				cs.moveToPosition(0);
				user=deserializeUser(cs.getBlob(1));
			}
			cs.close();
			db.close();
			return user;
		}
	}
	
	/**
	 * �ж���Ϣ�Ƿ����
	 * @param fromId
	 * @return
	 */
	public boolean isMessageExists(int fromId) {
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cs=db.query(MESSAGE_TOPIC, null, MESSAGE_ID+"=?", new String[]{""+fromId}, null, null, null, null);
		cs.moveToFirst();
		if(cs.getCount()>0) {
			return true;
		}
		return false;
	}
	
	/**
	 * ����δ�Ķ���
	 * @param fromId
	 * @param noreadCount
	 */
	public void updateNoReadCount(int fromId, int noreadCount, int data) {
		synchronized (this) {
			SQLiteDatabase db=this.getReadableDatabase();
			Cursor cs=db.query(MESSAGE_TOPIC, null, MESSAGE_ID+"=?", new String[]{""+fromId}, null, null, null, null);
			cs.moveToFirst();
			if(cs.getCount()>0) {
				cs.moveToPosition(0);
				MessageStatusModel model=deserializeMessageStatusModel(cs.getBlob(1));
				model.setNoReadCount(noreadCount);
				//ֻ�н������͹�����Ϣʱ�����޸����һ����Ϣ��ʱ��
				if(data!=0) {
					model.setSendDate(data);
				}				
				cs.close();
				db.close();
				SQLiteDatabase db2=this.getWritableDatabase();
				ContentValues values=new ContentValues();
				byte[] bytes=serialize(model);
				values.put(MESSAGE_INFO, bytes);
				db2.update(MESSAGE_TOPIC, values, MESSAGE_ID+"=?", new String[]{""+fromId});
				db2.close();
			}
		}
	}
	
	/**
	 * ����ͼƬurl
	 * @param fromId
	 * @param messageUrl
	 */
	public void updateOldMessage(int fromId, String messageUrl, String msgDesc) {
		synchronized (this) {
			SQLiteDatabase db=this.getReadableDatabase();
			Cursor cs=db.query(MESSAGE_TOPIC, null, MESSAGE_ID+"=?", new String[]{""+fromId}, null, null, null, null);
			cs.moveToFirst();
			if(cs.getCount()>0) {
				cs.moveToPosition(0);
				MessageStatusModel model=deserializeMessageStatusModel(cs.getBlob(1));
				model.setIcon(messageUrl);
				//��Ϣҳ��ˢ�³�����������Ҫ�滻������Ϣ
				if(!msgDesc.equals("")) {
					model.setMsgDesc(msgDesc);					
				}
				cs.close();
				db.close();
				SQLiteDatabase db2=this.getWritableDatabase();
				ContentValues values=new ContentValues();
				byte[] bytes=serialize(model);
				values.put(MESSAGE_INFO, bytes);
				db2.update(MESSAGE_TOPIC, values, MESSAGE_ID+"=?", new String[]{""+fromId});
				db2.close();
			}
		}
	}
	
	/**
	 * ���л�
	 * @param model
	 * @return
	 */
	public static byte[] serialize(Object obj) { 
        try { 
        	ByteArrayOutputStream mem_out = new ByteArrayOutputStream(); 
        	ObjectOutputStream out = new ObjectOutputStream(mem_out);  
        	if(obj instanceof SubjectModel) {
        		out.writeObject((SubjectModel) obj); 
        	}
        	else if(obj instanceof MessageStatusModel) {
        		out.writeObject((MessageStatusModel) obj); 
        	}
        	else if(obj instanceof Group) {
        		out.writeObject((Group) obj); 
        	}
        	else if(obj instanceof Hot) {
        		out.writeObject((Hot) obj); 
        	}
        	else if(obj instanceof User) {
        		out.writeObject((User) obj); 
        	}
            out.close(); 
            mem_out.close();  
            byte[] bytes =  mem_out.toByteArray(); 
            return bytes; 
        } catch (IOException e) { 
            return null; 
        } 
    } 
 
	/**
	 * �����л�SubjectModel
	 * @param bytes
	 * @return
	 */
	public static SubjectModel deserializeSubjectModel(byte[] bytes){ 
		try { 
			ByteArrayInputStream mem_in = new ByteArrayInputStream(bytes); 
			ObjectInputStream in = new ObjectInputStream(mem_in);  
			SubjectModel model = (SubjectModel)in.readObject();  
			in.close(); 
			mem_in.close();  
			return model; 
		} catch (StreamCorruptedException e) { 

		} catch (ClassNotFoundException e) { 

		} catch (IOException e) { 
		
		} 
		return null; 
	}
	
	/**
	 * �����л�MessageStatusModel
	 * @param bytes
	 * @return
	 */
	public static MessageStatusModel deserializeMessageStatusModel(byte[] bytes){ 
		try { 
			ByteArrayInputStream mem_in = new ByteArrayInputStream(bytes); 
			ObjectInputStream in = new ObjectInputStream(mem_in);  
			MessageStatusModel model = (MessageStatusModel)in.readObject();  
			in.close(); 
			mem_in.close();  
			return model; 
		} catch (StreamCorruptedException e) { 

		} catch (ClassNotFoundException e) { 

		} catch (IOException e) { 
		
		} 
		return null; 
	}
	
	/**
	 * �����л�MessageStatusModel
	 * @param bytes
	 * @return
	 */
	public static Hot deserializeHotModel(byte[] bytes){ 
		try { 
			ByteArrayInputStream mem_in = new ByteArrayInputStream(bytes); 
			ObjectInputStream in = new ObjectInputStream(mem_in);  
			Hot model = (Hot)in.readObject();  
			in.close(); 
			mem_in.close();  
			return model; 
		} catch (StreamCorruptedException e) { 

		} catch (ClassNotFoundException e) { 

		} catch (IOException e) { 
		
		} 
		return null; 
	}
	
	/**
	 * �����л�Group
	 * @param bytes
	 * @return
	 */
	public static Group deserializeGroupModel(byte[] bytes){ 
		try { 
			ByteArrayInputStream mem_in = new ByteArrayInputStream(bytes); 
			ObjectInputStream in = new ObjectInputStream(mem_in);  
			Group model = (Group)in.readObject();  
			in.close(); 
			mem_in.close();  
			return model; 
		} catch (StreamCorruptedException e) { 

		} catch (ClassNotFoundException e) { 

		} catch (IOException e) { 
		
		} 
		return null; 
	}
	
	/**
	 * �����л�SubjectModel
	 * @param bytes
	 * @return
	 */
	public static User deserializeUser(byte[] bytes){ 
		try { 
			ByteArrayInputStream mem_in = new ByteArrayInputStream(bytes); 
			ObjectInputStream in = new ObjectInputStream(mem_in);  
			User model = (User)in.readObject();  
			in.close(); 
			mem_in.close();  
			return model; 
		} catch (StreamCorruptedException e) { 

		} catch (ClassNotFoundException e) { 

		} catch (IOException e) { 
		
		} 
		return null; 
	}

}
