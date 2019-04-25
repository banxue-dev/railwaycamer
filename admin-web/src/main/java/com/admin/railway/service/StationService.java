package com.admin.railway.service;

import java.util.List;
import java.util.Map;

import com.admin.common.domain.Tree;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.StationDO;
import com.admin.system.domain.MenuDO;
import com.admin.system.domain.UserDO;

public interface StationService {
	
	boolean save(StationDO station, UserDO user);

	boolean update(StationDO station, UserDO user);
	
	boolean remove(Long stationId);
	
	void batchRemove(Long[] stationIds);
	
	StationDO get(Long stationId);
	List<StationDO> getByParentId(Long parentId);

	List<StationDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	List<Tree<StationDO>> getStationByTree(Map<String, Object> map);



	List<Tree<String>> getStationTimeByTree(Map<String, Object> map);

}
