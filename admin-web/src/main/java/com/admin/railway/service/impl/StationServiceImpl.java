package com.admin.railway.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.admin.common.domain.Tree;
import com.admin.common.utils.BuildTree;
import com.admin.common.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.railway.dao.StationDao;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.StationDO;
import com.admin.railway.service.StationService;

@Transactional
@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationDao stationMapper;

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
			tree.setAttributes(attributes);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<StationDO>> list = BuildTree.buildList(trees, "0");
		return list;
	}
	
}
