package cn.emg.poibatcheditor.commonjar;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class DirectionKeyEnum {
	
	public static final  String directionAttrname = "troadid1,troadid1_ver,direnamec1,direnamee1,direnamep1,exitnum1,exitdir1,troadid2,troadid2_ver,direnamec2,direnamee2,direnamep2,exitnum2,exitdir2,troadid3,troadid3_ver,direnamec3,direnamee3,direnamep3,exitnum3,exitdir3,troadid4,troadid4_ver,direnamec4,direnamee4,direnamep4,exitnum4,exitdir4,troadid5,troadid5_ver,direnamec5,direnamee5,direnamep5,exitnum5,exitdir5,troadid6,troadid6_ver,direnamec6,direnamee6,direnamep6,exitnum6,exitdir6,troadid7,troadid7_ver,direnamec7,direnamee7,direnamep7,exitnum7,exitdir7,troadid8,troadid8_ver,direnamec8,direnamee8,direnamep8,exitnum8,exitdir8,complain";
	
	private HashSet<String> directionAttrnameSet = new HashSet<String>();

	@PostConstruct
	public void init() {
		for (String attr : directionAttrname.split(",")) {
			directionAttrnameSet.add(attr);
		}
	}
	public HashSet<String> getDirectionAttrnameSet() {
		return directionAttrnameSet;
	}

	public void setDirectionAttrnameSet(HashSet<String> directionAttrnameSet) {
		this.directionAttrnameSet = directionAttrnameSet;
	}
	

}
