package com.admin.railway.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.admin.common.config.Constant;
import com.admin.common.domain.Tree;
import com.admin.common.exception.BDException;
import com.admin.common.utils.BuildTree;
import com.admin.common.utils.DateUtils;
import com.admin.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.railway.dao.OrderDao;
import com.admin.railway.dao.StationDao;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.StationDO;
import com.admin.railway.service.StationService;
import com.admin.railway.utils.TargetTime;

@Transactional
@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationDao stationMapper;
    @Autowired
    private OrderDao orderDao;

	@Override
	public boolean save(StationDO station, UserDO user) {
		station.setCreateTime(new Date());
		station.setCreateUser(String.valueOf(user.getUserId()));
		int i = stationMapper.save(station);
		if(i>0){
			return true;
		}else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean update(StationDO station, UserDO user) {
		//验证是否有子节点
		Map<String,Object> map = new HashMap<>();
		map.put(StationDO.PARENT_ID,station.getId());
		List<StationDO> list = stationMapper.list(map);
		if(list.size() > Constant.Number.ZERO.getCode()){
			this.updateState(list,station.getDelState(),station.getId(),user);
		}
		station.setModifyTime(new Date());
		station.setModifyUser(String.valueOf(user.getUserId()));
		int i = stationMapper.update(station);
		if(i>0){
			return true;
		}else {
			return false;
		}
	}

	private void updateState(List<StationDO> list,Integer delState,Long pId, UserDO user){
		//根据父级节点修改状态
		Map<String,Object> map = new HashMap<>();
		map.put(StationDO.PARENT_ID,pId);
		map.put(StationDO.DEL_STATE,delState);
		map.put(StationDO.MODIFY_TIME,new Date());
		map.put(StationDO.MODIFY_USER,user.getUserId());
		stationMapper.updateState(map);
		for(StationDO station : list){
			Map<String,Object> nodeMap = new HashMap<>();
			nodeMap.put(StationDO.PARENT_ID,station.getId());
			List<StationDO> nodeList = stationMapper.list(nodeMap);
			if(nodeList.size() > Constant.Number.ZERO.getCode()){
				this.updateState(nodeList,delState,station.getId(),user);
			}
		}
	}

	@Override
	public boolean remove(Long stationId) {
		//查询是否存在子节点
		Map<String,Object> nodeMap = new HashMap<>();
		nodeMap.put(StationDO.PARENT_ID,stationId);
		List<StationDO> nodeList = stationMapper.list(nodeMap);
		if(nodeList.size() > Constant.Number.ZERO.getCode()){
			throw new BDException(Constant.ErrorInfo.EXIST_STATION_DEL_FAIL.getCode(),Constant.ErrorInfo.EXIST_STATION_DEL_FAIL.getMsg());
		}else {
			int i = stationMapper.remove(stationId);
			if(i>0){
				return true;
			}else {
				return false;
			}
		}
	}

	@Override
	public void batchRemove(Long[] stationIds) {
		stationMapper.batchRemove(stationIds);
	}

	@Override
	public StationDO get(Long stationId) {
		return stationMapper.get(stationId);
	}

	@Override
	public List<StationDO> list(Map<String, Object> map) {
		return stationMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return stationMapper.count(map);
	}

	@Override
	public List<Tree<StationDO>> getStationByTree(Map<String, Object> map) {
		// TODO 此处为方法主题
		map.put("sort", "id");
		map.put("order", "asc");
		List<Tree<StationDO>> trees = new ArrayList<Tree<StationDO>>();
		List<StationDO> StationDOs = stationMapper.list(map);
		for (StationDO sysStationDO : StationDOs) {
			Tree<StationDO> tree = new Tree<StationDO>();
			tree.setId(sysStationDO.getId().toString());
			tree.setParentId(sysStationDO.getParentId().toString());
			tree.setText(sysStationDO.getName());
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("name", sysStationDO.getName());
			attributes.put("id", sysStationDO.getId());
			tree.setAttributes(attributes);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<StationDO>> list = BuildTree.buildList(trees, "0");
		return list;
	}
	/**
	 * map查询条件
	 */
	@Override
	public List<Tree<String>> getStationTimeByTree(Map<String, Object> map) {
		List<Tree<String>> trees = new ArrayList<Tree<String>>();
		Long stationId=(Long) map.get("stationId");
		Integer nowsize= (Integer) map.get("nowsize");//从1开始，表示第几页
		Integer pagesize= (Integer) map.get("pagesize");//从每次多少，相当于每页多少条
		Date now=new Date();
		Map<String, Object> timeMap=new HashMap<String, Object>();
		timeMap.put("offset", nowsize);//从0开始
		timeMap.put("limit", pagesize);
		timeMap.put("startStationId", stationId);
		/**
		 * 用来查询指定站点下有那些时间的车
		 */
		List<OrderDO>  timeorders=orderDao.gettimelist(timeMap);
		if(timeorders==null || timeorders.size()<1) {
			return null;
		}else {
			for(int i=0;i<timeorders.size();i++) {
				OrderDO order=timeorders.get(i);
				String date=DateUtils.format(order.getCreateTime(), DateUtils.DATE_PATTERN);
				/**
				 * 时间的顶节点
				 */
				Tree<String> tree = new Tree<String>();
				tree.setId(date);
				tree.setParentId("0");
				tree.setText(date);
				Map<String, Object> attributes = new HashMap<>(16);
				attributes.put("orderid", order.getId());
				tree.setAttributes(attributes);
				trees.add(tree);
				
				String sdate=date +" 00:00:00";
				String edate=date +" 23:59:59";
				Map<String, Object> maporder=new HashMap<String, Object>();
				maporder.put("beginTime", sdate);
				maporder.put("endTime", edate);
				maporder.put("startStationId", stationId);

				List<OrderDO>  orders=orderDao.list(maporder);
				if(orders==null || orders.size()<1) {
					Tree<String> tree2 = new Tree<String>();
					tree2.setId(i+"");
					tree2.setParentId(date);
					tree2.setText("暂无数据");
					Map<String, Object> attributes2 = new HashMap<>(16);
					attributes2.put("orderid", "0");
					attributes2.put("trinno", "暂无数据");
					tree2.setAttributes(attributes2);
					trees.add(tree2);
				}else {
					for(OrderDO temp:orders) {
						Tree<String> tree1 = new Tree<String>();
						tree1.setId(temp.getId()+"");
						tree1.setParentId(date);
						tree1.setText("车号："+temp.getTrainNo());
						Map<String, Object> attributes1 = new HashMap<>(16);
						attributes1.put("orderid", temp.getId().toString());
						attributes1.put("trinno", temp.getTrainNo());
						attributes1.put("ptime", date);
						attributes1.put("productName", temp.getProductName());
						attributes1.put("trainType", temp.getTrainType());
						attributes1.put("loadingLine", temp.getLoadingLine());
						tree1.setAttributes(attributes1);
						trees.add(tree1);
					}
				}
				
			}
		}
		
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<String>> list = BuildTree.buildList(trees, "0");
		return list;
	}
	
}
