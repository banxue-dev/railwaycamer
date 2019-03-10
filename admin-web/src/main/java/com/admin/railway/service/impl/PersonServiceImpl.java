package com.admin.railway.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.admin.common.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.railway.dao.PersonDao;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.service.PersonService;

@Transactional
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDao personMapper;

	@Override
	public void save(PersonDO person) {
		personMapper.save(person);
	}

	@Override
	public void update(PersonDO person) {
		personMapper.update(person);
	}

	@Override
	public void remove(Long personId) {
		personMapper.remove(personId);
	}

	@Override
	public void batchRemove(Long[] personIds) {
		personMapper.batchRemove(personIds);
	}

	@Override
	public PersonDO get(Long personId) {
		return personMapper.get(personId);
	}

	@Override
	public PersonDO getPerson(Map<String, Object> map) {
		return personMapper.getPerson(map);
	}

	@Override
	public List<PersonDO> list(Map<String, Object> map) {
		return personMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return personMapper.count(map);
	}

}
