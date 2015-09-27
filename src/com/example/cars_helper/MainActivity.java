package com.example.cars_helper;

import java.util.ArrayList;
import java.util.List;

import service.HttpServer;
import control.CareHelperMessageReceiver;
import control.CareHelperMessageReceiver.MessageReceive;
import control.Product_info;
import control.ProjectCommand;
import HttpUtils.FileDownloadUtil;
import HttpUtils.FileDownloadUtil.ImageDownloadUtilCallBack;
import android.R.array;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	/** SDK验证密钥 */
	private final static String ACCESS_KEY = "y9dRYG1EILY0mGAxjloSTmND";// "LVUOeu2yWl5uHwG6zewGwN0m";
	private  final String CATEGORY_SDK_DEMO = "android.intent.category.BAIDUNAVISDK_DEMO";
	double Latitude = 1.0; // 纬度坐标
	double Longitude = 1.0; // 经度坐标
	private Button button;
	boolean IsEngineInitSuccess = false;
	 CareHelperMessageReceiver msg;
	 CarsHelperApplication application;
	 TextView text;
	 TextView text2;
	 int flag = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		Toast.makeText(MainActivity.this, getSDcardDir(), Toast.LENGTH_LONG)
//				.show();
		ImageView log = (ImageView)findViewById(R.id.imageView1);
		//app开始时候连接service获得数据并进行缓存
		IniGetProductInfo();	
		//百度地图
//		BaiduNaviManager.getInstance().initEngine(MainActivity.this,
//				getSDcardDir(), mNaviEngineInitListener,
//				new LBSAuthManagerListener() {
//
//					@SuppressLint("ShowToast")
//					@Override
//					public void onAuthResult(int status, String msg) {
//						// TODO Auto-generated method stub
//						if (0 == status) {
////							Toast.makeText(MainActivity.this, "KEY验证成功",
////									Toast.LENGTH_LONG).show();
//						} else {
//							Toast.makeText(MainActivity.this, "KEY验证失败" + msg,
//									Toast.LENGTH_LONG).show();
//						}
//					}
//				});
		text = (TextView) findViewById(R.id.textView1);
		button = (Button) findViewById(R.id.tr);
//		final  EditText infoEditText = (EditText) findViewById(R.id.editText1);
		button.setText("点击进入地图");
//		button.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(MainActivity.this, Mapsearch.class);
//				intent.putExtra("info", infoEditText.getText().toString());
//				startActivity(intent);
//			}
//		});
//		Button button2=(Button)findViewById(R.id.button2);
//		button2.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent=new Intent(MainActivity.this,Home.class);
//		//		MainActivity.this.startActivity(intent);
//				MainActivity.this.startActivity(intent);
//			}
//		});
		AlphaAnimation animation=new AlphaAnimation(1.0f, 1.0f);
		animation.setDuration(15000);
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,Home.class);
				MainActivity.this.startActivity(intent);
				MainActivity.this.finish();
			}
		});
		log.setAnimation(animation);
		animation.start();
	}

	private void IniGetProductInfo() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, HttpServer.class);
		SetMessageReceiver();
		startService(intent);
	}
	public void SetMessageReceiver() {
		 msg=new CareHelperMessageReceiver();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction("GetProductInfo");
		registerReceiver(msg, intentFilter);
		msg.GetProductData(new MessageReceive() {		
			@Override
			public void GetData(List<Product_info> data) {
				// TODO Auto-generated method stub
				application=(CarsHelperApplication)getApplication();
				application.SetAppData(data);
				Intent intent2=new Intent(MainActivity.this,Home.class);
				MainActivity.this.startActivity(intent2);
				MainActivity.this.finish();
			}
		});
	}
	
//	public class MessageReceiver	extends BroadcastReceiver{
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			// TODO Auto-generated method stub
//			application=(CarsHelperApplication)getApplication();
//			@SuppressWarnings("unchecked")
//			final List<Product_info>data=(List<Product_info>)intent.getSerializableExtra("Data");
//			List<String>ProductImageName=new ArrayList<String>();
//			for (int i = 0; i < data.size(); i++) {
//				ProductImageName.add(data.get(i).getImagePath());
//			}
//			ProductImageName.add("Had_a.jpg");
//			ProductImageName.add("Had_b.jpg");
//			ProductImageName.add("Had_c.jpg");
////			Log.v("ProductImageName:", ProductImageName.toString());
//			ImageDownloadUtil imageDownloadUtil=new ImageDownloadUtil(MainActivity.this,ProductImageName);
//			imageDownloadUtil.Download(new ImageDownloadUtilCallBack() {
//				
//				@Override
//				public void ImageLoad(boolean complete) {
//					// TODO Auto-generated method stub
//					application.SetAppData(data);
//					Intent intent2=new Intent(MainActivity.this,Home.class);
//					MainActivity.this.startActivity(intent2);
//					MainActivity.this.finish();
//				}
//			});		
//		}		
//	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(msg);
		Intent intent = new Intent(this, HttpServer.class);
		stopService(intent);
		super.onDestroy();
		
	}
	
}

