package com.admin.railway.service.impl;

import java.util.*;

import com.admin.common.config.Constant;
import com.admin.common.domain.Tree;
import com.admin.common.exception.BDException;
import com.admin.common.utils.BuildTree;
import com.admin.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.railway.dao.StationDao;
import com.admin.railway.domain.StationDO;
import com.admin.railway.service.StationService;

@Transactional
@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationDao stationMapper;

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
	
}
