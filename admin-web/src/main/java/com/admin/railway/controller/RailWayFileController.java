package com.admin.railway.controller;

import com.admin.common.exception.BDException;
import com.admin.railway.domain.PictureDO;
import com.admin.railway.service.PictureService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @ClassName: FileController
 * @Description: 文件访问
 * @Author: luojing
 * @Date: 2019/3/13 14:52
 */
@Controller
@RequestMapping("/railway/file")
public class RailWayFileController {

    @Autowired
    private PictureService pictureService;

    @ApiOperation(value="获取略缩图照片", notes="")
    @ApiImplicitParam(name = "id", value = "照片ID", required = true, dataType = "string")
    @GetMapping("/getThumPhoto/{id}")
    public void getThumPhoto(@PathVariable("id") Long id, HttpServletResponse response){
        PictureDO picture = pictureService.get(id);
        if(picture != null){
            try {
                response.setContentType("image/jpg");
                //获取文件
                File file = new File(picture.getThumUrl());
                FileInputStream is = new FileInputStream(file);
                byte[] bytes = new byte[(int) file.length()];
                is.read(bytes);
                is.close();
                OutputStream os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }catch (Exception e){
                throw new BDException(e.getMessage());
            }
        }

    }

    @ApiOperation(value="获取原图照片", notes="")
    @ApiImplicitParam(name = "id", value = "照片ID", required = true, dataType = "string")
    @GetMapping("/getPhoto/{id}")
    public void getPhoto(@PathVariable("id") Long id, HttpServletResponse response){
        PictureDO picture = pictureService.get(id);
        if(picture != null){
            try {
                response.setContentType("image/jpg");
                //获取文件
                File file = new File(picture.getUrl());
                FileInputStream is = new FileInputStream(file);
                byte[] bytes = new byte[(int) file.length()];
                is.read(bytes);
                is.close();
                OutputStream os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }catch (Exception e){
                throw new BDException(e.getMessage());
            }
        }

    }

}
