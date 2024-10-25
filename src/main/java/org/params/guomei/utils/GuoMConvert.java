//package org.params.guomei.utils;
//
//import com.alibaba.fastjson.JSONObject;
//import com.jieyihua.app.domain.AppCustomer;
//import com.jieyihua.app.domain.AppCustomerDetails;
//import com.jieyihua.app.domain.AppCustomerDetailsApi;
//import com.jieyihua.app.service.sampling.guomei.dto.ApplicationDto;
//import com.jieyihua.app.service.sampling.guomei.dto.LinkMan;
//import com.jieyihua.common.core.http.dto.ChannelDTO;
//import com.jieyihua.common.core.utils.CommonUtils;
//import com.jieyihua.common.core.utils.EncryptUtil;
//import com.jieyihua.common.core.utils.StringUtils;
//import com.jieyihua.common.core.utils.ToolUtils;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//public class GuoMConvert {
//
//
//    @Setter
//    private String encrypted;
//
//    private Long customerDetailsId;
//    private Long customerApiId;
//    @Getter
//    private Long customerDeviceId;
//
//    @Setter
//    private Long customerId;
//
//    public void setCustomerOtherId(Long customerApiId, Long customerDetailsId, Long customerDeviceId) {
//        this.customerApiId = customerApiId;
//        this.customerDetailsId = customerDetailsId;
//        this.customerDeviceId = customerDeviceId;
//    }
//
//    public AppCustomer toAppCustomer(ApplicationDto applicationDto, ChannelDTO channelDTO) {
//        AppCustomer customer = new AppCustomer();
//        if (customerId != null) {
//            customer.setId(customerId);
//        } else {
//            customer.setNickname("默认昵称-" + ToolUtils.getRandomNumString(8));
//        }
//        customer.setChannelId(channelDTO.getId());
//        customer.setAge(calculateAge(applicationDto.getIdNo()));  //计算年龄
//        customer.setPhone(EncryptUtil.AESencode(applicationDto.getPhoneNo(), encrypted));
//        customer.setSex(convertSex(applicationDto.getSex()));
//        customer.setCity(applicationDto.getLiveCity());
//        customer.setCityCode(Long.parseLong(applicationDto.getLiveCityCode()));
//        customer.setMd5(CommonUtils.safeMd5(applicationDto.getPhoneNo()));
//        customer.setSha256(EncryptUtil.getSHA256Str(applicationDto.getPhoneNo()));
//        customer.setStatus(0);
//        customer.setInterbankProduct(0);
//        customer.setIdCardApprove(Boolean.TRUE);
//        customer.setIsThereSweepFace(Boolean.TRUE);
//        customer.setApiIdCardApprove(Boolean.TRUE);
//        customer.setSupplementApprove(Boolean.TRUE);
//        customer.setBasicApprove(Boolean.TRUE);
//        customer.setDetailsApprove(Boolean.FALSE);
//        customer.setBankCardApprove(Boolean.FALSE);
//        customer.setLastLoginTime(LocalDateTime.now());
//        return customer;
//    }
//
//    // 计算年龄的方法
//    public static int calculateAge(String idCardNumber) {
//        String birthdayStr = idCardNumber.substring(6, 14);  // 假设生日在身份证号码的固定位置
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//        Date birthday = null;
//        try {
//            birthday = dateFormat.parse(birthdayStr);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        // 获取当前日期
//        Date currentDate = new Date();
//        Calendar cal1 = Calendar.getInstance();
//        cal1.setTime(birthday);
//        Calendar cal2 = Calendar.getInstance();
//        cal2.setTime(currentDate);
//
//        int age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
//
//        // 判断是否已过生日
//        if (cal2.get(Calendar.MONTH) < cal1.get(Calendar.MONTH)
//                || (cal2.get(Calendar.MONTH) == cal1.get(Calendar.MONTH)
//                && cal2.get(Calendar.DAY_OF_MONTH) < cal1.get(Calendar.DAY_OF_MONTH))) {
//            age--;
//        }
//        return age;
//    }
//
//    private int convertSex(String sex){
//        if("F".equals(sex)){
//            return 2;
//        }else{
//            return 1;
//        }
//    }
//
//    public AppCustomerDetails toAppCustomerDetails(ApplicationDto applicationDto) {
//        AppCustomerDetails details = new AppCustomerDetails();
//        if (customerDetailsId != null) {
//            details.setId(customerDetailsId);
//        }
//        details.setActualName(EncryptUtil.AESencode(applicationDto.getCustomerName(), encrypted));
//        details.setIdCard(EncryptUtil.AESencode(applicationDto.getIdNo(), encrypted));
//        details.setEducation(toEducation(applicationDto.getEducation()));
//        details.setProfessionalIdentity(converProfessionalIdentity(applicationDto.getOccupation()));
//        details.setBankPhone(EncryptUtil.AESencode(applicationDto.getBankPhoneNo(), encrypted));
//        details.setBankCardNumber(applicationDto.getBankCardNo());
//        details.setMonthlyIncome(5000L);
//        details.setCurrentWorkingAge(2);
//        details.setFormOfPayroll(1);
//        details.setCreditCard(1);
//        return details;
//    }
//
//
//    public AppCustomerDetailsApi toAppCustomerDetailsApi(ApplicationDto applicationDto) {
//        AppCustomerDetailsApi detailsApi = new AppCustomerDetailsApi();
//        if (customerApiId != null) {
//            detailsApi.setId(customerApiId);
//        }
//        detailsApi.setLengthOfService(2);
//        detailsApi.setCustomerCreditCard(1);
//        detailsApi.setCustomerFormOfPayroll(1);
//        detailsApi.setHighestEducation(toEducation(applicationDto.getEducation()));
////        if ("99990101".equals(basicInfo.getIdValidEnd())) {
////            //永久用户
////            detailsApi.setPeriodValidityDate(dateFormat(basicInfo.getIdValidStart()) + "-" + "长期");  //有效期
////        } else {
////            //2015.02.13-2025.02.13
////            detailsApi.setPeriodValidityDate(dateFormat(basicInfo.getIdValidStart()) + "-" + dateFormat(basicInfo.getIdValidEnd()));  //有效期
////        }
//        detailsApi.setAddressDetailed(applicationDto.getLiveAddress());
//        detailsApi.setCustomerMarriage(toMarried(applicationDto.getMaritalStatus()));    //婚姻
//        detailsApi.setNation(applicationDto.getNation()==null?"汉": applicationDto.getNation());   //民族
//        detailsApi.setIssueAgency(applicationDto.getIssuer());               //签收机关
//        detailsApi.setMonthlyIncome(new BigDecimal("5000"));
//        detailsApi.setLoanPurpose(toLoanPurpose(applicationDto.getLoanUsage()));
//        detailsApi.setIdentityAddress(applicationDto.getIdCardAddress());
//        //处理联系人
//        doContactsInfo(applicationDto.getLinkmanList(),detailsApi);
//        detailsApi.setUnitName(applicationDto.getCompanyName());
//        detailsApi.setUnitPhone(applicationDto.getPhoneNo());
//        detailsApi.setUnitDetailed(applicationDto.getDetailCompanyAddress());
//        String address=(applicationDto.getCompanyProvince().replace("省","")+"-"+applicationDto.getCompanyCity().replace("市","")+"-"+applicationDto.getCompanyArea());
//        String liveAddress=(applicationDto.getLiveProvince().replace("省","")+"-"+applicationDto.getLiveCity().replace("市","")+"-"+applicationDto.getLiveDistrict());
//        detailsApi.setUnitCity(address);  //单位地址
//        detailsApi.setAddressCity(liveAddress);   //居住区
//        detailsApi.setProfessionalIdentity(converProfessionalIdentity(applicationDto.getOccupation()));
//        detailsApi.setActualName(EncryptUtil.AESencode(applicationDto.getCustomerName(), encrypted));
//        detailsApi.setIdCard(EncryptUtil.AESencode(applicationDto.getIdNo(), encrypted));
//        return detailsApi;
//    }
//
//
//    private void doContactsInfo(List<LinkMan> linkManList, AppCustomerDetailsApi detailsApi){
//        //联系人必传两个，进件前有校验
//        List<LinkMan> contactsInfoConsort= linkManList.stream().filter(item->item.getRelation().equals("C")).collect(Collectors.toList());   //配偶
//        List<LinkMan> contactsInfoFamily= linkManList.stream().filter(item->item.getRelation().equals("F") || item.getRelation().equals("M")).collect(Collectors.toList());   //父母
//        if(contactsInfoConsort.size()>0){
//            //如果有配偶，优先设置第一联系人
//            LinkMan linkMan=contactsInfoConsort.get(0);
//            detailsApi.setEmergencyType(toEmergencyType(linkMan.getRelation()));
//            detailsApi.setEmergencyName(linkMan.getName());
//            detailsApi.setEmergencyPhone(linkMan.getPhone());
//            linkManList.remove(linkMan);
//            LinkMan ofenLinkMan=linkManList.get(0);
//            detailsApi.setOftenType(toOftenType(ofenLinkMan.getRelation()));
//            detailsApi.setOftenName(ofenLinkMan.getName());
//            detailsApi.setOftenPhone(ofenLinkMan.getPhone());
//        }else if(contactsInfoFamily.size()>0){
//            //如果有家人，优先设置第一联系人
//            LinkMan linkMan=contactsInfoFamily.get(0);
//            detailsApi.setEmergencyType(toEmergencyType(linkMan.getRelation()));
//            detailsApi.setEmergencyName(linkMan.getName());
//            detailsApi.setEmergencyPhone(linkMan.getPhone());
//            linkManList.remove(linkMan);
//            LinkMan ofenLinkMan=linkManList.get(0);
//            detailsApi.setOftenType(toOftenType(ofenLinkMan.getRelation()));
//            detailsApi.setOftenName(ofenLinkMan.getName());
//            detailsApi.setOftenPhone(ofenLinkMan.getPhone());
//        }else{
//            //其他情况，默认第一个为第一联系人，第二个默认第二联系人
//            if(linkManList.size()>0){
//                LinkMan linkMan=linkManList.get(0);
//                detailsApi.setEmergencyType(toEmergencyType(linkMan.getRelation()));
//                detailsApi.setEmergencyName(linkMan.getName());
//                detailsApi.setEmergencyPhone(linkMan.getPhone());
//            }
//            if(linkManList.size()>1){
//                LinkMan linkMan=linkManList.get(1);
//                detailsApi.setOftenType(toOftenType(linkMan.getRelation()));
//                detailsApi.setOftenName(linkMan.getName());
//                detailsApi.setOftenPhone(linkMan.getPhone());
//            }
//        }
//    }
//
//    /**
//     *    PN01 日常消费
//     * PN02 教育
//     * PN03 装修
//     * PN04 旅游
//     * PN05 婚庆
//     * PN06 健康医疗
//     * PN07 数码电器
//     *
//     * 1：个人日常消费 2：装修 3：旅游 4：教育 5：医疗 6：婚庆开销  7：购置车辆 8：购置家具家电 9：购置货物生产设备  10：创业经营
//     * @param loanUsage
//     * @return
//     */
//    private Integer toLoanPurpose(String loanUsage) {
//        if("PN01".equals(loanUsage)){
//            return 1;
//        } else if ("PN02".equals(loanUsage)) {
//            return 4;
//        } else if ("PN03".equals(loanUsage)) {
//            return 2;
//        } else if ("PN04".equals(loanUsage)) {
//            return 3;
//        } else if ("PN05".equals(loanUsage)) {
//            return 6;
//        } else if ("PN06".equals(loanUsage)) {
//            return 5;
//        } else if ("PN07".equals(loanUsage)) {
//            return 8;
//        }else{
//            return 1;
//        }
//    }
//
//    /**
//     * 0-其他
//     * 1-未婚
//     * 2-已婚
//     * 3-丧偶
//     * 4-离异
//     * 9-未说明的婚姻状况
//     *
//     *   1：已婚  2：未婚  3：丧偶  4：离异  5：再婚  6：未知
//     * @param maritalStatus
//     * @return
//     */
//    private Integer toMarried(String maritalStatus) {
//        if("1".equals(maritalStatus)){
//            return 2;
//        } else if ("2".equals(maritalStatus)) {
//            return 1;
//        } else if ("3".equals(maritalStatus)) {
//            return 3;
//        } else if ("4".equals(maritalStatus)) {
//            return 4;
//        } else  {
//            return 6;
//        }
//    }
//
//    /**
//     *       职业身份
//     *       A	国家机关、党群组织、企业、事业单位负责人
//     * B	行政机构、企事业单位管理人员
//     * C	专业技术人员
//     * D	商业、服务人员
//     * E	农、林、牧、渔、水利业生产人员
//     * F	工人（生产、加工、运输设备操作人员及有关人员)
//     * G	军人
//     * H	其他从业人员
//     * J	经济、金融、法律、教育从业人员
//     * K	公务员、行政机构办事人员和有关人员
//     * L	销售/中介/业务代表/促销
//     * M	保安/防损
//     * N	个体工商户
//     *
//     *    1：企事业单位负责人  2：专业技术人员 3：行政办事人员 4：商业/服务业人员  5：农/林/牧/渔/水利业生产人员 6：生产/运输设备操作人员 7：公务员 8：私营业主 9：个体工商户 10：自由职业
//     * @param occupation
//     * @return
//     */
//    private Integer converProfessionalIdentity(String occupation) {
//        if("A".equals(occupation)){
//            return 1;
//        } else if ("B".equals(occupation)||"K".equals(occupation)) {
//            return 3;
//        } else if ("C".equals(occupation)) {
//            return 2;
//        } else if ("D".equals(occupation)) {
//            return 4;
//        }else if("E".equals(occupation)){
//            return 5;
//        } else if ("F".equals(occupation)) {
//            return 10;
//        } else if ("G".equals(occupation)) {
//            return 10;
//        }else if("N".equals(occupation)){
//            return 9;
//        } else  {
//         return 10;
//        }
//    }
//
//
//    /**
//     *  doctor博士及以上
//     * master 硕士
//     * university 本科
//     * college 专科
//     * high 高中/中专
//     * middle 初中
//     * primary 初中以下
//     * @param education
//     *
//     *   1:初中及以下  2:高中  3：中专  4：大专  5：本科 6：研究生及以上
//     * @return
//     */
//    public static Integer toEducation(String education) {
//        if (StringUtils.hasText(education)) {
//            if ("doctor".equals(education)) {
//                return 6;
//            } else if ("master".equals(education)) {
//                return 6;
//            } else if ("university".equals(education)) {
//                return 5;
//            } else if ("college".equals(education)) {
//                return 4;
//            } else if ("high".equals(education)) {
//                return 2;
//            } else if ("middle".equals(education)) {
//                return 1;
//            }
//        }
//        return 1;
//    }
//
//    /**
//     *    1：配偶  2：父亲  3：母亲
//     * @param type
//     * @return
//     */
//    private Integer toEmergencyType(String type){
//        if ("C".equals(type)) {
//            return 1;
//        }else if ("F".equals(type)) {
//            return 2;
//        }else if ("M".equals(type)) {
//            return 3;
//        }
//        return 1;
//    }
//
//    /**
//     *    1：同事  2：朋友   3：父亲  4：母亲  5：兄弟  6：姐妹  7：子女  8：亲戚
//     *    C-配偶
//     * F-父亲
//     * M-母亲
//     * B-兄弟
//     * S-姐妹
//     * H-子女
//     * W-同事
//     * Y-朋友
//     * T-同学
//     * Q-其他亲属
//     * O-其他
//     * @param type
//     * @return
//     */
//    private Integer toOftenType(String type){
//        if("B".equals(type)){
//            return 5;
//        } else if ("S".equals(type)) {
//            return 6;
//        }else if ("H".equals(type)) {
//            return 7;
//        }else if ("W".equals(type)) {
//            return 1;
//        }else if ("Y".equals(type)) {
//            return 2;
//        }else if ("T".equals(type)) {
//            return 2;
//        }else if ("Q".equals(type)) {
//            return 8;
//        }else if ("O".equals(type)) {
//            return 2;
//        }
//        return 2;
//    }
//
//    public String otherConfig(ApplicationDto dto){
//        JSONObject backObject=new JSONObject();
//        backObject.put("professionType",toJob(dto.getOccupation()));//职业类别
//        backObject.put("inCome",5000);    //Long.parseLong(toMonthIncome(jobInfo.getIncomeMonth()).toString())
//        backObject.put("housingType",7);//默认其他
//        backObject.put("loanType",toLoanPurpose(dto.getLoanUsage()));//贷款类型
//        backObject.put("companyType",9);
//        backObject.put("liabilities",1);
//        backObject.put("unitPhone",dto.getPhoneNo());
//        return backObject.toJSONString();
//    }
//
//
//
//    /*
//融优花 1农业/林业/畜牧业/渔业  2采矿业
//3制造业/电子电工 4电力/热力/燃气/水生产/供应业 5建筑业 6商超/百货/零售业 7交通运输/仓储业/邮政业
//8住宿/餐饮/娱乐/旅游业 9信息传输/软件/信息技术服务业 10金融业 11房地产业 12租赁/商务服务业 13科学研究/技术服务业 14水利/环境/公共设施管理业 15居民服务/修理/其他服务业
//16教育/艺术 17医疗卫生和社会工作 18文化/传媒/体育/娱乐业 19公共管理/社会保障和社会组织
//
//   A 机关事业单位
// F 国有企业
// G集体企业
// H股份合作企业
// J有限责任公司
// K股份有限公司
// L私营企业
// M外商投资企业(含港、澳、台)
// N中外合资经营企业(含港、澳、台)
// O中外合作经营企业(含港、澳、台)
// P外资企业(含港、澳、台)
// Q外商投资股份有限公司(含港、澳、台)
// R个体经营
// Z其他
//
//
//*/
//    public Integer toIndustry(String industry){
//        if(Objects.isNull(industry)){
//            return 1;
//        }
//        switch (industry){
//            case "A":  return 19;
//            case "F":  return 19;
//            case "INDU000003":  return 3;
//            case "INDU000004": return 4;
//            case "INDU000005": return 5;
//            case "INDU000006": return 7;
//            case "INDU000007": return 9;
//            case "INDU000008": return 6;
//            case "INDU000009": return 8;
//            case "INDU0000010": return 10;
//            case "INDU0000011": return 11;
//            case "INDU0000012": return 12;
//            case "INDU0000013": return 13;
//            case "INDU0000014": return 14;
//            case "INDU0000015": return 15;
//            case "INDU0000016": return 16;
//            case "INDU0000017": return 17;
//            case "INDU0000018": return 18;
//            case "INDU0000019": return 19;
//            case "INDU0000020": return 19;
//            case "INDU0000021": return 1;
//        }
//        return 1;
//    }
//    //    /
////           *   融优花 1企事业单位负责人 2专业技术人员 3行政办事人员 4 商业/服务业人员 5 农/林/牧/渔/水利业生产人员 6生产/运输设备操作人员
////        *   7公务员 8私营业主 9 个体工商户 10 自由职业 11退休 12 其他
////        */
//    public Integer toJob(String job){
//        if(Objects.isNull(job)){
//            return 12;
//        }
//        switch (job){
//            case "OCCU000001": return 6;
//            case "OCCU000002": return 1;
//            case "OCCU000003": return 1;
//            case "OCCU000004": return 10;
//            case "OCCU000005": return 2;
//            case "OCCU000006": return 5;
//            case "OCCU000007": return 11;
//            case "OCCU000008": return 11;
//        }
//        return 12;
//    }
//}
