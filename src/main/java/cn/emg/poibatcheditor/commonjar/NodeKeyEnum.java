package cn.emg.poibatcheditor.commonjar;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class NodeKeyEnum {
	public static final  String nodeAttrname = "tlflag,rwflag,saflag,sanamec,paflag,panamec,exitflag,exitnamec,entrancenamec,tsflag,tsnamec,tstype,tsnum,etcposition,tcposition,junctionid,owner,complexjunctionid,junctiontype,tsnamee,tsnamep,sanamee,sanamep,panamee,panamep,exitnamee,exitnamep,entrancenamee,entrancenamep,bcflag,complain,hasattach,speed,minspeed,truckspeed,truckminspeed";
	
	private HashSet<String> nodeAttrnameSet = new HashSet<String>();

	public HashSet<String> getNodeAttrnameSet() {
		return nodeAttrnameSet;
	}

	public void setNodeAttrnameSet(HashSet<String> nodeAttrnameSet) {
		this.nodeAttrnameSet = nodeAttrnameSet;
	}

	@PostConstruct
	public void init() {
		for (String attr : nodeAttrname.split(",")) {
			nodeAttrnameSet.add(attr);
		}
	}
	
	
}
