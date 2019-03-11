package com.admin.railway.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.admin.railway.domain.PersonDO;

@Mapper
public interface PersonDao {

	PersonDO get(Long personId);
	
	List<PersonDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);

	int save(PersonDO person);

	int update(PersonDO person);
	
	int remove(Long personId);
	
	int batchRemove(Long[] personIds);

	Map<String, Object> login(Map<String, Object> map);
	
}
