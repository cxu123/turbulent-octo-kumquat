package HttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import JsonTools.JsonTool;
import ResultCode.ResultCode;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class HttpTools {
	private Context context;
	private ProgressDialog dialog;
	public HttpTools(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		dialog=new ProgressDialog(context);
		dialog.setTitle("提示");
		dialog.setMessage("正在获取网络数据");
	}
	public void sendDataThread(final String urlPath,final Map<String, Object> params, final DownLoadCallBack callback) {
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				ResultCode resultCode=(ResultCode) msg.obj;
			//    System.out.print(resultCode);
				callback.loadContent(resultCode);
				if (msg.what==1) {
					dialog.dismiss();
				}
				if (msg.what==0) {
					dialog.dismiss();
//					Toast.makeText(context, "网络连接失败！请检查网络！",Toast.LENGTH_LONG).show();
				}
			}
		};
		class MyThread implements Runnable{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpClient httpClient=new DefaultHttpClient();
				HttpPost httpPost=new HttpPost(urlPath);
				String result="";
				List<BasicNameValuePair>paramete=new ArrayList<BasicNameValuePair>();
				try {
					if (params!=null&&!params.isEmpty()) {
						for (Map.Entry<String, Object> entry: params.entrySet()) {
							String username=entry.getKey();
							String value=entry.getValue().toString();
							BasicNameValuePair valuePair=new BasicNameValuePair(username, value);
							paramete.add(valuePair);
						}
					}
					// 纯文本表单，不包含文件
					UrlEncodedFormEntity encodedFormEntity=new UrlEncodedFormEntity(paramete,"utf-8");
					httpPost.setEntity(encodedFormEntity);
					HttpResponse httpResponse=httpClient.execute(httpPost);
					if (httpResponse.getStatusLine().getStatusCode()==200) {
						result=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
						ResultCode Code=new ResultCode();
						Code=JsonTool.getResultCode(result);
						Message message=Message.obtain();
						message.obj=Code;
						message.what=1;
						handler.sendMessage(message);
					}
					else {
						Message message=Message.obtain();
						message.obj=null;
						message.what=0;
						handler.sendMessage(message);
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					httpClient.getConnectionManager().shutdown();
				}
			}
		}
	new Thread(new MyThread()).start();
	dialog.show();
	}
	
	public interface DownLoadCallBack {
		public void loadContent(ResultCode result);
	}
	
	
	/**
	 * 提交数据方法
	 * @param urlPath 服务器路径
	 * @param map	上传表单
	 * @param encode
	 * @return
	 */
	public static String sendPostMethod(String urlPath,Map<String, Object> params,String encode,Context context) {
		HttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(urlPath);
		String result="";
		List<BasicNameValuePair>paramete=new ArrayList<BasicNameValuePair>();
		try {
			if (params!=null&&!params.isEmpty()) {
				for (Map.Entry<String, Object> entry: params.entrySet()) {
					String username=entry.getKey();
					String value=entry.getValue().toString();
					BasicNameValuePair valuePair=new BasicNameValuePair(username, value);
					paramete.add(valuePair);
				}
			}
			// 纯文本表单，不包含文件
			UrlEncodedFormEntity encodedFormEntity=new UrlEncodedFormEntity(paramete,encode);
			httpPost.setEntity(encodedFormEntity);
			HttpResponse httpResponse=httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode()==200) {
				result=EntityUtils.toString(httpResponse.getEntity(),encode);
			}else {
				
				//	System.out.print("232522");
				//	Toast.makeText(context, "网络出现问题请检查网络", Toast.LENGTH_LONG).show();
				result="false";
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
