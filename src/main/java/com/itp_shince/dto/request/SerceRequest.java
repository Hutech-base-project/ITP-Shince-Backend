package com.itp_shince.dto.request;

import java.util.List;

import com.itp_shince.dto.AbstractDTO;

public class SerceRequest extends AbstractDTO<SerceRequest>{
	private String seId;
	private String seName;
	private Float sePrice;
	private String seDescription;
	private String seNote;
	private String seImage;
	private Boolean seTurnOn;
	private Boolean isDelete;
	private List<String> idImgChild ;
	
	
	
	public SerceRequest() {
	}

	public SerceRequest(String seId, String seName, Float sePrice, String seDescription, String seNote, String seImage,
			Boolean seTurnOn, Boolean isDelete) {
		super();
		this.seId = seId;
		this.seName = seName;
		this.sePrice = sePrice;
		this.seDescription = seDescription;
		this.seNote = seNote;
		this.seImage = seImage;
		this.seTurnOn = seTurnOn;
		this.isDelete = isDelete;
	}

	public SerceRequest(String seId, String seName, Float sePrice, String seDescription, String seNote, String seImage,
			Boolean seTurnOn, Boolean isDelete, List<String> idImgChild) {
		super();
		this.seId = seId;
		this.seName = seName;
		this.sePrice = sePrice;
		this.seDescription = seDescription;
		this.seNote = seNote;
		this.seImage = seImage;
		this.seTurnOn = seTurnOn;
		this.isDelete = isDelete;
		this.idImgChild = idImgChild;
	}

	public String getSeId() {
		return seId;
	}

	public void setSeId(String seId) {
		this.seId = seId;
	}

	public String getSeName() {
		return seName;
	}

	public void setSeName(String seName) {
		this.seName = seName;
	}

	public Float getSePrice() {
		return sePrice;
	}

	public void setSePrice(Float sePrice) {
		this.sePrice = sePrice;
	}

	public String getSeDescription() {
		return seDescription;
	}

	public void setSeDescription(String seDescription) {
		this.seDescription = seDescription;
	}

	public String getSeNote() {
		return seNote;
	}

	public void setSeNote(String seNote) {
		this.seNote = seNote;
	}

	public String getSeImage() {
		return seImage;
	}

	public void setSeImage(String seImage) {
		this.seImage = seImage;
	}

	public Boolean getSeTurnOn() {
		return seTurnOn;
	}

	public void setSeTurnOn(Boolean seTurnOn) {
		this.seTurnOn = seTurnOn;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public List<String> getIdImgChild() {
		return idImgChild;
	}

	public void setIdImgChild(List<String> idImgChild) {
		this.idImgChild = idImgChild;
	}	
}
