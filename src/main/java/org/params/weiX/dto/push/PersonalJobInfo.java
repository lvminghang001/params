package org.params.weiX.dto.push;

import lombok.Data;

@Data
public class PersonalJobInfo {
    /**
     * 职业类别
     */
    private String occupation;
    /**
     * 单位所属行业
     */
    private String industry;

    /**
     * 月收入
     */
    private String incomeMonth;
    /**
     * 公司名称
     */
    private String corporationName;
    /**
     * 公司电话
     */
    private String corporationTel;
}
