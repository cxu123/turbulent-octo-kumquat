package com.example.cars_helper;

import java.util.HashMap;
import java.util.Map;

import control.ProjectCommand;
import db.DBSQLiteDao;
import DataStorage.SharedPrefencesUserInfo;
import HttpUtils.HttpTools;
import HttpUtils.HttpTools.DownLoadCallBack;
import ResultCode.ResultCode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginNew extends Activity {
	private String USER_NAME, USER_PASSWORD;
	private EditText UserName;
	private EditText UserPassWord;
	// private final String LOGIN_PATH =
	// "http://ona123.xicp.net/servlet/LoginActionForAndroid";
	private ResultCode resultcode;
	private TextView uName;
	private TextView uPassword;
	private Map<String, String> user;
	private DBSQLiteDao db;
	private CarsHelperApplication application ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    application = (CarsHelperApplication) getApplication();
		user = application.GetUserInfo();
		db = new DBSQLiteDao(this);
		if (user == null || user.isEmpty()) {
			NoUser();
		} else {
			ShowView();
		}
	}

	public void ShowView() { // ShowView
		setContentView(R.layout.activaty_logininfo);
		uName = (TextView) findViewById(R.id.usernameinfo);
		uName.setText(user.get("username"));
		RelativeLayout MyInfo = (RelativeLayout) findViewById(R.id.relativeLayout2);
		RelativeLayout MyBook = (RelativeLayout) findViewById(R.id.relativeLayout3);
		RelativeLayout My3 = (RelativeLayout) findViewById(R.id.relativeLayout4);
		RelativeLayout My4 = (RelativeLayout) findViewById(R.id.relativeLayout5);
		Button button = (Button) findViewById(R.id.button1);
		InfoOnclick infoOnclick = new InfoOnclick();
		MyInfo.setOnClickListener(infoOnclick);
		MyBook.setOnClickListener(infoOnclick);
		My3.setOnClickListener(infoOnclick);
		My4.setOnClickListener(infoOnclick);
		button.setOnClickListener(infoOnclick);
	}

	public void NoUser() {
		setContentView(R.layout.activity_login);
		UserName = (EditText) findViewById(R.id.login_name);
		UserPassWord = (EditText) findViewById(R.id.login_password);
		Button sub = (Button) findViewById(R.id.login_but);
		sub.setOnClickListener(new NoInfoOnclick());
		Button newuser=(Button)findViewById(R.id.newuser);
		newuser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginNew.this,UserNew.class);
				startActivityForResult(intent,1);
			}
		});
	}

	class NoInfoOnclick implements OnClickListener {

		@SuppressLint("ShowToast")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final Map<String, Object> userinfo = new HashMap<String, Object>();
			if (UserName.length() > 0 && UserPassWord.length() > 0) {
				// 保存信息
				userinfo.put("username", UserName.getText());
				userinfo.put("password", UserPassWord.getText());
				HttpTools httpTools = new HttpTools(LoginNew.this);
				httpTools.sendDataThread(ProjectCommand.LOGIN_PATH, userinfo,
						new DownLoadCallBack() {

							@Override
							public void loadContent(ResultCode result) {
								// TODO Auto-generated method stub
								resultcode = result;
								if (resultcode != null) {

									String[] params = {
											userinfo.get("username").toString(),
											userinfo.get("password").toString(),
											"true" };
									if (db.addUser(params)) {
										userinfo.put("username", userinfo.get("username").toString());
										userinfo.put("password", userinfo.get("password").toString());
										application.SetUserInfo(userinfo);
										ShowView(); // 保存数据并且更新View
									}

								} else {
									AlertDialog.Builder msg = new AlertDialog.Builder(
											LoginNew.this);
									msg.setTitle("登录失败！！！");
									msg.setMessage("请确定密码和你的网络状态");
									msg.setPositiveButton("确定", null);
									msg.show();
								}
							}
						});
			} else {
				Toast.makeText(LoginNew.this, "请输入用户名或者密码", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	class InfoOnclick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.relativeLayout2:
				Log.v("点击的是：", "MyInfo");
				break;
			case R.id.relativeLayout3:
				Log.v("点击的是：", "我的订单");
				break;
			case R.id.relativeLayout4:
				Log.v("点击的是：", "我的红包");
				break;
			case R.id.relativeLayout5:
				Log.v("点击的是：", "我的积分");
				break;
			case R.id.button1:
				LogOut();
				break;
			default:
				break;
			}
		}

		private void LogOut() {
			// TODO Auto-generated method stub
			String[] params={"false",user.get("username")};
			if (db.updateUser(params)) {
				Log.v("成功注销", "下次程序启动后生效");
				Toast.makeText(LoginNew.this, "成功注销,APP重启后生效", Toast.LENGTH_SHORT).show();
			}
		}		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode==1) {			
			Log.v("用户名：", data.getStringExtra("USERNAME"));
			Log.v("用户密码：", data.getStringExtra("USERPASSWORD"));
		}
		if (resultCode==0) {
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
