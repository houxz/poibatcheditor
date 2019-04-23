package cn.emg.poibatcheditor.common;

public enum FeatcodeEnum {
	/**
	 * -1, "未知"
	 */
	UNKOWN(-1, "未知"),
	/**
	 * 1010201, "全国"
	 */
	QUANGUO(1010201, "全国"),
	/**
	 * 1010202, "省"
	 */
	SHENG(1010202, "省"),
	/**
	 * 1010203, "市"
	 */
	SHI(1010203, "市"),
	/**
	 * 1010204, "区县"
	 */
	QUXIAN(1010204, "区县");

	private Integer value;
	private String des;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	private FeatcodeEnum(Integer value, String des) {
		this.value = value;
		this.des = des;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
}
