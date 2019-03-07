package com.admin.railway.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PictureDO;
import com.admin.railway.domain.StationDO;

@Mapper
public interface PictureDao {

	PictureDO get(Long personId);
	
	List<PictureDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);

	int save(PictureDO person);

	int update(PictureDO person);
	
	int remove(Long pictureId);
	
	int batchRemove(Long[] pictureIds);
	
}
