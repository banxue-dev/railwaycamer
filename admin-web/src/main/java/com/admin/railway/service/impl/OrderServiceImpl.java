package com.admin.railway.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.admin.common.domain.PageDO;
import com.admin.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.railway.dao.OrderDao;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.service.OrderService;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderMapper;

	@Override
	public void save(OrderDO order) {
		orderMapper.save(order);
	}

	@Override
	public void update(OrderDO order) {
		order.setModifyTime(new Date());
		orderMapper.update(order);
	}

	@Override
	public void remove(Long orderId) {
		orderMapper.remove(orderId);
	}

	@Override
	public void batchRemove(Long[] orderIds) {
		orderMapper.batchRemove(orderIds);
	}

	@Override
	public OrderDO get(Long orderId) {
		return orderMapper.get(orderId);
	}

	@Override
	public List<OrderDO> list(Map<String, Object> map) {
		return orderMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return orderMapper.count(map);
	}

	@Override
	public List<Map<String, Object>> listTask(Map<String, Object> param) {
		return orderMapper.listTask(param);
	}

	@Override
	public OrderDO getOrder(OrderDO order) {
		order.setCreateTime(new Date());
		return orderMapper.getOrder(order);
	}

}
