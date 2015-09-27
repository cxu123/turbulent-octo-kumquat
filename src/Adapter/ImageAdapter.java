package Adapter;

import com.example.cars_helper.Project_Home;
import com.example.cars_helper.R;
import com.example.cars_helper.SearchDataShow;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private Integer[] images;
	private LayoutInflater inflater;
	private int xml;
	public ImageAdapter(Context context, Integer[] datas,int xml) {
		this.context = context;
		this.images = datas;
		this.inflater = LayoutInflater.from(context);
		this.xml=xml;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return images[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(xml, null);
			
//			holder.text = (TextView) convertView.findViewById(R.id.text);
//			 holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			 
			viewHolder = new ViewHolder();
			viewHolder.imageView=(ImageView)convertView.findViewById(R.id.imageView1);
			viewHolder.imageView.setImageResource(images[position]);
			/*
			 * 1、美食
			 * 2、电影
			 * 3、酒店
			 * 4、KTV
			 * 5、火锅
			 * 6、自助
			 * 7、郊游
			 * 8、娱乐
			 * 
			 */
//			viewHolder.imageView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					switch (position) {
//					case 1:
//						Intent intent=new Intent();
//						break;
//
//					default:
//						break;
//					}
//				}
//			});
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
//		
//		holder.text.setText(DATA[position]);
//		holder.icon.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
	
		return convertView;
	}
	
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		if (observer!=null) {
			super.unregisterDataSetObserver(observer);
		}
		
	}

 class ViewHolder {
	TextView textView1;
	TextView textView2;
	ImageView imageView;
}
}
