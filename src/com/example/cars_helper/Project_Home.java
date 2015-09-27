package com.example.cars_helper;

import java.util.List;

import service.HttpServer;
import control.CareHelperMessageReceiver;
import control.CareHelperMessageReceiver.MessageReceive;
import control.Product_info;
import control.ProjectCommand;
import Adapter.ImageAdapter;
import Adapter.NewPagerAdapter;
import Adapter.ProductAdapter;
import Thread.GetProductFromKEYWORD;
import Thread.GetProductFromKEYWORD.GetProductFromKEYWORDCallback;
import View.ListViewForScrollView;
import View.ListViewForScrollView.ListViewOnItemClick;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class Project_Home extends Activity {
	CarsHelperApplication application;
	ViewPager viewpager;
	GridView gridLayout;
	ListViewForScrollView homeListView;
	List<Product_info> Productdata;
	List<Product_info> newProductdata;
	Button City;
	ImageView Souch;
	EditText SouchkeyWord;
	String APPCity;
	ProductAdapter imageAdapter;
	Handler handler;
	boolean ui_wtich = false; // 辨别listview是否被删除
	int Gridview_xml_name = R.layout.grid_view_items;
	int HomeListView_name = R.layout.new_list_item;
	int datas[] = { R.drawable.view_add_1, R.drawable.view_add_2,
			R.drawable.view_add_3 };
	int ImageNumber, NowImageCurrent = 0;
	String[] items;
	ImageView image, dot, dots[];
	// MessageReceiver msg;
	int currentindex = 0;
	LinearLayout viewLayout;
	RelativeLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.v("onCreate", "onCreate");
		setContentView(R.layout.activity_project_home);
		application = (CarsHelperApplication) getApplication();
		Productdata = application.GetAppData();
		APPCity = application.GetCityName();
		Ini();
		InitViewPager(); // 初始化VIew视图
		InitDot(); // 初始化图片点序列视图
		if (Productdata == null || Productdata.isEmpty()) {
			layout = (RelativeLayout) findViewById(R.id.ProjectBody);
			layout.removeAllViews();
			layout.setGravity(17); // 17为居中
			View net_error = View.inflate(this, R.layout.net_error, null);
			layout.addView(net_error);
			Button button = (Button) layout.findViewById(R.id.tr);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(Project_Home.this, "正在重新连接...",
							Toast.LENGTH_LONG).show();
					// setContentView(R.layout.activity_project_home);
					Rebuild();
				}
			});
		} else {
			InitListView();
		}
		InitGridView(); // 初始化Gridview
		StartImageChange();
	}

	private void Rebuild() {
		Intent intent = new Intent(this, HttpServer.class);
		CareHelperMessageReceiver msg = new CareHelperMessageReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("GetProductInfo");
		registerReceiver(msg, intentFilter);
		startService(intent);
		msg.GetProductData(new MessageReceive() {
			@Override
			public void GetData(final List<Product_info> data) {
				// TODO Auto-generated method stub
				layout.removeAllViews();
				View rebulid = View.inflate(Project_Home.this,
						R.layout.rebulid_listview, null);
				layout.addView(rebulid);
				homeListView = (ListViewForScrollView) layout
						.findViewById(R.id.HomeListView);
				ProductAdapter imageAdapter = new ProductAdapter(
						Project_Home.this, data, HomeListView_name);
				homeListView.setAdapter(imageAdapter);
				homeListView.setOnItemClickListener(new ListViewOnItemClick() {
					@Override
					public void OnClick(ViewGroup parent, View view,
							int position, Object o) {
						// TODO Auto-generated method stub
						Product_info info = data.get(position);
						Intent intent = new Intent(Project_Home.this,
								ProductInfo.class);
						intent.putExtra("tag", "ListView");
						intent.putExtra("Product", info);
						startActivity(intent);
					}
				});
			}
		});

	}

	private void InitGridView() {
		// TODO Auto-generated method stub
		gridLayout = (GridView) findViewById(R.id.homegridView);
		Integer[] datas = { R.drawable.gview1, R.drawable.gview2,
				R.drawable.gview3, R.drawable.gview4, R.drawable.gview5,
				R.drawable.gview6, R.drawable.gview7, R.drawable.gview8 };
		ImageAdapter imageAdapter = new ImageAdapter(this, datas,
				Gridview_xml_name);
		gridLayout.setAdapter(imageAdapter);
		gridLayout.setOnItemClickListener(new OnItemClickListener() {
			/*
			 * 1、美食 2、电影 3、酒店 4、KTV 5、火锅 6、自助 7、郊游 8、娱乐
			 */
			@SuppressLint("ShowToast")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					Intent intent = new Intent(Project_Home.this,
							SearchDataShow.class);
					intent.putExtra("keyword", "重庆");
					startActivityForResult(intent, 2);
					break;
				case 4:
					Intent intent2 = new Intent(Project_Home.this,
							SearchDataShow.class);
					intent2.putExtra("keyword", "火锅");
					startActivityForResult(intent2, 2);
					break;
				case 5:
					Intent intent3 = new Intent(Project_Home.this,
							SearchDataShow.class);
					intent3.putExtra("keyword", "自助");
					startActivityForResult(intent3, 2);
					break;
				default:
					Toast.makeText(Project_Home.this, "目前只开通了火锅、美食、自助服务", Toast.LENGTH_SHORT).show();;
					break;
				}
			}
		});
	}

	private void InitListView() {

		homeListView = (ListViewForScrollView) findViewById(R.id.HomeListView);

		homeListView.setAdapter(imageAdapter);
		homeListView.addFooterView();
		homeListView.setOnItemClickListener(new ListViewOnItemClick() {
			@Override
			public void OnClick(ViewGroup parent, View view, int position,
					Object o) {
				// TODO Auto-generated method stub
				Product_info info = Productdata.get(position);
				Intent intent = new Intent(Project_Home.this, ProductInfo.class);
				intent.putExtra("tag", "Project_Home");
				intent.putExtra("Product", info);
				startActivity(intent);
			}
		});
	}

	private void StartImageChange() {
		// TODO Auto-generated method stub
		final Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			int i = viewpager.getCurrentItem();

			public void run() {
				// int count = items.size();
				if (i < ImageNumber) {
					i++;
				}
				if (i == ImageNumber) {
					i = 0;
				}
				// Log.v("注意开始跟换Image", "");
				viewpager.setCurrentItem(i, true);

				handler.postDelayed(this, 5000);
			}
		};
		if (handler != null) {
			handler.postDelayed(runnable, 3000);
		}
	}

	// 初始化VIew视图
	private void InitViewPager() {
		// TODO Auto-generated method stub
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		NewPagerAdapter newPagerAdapter = new NewPagerAdapter(items, this);
		// Log.v("PagerAdapter-->", ""+newPagerAdapter.getCount());
		// Log.v("PagerAdapter-->", "");
		// Log.v("PagerAdapter-->", "");
		// Log.v("PagerAdapter-->", "");
		viewpager.setAdapter(newPagerAdapter);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < ImageNumber; i++) {
					dots[arg0].setBackgroundResource(R.drawable.dotc);
					if (arg0 != i) {
						dots[i].setBackgroundResource(R.drawable.dotn);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		// Log.v("设置了", "点击事件");
		// viewpager.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Log.v("viewpage被点击了->", "点击的当前页面是："+viewpager.getCurrentItem());
		// }
		// });
	}

	// 初始化图片点序列视图
	private void InitDot() {
		// TODO Auto-generated method stub
		viewLayout = (LinearLayout) findViewById(R.id.dotlayout);
		dots = new ImageView[ImageNumber];
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(2, 2, 2, 2);
		for (int i = 0; i < ImageNumber; i++) {
			dot = new ImageView(this);
			dot.setLayoutParams(layoutParams);
			dots[i] = dot;
			dots[i].setTag(i);
			dots[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int position = (Integer) v.getTag();
					setCurView(position);
					setCurDot(position);
				}
			});
			if (i == 0) {
				dots[i].setBackgroundResource(R.drawable.dotc);
			} else {
				dots[i].setBackgroundResource(R.drawable.dotn);
			}
			// Log.v("信息", ":" + dots[i]);
			viewLayout.addView(dots[i]);
		}
	}

	/**
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= ImageNumber) {
			return;
		}

		viewpager.setCurrentItem(position, true);
		currentindex = position;
	}

	/**
	 * 选中当前引导小点
	 */
	private void setCurDot(int position) {
		if (position < 0 || position > ImageNumber - 1) {
			return;
		}

		dots[position].setBackgroundResource(R.drawable.dotc);

		currentindex = position;

	}

	public class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			arg0.getId();
			// Log.v("点击", "事件");
			if (arg0.getId() == R.id.homegridView) {
				Intent intent = new Intent(Project_Home.this, ProductInfo.class);
				intent.putExtra("tag", "GridView");
				Log.v("", "");
			}
			if (arg0.getId() == R.id.HomeListView) {
				Product_info info = Productdata.get(arg2);
				Intent intent = new Intent(Project_Home.this, ProductInfo.class);
				intent.putExtra("tag", "Project_Home");
				intent.putExtra("Product", info);
			//	startActivity(intent);
				startActivityForResult(intent, 2);
			}
		}

	}

	public void Ini() {
		imageAdapter = new ProductAdapter(this, Productdata, HomeListView_name);
		items = ProjectCommand.Head;
		// Log.v("图片路径:", items.toString());
		ImageNumber = items.length;
		City = (Button) findViewById(R.id.button2);
		City.setText(APPCity);
		Souch = (ImageView) findViewById(R.id.keyworksouch);
		SouchkeyWord = (EditText) findViewById(R.id.editText1);
		City.setOnClickListener(new ClickListener());
		Souch.setOnClickListener(new ClickListener());
		Button tr=(Button)findViewById(R.id.tr);
		tr.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("点击了", "点击了");
				Intent intent = new Intent(Project_Home.this,
						SearchDataShow.class);
				intent.putExtra("keyword", "重庆");
				startActivityForResult(intent, 2);
			}
		});
	}

	class ClickListener implements OnClickListener {

		@SuppressLint({ "ShowToast", "NewApi" })
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button2: // 如果点击的是定位城市
				// Log.v("点击了：", "定位城市");
				Intent ChoseCity = new Intent(Project_Home.this, CityList.class);
				ChoseCity.putExtra("NowCity", City.getText());
				startActivityForResult(ChoseCity, 1);
				break;
			case R.id.keyworksouch:
				// Log.v("点击了：", "搜索");
				String keyWord = SouchkeyWord.getText().toString().trim();
				if (keyWord.isEmpty()) {
					Toast.makeText(Project_Home.this, "请输入关键词",
							Toast.LENGTH_SHORT).show();
					break;
				} else {
					Intent intent = new Intent(Project_Home.this,
							SearchDataShow.class);
					intent.putExtra("keyword", keyWord);
					startActivityForResult(intent, 2);
					break;
				}
			default:
				break;
			}
		}

	}

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		// Log.v("获得的城市名字：", city_name);
		// Log.v("获得的requestCode", ""+requestCode);
		// Log.v("获得的resultCode", ""+resultCode);
		switch (requestCode) {

		case 1:
			if (data != null) {
				String city_name = data.getStringExtra("Chose_City");
				if (city_name != null && (!city_name.isEmpty())) {
					City.setText(city_name);
					application.SetCityName(city_name);
					GetNewProductInfo(city_name);
				}
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void GetNewProductInfo(String city_name) {
		// TODO Auto-generated method stub
		Productdata.clear();
		String action_flag;
		String city;
		if (ui_wtich) {
			RebuildListVIew();
		}
		if (city_name.equals("重庆")) {
			action_flag = "";
			city = "";
		} else {
			action_flag = ProjectCommand.Product_classify;
			city = city_name;
		}
		handler = new Handler() {
			@SuppressLint("HandlerLeak")
			@SuppressWarnings("unchecked")
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					Log.v("测试", "测试");
					Productdata = (List<Product_info>) msg.obj;
					Log.v("Productdata的大小", "" + Productdata.size());
					imageAdapter.SetNewData(Productdata);
					imageAdapter.notifyDataSetChanged();
					InitListView();
				} else {
					ShowNone();
				}
			};
		};

		GetProductFromKEYWORD productFromKEYWORD = new GetProductFromKEYWORD(
				city, action_flag);
		Log.v("productFromKEYWORD", "productFromKEYWORD");
		productFromKEYWORD.Dowork(new GetProductFromKEYWORDCallback() {

			@Override
			public void GetProductFromKEYWORDCallbackGetData(
					List<Product_info> data) {
				// TODO Auto-generated method stub
				if (data != null && !data.isEmpty()) {
					Message msg = Message.obtain();
					msg.what = 1;
					msg.obj = data;
					Log.v("GetProduct", "" + data.size());
					handler.sendMessage(msg);
				} else {
					Message msg = Message.obtain();
					msg.what = 0;
					msg.obj = data;
					handler.sendMessage(msg);
				}
			}

		});
	}

	private void RebuildListVIew() {
		// TODO Auto-generated method stub
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.ProjectBody);
		layout.removeAllViews();
		View rebulid = View.inflate(Project_Home.this,
				R.layout.rebulid_listview, null);
		layout.addView(rebulid);
	}

	private void ShowNone() {
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.ProjectBody);
		// layout.s
		layout.removeAllViews();
		View None = View.inflate(Project_Home.this, R.layout.nothingshow, null);
		layout.addView(None);
		ui_wtich = true;
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.v("onStart", "onStart");
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("onResume", "onResume");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("onResume", "onResume");
	}
}
