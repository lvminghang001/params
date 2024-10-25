package org.params.guomei.dto;

public class GmResp {
    private GmReqHead reqHead;
    // 业务数据在这个字段上
    private String respData;

    public GmResp() {
    }

    public GmResp(GmReqHead reqHead, String respData) {
        this.reqHead = reqHead;
        this.respData = respData;
    }

    public GmReqHead getReqHead() {
        return reqHead;
    }

    public void setReqHead(GmReqHead reqHead) {
        this.reqHead = reqHead;
    }

    public String getRespData() {
        return respData;
    }

    public void setRespData(String respData) {
        this.respData = respData;
    }
}
