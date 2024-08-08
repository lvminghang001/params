package org.params.weiX.dto.push;

import lombok.Data;

@Data
public class ContactsInfo {
    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机号
     */
    private String contactPhoneNo;

    /**
     * 与申请人关系
     */
    private String contactRelation;
}
