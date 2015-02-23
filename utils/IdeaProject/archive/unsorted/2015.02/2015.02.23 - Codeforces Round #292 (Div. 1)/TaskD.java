package coding;

import ru.ifmo.niyaz.DataStructures.DisjointSetUnion;
import ru.ifmo.niyaz.arrayutils.ArrayUtils;
import ru.ifmo.niyaz.io.FastScanner;
import ru.ifmo.niyaz.io.FastPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TaskD {
    static class Edge {
        int from;
        int to;
        int w;

        Edge(int from, int to, int w) {
            this.from = from;
            this.to = to;
            this.w = w;
        }
    }

    static List<Edge>[] edges;
    static long[] down;
    static long[] down2;
    static long[] val;

    static void dfs(int v, int pv) {
        down[v] = 0;
        down2[v] = 0;
        for (int i = 0; i < edges[v].size(); i++) {
            Edge e = edges[v].get(i);
            if (e.to == pv) continue;
            dfs(e.to, v);
            long c = down[e.to] + e.w;
            if (c > down[v]) {
                down2[v] = down[v];
                down[v] = c;
            } else if (c > down2[v]) {
                down2[v] = c;
            }
        }
    }

    static void dfs2(int v, int pv, long up) {
        val[v] = Math.max(down[v], up);
        for (int i = 0; i < edges[v].size(); i++) {
            Edge e = edges[v].get(i);
            if (e.to == pv) continue;
            dfs2(e.to, v, Math.max(down[e.to] + e.w == down[v] ? down2[v] : down[v], up) + e.w);
        }
    }

    static int[] pr;

    static int get(int x) {
        return x == pr[x] ? x : (pr[x] = get(pr[x]));
    }

    public void solve(int testNumber, FastScanner in, FastPrinter out) {
        int n = in.nextInt();
        edges = new List[n];
        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i + 1 < n; i++) {
            int from = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            int w = in.nextInt();
            edges[from].add(new Edge(from, to, w));
            edges[to].add(new Edge(to, from, w));
        }
        down = new long[n];
        down2 = new long[n];
        val = new long[n];
        dfs(0, -1);
        dfs2(0, -1, 0);
        Integer[] id = new Integer[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
        Arrays.sort(id, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Long.compare(val[o1], val[o2]);
            }
        });
        int q = in.nextInt();
        pr = new int[n];
        boolean[] has = new boolean[n];
        int[] sz = new int[n];
        for (int i = 0; i < q; i++) {
            long len = in.nextLong();
            int ans = 1;
            for (int j = 0; j < n; j++) pr[j] = j;
            Arrays.fill(has, false);
            Arrays.fill(sz, 1);
            for (int right = n - 1, left = n - 1; right >= 0; right--) {
                while (left >= 0 && val[id[right]] - val[id[left]] <= len) {
                    int add = id[left];
                    for (int z = 0; z < edges[add].size(); z++) {
                        Edge e = edges[add].get(z);
                        if (has[e.to]) {
                            int v = get(e.to);
                            int u = get(add);
                            if (v != u) {
                                sz[v] += sz[u];
                                ans = Math.max(ans, sz[v]);
                                pr[u] = v;
                            }
                        }
                    }
                    has[add] = true;
                    --left;
                }
                int rem = id[right];
                --sz[get(rem)];
                has[rem] = false;
            }
            out.println(ans);
        }
    }
}
