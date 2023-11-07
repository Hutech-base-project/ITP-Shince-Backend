package com.itp_shince.model;
// Generated Oct 11, 2023, 2:33:52 PM by Hibernate Tools 4.3.6.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Orderserdetail generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "orderserdetail", catalog = "itpshince")
public class OrderSerDetail implements java.io.Serializable {

	private Integer ordSerId;
	private OrdersSer ordersser;
	private Serce serce;
	private String ordSerServiceName;
	private float ordSerServicePrice;

	public OrderSerDetail() {
	}

	public OrderSerDetail(OrdersSer ordersser, Serce serce, String ordSerServiceName, float ordSerServicePrice) {
		this.ordersser = ordersser;
		this.serce = serce;
		this.ordSerServiceName = ordSerServiceName;
		this.ordSerServicePrice = ordSerServicePrice;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ordSer_Id", unique = true, nullable = false)
	public Integer getOrdSerId() {
		return this.ordSerId;
	}

	public void setOrdSerId(Integer ordSerId) {
		this.ordSerId = ordSerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ordSer_Order_Id", nullable = false)
	public OrdersSer getOrdersser() {
		return this.ordersser;
	}

	public void setOrdersser(OrdersSer ordersser) {
		this.ordersser = ordersser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ordSer_Service_Id", nullable = false)
	public Serce getSerce() {
		return this.serce;
	}

	public void setSerce(Serce serce) {
		this.serce = serce;
	}

	@Column(name = "ordSer_Service_Name", nullable = false, length = 128)
	public String getOrdSerServiceName() {
		return this.ordSerServiceName;
	}

	public void setOrdSerServiceName(String ordSerServiceName) {
		this.ordSerServiceName = ordSerServiceName;
	}

	@Column(name = "ordSer_Service_Price", nullable = false, precision = 12, scale = 0)
	public float getOrdSerServicePrice() {
		return this.ordSerServicePrice;
	}

	public void setOrdSerServicePrice(float ordSerServicePrice) {
		this.ordSerServicePrice = ordSerServicePrice;
	}

}