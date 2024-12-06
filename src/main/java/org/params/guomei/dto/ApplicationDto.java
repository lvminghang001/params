package org.params.guomei.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApplicationDto {
    /** 客户姓名 */
    private String customerName;

    /** 证件号码（身份证） */
    private String idNo;

    /** 证件类型（见字典6.3） */
    private String idType;

    /** 证件正面照片-人脸面（Base64 编码） */
    private String idCardFace;

    /** 证件反面照片-国徽面（Base64 编码） */
    private String idCardBank;

    /** 活体识别照片（Base64 编码） */
    private String borrower;

    /** 文件类型（png） */
    private String photoType;

    /** 性别（见字典6.1） */
    private String sex;

    /** 贷款用途（见字典6.2） */
    private String loanUsage;

    /** 学历（见字典6.4） */
    private String education;

    /** 婚姻状况（见字典6.5） */
    private String maritalStatus;

    /** 手机号 */
    private String phoneNo;

    /** 证件地址/户籍地址 */
    private String idCardAddress;

    /** 省居住地（非地区编号） */
    private String liveProvince;

    /** 市居住地（非地区编号） */
    private String liveCity;

    /** 区居住地（非地区编号） */
    private String liveDistrict;

    /** 居住地址（非地区编号） */
    private String liveAddress;

    /** 省居住地编码 */
    private String liveProvinceCode;

    /** 市居住地编码 */
    private String liveCityCode;

    /** 区居住地编码 */
    private String liveDistrictCode;

    /** 职业（见字典6.6） */
    private String occupation;

    /** 单位名称 */
    private String companyName;

    /** 证件生效日期（时间戳毫秒） */
    private Long idEffectDate;

    /** 证件过期日期（时间戳毫秒） */
    private Long idDueDate;

    /** 放款银行卡 */
    private String bankCardNo;

    /** 银行卡预留手机号 */
    private String bankPhoneNo;

    /** 联系人列表 */
    private List<LinkMan> linkmanList;

    /** 单位性质（见字典6.7） */
    private String companyNature;

    /** 单位所在省名称 */
    private String companyProvince;

    /** 单位所在市名称 */
    private String companyCity;

    /** 单位所在区名称 */
    private String companyArea;

    /** 单位所在省编码 */
    private String companyProvinceCode;

    /** 单位所在市编码 */
    private String companyCityCode;

    /** 单位所在区编码 */
    private String companyAreaCode;

    /** 单位详细地址 */
    private String detailCompanyAddress;

    /** 签发机关 */
    private String issuer;

    /** 民族（样例：汉） */
    private String nation;

    /** 出生日期（格式 yyyy-MM-dd） */
    private String birthday;
}
