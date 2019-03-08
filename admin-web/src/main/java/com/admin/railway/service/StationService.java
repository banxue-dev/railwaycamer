package com.admin.railway.service;

import java.util.List;
import java.util.Map;

import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.StationDO;

public interface StationService {
	
	void save(StationDO station);

	void update(StationDO station);
	
	void remove(Long stationId);
	
	void batchRemove(Long[] stationIds);
	
	StationDO get(Long stationId);

	List<StationDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

}