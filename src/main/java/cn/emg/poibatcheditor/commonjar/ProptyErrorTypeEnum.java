package cn.emg.poibatcheditor.commonjar;

/**
 * 属性错误
 * 
 * @author zsen
 * 
 */
public enum ProptyErrorTypeEnum {
	/**
	 * 无
	 */
	ERROR_TYPE_FIELD_NOTHING(21100010000L, "无"),
	/**
	 * 存在错别字
	 */
	ERROR_TYPE_FIELD_WRONGWORD(21100010001L, "存在错别字"),
	/**
	 * 未按规则制作
	 */
	ERROR_TYPE_FIELD_NORULE(21100010002L, "未按规则制作"),
	/**
	 * 多制作
	 */
	ERROR_TYPE_FIELD_OVER_DONE(21100010003L, "多制作"),
	/**
	 * 漏制作
	 */
	ERROR_TYPE_FIELD_MISS_DONE(21100010004L, "漏制作"),
	/**
	 * 多字或少字
	 */
	ERROR_TYPE_FIELD_MOREORLESS_CHAR(21100010006L, "多字或少字");

	private Long value;
	private String des;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	private ProptyErrorTypeEnum(Long value, String des) {
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
		for(ProptyErrorTypeEnum v : ProptyErrorTypeEnum.values()) {
			if(v.getValue().equals(value)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public static ProptyErrorTypeEnum valueOf(Long value) {
		ProptyErrorTypeEnum ret = null;
		for(ProptyErrorTypeEnum v : ProptyErrorTypeEnum.values()) {
			if(v.getValue().equals(value)) {
				ret = v;
				break;
			}
		}
		return ret;
	}
	
	public static String toJsonStr() {
		String str = new String("{");
		for (ProptyErrorTypeEnum val : ProptyErrorTypeEnum.values()) {
			if(val.equals(ERROR_TYPE_FIELD_NOTHING)) continue;
			str += "\"" + val.getValue() + "\":\"" + val.getDes() + "\",";
		}
		str += "}";
		return str;
	}
}
