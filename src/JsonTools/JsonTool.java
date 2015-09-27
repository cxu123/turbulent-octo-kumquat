package JsonTools;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import control.Product_info;
import ResultCode.ResultCode;

public class JsonTool {
	public JsonTool() {
		
	}
	public static ResultCode getResultCode(String jsonString) {
		ResultCode code=new ResultCode();
		try {
			JSONObject source=new JSONObject(jsonString);
			JSONObject object=source.getJSONObject("result");
			code.setResultCode(object.getInt("resultCode"));
			code.setResultMessage(object.getString("resultMessage"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return code;
	}
	
	public static List<Product_info> getProduct_infos(String jsonString,String keyString) {
		List< Product_info> product_infos=new ArrayList<Product_info>();
		try {
			JSONObject source=new JSONObject(jsonString);
			JSONArray array=source.getJSONArray(keyString); //ALLPRODUCT
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject=array.getJSONObject(i);
				Product_info product_info=new Product_info();
				product_info.setName(jsonObject.getString("proname"));
				product_info.setAddres(jsonObject.getString("proaddress"));
				product_info.setPrice(jsonObject.getString("proprice"));
				product_info.setImage(jsonObject.getString("proimage"));
				product_infos.add(product_info);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return product_infos;
		
	}
	
	/*
	 public static List<Map<String, Object>> listKeyMaps(String key,
			String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				Iterator<String> iterator = jsonObject2.keys();
				while (iterator.hasNext()) {
					String json_key = iterator.next();
					Object json_value = jsonObject2.get(json_key);
					if (json_value == null) {
						json_value = "";
					}
					map.put(json_key, json_value);
				}
				list.add(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	 */
	
}
