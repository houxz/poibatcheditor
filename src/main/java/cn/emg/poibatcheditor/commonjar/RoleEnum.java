package cn.emg.poibatcheditor.commonjar;

import java.io.Serializable;

/** Created by RaymondLi on 2017/5/9. */
public enum RoleEnum implements Serializable {
  none {
    public String getRoleInCN() {
      return "尚未设定模式";
    }
  },
  edit {
    public String getRoleInCN() {
      return "编辑模式";
    }
  },
  cace {

    public String getRoleInCN() {
      return "改错模式";
    }
  },
  cmce {
    public String getRoleInCN() {
      return "改错模式";
    }
  },
  view {
    public String getRoleInCN() {
      return "浏览模式";
    }
  };

  public abstract String getRoleInCN();
}
