package cn.emg.poibatcheditor.commonjar;

/**
 * 属性错误
 * 
 * @author zsen
 * 
 */
public enum ErrorStateEnum {
	/**
	 * 0, "未知状态"
	 */
	ERROR_STATE_UNKNOW(0, "未知状态"),
	/**
	 * 1, "未解决"
	 */
	ERROR_STATE_UN_SLOVE(1, "未解决"),
	/**
	 * 2, "解决"
	 */
	ERROR_STATE_SLOVE(2, "解决"),
	/**
	 * 3, "关闭"
	 */
	ERROR_STATE_CLOSE(3, "关闭");

	private Integer value;
	private String des;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	private ErrorStateEnum(Integer value, String des) {
		this.setValue(value);
		this.des = des;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
}
