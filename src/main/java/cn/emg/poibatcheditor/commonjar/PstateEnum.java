package cn.emg.poibatcheditor.commonjar;

/**
 * 角色类型
 * 
 * @author zsen
 * 
 */
public enum PstateEnum {
	/**
	 * -1, "未知"
	 */
	UNKNOWN(-1, "未知"),
	/**
	 * 0, "待进行"
	 */
	UNDO(0, "初始值"),
	/**
	 * 1, "制作占用"
	 */
	EDITING(1, "制作占用"),
	/**
	 * 2, "制作完成"
	 */
	EDITED(2, "制作完成"),
	/**
	 * 3, "校正占用"
	 */
	CHECKING(3, "校正占用"),
	/**
	 * 4, "校正完成"
	 */
	CHECKED(4, "校正完成"),
	/**
	 * 40, "校正完成无错误"
	 */
	CHECKEDWITHOUTERROR(40, "校正完成无错误"),
	/**
	 * 41, "校正完成有错误"
	 */
	CHECKEDWITHERROR(41, "校正完成有错误"),
	/**
	 * 5, "改错占用"
	 */
	MODIFYING(5, "改错占用"),
	/**
	 * 6, "改错完成"
	 */
	MODIFIED(6, "改错完成"),
	/**
	 * 60, "改错完成无错误"
	 */
	MODIFIEDWITHOUTERROR(60, "改错完成无错误"),
	/**
	 * 61, "改错完成有错误"
	 */
	MODIFIEDWITHERROR(61, "改错完成有错误");

	private Integer value;
	private String des;

	public static PstateEnum valueOf(Integer value) {
		PstateEnum ret = PstateEnum.UNKNOWN;
		for(PstateEnum roleType : PstateEnum.values()) {
			if(roleType.getValue().equals(value)) {
				ret = roleType;
				break;
			}
		}
		return ret;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	private PstateEnum(Integer value, String des) {
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
		for (PstateEnum val : PstateEnum.values()) {
			str += "\"" + val.getValue() + "\":\"" + val.getDes() + "\",";
		}
		str += "}";
		return str;
	}
}
