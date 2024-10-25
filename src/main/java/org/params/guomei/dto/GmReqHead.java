package org.params.guomei.dto;


import org.params.guomei.constants.GmCommonConsts;

public class GmReqHead {
    private String randomKey;
    private String signData;
    private String capitalCode = GmCommonConsts.capitalCode;

    public GmReqHead(String randomKey, String signData) {
        this.randomKey = randomKey;
        this.signData = signData;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public String getCapitalCode() {
        return capitalCode;
    }

    public void setCapitalCode(String capitalCode) {
        this.capitalCode = capitalCode;
    }
}
