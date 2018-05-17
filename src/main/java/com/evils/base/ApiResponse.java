package com.evils.base;

/**
 * Title: evils
 * CreateDate:  2018/4/30
 *
 * @author houdengw
 * @version 1.0
 */
public class ApiResponse {

    private int code;
    private String message;
    private Object data;
    /**
     * 数据集是否还有更多信息
     */
    private boolean more;

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public static ApiResponse ofMessage(int code, String message) {
        return new ApiResponse(code, message, null);
    }

    public static ApiResponse ofSuccess(Object data) {
        return new ApiResponse(Status.SUCCESS.code, Status.SUCCESS.standardmessage, data);
    }

    public static ApiResponse ofStatus(Status status) {
        return new ApiResponse(status.getCode(), status.getStandardmessage(), null);
    }

    public enum Status {
        /**
         * 成功
         */
        SUCCESS(200, "OK"),
        /**
         * 失败的请求
         */
        BAD_REQUEST(400, "Bad Request"),
        /**
         * 未找到
         */
        NOT_FOUND(404, "Not Found"),
        /**
         * 系统未知错误
         */
        INTERNAL_SERVER_ERROR(500, "Unknown Internal Error"),
        /**
         * 参数校验未通过
         */
        NOT_VALID_PARAM(40005, "Not valid Params"),
        /**
         * 不支持的操作方法
         */
        NOT_SUPPORTED_OPERATION(40006, "Operation not supported"),
        /**
         * 未登录
         */
        NOT_LOGIN(50000, "Not Login");

        private int code;
        private String standardmessage;

        Status(int code, String standardmessage) {
            this.code = code;
            this.standardmessage = standardmessage;
        }


        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStandardmessage() {
            return standardmessage;
        }

        public void setStandardmessage(String standardmessage) {
            this.standardmessage = standardmessage;
        }
    }

}
