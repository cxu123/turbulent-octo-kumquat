package Thread;

import android.content.Context;
import android.widget.ListView;

public class IniListViewThread {
	private Context context;
	private int ListViewID;
	private ListView listView;
	public IniListViewThread(Context context,int ListViewID){
		this.context=context;
		this.ListViewID=ListViewID;
	}
	
	public void IniList(IniListViewThreadCallback callback) {
	
		
	}
		
	public interface IniListViewThreadCallback{
		public void GetListView(ListView listView);
		
	}
	
	class IniListThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
		//	listView=context.
		}
		
	}
	
}
