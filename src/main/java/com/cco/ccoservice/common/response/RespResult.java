package com.cco.ccoservice.common.response;


import com.cco.ccoservice.common.constant.HttpStatus;
import com.cco.ccoservice.common.utils.StringUtils;

import java.util.HashMap;

/**
 * 响应消息实体
 *
 * @author
 */
public class RespResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 RespResult 对象，使其表示一个空消息。
     */
    public RespResult() {
    }

    /**
     * 初始化一个新创建的 RespResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public RespResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 RespResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public RespResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public RespResult success() {
        return RespResult.success("请求成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static RespResult success(Object data) {
        return RespResult.success("请求成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static RespResult success(String msg) {
        return RespResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static RespResult success(String msg, Object data) {
        return new RespResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public RespResult error() {
        return RespResult.error("请求失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static RespResult error(String msg) {
        return RespResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static RespResult error(String msg, Object data) {
        return new RespResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static RespResult error(int code, String msg) {
        return new RespResult(code, msg, null);
    }
}
