package com.admin.railway.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: luojing
 * @Description: 拍照上传参数
 * @Date: 2019/3/9 10:03
 **/
@ApiModel(description = "拍照上传参数")
@Data
public class UploadImgVo {

    @ApiModelProperty(value = "拍照人ID", dataType = "string", required = true)
    private String personId;

    @ApiModelProperty(value = "车厢号", dataType = "string", required = true)
    private String trainNo;

    @ApiModelProperty(value = "调度任务ID,为空时代表拍照人员主动上传照片", dataType = "string", required = false)
    private String taskId;

}
