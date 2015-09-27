package Adapter;

import java.util.List;

import com.example.cars_helper.R;

import control.ImageMemoryCache;
import control.Product_info;
import Thread.GetViewForRunnable;
import Thread.GetViewForRunnable.GetViewForRunnableCallback;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {
	
	public static  List<Product_info> productData;
	private int xml;
	private Context context;
	private LayoutInflater inflater;
	ImageMemoryCache imageMemoryCache;
	private GetViewForRunnable getViewForRunnable;
	public  ProductAdapter(Context context,List<Product_info> product_infos,int xml) {
		// TODO Auto-generated constructor stub
		this.context=context;
		ProductAdapter.productData=product_infos;
		this.xml=xml;
		this.inflater = LayoutInflater.from(context);
		imageMemoryCache=new ImageMemoryCache();
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return productData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return productData.get(position);
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
	//	new Thread(new GetViewForRunnable()).start();
//		Log.v("ProductAdapter", "GetView");
		if (convertView==null) {
			convertView=inflater.inflate(xml, null);
			viewHolder=new ViewHolder();
			viewHolder.textView1=(TextView)convertView.findViewById(R.id.textView1);
			viewHolder.textView2=(TextView)convertView.findViewById(R.id.textView2);
			viewHolder.textView3=(TextView)convertView.findViewById(R.id.textView3);
			viewHolder.imageView=(ImageView)convertView.findViewById(R.id.imageView1);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder)convertView.getTag();
		}
		Product_info data=productData.get(position);
		if (data!=null) {
			viewHolder.textView1.setText( data.getName());		//"产品名称：" +
			viewHolder.textView2.setTextColor(Color.rgb(125, 125, 125));
			viewHolder.textView2.setText(data.getAddress());	//"产品地址：" + 
			viewHolder.textView3.setTextColor(Color.rgb(255, 0, 255));
			viewHolder.textView3.setText( data.getPrice());	//"产品价格：" +
			getViewForRunnable=new GetViewForRunnable( data.getImagePath());
			getViewForRunnable.GetImageView(new GetViewForRunnableCallback() {
				@Override
				public void Get_View(Bitmap bitmap) {
					// TODO Auto-generated method stub
					viewHolder.imageView.setImageBitmap(bitmap);
				}
			});
			
//			ImageFactory imageFactory=new ImageFactory();
//			
//			try {				
//				if (imageMemoryCache.GetBitmapFromMemoryCache(data.getImagePath())==null) {
//					Bitmap bitmap=imageFactory.GetBitmapOfLoadImage(ProjectCommand.DownLoad_Image_TMP_Path+data.getImagePath());				
//					viewHolder.imageView.setImageBitmap(bitmap);
//					imageMemoryCache.AddBitmapToMemoryCache(data.getName(), bitmap);
//					//bitmap.recycle();
//					bitmap=null;
//				}else {
//					viewHolder.imageView.setImageBitmap(imageMemoryCache.GetBitmapFromMemoryCache(data.getImagePath()));
//					Log.v("listview 的图片", "从手机内存中得到");
//				}
//				
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			
			
		}
//		ImageDownloadUtil imageDownloadUtil=new ImageDownloadUtil(this.context, "a.jpg");
//		imageDownloadUtil.Download(new ImageDownloadUtilCallBack() {
//			
//			@Override
//			public void ImageLoad(boolean complete) {
//				// TODO Auto-generated method stub
//				if (complete) {
//					ImageFactory imageFactory=new ImageFactory();
//					if (bitmap!=null) {
//						viewHolder.imageView.setImageBitmap(bitmap);
//					}
//					
//					viewHolder.imageView.setImageBitmap(imageFactory.GetBitmapOfNetImage(bitmap, 96, 65));
//					viewHolder.imageView.setImageBitmap(imageFactory.GetBitmapOfLoadImage(ProjectCommand.DownLoad_Image_TMP_Path+"a.jpg", 160, 130));
//				}
//			}
//		});
		return convertView;
	}
	
	 class ViewHolder {
		TextView textView1;
		TextView textView2;
		TextView textView3;
		ImageView imageView;
	}
	 
	 @Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		 if (observer!=null) {
			 super.unregisterDataSetObserver(observer);
		}
		
	}
	public void SetNewData(List<Product_info> newProductdata) {
		// TODO Auto-generated method stub
		ProductAdapter.productData=newProductdata;
		Log.v("数据更新大小：",""+ productData.size());
	}
	 
}
