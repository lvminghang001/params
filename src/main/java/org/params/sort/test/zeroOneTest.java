package org.params.sort.test;

import java.util.Arrays;

/**
 * 0/1 背包动态规划测试类。
 */
public class zeroOneTest {

    static int[][] aa(int [] weight, int [] value, int capacity){
        // 选择前i个物品 ，背包容量为 capacity 的最大价值
        int[][] dp = new int[weight.length + 1][capacity + 1];
        int n = weight.length;
        for(int i = 1;i<=n;i++){
            for(int j = 0;j<=capacity;j++){
                dp[i][j] = dp[i-1][capacity];   //不选择第i个物品的最大价值
                if(weight[j] <= capacity){
                    int selectValue = dp[i-1][capacity - weight[j]] + value[j];
                    dp[i][j] = Math.max(dp[i][j],selectValue);
                    System.out.println("第"+i+"个物品被选中");
                }
            }
        }
        return dp;
    }




}
