package com.admin.railway.service.impl;

import com.admin.common.config.Constant;
import com.admin.common.utils.Constants;
import com.admin.common.utils.QueryParam;
import com.admin.common.utils.R;
import com.admin.railway.dao.PhotoManageDao;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PictureDO;
import com.admin.railway.service.OrderService;
import com.admin.railway.service.PhotoManageService;
import com.admin.railway.service.PictureService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName: PhotoManageServiceImpl
 * @Description: 照片管理
 * @Author: luojing
 * @Date: 2019/3/14 10:25
 */
@Service
public class PhotoManageServiceImpl implements PhotoManageService {

    @Autowired
    private PhotoManageDao photoManageDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PictureService pictureService;

    @Override
    public R selectPagePhoto(Map<String, Object> map) {
        QueryParam query = new QueryParam(map);
        // 查询未删除
        query.put(OrderDO.DEL_STATE, Constants.NO);
        Map<String, Object> result = new HashMap<>();
        Integer count = orderService.count(query);
        if (count > 0) {
            List<Map<String, Object>> resultList = photoManageDao.list(query);
            result.put(Constant.DATA, resultList);
            result.put(Constant.COUNT, count);
        }
        return R.ok(result);
    }

    @Override
    public boolean deletePic(Long[] ids) {
        return pictureService.batchRemove(ids);
    }

    private List<PictureDO> listPic(Long id) {
        List<PictureDO> list = new ArrayList<>();
        //查询是否存在续拍
        OrderDO order = orderService.get(id);
        if(order != null){
            //不为0时续拍
            if (order.getContinueShot().intValue() != Constant.Number.ZERO.getCode()) {
                Map<String, Object> map = new HashMap<>();
                map.put(PictureDO.ORDER_ID, order.getContinueShot());
                list.addAll(pictureService.list(map));
                this.listPic(order.getContinueShot());
            }
        }
        return list;
    }

    @Override
    public void exportPic(Long id, HttpServletResponse response) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put(PictureDO.ORDER_ID, id);
            List<PictureDO> listPic = pictureService.list(map);
            listPic.addAll(this.listPic(id));
            if(!listPic.isEmpty()){
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ZipOutputStream zip = new ZipOutputStream(outputStream);
                for (PictureDO pic : listPic) {
                    File file = new File(pic.getUrl());
                    FileInputStream zipSource = new FileInputStream(file);
                    byte[] bytes = new byte[1024 * 20];
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zip.putNextEntry(zipEntry);
                    BufferedInputStream bufferStream = new BufferedInputStream(zipSource, 1024 * 20);
                    int read = 0;
                    while ((read = bufferStream.read(bytes, 0, 1024 * 20)) != -1) {
                        zip.write(bytes, 0, read);
                    }
                }
                IOUtils.closeQuietly(zip);
                byte[] bytes = outputStream.toByteArray();
                response.reset();
                response.setHeader("Content-Disposition", "attachment; filename=\"photo.zip\"");
                response.addHeader("Content-Length", "" + bytes.length);
                response.setContentType("application/octet-stream; charset=UTF-8");
                IOUtils.write(bytes, response.getOutputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PictureDO> listPicture(Map<String, Object> map) {
        Long id = Long.valueOf(map.get("orderId").toString());
        List<PictureDO> listPic = pictureService.list(map);
        listPic.addAll(this.listPic(id));
        return listPic;
    }
}
