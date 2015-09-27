package View;



import Interface.Pullable;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class PullableScrollView extends ScrollView implements Pullable{

	public PullableScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public PullableScrollView(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub		
		super(context, attrs);
	}
	
	public  PullableScrollView(Context context,AttributeSet attrs,int defStyle){
		super(context, attrs, defStyle);
		
	}
	@Override
	public boolean canPullDown(){
		if (getScrollY()==0) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public boolean canPullUp() {
		if (getScrollY()>=(getChildAt(0).getHeight()-getMeasuredHeight())) {
			return true;
		}else {
			return false;
		}
	}
}
