package com.itp_shince.dto.reponse;

import com.itp_shince.dto.AbstractDTO;

public class RoleReponse extends AbstractDTO<RoleReponse>{
	private Integer roId;
	private String roName;
	private String roDisplayName;
	
	
	public RoleReponse() {
	}
	public RoleReponse(Integer roId, String roName, String roDisplayName) {
		super();
		this.roId = roId;
		this.roName = roName;
		this.roDisplayName = roDisplayName;
	}
	public Integer getRoId() {
		return roId;
	}
	public void setRoId(Integer roId) {
		this.roId = roId;
	}
	public String getRoName() {
		return roName;
	}
	public void setRoName(String roName) {
		this.roName = roName;
	}
	public String getRoDisplayName() {
		return roDisplayName;
	}
	public void setRoDisplayName(String roDisplayName) {
		this.roDisplayName = roDisplayName;
	}
	
	
}
