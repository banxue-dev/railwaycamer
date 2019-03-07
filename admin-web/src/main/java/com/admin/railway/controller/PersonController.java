package com.admin.railway.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.common.controller.BaseController;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.service.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController extends BaseController {

	@Autowired
	private PersonService personService;
	
	@GetMapping("/get")
	@ResponseBody
	PersonDO get(@RequestParam(name="id") String personId) {
		PersonDO personDO = personService.get(Long.parseLong(personId));
		return personDO;
	}

	@GetMapping("/list")
	List<PersonDO> list() {
		Map<String, Object> map = new HashMap<>();
		List<PersonDO> list = personService.list(map);
		return list;
	}

}
