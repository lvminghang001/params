package org.params.shanqian.req;

import lombok.Data;

@Data
public class ShanQianPushDetailsReq {

    private String mobile;
    private String name;
    private Integer gender; //1男 2女
    private Integer age;
    private String city; //城市
    private Integer quota;// 申请额度 1代表5w, 2代表10w, 3代表20w, 4代表50w 5代表100w
    private Integer job; // 1民企/外企 2自由职业 3企业主/个体户 4国企/公务员/事业单位
    private Integer house; //房产信息 1 无房产 2有房不抵押 3有房可抵押
    private Integer car; //车产信息 1无车辆 2按揭车 3全款车
    private Integer zm;// 2-600分以下 3-600-650 4-650-700 5-700+
    private Integer social; // 社保情况 1: ⽆社保 2:未满6个⽉ 3:6个⽉以上
    private Integer funds; //公积金 公积⾦情况 1: ⽆公积⾦ 2:未满6个⽉ 3:6个⽉以上
    private Integer insurance;//保单情况 1: ⽆保单 2: 缴纳未满⼀年 3: 缴纳⼀年以上
    private Integer overdue;// 逾期 是否逾期 1未逾期 2有逾期
    private String client_ip; // 用户ip
    private String order_num; //预撞库时返回的orderId
    private String agreement; //进件协议

}
