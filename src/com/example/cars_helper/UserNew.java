package com.example.cars_helper;

import java.util.HashMap;
import java.util.Map;

import control.ProjectCommand;
import db.DBSQLiteDao;
import DataStorage.SharedPrefencesUserInfo;
import HttpUtils.HttpTools;
import HttpUtils.HttpTools.DownLoadCallBack;
import ResultCode.ResultCode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UserNew extends Activity {
	private String USER_NAME,USER_PASSWORD;
	private EditText button,button2;
	private String url_path="http://ona123.xicp.net/servlet/RegisterActionForAndroid";
	private ResultCode resultCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		button=(EditText)findViewById(R.id.User_name);
		 button2=(EditText)findViewById(R.id.User_password);
		Button login=(Button)findViewById(R.id.login);
		Button back=(Button)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserNew.this, LoginNew.class);
				intent.putExtra("None", "");
				UserNew.this.setResult(RESULT_CANCELED, intent);
				UserNew.this.finish();
			}
		});
		ImageView imageViewback=(ImageView)findViewById(R.id.imageViewback1);
		imageViewback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserNew.this, LoginNew.class);
				intent.putExtra("None", "");
				UserNew.this.setResult(0, intent);
				UserNew.this.finish();
			}
		});
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
					DBSQLiteDao db=new DBSQLiteDao(UserNew.this);
					Object[] params={USER_NAME,USER_PASSWORD,ID};
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					paramsMap.put("username", USER_NAME);
					paramsMap.put("password", USER_PASSWORD);
					HttpTools httpTools=new HttpTools(UserNew.this);
					httpTools.sendDataThread(ProjectCommand.Register_Path, paramsMap,new DownLoadCallBack() {

						@Override
						public void loadContent(ResultCode result) {
							// TODO Auto-generated method stub
							resultCode=result;
							if (resultCode!=null) {
								AlertDialog.Builder mge = new AlertDialog.Builder(
										UserNew.this);
								mge.setTitle("本地注册成功");
								mge.setMessage(resultCode.getResultMessage()
										+ "感谢你注册成功");
								mge.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										Intent intent = new Intent(UserNew.this, LoginNew.class);
										intent.putExtra("USERNAME", USER_NAME);
										intent.putExtra("USERPASSWORD", USER_PASSWORD);
										UserNew.this.setResult(RESULT_OK, intent);
										UserNew.this.finish();
									}
								});
								mge.show();
								
							}
							else {
								AlertDialog.Builder mge = new AlertDialog.Builder(
										UserNew.this);
								mge.setTitle("注册失败！！！");
								mge.setMessage("未能得到服务器的响应！！1");
								mge.setPositiveButton("确定", null);
								mge.show();
							}
						}
					});
					if (resultCode==null) {
						return;
					}
					Map<String, Object> paramsprefences = new HashMap<String, Object>();
					//password
					paramsprefences.put("username", USER_NAME);
					paramsprefences.put("password", USER_PASSWORD);
					SharedPrefencesUserInfo prefencesUserInfo=new SharedPrefencesUserInfo(UserNew.this);
					prefencesUserInfo.SaveLoginInfo("LoginInfo", paramsprefences);
					Toast.makeText(UserNew.this, resultCode.getResultMessage(), Toast.LENGTH_LONG).show();;
				}else {
					System.out.println(USER_NAME+"-------------"+USER_PASSWORD);
					AlertDialog.Builder mge = new AlertDialog.Builder(
							UserNew.this);
					mge.setTitle("错误");
					mge.setMessage("请输入用户名和密码");
					mge.setPositiveButton("确定", null);
					mge.show();
				}
			}
		});
	}
}
