package com.admin.railway.service.impl;

import java.util.List;
import java.util.Map;

import com.admin.common.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.railway.dao.PersonDao;
import com.admin.railway.dao.PictureDao;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.PictureDO;
import com.admin.railway.service.PersonService;
import com.admin.railway.service.PictureService;

@Transactional
@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureDao pictureMapper;

	@Override
	public boolean save(PictureDO picture) {
		Integer i = pictureMapper.save(picture);
		if(i > 0){
			return true;
		}
		return false;
	}

	@Override
	public void update(PictureDO picture) {
		pictureMapper.update(picture);
	}

	@Override
	public void remove(Long pictureId) {
		pictureMapper.remove(pictureId);
	}

	@Override
	public boolean batchRemove(Long[] pictureIds) {
		int i = pictureMapper.batchRemove(pictureIds);
		if(i > 0){
			return true;
		}
		return false;
	}

	@Override
	public PictureDO get(Long pictureId) {
		return pictureMapper.get(pictureId);
	}

	@Override
	public List<PictureDO> list(Map<String, Object> map) {
		return pictureMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return pictureMapper.count(map);
	}
	
}
