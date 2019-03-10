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
    PersonDao personMapper;

	@Override
	public void save(PersonDO person) {
		this.personMapper.save(person);
	}

	@Override
	public void update(PersonDO person) {
		this.personMapper.update(person);
	}

	@Override
	public void remove(Long personId) {
		this.personMapper.remove(personId);
	}

	@Override
	public void batchRemove(Long[] personIds) {
		this.personMapper.batchRemove(personIds);
	}

	@Override
	public PersonDO get(Long personId) {
		return this.personMapper.get(personId);
	}

	@Override
	public List<PersonDO> list(Map<String, Object> map) {
		return this.personMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return this.personMapper.count(map);
	}

}
