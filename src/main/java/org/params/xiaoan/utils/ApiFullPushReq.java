package org.params.xiaoan.utils;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 全流程 - 信贷api - 进件参数
 */
@Data
public class ApiFullPushReq implements Serializable {
    private static final long serialVersionUID = 7898664394454538150L;

    /**
     * 性别
     */
    @NotNull(message = "性别不能为空")
    private Integer sex;
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String name;
    /**
     * 年龄
     */
    @NotNull(message = "年龄不能为空")
    @Min(value = 23, message = "年龄不能小于23岁")
    @Max(value = 55, message = "年龄不能大于55岁")
    private Integer age;
    /**
     * 身份证
     */
    private String idCard;
    /**
     * 城市名
     */
    @NotBlank(message = "城市名不能为空")
    private String city;
    /**
     * 城市编码
     */
    @NotBlank(message = "城市编码不能为空")
    private String cityCode;
    /**
     * 学历
     */
    @NotNull(message = "学历不能为空")
    private Integer highestEducation;
    /**
     * 房产
     */
    @NotNull(message = "房产不能为空")
    private Integer estate;
    /**
     * 社保
     */
    @NotNull(message = "社保不能为空")
    private Integer socialSecurity;
    /**
     * 公积金
     */
    @NotNull(message = "公积金不能为空")
    private Integer accumulationFund;
    /**
     * 芝麻分
     */
    @NotNull(message = "芝麻分不能为空")
    private Integer sesame;
    /**
     * 职业身份
     */
    @NotNull(message = "职业身份不能为空")
    private Integer professionalIdentity;
    /**
     * 月收入
     */
    @NotNull(message = "月收入不能为空")
    private BigDecimal monthlyIncome;
    /**
     * 贷款用途
     */
    @NotNull(message = "贷款用途不能为空")
    private Integer loanPurpose;
    /**
     * 车产
     */
    @NotNull(message = "车产不能为空")
    private Integer carProduction;
    /**
     * ip
     */
    private String ip;
    /**
     * 信用卡
     */
    @NotNull(message = "信用卡不能为空")
    private Integer customerCreditCard;
    /**
     * 保单
     */
    @NotNull(message = "保单不能为空")
    private Integer unitSocialSecurity;
    /**
     * 工资发放形式
     */
    @NotNull(message = "工资发放形式不能为空")
    private Integer customerFormOfPayroll;
    /**
     * 工作年限
     */
    @NotNull(message = "工作年限不能为空")
    private Integer lengthOfService;
    /**
     * 0：安卓 1：ios
     */
    private Integer deviceType;
    /**
     * 渠道标识
     */
    @NotBlank(message = "渠道标识不能为空")
    private String channelSignature;


    private Integer huaBeiQuota;

    private Integer baiTiaoQuota;
}
