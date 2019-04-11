package com.admin.railway.controller;

import java.util.ArrayList;
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
import com.admin.common.utils.Constants;
import com.admin.common.utils.QueryParam;
import com.admin.common.utils.R;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.StationDO;
import com.admin.railway.service.StationService;

@Controller
@RequestMapping("/railway/station")
public class StationController extends BaseController {

	@Autowired
	private StationService stationService;
	
	@GetMapping("/endStation")
	public String endStation(){
		return "railway/endStation/list";
	}
	
	@RequiresPermissions("railway:station:station")
	@GetMapping("/endStation/selectList")
	public String selectEndStationList(){
		return "railway/endStation/selectList";
	}
	
	@RequiresPermissions("railway:station:station")
	@GetMapping("/endStation/listData")
	@ResponseBody
	public R endStationListData(@RequestParam Map<String, Object> params) {
		QueryParam query = new QueryParam(params);
		query.put("type", Constants.STATION_TYPE_END);
		List<StationDO> list = new ArrayList<>();
		int count = stationService.count(query);
		if (count > 0) {
			list = stationService.list(query);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("data", list);
		result.put("count", count);
		return R.ok(result);
	}
	
	@RequiresPermissions("railway:station:add")
	@GetMapping("/endStation/add")
	public String endStationAdd(){
		return "railway/endStation/add";
	}
	
	@RequiresPermissions("railway:station:edit")
	@GetMapping("/endStation/edit/{id}")
	public String endStationEdit(@PathVariable("id") Long id, Model model){
		StationDO station = stationService.get(id);
		model.addAttribute("station",station);
		return "railway/endStation/edit";
	}

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
		if(pId.intValue() == Constant.Number.ZERO.getCode()){
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
	public R getStationListByTree(@RequestParam Map<String, Object> params) {
		try {
			List<Tree<StationDO>> lst=stationService.getStationByTree(params);
			return R.okdata(lst);
		}catch(Exception e) {
			return R.error();
		}
	}
	@ResponseBody
	@RequestMapping("/listTimeTree")
	public R getStationListByTimeTree(Long stationId,Integer nowsize,Integer pagesize) {
		try {
			Map<String, Object> map=new HashMap<>();
			map.put("stationId", stationId);
			map.put("nowsize", nowsize);
			map.put("pagesize", pagesize);
			List<Tree<String>> lst=stationService.getStationTimeByTree(map);
			return R.okdata(lst);
		}catch(Exception e) {
			e.printStackTrace();
			return R.error();
		}
	}
	@RequestMapping("/leaderLook")
	public String leaderLook() {
		return "railway/leader/stationselect";
	}
}
