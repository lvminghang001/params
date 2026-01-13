package org.params.longxianghua.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LxhPushParams extends LxhBaseParams{
    private String phone;

    public String toString(){
        return "LxhPushParams {phone=" + phone +super.toString()+ "}";
    }
}
