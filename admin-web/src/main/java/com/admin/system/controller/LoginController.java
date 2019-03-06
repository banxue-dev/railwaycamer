package com.admin.system.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.common.annotation.Log;
import com.admin.common.controller.BaseController;
import com.admin.common.domain.FileDO;
import com.admin.common.domain.Tree;
import com.admin.common.service.FileService;
import com.admin.common.utils.MD5Utils;
import com.admin.common.utils.R;
import com.admin.common.utils.ShiroUtils;
import com.admin.system.domain.MenuDO;
import com.admin.system.service.MenuService;

@Controller
public class LoginController extends BaseController {

	@Autowired
	MenuService menuService;
	@Autowired
	FileService fileService;
	@GetMapping({ "/", "" })
	String welcome(Model model) {

		return "redirect:/login";
	}

	@Log("请求访问主页")
	@GetMapping({ "/index" })
	String index(Model model) {
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		FileDO fileDO = fileService.get(getUser().getPicId());
		if(fileDO!=null&&fileDO.getUrl()!=null){
			if(fileService.isExist(fileDO.getUrl())){
				model.addAttribute("picUrl",fileDO.getUrl());
			}else {
				model.addAttribute("picUrl","/img/photo_s.jpg");
			}
		}else {
			model.addAttribute("picUrl","/img/photo_s.jpg");
		}
		model.addAttribute("username", getUser().getUsername());
		return "index";
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password) {
		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return R.ok();
		} catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}

	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

}
