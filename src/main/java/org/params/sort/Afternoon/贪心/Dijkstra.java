package org.params.sort.Afternoon.贪心; // 声明当前类所在的包。

/**
 * 【说明】
 * Dijkstra 算法（贪心）：带权有向图 G 有 n 个顶点，编号 1..n。边权均为非负。
 *
 * 名词：
 *   n            顶点个数
 *   v0           起点（源点）编号
 *   edge[i][j]   有向边 i→j 的长度；无边为 INF；不是全程
 *   dist[j]      目前认为「起点 v0 走到点 j」有多远
 *   visited[j]   j 是否已锁定（1=最短路钉死，不再改）
 *   pre[j]       最短路上走到 j 之前是哪个点
 *   u            本轮选出、准备锁定的点的编号
 *
 * 初值抄起点那一行：dist[i] = edge[v0][i]，不是先全部写成 INF。
 * 每轮锁定未访问点中 dist 最小的 u，再松弛：
 *   dist[j] = min(dist[j], dist[u] + edge[u][j])
 *   其中 dist[u] 是起点到 u 的全程，edge[u][j] 只是边 u→j 这一跳。
 *
 * 【算法】Dijkstra 求单源最短路径；PrintPath 按 pre 回溯输出路径。
 * 下标从 1 开始。两个内层 for 并列，邻接矩阵时间复杂度 O(n^2)，不是 O(n^3)。
 * main 用的是教学图（dist[4]=3，路径 1-2-4）。自测第二题见学习计划笔记。
 */
public class Dijkstra { // 定义 Dijkstra 算法类（对应下午题中的一组 C 函数）。

    static final int N = 20; // 顶点个数上限。
    static final int INF = 99; // 表示无边，对应试卷中的无穷大。
    static int[] dist = new int[N + 1]; // dist[j] 保存 v0 到 j 的当前最短路径长度。
    static int[] visited = new int[N + 1]; // visited[j]=1 表示 j 的最短路径已确定。
    static int[] pre = new int[N + 1]; // pre[j] 保存最短路径上 j 的前驱顶点，便于回溯。

    /** 求 v0 到其余各顶点的最短路径长度，并记录前驱。 */
    /**
     *
     * @param n  节点数
     * @param v0 从哪个点出发
     * @param edge  邻接矩阵，记录边的权，比如 edge[i][j] 表示 i 到 j 的边权
     */
    static void Dijkstra(int n, int v0, int[][] edge) { // 对应原题中的 Dijkstra 函数。
        int i, j, u, minw; // i 为轮次，j 扫描顶点，u 为本轮锁定的顶点，minw 为当前最小 dist。

        for (i = 1; i <= n; i++) { // 初始化：先按起点的直达边填写 dist。
            dist[i] = edge[v0][i]; // 尚不知道绕路，暂记 v0 直接到 i 的边权。
            visited[i] = 0; // 除起点外均未锁定。
            pre[i] = v0; // 暂记前驱为起点，若存在更短路再改。
        }
        dist[v0] = 0; // 起点到自身的距离为 0。
        visited[v0] = 1; // 贪心：立刻锁定起点。

        for (i = 1; i < n; i++) { // 再确定其余 n-1 个顶点。
            minw = INF; // 本轮最小 dist 初值为无穷大。
            u = -1; // 本轮选出的顶点编号。
            for (j = 1; j <= n; j++) { // 在未锁定点中找 dist 最小者。
                if (visited[j] == 0 && dist[j] < minw) { // 尚未锁定且更近。
                    minw = dist[j]; // 更新本轮最小距离。
                    u = j; // 记下该顶点编号。
                }
            }
            if (u == -1) { // 剩余点均不可达时结束。
                break;
            }
            visited[u] = 1; // 贪心选择：锁定 u，其最短路径不再改变。

            for (j = 1; j <= n; j++) { // 用刚锁定的 u 去松弛邻接顶点。
                if (visited[j] == 0 && dist[u] + edge[u][j] < dist[j]) { // 经 u 到达 j 更短。
                    dist[j] = dist[u] + edge[u][j]; // 用新路径长度覆盖 dist[j]。
                    pre[j] = u; // 记录最短路径上 j 的前驱为 u。
                }
            }
        }
    } // 结束 Dijkstra。dist[j] 即为 v0 到 j 的最短路径长度。

    /** 根据前驱数组回溯并输出 v0 到 v 的一条最短路径。 */
    static void PrintPath(int v0, int v) { // 对应试卷中还原路径的递归输出。
        if (v == v0) { // 已回溯到起点。
            System.out.print(v0);
            return;
        }
        PrintPath(v0, pre[v]); // 先输出前驱路径，保证从起点到终点的顺序。
        System.out.print("-" + v); // 再输出当前顶点。
    } // 结束 PrintPath。

    public static void main(String[] args) { // 测试入口，对应下午题中的调用示例。
        int n = 4; // 顶点个数。
        int v0 = 1; // 源点。
        /* 与填空题同一张邻接矩阵：1→2 权 2，1→3 权 5，1→4 权 9，2→4 权 1，3→4 权 3。 */
        int[][] edge = {
                {0, 0, 0, 0, 0},
                {0, 0, 2, 5, 9},
                {0, INF, 0, INF, 1},
                {0, INF, INF, 0, 3},
                {0, INF, INF, INF, 0}
        };

        Dijkstra(n, v0, edge); // 求单源最短路径。

        System.out.print("dist[] ="); // 输出各点最短路径长度。
        for (int j = 1; j <= n; j++) {
            System.out.print(" " + dist[j]);
        }
        System.out.println();
        System.out.println("dist[4] = " + dist[4]); // 本题应为 3。
        System.out.print("1 到 4 的最短路径: ");
        PrintPath(v0, 4); // 本题应为 1-2-4。
        System.out.println();
    }
} // 结束 Dijkstra 类。
