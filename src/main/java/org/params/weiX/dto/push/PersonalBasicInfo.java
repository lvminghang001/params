package org.params.weiX.dto.push;

import lombok.Data;

@Data
public class PersonalBasicInfo {
    /**
     * 证件类型
     */
    private String idType;
    /**
     * 证件号
     */
    private String idCardNo;
    /**
     * 证件有效开始日
     */
    private String idValidStart;
    /**
     * 证件有效截止日
     */
    private String idValidEnd;
    /**
     * 用户姓名
     */
    private String customerName;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 居住地址 （户籍地）
     */
    private String address;
    /**
     * 婚姻状况
     */
    private String marriage;
    /**
     * 最高学历
     */
    private String education;
    /**
     * 民族
     */
    private String nation;
    /**
     * 签发机关
     */
    private String signOrg;

}
