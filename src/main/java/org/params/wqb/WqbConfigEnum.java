package org.params.wqb;

import lombok.Getter;

@Getter
public enum WqbConfigEnum {
    //MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDssd0uSCRk/g7CePp9aBzemQ3Wzr8rdgSaQAahW4Nt30BBSLptDe/4+7mb2DYIjmNUYR3MKMeUtXriRTmN+avR4pz4ibQRdH1ARu91GhAfbzBDOROS8wtt+738TESUk3Nis24FP9pUF/ZwW1sHcKCxqt1JoIqDADaSg6SVPWSm5wIDAQAB
    // 三方测试公钥  MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDssd0uSCRk/g7CePp9aBzemQ3Wzr8rdgSaQAahW4Nt30BBSLptDe/4+7mb2DYIjmNUYR3MKMeUtXriRTmN+avR4pz4ibQRdH1ARu91GhAfbzBDOROS8wtt+738TESUk3Nis24FP9pUF/ZwW1sHcKCxqt1JoIqDADaSg6SVPWSm5wIDAQAB

    TEST(
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDssd0uSCRk/g7CePp9aBzemQ3Wzr8rdgSaQAahW4Nt30BBSLptDe/4+7mb2DYIjmNUYR3MKMeUtXriRTmN+avR4pz4ibQRdH1ARu91GhAfbzBDOROS8wtt+738TESUk3Nis24FP9pUF/ZwW1sHcKCxqt1JoIqDADaSg6SVPWSm5wIDAQAB",
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDAVZCC2RaGLHlgvGsPcI9+fk39SosKeEQ4ctiM1lGf0KMU6CbVpcPy//3cM7ENxzrIeCJClsFy9KjwWW7FVLToxvQ5u6NAb2OBAB9sbRQ8MolAt60PhuqkAku8wbjBzy/XrDHJRVgKAd4DBJnHNlIvm7xPCBfENXoUSMgJYpfeBwIDAQAB",
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMBVkILZFoYseWC8aw9wj35+Tf1Kiwp4RDhy2IzWUZ/QoxToJtWlw/L//dwzsQ3HOsh4IkKWwXL0qPBZbsVUtOjG9Dm7o0BvY4EAH2xtFDwyiUC3rQ+G6qQCS7zBuMHPL9esMclFWAoB3gMEmcc2Ui+bvE8IF8Q1ehRIyAlil94HAgMBAAECgYAD9x+kxaNZvnv9exT9UKryBtpGcRgpw0NWI0QEr1HSfi4xOvy7UJDC0fVNaha/O87MnZwd4awjrxihOpl2jGGzFmnIPsprPOVcvNvrFEDtQxMlU3nhg/w3fl1ZRWK4bd60YjMp1b0X319shKYUjIuCI9tBqC2AA9Dy5NPRjUwAQQJBANrmYj0l/LXBPm5raBj8CaJxt3tTHSsskqlHSD7HQkaUOTf8cH+H1MnI56/8Bc0qisdkgmfGKYB2XO3UEAG0qMECQQDg7ozEf1fgC3umtFAVEH1Wo1lfXQLmGm+3x7y3fXxDldr5jUivySUe9CD4G01m/yhoVd7b6Fnho/8ECuwgJLDHAkARzlnBZZyn4D9G1h/SoHXcK+nj8z/VKjwH3w+GY8kFWrtIzZUNGqx5eYk3LIYASWyeiZN+A4WOuJIYmn5u1RZBAkEAgdIM2uhojn3yKwAM7Gts+9EhpwGmx6ngauDUEc9SJivai4e4uQRN8XSThr8zSEviRX2yN5f+MFYXvHyVVeP3RwJBAMCEPraHCz+Phcqz/SCbb9q8IG6nXt1rPLfgqHDUmCZnj/oBukg7XTWfUIJZ+7/LT6KJ/6NmRtsd8QwhD15rHyg=",
            "0Mzo0oLW2KdWLv045VCbzbQoFVLChPgfm58KhTg0vE",
            "https://yshtest.hzbxhd.com/h5/#/pages/loan/withdraw",
            "https://yshtest.hzbxhd.com/h5/#/pages/order/needRepayList",
            "http://xwtest.grjrong.com/xiaowei-api",
            "/half/api/loan/credit/v1/ysh/callback",
            "/half/api/loan/log/v1/ysh/callback",
            "/half/api/loan/plan/v1/ysh/callback",
            "/half/api/loan/limitChange/v1/ysh/callback"
    ),

    PRO(
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKQ7Xg/FC1kHrhmmfaT6G5UJEBVetoCzeVEtx6YuNlj/VnzZs8csmwvsb39K/oy0oNy1cjxmr0NC5xejFwd4H+AxYv7woNpWop7mf9V3awTGFyeol+jGSgyVNgYcfawDKdMRE9gUX+LEbXayVi6Dar1hdlgewjCA8tb8sCsYt9WQIDAQAB",
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsVaHX1fMqjohfjp7i1/1sNtSwdSWh/HqbAXxlookD2umj8odZPKenJlI6xbZX18Ws6UxHKAkW5HEC0lk1U0WRpskT6vlrbIGZxkt6kIdU3Dfv4RG14xutHzrLzvMaEt4g6accbGk47Z49+pT3HXhzyUOtI8A+CRuXC0zsJ4B/6QIDAQAB",
            "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKxVodfV8yqOiF+OnuLX/Ww21LB1JaH8epsBfGWiiQPa6aPyh1k8p6cmUjrFtlfXxazpTEcoCRbkcQLSWTVTRZGmyRPq+WtsgZnGS3qQh1TcN+/hEbXjG60fOsvO8xoS3iDppxxsaTjtnj36lPcdeHPJQ60jwD4JG5cLTOwngH/pAgMBAAECgYAAvgCrr4vzzbfYI4LbKr94aRcK6IJAO5hilQPOY0AQUG/LqTZ8g6acMW3Ou33AzJjZGQsfTlSVsOessa+5K1HkIB8HauD1SFFPAiktJusjqIH+5sy7dCaVc9fL9WzwamAT680c9mMHKWOxtmHNy01QM53suW6eWUKkHAi24VAYoQJBALi6wyfVWap1J5yLzAHKNIi2v96jnMLSnsVQ+iGo+dtfcOPDVfHk2/DFv6sI1kEcATivPp3dZLWAxJI3+XA0MLkCQQDu0qUyXGI7DNSZmGWvWmuzR6zoO4eowSO7apDaDUZAkiVctCVChMXSHuuI5nEItOBf8NzaYR7SXfHVEzZDOVCxAkEArpc1I0ytdkFLstUddwOZKy0tokqYOjm9/VdPUMjXfNJza5COhhR7GtCLGic7+EVzkhNu5tz79d9B6cAlbDqnYQJBAKsEaIuBoE4MyAkdGmPRaiI9h1Hdny128PF6RDQBIXeHr/2MesoBbe8jeSLVccf8TlglkOFIftvBrjUYxtioqGECQQCRpBOEAtt8vb3/DKdPNCW4qyA2dm3789StJ8jj486dZQaa60svE+YVbJYhpZ1P0MVtBi3Tio6NgIjQLLeJaCHQ",
            "Je3aWM2ww16ZcXaV0PtVVZsQJ8AW6v7Ak7pyGzPjHD",
            "https://api.bxysh.com/h5/#/pages/loan/withdraw",
            "https://api.bxysh.com/h5/#/pages/order/needRepayList",
            "https://wqbapi.veredloanweb.com/xiaowei-api",
            "/half/api/loan/credit/v1/ysh/callback",
            "/half/api/loan/log/v1/ysh/callback",
            "/half/api/loan/plan/v1/ysh/callback",
            "/half/api/loan/limitChange/v1/ysh/callback"
    );

    WqbConfigEnum(String thirdPublicKey,
                  String publicKey,
                  String privateKey,
                  String channelSignature,
                  String loanUrl,
                  String replayUrl,
                  String callBackBaseUrl,
                  String creditBackUrl,
                  String loanBackUrl,
                  String planBackUrl,
                  String limitChangeUrl
    ) {
        this.thirdPublicKey=thirdPublicKey;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.channelSignature = channelSignature;
        this.loanUrl=loanUrl;
        this.replayUrl=replayUrl;
        this.callBackBaseUrl=callBackBaseUrl;
        this.creditBackUrl=creditBackUrl;
        this.loanBackUrl=loanBackUrl;
        this.planBackUrl=planBackUrl;
        this.limitChangeUrl=limitChangeUrl;
    }


    /**
     * 对方公钥
     */
    private final String thirdPublicKey;

    /**
     * 我方公钥
     */
    private final String publicKey;
    /**
     * 我方私钥
     */
    private final String privateKey;

    /**
     * 渠道标识
     */
    private final String channelSignature;

    /**
     *  借款地址
     */
    private final String loanUrl;

    /**
     *  还款地址
     */
    private final String replayUrl;


    private final String callBackBaseUrl;

    /**
     *  授信回调地址
     */
    private final String creditBackUrl;

    /**
     *  订单状态回调地址
     */
    private final String loanBackUrl;

    /**
     *  还款计划回调地址
     */
    private final String planBackUrl;


    private final String limitChangeUrl;
}
