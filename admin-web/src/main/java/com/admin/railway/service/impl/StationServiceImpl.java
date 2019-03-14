package com.admin.railway.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.admin.common.domain.Tree;
import com.admin.common.utils.BuildTree;
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
	public void save(StationDO station) {
		stationMapper.save(station);;
	}

	@Override
	public void update(StationDO station) {
		stationMapper.update(station);
	}

	@Override
	public void remove(Long stationId) {
		stationMapper.remove(stationId);
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
		int start=(nowsize-1)*pagesize+1;
		Date now=new Date();
		for(int i=start;i<=pagesize*nowsize;i++) {
			String date=TargetTime.getBeforeOrAfterDateReString(now , 1-i);
			String sdate=date+" 00:00:00";
			String edate=date+" 23:59:59";
			Map<String, Object> maporder=new HashMap<String, Object>();
			maporder.put("beginTime", sdate);
			maporder.put("endTime", edate);
			maporder.put("startStationId", stationId);
			Tree<String> tree = new Tree<String>();
			tree.setId(date);
			tree.setParentId(stationId.toString());
			tree.setText(date);
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("trinno", "");
			tree.setAttributes(attributes);
			trees.add(tree);
			List<OrderDO>  orders=orderDao.list(maporder);
			for(OrderDO temp:orders) {
				Tree<String> tree1 = new Tree<String>();
				tree1.setId(i+"");
				tree1.setParentId(date);
				tree1.setText("车号："+temp.getTrainNo());
				Map<String, Object> attributes1 = new HashMap<>(16);
				attributes1.put("ttt", "11");
				attributes.put("trinno", temp.getTrainNo());
				tree.setAttributes(attributes1);
				trees.add(tree1);
			}
			
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<String>> list = BuildTree.buildList(trees, "0");
		return list;
	}
	
}
