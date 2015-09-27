package com.example.cars_helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import control.Product_info;
import db.DBSQLiteDao;
import DataStorage.SharedPrefencesUserInfo;
import android.app.Application;

public class CarsHelperApplication extends Application {
	List<Product_info> Product;
	String city_Name="重庆";
	Map<String, String>map;
	DBSQLiteDao db;
	SharedPrefencesUserInfo userInfo;
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//		SDKInitializer.initialize(this);
		Product=new ArrayList<Product_info>();
		map=new HashMap<String, String>();
		db=new DBSQLiteDao(this);
	//	IniAppData();
		IniUserInfo();
		userInfo=new SharedPrefencesUserInfo(this);
		GetCityNameFromSharedPrefences();
	}

	public void GetCityNameFromSharedPrefences() {
		city_Name=userInfo.GetCityData();
	}
	
	public void CleanCityNameToSharedPrefences() {
		userInfo.CleanData("City");
	}
	
	public Map<String, String> GetUserInfo() {
		return this.map;
	}
	
	public void IniUserInfo() {
	//	map=db.UserMap("true");
		String[] flag={"true"};
		this.map=db.GetUser(flag);	
	}
	
	public void SetUserInfo(Map<String, Object> userinfo) {
		Map< String, String> tmp=new HashMap<String, String>();
		this.map.put("username", userinfo.get("username").toString());
		this.map.put("password", userinfo.get("password").toString());
	}
	
	public void SetCityName(String city_name) {
		userInfo.SaveCity(city_name);
		this.city_Name=city_name;
	}
	
	public String GetCityName() {
		return city_Name;
	}
	
	public void SetAppData(List<Product_info> newdata) {
		// TODO Auto-generated method stub
		 
		Product=newdata;
	}
	
	public List<Product_info> GetAppData() {
		return Product;
	}
	
}
