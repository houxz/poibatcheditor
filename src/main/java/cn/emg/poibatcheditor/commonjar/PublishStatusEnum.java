package cn.emg.poibatcheditor.commonjar;

import java.io.Serializable;

public enum PublishStatusEnum  implements Serializable {
	unpublish, //未发布
	publishing, //发布中
	error,//冲突
	ok;//成功
}
