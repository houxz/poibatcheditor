package cn.emg.poibatcheditor.common;

/**
 * 角色类型
 * 
 * @author zsen
 * 
 */
public enum RoleTypeEnum {
	/**
	 * 0, "未知角色"
	 */
	UNKNOWN(0, "未知角色"),
	/**
	 * -1, "超级管理员"
	 */
	ROLE_SUPERADMIN(-1, "超级管理员"),
	/**
	 * -2, "研发管理员"
	 */
	ROLE_YANFAADMIN(-2, "研发管理员"),
	/**
	 * 1, "高级管理员"
	 */
	ROLE_ADMIN(1, "高级管理员"),
	
	/**
	 * 2, "管理人员"
	 */
	ROLE_POIVIDEOEDIT(2, "管理人员"),
	/**
	 * 5, "制作人员"
	 */
	ROLE_WORKER(5, "制作人员"),
	/**
	 * 6, "校正人员"
	 */
	ROLE_CHECKER(6, "校正人员"),
	/**
	 * 7, "导出人员"
	 */
	ROLE_EXPORT(7, "导出人员");

	private Integer value;
	private String des;

	public static RoleTypeEnum valueOf(Integer value) {
		RoleTypeEnum ret = RoleTypeEnum.UNKNOWN;
		for(RoleTypeEnum roleType : RoleTypeEnum.values()) {
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

	private RoleTypeEnum(Integer value, String des) {
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
