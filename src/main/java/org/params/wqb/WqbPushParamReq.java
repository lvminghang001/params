package org.params.wqb;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@Data
public class WqbPushParamReq implements Serializable {
    // 进件申请订单号
    @NotBlank(message = "订单号不能为空")
    private String applyNo;

    // 用户手机号MD5
    @NotBlank(message = "用户手机号MD5不能为空")
    private String phoneMd5;

    // 用户身份证MD5
    @NotBlank(message = "用户身份证MD5不能为空")
    private String idCardMd5;

    // 用户姓名
    @NotBlank(message = "用户姓名不能为空")
    private String name;

    // 用户身份证
    @NotBlank(message = "用户身份证不能为空")
    private String idcard;

    // 证件签发日期
    @NotBlank(message = "证件签发日期不能为空")
    private String credentialsIssueDate;

    // 证件失效日期
    @NotBlank(message = "证件失效日期不能为空")
    private String credentialsInvalidDate;

    // 证件是否是长期
    private String permanent;

    // 发证机关
    @NotBlank(message = "发证机关不能为空")
    private String issuingOrgan;

    // 性别
    @NotBlank(message = "性别不能为空")
    private String gender;

    // 民族
    @NotBlank(message = "民族不能为空")
    private String nation;

    // 活体认证照片url
    @NotBlank(message = "活体认证照片url不能为空")
    private String aliveAuthImageUrl;

    // 活体认证时间
    @NotBlank(message = "活体认证时间不能为空")
    private String aliveAuthTime;

    // 活体认证分数
    @NotBlank(message = "活体认证分数不能为空")
    private String faceScore;

    // 身份证人像面照片url
    @NotBlank(message = "身份证人像面照片url不能为空")
    private String idCardFrontImageUrl;

    // 身份证国徽面照片url
    @NotBlank(message = "身份证国徽面照片url不能为空")
    private String idCardBackImageUrl;

    // 身份证省份
    @NotBlank(message = "身份证省份不能为空")
    private String idProvinceName;

    // 身份证省份code
    @NotBlank(message = "身份证省份code不能为空")
    private String idProvinceCode;

    // 身份证城市
    @NotBlank(message = "身份证城市不能为空")
    private String idCityName;

    // 身份证城市code
    @NotBlank(message = "身份证城市code不能为空")
    private String idCityCode;

    // 身份证地址
    @NotBlank(message = "身份证地址不能为空")
    private String idAddress;

    // 月收入，单位元
    @NotBlank(message = "月收入不能为空")
    private String income;

    // 用户登录IP
    @NotBlank(message = "用户登录IP不能为空")
    private String ip;

    // 用户定位经度
    @NotBlank(message = "用户定位经度不能为空")
    private String longitude;

    // 用户定位纬度
    @NotBlank(message = "用户定位纬度不能为空")
    private String latitude;

    // 设备类型
    private String deviceType;

    // 用户开卡银行名称
    private String bankName;

    // 银行卡号
    private String bankCard;

    // 银行预留手机号
    private String reservePhone;

    // 用户手机号
    @NotBlank(message = "用户手机号不能为空")
    private String phone;

    // 婚姻状态
    @NotBlank(message = "婚姻状态不能为空")
    private String marriageStatus;

    // 居住地省份
    private String liveProvinceName;

    // 居住地省份code
    private String liveProvinceCode;

    // 居住地城市
    private String liveCityName;

    // 居住地城市code
    private String liveCityCode;

    // 居住地区
    private String liveAreaName;

    // 居住地区code
    private String liveAreaCode;

    // 居住地详细地址
    @NotBlank(message = "居住地详细地址不能为空")
    private String liveAddress;

    // 最高学历
    @NotBlank(message = "最高学历不能为空")
    private String highestEducationalLevel;

    // 公司名称
    @NotBlank(message = "公司名称不能为空")
    private String companyName;

    // 单位所在省份
    @NotBlank(message = "单位所在省份不能为空")
    private String workProvinceName;

    // 单位所在省份code
    @NotBlank(message = "单位所在省份code不能为空")
    private String workProvinceCode;

    // 单位所在城市
    @NotBlank(message = "单位所在城市不能为空")
    private String workCityName;

    // 单位所在城市code
    @NotBlank(message = "单位所在城市code不能为空")
    private String workCityCode;

    // 单位所在区
    @NotBlank(message = "单位所在区不能为空")
    private String workAreaName;

    // 单位所在区code
    @NotBlank(message = "单位所在区code不能为空")
    private String workAreaCode;

    // 单位详细地址
    @NotBlank(message = "单位详细地址不能为空")
    private String workAddress;

    // 工作单位类型
    @NotBlank(message = "工作单位类型不能为空")
    private String workUnit;

    // 从事行业
    @NotBlank(message = "从事行业不能为空")
    private String occupation;

    // 亲属关系
    @NotBlank(message = "亲属关系不能为空")
    private String kinShip;

    // 亲属姓名
    @NotBlank(message = "亲属姓名不能为空")
    private String kinName;

    // 亲属电话
    @NotBlank(message = "亲属电话不能为空")
    private String kinPhone;

    // 紧急联系人关系
    @NotBlank(message = "紧急联系人关系不能为空")
    private String contactRelationship;

    // 紧急联系人姓名
    @NotBlank(message = "紧急联系人姓名不能为空")
    private String contactName;

    // 紧急联系人电话
    @NotBlank(message = "紧急联系人电话不能为空")
    private String contactPhone;
}
