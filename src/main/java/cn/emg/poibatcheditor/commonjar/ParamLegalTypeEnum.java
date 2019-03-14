package cn.emg.poibatcheditor.commonjar;

public enum ParamLegalTypeEnum {
	/**
	 * -1, "未知"
	 */
	UNKNOW(-1, "未知"),
	/**
	 * 0, "合法"
	 */
	DOLEGAL(0, "合法"),
	/**
	 * 1, "缺少参数：%s"
	 */
	PARAMLOST(1, "缺少参数：%s"),
	/**
	 * 2, "参数不能为空：%s"
	 */
	PARAMNULL(2, "参数不能为空：%s"),
	/**
	 * 3, "非法的取值范围，%s： %s"
	 */
	PARAMOUTOFRANGE(3, "非法的取值范围，%s： %s");

	private Integer code;
	private String des;

	private ParamLegalTypeEnum(Integer code, String des) {
		this.code = code;
		this.des = des;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
	
	public String getMsg(Object... args) {
		String ret = new String();
		try {
			ret = String.format(this.des, args);
		} catch (Exception e) {
		}
		return ret;
	}
}
