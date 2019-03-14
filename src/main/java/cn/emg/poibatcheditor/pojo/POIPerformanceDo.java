package cn.emg.poibatcheditor.pojo;

import java.io.Serializable;

import cn.emg.poibatcheditor.commonjar.POIChangeTypeEnum;

public class POIPerformanceDo implements Serializable {
	private static final long serialVersionUID = -5167871826401908801L;
	
	private Long id;
	private Long oid;
	private POIChangeTypeEnum changetype;
	private Integer owner;
	private Long editver;
	private String ver;
	private String updatetime;
	private Long uid;
	private Long sourceid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public POIChangeTypeEnum getChangetype() {
		return changetype;
	}
	public void setChangetype(POIChangeTypeEnum changetype) {
		this.changetype = changetype;
	}
	public Integer getOwner() {
		return owner;
	}
	public void setOwner(Integer owner) {
		this.owner = owner;
	}
	public Long getEditver() {
		return editver;
	}
	public void setEditver(Long editver) {
		this.editver = editver;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getSourceid() {
		return sourceid;
	}
	public void setSourceid(Long sourceid) {
		this.sourceid = sourceid;
	}
	
	
}
