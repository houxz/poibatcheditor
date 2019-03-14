package cn.emg.poibatcheditor.commonjar;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class LaneKeyEnum {
	public static final  String laneAttrname = "clane,lane,arrowno,laneno1,lanetype1,varlane1,troadid1,troadid1_ver,as1,cartype1,uturnflag1,timeflag1,laneno2,lanetype2,varlane2,troadid2,troadid2_ver,as2,cartype2,uturnflag2,timeflag2,laneno3,lanetype3,varlane3,troadid3,troadid3_ver,as3,cartype3,uturnflag3,timeflag3,laneno4,lanetype4,varlane4,troadid4,troadid4_ver,as4,cartype4,uturnflag4,timeflag4,laneno5,lanetype5,varlane5,troadid5,troadid5_ver,as5,cartype5,uturnflag5,timeflag5,laneno6,lanetype6,varlane6,troadid6,troadid6_ver,as6,cartype6,uturnflag6,timeflag6,laneno7,lanetype7,varlane7,troadid7,troadid7_ver,as7,cartype7,uturnflag7,timeflag7,laneno8,lanetype8,varlane8,troadid8,troadid8_ver,as8,cartype8,uturnflag8,timeflag8,laneno9,lanetype9,varlane9,troadid9,troadid9_ver,as9,cartype9,uturnflag9,timeflag9,laneno10,lanetype10,varlane10,troadid10,troadid10_ver,as10,cartype10,uturnflag10,timeflag10,laneno11,lanetype11,varlane11,troadid11,troadid11_ver,as11,cartype11,uturnflag11,timeflag11,laneno12,lanetype12,varlane12,troadid12,troadid12_ver,as12,cartype12,uturnflag12,timeflag12,laneno13,lanetype13,varlane13,troadid13,troadid13_ver,as13,cartype13,uturnflag13,timeflag13,laneno14,lanetype14,varlane14,troadid14,troadid14_ver,as14,cartype14,uturnflag14,timeflag14,laneno15,lanetype15,varlane15,troadid15,troadid15_ver,as15,cartype15,uturnflag15,timeflag15,laneno16,lanetype16,varlane16,troadid16,troadid16_ver,as16,cartype16,uturnflag16,timeflag16,complain,remark,tidallane1,tidallane2,tidallane3,tidallane4,tidallane5,tidallane6,tidallane7,tidallane8,tidallane9,tidallane10,tidallane11,tidallane12,tidallane13,tidallane14,tidallane15,tidallane16";
	
	private HashSet<String> laneAttrnameSet = new HashSet<String>();

	public HashSet<String> getLaneAttrnameSet() {
		return laneAttrnameSet;
	}

	public void setLaneAttrnameSet(HashSet<String> laneAttrnameSet) {
		this.laneAttrnameSet = laneAttrnameSet;
	}

	@PostConstruct
	public void init() {
		for (String attr : laneAttrname.split(",")) {
			laneAttrnameSet.add(attr);
		}
	}
}
