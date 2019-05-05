package com.admin.railway.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.admin.common.annotation.Log;
import com.admin.common.config.Constant;
import com.admin.common.controller.BaseController;
import com.admin.common.domain.Tree;
import com.admin.common.utils.Constants;
import com.admin.common.utils.QueryParam;
import com.admin.common.utils.R;
import com.admin.common.utils.StringUtils;
import com.admin.railway.domain.StationDO;
import com.admin.railway.service.StationService;
import com.admin.system.domain.UserDO;

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
	public String station(HttpServletRequest request){
		UserDO user=getUser();
		if(user.getUserStationIds()==null || user.getUserStationIds().size()<1) {
		}else {
			String temp="";
			for(Long str:user.getUserStationIds()) {
				temp+=str+",";
			}
			request.setAttribute("quanxian", temp.substring(0,temp.lastIndexOf(",")));
		}
		return "railway/station/station";
	}

	@RequiresPermissions("railway:station:station")
	@ResponseBody
	@GetMapping("/list")
	public List<StationDO> list(@RequestParam Map<String, Object> map){
		UserDO user=getUser();
		if(user.getUserStationIds()==null || user.getUserStationIds().size()<1) {
		}else {
			map.put("stationIds_", user.getUserStationIds());
		}
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
			UserDO user=getUser();
			if(user.getDeptId()==null || user.getDeptId()==-1L) {
				user.setUserStationIds(null);
			}else {
				Map<String, Object> map=new HashMap<String,Object>();
				map.put("type", 1);
				List<StationDO> sds=stationService.list(map);
				StationDO sta=stationService.get(user.getDeptId());
				user.setIsBootom(sta.getIsBottom()==0?false:true);
				Long stations=user.getDeptId();
				List<Long> ids=new ArrayList<Long>();
				List<Long> idst=new ArrayList<Long>();
				idst=getIds(stations,ids,sds);
				idst.add(stations);
				user.setUserStationIds(idst);
				System.out.println("拥有的权限"+idst.toString());
			}
			return R.ok(Constant.SuccessInfo.ADD_SUCCESS.getMsg());
		}else {
			return R.error(Constant.ErrorInfo.ADD_FAIL.getCode(),Constant.ErrorInfo.ADD_FAIL.getMsg());
		}
	}
	/**
	 * 下钻
	 * @param id
	 * @param ids
	 * @param sds
	 * @return
	 * 2019年4月29日
	 * 作者：fengchase
	 */
	public List<Long>  getIds(Long id,List<Long> ids,List<StationDO> sds) {
		List<StationDO> result=getChildres(sds,id);
		if(result!=null && result.size()>0) {
			for(StationDO te:result) {
				if(ids.indexOf(te.getId())==-1) {
					ids.add(te.getId());
				}
				getIds(te.getId(),ids,sds);
			}
		}else {
			if(ids.indexOf(id)==-1) {
				ids.add(id);
			}
		}
		return ids;
	}
	public List<StationDO> getChildres(List<StationDO> sds,Long id) {
		List<StationDO> result=new ArrayList<StationDO>();
		for(StationDO sd:sds) {
			if(sd.getParentId()==id || sd.getParentId().equals(id)) {
				result.add(sd);
			}
		}
		return result;
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
			UserDO user=getUser();
			if(user.getUserStationIds()==null || user.getUserStationIds().size()<1) {
			}else {
				params.put("stationIds_", user.getUserStationIds());
			}
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
