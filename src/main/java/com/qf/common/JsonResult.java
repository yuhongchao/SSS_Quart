package com.qf.common;

import com.qf.domain.Rank;

//统一结果返回的json
public class JsonResult {
    private int code;//状态吗
    private String msg;//描述信息
    private Object data;//返回数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    //成功
    public static JsonResult setOK(Object data){
        JsonResult jr=new JsonResult();
        jr.setCode(200);
        jr.setMsg("操作成功");
        jr.setData(data);
        return  jr;
    }
    //
    public static JsonResult setERROR(Object data){
        JsonResult jr=new JsonResult();
        jr.setCode(404);
        jr.setMsg("操作错误");
        jr.setData(data);
        return  jr;
    }
}
