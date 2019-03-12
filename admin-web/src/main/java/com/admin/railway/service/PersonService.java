package com.admin.railway.service;

import java.util.List;
import java.util.Map;

import com.admin.railway.domain.PersonDO;

public interface PersonService {
	
	void save(PersonDO person);

	void update(PersonDO person);
	
	void remove(Long personId);
	
	void batchRemove(Long[] personIds);
	
	PersonDO get(Long personId);

	Map<String, Object> login(Map<String, Object> map);

	List<PersonDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	List<PersonDO> getByIds(List<Long> personIds);

}
