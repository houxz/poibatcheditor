package cn.emg.poibatcheditor.commonjar;

import java.io.Serializable;

/** Created by RaymondLi on 2017/5/9. */
public enum ComputeModeEnum implements Serializable {
  none {
    @Override
    public String getDesc() {
      return "不存在该模式";
    }

    @Override
    public int getCode() {
      return -1;
    }
  },
  line {
    @Override
    public String getDesc() {
      return "Line模式";
    }

    @Override
    public int getCode() {
      return 0;
    }
  },
  polygon {
    @Override
    public String getDesc() {
      return "Polygon模式";
    }

    @Override
    public int getCode() {
      return 1;
    }
  };

  public abstract String getDesc();

  public abstract int getCode();
}
