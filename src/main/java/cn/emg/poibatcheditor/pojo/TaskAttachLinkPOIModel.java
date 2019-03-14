package cn.emg.poibatcheditor.pojo;

public class TaskAttachLinkPOIModel {
	private Long id;
	
	private Long taskid;
	
	private Long attachid;

	private Long featureid;

	private Integer layerid;
	
	private Integer index;
	
	private String updatetime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskid() {
		return taskid;
	}

	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}

	public Long getAttachid() {
		return attachid;
	}

	public void setAttachid(Long attachid) {
		this.attachid = attachid;
	}

	public Long getFeatureid() {
		return featureid;
	}

	public void setFeatureid(Long featureid) {
		this.featureid = featureid;
	}

	public Integer getLayerid() {
		return layerid;
	}

	public void setLayerid(Integer layerid) {
		this.layerid = layerid;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
	

}