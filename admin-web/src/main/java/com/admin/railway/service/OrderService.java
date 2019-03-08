package com.admin.railway.service;

import java.util.List;
import java.util.Map;

import com.admin.railway.domain.OrderDO;

public interface OrderService {
	
	void save(OrderDO order);

	void update(OrderDO order);
	
	void remove(Long orderId);
	
	void batchRemove(Long[] orderIds);
	
	OrderDO get(Long orderId);

	List<OrderDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

}
