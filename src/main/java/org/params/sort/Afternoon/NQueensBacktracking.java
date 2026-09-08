package org.params.sort.Afternoon; // 声明当前类所在的包。

import java.util.ArrayList; // 导入动态数组实现类。
import java.util.List; // 导入 List 接口。

/** 回溯 + 剪枝
/** 软件设计师下午题风格的 N 皇后回溯法实现。 */
public class NQueensBacktracking { // 定义 N 皇后回溯求解器。

    private int n; // 保存棋盘大小，即皇后数量。
    private int[] x; // x[k] 表示第 k 行皇后所在的列号。
    private List<int[]> solutions; // 保存所有可行解。

    /** 求解 n 皇后问题，并返回全部可行解。 */
    public List<int[]> solve(int n) { // 定义求解问题的入口方法。
        if (n < 1) { // 若皇后数量小于 1，则不存在有意义的棋盘。
            return List.of(); // 返回空的解集合。
        } // 结束非法输入的判断。

        this.n = n; // 记录本次要求解的棋盘规模。
        this.x = new int[n]; // 创建状态数组，存放当前部分解。
        this.solutions = new ArrayList<>(); // 创建结果集合，存放所有完整解。

        backtrack(0); // 从解空间树的第 0 层开始搜索。
        return solutions; // 返回搜索到的全部可行解。
    } // 结束 solve 方法。

    /** Backtrack(k)：处理第 k 层，即为第 k 行皇后选择列号。 */
    private void backtrack(int k) { // 定义递归回溯函数。
        if (k == n) { // 若已处理完 n 行，说明到达一个叶结点。
            solutions.add(x.clone()); // 复制当前状态数组，并将该可行解保存。
            return; // 返回上一层，继续枚举其他分支。
        } // 结束叶结点判断。

        for (int column = 0; column < n; column++) { // 枚举第 k 行所有可能的列位置。
            x[k] = column; // 做选择：将第 k 行皇后暂时放在 column 列。

            if (place(k)) { // 调用约束函数，判断当前部分解是否可行。
                backtrack(k + 1); // 当前分支可行，递归处理下一行。
            } // 不可行时执行剪枝，循环自动尝试下一列。
        } // 当前行所有候选列均已搜索完毕。
    } // 结束 backtrack 方法。

    /** Place(k)：检查第 k 行皇后是否与前 k 行已放置的皇后冲突。 */
    private boolean place(int k) { // 定义约束函数。
        for (int j = 0; j < k; j++) { // 依次检查第 k 行之前的每一个皇后。
            if (x[j] == x[k] // 若两个皇后的列号相同，则它们处于同一列。
                    || Math.abs(j - k) == Math.abs(x[j] - x[k])) { // 若行差等于列差，则它们位于同一对角线。
                return false; // 发生冲突，当前结点不可行，通知调用者剪枝。
            } // 结束冲突条件判断。
        } // 所有已放置皇后均检查完毕。
        return true; // 未发现冲突，当前结点可继续向下搜索。
    } // 结束 place 方法。
} // 结束 NQueensBacktracking 类。
