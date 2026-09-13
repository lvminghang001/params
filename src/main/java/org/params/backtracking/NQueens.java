package org.params.backtracking;

/**
 * N 皇后回溯算法。
 *
 * <p>软考下午题常用表示：x[k] 表示第 k 行皇后所在的列号（行、列均从 1 开始）。
 * 按行尝试放置皇后；若当前位置与已放置的皇后冲突，则换列；否则递归处理下一行。</p>
 */
public class NQueens {
    private final int n;
    private final int[] x;
    private int solutionCount;

    public NQueens(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n 必须大于 0");
        }
        this.n = n;
        this.x = new int[n + 1]; // 下标 0 不使用，便于与题目中的 x[1..n] 对应
    }

    /** 回溯函数：为第 k 行选择皇后所在的列。 */
    public void backtrack(int k) {
        if (k > n) {             // n 行均已成功放置皇后，得到一个可行解
            solutionCount++;
            printSolution();
            return;
        }

        for (int column = 1; column <= n; column++) {
            x[k] = column;       // 尝试将第 k 行皇后放在第 column 列
            if (place(k)) {      // 若与前 k - 1 行皇后不冲突
                backtrack(k + 1);
            }
        }
    }

    /**
     * 判断第 k 行皇后是否能放在 x[k] 列。
     * 同列：x[j] == x[k]；同一对角线：|j-k| == |x[j]-x[k]|。
     */
    private boolean place(int k) {
        for (int j = 1; j < k; j++) {
            if (x[j] == x[k] || Math.abs(j - k) == Math.abs(x[j] - x[k])) {
                return false;
            }
        }
        return true;
    }

    private void printSolution() {
        System.out.println("第 " + solutionCount + " 种解法：");
        for (int row = 1; row <= n; row++) {
            for (int column = 1; column <= n; column++) {
                System.out.print(x[row] == column ? "Q " : ". ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int n = args.length == 0 ? 4 : Integer.parseInt(args[0]);
        NQueens queens = new NQueens(n);
        queens.backtrack(1);     // 从第 1 行开始搜索
        System.out.println(n + " 皇后共有 " + queens.solutionCount + " 种解法。");
    }
}
