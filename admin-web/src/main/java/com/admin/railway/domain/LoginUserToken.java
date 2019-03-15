package com.admin.railway.domain;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: LoginUserToken
 * @Description: 存放APP登录token
 * @Author: luojing
 * @Date: 2019/3/15 16:36
 */
@Data
public class LoginUserToken {

    //用户Id
    private String userId;
    //token
    private String token;
    //超时时间 秒
    private Integer timeOut;
    //创建时间
    private Long time;
}
