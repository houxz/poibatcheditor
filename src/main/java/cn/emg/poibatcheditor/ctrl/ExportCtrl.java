package cn.emg.poibatcheditor.ctrl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import cn.emg.poibatcheditor.common.ActionEnum;
import cn.emg.poibatcheditor.common.CommonConstants;
import cn.emg.poibatcheditor.common.ParamUtils;
import cn.emg.poibatcheditor.common.RequestModule;
import cn.emg.poibatcheditor.common.ResultModel;
import cn.emg.poibatcheditor.dao.POIModelDao;
import cn.emg.poibatcheditor.pojo.EmployeeModel;

@Controller
@RequestMapping("/export.web")
public class ExportCtrl extends BaseCtrl {

	private static final Logger logger = LoggerFactory.getLogger(ExportCtrl.class);
	
	@Autowired
	private POIModelDao poiModelDao;

	@RequestMapping()
	public String openLader(Model model, HttpSession session, HttpServletRequest request) {
		logger.debug("OPENLADER");
		try {
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("OPENLADER OVER");
		return "export";
	}

	@RequestMapping(method = RequestMethod.POST)
	private ModelAndView controller(Model model, HttpSession session, HttpServletRequest request,
			@RequestParam("action") ActionEnum action,
			@RequestParam("params") RequestModule module) {

		Integer userid = ParamUtils.getIntAttribute(session, CommonConstants.SESSION_USER_ID, -1);
		EmployeeModel userInfo = new EmployeeModel();
		userInfo.setId(userid);
		module.setUserInfo(userInfo);

		ResultModel result = new ResultModel();
		try {
			switch (action) {
			case getPOIs:
				result = getPOIs(module);
				break;
			default:
				result.setResult(0);
				result.setResultMsg("未知请求：" + action);
				break;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}
		return new ModelAndView(new MappingJackson2JsonView()).addAllObjects(result);
	}

	private ResultModel getPOIs(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			String columnsStr = module.getParameter("columns");
			Set<String> columns = new HashSet<String>();
			for (String column : columnsStr.split(",")) {
				if (column != null && !column.isEmpty() && !column.trim().isEmpty())
					columns.add(column);
			}
			String code = module.getParameter("code");
			List<Map<String, Object>> pois = poiModelDao.select(columns, code);
			result.setResult(1);
			result.setRows(pois);
			result.setTotal(pois.size());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
}
