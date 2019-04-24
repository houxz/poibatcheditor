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
import cn.emg.poibatcheditor.dao.AdminCodeModelDao;
import cn.emg.poibatcheditor.dao.JunctionviewModelDao;
import cn.emg.poibatcheditor.pojo.AdminCodeModel;
import cn.emg.poibatcheditor.pojo.EmployeeModel;

@Controller
@RequestMapping("/junctionview.web")
public class JunctionviewCtrl extends BaseCtrl {

	private static final Logger logger = LoggerFactory.getLogger(JunctionviewCtrl.class);
	
	@Autowired
	private JunctionviewModelDao junctionviewModelDao;
	
	@Autowired
	private AdminCodeModelDao adminCodeModelDao;

	@RequestMapping()
	public String openLader(Model model, HttpSession session, HttpServletRequest request) {
		logger.debug("OPENLADER");
		try {
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("OPENLADER OVER");
		return "junctionview";
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
			case getJunctionview:
				result = getJunctionview(module);
				break;
			case getJunctionviewByAdminCodes:
				result = getJunctionviewByAdminCodes(module);
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

	private ResultModel getJunctionview(RequestModule module) {
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
			Integer number = module.getIntParameter("number", -1);
			Integer size = module.getIntParameter("size", -1);
			
			List<Map<String, Object>> pois = junctionviewModelDao.select(columns, code, size, (number-1)*size);
			Integer total = junctionviewModelDao.count(columns, code);
			result.setResult(1);
			result.setRows(pois);
			result.setTotal(total);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
	
	private ResultModel getJunctionviewByAdminCodes(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			String columnsStr = module.getParameter("columns");
			Set<String> columns = new HashSet<String>();
			for (String column : columnsStr.split(",")) {
				if (column != null && !column.isEmpty() && !column.trim().isEmpty())
					columns.add(column);
			}
			Integer number = module.getIntParameter("number", -1);
			Integer size = module.getIntParameter("size", -1);
			
			Set<Integer> list = new HashSet<Integer>();
			String codes = module.getParameter("code");
			for (String code : codes.split(",")) {
				list.add(Integer.valueOf(code));
			}
			
			List<AdminCodeModel> adminCodes = adminCodeModelDao.selectOnTree(list );
			Set<String> owners = new HashSet<String>();
			for (AdminCodeModel adminCode : adminCodes) {
				owners.add(adminCode.getAdaid().toString());
			}
			List<Map<String, Object>> pois = junctionviewModelDao.selectByOwners(columns, owners, size, (number-1)*size);
			Integer total = junctionviewModelDao.countByOwners(columns, owners);
			result.setResult(1);
			result.setRows(pois);
			result.setTotal(total);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
}
