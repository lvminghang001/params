package org.params.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.params.common.StringUtils;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_DATE;

/**
 * 时间工具类
 *
 * @author jieyihua
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private static Date nowDate=new Date();
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static LocalDateTime getNowDate() {
        return LocalDateTime.now();
    }

    public static Date getDateNow() {
        return nowDate;
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        return DateFormatUtils.format(nowDate, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
//        return DateFormatUtils.format(nowDate, "yyyyMMdd");
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    //当前时间的  前后多少秒
    //date当前时间
    //往后   second>0  往前  second<0
    public static Date jumpSecond(Date date, int second) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + second);
            return calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //距离当前时间还有多少秒
    public static Long surplusSecond(Date date) {
        long diff = date.getTime() - getDateNow().getTime();
        long sec = diff/ 1000;
        return sec;
    }
    //获取当日剩余秒数
    public static Long todaySurplusSecond(){
        try{
            long dateLong = System.currentTimeMillis();
            String localDateStr=LocalDate.now().plusDays(1).toString()+" 00:00:00";
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            return (simpleDateFormat.parse(localDateStr).getTime() - dateLong)/1000;
        }catch (Exception e){
            return 0L;
        }
    }
    //获取次日凌晨0点日期
    public static String getNextDay(String str){
        LocalDate parse = LocalDate.parse(str, ISO_DATE);
        return parse.plusDays(1).toString()+" 00:00:00";
    }
    /**
     @param: 入参是当前时间2020-03-01
     @return:返参是前一天的日期,理应为2020-02-29(闰年)
     */
    public static String getBeforeDay(String dateTime){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            date=simpleDateFormat.parse(dateTime);
        }catch (ParseException e){

        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        //往前一天
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static long getRemainSecondsOneDay(Date currentDate) {
        //使用plusDays加传入的时间加1天，将时分秒设置成0
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                        ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        //使用ChronoUnit.SECONDS.between方法，传入两个LocalDateTime对象即可得到相差的秒数
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return seconds;
    }

    public static void main(String[] args) throws ParseException {
//        Date da = new Date();
//        System.out.println(da);
//        System.out.println(surplusSecond(jumpSecond(da, 300)));
//        System.out.println(nowDate.after(jumpSecond(da,-60)));
//        //System.out.println(todaySurplusSecond());
//        System.out.println(getTodayExpireTime());
     //   System.out.println(strToLocalDate("2021-05-02"));
        System.out.println(todaySurplusSecond());
        System.out.println(getRemainSecondsOneDay(new Date()));
    }

    public static List<String> getStartToEndTimeHours(String dateFirst, String dateSecond,Integer period) {
        try{
          List<String>dates=new ArrayList<>();
          boolean b = StringUtils.hasText(dateFirst) && StringUtils.hasText(dateSecond);
          LocalDate endDate = LocalDate.now();
          LocalDate startDate = LocalDate.now().minusDays(30);
          if(b){
               startDate = LocalDate.parse(dateFirst , DateTimeFormatter.ofPattern("yyyy-MM-dd"));
               endDate =  LocalDate.parse(dateSecond , DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          }
          if(endDate.isAfter(LocalDate.now())){
              endDate=LocalDate.now();
          }

          List<LocalDate> descDateList = getDescDateList(startDate, endDate);
            CollectionUtil.reverse(descDateList);
            for (LocalDate localDate : descDateList) {
                //如果日期等于当天
                if(localDate.toString().equals(LocalDate.now().toString())){
                    for (int i = 0; i <= LocalDateTime.now().getHour(); i++) {
                        if(i<10){
                            if(period!=null&&i!=period){
                                continue;
                            }
                            String date=localDate+" 0"+i+":00:00";
                            dates.add(date);
                        }else{
                            if(period!=null&&i!=period){
                                continue;
                            }
                            String date=localDate+" "+i+":00:00";
                            dates.add(date);
                        }
                    }
                }else{
                    for (int i = 0; i <= 23; i++) {
                        if(i<10){
                            if(period!=null&&i!=period){
                                continue;
                            }
                            String date=localDate+" 0"+i+":00:00";
                            dates.add(date);
                        }else{
                            if(period!=null&&i!=period){
                                continue;
                            }
                            String date=localDate+" "+i+":00:00";
                            dates.add(date);
                        }
                    }

                }

            }
            CollectionUtil.reverse(dates);
           return dates;
        }
        catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 计算两个日期之间的所有日期
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<LocalDate>  getDescDateList(LocalDate startDate,LocalDate endDate) {
        List<LocalDate> result = new ArrayList<>();
        if(endDate.compareTo(startDate) < 0 ){
            return  result;
        }
        while (true){
            result.add(endDate);
            if(endDate.compareTo(startDate) <= 0){
                break;
            }
            endDate= endDate.plusDays(-1);
        }
        return result;
    }
    //获取当日还剩多少秒
    public static Long getTodayExpireTime() throws ParseException {
        long time = new Date().getTime();
        LocalDate localDate = LocalDate.now().minusDays(-1);
        String endDate=localDate.toString()+" 00:00:00";
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date parse = format.parse(endDate);
        long endTime=parse.getTime()-time;
        return endTime/1000;
    }


    /**
     * 判断是否为工作日 - 简单判断 - 不严谨
     * @return
     */
    public static boolean checkTimeIsWork() {
        boolean flag = false;
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            Date testdate = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(testdate);
            int x = cal.get(Calendar.DAY_OF_WEEK);
            switch (x) {
                case Calendar.MONDAY:
                case Calendar.TUESDAY:
                case Calendar.WEDNESDAY:
                case Calendar.THURSDAY:
                case Calendar.FRIDAY:
                    flag = true;
                    break;
                case Calendar.SATURDAY:
                case Calendar.SUNDAY:
                    flag = false;
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        if (nowTime.getTime() == beginTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean inTime(String start, String end){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm"); // 设置日期格式
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
//            now = df.parse("22:01");
            beginTime = df.parse(start);
            endTime = df.parse(end);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean flag = belongCalendar(now, beginTime, endTime);
        return flag;
    }


    public static long getTakeTime(long startTime, long endTime){
        return endTime - startTime;
    }

    //字符串转LocalDate
    public static LocalDate strToLocalDate(String str){
        DateTimeFormatter d=new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter();
        LocalDate dt = LocalDate.parse(str, d);
        return dt;
    }
    //字符串转Date
    public static Date strToDate(String str) {
       try {
           SimpleDateFormat format=new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
           return format.parse(str);
       }catch (Exception e){
           return null;
       }
    }

    //字符串转Date
    public static Date strToDate(String str, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(str);
        }catch (Exception e){
            return null;
        }
    }
    //LocaDateTime转字符串
    public static String localDateTimeToStr(LocalDateTime localDateTime) {
        try {
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return localDateTime.format(formatter);
        }catch (Exception e){
            return null;
        }
    }

    //比较两个日期是否为同月
    public static boolean isSameMonth(LocalDate date1, LocalDate date2) {
        return date1.getYear() == date2.getYear() && date1.getMonthValue() == date2.getMonthValue();
    }

    public static String toString(Date date, String format) {
        String dateStr = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dateStr = sdf.format(date);
        } catch (Exception e) {
        }
        return dateStr;
    }

    public static String stampToDate(Long timestamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }
}
