package com.example.cars_helper;

import java.util.ArrayList;
import java.util.List;

import control.MoveImage;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;

public class Home extends TabActivity {
	private TabHost tabHost;
	private RadioGroup radioGroup;
	private RelativeLayout botton_Layout;
	int startLeft=0;
	int imageviewwieth=0;
	RadioButton radio_two,radio_three,radio_four,radio_five;
	 List< RadioButton> view =null ;
	 List<Integer> imagepath;
	@SuppressWarnings({ "deprecation", "null" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activiay_home);
		botton_Layout = (RelativeLayout) findViewById(R.id.layout_bottom);
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("home").setIndicator("Home").setContent(new Intent(this, Project_Home.class)));
	//	tabHost.addTab(tabHost.newTabSpec("home").setIndicator("Home").setContent(new Intent(this, LoginNew.class)));
		tabHost.addTab(tabHost.newTabSpec("two").setIndicator("two").setContent(new Intent(this, Near_Info.class)));	//Home_Info
		tabHost.addTab(tabHost.newTabSpec("three").setIndicator("three").setContent(new Intent(this, LoginNew.class)));
		tabHost.addTab(tabHost.newTabSpec("four").setIndicator("four").setContent(new Intent(this, testactivity.class)));
//		tabHost.addTab(tabHost.newTabSpec("five").setIndicator("five").setContent(new Intent(this, testactivity.class)));
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioGroup.setOnCheckedChangeListener(checkedChangeListener);		
		radio_two=(RadioButton) findViewById(R.id.radio_two);
		radio_three=(RadioButton) findViewById(R.id.radio_three);
		radio_four=(RadioButton) findViewById(R.id.radio_four);
		radio_five=(RadioButton) findViewById(R.id.radio_five);
		radio_two.setBackgroundResource(R.drawable.house_click);
		view=new ArrayList<RadioButton>();
		view.add(radio_two);
		view.add(radio_three);
		view.add(radio_four);
		view.add(radio_five);
		imagepath=new ArrayList<Integer>();
		imagepath.add(R.drawable.house);
		imagepath.add(R.drawable.near);
		imagepath.add(R.drawable.user);
		imagepath.add(R.drawable.more);
	}

	private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {
		int i=0;
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radio_two:
				if (i!=0) {
					view.get(i).setBackgroundResource(imagepath.get(i));
					tabHost.setCurrentTabByTag("home");
					radio_two.setBackgroundResource(R.drawable.house_click);
					i=0;
				}
				break;
			case R.id.radio_three:
				if (i!=1) {
					view.get(i).setBackgroundResource(imagepath.get(i));
					tabHost.setCurrentTabByTag("two");
					radio_three.setBackgroundResource(R.drawable.near_click);
					i=1;
				}
				break;
			case R.id.radio_four:
				if (i!=2) {
					view.get(i).setBackgroundResource(imagepath.get(i));
					tabHost.setCurrentTabByTag("three");
					radio_four.setBackgroundResource(R.drawable.user_click);
					i=2;
				}
				break;
			case R.id.radio_five:
				if (i!=3) {
					view.get(i).setBackgroundResource(imagepath.get(i));
					tabHost.setCurrentTabByTag("four");
					radio_five.setBackgroundResource(R.drawable.more_click);
					i=3;
				}
				break;
			default:
				break;
			}
		}
	};
}
