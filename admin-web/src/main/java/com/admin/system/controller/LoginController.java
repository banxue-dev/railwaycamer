package com.admin.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.admin.railway.domain.StationDO;
import com.admin.railway.service.StationService;
import com.admin.system.domain.MenuDO;
import com.admin.system.domain.UserDO;
import com.admin.system.service.MenuService;

@Controller
public class LoginController extends BaseController {

	@Autowired
	MenuService menuService;
	@Autowired
	FileService fileService;
	@Autowired
	StationService stationService;
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
		return "railway/index";
//		return "index";
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
			UserDO user=getUser();
			/*if(user.getStartStationIds()==null || user.getStartStationIds()=="0" || user.getStartStationIds().equals("0")) {
				
			}else {
				String[] stations=user.getStartStationIds().split(",");
				List<Long> ids=new ArrayList<Long>();
				for(String str:stations) {
					Long id=Long.parseLong(str);
					List<StationDO> sds=stationService.getByParentId(id);
					if(sds!=null && sds.size()>0) {
						for(StationDO te:sds) {
							ids.add(te.getId());
						}
					}else {
						ids.add(id);
					}
				}
				user.setUserStationIds(ids);
			}*/
			if(user.getDeptId()==null || user.getDeptId()==-1) {
				
			}else {
				Map<String, Object> map=new HashMap<String,Object>();
				map.put("type", 1);
				List<StationDO> sds=stationService.list(map);
				Long stations=user.getDeptId();
				List<Long> ids=new ArrayList<Long>();
				user.setUserStationIds(getIds(stations,ids,sds));
			}
			return R.ok();
		} catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}
	public List<Long>  getIds(Long id,List<Long> ids,List<StationDO> sds) {
		List<StationDO> result=getChildres(sds,id);
		if(result!=null && result.size()>0) {
			for(StationDO te:result) {
				getIds(te.getId(),ids,sds);
			}
		}else {
			if(ids.indexOf(ids)==-1) {
				ids.add(id);
			}
		}
		return ids;
	}
	public List<StationDO> getChildres(List<StationDO> sds,Long id) {
		List<StationDO> result=new ArrayList<StationDO>();
		for(StationDO sd:sds) {
			if(sd.getParentId()==id) {
				result.add(sd);
			}
		}
		return result;
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
