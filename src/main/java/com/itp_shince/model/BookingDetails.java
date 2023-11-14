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
@Table(name = "bookingdetails", catalog = "itpshince")
public class BookingDetails implements java.io.Serializable {

	private Integer bodId;
	private Booking booking;
	private Serce serce;
	private String bodServiceName;
	private float bodServicePrice;

	public BookingDetails() {
	}

	public BookingDetails(Booking booking, Serce serce, String bodServiceName, float bodServicePrice) {
		this.booking = booking;
		this.serce = serce;
		this.bodServiceName = bodServiceName;
		this.bodServicePrice = bodServicePrice;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "bod_Id", unique = true, nullable = false)
	public Integer getBodId() {
		return this.bodId;
	}

	public void setBodId(Integer bodId) {
		this.bodId = bodId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bod_Order_Id", nullable = false)
	public Booking getBooking() {
		return this.booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bod_Service_Id", nullable = false)
	public Serce getSerce() {
		return this.serce;
	}

	public void setSerce(Serce serce) {
		this.serce = serce;
	}

	@Column(name = "bod_Service_Name", nullable = false, length = 128)
	public String getBodServiceName() {
		return this.bodServiceName;
	}

	public void setBodServiceName(String bodServiceName) {
		this.bodServiceName = bodServiceName;
	}

	@Column(name = "bod_Service_Price", nullable = false, precision = 12, scale = 0)
	public float getBodServicePrice() {
		return this.bodServicePrice;
	}

	public void setBodServicePrice(float bodServicePrice) {
		this.bodServicePrice = bodServicePrice;
	}

}
