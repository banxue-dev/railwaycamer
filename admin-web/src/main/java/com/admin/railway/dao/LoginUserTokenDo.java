package com.admin.railway.dao;

import com.admin.railway.domain.LoginUserToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Author: luojing
 * @Description: 存放APP登录token
 * @Date: 2019/3/15 16:42
 **/
@Mapper
public interface LoginUserTokenDo {

    /**
     * @Author: luojing
     * @Description: 查询token
     * @Param: [map]
     * @Return: com.admin.railway.domain.LoginUserToken
     * @Date: 2019/3/15 16:43
     **/
    LoginUserToken getUserToken(Map<String,Object> map);

    int save(LoginUserToken token);

    int update(LoginUserToken token);


}
