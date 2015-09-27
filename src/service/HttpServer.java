package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import control.Product_info;
import control.ProjectCommand;
import JsonTools.JsonTool;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class HttpServer extends Service {
	private Context context;
	private Messenger replyMessenger;
	
	List<Product_info> products=new ArrayList<Product_info>();
	MyHandler handler=new MyHandler();

	@SuppressLint("HandlerLeak")
	class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
//			System .out.println("获得数据");
//			Log.v("test", "获得数据");
			products=(List<Product_info>) msg.obj;
//			System.out.println(products);
			Intent intent2=new Intent("GetProductInfo");
			intent2.putExtra("Data", (Serializable)products);
			sendBroadcast(intent2);
//				System.out.print("Myhandler获得！！");
			
		}
	}
	
	
//	private Handler handler=new Handler(){
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			if (msg.what==1) {
//				System.out.println(msg.obj);
//				for (int i = 0; i < products.size(); i++) {
//					Product_info info=new Product_info();
//					info=products.get(i);
//					System.out.println("产品名称："+info.getName());
//					System.out.println("产品产地："+info.getAddress());
//					System.out.println("产品价格："+info.getPrice());
//					System.out.println("产品图片："+info.getImagePath());
//				}
//			}
//			if (msg.what==0) {
//				System.out.println("获得activity的信息");
//			}
//		};
//	};
//	
	final Messenger messenger = new Messenger(new MyHandler());
	
	public class MyThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
//			System.out.println("开始run");
			HttpClient httpClient=new DefaultHttpClient();
			HttpPost httpPost=new HttpPost(ProjectCommand.PRODUCT_PATH);
			String respon=null;
			try {
				HttpResponse response=httpClient.execute(httpPost);
				if (response.getStatusLine().getStatusCode()==200) {
//					System.out.println("连接上网络");
					respon=EntityUtils.toString(response.getEntity(),"utf-8");
					if (respon!=null&&!respon.equals("")) {
						products = JsonTool.getProduct_infos(respon,
								"ALLPRODUCT");
						Message message=Message.obtain();
						message.what=1;
						message.obj=products;
//						System.out.println(products );
					handler.sendMessage(message);
					}else {
//						System.out.println("获得的数据是空!!");
					}
				}else {
//					System.out.println("未能连接上网络！！！");
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
//		System.out.println("serverice开始创建！");
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Thread(new MyThread()).start();
		Log.v("HttpSeriver", "StartCommand");
//		System.out.println("线程已经开启！");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
//		System.out.print("开始！！！");
		return messenger.getBinder();
//		return null;
	}
}
