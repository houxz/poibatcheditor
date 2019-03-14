package cn.emg.poibatcheditor.commonjar;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class WayKeyEnum {
	public static final  String wayAttrname = "nr,ow,pf,namec,bynamec,rsnamec,lanes,dt,df,fw,toll,vl,routeno,fc,isroadflag,buildflag,bridgeflag,viadflag,viadtype,tunnelflag,tunneltype,foverflag,vianvflag,spm,spmsource,sps,sourcetime,remark,featcode,namee,namep,bynamee,bynamep,rsnamee,rsnamep,gradient,srflag,htlim,wthlim,wtlim,axlelim,spm1,sps1,clflag,elflag,spm1source,trucklimflag,ramplimflag,inputdatatype,complain,fjunctionid,tjunctionid,timeflag,cartypeflag,stnameid,bynameid,rsnameid,owner,length,roadid,extendflag,fieldcollect" ;
	
	private HashSet<String> wayAttrnameSet = new HashSet<String>();
	

	public HashSet<String> getWayAttrnameSet() {
		return wayAttrnameSet;
	}


	public void setWayAttrnameSet(HashSet<String> wayAttrnameSet) {
		this.wayAttrnameSet = wayAttrnameSet;
	}


	@PostConstruct
	public void init() {
		for (String attr : wayAttrname.split(",")) {
			wayAttrnameSet.add(attr);
		}
	}
	
}
