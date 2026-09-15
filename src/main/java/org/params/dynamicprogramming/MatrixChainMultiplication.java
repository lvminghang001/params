package org.params.dynamicprogramming;

/**
 * 矩阵连乘最优计算次序（软考下午题风格）。
 *
 * <p>矩阵 A_i 的维度为 p[i - 1] × p[i]。
 * m[i][j] 表示计算 A_i...A_j 所需的最少标量乘法次数；
 * s[i][j] 表示达到最小次数时的断开位置 k。</p>
 *
 * m[i][i] = 0   //初始值
 * p[i - 1] * p[k] * p[j]   // 最终的乘的值
 * m[i][k] + m[k + 1][j]   // 左右两部分已经算出来所花的最少乘法次数
 * O(n³)  时间复杂度
 *
 */
public class MatrixChainMultiplication {

    private static final int INF = Integer.MAX_VALUE;

    /**
     * 动态规划求解矩阵连乘。
     *
     * @param p 维度数组。例如 {30, 35, 15, 5, 10} 对应 A1(30×35)、A2(35×15)...
     * @param n 矩阵个数
     * @param m 最少乘法次数表
     * @param s 最优断开位置表
     */
    public static void matrixChain(int[] p, int n, int[][] m, int[][] s) {
        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;                 // 单个矩阵不需要相乘
        }

        // r 是矩阵链长度，先算短链，再利用短链结果计算长链
        for (int r = 2; r <= n; r++) {
            for (int i = 1; i <= n - r + 1; i++) {
                int j = i + r - 1;
                m[i][j] = INF;

                // 将 A_i...A_j 在 A_k 和 A_(k+1) 之间断开
                for (int k = i; k < j; k++) {
                    int cost = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (cost < m[i][j]) {
                        m[i][j] = cost;
                        s[i][j] = k;
                    }
                }
            }
        }
    }

    /** 根据 s 表递归输出最优加括号方式。 */
    public static void printOptimalParens(int[][] s, int i, int j) {
        if (i == j) {
            System.out.print("A" + i);
            return;
        }

        System.out.print("(");
        printOptimalParens(s, i, s[i][j]);
        printOptimalParens(s, s[i][j] + 1, j);
        System.out.print(")");
    }

    public static void main(String[] args) {
        // A1: 30×35, A2: 35×15, A3: 15×5, A4: 5×10
        int[] p = {30, 35, 15, 5, 10};
        int n = p.length - 1;
        int[][] m = new int[n + 1][n + 1];
        int[][] s = new int[n + 1][n + 1];

        matrixChain(p, n, m, s);
        System.out.println("最少标量乘法次数：" + m[1][n]);
        System.out.print("最优计算次序：");
        printOptimalParens(s, 1, n);
    }
}
