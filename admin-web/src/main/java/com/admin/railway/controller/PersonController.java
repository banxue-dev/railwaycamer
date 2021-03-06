package com.admin.railway.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.admin.common.annotation.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.common.controller.BaseController;
import com.admin.common.utils.Constants;
import com.admin.common.utils.MD5Utils;
import com.admin.common.utils.QueryParam;
import com.admin.common.utils.R;
import com.admin.common.utils.ShiroUtils;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.StationDO;
import com.admin.railway.service.PersonService;
import com.admin.railway.service.StationService;
import com.admin.system.domain.UserDO;

@Controller
@RequestMapping("/railway/person")
public class PersonController extends BaseController {

	@Autowired
	private PersonService personService;
	@Autowired
	private StationService stationService;
	
	@RequiresPermissions("railway:person:eidt")
	@GetMapping("/get")
	@ResponseBody
	public PersonDO get(@RequestParam(name="id") String personId) {
		PersonDO personDO = personService.get(Long.parseLong(personId));
		return personDO;
	}
	
	/**
	 * 返回拍照人员-人员管理界面列表数据
	 * @param stationId
	 * @return
	 */
	@RequiresPermissions("railway:person:list")
	@PostMapping("/list")
	@ResponseBody
	public R list(@RequestParam Map<String, Object> params) {
		Object stationId=params.get("stationId");
		if(stationId==null ||com.admin.common.utils.StringUtils.isNullString(stationId.toString())) {
			UserDO user=getUser();
			if(user.getUserStationIds()!=null) {
				String ids="";
				int i=0;
				for(Long id:user.getUserStationIds()) {
					ids+=id;
					i++;
					if(i<user.getUserStationIds().size()) {
						ids+=",";
					}
				}
				params.put("startStationIds", ids);
			}
		}
		QueryParam query = new QueryParam(params);
		query.put("delState", Constants.NO); // 查询未删除
		
		List<PersonDO> list = new ArrayList<>();
		int count = personService.count(query);
		if (count > 0) {
			list = personService.list(query);
		}
		
		System.err.println(list.size());
		
		Map<String, Object> result = new HashMap<>();
		result.put("data", list);
		result.put("count", count);
		return R.ok(result);
	}

	/**
	 * 返回拍照人员-人员管理界面
	 * @return
	 */
	@RequiresPermissions("railway:person:list")
	@GetMapping("/list")
	public String listUI() {
		return "railway/person/list";
	}
	
	/**
	 * 返回拍照人员-添加页面
	 * @return
	 */
	@RequiresPermissions("railway:person:add")
	@GetMapping("/add")
	public String addUI() {
		return "railway/person/add";
	}
	
	/**
	 * 返回拍照人员-添加
	 * @return
	 */
	@Log("添加拍照人员")
	@RequiresPermissions("railway:person:add")
	@PostMapping("/add")
	@ResponseBody
	public R add(PersonDO person) {
		
		UserDO user = ShiroUtils.getUser();
		
		// 设置stationName
		if (person.getStationId() != null) {
			StationDO station = stationService.get(person.getStationId());
			person.setStationName(station.getName());
		}
		
		if (StringUtils.isBlank(person.getPassword())) {
			person.setPassword("123456");
		}
		// 用户密码加密
		person.setPassword(MD5Utils.encrypt(person.getLoginName(),person.getPassword()));
		
		person.setDelState(Constants.NO);
		person.setCreateTime(new Date());
		person.setCreateUser(user.getName());
		
		personService.save(person);
		return R.ok();
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@Log("删除拍照人员")
	@RequiresPermissions("railway:person:remove")
	@PostMapping("/remove")
	@ResponseBody
	public R remove(Long id) {
		personService.remove(id);
		return R.ok();
	}
	
	/**
	 * 返回拍照人员-添加页面
	 * @return
	 */
	@RequiresPermissions("railway:person:edit")
	@GetMapping("/edit/{id}")
	public String editUI(@PathVariable("id") Long pesonId, Map<String, Object> map) {
		PersonDO person = personService.get(pesonId);
		map.put("person", person);
		return "railway/person/edit";
	}
	
	/**
	 * 返回拍照人员-添加
	 * @return
	 */
	@Log("修改拍照人员")
	@RequiresPermissions("railway:person:edit")
	@PostMapping("/update")
	@ResponseBody
	public R update(PersonDO person) {
		
		UserDO user = ShiroUtils.getUser();
		
		// 设置stationName
		if (person.getStationId() != null) {
			StationDO station = stationService.get(person.getStationId());
			person.setStationName(station.getName());
		}
		
		if (StringUtils.isNotBlank(person.getPassword())) {
			// 用户密码加密
			PersonDO personDO = personService.get(person.getId());
			person.setPassword(MD5Utils.encrypt(personDO.getLoginName(),person.getPassword()));
		}
		
		person.setModifyTime(new Date());
		person.setModifyUser(user.getName());
		
		personService.update(person);
		return R.ok();
	}
	
	/**
	 * 校验数据
	 * @loginName 校验的数据
	 * @return
	 */
	@RequiresPermissions("railway:person:add")
	@PostMapping("/check")
	@ResponseBody
	public boolean check(
			@RequestParam(value="loginName", required = false) String loginName,
			@RequestParam(value="personId", required = false) Long personId) {
		// 修改页面, loginName和原始的loginName相同, 检查通过
		if (personId != null) {
			PersonDO person = personService.get(personId); 
			if (StringUtils.equals(person.getLoginName(), loginName)) {
				return true;
			}
		}
		
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotBlank(loginName)) {
			map.put("loginName", loginName);
		}
		
		return personService.count(map) == 0;
	}

}
