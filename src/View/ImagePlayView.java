package View;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class ImagePlayView extends LinearLayout{
	
	private Context context;
	
	private ViewPager mViewPager;
	
	private LinearLayout pageLineLayout;
	
	private int count,i;
	
	private Bitmap displayImage,hideImageBitmap;
	
	public LinearLayout.LayoutParams layoutParamsFF = null;
	
	public LinearLayout.LayoutParams layoutParamsFW = null;
	
	public LinearLayout.LayoutParams layoutParamsWF = null;
	
	public LinearLayout.LayoutParams layoutParamsWW = null;
//	private 
	
	private ArrayList<View> mListViews = null;
	
	
//	private LayoutParams;
	
	public ImagePlayView(Context context, AttributeSet attrs) {
		super(context);
		// TODO Auto-generated constructor stub
	    this.context=context;
		layoutParamsFF=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		layoutParamsFW = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWF = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
		layoutParamsWW = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		this.setOrientation(LinearLayout.VERTICAL);
		this.setBackgroundColor(Color.rgb(255, 255, 255));
	}

}
