package com.admin.railway.controller;

import com.admin.common.annotation.Log;
import com.admin.common.config.Constant;
import com.admin.common.utils.R;
import com.admin.common.utils.StringUtils;
import com.admin.railway.domain.vo.LoginVo;
import com.admin.railway.domain.vo.UploadImgVo;
import com.admin.railway.service.ApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: ApiController
 * @Description: App接口
 * @Author: luojing
 * @Date: 2019/3/9 9:31
 */
@Api(tags = "App Interface")
@RestController
@RequestMapping("/api/")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Log("App登录")
    @ApiOperation(value="App登录接口", notes="")
    @PostMapping("login")
    public R login(LoginVo vo){
        if(StringUtils.isEmpty(vo.getLoginName())){
            return R.error(Constant.ErrorInfo.LOGIN_NAME_NULL.getCode(),Constant.ErrorInfo.LOGIN_NAME_NULL.getMsg());
        }
        if(StringUtils.isEmpty(vo.getPassword())){
            return R.error(Constant.ErrorInfo.PASSWORD_NULL.getCode(),Constant.ErrorInfo.PASSWORD_NULL.getMsg());
        }
        return apiService.login(vo);
    }

    @Log("拍照上传")
    @ApiOperation(value="拍照上传接口", notes="")
    @PostMapping("uploadImg")
    public R uploadImg(UploadImgVo vo,@RequestParam("file") MultipartFile file) {
        if(StringUtils.isEmpty(vo.getPersonId())){
            return R.error(Constant.ErrorInfo.PERSION_ID_NULL.getCode(),Constant.ErrorInfo.PERSION_ID_NULL.getMsg());
        }
        if(StringUtils.isEmpty(vo.getTrainNo())){
            return R.error(Constant.ErrorInfo.TRANIN_NO_NULL.getCode(),Constant.ErrorInfo.TRANIN_NO_NULL.getMsg());
        }
        if(file.getSize() == 0){
            return R.error(Constant.ErrorInfo.IMAGE_NULL.getCode(),Constant.ErrorInfo.IMAGE_NULL.getMsg());
        }
        return apiService.uploadImg(vo,file);
    }

    @Log("获取拍照任务")
    @ApiOperation(value="获取拍照任务", notes="")
    @GetMapping("listTask/{personId}")
    public R listTask(@PathVariable("personId") String personId){
        return apiService.listTask(personId);
    }

}
