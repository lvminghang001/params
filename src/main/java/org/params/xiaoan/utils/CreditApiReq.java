package org.params.xiaoan.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: xiaoanfenqi
 * @description: 信贷api进件
 * @author: LiYu
 * @create: 2023-06-21 10:10
 **/
@Data
@Accessors(chain = true)
public class CreditApiReq implements Serializable {
    private static final long serialVersionUID = -5774761165394295658L;
    /**
     * 用户id
     */
    private Long customerId;
    /**
     * 手机号 - 已解密
     */
    private String phone;
    /**
     * 身份证 - 已解密
     */
    private String idCard;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 城市
     */
    private String city;
    /**
     * 城市编码
     */
    private String cityCode;
    /**
     * 学历
     */
    private Integer highestEducation;
    /**
     * 房产
     */
    private Integer estate;
    /**
     * 社保
     */
    private Integer socialSecurity;
    /**
     * 公积金
     */
    private Integer accumulationFund;
    /**
     * 芝麻分
     */
    private Integer sesame;
    /**
     * 职业身份
     */
    private Integer professionalIdentity;
    /**
     * 月收入
     */
    private BigDecimal monthlyIncome;
    /**
     * 贷款用途
     */
    private Integer loanPurpose;
    /**
     * api产品
     */
    private CreditApiType creditApiType;
    /**
     * 车产
     */
    private Integer carProduction;

    /**
     * ip
     */
    private String ip;

    /**
     * 授权书地址
     */
    private String accessoryUrl;
    /**
     * 附件类型
     */
    private String accessoryType;
    /**
     * 文件类型
     */
    private String accessoryFileType;
    /**
      * 撞库url
     */
    private String hitLibraryUrl;
    /**
     * 注册url
     */
    private String registeredUrl;
    /**
     * 撞库流水号
     */
    private String checkNo;
    /**
     * 信用卡
     */
    private Integer customerCreditCard;
    /**
     * 渠道标识
     */
    private String channelSignature;
    /**
     * 保单
     */
    private Integer unitSocialSecurity;
    /**
     * 工资发放形式
     */
    private Integer customerFormOfPayroll;
    /**
     * 工作年限
     */
    private Integer lengthOfService;

    /**
     * 0：安卓 1：ios
     */
    private Integer deviceType;

    /**
     * h5 职业是否为空
     */
    private Boolean h5Job;

    /**
     * 请求是否来自app
     * true app  false web
     */
    private Boolean osVersion;

    /**
     * 蚂蚁花呗额度
     */
    private Integer huaBeiQuota;
    /**
     * 京东白天额度
     */
    private Integer baiTiaoQuota;
    /**
     * 用户渠道编号
     */
    private String customerChannel;
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 授权类型：0api授权，1h5授权
     */
    private Integer authorizationType;
    /**
     * 产品名称
     */
    private String productName;

    /**
     *  渠道id
     */
    private Long channelId;

    private String loanName;


    private String loanProductName;

    private String channelName;
}
