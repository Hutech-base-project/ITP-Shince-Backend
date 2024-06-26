package com.itp_shince.dto.reponse;

import java.util.List;

import com.itp_shince.dto.AbstractDTO;


public class UsersReponse extends AbstractDTO<UsersReponse>{
	private String usId;
	private String usUserName;
	private String usPassword;
	private String usDob;
	private String usAddress;
	private String usPhoneNo;
	private String usEmailNo;
	private String usImage;
	private String usNote;
	private Boolean isBlock;
	private Boolean isAdmin;
	private Boolean isDelete;
	private List<String> listRole;
	
	
	public UsersReponse() {
		
	}

	public UsersReponse(String usId, String usUserName, String usPassword, String usDob, String usAddress,
			String usPhoneNo, String usEmailNo, String usImage, String usNote, Boolean isBlock, Boolean isAdmin,
			Boolean isDelete, List<String> listRole) {
		super();
		this.usId = usId;
		this.usUserName = usUserName;
		this.usPassword = usPassword;
		this.usDob = usDob;
		this.usAddress = usAddress;
		this.usPhoneNo = usPhoneNo;
		this.usEmailNo = usEmailNo;
		this.usImage = usImage;
		this.usNote = usNote;
		this.isBlock = isBlock;
		this.isAdmin = isAdmin;
		this.isDelete = isDelete;
		this.listRole = listRole;
	}


	public String getUsId() {
		return usId;
	}


	public void setUsId(String usId) {
		this.usId = usId;
	}


	public String getUsUserName() {
		return usUserName;
	}


	public void setUsUserName(String usUserName) {
		this.usUserName = usUserName;
	}


	public String getUsPassword() {
		return usPassword;
	}


	public void setUsPassword(String usPassword) {
		this.usPassword = usPassword;
	}


	public String getUsDob() {
		return usDob;
	}


	public void setUsDob(String usDob) {
		this.usDob = usDob;
	}


	public String getUsAddress() {
		return usAddress;
	}


	public void setUsAddress(String usAddress) {
		this.usAddress = usAddress;
	}


	public String getUsPhoneNo() {
		return usPhoneNo;
	}


	public void setUsPhoneNo(String usPhoneNo) {
		this.usPhoneNo = usPhoneNo;
	}


	public String getUsEmailNo() {
		return usEmailNo;
	}


	public void setUsEmailNo(String usEmailNo) {
		this.usEmailNo = usEmailNo;
	}


	public String getUsImage() {
		return usImage;
	}


	public void setUsImage(String usImage) {
		this.usImage = usImage;
	}


	public String getUsNote() {
		return usNote;
	}


	public void setUsNote(String usNote) {
		this.usNote = usNote;
	}


	public Boolean getIsBlock() {
		return isBlock;
	}


	public void setIsBlock(Boolean isBlock) {
		this.isBlock = isBlock;
	}


	public Boolean getIsAdmin() {
		return isAdmin;
	}


	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public Boolean getIsDelete() {
		return isDelete;
	}


	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}


	public List<String> getListRole() {
		return listRole;
	}


	public void setListRole(List<String> listRole) {
		this.listRole = listRole;
	}
	
}
