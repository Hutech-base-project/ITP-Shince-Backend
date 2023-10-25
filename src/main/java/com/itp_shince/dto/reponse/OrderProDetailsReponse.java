package com.itp_shince.dto.reponse;



public class OrderProDetailsReponse {
	private String ProProductName;
	private float ProProductPrice;
	private int ProQuantity;
	private String ProductId;
	
	public OrderProDetailsReponse() {
		super();
	}

	public OrderProDetailsReponse(String proProductName, float proProductPrice, int proQuantity, String productId) {
		super();
		ProProductName = proProductName;
		ProProductPrice = proProductPrice;
		ProQuantity = proQuantity;
		ProductId = productId;
	}

	public String getProProductName() {
		return ProProductName;
	}

	public void setProProductName(String proProductName) {
		ProProductName = proProductName;
	}

	public float getProProductPrice() {
		return ProProductPrice;
	}

	public void setProProductPrice(float proProductPrice) {
		ProProductPrice = proProductPrice;
	}

	public int getProQuantity() {
		return ProQuantity;
	}

	public void setProQuantity(int proQuantity) {
		ProQuantity = proQuantity;
	}

	public String getProductId() {
		return ProductId;
	}

	public void setProductId(String productId) {
		ProductId = productId;
	}
}
