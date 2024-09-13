package org.params.xiaoan.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 渠道类型
 * @author luona
 * @date 2024年03月22日 9:21
 */
@Getter
@AllArgsConstructor
@ToString
public enum ChannelType {
    RONG_YOU_HUA(0, "融优花",false),
    LV_XIN(1, "绿信",true),
    JI_XIANG(2, "极享",true),
    REN_TIAN_HUA(3, "仁天花", true),
    YOU_QIAN_LAI(4, "有钱来", true),
    WEI_YONG(5, "微用", true),
    JI_YOU(6, "桔柚花", true),
    JI_XIANG_YOU_QIAN(7, "极享有钱", true),
    HUI_YI_DAI(8, "惠易贷", true),
    ;
    /**
     * 渠道序号
     */
    private final Integer code;
    /**
     * 渠道名称
     */
    private final String channelName;
    /**
     * 是否是第三方文档
     */
    private final Boolean thirdPart;

    /**
     * 根据名称获取枚举
     *
     * @param enumName 枚举名称
     * @return 枚举
     */
    public static ChannelType getByEnumName(String enumName) {
        //字符串转大写
        enumName = enumName.toUpperCase();
        for (ChannelType channelType : ChannelType.values()) {
            if (channelType.name().equals(enumName)) {
                return channelType;
            }
        }
        return null;
    }
}
