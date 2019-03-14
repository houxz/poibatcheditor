package cn.emg.poibatcheditor.pojo;

public class TaskLinkAllModel extends TaskLinkAttachModel {
	private Long poiid;
	
	private Integer poiindex;

	private Long errorid;

	private Long errortype;

	private Integer errorpstate;

	public Long getPoiid() {
		return poiid;
	}

	public void setPoiid(Long poiid) {
		this.poiid = poiid;
	}

	public Integer getPoiindex() {
		return poiindex;
	}

	public void setPoiindex(Integer poiindex) {
		this.poiindex = poiindex;
	}

	public Long getErrorid() {
		return errorid;
	}

	public void setErrorid(Long errorid) {
		this.errorid = errorid;
	}

	public Long getErrortype() {
		return errortype;
	}

	public void setErrortype(Long errortype) {
		this.errortype = errortype;
	}

	public Integer getErrorpstate() {
		return errorpstate;
	}

	public void setErrorpstate(Integer errorpstate) {
		this.errorpstate = errorpstate;
	}
	


}