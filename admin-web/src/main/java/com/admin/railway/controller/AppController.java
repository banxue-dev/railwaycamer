package com.admin.railway.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.admin.common.annotation.Log;
import com.admin.common.config.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.common.controller.BaseController;
import com.admin.common.utils.Constants;
import com.admin.common.utils.QueryParam;
import com.admin.common.utils.R;
import com.admin.common.utils.ShiroUtils;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.StationDO;
import com.admin.railway.service.OrderService;
import com.admin.railway.service.PersonService;
import com.admin.railway.service.StationService;
import com.admin.system.domain.UserDO;

@Controller
@RequestMapping("/app")
public class AppController extends BaseController {

	@GetMapping({ "/login" })
	String login(Model model) {
		
		return "railway/andriodApp/login";
	}
	@GetMapping({ "/setserver" })
	String setserver(Model model) {
		
		return "railway/andriodApp/setserver";
	}
	@GetMapping({ "/menu" })
	String menu(Model model) {
		
		return "railway/andriodApp/menu";
	}
}
