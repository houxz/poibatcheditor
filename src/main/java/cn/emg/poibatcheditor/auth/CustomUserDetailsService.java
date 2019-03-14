package cn.emg.poibatcheditor.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import cn.emg.poibatcheditor.client.EmapgoAccountClient;
import cn.emg.poibatcheditor.common.RoleTypeEnum;
import cn.emg.poibatcheditor.pojo.EmployeeModel;
import cn.emg.poibatcheditor.pojo.UserRoleModel;
import cn.emg.poibatcheditor.service.SessionService;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	private final static String SUPERADMIN_USERNAME = "superadmin";
	@Value("${superadmin.password}")
	private String SUPERADMIN_PASSWORD = "emapgo1qaz@WSX";
	private final static List<RoleTypeEnum> SUPERADMIN_ROLETYPE = new ArrayList<RoleTypeEnum>(Arrays.asList(RoleTypeEnum.ROLE_SUPERADMIN));

	private final static String YANFAADMIN_USERNAME = "yanfaadmin";
	@Value("${yanfaadmin.password}")
	private String YANFAADMIN_PASSWORD = "5921034";
	private final static List<RoleTypeEnum> YANFAADMIN_ROLETYPE = new ArrayList<RoleTypeEnum>(Arrays.asList(RoleTypeEnum.ROLE_YANFAADMIN));

	private final static String ADMIN_USERNAME = "admin";
	@Value("${admin.password}")
	private String ADMIN_PASSWORD = "emapgo123!@#";
	private final static List<RoleTypeEnum> ADMIN_ROLETYPE = new ArrayList<RoleTypeEnum>(Arrays.asList(RoleTypeEnum.ROLE_ADMIN));

	@Autowired
	private EmapgoAccountClient emapgoAccountClient;

	@Autowired
	private SessionService sessionService;

	@Override
	public CustomUserDetails loadUserByUsername(String username) {

		if (sessionService.isDuplicateLogin(username)) {
			throw new SessionAuthenticationException(new String());
		}

		if (username.equals(SUPERADMIN_USERNAME)) {
			CustomUserDetails userDetails = new CustomUserDetails();
			userDetails.setUsername(username);
			userDetails.setPassword(DigestUtils.md5DigestAsHex(SUPERADMIN_PASSWORD.getBytes()));
			userDetails.setEnabled(Boolean.valueOf(true));
			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			for (RoleTypeEnum role : SUPERADMIN_ROLETYPE) {
				auths.add(new SimpleGrantedAuthority(role.toString()));
			}
			userDetails.setAuthorities(auths);
			return userDetails;
		}
		if (username.equals(YANFAADMIN_USERNAME)) {
			CustomUserDetails userDetails = new CustomUserDetails();
			userDetails.setUsername(username);
			userDetails.setPassword(DigestUtils.md5DigestAsHex(YANFAADMIN_PASSWORD.getBytes()));
			userDetails.setEnabled(Boolean.valueOf(true));
			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			for (RoleTypeEnum role : YANFAADMIN_ROLETYPE) {
				auths.add(new SimpleGrantedAuthority(role.toString()));
			}
			userDetails.setAuthorities(auths);
			return userDetails;
		}
		if (username.equals(ADMIN_USERNAME)) {
			CustomUserDetails userDetails = new CustomUserDetails();
			userDetails.setUsername(username);
			userDetails.setPassword(DigestUtils.md5DigestAsHex(ADMIN_PASSWORD.getBytes()));
			userDetails.setEnabled(Boolean.valueOf(true));
			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			for (RoleTypeEnum role : ADMIN_ROLETYPE) {
				auths.add(new SimpleGrantedAuthority(role.toString()));
			}
			userDetails.setAuthorities(auths);
			return userDetails;
		}

		EmployeeModel authority;
		CustomUserDetails userDetails = new CustomUserDetails();
		try {
			authority = emapgoAccountClient.getEmployeeByUsername(username);

			userDetails.setUserid(authority.getId());
			userDetails.setUsername(authority.getUsername());
			userDetails.setPassword(authority.getPassword());
			userDetails.setEnabled(authority.getEnabled().equals(1));

			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			List<?> list = emapgoAccountClient.selectUserRoleByUserID(authority.getId());
			for (Object l : list) {
				if (l instanceof UserRoleModel)
				auths.add(new SimpleGrantedAuthority(((UserRoleModel) l).getRolename()));
			}
			userDetails.setAuthorities(auths);
		} catch (Exception e) {
			return userDetails;
		}
		
		return userDetails;
	}

}