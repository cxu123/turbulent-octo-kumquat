package com.example.cars_helper;


import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import db.DBSQLiteDao;
import DataStorage.SharedPrefencesUserInfo;
import HttpUtils.HttpTools;
import JsonTools.JsonTool;
import ResultCode.ResultCode;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class User extends Activity {
	private String USER_NAME,USER_PASSWORD;
	private EditText button,button2;
	private String url_path="http://ona123.xicp.net/servlet/RegisterActionForAndroid";
	private ProgressDialog dialog;
	ResultCode resultCode=new ResultCode();
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_user);
	 button=(EditText)findViewById(R.id.User_name);
	 button2=(EditText)findViewById(R.id.User_password);
	Button login=(Button)findViewById(R.id.login);
	dialog=new ProgressDialog(this);
	dialog.setTitle("");
	dialog.setMessage("注册中请等待....");
	login.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			USER_NAME=button.getText().toString().trim();
			USER_PASSWORD=button2.getText().toString().trim();
			if (USER_NAME.length()>0&&USER_PASSWORD.length()>0) {
				System.err.println("开始");
				
				Time t=new Time();
				t.setToNow();
				int hour=t.hour;
				int minute=t.minute;
				int second=t.second;
				String ID="3"+hour+minute+second;
				DBSQLiteDao db=new DBSQLiteDao(User.this);
				Object[] params={USER_NAME,USER_PASSWORD,ID};
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				//password
				paramsMap.put("username", USER_NAME);
				paramsMap.put("password", USER_PASSWORD);
				try {
					String result=new RegisterAsyncTask().execute(paramsMap).get();
					JsonTool jsonTool=new JsonTool();
					Log.e("test", result);
					if (!result.equals("false")) {
						resultCode = jsonTool.getResultCode(result);
					}else {
						Toast.makeText(User.this, "网络出现问题请检查网络", Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				if (resultCode.getResultCode()==1) {
					Map<String, Object> paramsprefences = new HashMap<String, Object>();
					//password
					paramsprefences.put("username", USER_NAME);
					paramsprefences.put("password", USER_PASSWORD);
					SharedPrefencesUserInfo prefencesUserInfo=new SharedPrefencesUserInfo(User.this);
					prefencesUserInfo.SaveLoginInfo("LoginInfo", paramsprefences);
					if (db.addUser(params)) {
						AlertDialog.Builder mge = new AlertDialog.Builder(
								User.this);
						mge.setTitle("本地注册成功");
						mge.setMessage(resultCode.getResultMessage()+"感谢你注册成功");
						mge.setPositiveButton("确定", null);
						mge.show();
					}
					
				}
			}else {
				System.out.println(USER_NAME+"-------------"+USER_PASSWORD);
				AlertDialog.Builder mge = new AlertDialog.Builder(
						User.this);
				mge.setTitle("错误");
				mge.setMessage("请输入用户名和密码");
				mge.setPositiveButton("确定", null);
				mge.show();
			}
		}
	});
}
	
	class RegisterAsyncTask extends AsyncTask<Map<String, Object>, Void, String> {

		@Override
		protected String doInBackground(Map<String, Object>... params) {
			// TODO Auto-generated method stub
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			//password
			paramsMap.put("username", USER_NAME);
			paramsMap.put("password", USER_PASSWORD);
			
			String result=HttpTools.sendPostMethod(url_path, paramsMap, "utf-8",User.this);
			System.out.print(result);
			return result;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
		}
	}
}
