package Adapter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import control.ImageFactory;
import control.ImageMemoryCache;
import control.ProjectCommand;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class NewPagerAdapter extends android.support.v4.view.PagerAdapter {

	private String[] Item;
	private Context context;
	private ImageFactory imageFactory;
	private ImageMemoryCache imageMemoryCache;
	private List<View> listview;
	
	public NewPagerAdapter(String[] Item, Context context) {
		this.Item = Item;
		this.context = context;
		imageFactory = new ImageFactory();
		imageMemoryCache = new ImageMemoryCache();
		listview=new ArrayList<View>();
		SetView();
	}

	public void SetView() {
		Bitmap bitmap;
		for (int i = 0; i < Item.length; i++) {
			ImageView imageView = new ImageView(this.context);
				bitmap = imageMemoryCache.GetBitmapFromMemoryCache(Item[i]);
//				Log.v("图片路径:", Item[i]);
				if (bitmap==null) {
					try {
						bitmap=imageFactory.GetBitmapOfLoadImage(ProjectCommand.DownLoad_Image_TMP_Path+Item[i],ProjectCommand.HeadImageWidth,ProjectCommand.HeadImageHeight);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					imageMemoryCache.AddBitmapToMemoryCache(Item[i], bitmap);
				}
				if (bitmap!=null) {
					imageView.setImageBitmap(bitmap);
				}
				
			listview.add(imageView);
			
			
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listview.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == (arg1);
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(listview.get(position));

	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@SuppressWarnings("finally")
	@Override
	public Object instantiateItem(View container, final int position) {
		// TODO Auto-generated method stub
			View v=listview.get(position);
			((ViewPager) container).addView(v,0);
			// ((ViewGroup) container).addView(imageView, position);
			// ((ViewPager)container).removeView(imageView);		
			v.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (Item[position].equals(ProjectCommand.Head[position])) {
						Log.v("点击的图片是：", ProjectCommand.Head[position]);
					}
				}
			});
			return v;		
	}
}
