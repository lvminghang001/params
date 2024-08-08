package org.params.weiX.dto.push;

import lombok.Data;

@Data
public class BankCardInfo {

    /**
     * 银行卡银行名
     */
    private String bankName;

    /**
     * 银行卡卡号
     */
    private String bankCardNo;

    /**
     * 银行卡预留手机号
     */
    private String bindPhoneNo;
}
