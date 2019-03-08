package com.admin.railway.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.admin.railway.domain.OrderDO;

@Mapper
public interface OrderDao {

	OrderDO get(Long orderId);
	
	List<OrderDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);

	int save(OrderDO order);

	int update(OrderDO order);
	
	int remove(Long orderId);
	
	int batchRemove(Long[] orderIds);
	
}
