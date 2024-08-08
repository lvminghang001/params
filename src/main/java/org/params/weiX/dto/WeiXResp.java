package org.params.weiX.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class WeiXResp implements Serializable, Cloneable{
    private WeiXRespDto weiXRespDto;
//    private ChannelDTO channelDTO;
    private static WeiXResp judgment = new WeiXResp();
    public static WeiXResp getInstance(){return judgment.clone();}

    @Override
    public WeiXResp clone(){
        try {
            return (WeiXResp) super.clone();
        } catch (CloneNotSupportedException e){
            throw new AssertionError();
        }
    }
}
