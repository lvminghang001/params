package org.params.sort.Afternoon.贪心; // 声明当前类所在的包。

/**
 * 【说明】
 * Prim 算法（贪心）：求带权无向连通图的最小生成树（MST）。
 * 选出若干边，把全部顶点连成一棵树，且边权之和最小。不能有环。
 *
 * 与 Dijkstra 很像，但问的不是「起点到各点有多远」，而是「把所有点连起来总权最小」。
 * 名词：
 *   n            顶点个数
 *   v0           生成树的起始顶点（从哪一点开始长树）
 *   edge[i][j]   无向边 i-j 的权；无边为 INF；对称，edge[i][j]=edge[j][i]
 *   dist[j]      点 j 到 **当前已经生成的那棵树** 有多近（不是到 v0 的路程）
 *   visited[j]   j 是否已经进树（1=已进树）
 *   pre[j]       把 j 拉进树的那条边的另一端（树上的父亲）
 *   u            本轮选中、即将进树的点
 *
 * 初值抄起点那一行：dist[i] = edge[v0][i]。
 * 每轮锁定未进树中 dist 最小的 u（贪心：当前离树最近的点），把 dist[u] 累加进总权。
 * 再用 u 更新别人，核心与 Dijkstra 不同：
 *   dist[j] = min(dist[j], edge[u][j])     // 只看边 u-j，不要写成 dist[u]+edge[u][j]
 *
 * 【算法】函数 Prim 求 MST 总权并记录 pre；邻接矩阵时间复杂度 O(n^2)。
 * 顶点下标从 1 开始。main：从 1 号点长树，总权应为 9。
 */
public class Prim { // 定义 Prim 算法类（对应下午题中的一组 C 函数）。

    static final int N = 20; // 顶点个数上限。
    static final int INF = 99; // 表示无边。
    static int[] dist = new int[N + 1]; // dist[j]：j 到当前生成树有多近。
    static int[] visited = new int[N + 1]; // visited[j]=1 表示 j 已进树。
    static int[] pre = new int[N + 1]; // pre[j]：树上连接 j 的父亲。

    /**
     * 从 v0 开始构造最小生成树，返回树的边权之和。
     *
     * @param n    顶点个数
     * @param v0   从哪个点开始长树
     * @param edge 无向图邻接矩阵，edge[i][j] 为边 i-j 的权
     */
    static int Prim(int n, int v0, int[][] edge) { // 对应原题中的 Prim 函数。
        int i, j, u, minw, sum; // sum 为最小生成树的总权值。

        for (i = 1; i <= n; i++) { // 初始化：树里只有 v0，其它点到树的距离就是连 v0 的边。
            dist[i] = edge[v0][i]; // j 到树有多近，先看成到 v0 的边权。
            visited[i] = 0; // 尚未进树。
            pre[i] = v0; // 暂记连到起点，若有更短的连树边再改。
        }
        dist[v0] = 0; // 起点已在树上，到树的距离为 0。
        visited[v0] = 1; // 贪心：先把起点放进树。
        sum = 0; // 还没有选边。

        for (i = 1; i < n; i++) { // 还要拉进其余 n-1 个点（也就选出 n-1 条边）。
            minw = INF;
            u = -1;
            for (j = 1; j <= n; j++) { // 在未进树的点里，找 dist 最小者，即离树最近的点。
                if (visited[j] == 0 && dist[j] < minw) {
                    minw = dist[j];
                    u = j;
                }
            }
            if (u == -1) { // 图不连通则无法生成树。
                break;
            }
            visited[u] = 1; // 把 u 拉进树。这条边权就是 dist[u]。
            sum = sum + dist[u]; // 累加进 MST 总权。

            for (j = 1; j <= n; j++) { // 树变大了，更新其它点「到树」有多近。
                if (visited[j] == 0 && edge[u][j] < dist[j]) { // 连到刚进树的 u 比原来连树更短。
                    dist[j] = edge[u][j]; // 只比较边 u-j，不要加上 dist[u]。
                    pre[j] = u; // 记下：靠边 u-j 把 j 连到树上。
                }
            }
        }
        return sum; // 最小生成树的边权之和。
    } // 结束 Prim。

    public static void main(String[] args) { // 测试入口。
        int n = 4;
        int v0 = 1;
        /* 无向图：1-2=6，1-3=1，1-4=5，2-3=5，2-4=3，3-4=5。 */
        int[][] edge = {
                {0, 0, 0, 0, 0},
                {0, 0, 6, 1, 5},
                {0, 6, 0, 5, 3},
                {0, 1, 5, 0, 5},
                {0, 5, 3, 5, 0}
        };

        int sum = Prim(n, v0, edge); // 求 MST 总权。

        System.out.println("MST 总权: " + sum); // 应为 9。
        System.out.println("树上的边（父亲-点 权）:");
        for (int j = 1; j <= n; j++) {
            if (j != v0) {
                System.out.println(pre[j] + "-" + j + "  权=" + edge[pre[j]][j]);
            }
        }
    }
} // 结束 Prim 类。
