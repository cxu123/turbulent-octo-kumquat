package control;

import java.io.Serializable;

public class Product_info implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String Product_name;
	String Product_address;
	String Product_price ;
	String product_image_path;
	public Product_info() {
		
	}
	public Product_info(String productName,String productAddress,String productPrice,String productImagePath) {
		this.Product_name=productName;
		this.Product_address=productAddress;
		this.Product_price=productPrice;
		this.product_image_path=productImagePath;
	}
	
	public void setName(String productName) {
		this.Product_name=productName;
	}
	
	public void setAddres(String productAddress) {
		this.Product_address=productAddress;
	}
	
	public void setPrice(String productPrice) {
		this.Product_price=productPrice;
	}
	
	public void setImage(String productImagePath) {
		this.product_image_path=productImagePath;
	}
	
	public String getName() {
		return this.Product_name;
	}
	
	public String getAddress() {
		return this.Product_address;
	}
	
	public String getPrice() {
		return this.Product_price;
	}
	
	public String getImagePath() {
		return this.product_image_path;
	}
	
}
