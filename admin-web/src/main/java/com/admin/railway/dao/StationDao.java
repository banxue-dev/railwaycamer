package com.admin.railway.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.admin.railway.domain.StationDO;

@Mapper
public interface StationDao {

	StationDO get(Long id);
	
	List<StationDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);

	int save(StationDO station);

	int update(StationDO station);
	
	int remove(Long stationId);
	
	int batchRemove(Long[] stationIds);

	int updateState(Map<String,Object> map);
	StationDO getByParentId(Long parentId) ;
}
