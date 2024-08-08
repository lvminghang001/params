package org.params.weiX.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class WeiXRespDto implements Serializable, Cloneable{

    private static WeiXRespDto judgment = new WeiXRespDto();

    private String responseCode;

    private String message;

    private Object data;

    public static WeiXRespDto getInstance(){return judgment.clone();}

    @Override
    public WeiXRespDto clone(){
        try {
            return (WeiXRespDto) super.clone();
        } catch (CloneNotSupportedException e){
            throw new AssertionError();
        }
    }
}
