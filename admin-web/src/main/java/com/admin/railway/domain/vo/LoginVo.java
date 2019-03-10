package com.admin.railway.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: LoginVo
 * @Description: 登录参数
 * @Author: luojing
 * @Date: 2019/3/10 14:03
 */
@Data
public class LoginVo {

    @ApiModelProperty(value = "拍照人登录名", dataType = "string", required = true)
    private String loginName;

    @ApiModelProperty(value = "拍照人密码", dataType = "string", required = true)
    private String password;

}
