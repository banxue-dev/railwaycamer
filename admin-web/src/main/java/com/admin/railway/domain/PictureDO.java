package com.admin.railway.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 照片表
 *
 * @author luojing
 * @email lg932740579@163.com
 * @date 2019-03-10 09:22:12
 */
@Data
public class PictureDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //自增长id
    private Long id;
    //任务id
    private Long orderId;
    //车厢号
    private String trainNo;
    //拍照人Id
    private Long personId;
    //略缩图路径地址
    private String thumUrl;
    //原图地址
    private String url;
    //是否删除
    private Integer delState;
    //创建时间
    private Date createTime;
    //创建用户
    private String createUser;
    //修改时间
    private Date modifyTime;
    //修改用户
    private String modifyUser;

    public final static String ORDER_ID = "orderId";
}
