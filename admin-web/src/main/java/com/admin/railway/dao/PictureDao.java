package com.admin.railway.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PictureDO;
import com.admin.railway.domain.StationDO;

@Mapper
public interface PictureDao {

	PictureDO get(Long pictureId);
	
	List<PictureDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);

	int save(PictureDO picture);

	int update(PictureDO picture);
	
	int remove(Long pictureId);
	
	int batchRemove(Long[] pictureIds);
	
}
