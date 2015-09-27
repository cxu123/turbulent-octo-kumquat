package Thread;

import java.io.FileNotFoundException;
import control.ImageFactory;
import control.ProjectCommand;
import android.R.string;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class GetViewForRunnable {
	private Bitmap bitmap;
	Handler handler;
	private String ImageName;
	public GetViewForRunnable(String ImageName) {
		this.ImageName=ImageName;
	}

	@SuppressLint("HandlerLeak")
	public void GetImageView(final GetViewForRunnableCallback callback) {
		 handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 1) {
					bitmap = (Bitmap) msg.obj;
					if (bitmap!=null) {
						callback.Get_View(bitmap);
					}
				}
			}
		};
		new Thread(new MyRunnable()).start();
//		return bitmap;
	}
	
	public interface GetViewForRunnableCallback{
		public void Get_View(Bitmap bitmap);
	}
	
	class MyRunnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ImageFactory imageFactory = new ImageFactory();
			try {
//				Log.v("²âÊÔ", "ListView");
//				Log.v("ProjectCommand.ListImageWidth->"+ProjectCommand.ListImageWidth, "ProjectCommand.HeadImageHeight->"+ProjectCommand.HeadImageHeight);
				Bitmap bitmap = imageFactory.GetBitmapOfLoadImage(ProjectCommand.DownLoad_Image_TMP_Path+ ImageName,ProjectCommand.ListImageWidth,ProjectCommand.ListImageHeight);
				new Message();
				Message msMessage=Message.obtain();
				msMessage.what=1;
				msMessage.obj=bitmap;
				handler.sendMessage(msMessage);
				bitmap = null;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
