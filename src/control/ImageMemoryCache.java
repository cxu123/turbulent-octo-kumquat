package control;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

public class ImageMemoryCache  {
	public int MaxMemory;
	public LruCache<String,Bitmap>mMemoryCache;
	
	public ImageMemoryCache(){
		CutMemoryCache();
	}
	
	public void AddBitmapToMemoryCache(String key,Bitmap bitmap) {
		if (GetBitmapFromMemoryCache(key)==null&&bitmap!=null) {
			mMemoryCache.put(key, bitmap);
//			Log.v("�����õ�ͼƬ", "�Ѿ����浽Memorycache��");
		}
	}
	
	public void CutMemoryCache(){
		MaxMemory=(int)Runtime.getRuntime().maxMemory()/8;
		mMemoryCache=new LruCache<String, Bitmap>(MaxMemory){
			@SuppressLint("NewApi")
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				return value.getByteCount();
			}
		};
	}
	
	public Bitmap GetBitmapFromMemoryCache(String key) {
	//	Log.v("�����õ�ͼƬ", "�Ѿ����ͼƬ��cache");
		return mMemoryCache.get(key);
	}
	
}
