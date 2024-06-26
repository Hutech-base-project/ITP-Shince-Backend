package com.itp_shince.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ordersprodetail", catalog = "itpshince")
public class OrdersProDetail implements java.io.Serializable {

	private Integer ordProId;
	private OrdersPro orderspro;
	private Product product;
	private String ordProProductName;
	private float ordProProductPrice;
	private int ordProQuantity;

	public OrdersProDetail() {
	}

	public OrdersProDetail(OrdersPro orderspro, Product product, String ordProProductName, float ordProProductPrice,
			int ordProQuantity) {
		this.orderspro = orderspro;
		this.product = product;
		this.ordProProductName = ordProProductName;
		this.ordProProductPrice = ordProProductPrice;
		this.ordProQuantity = ordProQuantity;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ordPro_Id", unique = true, nullable = false)
	public Integer getOrdProId() {
		return this.ordProId;
	}

	public void setOrdProId(Integer ordProId) {
		this.ordProId = ordProId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ordPro_Order_Id", nullable = false)
	public OrdersPro getOrderspro() {
		return this.orderspro;
	}

	public void setOrderspro(OrdersPro orderspro) {
		this.orderspro = orderspro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ordPro_Product_Id", nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "ordPro_Product_Name", nullable = false)
	public String getOrdProProductName() {
		return this.ordProProductName;
	}

	public void setOrdProProductName(String ordProProductName) {
		this.ordProProductName = ordProProductName;
	}

	@Column(name = "ordPro_Product_Price", nullable = false, precision = 12, scale = 0)
	public float getOrdProProductPrice() {
		return this.ordProProductPrice;
	}

	public void setOrdProProductPrice(float ordProProductPrice) {
		this.ordProProductPrice = ordProProductPrice;
	}

	@Column(name = "ordPro_Quantity", nullable = false)
	public int getOrdProQuantity() {
		return this.ordProQuantity;
	}

	public void setOrdProQuantity(int ordProQuantity) {
		this.ordProQuantity = ordProQuantity;
	}

}
