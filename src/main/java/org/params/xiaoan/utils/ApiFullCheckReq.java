package org.params.xiaoan.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import io.micrometer.common.util.StringUtils;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 全流程 - 信贷api - 撞库参数
 */
@Data
public class ApiFullCheckReq implements Serializable {
    private static final long serialVersionUID = 6299232944414813820L;

    /**
     * 性别
     */
    @NotNull(message = "性别不能为空")
    private Integer sex;
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phoneMd5;
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String nameMd5;
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
    private String idCardMd5;
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
//    @Min(value = 350, message = "芝麻分不能小于350")
//    @Max(value = 950, message = "芝麻分不能大于950")
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


    public String xafqParamsValid(String dataReq, boolean isCheck) {
        ApiFullCheckReq checkReq = null;
        ApiFullPushReq pushReq = null;
        if (isCheck) {
            checkReq = JSON.parseObject(dataReq, ApiFullCheckReq.class);
        } else {
            pushReq = JSON.parseObject(dataReq, ApiFullPushReq.class);
            checkReq = JSON.parseObject(dataReq, ApiFullCheckReq.class);
        }

        if (StringUtils.isBlank(checkReq.getChannelSignature())) {
            return "渠道标识为空";
        }

        if (StringUtils.isBlank(checkReq.getCity())) {
            return "城市为空";
        }

        if (isCheck) {
            if (StringUtils.isBlank(checkReq.getPhoneMd5())) {
                return "手机号md5为空";
            }
        } else {
            if (StringUtils.isBlank(pushReq.getPhone())) {
                return "手机号为空";
            }
            if (StringUtils.isBlank(pushReq.getName())) {
                return "姓名为空";
            }
            if (!Validator.isChineseName(pushReq.getName())) {
                return "姓名校验不通过";
            }
        }

        if (Objects.isNull(checkReq.getSex())) {
            return "性别为空";
        }
        if (Objects.isNull(checkReq.getAge())) {
            return "年龄为空";
        }
        if (Objects.isNull(checkReq.getSocialSecurity())) {
            return "社保为空";
        }
        if (Objects.isNull(checkReq.getAccumulationFund())) {
            return "公积金为空";
        }
        if (Objects.isNull(checkReq.getCarProduction())) {
            return "车辆信息为空";
        }
        if (Objects.isNull(checkReq.getEstate())) {
            return "房产信息为空";
        }
        if (Objects.isNull(checkReq.getUnitSocialSecurity())) {
            return "保险为空";
        }
        if (Objects.isNull(checkReq.getSesame())) {
            return "芝麻分为空";
        }
        if (Objects.isNull(checkReq.getProfessionalIdentity())) {
            return "职业为空";
        }
        if (Objects.isNull(checkReq.getCustomerCreditCard())) {
            return "信用卡额度为空";
        }
        if (Objects.isNull(checkReq.getHighestEducation())) {
            return "学历为空";
        }
        if (Objects.isNull(checkReq.getMonthlyIncome())) {
            return "月收入为空";
        }
        if (Objects.isNull(checkReq.getCustomerFormOfPayroll())) {
            return "工资发放形式为空";
        }
        if (Objects.isNull(checkReq.getLengthOfService())) {
            return "当前单位工龄为空";
        }
        if (Objects.isNull(checkReq.getLoanPurpose())) {
            return "贷款用途为空";
        }
        return null;
    }

    public static CreditApiReq getCreditApiReq(ApiFullCheckReq req) {
        CreditApiReq apiReq = BeanUtil.copyProperties(req, CreditApiReq.class);
        apiReq.setPhone(req.getPhoneMd5());
        apiReq.setIdCard(req.getIdCardMd5());
        apiReq.setName(req.getNameMd5());
        apiReq.setMonthlyIncome(req.getMonthlyIncome());
        apiReq.setH5Job(true);
        return apiReq;
    }
}
