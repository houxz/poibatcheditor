package cn.emg.poibatcheditor.pojo;

import java.util.HashSet;
import java.util.Set;

import cn.emg.poibatcheditor.commonjar.PstateEnum;

public class POIDoIndexWithError extends POIDo {
	private Integer index = 0;
	private Long attachid = 0L;
	private Set<ErrorModel> errors = new HashSet<ErrorModel>();
	
	/**
	 * 判断当前POI中是否有未修改的错误
	 * 判断pstate状态为 41表示有未处理错误
	 * @return 
	 * 是：true
	 * 否：false
	 */
	public Boolean hasUnModifyError() {
		try {
			for (ErrorModel error : errors) {
				if (error.getPstate().equals(PstateEnum.UNDO.getValue()) ||
					error.getPstate().equals(PstateEnum.CHECKEDWITHERROR.getValue()))
					return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	/**
	 * 判断当前POI中是否有未校正的错误
	 * 判断pstate状态为 61表示有未处理错误
	 * @return 
	 * 是：true
	 * 否：false
	 */
	public Boolean hasUnCheckError() {
		try {
			for (ErrorModel error : errors) {
				if (error.getPstate().equals(PstateEnum.MODIFIEDWITHERROR.getValue()))
					return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public Set<ErrorModel> getErrors() {
		return errors;
	}

	public void setErrors(Set<ErrorModel> errors) {
		this.errors = errors;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void addError(ErrorModel error) {
		this.errors.add(error);
	}

	public Long getAttachid() {
		return attachid;
	}

	public void setAttachid(Long attachid) {
		this.attachid = attachid;
	}
	
	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof POIDoIndexWithError) {
			POIDoIndexWithError p = (POIDoIndexWithError) obj;
			return this.getId().equals(p.getId());
		} else {
			return false;
		}
	}
	
}
