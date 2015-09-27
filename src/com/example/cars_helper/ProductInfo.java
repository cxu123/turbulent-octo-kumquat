package com.example.cars_helper;

import java.io.FileNotFoundException;

import control.ImageFactory;
import control.Product_info;
import control.ProjectCommand;
import service.GetProductInfoService;
import HttpUtils.DataReadFromService;
import HttpUtils.DataReadFromService.DataReadCallback;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductInfo extends Activity {

	private TextView textView, textView2;
	private Product_info info;
	private Button button, button2;
	private ImageView imageView;
	private Handler handler;
	private String tag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// Log.v("这里是B-activity", "OnCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activaty_productinfo);
		Ini();
		IniImageLog();
		GetProductDetail();
	}

	private void Ini() {
		textView = (TextView) findViewById(R.id.textView1);
		Intent in = getIntent();
		tag = in.getStringExtra("tag");
		info = (Product_info) in.getSerializableExtra("Product");
		textView2 = (TextView) findViewById(R.id.textView2);
		textView2.setText("价格：" + info.getPrice());
		button = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button3);
		imageView = (ImageView) findViewById(R.id.imageView5);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ProductInfo.this, "预定成功", Toast.LENGTH_LONG)
						.show();

			}
		});
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("SearchDataShow", tag);
				if (tag.equals("Project_Home")) {
					Intent intent = new Intent(ProductInfo.this, Project_Home.class);
					intent.putExtra("None", "");
					ProductInfo.this.setResult(RESULT_CANCELED, intent);
					ProductInfo.this.finish();
				}
				if (tag.equals("SearchDataShow")) {
					Intent intent = new Intent(ProductInfo.this, SearchDataShow.class);
					intent.putExtra("None", "");
					ProductInfo.this.setResult(RESULT_CANCELED, intent);
					ProductInfo.this.finish();
				}
				if (tag.equals("Near_Info")) {
					Intent intent = new Intent(ProductInfo.this, Near_Info.class);
					intent.putExtra("None", "");
					ProductInfo.this.setResult(RESULT_CANCELED, intent);
					ProductInfo.this.finish();
				}
			}
		});
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (tag.equals("Project_Home")) {
					Intent intent = new Intent(ProductInfo.this, Project_Home.class);
					intent.putExtra("None", "");
					ProductInfo.this.setResult(RESULT_CANCELED, intent);
					ProductInfo.this.finish();
				}
				if (tag.equals("SearchDataShow")) {
					Intent intent = new Intent(ProductInfo.this, SearchDataShow.class);
					intent.putExtra("None", "");
					ProductInfo.this.setResult(RESULT_CANCELED, intent);
					ProductInfo.this.finish();
				}
				
			}
		});
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 1) {
					Log.v("获得的数据-》", msg.obj.toString());
				}
			}
		};
	}

	private void IniImageLog() {
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		ImageFactory imageFactory = new ImageFactory();
		Bitmap bm;
		try {
			bm = imageFactory.GetBitmapOfLoadImage(
					ProjectCommand.DownLoad_Image_TMP_Path
							+ info.getImagePath(),
					ProjectCommand.ProductPICwidth,
					ProjectCommand.ProductPICheight);
			imageView.setImageBitmap(bm);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void GetProductDetail() {
		Log.v("GetProductDetail", "开始");
		DataReadFromService dataReadFromService = new DataReadFromService(
				ProjectCommand.Get_ProductText_From_Service + "test.txt");
		dataReadFromService.StartWork(new DataReadCallback() {

			@Override
			public void DataReadCallbackGetData(StringBuffer buff) {
				// TODO Auto-generated method stub
				Message msg = Message.obtain();
				msg.what = 1;
				msg.obj = buff;
				handler.sendMessage(msg);
			}
		});
	}
}
