package com.example.cars_helper;

import java.util.List;

import control.Product_info;
import control.ProjectCommand;
import control.PullToReFreshLayoutListener;
import Adapter.ProductAdapter;
import Thread.GetProductFromKEYWORD;
import Thread.GetProductFromKEYWORD.GetProductFromKEYWORDCallback;
import View.ListViewForScrollView;
import View.PullToRefreshLayout;
import View.ListViewForScrollView.ListViewOnItemClick;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ScrollView;

public class SearchDataShow extends Activity{
	private ListViewForScrollView listView;
	private int HomeListView_name =R.layout.new_list_item;
	private Handler handler;
	private ProgressDialog progressdialog;
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_data_show);
		listView=(ListViewForScrollView)findViewById(R.id.datafind);
		progressdialog=new ProgressDialog(SearchDataShow.this);
		progressdialog.setTitle("提示");
		progressdialog.setMessage("正在获取网络数据");
		progressdialog.setCanceledOnTouchOutside(false);
		progressdialog.show();
		Intent intent=getIntent();
		String keyword=intent.getStringExtra("keyword");
		GetProducts(keyword);
		handler=new Handler(){
			public void handleMessage(Message msg) {
				if (msg.what==1) {
					progressdialog.dismiss();
					@SuppressWarnings("unchecked")
					List<Product_info> data=(List<Product_info>) msg.obj;
					SetDataListView(data);
				}else {
					progressdialog.dismiss();
					ShowNone();
				}
			}
		};
		Ini();
	}

	private void GetProducts(String keyword) {
		// TODO Auto-generated method stub
	//	Log.v("GetProducts", keyword);
		String	ckeyword=keyword;
		String flag_cation=ProjectCommand.Product_classify;
		if (keyword.equals("重庆")) {
			ckeyword="";
			flag_cation="";
		}
		GetProductFromKEYWORD Product=new GetProductFromKEYWORD(ckeyword,flag_cation);
		Product.Dowork(new GetProductFromKEYWORDCallback() {

			@Override
			public void GetProductFromKEYWORDCallbackGetData(
					List<Product_info> data) {
				// TODO Auto-generated method stub
				if (data!=null&&!data.isEmpty()) {
					Message msg=Message.obtain();
					msg.what=1;
					msg.obj=data;
					handler.sendMessage(msg);
				}else {
					Message msg=Message.obtain();
					msg.what=0;
					msg.obj=data;
					handler.sendMessage(msg);
				}
				
			}
		});
	}
	
	private void ShowNone(){
		Log.v("没有获得数据", "没有获得数据");
		ScrollView layout=(ScrollView)findViewById(R.id.scrollView1);
	//	layout.s
		layout.removeAllViews();
		View None=View.inflate(SearchDataShow.this, R.layout.nothingshow, null);
		layout.addView(None);
	}
	
	private void SetDataListView(final List<Product_info> data){		
		if (data==null||data.isEmpty()) {
			Log.v("测试", "data为空");
		}
		((PullToRefreshLayout) findViewById(R.id.refresh_view)).setOnRefreshListener(new PullToReFreshLayoutListener());
		ProductAdapter productAdapter=new ProductAdapter(SearchDataShow.this, data, HomeListView_name);
		listView.setAdapter(productAdapter);
		listView.setOnItemClickListener(new ListViewOnItemClick() {

			@Override
			public void OnClick(ViewGroup parent, View view, int position,
					Object o) {
				// TODO Auto-generated method stub
				Product_info info = data.get(position);
				Intent intent = new Intent(SearchDataShow.this, ProductInfo.class);
				intent.putExtra("tag", "SearchDataShow");
				intent.putExtra("Product", info);
				startActivity(intent);
			}
		});
	}
	
	@SuppressLint("CutPasteId")
	private void Ini(){
		ImageView imageView=(ImageView)findViewById(R.id.imageView1);
		Button button=(Button)findViewById(R.id.button1);
		imageView.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SearchDataShow.this,Project_Home.class);
				intent.putExtra("None", "");
				SearchDataShow.this.setResult(RESULT_CANCELED, intent);
				SearchDataShow.this.finish();
			}
		});
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SearchDataShow.this,Project_Home.class);
				intent.putExtra("None", "");
				SearchDataShow.this.setResult(RESULT_CANCELED, intent);
				SearchDataShow.this.finish();
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}
	
	
}
