package cn.emg.poibatcheditor.commonjar;

import java.io.Serializable;

/** Created by RaymondLi on 2017/4/26. */
public enum ChangeTypeEnum implements Serializable {
  no_change,
  create,
  remove,
  tag_change,
  geo_change,
  tag_geo_change,
  split;
}
