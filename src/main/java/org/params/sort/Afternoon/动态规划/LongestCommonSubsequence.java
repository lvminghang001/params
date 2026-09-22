package org.params.sort.Afternoon.动态规划; // 声明当前类所在的包。

/**
 * 【说明】
 * 最长公共子序列（LCS）问题：给定两个序列 X = <x1, x2, ..., xm> 和 Y = <y1, y2, ..., yn>，
 * 求 X 与 Y 的一个最长公共子序列。子序列不必连续，但须保持元素在原序列中的相对次序。
 *
 * 设 c[i][j] 表示 Xi 与 Yj 的最长公共子序列长度，则：
 *   c[i][j] = 0                              当 i = 0 或 j = 0
 *   c[i][j] = c[i-1][j-1] + 1                当 i, j > 0 且 xi = yj
 *   c[i][j] = max(c[i-1][j], c[i][j-1])      当 i, j > 0 且 xi != yj
 *
 * 为便于还原序列，用 b[i][j] 记录构造 c[i][j] 时的选择：
 *   1 表示取自左上方（当前字符进入 LCS）；
 *   2 表示取自上方（丢弃 xi）；
 *   3 表示取自左方（丢弃 yj）。
 *
 * 【算法】函数 LCSLength 按上述递推填表；函数 PrintLCS 根据 b 数组回溯输出一条 LCS。
 * 字符数组下标从 1 开始存放有效字符，X[0]、Y[0] 闲置不用。
 */
public class LongestCommonSubsequence { // 定义 LCS 算法类（对应下午题中的一组 C 函数）。

    static final int M = 20; // 序列 X 的最大长度。
    static final int N = 20; // 序列 Y 的最大长度。
    static int[][] c = new int[M + 1][N + 1]; // c[i][j] 保存 Xi 与 Yj 的 LCS 长度。
    static int[][] b = new int[M + 1][N + 1]; // b[i][j] 保存构造 c[i][j] 时的方向。

    /** 计算两个序列的 LCS 长度，并填写方向数组 b。 */
    static void LCSLength(char[] X, char[] Y, int m, int n) { // 对应原题中的 LCSLength 函数。
        int i, j; // i、j 分别为 X、Y 的下标。
        for (i = 0; i <= m; i++) { // 初始化第 0 列：Y 为空时 LCS 长度为 0。
            c[i][0] = 0;
        }
        for (j = 0; j <= n; j++) { // 初始化第 0 行：X 为空时 LCS 长度为 0。
            c[0][j] = 0;
        }
        for (i = 1; i <= m; i++) { // 自底向上依次考虑 X 的第 1 至第 m 个字符。
            for (j = 1; j <= n; j++) { // 依次考虑 Y 的第 1 至第 n 个字符。
                if (X[i] == Y[j]) { // 当前两字符相等，该字符可进入 LCS。
                    c[i][j] = c[i - 1][j - 1] + 1; // 长度等于左上角子问题最优值加 1。
                    b[i][j] = 1; // 标记：来自左上方。
                } else if (c[i - 1][j] >= c[i][j - 1]) { // 上方不小于左方，丢弃 xi。
                    c[i][j] = c[i - 1][j]; // 长度取自上方子问题。
                    b[i][j] = 2; // 标记：来自上方。
                } else { // 左方更大，丢弃 yj。
                    c[i][j] = c[i][j - 1]; // 长度取自左方子问题。
                    b[i][j] = 3; // 标记：来自左方。
                }
            }
        }
    } // 结束 LCSLength。c[m][n] 即为 LCS 长度。

    /** 根据方向数组 b 回溯并输出一条 LCS。 */
    static void PrintLCS(char[] X, int i, int j) { // 对应原题中的 PrintLCS / LCS 函数。
        if (i == 0 || j == 0) { // 某一序列已取空，回溯结束。
            return;
        }
        if (b[i][j] == 1) { // 当前字符被选入 LCS。
            PrintLCS(X, i - 1, j - 1); // 先递归处理左上角子问题，保证按从前到后输出。
            System.out.print(X[i]); // 再输出当前字符 xi。
        } else if (b[i][j] == 2) { // 方向向上，未选取 xi。
            PrintLCS(X, i - 1, j);
        } else { // 方向向左，未选取 yj。
            PrintLCS(X, i, j - 1);
        }
    } // 结束 PrintLCS。

    public static void main(String[] args) { // 测试入口，对应下午题中的调用示例。
        char[] X = {' ', 'A', 'B', 'C', 'B', 'D', 'A', 'B'}; // 序列 X，下标 1..7 有效。
        char[] Y = {' ', 'B', 'D', 'C', 'A', 'B', 'A'}; // 序列 Y，下标 1..6 有效。
        int m = 7; // 序列 X 的长度。
        int n = 6; // 序列 Y 的长度。

        LCSLength(X, Y, m, n); // 填表，求 LCS 长度。
        System.out.println("c[m][n] = " + c[m][n]); // 输出 LCS 长度，本题应为 4。
        System.out.print("一条 LCS: "); // 提示即将输出回溯得到的子序列。
        PrintLCS(X, m, n); // 从 c 表右下角开始回溯并打印。
        System.out.println();
    }
} // 结束 LongestCommonSubsequence 类。
