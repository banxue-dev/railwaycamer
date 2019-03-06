package com.admin.common.service;

import org.springframework.stereotype.Service;

import com.admin.common.domain.LogDO;
import com.admin.common.domain.PageDO;
import com.admin.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
