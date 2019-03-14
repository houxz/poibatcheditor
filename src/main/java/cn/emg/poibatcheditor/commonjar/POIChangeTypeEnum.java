package cn.emg.poibatcheditor.commonjar;

import java.io.Serializable;

public enum POIChangeTypeEnum implements Serializable {
	/**
	 *  新增POI
	 */
	create,
	/**
	 *  如下字段发生变化时
	 *  tags表
	 *  geo，namec，owner，featcode，sortcode，grade
	 */
	field_change,
	/**
	 *   不会出现
	 */
	no_change,
	/**
	 *  删除
	 */
	remove,
	/**
	 * 如下字段发生变化时
	 * optype,autocheck,manualcheck,confirm
	 */
	tag_change,
	/**
	 *  组合变化时
	 *  field_change + tag_change
	 */
	tag_field_change;
}
