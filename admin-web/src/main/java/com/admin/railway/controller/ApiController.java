package com.admin.railway.controller;

import com.admin.common.config.AdminConfig;
import com.admin.common.domain.FileDO;
import com.admin.common.utils.FileType;
import com.admin.common.utils.FileUtil;
import com.admin.common.utils.R;
import com.admin.railway.service.ApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ApiController
 * @Description: App接口
 * @Author: luojing
 * @Date: 2019/3/9 9:31
 */
@Api(tags = "App接口")
@RestController
@RequestMapping("/api/")
public class ApiController {

    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private ApiService apiService;

    @ApiOperation(value="App登录接口", notes="")
    @PostMapping("login")
    public Map<String,Object> login(){
        return null;
    }

    @ResponseBody
    @PostMapping("/upload")
    public R upload(@RequestParam("files") List<MultipartFile> files) {
        return R.error();
    }

}
