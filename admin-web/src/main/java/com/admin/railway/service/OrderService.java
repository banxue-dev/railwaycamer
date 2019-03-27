package com.admin.railway.service;

import java.util.List;
import java.util.Map;

import com.admin.common.domain.PageDO;
import com.admin.railway.domain.OrderDO;
import org.springframework.core.annotation.Order;

public interface OrderService {
	
	void save(OrderDO order);

	void update(OrderDO order);
	
	void remove(Long orderId);
	
	void batchRemove(Long[] orderIds);
	
	OrderDO get(Long orderId);

	List<OrderDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	/**
	 * @Author: luojing
	 * @Description: 根据拍照人员查询拍照任务
	 * @Param: [personId]
	 * @Return: list
	 * @Date: 2019/3/10 14:51
	 **/
	List<Map<String, Object>> listTask(Map<String, Object> map);

	/**
	 * @Author: luojing
	 * @Description: 查询任务信息
	 * @Param: [order]
	 * @Return: com.admin.railway.domain.OrderDO
	 * @Date: 2019/3/17 14:24
	 **/
	OrderDO getOrder(OrderDO order);

}
