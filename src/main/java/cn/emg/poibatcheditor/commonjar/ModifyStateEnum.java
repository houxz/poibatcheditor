package cn.emg.poibatcheditor.commonjar;

/**
 * 属性错误
 * 
 * @author zsen
 * 
 */
public enum ModifyStateEnum {
	/**
	 * 0, "未知状态"
	 */
	ACCEPT_UNKNOW(0, "新增"),
	/**
	 * 1, "接受"
	 */
	ACCEPT_YES(1, "已接受"),
	/**
	 * 2, "不接受"
	 */
	ACCEPT_NO(2, "不接受"),
	/**
	 * 3, "其他值"
	 */
	ACCEPT_ANOTHER(3, "其他值"),
	/**
	 * 4, "搁置"
	 */
	ACCEPT_WAIT(4, "搁置");

	private Integer value;
	private String des;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	private ModifyStateEnum(Integer value, String des) {
		this.setValue(value);
		this.des = des;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	public static String toJsonStr() {
		String str = new String("{");
		for (ModifyStateEnum val : ModifyStateEnum.values()) {
			str += "\"" + val.getValue() + "\":\"" + val.getDes() + "\",";
		}
		str += "}";
		return str;
	}
	
}
