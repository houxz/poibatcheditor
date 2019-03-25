package cn.emg.poibatcheditor.common;

public enum POIMainAttrnameEnum {
	/**
	 * "unkown", "未知属性"
	 */
	unkown("unkown", "未知属性"),
	/**
	 * "oid", "poi永久id"
	 */
	oid("oid", "poi永久id"),
	/**
	 * "featcode", "poi对象类型代码"
	 */
	featcode("featcode", "poi对象类型代码"),
	/**
	 * "namec", "中文正式名称"
	 */
	namec("namec", "中文正式名称"),
	/**
	 * "owner", "所属行政区划代码"
	 */
	owner("owner", "所属行政区划代码"),
	/**
	 * "sortcode", "poi系列代码"
	 */
	sortcode("sortcode", "poi系列代码"),
	/**
	 * "newfeatcode", "新poi对象类型代码"
	 */
	newfeatcode("newfeatcode", "新poi对象类型代码"),
	/**
	 * "newsortcode", "新poi系列代码"
	 */
	newsortcode("newsortcode", "新poi系列代码");

	private String value;
	private String des;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	private POIMainAttrnameEnum(String value, String des) {
		this.value = value;
		this.des = des;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
