package com.admin.railway.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.common.controller.BaseController;
import com.admin.common.domain.Tree;
import com.admin.common.utils.R;
import com.admin.railway.domain.StationDO;
import com.admin.railway.service.StationService;

@Controller
@RequestMapping("/railway/station")
public class StationController extends BaseController {

	@Autowired
	private StationService stationService;

	@RequestMapping("/listTree")
	@ResponseBody
	public R getStationListByTree() {
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			List<Tree<StationDO>> lst=stationService.getStationByTree(map);
			return R.okdata(lst);
		}catch(Exception e) {
			return R.error();
		}
	}
	@RequestMapping("/leaderLook")
	public String leaderLook() {
		return "railway/leader/stationselect";
	}
}
