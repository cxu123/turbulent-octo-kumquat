package View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NewGridView extends GridView {

	/*
	 * 该类为了解决在与带有滚动效果的控件互相冲突从而导致功能
	 * 有问题而重新定义的
	 */
	public NewGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public NewGridView(Context context,AttributeSet attrs) {
		super(context,attrs);
		// TODO Auto-generated constructor stub
	}
	
	public NewGridView(Context context,AttributeSet attrs,int defStyls) {
		super(context,attrs,defStyls);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		 int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
}
