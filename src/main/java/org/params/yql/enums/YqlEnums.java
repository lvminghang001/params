package org.params.yql.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum YqlEnums {
    YQL_CHECK("user.api.check", "撞库"),
    YQL_APPLY("user.api.apply", "进件");

    private String method;

    private String remark;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    YqlEnums(String method, String remark) {
        this.method = method;
        this.remark = remark;
    }

    public static Boolean checkMethod(String method){
        for(YqlEnums enums:YqlEnums.values()){
            if(enums.getMethod().equals(method)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
