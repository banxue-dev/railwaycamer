package com.admin.railway.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.StationDO;
import com.admin.railway.service.OrderService;
import com.admin.railway.service.PersonService;
import com.admin.railway.service.StationService;

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
	@GetMapping("/list")
	public String listUI() {
		return "railway/order/list";
	}
	
	/**
	 * 返回任务调度界面列表数据
	 * @param stationId
	 * @return
	 */
	@PostMapping("/list")
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
	@GetMapping("/add")
	public String addUI() {
		return "railway/order/add";
	}
	
	/**
	 * 返回拍照人员-添加
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public R add(OrderDO order) {
		
		// 根据前端传的 personIds 查找用户名
		String personIds = order.getPersonIds();
		List<Long> ids = Arrays.stream(personIds.split(","))
				.map(s -> Long.parseLong(s.trim()))
				.collect(Collectors.toList());
		List<PersonDO> personList = personService.getByIds(ids);
		
		String personNames = personList.stream().map(PersonDO::getName).collect(Collectors.joining(","));
		order.setPersonNames(personNames);
		
		// 设置发站 到站名称
		StationDO startStation = stationService.get(order.getStartStationId());
		if (startStation != null) {
			order.setStartStationName(startStation.getName());
		}
		StationDO endStation = stationService.get(order.getEndStationId());
		if (endStation != null) {
			order.setEndStationName(endStation.getName());
		}
		
		// 检查 续拍是否设置:没有设置设置默认值 0
		if (order.getContinueShot() == null) {
			order.setContinueShot(Constants.NO);
		}
		
		order.setDelState(Constants.NO);
		order.setCreateTime(new Date());
		order.setCreateUser("系统");
		
		orderService.save(order);
		return R.ok();
	}
	
	/**
	 * 修改页面
	 * @return
	 */
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
	@PostMapping("/update")
	@ResponseBody
	public R update(OrderDO order) {
		
		// 根据前端传的 personIds 查找用户名
		String personIds = order.getPersonIds();
		List<Long> ids = Arrays.stream(personIds.split(","))
				.map(s -> Long.parseLong(s.trim()))
				.collect(Collectors.toList());
		List<PersonDO> personList = personService.getByIds(ids);
		
		String personNames = personList.stream().map(PersonDO::getName).collect(Collectors.joining(","));
		order.setPersonNames(personNames);
		
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
		// 检查 续拍是否设置:没有设置设置默认值 0
		if (order.getContinueShot() == null) {
			order.setContinueShot(Constants.NO);
		}
		
		order.setModifyTime(new Date());
		order.setModifyUser("系统");
		
		orderService.update(order);
		return R.ok();
	}
	
	/**
	 * 复制
	 * @param id
	 * @return
	 */
	@PostMapping("/copy")
	@ResponseBody
	public R copy(Long id) {
		OrderDO order = orderService.get(id);
		order.setId(null);
		order.setCreateTime(new Date());
		order.setCreateUser("系统");
		order.setModifyTime(null);
		order.setModifyUser(null);
		orderService.save(order);
		
		return R.ok();
	}
}
