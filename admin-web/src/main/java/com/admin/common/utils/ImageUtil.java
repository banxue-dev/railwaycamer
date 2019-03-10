package com.admin.common.utils;

import javafx.util.Builder;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


/**
 * @ClassName: ImageUtil
 * @Description: TODO
 * @Author: luojing
 * @Date: 2019/3/10 12:49
 */
public class ImageUtil {

    public static final int WIDTH = 350;
    public static final int HEIGHT = 450;

    /**
     * @Author: luojing
     * @Description: 生成并保存缩略图
     * @Param: [file, uploadPath, newPath]
     * @Return: java.lang.String
     * @Date: 2019/3/10 13:03
     **/
    public static String thumbnail(MultipartFile file, String path, String fileName) {
        String filePath = path + "thum/";
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String url = filePath + fileName;
        try {
            //图片缩略图实现，强制按照指定的宽高进行缩略keepAspectRatio(false)
            Thumbnails.of(file.getInputStream())
//                    .size(WIDTH, HEIGHT) 按大小缩放
                    .scale(0.3f) //等比例缩放
                    .toFile(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //缩略图返回的相对路径
        return url;
    }
}
