package cn.emg.poibatcheditor.pojo;

public class ShapeModel {
	private Long id;
	
	private Long datasetid;
	
	private Integer geotype;
	
	private Integer sourcetype;
	
	private Integer featuretype;

	private Integer synchscheme;
	
	private String geostr;
	
	private Integer subfeaturetype;
	
	private Integer microfeaturetype;
	
	private String srctaskid;
	
	private String srctaskname;

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

	public Integer getGeotype() {
		return geotype;
	}

	public void setGeotype(Integer geotype) {
		this.geotype = geotype;
	}

	public Integer getSourcetype() {
		return sourcetype;
	}

	public void setSourcetype(Integer sourcetype) {
		this.sourcetype = sourcetype;
	}

	public Integer getFeaturetype() {
		return featuretype;
	}

	public void setFeaturetype(Integer featuretype) {
		this.featuretype = featuretype;
	}

	public Integer getSynchscheme() {
		return synchscheme;
	}

	public void setSynchscheme(Integer synchscheme) {
		this.synchscheme = synchscheme;
	}

	public Integer getSubfeaturetype() {
		return subfeaturetype;
	}

	public void setSubfeaturetype(Integer subfeaturetype) {
		this.subfeaturetype = subfeaturetype;
	}

	public Integer getMicrofeaturetype() {
		return microfeaturetype;
	}

	public void setMicrofeaturetype(Integer microfeaturetype) {
		this.microfeaturetype = microfeaturetype;
	}

	public String getGeostr() {
		return geostr;
	}

	public void setGeostr(String geostr) {
		this.geostr = geostr;
	}

	public String getSrctaskid() {
		return srctaskid;
	}

	public void setSrctaskid(String srctaskid) {
		this.srctaskid = srctaskid;
	}

	public String getSrctaskname() {
		return srctaskname;
	}

	public void setSrctaskname(String srctaskname) {
		this.srctaskname = srctaskname;
	}
	

}