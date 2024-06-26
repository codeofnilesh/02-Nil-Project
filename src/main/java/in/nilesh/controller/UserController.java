package in.nilesh.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import in.nilesh.binding.LoginForm;
import in.nilesh.binding.RegisterForm;
import in.nilesh.binding.ResetPwdForm;
import in.nilesh.contants.AppContants;
import in.nilesh.entities.User;
import in.nilesh.props.AppProps;
import in.nilesh.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private AppProps appProps;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("login", new LoginForm());
		return "index";
	}

	@PostMapping("/login")
	public String loginCheck(@ModelAttribute("login") LoginForm login, Model model) {
		User user = userService.login(login);

		if (user == null) {
			model.addAttribute(AppContants.ERROR_MSG, appProps.getMessages().get("invalidLogin"));
			return "index";
		}

		if (user.getPwdUpdated().equals("NO")) {

			ResetPwdForm formObj = new ResetPwdForm();
			formObj.setUserId(user.getUserId());

			model.addAttribute(AppContants.RESET_PWD, formObj);
			return AppContants.RESET_PWD;
		}

		return "redirect:dashboard";
	}

	@PostMapping("/updatePwd")
	public String updatePwd(@ModelAttribute("resetPwd") ResetPwdForm resetPwd, Model model) {

		if (!resetPwd.getNewPwd().equals(resetPwd.getConfirmPwd())) {
			model.addAttribute(AppContants.ERROR_MSG, appProps.getMessages().get("bothPwdSame"));
			return AppContants.RESET_PWD;
		}

		boolean status = userService.resetPwd(resetPwd);

		if (status) {
			return "redirect:dashboard";
		}

		model.addAttribute(AppContants.ERROR_MSG, appProps.getMessages().get("Pwd Update faild"));
		return AppContants.RESET_PWD;

	}

	@GetMapping("/register")
	public String loadRegisterPage(Model model) {
		model.addAttribute("registerForm", new RegisterForm());
		Map<Integer, String> countries = userService.getCountries();
		model.addAttribute("countries", countries);
		return "register";
	}

	@GetMapping("/getStates")
	@ResponseBody
	public Map<Integer, String> getStates(@RequestParam("countryId") Integer countryId) {
		return userService.getStates(countryId);
	}

	@GetMapping("/getCities")
	@ResponseBody
	public Map<Integer, String> getCities(@RequestParam("stateId") Integer stateId) {
		return userService.getCities(stateId);
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute RegisterForm registerForm, Model model) {

		boolean saveUser = userService.saveUser(registerForm);

		if (saveUser) {
			model.addAttribute(AppContants.SUCCESS_MSG, appProps.getMessages().get("regSuccess"));
		} else {
			model.addAttribute(AppContants.ERROR_MSG, appProps.getMessages().get("regFailed"));
		}

		Map<Integer, String> countries = userService.getCountries();
		model.addAttribute("countries", countries);

		return "register";
	}

}
