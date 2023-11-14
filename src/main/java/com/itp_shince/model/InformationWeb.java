package com.itp_shince.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "information_web", catalog = "itpshince")
public class InformationWeb implements java.io.Serializable {

	private Integer inwId;
	private String inwLinkLogo;
	private String inwLinkLogoIcon;
	private String inwImgSlider1;
	private String inwImgSlider2;
	private String inwImgSlider3;
	private String inwImgBannerProduct;
	private String inwImgBannerService;
	private String inwImgDealOfDay;
	private String inwTitleDealOfDay;
	private String inwContentDealOfDay;
	private String inwPriceDealOfDay;
	private int inwBusinessCertificateNumber;
	private Date inwLicenseDate;
	private String inwTelephone;
	private String inwGmail;
	private String inwFacebook;
	private String inwTwitter;
	private String inwLinkBusinessCertificateNumber;
	private String inwLinkDmca;
	private Date createdAt;
	private Date updatedAt;

	public InformationWeb() {
	}

	public InformationWeb(String inwLinkLogo, String inwLinkLogoIcon, String inwImgSlider1, String inwImgSlider2,
			String inwImgSlider3, String inwImgBannerProduct, String inwImgBannerService, String inwImgDealOfDay,
			String inwTitleDealOfDay, String inwContentDealOfDay, String inwPriceDealOfDay,
			int inwBusinessCertificateNumber, Date inwLicenseDate, String inwTelephone, String inwGmail) {
		this.inwLinkLogo = inwLinkLogo;
		this.inwLinkLogoIcon = inwLinkLogoIcon;
		this.inwImgSlider1 = inwImgSlider1;
		this.inwImgSlider2 = inwImgSlider2;
		this.inwImgSlider3 = inwImgSlider3;
		this.inwImgBannerProduct = inwImgBannerProduct;
		this.inwImgBannerService = inwImgBannerService;
		this.inwImgDealOfDay = inwImgDealOfDay;
		this.inwTitleDealOfDay = inwTitleDealOfDay;
		this.inwContentDealOfDay = inwContentDealOfDay;
		this.inwPriceDealOfDay = inwPriceDealOfDay;
		this.inwBusinessCertificateNumber = inwBusinessCertificateNumber;
		this.inwLicenseDate = inwLicenseDate;
		this.inwTelephone = inwTelephone;
		this.inwGmail = inwGmail;
	}

	public InformationWeb(String inwLinkLogo, String inwLinkLogoIcon, String inwImgSlider1, String inwImgSlider2,
			String inwImgSlider3, String inwImgBannerProduct, String inwImgBannerService, String inwImgDealOfDay,
			String inwTitleDealOfDay, String inwContentDealOfDay, String inwPriceDealOfDay,
			int inwBusinessCertificateNumber, Date inwLicenseDate, String inwTelephone, String inwGmail,
			String inwFacebook, String inwTwitter, String inwLinkBusinessCertificateNumber, String inwLinkDmca,
			Date createdAt, Date updatedAt) {
		this.inwLinkLogo = inwLinkLogo;
		this.inwLinkLogoIcon = inwLinkLogoIcon;
		this.inwImgSlider1 = inwImgSlider1;
		this.inwImgSlider2 = inwImgSlider2;
		this.inwImgSlider3 = inwImgSlider3;
		this.inwImgBannerProduct = inwImgBannerProduct;
		this.inwImgBannerService = inwImgBannerService;
		this.inwImgDealOfDay = inwImgDealOfDay;
		this.inwTitleDealOfDay = inwTitleDealOfDay;
		this.inwContentDealOfDay = inwContentDealOfDay;
		this.inwPriceDealOfDay = inwPriceDealOfDay;
		this.inwBusinessCertificateNumber = inwBusinessCertificateNumber;
		this.inwLicenseDate = inwLicenseDate;
		this.inwTelephone = inwTelephone;
		this.inwGmail = inwGmail;
		this.inwFacebook = inwFacebook;
		this.inwTwitter = inwTwitter;
		this.inwLinkBusinessCertificateNumber = inwLinkBusinessCertificateNumber;
		this.inwLinkDmca = inwLinkDmca;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "inw_Id", unique = true, nullable = false)
	public Integer getInwId() {
		return this.inwId;
	}

	public void setInwId(Integer inwId) {
		this.inwId = inwId;
	}

	@Column(name = "inw_Link_Logo", nullable = false, length = 128)
	public String getInwLinkLogo() {
		return this.inwLinkLogo;
	}

	public void setInwLinkLogo(String inwLinkLogo) {
		this.inwLinkLogo = inwLinkLogo;
	}

	@Column(name = "inw_Link_Logo_Icon", nullable = false, length = 128)
	public String getInwLinkLogoIcon() {
		return this.inwLinkLogoIcon;
	}

	public void setInwLinkLogoIcon(String inwLinkLogoIcon) {
		this.inwLinkLogoIcon = inwLinkLogoIcon;
	}

	@Column(name = "inw_Img_Slider_1", nullable = false, length = 65535)
	public String getInwImgSlider1() {
		return this.inwImgSlider1;
	}

	public void setInwImgSlider1(String inwImgSlider1) {
		this.inwImgSlider1 = inwImgSlider1;
	}

	@Column(name = "inw_Img_Slider_2", nullable = false, length = 65535)
	public String getInwImgSlider2() {
		return this.inwImgSlider2;
	}

	public void setInwImgSlider2(String inwImgSlider2) {
		this.inwImgSlider2 = inwImgSlider2;
	}

	@Column(name = "inw_Img_Slider_3", nullable = false, length = 65535)
	public String getInwImgSlider3() {
		return this.inwImgSlider3;
	}

	public void setInwImgSlider3(String inwImgSlider3) {
		this.inwImgSlider3 = inwImgSlider3;
	}

	@Column(name = "inw_Img_Banner_product", nullable = false, length = 65535)
	public String getInwImgBannerProduct() {
		return this.inwImgBannerProduct;
	}

	public void setInwImgBannerProduct(String inwImgBannerProduct) {
		this.inwImgBannerProduct = inwImgBannerProduct;
	}

	@Column(name = "inw_Img_Banner_service", nullable = false, length = 65535)
	public String getInwImgBannerService() {
		return this.inwImgBannerService;
	}

	public void setInwImgBannerService(String inwImgBannerService) {
		this.inwImgBannerService = inwImgBannerService;
	}

	@Column(name = "inw_Img_Deal_Of_Day", nullable = false, length = 65535)
	public String getInwImgDealOfDay() {
		return this.inwImgDealOfDay;
	}

	public void setInwImgDealOfDay(String inwImgDealOfDay) {
		this.inwImgDealOfDay = inwImgDealOfDay;
	}

	@Column(name = "inw_Title_Deal_Of_Day", nullable = false, length = 128)
	public String getInwTitleDealOfDay() {
		return this.inwTitleDealOfDay;
	}

	public void setInwTitleDealOfDay(String inwTitleDealOfDay) {
		this.inwTitleDealOfDay = inwTitleDealOfDay;
	}

	@Column(name = "inw_Content_Deal_Of_Day", nullable = false, length = 128)
	public String getInwContentDealOfDay() {
		return this.inwContentDealOfDay;
	}

	public void setInwContentDealOfDay(String inwContentDealOfDay) {
		this.inwContentDealOfDay = inwContentDealOfDay;
	}

	@Column(name = "inw_Price_Deal_Of_Day", nullable = false, length = 128)
	public String getInwPriceDealOfDay() {
		return this.inwPriceDealOfDay;
	}

	public void setInwPriceDealOfDay(String inwPriceDealOfDay) {
		this.inwPriceDealOfDay = inwPriceDealOfDay;
	}

	@Column(name = "inw_Business_Certificate_Number", nullable = false)
	public int getInwBusinessCertificateNumber() {
		return this.inwBusinessCertificateNumber;
	}

	public void setInwBusinessCertificateNumber(int inwBusinessCertificateNumber) {
		this.inwBusinessCertificateNumber = inwBusinessCertificateNumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "inw_License_Date", nullable = false, length = 10)
	public Date getInwLicenseDate() {
		return this.inwLicenseDate;
	}

	public void setInwLicenseDate(Date inwLicenseDate) {
		this.inwLicenseDate = inwLicenseDate;
	}

	@Column(name = "inw_Telephone", nullable = false, length = 15)
	public String getInwTelephone() {
		return this.inwTelephone;
	}

	public void setInwTelephone(String inwTelephone) {
		this.inwTelephone = inwTelephone;
	}

	@Column(name = "inw_Gmail", nullable = false, length = 150)
	public String getInwGmail() {
		return this.inwGmail;
	}

	public void setInwGmail(String inwGmail) {
		this.inwGmail = inwGmail;
	}

	@Column(name = "inw_Facebook", length = 150)
	public String getInwFacebook() {
		return this.inwFacebook;
	}

	public void setInwFacebook(String inwFacebook) {
		this.inwFacebook = inwFacebook;
	}

	@Column(name = "inw_Twitter", length = 150)
	public String getInwTwitter() {
		return this.inwTwitter;
	}

	public void setInwTwitter(String inwTwitter) {
		this.inwTwitter = inwTwitter;
	}

	@Column(name = "inw_Link_Business_Certificate_Number", length = 150)
	public String getInwLinkBusinessCertificateNumber() {
		return this.inwLinkBusinessCertificateNumber;
	}

	public void setInwLinkBusinessCertificateNumber(String inwLinkBusinessCertificateNumber) {
		this.inwLinkBusinessCertificateNumber = inwLinkBusinessCertificateNumber;
	}

	@Column(name = "inw_Link_DMCA", length = 150)
	public String getInwLinkDmca() {
		return this.inwLinkDmca;
	}

	public void setInwLinkDmca(String inwLinkDmca) {
		this.inwLinkDmca = inwLinkDmca;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_At", length = 26)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_At", length = 26)
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
