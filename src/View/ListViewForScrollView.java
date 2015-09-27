package View;


import Adapter.ProductAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ListViewForScrollView extends LinearLayout {

	private ProductAdapter productAdapter;
	
	private ListViewOnItemClick click;
	
	public ListViewForScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		IniAttr(null);
	}
	
	public  ListViewForScrollView(Context context,AttributeSet attrs) {
		super(context,attrs);
		// TODO Auto-generated constructor stub
		IniAttr(attrs);
	}
	//设置Linearlayout的方向
	public void IniAttr(AttributeSet attrs) {
		setOrientation(VERTICAL);
	}
	
	public void setAdapter(ProductAdapter productAdapter) {
		this.productAdapter=productAdapter;
		removeAllViews();
		notifyChange();
	}

	private void notifyChange() {
		// TODO Auto-generated method stub
		final int count=productAdapter.getCount();
//		LayoutParams params=new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < count; i++) {
			final int index=i;
			final LinearLayout layout = new LinearLayout(getContext());
			View view=productAdapter.getView(i, null, null);
			view.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					click.OnClick(ListViewForScrollView.this, layout, index, index);
				}
			});
			addView(view, i);
		}
	}
	
	public void setOnItemClickListener(ListViewOnItemClick click){
		Log.v("Listview", "点击事件");
		this.click=click;
	}
	
	public interface ListViewOnItemClick{
		public void OnClick(ViewGroup parent, View view, int position,Object o);
	}

	public void addFooterView() {
		// TODO Auto-generated method stub
		
	}
	
}
