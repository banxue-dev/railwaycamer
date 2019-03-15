package com.admin.railway.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.admin.common.annotation.Log;
import com.admin.common.config.Constant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

	@RequiresPermissions("railway:station:station")
	@GetMapping()
	public String station(){
		return "railway/station/station";
	}

	@RequiresPermissions("railway:station:station")
	@ResponseBody
	@GetMapping("/list")
	public List<StationDO> list(@RequestParam Map<String, Object> map){
		return stationService.list(map);
	}

	@RequiresPermissions("railway:station:add")
	@GetMapping("/add/{pId}")
	public String add(@PathVariable("pId") Long pId, Model model){
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", Constant.ROOT_DIRECTORY);
		} else {
			model.addAttribute("pName", stationService.get(pId).getName());
		}
		return "railway/station/add";
	}

	@Log("新增站点")
	@RequiresPermissions("railway:station:add")
	@PostMapping("/save")
	@ResponseBody
	public R save(StationDO station){
		boolean fag = stationService.save(station,getUser());
		if(fag){
			return R.ok(Constant.SuccessInfo.ADD_SUCCESS.getMsg());
		}else {
			return R.error(Constant.ErrorInfo.ADD_FAIL.getCode(),Constant.ErrorInfo.ADD_FAIL.getMsg());
		}
	}

	@RequiresPermissions("railway:station:edit")
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model){
		StationDO station = stationService.get(id);
		Long pId = station.getParentId();
		if(pId == Constant.Number.ZERO.getCode()){
			model.addAttribute("pName", Constant.ROOT_DIRECTORY);
		}else {
			model.addAttribute("pName", stationService.get(pId).getName());
		}
		model.addAttribute("station",station);
		return "railway/station/edit";
	}

	@Log("修改站点")
	@RequiresPermissions("railway:station:edit")
	@PostMapping("/update")
	@ResponseBody
	public R update(StationDO station){
		boolean fag = stationService.update(station,getUser());
		if(fag){
			return R.ok(Constant.SuccessInfo.UPDATE_SUCCESS.getMsg());
		}else {
			return R.error(Constant.ErrorInfo.UPDATE_FAIL.getCode(),Constant.ErrorInfo.UPDATE_FAIL.getMsg());
		}
	}

	@Log("删除站点")
	@RequiresPermissions("railway:station:remove")
	@PostMapping("/remove")
	@ResponseBody
	public R remove(Long id){
		boolean fag = stationService.remove(id);
		if(fag){
			return R.ok(Constant.SuccessInfo.UPDATE_SUCCESS.getMsg());
		}else {
			return R.error(Constant.ErrorInfo.UPDATE_FAIL.getCode(),Constant.ErrorInfo.UPDATE_FAIL.getMsg());
		}
	}

	@ResponseBody
	@RequestMapping("/listTree")
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
