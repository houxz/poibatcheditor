package cn.emg.poibatcheditor.commonjar;

import java.io.Serializable;

public enum ErrorStatusEnum implements Serializable {
  none, //未修改
  accept, //已修改
  refuse;
}
