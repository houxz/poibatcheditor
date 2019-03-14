package cn.emg.poibatcheditor.commonjar;

/**
 * 属性错误
 * 
 * @author zsen
 * 
 */
public enum QIDEnum {
	/**
	 * "EID000000000", "目视错误"
	 */
	QID_MS_ERROR("EID000000000", "目视错误");

	private String value;
	private String des;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	private QIDEnum(String value, String des) {
		this.setValue(value);
		this.des = des;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
