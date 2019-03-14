package com.admin.railway.controller;

import com.admin.common.config.Constant;
import com.admin.common.utils.GenUtils;
import com.admin.common.utils.R;
import com.admin.railway.domain.OrderDO;
import com.admin.railway.domain.PictureDO;
import com.admin.railway.service.OrderService;
import com.admin.railway.service.PhotoManageService;
import com.admin.railway.service.PictureService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName: PhotoManageController
 * @Description: 照片管理
 * @Author: luojing
 * @Date: 2019/3/14 9:37
 */
@Controller
@RequestMapping("/railway/photomanage")
public class PhotoManageController {

    @Autowired
    private PhotoManageService photoManageService;

    @RequiresPermissions("railway:photomanage:list")
    @GetMapping()
    public String photoManage() {
        return "railway/photoManage/photoList";
    }

    @RequiresPermissions("railway:photomanage:list")
    @GetMapping("/list")
    @ResponseBody
    public R photoList(@RequestParam Map<String, Object> map) {
        return photoManageService.selectPagePhoto(map);
    }

    @RequiresPermissions("railway:photomanage:viewPhoto")
    @GetMapping("/viewPhoto/{id}")
    public String viewPhoto(@PathVariable("id") Long id, Model model) {
        model.addAttribute(OrderDO.ID, id);
        return "railway/photoManage/viewPhoto";
    }

    @GetMapping("/listPicture")
    @ResponseBody
    public List<PictureDO> listPicture(@RequestParam Map<String, Object> map) {
        return photoManageService.listPicture(map);
    }

    @PostMapping("/deletePic")
    @ResponseBody
    public R deletePic(@RequestParam("ids[]") Long[] ids) {
        boolean fag = photoManageService.deletePic(ids);
        if (fag) {
            return R.ok(Constant.SuccessInfo.DELETE_SUCCESS.getMsg());
        } else {
            return R.error(Constant.ErrorInfo.DELETE_FAIL.getCode(), Constant.ErrorInfo.DELETE_FAIL.getMsg());
        }
    }

    @GetMapping("/exportPic/{id}")
    public void exportPic(@PathVariable("id") Long id,HttpServletResponse response) {
        photoManageService.exportPic(id,response);
    }
}
