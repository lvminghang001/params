package org.params.yql.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *   公共请求参数
 */
@Accessors(chain = true)
@Data
public class YqlRequest implements Serializable, Cloneable{
    private static YqlRequest yqlRequest = new YqlRequest();

    //API名称
    private String method;

    //数据对象转json之后进行AES加密的内容
    private String params;

    //unix时间戳(单位毫秒)
    private Long timestamp;

    //Md5(method+timestamp+params+aesKey)
    private String sign;

    public static YqlRequest getInstance(){return yqlRequest.clone();}
    @Override
    public YqlRequest clone(){
        try {
            return (YqlRequest) super.clone();
        } catch (CloneNotSupportedException e){
            throw new AssertionError();
        }
    }
}
