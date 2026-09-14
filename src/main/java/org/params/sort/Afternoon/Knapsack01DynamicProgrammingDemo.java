package org.params.sort.Afternoon; // 声明当前示例类所在的包。

/** 演示如何调用 0/1 背包动态规划求解器。 */
public class Knapsack01DynamicProgrammingDemo { // 定义示例程序类。

    public static void main(String[] args) { // 定义 Java 程序入口方法。
        int[] weight = {2, 3, 4, 5}; // 定义四件物品的重量，数组下标即物品下标。
        int[] value = {3, 4, 5, 6}; // 定义四件物品与重量数组对应的价值。
        int capacity = 8; // 定义背包的最大承重容量。

        Knapsack01DynamicProgramming solver = new Knapsack01DynamicProgramming(); // 创建 0/1 背包求解器对象。
        Knapsack01DynamicProgramming.Result result = solver.solve(weight, value, capacity); // 调用 solve 方法求得最优结果。

        System.out.println("背包最大容量: " + capacity); // 输出背包容量。
        System.out.println("可获得的最大价值: " + result.maxValue()); // 输出动态规划得到的最大价值。
        System.out.println("选中的物品下标: " + result.selectedItems()); // 输出选中物品的数组下标。

        for (int index : result.selectedItems()) { // 逐个遍历被选中物品的下标。
            System.out.println("物品" + (index + 1) + "：重量=" + weight[index] + "，价值=" + value[index]); // 输出该物品的编号、重量和价值。
        } // 结束对选中物品的遍历。
    } // 结束 main 方法。



//    int Memoized_Knapsack(int v[N], int w[N], int T) {
//        int i;
//        int j;
//        for (i = 0; i < N; i ++ ) {
//            for (j = 0; j <= T; j ++ ) {
//                c[i][j] = -1;
//            }
//        }
//        return Calculate_Max_Value(v, w, N - 1, T);
//    }
//
//    int Calculate_Max_Value(int v[N], int w[N], int i, int j) {
//        int temp = 0;
//        if (c[i][j] != -1) {
//            return c[i][j];
//        }
//        if (i == 0 || j == 0) {
//            c[i][j] == 0;
//        } else {
//            c[i][j] = Calculate_Max_Value(v, w, i - 1, j);
//            // 当前的重量小于背包的重量，说明能装下
//            if (  w[i] < j  ) {
//                temp =   c[i-1][j - w[i]] + v[i]  ;
//                if (c[i][j] < temp) {
//                    c[i][j] =  temp;
//                }
//            }
//        }
//        return c[i][j];
//    }
} // 结束 Knapsack01DynamicProgrammingDemo 类。
