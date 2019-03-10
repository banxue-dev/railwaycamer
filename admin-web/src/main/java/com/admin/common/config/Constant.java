package com.admin.common.config;

public class Constant {
    //演示系统账户
    public static String DEMO_ACCOUNT = "test";
    //自动去除表前缀
    public static String AUTO_REOMVE_PRE = "true";
    //停止计划任务
    public static String STATUS_RUNNING_STOP = "stop";
    //开启计划任务
    public static String STATUS_RUNNING_START = "start";
    //通知公告阅读状态-未读
    public static String OA_NOTIFY_READ_NO = "0";
    //通知公告阅读状态-已读
    public static int OA_NOTIFY_READ_YES = 1;
    //部门根节点id
    public static Long DEPT_ROOT_ID = 0l;
    //缓存方式
    public static String CACHE_TYPE_REDIS ="redis";

    public static String LOG_ERROR = "error";


    /**
     * @Author: luojing
     * @Description: 错误信息
     * @Date: 2019/3/10 12:05
     **/
    public enum ErrorInfo{
        PERSION_ID_NULL(-10000,"拍照人信息为空"),
        TRANIN_NO_NULL(-10001,"车号信息为空"),
        IMAGE_NULL(-10002,"图片信息为空"),
        IMAGE_UPLOAD_FAIL(-10003,"图片上传失败"),
        LOGIN_NAME_NULL(-10004,"登录名不能为空"),
        PASSWORD_NULL(-10005,"密码不能为空"),
        USER_NOT_EXIST(-10006,"用户不存在,请重新输入"),
        PASSWORD_ERROR(-10007,"密码错误"),
        PERSION_NULL(-10008,"系统中不存在拍照人员");

        private int code;
        private String msg;
        ErrorInfo(int code,String msg) {
            this.code = code;
            this.msg = msg;
        }
        public int getCode() {
            return code;
        }
        public String getMsg(){
            return msg;
        }
    }

}
