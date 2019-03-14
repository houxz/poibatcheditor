package cn.emg.poibatcheditor.pojo;

import java.util.Date;

public class POIModel {
    private Long oid;

    private Long featcode;

    private String namec;

    private Object namecTsvector;

    private Object datasource;

    private String grade;

    private Date ver;

    private Date updatetime;

    private String optype;

    private String autocheck;

    private String manualcheck;

    private Object process;

    private Boolean isdel;

    private Long projectid;

    private Long editver;

    private Object geo;

    private Integer owner;

    private String sortcode;

    private String confirm;

    private Long confirmUid;

    private Date confirmTimestamp;

    private Date autocheckTimestamp;

    private Date manualcheckTimestamp;

    private Long uid;

    private Long manualcheckUid;

    private Integer newfeatcode;

    private String newsortcode;

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Long getFeatcode() {
        return featcode;
    }

    public void setFeatcode(Long featcode) {
        this.featcode = featcode;
    }

    public String getNamec() {
        return namec;
    }

    public void setNamec(String namec) {
        this.namec = namec == null ? null : namec.trim();
    }

    public Object getNamecTsvector() {
        return namecTsvector;
    }

    public void setNamecTsvector(Object namecTsvector) {
        this.namecTsvector = namecTsvector;
    }

    public Object getDatasource() {
        return datasource;
    }

    public void setDatasource(Object datasource) {
        this.datasource = datasource;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public Date getVer() {
        return ver;
    }

    public void setVer(Date ver) {
        this.ver = ver;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getOptype() {
        return optype;
    }

    public void setOptype(String optype) {
        this.optype = optype == null ? null : optype.trim();
    }

    public String getAutocheck() {
        return autocheck;
    }

    public void setAutocheck(String autocheck) {
        this.autocheck = autocheck == null ? null : autocheck.trim();
    }

    public String getManualcheck() {
        return manualcheck;
    }

    public void setManualcheck(String manualcheck) {
        this.manualcheck = manualcheck == null ? null : manualcheck.trim();
    }

    public Object getProcess() {
        return process;
    }

    public void setProcess(Object process) {
        this.process = process;
    }

    public Boolean getIsdel() {
        return isdel;
    }

    public void setIsdel(Boolean isdel) {
        this.isdel = isdel;
    }

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    public Long getEditver() {
        return editver;
    }

    public void setEditver(Long editver) {
        this.editver = editver;
    }

    public Object getGeo() {
        return geo;
    }

    public void setGeo(Object geo) {
        this.geo = geo;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getSortcode() {
        return sortcode;
    }

    public void setSortcode(String sortcode) {
        this.sortcode = sortcode == null ? null : sortcode.trim();
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm == null ? null : confirm.trim();
    }

    public Long getConfirmUid() {
        return confirmUid;
    }

    public void setConfirmUid(Long confirmUid) {
        this.confirmUid = confirmUid;
    }

    public Date getConfirmTimestamp() {
        return confirmTimestamp;
    }

    public void setConfirmTimestamp(Date confirmTimestamp) {
        this.confirmTimestamp = confirmTimestamp;
    }

    public Date getAutocheckTimestamp() {
        return autocheckTimestamp;
    }

    public void setAutocheckTimestamp(Date autocheckTimestamp) {
        this.autocheckTimestamp = autocheckTimestamp;
    }

    public Date getManualcheckTimestamp() {
        return manualcheckTimestamp;
    }

    public void setManualcheckTimestamp(Date manualcheckTimestamp) {
        this.manualcheckTimestamp = manualcheckTimestamp;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getManualcheckUid() {
        return manualcheckUid;
    }

    public void setManualcheckUid(Long manualcheckUid) {
        this.manualcheckUid = manualcheckUid;
    }

    public Integer getNewfeatcode() {
        return newfeatcode;
    }

    public void setNewfeatcode(Integer newfeatcode) {
        this.newfeatcode = newfeatcode;
    }

    public String getNewsortcode() {
        return newsortcode;
    }

    public void setNewsortcode(String newsortcode) {
        this.newsortcode = newsortcode == null ? null : newsortcode.trim();
    }
}