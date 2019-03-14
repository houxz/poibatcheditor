package cn.emg.poibatcheditor.commonjar;

import java.io.Serializable;

public enum AttrnameExtendEnum implements Serializable {
  tel, //电话号码
  postalcode, //邮政代码
  namee, //英文正式名称
  namep, //拼音名称
  names, //简称
  namese, //英文简称
  namesp, //拼音简称
  address1, //详细地址（省级）
  address1p, //详细地址拼音（省级）
  address2, //详细地址（地级）
  address2p, //详细地址拼音（地级）
  address3, //详细地址（区县级）
  address3p, //详细地址拼音（区县级）
  address4, //详细地址（乡镇级）
  address4p, //详细地址拼音（乡镇级）
  address5, //详细地址（村级、除行政区划外的其它区域）
  address5p, //详细地址拼音（村级、除行政区划外的其它区域）
  address6, //详细地址（大街/路/巷/里）
  address6p, //详细地址拼音（大街/路/巷/里）
  address7, //详细地址（号/组）
  address7p, //详细地址拼音（号/组）
  address8, //详细地址（其它）
  address8p, //详细地址拼音（其它）
  poistate, //POI状态
  sortcode, //系列代码
  dispc, //优先顺序
  fax, //传真号码
  website, //网址
  email, //电子邮箱
  relationtype, //与所属POI的关系类型
  linkpoiid, //所属POI的编号
  roadid, //所在道路编号
  matchrdver, //匹配的道路版本
  iconname, //关联Landmark图标名称
  featcode2, //补充类别
  shophour, //营业时间
  restday, //休息日
  charge, //收费情况
  rtname, //所属路线
  ai, //附加信息
  inputdatatype, //源数据类型
  outputdatatype, //商品化类型
  prolabel, //过程标记
  starhotel, //星级酒店标记
  ascenic, //旅游景区标记
  trtype, //轨道交通标记
  olabel, //其他标记
  fueltype, //燃料类型
  relatdata, //相关资料
  remark, //备注
  owner, //所属行政区域代码
  meshid, //所属网格代码
  poimeshid, //所属POI的网格代码
  roadmesid, //所在道路的网格代码
  updatedate, //更新资料的年月
  namepic, //名称照片
  addresspic, //门址照片
  viewpic, //全景照片
  isrelated; //是否被关联

  public static AttrnameExtendEnum get(String name) {
    for (AttrnameExtendEnum c : AttrnameExtendEnum.values()) {
      if (c.name().equals(name)) {
        return c;
      }
    }
    throw new IllegalArgumentException(name);
  }
}
