package org.params.sort.Afternoon; // 声明当前类所在的包。

import java.util.ArrayList; // 导入动态数组实现类。
import java.util.Collections; // 导入集合反转工具类。
import java.util.List; // 导入 List 接口。

/**  0/1 背包动态规划实现。
 *   有 n 件物品和一个容量为 C 的背包。第 i 件物品的重量为 weight[i]，价值为 value[i]。每件物品只有两种选择：
 * - 放入背包一次；
 * - 不放入背包。
 * 在背包总重量不超过容量 C 的前提下，求所能获得的最大总价值。
 *
 *    目标：    1.能用一句话定义状态：dp[i][j] 的含义。
 *              答：在前 i 件物品中，总容量不超过 j 的情况下，能够获得的最大的总价值
             * 2.能独立写出“不选”和“选”的转移式。
             * 3.能解释初始化为什么是 dp[0][j] = 0。
             * 4.能手工填一个小型 dp 表。
             * 5.能从 dp[n][capacity] 倒推出选了哪些物品。
             * 6.能写出时间复杂度 O(nC)、空间复杂度 O(nC)，并理解滚动数组可优化为 O(C)。
 *
 * */
public class Knapsack01DynamicProgramming { // 定义 0/1 背包求解器。

    /** 求解 0/1 背包问题。 */
    /**
     *
     * @param weight  重量数组
     * @param value  价值数组
     * @param capacity  背包容量
     * @return
     */
    public Result solve(int[] weight, int[] value, int capacity) { // 定义求解入口方法。
        if (weight == null || value == null || weight.length != value.length || capacity < 0) { // 校验输入参数是否合法。
            throw new IllegalArgumentException("物品重量、价值数组必须等长，且容量不能为负数"); // 输入不合法时抛出异常。
        } // 结束输入校验。

        int n = weight.length; // n 表示物品总数。
        int[][] dp = new int[n + 1][capacity + 1]; // dp[i][c] 表示前 i 个物品放入容量为 c 的背包时的最大价值。

        for (int i = 1; i <= n; i++) { // 依次考虑第 1 到第 n 个物品。
            for (int c = 0; c <= capacity; c++) { // 依次计算每一种背包容量。
                dp[i][c] = dp[i - 1][c]; // 不选第 i 个物品时，最大价值等于前 i-1 个物品的最优值。

                if (weight[i - 1] <= c) { // 若第 i 个物品的重量不超过当前容量，则可以选择它。
                    int selectedValue = dp[i - 1][c - weight[i - 1]] + value[i - 1]; // 计算选择第 i 个物品后的总价值。
                    dp[i][c] = Math.max(dp[i][c], selectedValue); // 在“选”与“不选”之间取价值较大的方案。
                } // 结束能否装入当前物品的判断。
            } // 当前物品对应的所有容量均已计算完毕。
        } // 所有状态均已填表完成。

        List<Integer> selectedItems = new ArrayList<>(); // 创建集合，用于保存被选中物品的下标。
        int remainingCapacity = capacity; // 记录逆向寻找最优解时的剩余容量。

        for (int i = n; i >= 1; i--) { // 从最后一个物品开始逆向追踪决策。
            if (dp[i][remainingCapacity] != dp[i - 1][remainingCapacity]) { // 若最优值发生变化，说明第 i 个物品被选中。
                selectedItems.add(i - 1); // 保存第 i 个物品的数组下标。
                remainingCapacity -= weight[i - 1]; // 扣除该物品占用的背包容量。
            } // 未变化时表示没有选择第 i 个物品。
        } // 最优解中的所有物品均已追踪完毕。

        Collections.reverse(selectedItems); // 将物品下标恢复为从小到大的顺序。
        return new Result(dp[n][capacity], selectedItems); // 返回最大价值和对应的选中物品。
    } // 结束 solve 方法。

    /** 封装动态规划的求解结果。 */
    public record Result(int maxValue, List<Integer> selectedItems) { // 定义不可变结果对象。
    } // 结束 Result 记录类。
} // 结束 Knapsack01DynamicProgramming 类。
