package HttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Delayed;

import control.ImageFactory;
import control.ImageFactory.ImageFactoryCallBack;
import control.ImageMemoryCache;
import control.ProjectCommand;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;

public class FileDownloadUtil {
	public Handler handler;
	public List<String> ImageUrl;

	public FileDownloadUtil(Context context, List<String> imageName) {
		this.ImageUrl = imageName;

	}

	@SuppressLint("HandlerLeak")
	public void Download(final ImageDownloadUtilCallBack callBack) {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 1) {
					// Log.v("下载", "下载完成");
					boolean flag = true;
					// Bitmap img=(Bitmap)msg.obj;
					callBack.ImageLoad(flag);
				}
				if (msg.what != 1) {
					boolean flag = false;
					callBack.ImageLoad(flag);
				}
			}
		};
		new Thread(new MyThread()).start();
	}

	public interface ImageDownloadUtilCallBack {
		public void ImageLoad(boolean complete);
	}

	class MyThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			File file = new File(ProjectCommand.DownLoad_Image_Path);
			File TMP = new File(ProjectCommand.DownLoad_Image_TMP_Path);
			// msg.obj=mBitmap;
			if (!file.exists()) {
				file.mkdir();
				// Log.v("下载", "创建图片路径："+ProjectCommand.DownLoad_Image_Path);
			}
			if (!TMP.exists()) {
				TMP.mkdir();
				// Log.v("下载",
				// "创建图片路径："+ProjectCommand.DownLoad_Image_TMP_Path);
			}
			ImageUrl = RemoveDuplicate(ImageUrl);
			Boolean flag = true;
			for (int i = 0; i < ImageUrl.size(); i++) {
				flag = flag
						&& StartWork(ProjectCommand.Get_Image_From_Service
								+ ImageUrl.get(i), ImageUrl.get(i));
			}
			new Message();
			Message msMessage = Message.obtain();
			if (flag) {
				msMessage.what = 1;
				handler.sendMessage(msMessage);
			} else {
				msMessage.what = 0;
				handler.sendMessage(msMessage);
			}

		}

	}

	public List<String> RemoveDuplicate(List<String> ImageUrl) { // 去除List里面重复的数据
		HashSet<String> hashSet = new HashSet<String>(ImageUrl);
		ImageUrl.clear();
		ImageUrl.addAll(hashSet);
		return ImageUrl;
	}

	
	/*
	 * 下载图片并且 保存到SD卡中
	 */
	// 传入为产品图片的路径 和 图片名字
	@SuppressWarnings("finally")
	@SuppressLint("NewApi")
	public boolean StartWork(String ImageUrlData, final String ImageName) {
		// HttpClient httpClient=new DefaultHttpClient();
		// HttpPost httpPost=new HttpPost(ImageUrl);

		final File file = new File(ProjectCommand.DownLoad_Image_TMP_Path
				+ ImageName);
		int imageWidth = ProjectCommand.ListImageWidth;
		int imageHeight = ProjectCommand.ListImageHeight;
		if (ImageName.equals(ProjectCommand.Head[0])
				|| ImageName.equals(ProjectCommand.Head[1])
				|| ImageName.equals(ProjectCommand.Head[2])) {
			imageWidth = ProjectCommand.HeadImageWidth;
			imageHeight = ProjectCommand.HeadImageHeight;
		}
		// Log.v("image",""+image.length());
		if (!file.exists() || file.length() <= 0) {
			try {
				// Log.v("下载","图片");
				FileOutputStream fileOutputStream=new FileOutputStream(file);
				URL url = new URL(ImageUrlData);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setConnectTimeout(5 * 1000);
				connection.setRequestMethod("GET");
				int responsecode = connection.getResponseCode();
				if (responsecode == 200) {
					InputStream inputStream=connection.getInputStream();
					byte[] data=new byte[1024];
					int len=0;
					try {						
						while ((len=inputStream.read(data))!=-1) {
							fileOutputStream.write(data,0,len);	
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						if (inputStream!=null) {
							inputStream.close();
						}
						if (fileOutputStream!=null) {
							fileOutputStream.close();
						}
						return true;
					}
				}
				if (responsecode != 200) {
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return true;
		}
		return false;
	}
	
	

}
