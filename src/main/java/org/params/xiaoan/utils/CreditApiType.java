package org.params.xiaoan.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @program: xiaoanfenqi
 * @description: 信贷api产品
 * @author: LiYu
 * @create: 2023-06-21 15:14
 **/
@Getter
@AllArgsConstructor
@ToString
public enum CreditApiType {
    LV_XIN(1, "绿信", "吉客有钱",true,1),
    XIN_HU(2, "信狐", "51信狐",true,1),
    KUAI_YI_DAI(3, "快易贷", "快易贷",true,1),
    LE_YOU_HUA(4, "乐优花", "乐优花",true,1),
    XIN_YE_BANG(5, "信业帮", "信业帮",true,1),
    YOU_XIN(6, "有信", "融逸花",true,1),
    YI_XIN(7, "亿信", "亿信",true,1),
    HENG_XIANG_HUA(8, "恒享花", "恒享花",true,1),
    PAI_YI_DAI(9, "拍易贷", "拍易贷",true,1),
    QU_JI_HUA(10, "趣记花", "趣记花",false,2),
    QI_DAI(11, "期贷", "期贷",true,1),
    HAO_HUI_TUI(12, "好汇推", "好汇推",true,1),
    HAO_XIN(13, "好信", "好信",true,1),
    CHENG_YI_TONG(14, "橙易通", "橙易通",true,1),

    DA_MAI(15, "大麦", "大麦",true,1),
    YOU_QIAN_LAI(16, "有钱来", "有钱来",true,1),
    QI_HUI_TONG(17, "企惠通", "企惠通",true,1),
    HAI_YI_TUI(18, "嗨易推", "嗨易推",true,1),
    WEI_QIAN_BAO(19, "微钱包", "51微卡包",false,2),
    AN_XIN_JIE_QIAN(20, "安信借钱", "安信借钱",true,1),
    WEI_YIN_XIN_YONG(21, "微银信用", "微银信用",true,1),
    HAO_HAN(22, "浩瀚", "浩瀚有借",true,1),
    //智富金服
    ZHI_FU_JIN_FU(23, "智富金服", "智富金服",true,1),
    //乐呗
    LE_BEI(24, "乐呗", "乐呗",true,1),
    KUAI_YI_DAI_WEI(24, "快易贷尾量", "快易贷尾量",true,1),
    //融鑫花
    RONG_XIN_HUA(25, "融鑫花", "融鑫花",false,1),
    RONG_YOU_TUI(26, "融优推", "融优推",false,1),
    //阳薪花
    YANG_XIN_HUA(27, "阳薪花", "阳薪花",false,1),
    QIAN_XIAO_HUA(28, "乾小花", "乾小花",false,1),
    XIAO_AN_API(29, "小安分期", "小安分期",false,1),
    CHENG_YI_TONG_V2(30, "橙易通V2", "橙易通V2",false,2),
    HAO_HAN_YOU_JIE(31, "浩瀚有借", "浩瀚有借",false,1),
    JI_YI_FEN_QI_API(32, "极易分期", "极易分期",true,2),
    YOU_QIAN_LAI_V2(33, "有钱来V2", "有钱来V2",false,2),
    HUI_RONG(34, "惠融", "惠融",true,2),
    TONG_CHENG_JIE(35, "同城借", "同城借",true,2),
    DIAN_DIAN_FEN_QI(36, "点点分期", "点点分期",false,2),
    HU_RONG_ZHI_DAI(37, "互融智贷", "互融智贷",false,2),
    CHENGYI_RONG(38, "诚易融", "诚易融",true,2),
    LONG_XIANG_HUA(39, "龙享花", "龙享花",false,2),
    CHENG_FEN_QI(40, "橙分期", "橙分期",false,2),
    JI_YI_FEN_QI_V2(41, "极易分期V2", "极易分期V2",false,2),
    RONG_AN_FEN_QI(42, "融安分期V2", "融安分期V2",false,2),
    XIN_YONG_MIAO_JIE(43, "信用秒借", "信用秒借",false,2),
    LING_YONG_HUA_V2(44, "灵用花V2", "灵用花V2",false,2),
    LING_YONG_HUA(45, "灵用花", "灵用花",false,1),
    BA_JIE_JIN_FU(46, "八戒金服", "八戒金服",false,2),
    ;
    /**
     * 产品序号
     */
    private final Integer code;
    /**
     * 产品描述
     */
    private final String info;
    /**
     * 产品名称
     */
    private final String productName;
    /**
     * 是否是第三方文档
     */
    private final Boolean thirdPart;

    private final Integer version;

    /**
     * 根据名称获取枚举
     *
     * @param enumName 枚举名称
     * @return 枚举
     */
    public static CreditApiType getByEnumName(String enumName) {
        //字符串转大写
        enumName = enumName.toUpperCase();
        for (CreditApiType creditApiType : CreditApiType.values()) {
            if (creditApiType.name().equals(enumName)) {
                return creditApiType;
            }
        }
        return null;
    }

    /**
     * 根据产品名称获取枚举
     *
     * @param info 产品名称
     * @return 枚举
     */
    public static CreditApiType getByProductName(String info) {
        for (CreditApiType creditApiType : CreditApiType.values()) {
            if (info.contains(creditApiType.getProductName())) {
                return creditApiType;
            }
        }
        String[]productNames={"鑫贷","网融贷","融通乐","百易客","融易宝" ,"优易通","银富通" ,"微融易" ,"融捷通" ,"惠利通","融汇通","惠金猫","聚易宝" ,"豆融宝","微诚邦","鼎诺威","集客宝" ,"融旺" ,"微融益" ,"通泰富" ,"e诺金" ,"微启泰","微贷宝" ,"富易贷" ,"融鑫达" ,"微金盈" ,"惠民融易" ,"微聚惠" ,"民融通" ,"恒亿创盈" ,"宝立客" ,"惠民融" ,"储贷宝" ,"融资酷" ,"汇业宝" ,"微贷鑫","聚业富","易通达","鑫e贷","民融易","金易达","微聚通","优聚宝","微鑫通","融聚宝","易通花","盛鑫融","惠通达","薪易通","极速易"};
        for (String name : productNames) {
            if (name.equals(info)) {
                return CreditApiType.KUAI_YI_DAI;
            }
        }
        return null;
    }

}
