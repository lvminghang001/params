package org.params.common.utils;

import java.math.BigDecimal;

/**
 * PMT计算工具类 - 用于计算等额本息贷款的每期还款金额
 * 公式: PMT = (r * PV) / (1 - (1 + r)^(-n))
 */
public class PMTUtil {

    /**
     * 计算等额本息贷款的每期还款金额（基础版本）
     *
     * @param rate 每期利率（年利率需除以12转换为月利率）
     * @param nper 总还款期数
     * @param pv 贷款本金（现值）
     * @return 每期还款金额
     */
    public static double calculatePMT(double rate, int nper, double pv) {
        if (rate == 0) {
            return pv / nper;
        }
        double denominator = 1 - Math.pow(1 + rate, -nper);
        return (rate * pv) / denominator;
    }

    /**
     * 基于年利率计算月供（更方便的版本）
     *
     * @param annualRate 年利率（如5%表示为0.05）
     * @param years 贷款年限
     * @param principal 贷款本金
     * @return 每月还款金额
     */
    public static double calculateMonthlyPayment(double annualRate, int years, double principal) {
        double monthlyRate = annualRate / 12;
        int totalMonths = years * 12;
        return calculatePMT(monthlyRate, totalMonths, principal);
    }

    /**
     * 完整的Excel风格PMT函数实现（包含可选参数）
     *
     * @param rate 各期利率
     * @param nper 总期数
     * @param pv 现值（本金）
     * @param fv 未来值（默认为0）
     * @param type 付款类型（0-期末付款，1-期初付款）
     * @return 每期付款金额
     */
    public static double calculatePMTExcelStyle(double rate, int nper, double pv, double fv, int type) {
        if (rate == 0) {
            return -(pv + fv) / nper;
        }

        double temp = Math.pow(1 + rate, nper);
        double pmt = (-pv * temp - fv) / ((1 + rate * type) * (temp - 1) / rate);

        // 四舍五入保留两位小数
        return new BigDecimal(pmt).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 生成还款计划表
     */
    public static void generatePaymentSchedule(double annualRate, int years, double principal) {
        double monthlyRate = annualRate / 12;
        int totalMonths = years * 12;
        double monthlyPayment = calculateMonthlyPayment(annualRate, years, principal);

        double balance = principal;
        double totalInterest = 0;

        System.out.println("期次\t月供\t本金\t利息\t剩余本金");
        System.out.println("---------------------------------------------------");

        for (int i = 1; i <= totalMonths; i++) {
            double interest = balance * monthlyRate;
            double principalPaid = monthlyPayment - interest;
            balance -= principalPaid;
            totalInterest += interest;

            System.out.printf("%d\t%.2f\t%.2f\t%.2f\t%.2f\n",
                    i, monthlyPayment, principalPaid, interest, Math.max(0, balance));
        }

        System.out.println("---------------------------------------------------");
        System.out.printf("总利息: %.2f, 总还款额: %.2f\n", totalInterest, monthlyPayment * totalMonths);
    }

    /**
     * 测试用例
     */
    public static void main(String[] args) {
        // 示例1: 10万元贷款，年利率5%，期限5年
        double pmt1 = calculateMonthlyPayment(0.05, 5, 100000);
        System.out.printf("示例1 - 月供: %.2f元\n", pmt1);

        // 示例2: 50万元房贷，年利率4.5%，期限30年
        double pmt2 = calculateMonthlyPayment(0.029, 30, 1000000);
        System.out.printf("示例2 - 月供: %.2f元\n", pmt2);

        // 示例3: 使用Excel风格函数
        double pmt3 = calculatePMTExcelStyle(0.05/12, 60, 100000, 0, 0);
        System.out.printf("示例3 - Excel风格PMT: %.2f元\n", pmt3);

        // 生成还款计划表示例
        System.out.println("\n还款计划表示例:");
        generatePaymentSchedule(0.05, 2, 10000);
    }
}