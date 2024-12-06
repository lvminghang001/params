package org.params.guomei.dto;

import lombok.Data;

@Data
public class LinkMan {

    /** 联系人姓名 */
    private String name;

    /** 电话号码 */
    private String phone;

    /** 关系（见字典6.8） */
    private String relation;

    /** 关系级别 */
    private String relationLevel;
}
