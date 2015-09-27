package com.example.cars_helper;

import java.util.List;







import service.HttpServer;
import control.Product_info;
import control.ProjectCommand;
import control.PullToReFreshLayoutListener;
import Adapter.ImageAdapter;
import Adapter.ProductAdapter;
import Thread.GetProductFromKEYWORD;
import Thread.GetProductFromKEYWORD.GetProductFromKEYWORDCallback;
import View.ListViewForScrollView;
import View.ListViewForScrollView.ListViewOnItemClick;
import View.PullToRefreshLayout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class Near_Info extends Activity {

//	private ProgressDialog dialog;
	private  List<Product_info>Product_data ;
	private  ListViewForScrollView listView ;
	private ProgressBar progressBar;
	private String City;
	private boolean changeflag=false;
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if (msg.what==1) {
				progressBar.setVisibility(View.GONE);
				Product_data=(List<Product_info>) msg.obj;
				SetListView();
			}else {
				Log.v("没有数据", "handleMessage");
				progressBar.setVisibility(View.GONE);
				ShowNone();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activaty_maininfo);
		TextView textView=(TextView)findViewById(R.id.activityInfo);
		City=GetCityName();
		textView.setText(City+"附近的商家");
		progressBar=(ProgressBar)findViewById(R.id.progressBar1);
	//	dialog.setCancelable(false);无法取消
	//	dialog.setCanceledOnTouchOutside(false);
		// 屏幕上方的Gridview;
		
		//开启线程
		GetNearProductData(City);
	}

	private void ShowNone(){
		Log.v("没有获得数据", "没有获得数据");
		ScrollView layout=(ScrollView)findViewById(R.id.scrollView1);
	//	layout.s
		layout.removeAllViews();
		View None=View.inflate(Near_Info.this, R.layout.nothingshow, null);
		layout.addView(None);
		changeflag=true;
	}
	
	private void GetNearProductData(String keyword) {	
		// TODO Auto-generated method stub
		String	ckeyword=keyword;
		String flag_cation=ProjectCommand.Product_Near;
		if (keyword.equals("重庆")) {
			ckeyword="";
			flag_cation="";
		}
		GetProductFromKEYWORD getProductFromKEYWORD=new GetProductFromKEYWORD(ckeyword, flag_cation);
		getProductFromKEYWORD.Dowork(new GetProductFromKEYWORDCallback() {

			@Override
			public void GetProductFromKEYWORDCallbackGetData(
					List<Product_info> data) {
				// TODO Auto-generated method stub
				Message messages=Message.obtain();
				if (data!=null&&!data.isEmpty()) {
					messages.obj = data;
					messages.what = 1;
				}else {
					messages.what=0;
				}
				handler.sendMessage(messages);
			}
		});
	}

	private String GetCityName(){
		CarsHelperApplication application=(CarsHelperApplication) getApplication();
		return application.GetCityName();	
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		
		// bindService(new Intent(this,HttpServer.class), connection,
		// Context.BIND_AUTO_CREATE);
	//	Log.v("A-activity:", "开始运行-》onStart");
		
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub	
	//	Log.v("A-activity:", "开始结束-》onStop");
		super.onStop();
	}



	public void SetListView() {
		
		int list_xml_name = R.layout.new_list_item;
		ProductAdapter productAdapter=new ProductAdapter(Near_Info.this, Product_data, list_xml_name);
		Log.v("SetListView", ""+Product_data.size());
		listView=(ListViewForScrollView)findViewById(R.id.datanear);
		listView.setAdapter(productAdapter);
		listView.setOnItemClickListener(new ListViewOnItemClick() {

			@Override
			public void OnClick(ViewGroup parent, View view, int position,
					Object o) {
				// TODO Auto-generated method stub
				Product_info info = Product_data.get(position);
				Intent intent = new Intent(Near_Info.this, ProductInfo.class);
				intent.putExtra("tag", "Near_Info");
				intent.putExtra("Product", info);
				startActivity(intent);
			}
		});
		
	}
	
	@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
		}

	
	class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			arg0.getId();
	//		Log.v("A-activity点击事件:", "activity->点击了");
			if (arg0.getId()==R.id.listView1) {
				Product_info info=Product_data.get(arg2);
				Intent intent=new Intent(Near_Info.this,ProductInfo.class);
				intent.putExtra("tag", "Near_Info");
				intent.putExtra("Product", info);
				startActivity(intent);
			}
		}
		
	}
	
	@Override
		protected void onPause() {
			// TODO Auto-generated method stub
		Log.v("测试", "onPause");
			super.onPause();
		}
	
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
		Log.v("测试", "onResume");
		if (!City.equals(GetCityName())) {
			City=GetCityName();
			TextView textView=(TextView)findViewById(R.id.activityInfo);
			textView.setText(City+"附近的商家");
			if (changeflag) {
				ReBulidUi();
			}
		GetNearProductData(City);
		}
			super.onResume();		
		}
	
	private void ReBulidUi() {
		ScrollView layout=(ScrollView)findViewById(R.id.scrollView1);
		//	layout.s
			layout.removeAllViews();
			View rebulid=View.inflate(Near_Info.this, R.layout.listviewrebulid, null);
			layout.addView(rebulid);
			changeflag=false;
	}
	
	@Override
		protected void onRestart() {
			// TODO Auto-generated method stub
		Log.v("测试", "onRestart");
			super.onRestart();
		}
	
}
