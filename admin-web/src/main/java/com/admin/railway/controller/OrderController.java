package com.admin.railway.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.admin.common.utils.QueryParam;
import com.admin.common.utils.R;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.service.OrderService;
import com.admin.railway.service.PersonService;

@Controller
@RequestMapping("/railway/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;
	
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
		
		order.setDelState(Constants.NO);
		order.setCreateTime(new Date());
		order.setCreateUser("系统");
		
		orderService.save(order);
		return R.ok();
	}
}
