package Thread;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import control.Product_info;
import control.ProjectCommand;
import JsonTools.JsonTool;
import android.util.Log;

public class GetProductFromKEYWORD {
	private String keywork;
	private String result;
	private String action_flag;
	private List<Product_info> products = new ArrayList<Product_info>();

	public GetProductFromKEYWORD(String keyword, String action_flag) {
		this.keywork = keyword;
		this.action_flag = action_flag;
	}

	public interface GetProductFromKEYWORDCallback {
		public void GetProductFromKEYWORDCallbackGetData(List<Product_info> data);
	}

	public void Dowork(final GetProductFromKEYWORDCallback callback) {

		class KEYWORD implements Runnable {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(ProjectCommand.PRODUCT_PATH);
				// httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				// 5000);
				List<BasicNameValuePair> paramete = new ArrayList<BasicNameValuePair>();
				BasicNameValuePair valuePair_flag = new BasicNameValuePair(
						"action_flag", action_flag);
				BasicNameValuePair valuePair = new BasicNameValuePair(
						"key_word", keywork);
				paramete.add(valuePair_flag);
				paramete.add(valuePair);
				try {
					UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(
							paramete, "utf-8");
					httpPost.setEntity(encodedFormEntity);
					HttpResponse httpResponse = httpClient.execute(httpPost);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						result = EntityUtils.toString(httpResponse.getEntity(),
								"utf-8");
						if (result != null && !result.equals("")) {
							if (action_flag
									.equals(ProjectCommand.Product_classify)) {
								products = JsonTool.getProduct_infos(result,
										ProjectCommand.Product_classify);
								callback.GetProductFromKEYWORDCallbackGetData(products);
							}else if (action_flag.equals(ProjectCommand.Product_Near)) {
								products = JsonTool.getProduct_infos(result,
										ProjectCommand.Product_Near);
								callback.GetProductFromKEYWORDCallbackGetData(products);
							}else {
								products = JsonTool.getProduct_infos(result,
										"ALLPRODUCT");
								callback.GetProductFromKEYWORDCallbackGetData(products);
							}
							
						}
					} else {
					
						callback.GetProductFromKEYWORDCallbackGetData(products);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		new Thread(new KEYWORD()).start();
	}
}
