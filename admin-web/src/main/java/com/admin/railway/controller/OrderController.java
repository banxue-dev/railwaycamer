package com.admin.railway.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.admin.common.annotation.Log;
import com.admin.common.config.Constant;
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
import com.admin.common.utils.QueryParam;
import com.admin.common.utils.R;
import com.admin.common.utils.ShiroUtils;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.StationDO;
import com.admin.railway.service.OrderService;
import com.admin.railway.service.PersonService;
import com.admin.railway.service.StationService;
import com.admin.system.domain.UserDO;

@Controller
@RequestMapping("/railway/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private PersonService personService;
	@Autowired
	private StationService stationService;
	
	/**
	 * 返回任务调度页面
	 * @return
	 */
	@RequiresPermissions("railway:order:list")
	@GetMapping("/list")
	public String listUI() {
		return "railway/order/list";
	}
	
	/**
	 * 返回任务调度界面列表数据
	 * @param stationId
	 * @return
	 */
	@RequiresPermissions("railway:order:list")
	@GetMapping("/listData")
	@ResponseBody
	public R list(@RequestParam Map<String, Object> params) {
		QueryParam query = new QueryParam(params);
		query.put("delState", Constants.NO); // 查询未删除
		
		List<OrderDO> list = new ArrayList<>();
		int count = orderService.count(query);
		if (count > 0) {
			list = orderService.list(query);
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("data", list);
		result.put("count", count);
		return R.ok(result);
	}
	
	/**
	 * 返回添加页面
	 * @return
	 */
	@RequiresPermissions("railway:order:add")
	@GetMapping("/add")
	public String addUI() {
		return "railway/order/add";
	}
	
	/**
	 * 返回拍照人员-添加
	 * @return
	 */
	@Log("新增调度任务")
	@RequiresPermissions("railway:order:add")
	@PostMapping("/add")
	@ResponseBody
	public R add(OrderDO order) {
		UserDO user = ShiroUtils.getUser();
		order.setDelState(Constants.NO);
		//验证车厢号是否重复添加
		OrderDO o = orderService.getOrder(order);
		if(o != null){
			return R.error(Constant.ErrorInfo.ADD_TASK_EXIST_TRANIN.getCode(),Constant.ErrorInfo.ADD_TASK_EXIST_TRANIN.getMsg());
		}
		// 根据前端传的 personIds 查找用户名
		String personIds = order.getPersonIds();
		if(StringUtils.isNoneBlank(personIds)){
			List<Long> ids = Arrays.stream(personIds.split(","))
					.map(s -> Long.parseLong(s.trim()))
					.collect(Collectors.toList());
			List<PersonDO> personList = personService.getByIds(ids);
			
			String personNames = personList.stream().map(PersonDO::getName).collect(Collectors.joining(","));
			order.setPersonNames(personNames);
		}
		// 设置发站 到站名称
		StationDO startStation = stationService.get(order.getStartStationId());
		if (startStation != null) {
			order.setStartStationName(startStation.getName());
		}
		StationDO endStation = stationService.get(order.getEndStationId());
		if (endStation != null) {
			order.setEndStationName(endStation.getName());
		}
		order.setCreateTime(new Date());
		order.setCreateUser(user.getName());
		order.setContinueShot(Long.valueOf(Constant.Number.ZERO.getCode()));
		orderService.save(order);
		return R.ok();
	}
	
	/**
	 * 修改页面
	 * @return
	 */
	@RequiresPermissions("railway:order:edit")
	@GetMapping("/edit/{id}")
	public String editUI(@PathVariable("id") Long orderId, Map<String, Object> map) {
		OrderDO order = orderService.get(orderId);
		map.put("order", order);
		return "railway/order/edit";
	}
	
	/**
	 * 修改
	 * @return
	 */
	@Log("修改调度任务")
	@RequiresPermissions("railway:order:edit")
	@PostMapping("/update")
	@ResponseBody
	public R update(OrderDO order) {
		UserDO user = ShiroUtils.getUser();
		
		// 根据前端传的 personIds 查找用户名
		String personIds = order.getPersonIds();
		if(StringUtils.isNoneBlank(personIds)){
			List<Long> ids = Arrays.stream(personIds.split(","))
					.map(s -> Long.parseLong(s.trim()))
					.collect(Collectors.toList());
			List<PersonDO> personList = personService.getByIds(ids);
			
			String personNames = personList.stream().map(PersonDO::getName).collect(Collectors.joining(","));
			order.setPersonNames(personNames);
		}
		
		// 设置stationName
		StationDO station = null;
		if (order.getStartStationId() != null) {
			station = stationService.get(order.getStartStationId());
			order.setStartStationName(station.getName());
		}
		if (order.getEndStationId() != null) {
			station = stationService.get(order.getEndStationId());
			order.setEndStationName(station.getName());
		}

		order.setModifyTime(new Date());
		order.setModifyUser(user.getName());
		
		orderService.update(order);
		return R.ok();
	}
	
	/**
	 * 复制
	 * @param id
	 * @return
	 */
	@Log("复制调度任务")
	@RequiresPermissions("railway:order:copy")
	@PostMapping("/copy")
	@ResponseBody
	public R copy(Long id) {
		UserDO user = ShiroUtils.getUser();
		OrderDO order = orderService.get(id);
		order.setCreateTime(new Date());
		//验证车厢号是否重复添加
		OrderDO o = orderService.getOrder(order);
		if(o != null){
			return R.error(Constant.ErrorInfo.ADD_TASK_EXIST_TRANIN.getCode(),Constant.ErrorInfo.ADD_TASK_EXIST_TRANIN.getMsg());
		}
		order.setId(null);
		order.setCreateUser(user.getName());
		order.setModifyTime(null);
		order.setModifyUser(null);
		order.setContinueShot(id);
		orderService.save(order);
		return R.ok();
	}
}
