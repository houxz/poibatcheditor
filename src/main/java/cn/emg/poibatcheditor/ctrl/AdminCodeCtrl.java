package cn.emg.poibatcheditor.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.alibaba.fastjson.JSONObject;

import cn.emg.poibatcheditor.common.FeatcodeEnum;
import cn.emg.poibatcheditor.common.ParamUtils;
import cn.emg.poibatcheditor.dao.AdminCodeModelDao;
import cn.emg.poibatcheditor.pojo.AdminCodeModel;
import cn.emg.poibatcheditor.pojo.AdminCodeModelExample;
import cn.emg.poibatcheditor.pojo.AdminCodeModelExample.Criteria;

@Controller
@RequestMapping("/admincode.web")
public class AdminCodeCtrl extends BaseCtrl {

	private static final Logger logger = LoggerFactory.getLogger(AdminCodeCtrl.class);
	
	@Autowired
	private AdminCodeModelDao adminCodeModelDao;

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "atn=getAdminCodes")
	public ModelAndView getAdminCodes(Model model, HttpServletRequest request, HttpSession session) {
		logger.debug("START");
		ModelAndView json = new ModelAndView(new MappingJackson2JsonView());
		List<AdminCodeModel> adminCodes = new ArrayList<AdminCodeModel>();
		Integer total = 0;
		try {
			String filter = ParamUtils.getParameter(request, "filter", "");
			Integer offset = ParamUtils.getIntParameter(request, "offset", 0);
			Integer limit = ParamUtils.getIntParameter(request, "limit", 10);
			String radio = ParamUtils.getParameter(request, "radio", "all");
			String admincodes = ParamUtils.getParameter(request, "admincodes", "");
			
			Map<String, Object> filterPara = null;
			AdminCodeModelExample example = new AdminCodeModelExample();
			Criteria criteria = example.or().andFeatcodeIn(new ArrayList<Integer>() {
				private static final long serialVersionUID = -3192945586983155641L;
			{
				add(FeatcodeEnum.QUANGUO.getValue());
				add(FeatcodeEnum.SHENG.getValue());
				add(FeatcodeEnum.SHI.getValue());
			}});
			if (filter.length() > 0) {
				filterPara = (Map<String, Object>) JSONObject.parse(filter);
				for (String key : filterPara.keySet()) {
					switch (key) {
					case "adaid":
						criteria.andAdaidEqualTo(Integer.valueOf(filterPara.get(key).toString()));
						break;
					case "aliasc":
						criteria.andAliascLike("%" + filterPara.get(key).toString().trim() + "%");
						break;
					case "areacode":
						criteria.andAreacodeEqualTo(filterPara.get(key).toString());
						break;
					case "citycode":
						criteria.andCitycodeEqualTo(Integer.valueOf(filterPara.get(key).toString()));
						break;
					case "featcode":
						criteria.andFeatcodeEqualTo(Integer.valueOf(filterPara.get(key).toString()));
						break;
					case "namec":
						criteria.andNamecLike("%" + filterPara.get(key).toString().trim() + "%");
						break;
					case "postcode":
						criteria.andPostcodeEqualTo(filterPara.get(key).toString());
						break;
					case "owner":
						criteria.andOwnerEqualTo(Integer.valueOf(filterPara.get(key).toString()));
						break;
					case "rank":
						criteria.andRankEqualTo(Integer.valueOf(filterPara.get(key).toString()));
						break;
					default:
						logger.error("未处理的筛选项：" + key);
						break;
					}
				}
			}
			
			if (limit.compareTo(0) > 0)
				example.setLimit(limit);
			if (offset.compareTo(0) > 0)
				example.setOffset(offset);
			example.setOrderByClause("adaid ASC");
			if (!radio.equals("all")) {
				List<Integer> adaids = new ArrayList<Integer>();
				for (String adaid : admincodes.split(",")) {
					try {
						adaids.add(Integer.valueOf(adaid));
					} catch (Exception e) {
					}
				}
				criteria.andAdaidIn(adaids);
				
				if (!adaids.isEmpty()) {
					adminCodes = adminCodeModelDao.selectByExample(example);
					total = adminCodeModelDao.countByExample(example);
				}
			} else {
				adminCodes = adminCodeModelDao.selectByExample(example);
				total = adminCodeModelDao.countByExample(example);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		json.addObject("rows", adminCodes);
		json.addObject("total", total);
		json.addObject("result", 1);

		logger.debug("ProcessesManageCtrl-getItemAreas end.");
		return json;
	}
	
}
