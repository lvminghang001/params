package org.params.longxianghua.dto;

import lombok.Data;

@Data
public class LxhBaseParams {

    /** 渠道编码 */
    private String channelCode;

    /**Aes(姓名)*/
    private String name;

    /** Aes(身份证号码) */
    private String idCard;

    /** 城市名称 */
    private String city;

    /** 额度 10000-（3-5万） 50000-（5-20万） 100000-（20万以上） */
    private Integer money;

    /** 性别 1: 男 2: 女 */
    private Integer sex;

    /** 年龄 */
    private Integer age;

    /** 设备 1: 安卓 2: iOS */
    private Integer device;

    /** 用户IP */
    private String ip;

    /** 芝麻分 1: 大于700 2: 650-700 3: 600-650 4: 小于600 */
    private Integer sesameScore;

    /** 车 1: 有 0: 无 */
    private Integer car;

    /** 房 1: 有 0: 无 */
    private Integer house;

    /** 公积金 1: 有 0: 无 */
    private Integer fund;

    /** 社保 1: 有 0: 无 */
    private Integer social;

    /** 保单 1: 有 0: 无 */
    private Integer insurance;

    /** 营业执照 1: 有 0: 无 */
    private Integer businessLicense;

    /** 白条 1: 有 0: 无 */
    private Integer baiTiao;

    /** 花呗 1: 有 0: 无 */
    private Integer huaBei;

    /** 信用卡 1: 有 0: 无 */
    private Integer creditCard;

    /** 逾期 1: 有 0: 无 */
    private Integer credit;

    public String toString(){
        return "channelCode="+channelCode+",name="+name+",idCard="+idCard+",city="+city+",money="+money+",sex="+sex+",age="+age+",device="+device+",ip="+ip+",sesameScore="+sesameScore+",car="+car+",house="+house+",fund="+fund+",social="+social+",insurance="+insurance+",businessLicense="+ businessLicense +",baiTiao="+baiTiao+",huaBei="+huaBei+",creditCard="+creditCard+",credit="+credit;
    }
}
