package com.admin.railway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.admin.common.controller.BaseController;
import com.admin.railway.service.OrderService;
import com.admin.railway.service.PersonService;

@Controller
@RequestMapping("/railway/order")
public class OrderController extends BaseController {

	@Autowired
	private PersonService personService;
	
	@Autowired
	private OrderService orderService;
	
	

}
