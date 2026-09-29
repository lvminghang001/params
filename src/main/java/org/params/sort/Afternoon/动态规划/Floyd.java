package org.params.sort.Afternoon.动态规划; // 声明当前类所在的包。

/**
 * 【说明】
 * Floyd 算法（动态规划）：求带权图中任意两点之间的最短路径长度。
 * 与 Dijkstra 不同：Dijkstra 只算一个起点 v0 到其余点；Floyd 一次算出所有 dist[i][j]。
 *
 * 名词：
 *   n            顶点个数
 *   edge[i][j]   有向边 i→j 的长度；无边为 INF；不是全程
 *   dist[i][j]   目前认为「点 i 走到点 j」有多远（注意比 Dijkstra 的 dist[j] 多一个起点下标 i）
 *   k            允许当作中间点的那个顶点编号（DP 阶段）
 *   i            路径起点编号
 *   j            路径终点编号
 *
 * 状态转移（核心就这一句）：
 *   dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])
 *   dist[i][k] 是 i 走到中间点 k 的全程；dist[k][j] 是 k 走到 j 的全程；相加再与直达/旧路比较。
 *
 * 【算法】函数 Floyd 按 k、i、j 三重循环填表。k 必须在最外层。
 * 顶点下标从 1 开始。三重嵌套，时间复杂度 O(n^3)。
 * main 与 Dijkstra 教学图相同，算完后第 1 行应为 0 2 5 3，其中 dist[1][4]=3。
 */
public class Floyd { // 定义 Floyd 算法类（对应下午题中的一组 C 函数）。

    static final int N = 20; // 顶点个数上限。
    static final int INF = 99; // 表示无边，对应试卷中的无穷大。
    static int[][] dist = new int[N + 1][N + 1]; // dist[i][j]：i 走到 j 目前有多远。

    /** 求任意两点之间的最短路径长度。 */
    static void Floyd(int n, int[][] edge) { // 对应原题中的 Floyd 函数。
        int i, j, k; // i 起点，j 终点，k 中间点。

        for (i = 1; i <= n; i++) { // 初值：先抄邻接矩阵，只认直达边。
            for (j = 1; j <= n; j++) {
                dist[i][j] = edge[i][j]; // 尚不允许绕路时，i 到 j 就是边权。
            }
        }

        for (k = 1; k <= n; k++) { // DP 阶段：允许把 1..k 当中间点；k 必须在最外层。
            for (i = 1; i <= n; i++) { // 枚举路径起点 i。
                for (j = 1; j <= n; j++) { // 枚举路径终点 j。
                    if (dist[i][k] == INF || dist[k][j] == INF) { // 到不了 k 或从 k 走不出去，不能加。
                        continue;
                    }
                    if (dist[i][k] + dist[k][j] < dist[i][j]) { // 经 k 绕路比原来更短。
                        dist[i][j] = dist[i][k] + dist[k][j]; // 用「i 到 k」+「k 到 j」覆盖旧路程。
                    }
                }
            }
        }
    } // 结束 Floyd。dist[i][j] 即为 i 到 j 的最短路径长度。

    public static void main(String[] args) { // 测试入口，对应下午题中的调用示例。
        int n = 4; // 顶点个数。
        /* 与 Dijkstra 教学图同一张邻接矩阵。 */
        int[][] edge = {
                {0, 0, 0, 0, 0},
                {0, 0, 2, 5, 9},
                {0, INF, 0, INF, 1},
                {0, INF, INF, 0, 3},
                {0, INF, INF, INF, 0}
        };

        Floyd(n, edge); // 求所有点对最短路径。

        System.out.println("dist[i][j]（行 i 走到列 j 有多远）:");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.print(dist[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("dist[1][4] = " + dist[1][4]); // 与 Dijkstra 从 1 出发到 4 相同，应为 3。
    }
} // 结束 Floyd 类。
