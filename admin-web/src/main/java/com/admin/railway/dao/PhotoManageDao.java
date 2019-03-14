package com.admin.railway.dao;

import com.admin.railway.domain.OrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: PhotoManageServiceImpl
 * @Description: 照片管理
 * @Author: luojing
 * @Date: 2019/3/14 10:25
 */
@Mapper
public interface PhotoManageDao {

    List<Map<String,Object>> list(Map<String, Object> map);

}
