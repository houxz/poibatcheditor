package cn.emg.poibatcheditor.auth;

import java.util.Set;

import cn.emg.poibatcheditor.common.RoleTypeEnum;

public class MenuAuthModel implements Comparable<MenuAuthModel> {
	private Integer code;
	private String url;
	private String label;
	private Set<RoleTypeEnum> roleSet;
	private Boolean enabled;
	private Boolean active = false;
	private Boolean isshow = false;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Set<RoleTypeEnum> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<RoleTypeEnum> roleSet) {
		this.roleSet = roleSet;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	@Override
	public int compareTo(MenuAuthModel obj) {
		if(((MenuAuthModel)obj).getCode().equals(this.code) &&
			((MenuAuthModel)obj).getUrl().equals(this.url) &&
			((MenuAuthModel)obj).getLabel().equals(this.label) &&
			((MenuAuthModel)obj).getRoleSet().equals(this.roleSet) &&
			((MenuAuthModel)obj).getEnabled().equals(this.enabled) &&
			((MenuAuthModel)obj).getActive().equals(this.active)) {
			return 0;
		} else
			return 1;
	}
	public Boolean getIsshow() {
		return isshow;
	}
	public void setIsshow(Boolean isshow) {
		this.isshow = isshow;
	}
	
}
