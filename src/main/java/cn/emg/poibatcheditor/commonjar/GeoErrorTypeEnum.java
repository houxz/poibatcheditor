package cn.emg.poibatcheditor.commonjar;

/**
 * 几何错误
 * 
 * @author zsen
 * 
 */
public enum GeoErrorTypeEnum {
	/**
	 * 漏新增
	 */
	ERROR_TYPE_MISS_NEW2(21200010031L, "漏新增"),
	/**
	 * 多新增
	 */
	ERROR_TYPE_OVER_NEW2(21200010033L, "多新增");

	private Long value;
	private String des;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	private GeoErrorTypeEnum(Long value, String des) {
		this.setValue(value);
		this.des = des;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
	
	public static Boolean is(Long value) {
		for(GeoErrorTypeEnum v : GeoErrorTypeEnum.values()) {
			if(v.getValue().equals(value)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public static GeoErrorTypeEnum valueOf(Long value) {
		GeoErrorTypeEnum ret = null;
		for(GeoErrorTypeEnum v : GeoErrorTypeEnum.values()) {
			if(v.getValue().equals(value)) {
				ret = v;
				break;
			}
		}
		return ret;
	}
	
	public static String toJsonStr() {
		String str = new String("{");
		for (GeoErrorTypeEnum val : GeoErrorTypeEnum.values()) {
			str += "\"" + val.getValue() + "\":\"" + val.getDes() + "\",";
		}
		str += "}";
		return str;
	}
	
}
