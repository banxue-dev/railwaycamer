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
	
	/**
	 * 获取根据时间分组，有最近有多少时间段内的数据
	 * 参数：分页数据limit,pagenow：从第几条开始,pagesize:每页大小
	 * @return
	 * 2019年3月21日
	 * 作者：fengchase
	 */
	List<OrderDO> gettimelist(Map<String, Object> map);
	
	int batchRemove(Long[] orderIds);
	/**
	 * @Author: luojing
	 * @Description: 根据拍照人员查询拍照任务
	 * @Param: [personId]
	 * @Return: list
	 * @Date: 2019/3/10 14:51
	 **/
	List<Map<String, Object>> listTask(Map<String, Object> param);

	/**
	 * @Author: luojing
	 * @Description: 查询任务信息
	 * @Param: [order]
	 * @Return: com.admin.railway.domain.OrderDO
	 * @Date: 2019/3/17 14:24
	 **/
	OrderDO getOrder(OrderDO order);
}
