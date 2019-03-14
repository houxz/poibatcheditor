package cn.emg.poibatcheditor.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.emg.poibatcheditor.client.EmapgoAccountClient;
import cn.emg.poibatcheditor.common.CommonConstants;
import cn.emg.poibatcheditor.commonjar.RoleTypeEnum;
import cn.emg.poibatcheditor.pojo.EmployeeModel;
import cn.emg.poibatcheditor.pojo.LogModel;

@Controller
@RequestMapping("/login.web")
public class LoginCtrl extends BaseCtrl {

	private static final Logger logger = LoggerFactory.getLogger(LoginCtrl.class);

	@Autowired
	private EmapgoAccountClient emapgoAccountClient;

	@RequestMapping()
	public String login(Model model, HttpSession session, HttpServletRequest request) {
		logger.debug("START");
		try {
			String account = getLoginAccount(session);
			Integer userid = 0;
			String realname = new String();
			if(hasRole(request, RoleTypeEnum.ROLE_SUPERADMIN.toString())) {
				userid = RoleTypeEnum.ROLE_SUPERADMIN.getValue();
				realname = RoleTypeEnum.ROLE_SUPERADMIN.getDes();
			} else if(hasRole(request, RoleTypeEnum.ROLE_YANFAADMIN.toString())) {
				userid = RoleTypeEnum.ROLE_YANFAADMIN.getValue();
				realname = RoleTypeEnum.ROLE_YANFAADMIN.getDes();
			} else if(hasRole(request, RoleTypeEnum.ROLE_ADMIN.toString())) {
				userid = RoleTypeEnum.ROLE_ADMIN.getValue();
				realname = RoleTypeEnum.ROLE_ADMIN.getDes();
			} else {
				EmployeeModel user = emapgoAccountClient.getEmployeeByUsername(account);
				if (user == null) {
					if (session != null) {
						session.invalidate();
					}
					SecurityContext context = SecurityContextHolder.getContext();
					context.setAuthentication(null);
					SecurityContextHolder.clearContext();
					logger.error("user : " + account + " deny to login.");
					return "redirect:login.jsp";
				}
			
				userid = user.getId();
				realname = user.getRealname();
			}
			
			session.setAttribute(CommonConstants.SESSION_USER_ACC, account);
			session.setAttribute(CommonConstants.SESSION_USER_ID, userid);
			session.setAttribute(CommonConstants.SESSION_USER_NAME, realname);
			
			LogModel log = new LogModel();
			log.setType("LOGIN");
			log.setKey(userid.toString());
			log.setValue(account);
			log.setSessionid(session.getId());
			log.setIp(getRemortIP(request));

			if (hasRole(request, RoleTypeEnum.ROLE_WORKER.toString())) {
				logger.debug("LoginCtrl-login end to ROLE_WORKER page.");
				return "redirect:edit.web";
			} else if (hasRole(request, RoleTypeEnum.ROLE_CHECKER.toString())) {
				logger.debug("LoginCtrl-login end to ROLE_CHECKER page.");
				return "redirect:check.web";
			} else {
				if (session != null) {
					session.invalidate();
				}
				SecurityContext context = SecurityContextHolder.getContext();
				context.setAuthentication(null);
				SecurityContextHolder.clearContext();
				logger.error("user has no power getting in : " + account);
				return "redirect:login.jsp?login_error=4";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "redirect:login.jsp";
		}
	}
}
