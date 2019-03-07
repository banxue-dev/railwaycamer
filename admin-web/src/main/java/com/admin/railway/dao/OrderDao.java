package com.admin.railway.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.StationDO;

@Mapper
public interface OrderDao {

	OrderDO get(Long personId);
	
	List<OrderDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);

	int save(OrderDO person);

	int update(OrderDO person);
	
	int remove(Long orderId);
	
	int batchRemove(Long[] orderIds);
	
}
