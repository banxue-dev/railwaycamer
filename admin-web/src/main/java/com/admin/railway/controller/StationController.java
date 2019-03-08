package com.admin.railway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.admin.common.controller.BaseController;
import com.admin.railway.service.StationService;

@Controller
@RequestMapping("/railway/station")
public class StationController extends BaseController {

	@Autowired
	private StationService stationService;


}
