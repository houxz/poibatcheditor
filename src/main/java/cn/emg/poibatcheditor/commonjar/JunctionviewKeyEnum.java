package cn.emg.poibatcheditor.commonjar;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class JunctionviewKeyEnum {

public static final  String junctionviewAttrname = "troadid1,troadid1_ver,troadid2,troadid2_ver,troadid3,troadid3_ver,troadid4,troadid4_ver,troadid5,troadid5_ver,troadid6,troadid6_ver,troadid7,troadid7_ver,troadid8,troadid8_ver,bno,lno,ano,pno,inputdatatype,complain,remark";
	
	private HashSet<String> junctionviewAttrnameSet = new HashSet<String>();

	@PostConstruct
	public void init() {
		for (String attr : junctionviewAttrname.split(",")) {
			junctionviewAttrnameSet.add(attr);
		}
	}

	public HashSet<String> getJunctionviewAttrnameSet() {
		return junctionviewAttrnameSet;
	}

	public void setJunctionviewAttrnameSet(HashSet<String> junctionviewAttrnameSet) {
		this.junctionviewAttrnameSet = junctionviewAttrnameSet;
	}
	
}
