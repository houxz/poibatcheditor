package cn.emg.poibatcheditor.commonjar;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class BGAttrnameEnum {
	public static final  String bgAttrname = "featcode,backid,owner,namec,namee,namep,dc,type,rtype,height,floor,inputdatatype,outputdatatype,remark,area,length,brflag,buildflag,planflag,poiid,railwayid,objid,pmeshid,bmeshid,iconname,updatetime,sourcetime";
	
	private HashSet<String> bgAttrnameSet = new HashSet<String>();
	
	public HashSet<String> getBgAttrnameSet() {
		return bgAttrnameSet;
	}

	public void setBgAttrnameSet(HashSet<String> bgAttrnameSet) {
		this.bgAttrnameSet = bgAttrnameSet;
	}

	@PostConstruct
	public void init() {
		for (String attr : bgAttrname.split(",")) {
			bgAttrnameSet.add(attr);
		}
	}
	
}
