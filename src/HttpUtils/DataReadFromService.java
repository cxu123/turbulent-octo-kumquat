package HttpUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class DataReadFromService {
	public String urlpath;
	public BufferedReader str;
	public DataReadFromService(String url) {
		// TODO Auto-generated constructor stub
		this.urlpath = url;
	}

	public interface DataReadCallback {
		public void DataReadCallbackGetData(StringBuffer buff);
	}

	public void StartWork(final DataReadCallback callback) {

		class DataRead implements Runnable {

			@SuppressWarnings("null")
			@Override
			public void run() {
				try {
					// TODO Auto-generated method stub
					URL url = new URL(urlpath);
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setConnectTimeout(5 * 1000);
					connection.setRequestMethod("GET");
					int responsecode = connection.getResponseCode();
					if (responsecode == 200) {
						Log.v("StartWork", "¡¨Ω”…œ");
						InputStream inputStream = connection.getInputStream();
					   str=new BufferedReader(new InputStreamReader(inputStream));
						String s;
						StringBuffer buffer = new StringBuffer();
						while ((s=str.readLine())!=null) {
							Log.v("≤‚ ‘", s);	
							buffer.append(s);
						}						
						callback.DataReadCallbackGetData(buffer);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		}
		
		new Thread(new DataRead()).start();
		
	}
}
