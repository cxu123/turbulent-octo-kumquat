package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBSQLiteDao implements DBSQLiteService {
	
	private DBSQLiteTool db=null;
	
	public DBSQLiteDao(Context context) {
		db=new DBSQLiteTool(context);
	}
	
	//实现添加
	@Override
	public boolean addUser(Object[] params) {
		// TODO Auto-generated method stub
		boolean flag=false;
		SQLiteDatabase database=null;
		try {
			String sql="insert into user(username,password,FirstLogin) values(?,?,?)";
			database=db.getWritableDatabase();
			database.execSQL(sql,params);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (database!=null) {
				database.close();
			}
		}
		return flag;
	}
	
	//实现删除
	@Override
	public boolean deleteUser(Object[] params) {
		// TODO Auto-generated method stub
		boolean flag=false;
		SQLiteDatabase database=null;
		try {
			String sql="delete from user where FirstLogin=?";
			database=db.getWritableDatabase();
			database.execSQL(sql,params);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
		if (database!=null) {
			database.close();
		}	
		}
		
		return false;
	}

	//实现更新
	@Override
	public boolean updateUser(Object[] params) {
		// TODO Auto-generated method stub
		boolean flag=false;
		SQLiteDatabase database=null;
		try {
			String sql="update user set FirstLogin = ? where username = ? ";
			database=db.getWritableDatabase();
			database.execSQL(sql,params);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (database!=null) {
				database.close();
			}
		}
		return flag;
	}
	
//	public Map<String, String> GetFirstLoginUser(Boolean flag) {
//		// TODO Auto-generated method stub
//		Map<String, String> map=new HashMap<String, String>();
//		SQLiteDatabase database=null;
//		try {
//			String sql="select * from user where FirstLogin=?";
//			database=db.getReadableDatabase();
//			Cursor cursor=database.rawQuery(sql, flag.toString());
//			int colums = cursor.getColumnCount();
//			while (cursor.moveToNext()) {
//				for (int i = 0; i < colums; i++) {
//					String cols_name=cursor.getColumnName(i);
//					String cols_value =cursor.getString(cursor.getColumnIndex(cols_name));
//					if (cols_value==null) {
//						cols_value="";
//					}
//					map.put(cols_name, cols_value);
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}finally{
//			if (database!=null) {
//				database.close();
//			}
//		}
//		return map;
//	}
//
	
	//实现查询
	@Override
	public Map<String, String> GetUser(String[] selectionArgs) {
		// TODO Auto-generated method stub
		Map<String, String> map=new HashMap<String, String>();
		SQLiteDatabase database=null;
		try {
			String sql="select * from user where FirstLogin=?";
			database=db.getReadableDatabase();
			Cursor cursor=database.rawQuery(sql, selectionArgs);
			int colums = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				for (int i = 0; i < colums; i++) {
					String cols_name=cursor.getColumnName(i);
					String cols_value =cursor.getString(cursor.getColumnIndex(cols_name));
					if (cols_value==null) {
						cols_value="";
					}
					map.put(cols_name, cols_value);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (database!=null) {
				database.close();
			}
		}
		return map;
	}

	
	//实现查询多个值
	@Override
	public List<Map<String, String>> ListUserMap(String[] selectionArgs) {
		// TODO Auto-generated method stub
		List<Map<String, String>> listMap=new ArrayList<Map<String,String>>();
		
		SQLiteDatabase database=null;
		try {
			String sql="select * from user ";
			database=db.getReadableDatabase();
			Cursor cursor=database.rawQuery(sql, selectionArgs);
			int colums = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				Map<String, String> map=new HashMap<String, String>();
				for (int i = 0; i < colums; i++) {
					String cols_name=cursor.getColumnName(i);
					String cols_value =cursor.getString(cursor.getColumnIndex(cols_name));
					if (cols_value==null) {
						cols_value="";
					}
					map.put(cols_name, cols_value);
				}
				listMap.add(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (database!=null) {
				database.close();
			}
		}
		return listMap;
	}

}
