package cn.emg.poibatcheditor.pojo;

import java.util.List;

public class EntityTagsDO extends EntityCommonFieldsDO {
	private List<TagDO> tags;
	
	public List<TagDO> getTags() {
		return tags;
	}
	public void setTags(List<TagDO> tags) {
		this.tags = tags;
	}
}
