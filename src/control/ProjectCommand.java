package control;

import java.util.List;

import android.os.Environment;

public class ProjectCommand {
//	public final static String PRODUCT_PATH="http://192.168.0.100/test_work/servlet/ProductActionForAndroid";
//	public final static String LOGIN_PATH = "http://192.168.0.100/test_work/servlet/LoginActionForAndroid";
//	public final static String Register_Path="http://192.168.0.100/test_work/servlet/RegisterActionForAndroid";
	/*
	public final static String PRODUCT_PATH="http://192.168.0.131/test_work/servlet/ProductActionForAndroid";
	public final static String LOGIN_PATH = "http://192.168.0.131/test_work/servlet/LoginActionForAndroid";
	public final static String Register_Path="http://192.168.0.131/test_work/servlet/RegisterActionForAndroid";
	public final static String Get_Image_From_Service="http://192.168.0.131/test_work/UpLoad/";
	 */
	
	
	public final static String PRODUCT_PATH="http://192.168.0.100/test_work/servlet/ProductActionForAndroid";
	public final static String LOGIN_PATH = "http://192.168.0.100/test_work/servlet/LoginActionForAndroid";
	public final static String Register_Path="http://192.168.0.100/test_work/servlet/RegisterActionForAndroid";
	public final static String Get_Image_From_Service="http://192.168.0.100/test_work/UpLoad/";
	//商品简洁
	public final static String Get_ProductText_From_Service="http://192.168.0.100/test_work/ProductDetail/ProductText/";
	//消费描述
	public final static String Get_ProductConsumerDescription_From_Service="http://192.168.0.100/test_work/ProductDetail/ProductConsumerDescription/";
	//产品评价
	public final static String Get_ProductEvaluation_From_Service="http://192.168.0.100/test_work/ProductDetail/ProductEvaluation/";
	public final  static String Image_Path="";
	public final static String PRODUCT_INFO_DATA_PATH="http://192.168.0.100/test_work/Info/";
	public final static String DownLoad_Image_Path=Environment.getExternalStorageDirectory()+"/CarsHelp/";
	public final static String DownLoad_Image_TMP_Path=Environment.getExternalStorageDirectory()+"/CarsHelp/"+"temp/";
	public final static String Head_Image="http://192.168.0.100/test_work/UpLoad/";
	public final static String[] Head={"Had_a.png","Had_b.png","Had_c.png"};
	public final static String Product_Near="NearProduct";
	public final static String Product_classify="ProductClass";
	public final static int ListImageWidth=150;//sWidth, int sHeight
	public final static int ListImageHeight=140;
	public final static int HeadImageWidth=480;		
	public final static int HeadImageHeight=196;
	public final static int ProductPICwidth=480;
	public final static int ProductPICheight=350;
}
