package control;

import java.util.ArrayList;
import java.util.List;

import com.example.cars_helper.CarsHelperApplication;
import com.example.cars_helper.Home;
import com.example.cars_helper.MainActivity;

import HttpUtils.FileDownloadUtil;
import HttpUtils.FileDownloadUtil.ImageDownloadUtilCallBack;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CareHelperMessageReceiver extends BroadcastReceiver {
	Handler handler;
	@SuppressLint("HandlerLeak")
	public void  GetProductData(final MessageReceive callBack) {
		handler=new Handler(){
				@SuppressWarnings("unchecked")
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					if (msg.what==1) {
						callBack.GetData((List<Product_info>) msg.obj);
					}
				};
			};
		
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub			
		@SuppressWarnings("unchecked")	
		final List<Product_info> data=(List<Product_info>)intent.getSerializableExtra("Data");
		List<String>ProductImageName=new ArrayList<String>();
		for (int i = 0; i < data.size(); i++) {
			ProductImageName.add(data.get(i).getImagePath());
		}
		ProductImageName.add(ProjectCommand.Head[0]);
		ProductImageName.add(ProjectCommand.Head[1]);
		ProductImageName.add(ProjectCommand.Head[2]);
//		Log.v("ProductImageName:", ProductImageName.toString());
		FileDownloadUtil imageDownloadUtil=new FileDownloadUtil(context,ProductImageName);
		imageDownloadUtil.Download(new ImageDownloadUtilCallBack() {			
			@Override
			public void ImageLoad(boolean complete) {
				new Message();
				// TODO Auto-generated method stub
				Message msMessage=Message.obtain();
				msMessage.what=1;
				msMessage.obj=data;
				handler.sendMessage(msMessage);
			}
		});
		
	}
	
	public interface MessageReceive{
		public void GetData(List<Product_info> data);
	}

}
