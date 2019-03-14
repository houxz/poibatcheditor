package cn.emg.poibatcheditor.ctrl;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foot.web")
public class FootCtrl extends BaseCtrl {

	@RequestMapping()
	public String foot(Model model, HttpSession session, HttpServletRequest request) {
		Calendar date = Calendar.getInstance();
		String thisYear = String.valueOf(date.get(Calendar.YEAR));

		model.addAttribute("thisYear", thisYear);
		return "foot";
	}
}
