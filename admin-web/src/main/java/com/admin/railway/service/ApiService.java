package com.admin.railway.service;

import com.admin.common.utils.R;
import com.admin.railway.domain.vo.LoginVo;
import com.admin.railway.domain.vo.PasswordVo;
import com.admin.railway.domain.vo.UploadImgVo;
import org.springframework.web.multipart.MultipartFile;

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
    R login(LoginVo vo, HttpServletRequest request);

    /**
     * @Author: luojing
     * @Description: 上传拍照图片
     * @Param: [vo, file]
     * @Return: R
     * @Date: 2019/3/10 11:45
     **/
    R uploadImg(UploadImgVo vo, MultipartFile[] file);


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

}
