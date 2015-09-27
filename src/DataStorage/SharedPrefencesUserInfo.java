package DataStorage;

import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefencesUserInfo {
	private Context context;
	public SharedPrefencesUserInfo(Context context) {
		this.context=context;
	}
	
	public Boolean SaveCity(String datacity) {
		SharedPreferences preferences=context.getSharedPreferences("City", Context.MODE_PRIVATE);
		Editor editor=preferences.edit();
		editor.putString("City", datacity);
		Boolean flag=false;
		flag=editor.commit();
		return flag;
	}
	
	public String GetCityData() {
		SharedPreferences preferences=context.getSharedPreferences("City", Context.MODE_PRIVATE);
		String city;
		city=preferences.getString("City", "÷ÿ«Ï");
		return city;
	}
	
	public boolean SaveLoginInfo(String dataName,Map<String, Object>map) {
		boolean flag=false;
		SharedPreferences preferences=context.getSharedPreferences(dataName, Context.MODE_PRIVATE);
		Editor editor=preferences.edit();
		for (Map.Entry<String, Object> entry:map.entrySet()) {
			String key=entry.getKey();
			Object values=entry.getValue();
			if (values instanceof Boolean) {
				Boolean value=(Boolean)values;
				editor.putBoolean(key, value);
			}
			if (values instanceof Integer) {
				Integer value=(Integer)values;
				editor.putInt(key, value);
			}
			if (values instanceof Boolean) {
				Float value=(Float)values;
				editor.putFloat(key, value);
			}
			if (values instanceof String) {
				String value=(String)values;
				editor.putString(key, value);
			}	
		}
		flag=editor.commit();
		return flag;
	}
	
	public void CleanData(String dataname) {
		SharedPreferences preferences=context.getSharedPreferences(dataname, Context.MODE_PRIVATE);
		Editor editor=preferences.edit();
		editor.clear();
		editor.commit();
		
	}
	
	public Map<String, ?> GetLoginInfo(String dataname) {
		Map<String, ?> map=null;
		SharedPreferences preferences=context.getSharedPreferences(dataname, Context.MODE_PRIVATE);
		map=preferences.getAll();
		return map;
	}
}
