package com.admin.railway.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.admin.railway.domain.StationDO;

@Mapper
public interface StationDao {

	StationDO get(Long personId);
	
	List<StationDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);

	int save(StationDO person);

	int update(StationDO person);
	
	int remove(Long stationId);
	
	int batchRemove(Long[] stationIds);
	
}
