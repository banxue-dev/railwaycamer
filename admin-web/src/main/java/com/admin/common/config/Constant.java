package com.admin.common.config;

import io.swagger.models.auth.In;

public class Constant {
    //演示系统账户
    public final static String DEMO_ACCOUNT = "test";
    //自动去除表前缀
    public final static String AUTO_REOMVE_PRE = "true";
    //停止计划任务
    public final static String STATUS_RUNNING_STOP = "stop";
    //开启计划任务
    public final static String STATUS_RUNNING_START = "start";
    //通知公告阅读状态-未读
    public final static String OA_NOTIFY_READ_NO = "0";
    //通知公告阅读状态-已读
    public final static int OA_NOTIFY_READ_YES = 1;
    //部门根节点id
    public final static Long DEPT_ROOT_ID = 0l;
    //缓存方式
    public final static String CACHE_TYPE_REDIS = "redis";

    public final static String LOG_ERROR = "error";

    public final static String ROOT_DIRECTORY = "根目录";

    public final static String DATA = "data";

    public final static String COUNT = "count";

    public final static String PERSION = "person";

    public final static String TOKEN = "token";

    // 单位秒
    public final static Integer TOKEN_TIME_OUT = 1800;

    /**
     * @Author: luojing
     * @Description: 成功信息
     * @Date: 2019/3/10 12:05
     **/
    public enum SuccessInfo {
        ADD_SUCCESS("添加成功"),
        DELETE_SUCCESS("删除成功"),
        UPDATE_SUCCESS("修改成功");
        private String msg;

        SuccessInfo(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * @Author: luojing
     * @Description: 错误信息
     * @Date: 2019/3/10 12:05
     **/
    public enum ErrorInfo {
        PERSION_ID_NULL(-10000, "拍照人信息为空"),
        TRANIN_NO_NULL(-10001, "车号信息为空"),
        IMAGE_NULL(-10002, "图片信息为空"),
        IMAGE_UPLOAD_FAIL(-10003, "图片上传失败"),
        LOGIN_NAME_NULL(-10004, "登录名不能为空"),
        PASSWORD_NULL(-10005, "密码不能为空"),
        USER_NOT_EXIST(-10006, "用户不存在,请重新输入"),
        PASSWORD_ERROR(-10007, "密码错误"),
        PERSION_NULL(-10008, "系统中不存在拍照人员"),

        ADD_TASK_EXIST_TRANIN(-10009, "该车号今日已经添加任务，如需操作，可以进行修改"),

        ADD_FAIL(-20000, "添加失败"),
        DELETE_FAIL(-20001, "删除失败"),
        UPDATE_FAIL(-20002, "修改失败"),
        EXIST_STATION_DEL_FAIL(-20003, "存在下级站点，删除失败"),


        LOGIN_TIME_OUT(-20004, "登录超时,请重新登录"),
        AUTH_FAIL(-20005, "身份验证失败，请输入有效的身份认证");

        private int code;
        private String msg;

        ErrorInfo(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * @Author: luojing
     * @Description: 数字
     * @Date: 2019/3/13 12:57
     **/
    public enum Number {
        ZERO(0);
        private Integer code;

        Number(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

}
