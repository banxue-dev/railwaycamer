package com.admin.railway.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.StationDO;
import com.admin.railway.service.StationService;

@Transactional
@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationService stationMapper;

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
	
}
