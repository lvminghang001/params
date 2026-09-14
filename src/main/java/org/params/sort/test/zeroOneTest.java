package org.params.sort.test;

import java.util.Arrays;

public class zeroOneTest {



    //背包最大承重
    static int n = 11;

    // 重量数组
    static int [] weight = {1,2,3,4,5,6};

    // 价值数组
    static int [] value =  {1,2,3,4,5,6};

    static int[][] aa(int[] weight,int[] value,int cap){

        // 1.先确认物品的数量
        int num = weight.length;

        // 在 num 中 选择
        int [][] dp = new int[num+1][cap+1];

        // 2. 循环每一个物品
        for(int i = 1 ;i<= num ;i++){
            // 3.每一种重量都考虑
            for(int c = 0; c<=cap;c++){
                dp[i-1][c] = value[i-1];
                // 可以放的下的话
                if(weight[i-1] <= c){
                    // 当前物品的价值
                    int currentV = dp[i-1][c - weight[i-1]] + value[i-1];
                    // 比较大小
                    dp[i-1][c] = Math.max( dp[i-1][c],currentV);
                }
            }
        }
        return dp;

    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(aa(weight, value, n)));
    }

}
