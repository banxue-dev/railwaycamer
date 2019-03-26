package com.admin.railway.service.impl;

import com.admin.common.aspect.WebLogAspect;
import com.admin.common.config.AdminConfig;
import com.admin.common.config.Constant;
import com.admin.common.utils.*;
import com.admin.railway.dao.LoginUserTokenDo;
import com.admin.railway.domain.LoginUserToken;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PersonDO;
import com.admin.railway.domain.PictureDO;
import com.admin.railway.domain.vo.LoginVo;
import com.admin.railway.domain.vo.PasswordVo;
import com.admin.railway.domain.vo.UploadImgVo;
import com.admin.railway.service.ApiService;
import com.admin.railway.service.OrderService;
import com.admin.railway.service.PersonService;
import com.admin.railway.service.PictureService;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Autowired
    private LoginUserTokenDo loginUserTokenDo;
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
        r.put(Constant.PERSION, personMap);
        //生成token
        String tokenStr = MD5Utils.encrypt(vo.getLoginName(), String.valueOf(System.currentTimeMillis()));
        String userId = personMap.get("id").toString();
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("usreId", userId);
        LoginUserToken token = loginUserTokenDo.getUserToken(tokenMap);
        if (token == null) {
            token = new LoginUserToken();
            token.setUserId(userId);
            token.setToken(tokenStr);
            token.setTimeOut(Constant.TOKEN_TIME_OUT);
            token.setTime(System.currentTimeMillis());
            loginUserTokenDo.save(token);
        } else {
            token.setToken(tokenStr);
            token.setTimeOut(Constant.TOKEN_TIME_OUT);
            token.setTime(System.currentTimeMillis());
            loginUserTokenDo.update(token);
        }
        r.put(Constant.TOKEN, tokenStr);
        return r;
    }

    @Override
    @Transactional
    public R uploadImg(UploadImgVo vo, MultipartFile[] files) {
        //查询拍照人信息
        PersonDO personDO = personService.get(Long.valueOf(vo.getPersonId()));
        if (personDO == null) {
            return R.error(Constant.ErrorInfo.PERSION_NULL.getCode(), Constant.ErrorInfo.PERSION_NULL.getMsg());
        }
        //上传地址路径
        StringBuffer sbUrl = new StringBuffer(adminConfig.getUploadPath());
        //时间
        sbUrl.append(DateUtils.format(new Date(), DateUtils.DATE_PATTERN) + "/");
        //车厢
        sbUrl.append(vo.getTrainNo() + "/");
        try {
            for (int i = 0; i < files.length; i++) {
                String fileName = UuidUtil.get16UUID() + ".jpg";
                FileUtil.uploadFile(files[i].getBytes(), sbUrl.toString(), fileName);
                //保存缩略图
                String thumUrl = ImageUtil.thumbnail(files[i], sbUrl.toString(), fileName);
                OrderDO orderDO = new OrderDO();
                orderDO.setTrainNo(vo.getTrainNo());
                orderDO.setCreateTime(new Date());
                if (StringUtils.isBlank(vo.getTaskId())) {
                    //查询
                    OrderDO o = orderService.getOrder(orderDO);
                    if (o != null) {
                        vo.setTaskId(String.valueOf(o.getId()));
                    } else {
                        //为空时代表拍照人员主动上传照片,添加任务信息
                        orderDO.setPersonIds(vo.getPersonId());
                        orderDO.setContinueShot(Long.valueOf(Constant.Number.ZERO.getCode()));
                        orderDO.setUploadWay(Constant.Number.ONE.getCode());
                        orderDO.setStartStationId(personDO.getStationId());
                        orderDO.setStartStationName(personDO.getStationName());
                        orderService.save(orderDO);
                        vo.setTaskId(String.valueOf(orderDO.getId()));
                    }
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
                pictureService.save(pictureDO);
            }
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(Constant.ErrorInfo.IMAGE_UPLOAD_FAIL.getCode(), e.getMessage());
        }
    }

    @Override
    public R listTask(String personId) {
        List<Map<String, Object>> map = orderService.listTask(personId);
        R r = new R();
        r.put("taskList", map);
        return r;
    }

    @Override
    public R getToken(String token) {
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        LoginUserToken userToken = loginUserTokenDo.getUserToken(tokenMap);
        if (userToken != null) {
            //计算时间是否过期
            long loginTime = userToken.getTime();
            //当前时间
            long curTime = System.currentTimeMillis();
            long time = (curTime - loginTime) / 1000;
            if (time > userToken.getTimeOut()) {
                return R.error(Constant.ErrorInfo.LOGIN_TIME_OUT.getCode(), Constant.ErrorInfo.LOGIN_TIME_OUT.getMsg());
            } else {
                userToken.setTime(System.currentTimeMillis());
                loginUserTokenDo.update(userToken);
                return R.ok();
            }
        } else {
            return R.error(Constant.ErrorInfo.LOGIN_TIME_OUT.getCode(), Constant.ErrorInfo.LOGIN_TIME_OUT.getMsg());
        }
    }

    @Override
    public boolean updatePassword(PasswordVo vo) {
        PersonDO person = new PersonDO();
        person.setLoginName(vo.getLoginName());
        String password = MD5Utils.encrypt(vo.getLoginName(), vo.getPassword());
        person.setPassword(password);
        return personService.update(person);
    }

	

}

