package org.params.sort.Afternoon.贪心; // 声明当前类所在的包。

/**
 * 【说明】
 * Kruskal 算法（贪心）：求带权无向连通图的最小生成树。选出 n-1 条边连通全部顶点，边权之和最小。
 * 边按权值升序排序，依次考察：两端尚不在同一连通分量（不成环）则选中并合并。
 *
 * 名词：
 *   n, e         顶点数、边数
 *   u[i], v[i], w[i]  第 i 条无向边的两端和权（边下标从 1 开始）
 *   parent[x]    并查集中 x 的父亲；parent[x]==x 时 x 为所在连通分量的根
 *   Find(x)      找 x 所在连通分量的根；根相同则两点已连通，再连会成环
 *   ru, rv       当前边两端的根
 *   count        已选中的边数，树需要 n-1 条
 *   sum          已选边权之和
 *
 * 【算法】先按 w 冒泡排序；Find(u)!=Find(v) 则 parent[ru]=rv 且 sum+=w。
 * 排序为主，时间复杂度常见 O(e^2)（冒泡）或 O(e log e)（若试卷已假定排序）。
 */
public class Kruskal { // 对应下午题中的一组 C 函数。

    static final int N = 20; // 顶点个数上限。
    static final int M = 50; // 边数上限。
    static int[] u = new int[M + 1]; // u[i]：第 i 条边的一个端点。
    static int[] v = new int[M + 1]; // v[i]：第 i 条边的另一个端点。
    static int[] w = new int[M + 1]; // w[i]：第 i 条边的权。
    static int[] parent = new int[N + 1]; // 并查集。

    /** Find(x)：用循环找到 x 所在连通分量的根。 */
    static int Find(int x) { // 对应原题中的 Find 函数。
        while (parent[x] != x) { // 还没有走到根。
            x = parent[x]; // 沿父亲向上走。
        }
        return x; // x 即为根。
    } // 结束 Find。

    /** 按权值 w 对边表做冒泡排序（软考常用写法）。 */
    static void SortEdges(int e) { // 对应试卷中「边已按权值排序」或显式排序函数。
        int i, j, tu, tv, tw; // 交换边时的暂存。
        for (i = 1; i <= e - 1; i++) { // 冒泡：共 e-1 轮。
            for (j = 1; j <= e - i; j++) { // 相邻比较。
                if (w[j] > w[j + 1]) { // 前一条边更重，交换，使短边靠前。
                    tu = u[j];
                    u[j] = u[j + 1];
                    u[j + 1] = tu;
                    tv = v[j];
                    v[j] = v[j + 1];
                    v[j + 1] = tv;
                    tw = w[j];
                    w[j] = w[j + 1];
                    w[j + 1] = tw;
                }
            }
        }
    } // 结束 SortEdges。

    /** Kruskal：返回最小生成树的边权之和。 */
    static int Kruskal(int n, int e) { // 对应原题中的 Kruskal 函数。
        int i, ru, rv, count, sum; // count 为已选边数。

        SortEdges(e); // 边按权值从小到大排序。

        for (i = 1; i <= n; i++) { // 每个顶点自成一个连通分量。
            parent[i] = i;
        }
        sum = 0;
        count = 0;

        for (i = 1; i <= e; i++) { // 从最短边开始依次考察。
            ru = Find(u[i]); // 端点 u 所在连通分量的根。
            rv = Find(v[i]); // 端点 v 所在连通分量的根。
            if (ru != rv) { // 两端尚未连通，加上本边不会成环。
                parent[ru] = rv; // 合并两个连通分量。
                sum = sum + w[i]; // 累加边权。
                count = count + 1; // 已选边数加 1。
                if (count == n - 1) { // 已有 n-1 条边，生成树完成。
                    break;
                }
            } // 根相同则成环，跳过。
        }
        return sum; // MST 总权。
    } // 结束 Kruskal。

    public static void main(String[] args) { // 测试入口，对应试卷调用示例。
        int n = 4; // 顶点数。
        int e = 6; // 边数。
        /* 与 Prim 教学图相同。下标从 1 开始存放边。 */
        u[1] = 1; v[1] = 2; w[1] = 6;
        u[2] = 1; v[2] = 3; w[2] = 1;
        u[3] = 1; v[3] = 4; w[3] = 5;
        u[4] = 2; v[4] = 3; w[4] = 5;
        u[5] = 2; v[5] = 4; w[5] = 3;
        u[6] = 3; v[6] = 4; w[6] = 5;

        int sum = Kruskal(n, e);
        System.out.println("MST 总权: " + sum); // 应为 9。
    }
} // 结束 Kruskal 类。
