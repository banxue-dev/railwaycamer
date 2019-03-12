package com.admin.railway.service.impl;

import com.admin.common.config.AdminConfig;
import com.admin.common.config.Constant;
import com.admin.common.utils.*;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.PictureDO;
import com.admin.railway.domain.vo.LoginVo;
import com.admin.railway.domain.vo.UploadImgVo;
import com.admin.railway.service.ApiService;
import com.admin.railway.service.OrderService;
import com.admin.railway.service.PersonService;
import com.admin.railway.service.PictureService;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ApiServiceImpl
 * @Description: AppServie
 * @Author: luojing
 * @Date: 2019/3/9 9:54
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private PersonService personService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PictureService pictureService;

    @Override
    public R login(LoginVo vo) {
        //验证
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", vo.getLoginName());
        Map<String, Object> personMap = personService.login(map);
        if (personMap.isEmpty()) {
            return R.error(Constant.ErrorInfo.USER_NOT_EXIST.getCode(), Constant.ErrorInfo.USER_NOT_EXIST.getMsg());
        }
        //验证密码
        String password = MD5Utils.encrypt(vo.getLoginName(), vo.getPassword());
        String personPwd = personMap.get("password").toString();
        if (!password.equals(personPwd)) {
            return R.error(Constant.ErrorInfo.PASSWORD_ERROR.getCode(), Constant.ErrorInfo.PASSWORD_ERROR.getMsg());
        }
        R r = new R();
        r.put("person",personMap);
        r.put("token","");
        return r;
    }

    @Override
    public R uploadImg(UploadImgVo vo, MultipartFile file) {
        //查询拍照人信息
        PersonDO personDO = personService.get(Long.valueOf(vo.getPersonId()));
        if(personDO == null){
            return R.error(Constant.ErrorInfo.PERSION_NULL.getCode(), Constant.ErrorInfo.PERSION_NULL.getMsg());
        }
        //上传地址路径
        StringBuffer sbUrl = new StringBuffer(adminConfig.getUploadPath());
        //站点
        sbUrl.append(personDO.getStationId() + "/");
        //人
        sbUrl.append(vo.getPersonId() + "/");
        //时间
        sbUrl.append(DateUtils.format(new Date(), "yyyyMMdd") + "/");
        //车厢
        sbUrl.append(vo.getTrainNo() + "/");
        try {
            //
            String fileName = DateUtils.format(new Date(), "yyyyMMddHHmmsss") + ".jpg";
            FileUtil.uploadFile(file.getBytes(), sbUrl.toString(), fileName);
            //保存缩略图
            String thumUrl = ImageUtil.thumbnail(file, sbUrl.toString(), fileName);
            if (StringUtils.isBlank(vo.getTaskId())) {
                //为空时代表拍照人员主动上传照片,添加任务信息
                OrderDO orderDO = new OrderDO();
                orderDO.setTrainNo(vo.getTrainNo());
                orderDO.setCreateTime(new Date());
                orderDO.setPersonIds(vo.getPersonId());
                orderService.save(orderDO);
                vo.setTaskId(String.valueOf(orderDO.getId()));
            }
            //添加图片信息
            PictureDO pictureDO = new PictureDO();
            pictureDO.setOrderId(Long.valueOf(vo.getTaskId()));
            pictureDO.setPersonId(Long.valueOf(vo.getPersonId()));
            pictureDO.setTrainNo(vo.getTrainNo());
            pictureDO.setThumUrl(thumUrl);
            pictureDO.setUrl(sbUrl + fileName);
            pictureDO.setDelState(0);
            pictureDO.setCreateTime(new Date());
            pictureDO.setCreateUser(vo.getPersonId());
            boolean fag = pictureService.save(pictureDO);
            if (fag) {
                return R.ok();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(Constant.ErrorInfo.IMAGE_UPLOAD_FAIL.getCode(), e.getMessage());
        }
        return R.error(Constant.ErrorInfo.IMAGE_UPLOAD_FAIL.getCode(), Constant.ErrorInfo.IMAGE_UPLOAD_FAIL.getMsg());
    }

    @Override
    public R listTask(String personId) {
        List<Map<String,Object>> map = orderService.listTask(personId);
        R r = new R();
        r.put("taskList",map);
        return r;
    }
}

