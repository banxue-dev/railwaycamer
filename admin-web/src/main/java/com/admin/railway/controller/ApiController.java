package com.admin.railway.controller;

import com.admin.common.annotation.Log;
import com.admin.common.config.Constant;
import com.admin.common.utils.PageUtils;
import com.admin.common.utils.R;
import com.admin.common.utils.StringUtils;
import com.admin.railway.domain.vo.LoginVo;
import com.admin.railway.domain.vo.PasswordVo;
import com.admin.railway.domain.vo.UploadImgVo;
import com.admin.railway.service.ApiService;
import com.admin.railway.service.impl.ApiServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

    @Log("APP登录")
    @ApiOperation(value = "App登录接口", notes = "")
    @PostMapping(value = "login", produces = "application/json;charset=utf-8")
    public R login(@RequestBody LoginVo vo) {
        if (StringUtils.isEmpty(vo.getLoginName())) {
            return R.error(Constant.ErrorInfo.LOGIN_NAME_NULL.getCode(), Constant.ErrorInfo.LOGIN_NAME_NULL.getMsg());
        }
        if (StringUtils.isEmpty(vo.getPassword())) {
            return R.error(Constant.ErrorInfo.PASSWORD_NULL.getCode(), Constant.ErrorInfo.PASSWORD_NULL.getMsg());
        }
        return apiService.login(vo);
    }

    @Log("APP拍照上传")
    @ApiOperation(value = "拍照上传接口", notes = "")
    @PostMapping("uploadImg")
    public R uploadImg(UploadImgVo vo, @RequestParam("files") MultipartFile[] files) {
        if (StringUtils.isEmpty(vo.getPersonId())) {
            return R.error(Constant.ErrorInfo.PERSION_ID_NULL.getCode(), Constant.ErrorInfo.PERSION_ID_NULL.getMsg());
        }
        if (StringUtils.isEmpty(vo.getTrainNo())) {
            return R.error(Constant.ErrorInfo.TRANIN_NO_NULL.getCode(), Constant.ErrorInfo.TRANIN_NO_NULL.getMsg());
        }
        if (files.length == 0) {
            return R.error(Constant.ErrorInfo.IMAGE_NULL.getCode(), Constant.ErrorInfo.IMAGE_NULL.getMsg());
        }
        return apiService.uploadImg(vo, files);
    }

    @Log("APP获取拍照任务")
    @ApiOperation(value = "获取拍照任务", notes = "")
    @GetMapping("listTask/{personId}")
    public R listTask(@PathVariable("personId") String personId) {
        return apiService.listTask(personId);
    }
    @Log("获取站点信息")
    @ApiOperation(value = "获取站点信息", notes = "")
    @GetMapping("getStation/{stationId}")
    public R getStationInfo(@PathVariable("stationId") Long stationId) {
    	return apiService.getStationInfo(stationId);
    }
    @Log("获取站点人员信息")
    @ApiOperation(value = "获取站点人员信息", notes = "")
    @GetMapping("getPersons/{stationId}")
    public R getPersonsInfo(@PathVariable("stationId") Long stationId) {
    	return apiService.getPersonsInfo(stationId);
    }
    @Log("获取发站站点信息")
    @ApiOperation(value = "获取发站点信息", notes = "")
    @GetMapping("getStartStations")
    public R getStartStations(Long personId) {
    	return apiService.getStartStations(personId);
    }
    @Log("获取到站站点信息")
    @ApiOperation(value = "获取站点信息", notes = "")
    @GetMapping("getEndStations")
    public R getEndStations(Integer offset,Integer limit) {

		Map<String,Object> map=new HashMap<String,Object>();
		map.put("type", 2);
		map.put("offset", offset);
		map.put("limit", limit);
    	return apiService.getEndStations(map);
    }

    @Log("APP修改密码")
    @ApiOperation(value = "获取拍照任务", notes = "")
    @PostMapping(value = "updatePassword", produces = "application/json;charset=utf-8")
    public R updatePassword(@RequestBody PasswordVo vo) {
        boolean fag = apiService.updatePassword(vo);
        if (fag) {
            return R.ok(Constant.SuccessInfo.UPDATE_SUCCESS.getMsg());
        } else {
            return R.error(Constant.ErrorInfo.UPDATE_FAIL.getCode(), Constant.ErrorInfo.UPDATE_FAIL.getMsg());
        }
    }

}
