package cn.emg.poibatcheditor.common;

import java.util.HashSet;
import java.util.Set;

public enum POITagAttrnameEnum {
	unkown("unkown", "未知属性"),
	address1("address1", "详细地址（省级）"),
	address1e("address1e", "address1e"),
	address1p("address1p", "详细地址拼音（省级）"),
	address2("address2", "详细地址（地级）"),
	address2e("address2e", "address2e"),
	address2p("address2p", "详细地址拼音（地级）"),
	address3("address3", "详细地址（区县级）"),
	address3e("address3e", "address3e"),
	address3p("address3p", "详细地址拼音（区县级）"),
	address4("address4", "详细地址（乡镇级）"),
	address4e("address4e", "address4e"),
	address4p("address4p", "详细地址拼音（乡镇级）"),
	address5("address5", "详细地址（村级、除行政区划外的其它区域）"),
	address5e("address5e", "address5e"),
	address5p("address5p", "详细地址拼音（村级、除行政区划外的其它区域）"),
	address6("address6", "详细地址（大街/路/巷/里）"),
	address6e("address6e", "address6e"),
	address6p("address6p", "详细地址拼音（大街/路/巷/里）"),
	address7("address7", "详细地址（号/组）"),
	address7e("address7e", "address7e"),
	address7p("address7p", "详细地址拼音（号/组）"),
	address8("address8", "详细地址（其它）"),
	address8e("address8e", "address8e"),
	address8p("address8p", "详细地址拼音（其它）"),
	addressnflag("addressnflag", "内门址标记"),
	addresspicture("addresspicture", "门址照片"),
	ai("ai", "附加信息"),
	ascenic("ascenic", "4a5a旅游景区标记旅游景区等级"),
	brandptflag("brandptflag", "非品牌点标记"),
	busroute("busroute", "公交线路"),
	busstationflag("busstationflag", "公交总站标记"),
	cargostationflag("cargostationflag", "客运交通站点标记"),
	featcode2("featcode2", "补充类别"),
	namee("namee", "英文正式名称"),
	namep("namep", "拼音名称"),
	names("names", "简称"),
	namese("namese", "英文简称"),
	namesp("namesp", "拼音简称"),
	poistate("poistate", "poi状态"),
	postalcode("postalcode", "邮政代码"),
	starhotel("starhotel", "星级酒店"),
	tel("tel", "电话号码"),
	trtype("trtype", "轨道交通类型"),
	truckflag("truckflag", "货车停车场标记"),
	aliasc("aliasc", "别名"),
	hospgrade("hospgrade", "医院等级"),
	subnamec("subnamec", "中文分店名称"),
	officialnamec("officialnamec", "官方中文名称"),
	adminnamec("adminnamec", "区划更名前的旧中文名称"),
	cstorenamec("cstorenamec", "连锁店自有中文名称"),
	othernamec("othernamec", "其它名称"),
	famousflag("famousflag", "poi重要性"),
	adapri("adapri", "对象等级"),
	heatdegree("heatdegree", "热度值"),
	brandcode("brandcode", "品牌代码"),
	dataset("dataset", "数据集"),
	maxzoom("maxzoom", "最大显示等级"),
	minzoom("minzoom", "最小显示等级"),
	buildingareaid("buildingareaid", "关联建筑物区域编号");

	private String value;
	private String des;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	private POITagAttrnameEnum(String value, String des) {
		this.value = value;
		this.des = des;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static Set<String> getValues () {
		Set<String> set = new HashSet<String>();
		for (POITagAttrnameEnum item : POITagAttrnameEnum.values()) {
			if (item.equals(POITagAttrnameEnum.unkown))
				continue;
			
			set.add(item.getValue());
		}
		return set;
	}
}
