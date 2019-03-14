package com.admin.railway.service;

import com.admin.common.domain.PageDO;
import com.admin.common.utils.R;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PictureDO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PhotoManageServiceImpl
 * @Description: 照片管理
 * @Author: luojing
 * @Date: 2019/3/14 10:25
 */
public interface PhotoManageService {

    /**
     * @Author: luojing
     * @Description: 分页查询
     * @Param: [map]
     * @Return: PageDO<OrderDO>
     * @Date: 2019/3/14 10:27
     **/
    R selectPagePhoto(Map<String,Object> map);

    /**
     * @Author: luojing
     * @Description: 删除图片
     * @Param: [ids]
     * @Return: boolean
     * @Date: 2019/3/14 20:10
     **/
    boolean deletePic(Long [] ids);

    /**
     * @Author: luojing
     * @Description: 导出图片
     * @Param: [id]
     * @Return: void
     * @Date: 2019/3/14 21:02
     **/
    void exportPic(Long id, HttpServletResponse response);


    /**
     * @Author: luojing
     * @Description: 查询图片列表
     * @Param: [map]
     * @Return: java.util.List<com.admin.railway.domain.PictureDO>
     * @Date: 2019/3/14 21:05
     **/
    List<PictureDO> listPicture(Map<String, Object> map);

}
