package com.admin.railway.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: PasswordVo
 * @Description: TODO
 * @Author: luojing
 * @Date: 2019/3/15 18:30
 */
@Data
public class PasswordVo {

    @ApiModelProperty(value = "拍照人登录名", dataType = "string", required = true)
    private String loginName;

    @ApiModelProperty(value = "新密码", dataType = "string", required = true)
    private String password;
}
