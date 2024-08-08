package org.params.weiX.dto.push;

import lombok.Data;

import java.util.List;

@Data
public class PushDto {
    private String registerId;
    private String transactionId;
    private PersonalBasicInfo personalBasicInfo;
    private PersonalJobInfo personalJobInfo;
    private LoanInfo loanInfo;
    private BankCardInfo bankCardInfo;
    private List<ContactsInfo> contactsInfo;
    private IdCardImageInfo idCardImageInfo;
    private Extensions extensions;
}
