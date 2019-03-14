package cn.emg.poibatcheditor.converter;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.emg.poibatcheditor.common.RequestModule;

@Component
public class RequestModuleConverter implements Converter<String, RequestModule> {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestModuleConverter.class);
	
	@Override
	public RequestModule convert(String source) {
		RequestModule requestModule = new RequestModule();
		try {
			if (source != null && source.length() > 0) {
				JSONObject obj = JSONObject.parseObject(source);
				Map<String,Object> map = (Map<String,Object>) obj;
				requestModule.setReqParams(map);
				requestModule.setSource(source);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			requestModule = new RequestModule();
		}
		return requestModule;
	}
}
