package com.admin.railway.service;

import java.util.List;
import java.util.Map;

import com.admin.railway.domain.PictureDO;

public interface PictureService {
	
	boolean save(PictureDO picture);

	void update(PictureDO picture);
	
	void remove(Long pictureId);

	boolean batchRemove(Long[] pictureIds);
	
	PictureDO get(Long pictureId);

	List<PictureDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

}
