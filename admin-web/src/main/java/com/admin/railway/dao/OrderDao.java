package com.admin.railway.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.admin.railway.domain.OrderDO;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

	OrderDO get(Long orderId);
	
	List<OrderDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);

	int save(OrderDO order);

	int update(OrderDO order);
	
	int remove(Long orderId);
	
	int batchRemove(Long[] orderIds);
	/**
	 * @Author: luojing
	 * @Description: 根据拍照人员查询拍照任务
	 * @Param: [personId]
	 * @Return: list
	 * @Date: 2019/3/10 14:51
	 **/
	List<OrderDO> listTask(@Param("personId") String personId);
}
