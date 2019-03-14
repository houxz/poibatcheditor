package cn.emg.poibatcheditor.config;

import java.util.Set;

import org.springframework.stereotype.Component;

import cn.emg.poibatcheditor.auth.MenuAuthModel;

@Component
public class MenuConfig {
	private Set<MenuAuthModel> menus;

	public Set<MenuAuthModel> getMenus() {
		return menus;
	}

	public void setMenus(Set<MenuAuthModel> menus) {
		this.menus = menus;
	}
}
