package control;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import View.PullToRefreshLayout;
import View.PullToRefreshLayout.OnRefreshListener;

public class PullToReFreshLayoutListener implements OnRefreshListener {

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		// ����ˢ�²���
				new Handler()
				{
					@Override
					public void handleMessage(Message msg)
					{
						// ǧ������˸��߿ؼ�ˢ�������Ŷ��
						Log.v("PullToReFreshLayoutListener", "����2--������");
						pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
					}
				}.sendEmptyMessageDelayed(0, 10000);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		// ���ز���
				new Handler()
				{
					@Override
					public void handleMessage(Message msg)
					{
						Log.v("PullToReFreshLayoutListener", "����2--������");
						// ǧ������˸��߿ؼ����������Ŷ��
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}
				}.sendEmptyMessageDelayed(0, 1000);
	}

}
