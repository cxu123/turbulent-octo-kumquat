package com.example.cars_helper;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import control.ImageFactory;
import control.ProjectCommand;
import HttpUtils.FileDownloadUtil;
import HttpUtils.FileDownloadUtil.ImageDownloadUtilCallBack;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class testactivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testactivity);
		Button button = (Button) findViewById(R.id.test_button);
		button.setText("通过百度地图定位到大学城");
//		final ImageView imageView = (ImageView) findViewById(R.id.test_image);
//		ImageFactory imageFactory = new ImageFactory();
//		try {
//			Bitmap bitmap = imageFactory.GetBitmapOfLoadImage(
//					ProjectCommand.DownLoad_Image_TMP_Path
//							+ ProjectCommand.Head[1], 480, 196);
//			imageView.setImageBitmap(bitmap);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		button.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent ;
					try {
						intent=Intent.getIntent("intent://map/geocoder?address=重庆市沙坪坝区大学城中路55号&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
						startActivity(intent); // 启劢调用
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
			}
		});

	}
}
