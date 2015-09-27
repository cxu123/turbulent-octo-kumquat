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
//			System .out.println("�������");
//			Log.v("test", "�������");
			products=(List<Product_info>) msg.obj;
//			System.out.println(products);
			Intent intent2=new Intent("GetProductInfo");
			intent2.putExtra("Data", (Serializable)products);
			sendBroadcast(intent2);
//				System.out.print("Myhandler��ã���");
			
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
//					System.out.println("��Ʒ���ƣ�"+info.getName());
//					System.out.println("��Ʒ���أ�"+info.getAddress());
//					System.out.println("��Ʒ�۸�"+info.getPrice());
//					System.out.println("��ƷͼƬ��"+info.getImagePath());
//				}
//			}
//			if (msg.what==0) {
//				System.out.println("���activity����Ϣ");
//			}
//		};
//	};
//	
	final Messenger messenger = new Messenger(new MyHandler());
	
	public class MyThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
//			System.out.println("��ʼrun");
			HttpClient httpClient=new DefaultHttpClient();
			HttpPost httpPost=new HttpPost(ProjectCommand.PRODUCT_PATH);
			String respon=null;
			try {
				HttpResponse response=httpClient.execute(httpPost);
				if (response.getStatusLine().getStatusCode()==200) {
//					System.out.println("����������");
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
//						System.out.println("��õ������ǿ�!!");
					}
				}else {
//					System.out.println("δ�����������磡����");
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
//		System.out.println("serverice��ʼ������");
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Thread(new MyThread()).start();
		Log.v("HttpSeriver", "StartCommand");
//		System.out.println("�߳��Ѿ�������");
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
//		System.out.print("��ʼ������");
		return messenger.getBinder();
//		return null;
	}
}
