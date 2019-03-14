package cn.emg.poibatcheditor.pojo;

import java.util.HashSet;
import java.util.Set;

import cn.emg.poibatcheditor.commonjar.PstateEnum;

public class AttachModel {
	private Long id;
	
	private Long datasetid;
	
	private Long shapeid;

	private Integer type;
	
	private String time;
	
	private Double x;
	
	private Double y;
	
	private String location;

	private Integer direction;
	
	private String timestamp;
	
	private Integer roleid;
	
	private String ip;
	
	private String path;
	
	private String name;
	
	private Integer pstate;
	
	private Set<POIDoIndexWithError> pois = new HashSet<POIDoIndexWithError>();
	
	public Boolean isEditing() {
		return pstate.equals(PstateEnum.EDITING.getValue());
	}
	public Boolean needEdit() {
		return pstate.equals(PstateEnum.UNDO.getValue()) ||
				pstate.equals(PstateEnum.EDITING.getValue());
	}
	
	public Boolean isChecking() {
		return pstate.equals(PstateEnum.CHECKING.getValue());
	}
	
	public Boolean needCheck() {
		return pstate.equals(PstateEnum.EDITED.getValue()) ||
				pstate.equals(PstateEnum.CHECKING.getValue());
	}
	
	public Boolean isModifying() {
		return pstate.equals(PstateEnum.MODIFYING.getValue());
	}
	
	public Boolean needModify() {
		return pstate.equals(PstateEnum.CHECKED.getValue()) ||
				pstate.equals(PstateEnum.MODIFYING.getValue());
	}
	
	/**
	 * 添加POI
	 * @param poi
	 */
	public void addPoi(POIDoIndexWithError poi) {
		this.pois.add(poi);
	}
	
	/**
	 * 给当前资料下制定poi添加错误
	 * @param poiID
	 * @param error
	 * @return 是否成功
	 */
	public void addError(Long poiID, ErrorModel error) {
		try {
			for (POIDoIndexWithError poi : pois) {
				if (poi.getId().equals(poiID)) {
					poi.addError(error);
					break;
				}
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * 判断当前资料中是否有未修改的错误
	 * @return 
	 * 是：true
	 * 否：false
	 */
	public Boolean hasUnModifyError() {
		try {
			for (POIDoIndexWithError poi : pois) {
				if (poi.hasUnModifyError())
					return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	/**
	 * 判断当前资料中是否有未校正的错误
	 * @return 
	 * 是：true
	 * 否：false
	 */
	public Boolean hasUnCheckError() {
		try {
			for (POIDoIndexWithError poi : pois) {
				if (poi.hasUnCheckError())
					return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDatasetid() {
		return datasetid;
	}

	public void setDatasetid(Long datasetid) {
		this.datasetid = datasetid;
	}

	public Long getShapeid() {
		return shapeid;
	}

	public void setShapeid(Long shapeid) {
		this.shapeid = shapeid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Integer getPstate() {
		return pstate;
	}

	public void setPstate(Integer pstate) {
		this.pstate = pstate;
	}

	public Set<POIDoIndexWithError> getPois() {
		return pois;
	}

	public void setPois(Set<POIDoIndexWithError> poiids) {
		this.pois = poiids;
	}
	

}