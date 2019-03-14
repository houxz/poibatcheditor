package cn.emg.poibatcheditor.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class ErrorModel {
    private Long id;

    private Long taskid;

    private Long blockid;

    private Long featureid;

    private Integer layerid;

    private Long editver;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    private String fieldname;

    private String editvalue;

    private String checkvalue;

    private String qid;

    private Long errortype;

    private Integer level;

    private Integer modifystate;

    private Integer errorstate;

    private String errorremark;

    private Integer checkroleid;

    private Integer changeroleid;

    private String md5;

    private Long batchid;

    private Integer isrelated;

    private String sbound;

    private Object bound;
    
    private Integer pstate;

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

    public Long getBlockid() {
        return blockid;
    }

    public void setBlockid(Long blockid) {
        this.blockid = blockid;
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

    public Long getEditver() {
        return editver;
    }

    public void setEditver(Long editver) {
        this.editver = editver;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getEditvalue() {
        return editvalue;
    }

    public void setEditvalue(String editvalue) {
        this.editvalue = editvalue == null ? null : editvalue.trim();
    }

    public String getCheckvalue() {
        return checkvalue;
    }

    public void setCheckvalue(String checkvalue) {
        this.checkvalue = checkvalue == null ? null : checkvalue.trim();
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid == null ? null : qid.trim();
    }

    public Long getErrortype() {
        return errortype;
    }

    public void setErrortype(Long errortype) {
        this.errortype = errortype;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getModifystate() {
        return modifystate;
    }

    public void setModifystate(Integer modifystate) {
        this.modifystate = modifystate;
    }

    public Integer getErrorstate() {
        return errorstate;
    }

    public void setErrorstate(Integer errorstate) {
        this.errorstate = errorstate;
    }

    public String getErrorremark() {
        return errorremark;
    }

    public void setErrorremark(String errorremark) {
        this.errorremark = errorremark == null ? null : errorremark.trim();
    }

    public Integer getCheckroleid() {
        return checkroleid;
    }

    public void setCheckroleid(Integer checkroleid) {
        this.checkroleid = checkroleid;
    }

    public Integer getChangeroleid() {
        return changeroleid;
    }

    public void setChangeroleid(Integer changeroleid) {
        this.changeroleid = changeroleid;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    public Long getBatchid() {
        return batchid;
    }

    public void setBatchid(Long batchid) {
        this.batchid = batchid;
    }

    public Integer getIsrelated() {
        return isrelated;
    }

    public void setIsrelated(Integer isrelated) {
        this.isrelated = isrelated;
    }

    public String getSbound() {
        return sbound;
    }

    public void setSbound(String sbound) {
        this.sbound = sbound == null ? null : sbound.trim();
    }

    public Object getBound() {
        return bound;
    }

    public void setBound(Object bound) {
        this.bound = bound;
    }

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public Integer getPstate() {
		return pstate;
	}

	public void setPstate(Integer pstate) {
		this.pstate = pstate;
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ErrorModel) {
			ErrorModel p = (ErrorModel) obj;
			return this.id.equals(p.getId());
		} else {
			return false;
		}
	}
}