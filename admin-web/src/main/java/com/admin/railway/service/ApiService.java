package com.admin.railway.service;

import com.admin.common.utils.R;
import com.admin.railway.domain.StationDO;
import com.admin.railway.domain.vo.LoginVo;
import com.admin.railway.domain.vo.PasswordVo;
import com.admin.railway.domain.vo.UploadImgVo;
import com.alibaba.fastjson.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * @ClassName: ApiService
 * @Description: AppServie
 * @Author: luojing
 * @Date: 2019/3/9 9:54
 */
public interface ApiService {

    /**
     * @Author: luojing
     * @Description: 登录
     * @Param: [vo]
     * @Return: com.admin.common.utils.R
     * @Date: 2019/3/10 14:05
     **/
    R login(LoginVo vo);

    /**
     * @Author: luojing
     * @Description: 上传拍照图片
     * @Param: [vo, file]
     * @Return: R
     * @Date: 2019/3/10 11:45
     **/
    R uploadImg(UploadImgVo vo, MultipartFile[] files);


    /**
     * @Author: luojing
     * @Description: 获取拍照任务
     * @Param: [personId]
     * @Return: com.admin.common.utils.R
     * @Date: 2019/3/10 14:47
     **/
    R listTask(String personId);


    /**
     * @Author: luojing
     * @Description: 获取Token
     * @Param: [token]
     * @Return: R
     * @Date: 2019/3/15 16:33
     **/
    R getToken(String token);

    /**
     * @Author: luojing
     * @Description: 修改密码
     * @Param: [vo]
     * @Return: com.admin.common.utils.R
     * @Date: 2019/3/15 18:33
     **/
    boolean updatePassword(PasswordVo vo);

	R getStationInfo(Long stationId);

	R getPersonsInfo(Long stationId);

	R getStartStations();
	/**
	 * 获取到站站点信息
	 * @return
	 * 2019年4月2日
	 * 作者：fengchase
	 */
	R getEndStations(Map<String, Object> map);

}
