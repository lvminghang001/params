package org.params.bjxd;


/**
 * @Author: wangshuilin
 * @Since: 2024/12/9
 * @Version: 1.0
 * @Description: 八戒小贷常量
 */
public class BaJieXiaoDaiConstants {

    /**
     * 小贷提供的唯一id
     */
    public static final String CLIENT_ID_PRO = "1868533924503252992";
    public static final String CLIENT_ID_TEST = "1868533924503252992";
    /**
     * 验签用的token
     */
    public static final String TOKEN_PRO = "0xv4xtpm4s5w66rnb5hwsxmed4x9078h";
    public static final String TOKEN_TEST = "0xv4xtpm4s5w66rnb5hwsxmed4x9078h";
    /**
     * 授权类型
     */
    public static final String GRANT_TYPE = "client_credentials";
    /**
     * 产品id
     */
    public static final String PRODUCT_ID_PRO = "bjjr_xdhtcl";
    public static final String PRODUCT_ID_TEST = "bjjr_xdhtcl";

    /**
     * 编码格式
     */
    public static final String CHARSET = "UTF-8";


    /**
     * accessToken接口地址
     */
    public static final String ACCESS_TOKEN_URL = "openapi/token";

    /**
     * 借款试算
     */
    public static final String LOAN_TRIAL_URL = "openapi/loan/trial?access_token=";
    /**
     * 借款申请
     */
    public static final String LOAN_APPLY_URL = "openapi/loan/apply?access_token=";
    /**
     * 借款结果查询
     */
    public static final String LOAN_RESULT_QUERY_URL = "openapi/loan/apply/query?access_token=";

    /**
     * 绑卡申请
     */
    public static final String BIND_CARD_APPLY_URL = "openapi/loan/sign/apply?access_token=";
    /**
     * 绑卡验证
     */
    public static final String BIND_CARD_VERIFY_URL = "openapi/loan/sign/verify?access_token=";
    /**
     * 还款计划查询
     */
    public static final String LOAN_REPAY_PLAN_QUERY_URL = "openapi/loan/repayPlan/query?access_token=";
    /**
     * 余额查询
     */
    public static final String LOAN_BALANCE_QUERY_URL = "openapi/loan/balance?access_token=";
    /**
     * 短信模板调用
     */
    public static final String SMS_TEMPLATE_CALL_URL = "openapi/loan/smsNotify?access_token=";
    /**
     * 还款试算
     */
    public static final String REPAY_TRIAL_URL = "openapi/loan/repay/trial?access_token=";
    /**
     * 还款
     */
    public static final String REPAY_URL = "openapi/loan/repay/apply?access_token=";
    /**
     * 还款结果查询
     */
    public static final String REPAY_RESULT_URL = "openapi/loan/repay/query?access_token=";
    /**
     * 划扣记录查询
     */
    public static final String REPAY_RECORD_URL = "openapi/loan/getPKData?access_token=";
    /**
     * 补签通知接口
     */
    public static final String SUPPLEMENTARY_SIGNATURE = "openapi/pushSupplementarySignature?access_token=";
    /**
     * 文件上传接口
     * */
    public static final String FILE_UPLOAD_URL = "openapi/fs/fsUpload?access_token=";
    /**
     * 飞书线下还款审批接口
     * */
    public static final String FEISHU_XIANXIA_REPAY_FLOW = "openapi/create/createApprovalProcess?access_token=";

    /**
     * 飞书线下还款审批接口
     * */
    public static final String FEISHU_REDUCTION_FLOW = "openapi/create/createDerateApprovalProcess?access_token=";

    /**
     * 八戒E签宝获取token
     * */
    public static final String EQB_ACCESS_TOKEN_URL = "openapi/esign/token";

    /**
     * 八戒E签宝合同签署
     * */
    public static final String EQB_CONTRACT_SIGN_URL = "openapi/sign?access_token=";

    /**
     * 八戒E签宝查询合同签署
     * */
    public static final String EQB_QUERY_CONTRACT_SIGN_URL = "openapi/querySign?access_token=";

    public static final String REDUCTION_CALLBACK_URL = "prod-api/market/callback/bjxd/feishuCallBack";
}
