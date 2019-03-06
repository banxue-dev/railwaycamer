package com.admin.railway.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("railway")
public class RailwayController extends BaseController {


	@GetMapping({ "/index" })
	String index(Model model) {
		
		return "railway/index";
	}
	@GetMapping({ "/photolist" })
	String photo(Model model) {
		
		return "railway/photoManage/photoList";
	}

	

}
