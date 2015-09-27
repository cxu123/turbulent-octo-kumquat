package com.example.cars_helper;

import java.util.List;

import control.CityListData;
import View.NewGridView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/*
 * 商圈选择列表，由ListView套用Gridview来使用
 */

public class CityList extends Activity {
	ListView city;
	CityListData cityList;
	ImageView imageView;
	Button button;
	int xml;
	private LayoutInflater inflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_citylist);
		city=(ListView)findViewById(R.id.listView1);
	    cityList=new CityListData();
	    CityOnClick cityOnClick=new CityOnClick();
	   imageView=(ImageView)findViewById(R.id.imageView1);
	   imageView.setOnClickListener(cityOnClick);
	   button=(Button)findViewById(R.id.tr);
	   button.setOnClickListener(cityOnClick);
	    CityListAdapter cityListAdapter=new CityListAdapter();
		city.setAdapter(cityListAdapter);
	   
	}
	
	class CityOnClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(CityList.this,Project_Home.class);
			intent.putExtra("Chose_City", "");
			CityList.this.setResult(RESULT_CANCELED, intent);
			CityList.this.finish();
		}
		
	}
	
	//listview的适配器
	
	class CityListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		public CityListAdapter(){
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cityList.GetCityCount();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder viewHolder;
			 inflater=LayoutInflater.from(CityList.this);
			 xml=R.layout.citylist_item;
			 CityGridViewAdapter cityGridViewAdapter=new CityGridViewAdapter(cityList.GetCityOneData(position));
			if (convertView==null) {
				convertView=inflater.inflate(xml, null);
				viewHolder=new ViewHolder();
				viewHolder.gridView=(NewGridView)convertView.findViewById(R.id.gridView1);
				viewHolder.textView=(TextView)convertView.findViewById(R.id.textView1);
				convertView.setTag(viewHolder);
			}else {
				viewHolder=(ViewHolder)convertView.getTag();
			}
			viewHolder.gridView.setAdapter(cityGridViewAdapter);		//设定GridView
			viewHolder.textView.setText(cityList.GetCityF(position,0));
			return convertView;
		}
		 class ViewHolder {
			 	TextView textView;
				NewGridView gridView;
			}
	}
	
	
	//GridView的适配器
	
	class CityGridViewAdapter extends BaseAdapter{

		List<String>Data;
		
		public CityGridViewAdapter(List<String>Data){
			this.Data=Data;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			G_ViewHolder gHolder;
			int G_xml=R.layout.city_gridview_item;
			 inflater=LayoutInflater.from(CityList.this);
			if (convertView==null) {
				gHolder=new G_ViewHolder();
				convertView=inflater.inflate(G_xml, null);
				gHolder.button=(Button)convertView.findViewById(R.id.tr);
				convertView.setTag(gHolder);
			}else {
				gHolder=(G_ViewHolder)convertView.getTag();
			}
			final String City_name=Data.get(position);
			gHolder.button.setText(City_name);
			gHolder.button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(CityList.this,Project_Home.class);
					intent.putExtra("Chose_City", City_name);
					CityList.this.setResult(RESULT_OK, intent);
					CityList.this.finish();
				}
			});
						
			return convertView;
		}
		
		 class G_ViewHolder {
			 	Button button;
			}
		
	}
	
	/*
	 * 
	 * 用户可能没有点击返回按钮 也没有 选择城市 而是直接点击Back键
	 * 所以需要在Destroy里面加入
	 * (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		setResult(RESULT_CANCELED,intent);
		super.onDestroy();
	}
	
}
