package org.params.yql.utils;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.crypto.SecureUtil;
import org.params.common.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: JieYiHua-Cloud
 * @description: 通用工具类
 * @author: LiYu
 * @create: 2021-08-12 18:03
 **/
public class CommonUtils {
    private final static Integer INITIAL_DAYS = 7;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取应得积分
     *
     * @param signInDays 签到天数
     * @return 积分数量
     */
    public static Integer numberOfPoints(Integer signInDays) {
        if (signInDays > INITIAL_DAYS) {
            //七天以上直接返回50
            return 50;
        }
        return 20 + (signInDays - 1) * 5;
    }

    /**
     * 整数相除保留两位小数
     *
     * @param molecular   分子
     * @param denominator 分母
     * @return 结果
     */
    public static String divide(int molecular, int denominator) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (denominator == 0) {
            return "0.00";
        }
        return df.format((float) molecular / denominator * 100);
    }

    public static Integer calculateAge(String birthYear) {
        if (birthYear == null || birthYear.length() < 4) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //得到当前的年份
        String cYear = sdf.format(new Date()).substring(0, 4);
        String cMouth = sdf.format(new Date()).substring(5, 7);
        String cDay = sdf.format(new Date()).substring(8, 10);
        //得到生日年份
        String birth_Year = birthYear.substring(0, 4);
        String birth_Mouth = birthYear.substring(4, 6);
        String birth_Day = birthYear.substring(6, 8);
        int age = Integer.parseInt(cYear) - Integer.parseInt(birth_Year);
        if ((Integer.parseInt(cMouth) - Integer.parseInt(birth_Mouth)) < 0) {
            age = age - 1;
        } else if ((Integer.parseInt(cMouth) - Integer.parseInt(birth_Mouth)) == 0) {
            if ((Integer.parseInt(cDay) - Integer.parseInt(birth_Day)) > 0) {
                age = age - 1;
            } else {
                age = Integer.parseInt(cYear) - Integer.parseInt(birth_Year);
            }
        } else if ((Integer.parseInt(cMouth) - Integer.parseInt(birth_Mouth)) > 0) {
            age = Integer.parseInt(cYear) - Integer.parseInt(birth_Year);
        }
        return age;
    }

    public static String modifyId(String idCard, Integer age) {
        if (idCard.length() != 18) {
            return null;
        }
        return idCard.substring(0, 6) + LocalDateTime.now().minusYears(age + 1).getYear() + idCard.substring(10);
    }

    /**
     * 18位身份证获取性别和年龄
     *
     * @param CardCode
     * @return
     * @throws Exception
     */
    public static Map<String, Object> identityCard18(String CardCode) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 得到年份
//        String year = CardCode.substring(6).substring(0, 4);
//        // 得到月份
//        String month = CardCode.substring(10).substring(0, 2);
        //得到日
        //String day=CardCode.substring(12).substring(0,2);
        String sex;
        // 判断性别
        if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {
            sex = "2";
        } else {
            sex = "1";
        }
        // 得到当前的系统时间
//        Date date = new Date();
//        // 当前年份
//        String currentYear = format.format(date).substring(0, 4);
//        // 月份
//        String currentMonth = format.format(date).substring(5, 7);
//        //String currentdDay=format.format(date).substring(8,10);
////        int age = 0;
        int age = IdcardUtil.getAgeByIdCard(CardCode);
        // 当前月份大于用户出身的月份表示已过生日
//        if (Integer.parseInt(month) <= Integer.parseInt(currentMonth)) {
//            age = Integer.parseInt(currentYear) - Integer.parseInt(year) + 1;
//        } else {
//            // 当前用户还没过生日
//            age = Integer.parseInt(currentYear) - Integer.parseInt(year);
//        }
        map.put("sex", sex);
        map.put("age", age);
        return map;
    }

    public static void main(String[] args) {
        String idCard = "339005199509142130";
        int age = IdcardUtil.getAgeByIdCard(idCard);
        System.out.println("age:" + age);
    }

    /**
     * 15位身份证获取性别和年龄
     *
     * @param card
     * @return
     * @throws Exception
     */
    public static Map<String, Object> identityCard15(String card) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        //年份
//        String year = "19" + card.substring(6, 8);
//        //月份
//        String yue = card.substring(8, 10);
        //日
        //String day=card.substring(10, 12);
        String sex;
        if (Integer.parseInt(card.substring(14, 15)) % 2 == 0) {
            sex = "2";
        } else {
            sex = "1";
        }
        // 得到当前的系统时间
//        Date date = new Date();
//        //当前年份
//        String currentYear = format.format(date).substring(0, 4);
//        //月份
//        String currentMonth = format.format(date).substring(5, 7);
//        //String fday=format.format(date).substring(8,10);
//        int age = 0;
//        //当前月份大于用户出身的月份表示已过生日
//        if (Integer.parseInt(yue) <= Integer.parseInt(currentMonth)) {
//            age = Integer.parseInt(currentYear) - Integer.parseInt(year) + 1;
//        } else {
//            // 当前用户还没过生日
//            age = Integer.parseInt(currentYear) - Integer.parseInt(year);
//        }
        int age = IdcardUtil.getAgeByIdCard(card);
        map.put("sex", sex);
        map.put("age", age);
        return map;
    }

    public static Object getObjDefault(Object obj) {
        // 得到类对象
        Class objCla = obj.getClass();
        Field[] fs = objCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];

            // 设置些属性是可以访问的
            boolean isStatic = Modifier.isStatic(f.getModifiers());
            if (isStatic) {
                continue;
            }
            // 设置些属性是可以访问的
            f.setAccessible(true);
            try {
                // 得到此属性的值
                Object val = f.get(obj);
                // 得到此属性的类型
                String type = f.getType().toString();
                if (type.endsWith("String") && val == null) {
                    // 给属性设值
                    f.set(obj, "");
                } else if ((type.endsWith("int") || type.endsWith("Integer") || type.endsWith("double")) && val == null) {
                    f.set(obj, 0);
                } else if ((type.endsWith("long") || type.endsWith("Long")) && val == null) {
                    f.set(obj, 0L);
                } else if (type.endsWith("Timestamp") && val == null) {
                    f.set(obj, Timestamp.valueOf("1970-01-01 00:00:00"));
                } else if (type.endsWith("BigDecimal") && val == null) {
                    f.set(obj, new BigDecimal(0));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static List removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }


    public static void main1(String[] args) throws Exception {
//        System.out.println(identityCard18("111111111111111111"));
        System.out.println(getSex("530121198903119561"));
    }


    /**
     * 0女 1男
     *  根据身份证号判断用户性别
     * @param cardNo
     * @return
     */
    public static Integer getSex(String cardNo) {
        String sexStr = "0";
        if (cardNo.length() == 15) {
            sexStr = cardNo.substring(14, 15);
        } else if (cardNo.length() == 18) {
            sexStr = cardNo.substring(16,17);
        }
        int sexNo = Integer.parseInt(sexStr);
        return sexNo % 2 == 0 ? 0 : 1;
    }

    /**
     * 安全的md5加密
     * 同字符多次加密 - 结果一致
     * @param str 字符串
     * @return 结果
     */
    public static String safeMd5(String str) {
        return StringUtils.hasText(str) ? isMD5(str) ? str : SecureUtil.md5(str) : null;
    }

    /**
     * 判断是否为md5加密
     * @param str 字符串
     * @return 结果
     */
    public static boolean isMD5(String str) {
        return str.matches("^[a-f0-9]{32}$");
    }

    /**
     * 省份证的正则表达式^(\d{15}|\d{17}[\dx])$
     * @param idCard    省份证号
     * @return    生日（yyyy-MM-dd）
     */
    public static String getBirthday(String idCard){

        String year = null;
        String month = null;
        String day = null;
        //正则匹配身份证号是否是正确的，15位或者17位数字+数字/x/X
        if (idCard.matches("^\\d{15}|\\d{17}[\\dxX]$")) {
            year = idCard.substring(6, 10);
            month = idCard.substring(10,12);
            day = idCard.substring(12,14);
        }else {
            System.out.println("身份证号码不匹配！");
            return null;
        }
        return year + "-" + month + "-" + day;
    }
}
