package com.admin.railway.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.common.controller.BaseController;
import com.admin.common.utils.Constants;
import com.admin.common.utils.Query;
import com.admin.common.utils.QueryParam;
import com.admin.common.utils.R;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.service.PersonService;
import com.sun.tools.javac.resources.compiler;

@Controller
@RequestMapping("/railway/person")
public class PersonController extends BaseController {

	@Autowired
	private PersonService personService;
	
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
	@PostMapping("/list")
	@ResponseBody
	public R list(@RequestParam Map<String, Object> params) {
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
	@GetMapping("/list")
	public String listUI() {
		return "railway/person/list";
	}

}
