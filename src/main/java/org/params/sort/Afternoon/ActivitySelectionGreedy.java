package org.params.sort.Afternoon; // 声明当前类所在的包。

import java.util.Arrays; // 导入数组复制工具类。

/** 软件设计师下午题原题风格：活动选择问题的递归与迭代贪心算法。 */
public class ActivitySelectionGreedy { // 定义活动选择算法类。

    private final int[] optimalSubset = new int[100]; // OptimalSubset[] 存放被选中活动的编号。
    private int activityNumber; // activity_number 记录当前选中了多少个活动。

    /** 前提：s[1..n] 和 f[1..n] 已按结束时间 f 从小到大排列，s[0] 和 f[0] 为哨兵值 0。 */
    public int greedyActivitySelector(int[] s, int[] f, int n) { // 定义题目中的 GreedyActivitySelector 函数。
        checkInput(s, f, n); // 校验数组和活动数量。
        activityNumber = 0; // 每次求解前清空已选活动数量。

        if (n == 0) { // 若没有活动可选。
            return activityNumber; // 直接返回 0。
        } // 结束空活动集合判断。

        optimalSubset[activityNumber++] = 1; // 选择结束时间最早的第 1 个活动。
        int index = 1; // index 指向最近一次被选中活动的编号。

        for (int m = 2; m <= n; m++) { // 从第 2 个活动开始依次考察所有活动。
            if (s[m] >= f[index]) { // 若活动 m 的开始时间不早于活动 index 的结束时间，则二者兼容。
                optimalSubset[activityNumber++] = m; // 选择活动 m，并保存其编号。
                index = m; // 将最近选中活动更新为活动 m。
            } // 若不兼容，则跳过活动 m。
        } // 所有活动均已考察完毕。

        return activityNumber; // 返回最多可安排的活动数量。
    } // 结束 GreedyActivitySelector 方法。

    /** 使用递归形式求解同一个活动选择问题。 */
    public int recursiveActivitySelector(int[] s, int[] f, int n) { // 定义递归算法入口方法。
        checkInput(s, f, n); // 校验数组和活动数量。
        activityNumber = 0; // 每次求解前清空已选活动数量。
        selectRecursively(s, f, 0, n); // 从虚拟活动 0 开始递归选择。
        return activityNumber; // 返回最多可安排的活动数量。
    } // 结束递归算法入口方法。

    /** 对应原题中的 RecursiveActivitySelector(s, f, index, n)。 */
    private void selectRecursively(int[] s, int[] f, int index, int n) { // 定义递归贪心函数。
        int m = index + 1; // m 从 index 后面的第一个活动开始查找。

        while (m <= n && s[m] < f[index]) { // 跳过所有开始时间早于活动 index 结束时间的冲突活动。
            m++; // 继续检查下一个活动。
        } // 循环结束时，m 指向最早结束且与 index 兼容的活动。

        if (m <= n) { // 若仍然找到兼容活动。
            optimalSubset[activityNumber++] = m; // 选择活动 m，并保存其编号。
            selectRecursively(s, f, m, n); // 以活动 m 为新的基准继续递归选择。
        } // 若 m 大于 n，说明没有剩余的兼容活动，递归自然结束。
    } // 结束递归贪心函数。

    /** 返回本次求解选中的活动编号。 */
    public int[] getOptimalSubset() { // 定义结果读取方法。
        return Arrays.copyOf(optimalSubset, activityNumber); // 只复制有效的活动编号部分。
    } // 结束结果读取方法。

    /** 检查题目输入是否满足下标从 1 开始的数组要求。 */
    private void checkInput(int[] s, int[] f, int n) { // 定义输入校验方法。
        if (s == null || f == null || n < 0 || s.length <= n || f.length <= n) { // 判断数组是否能访问到下标 1..n。
            throw new IllegalArgumentException("s 和 f 必须包含下标 0 至 n 的元素"); // 输入不符合题目约定时抛出异常。
        } // 结束输入合法性判断。
    } // 结束输入校验方法。

    public static void main(String[] args) { // 定义示例程序入口。
        int[] s = {0, 1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12}; // 定义活动 0 至活动 11 的开始时间，0 号活动为虚拟活动。
        int[] f = {0, 4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16}; // 定义活动 0 至活动 11 的结束时间，且已按结束时间升序排列。
        int n = 11; // 定义实际活动数量。

        ActivitySelectionGreedy solver = new ActivitySelectionGreedy(); // 创建活动选择算法对象。
        int count = solver.greedyActivitySelector(s, f, n); // 调用迭代贪心算法。

        System.out.println("选中的活动编号: " + Arrays.toString(solver.getOptimalSubset())); // 输出最优兼容活动集合。
        System.out.println("最多可安排活动数: " + count); // 输出最多可安排的活动数量。
    } // 结束 main 方法。
} // 结束 ActivitySelectionGreedy 类。
