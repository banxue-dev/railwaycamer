package com.admin.railway.controller;

import com.admin.common.utils.R;
import com.admin.railway.domain.vo.UploadImgVo;
import com.admin.railway.service.ApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * @ClassName: ApiController
 * @Description: App接口
 * @Author: luojing
 * @Date: 2019/3/9 9:31
 */
@Api(tags = "App接口")
@Controller
@RequestMapping("/api/")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @ApiOperation(value="App登录接口", notes="")
    @PostMapping("login")
    public Map<String,Object> login(){
        return null;
    }

    @ApiOperation(value="拍照上传接口", notes="")
    @ResponseBody
    @PostMapping("upload")
    public R upload(UploadImgVo vo) {
        apiService.uploadImg(vo);
        return R.error();
    }

}
