package mypackage;

import niyazio.FastScanner;
import niyazio.FastPrinter;

import java.util.*;

public class TaskG {
    public void solve(int testNumber, FastScanner in, FastPrinter out) {
        int n = 500;
        int[] x = new int[n];
        int[] p = new int[n];
        x[0] = 0;
        x[n - 1] = 1000000;
        p[0] = 1000000;
        p[n - 1] = 1000000;
        int curX = 500;
        int curR = 500;
        for (int i = 1; i < n - 1; i++) {
            x[i] = curX;
            p[i] = curR;
            curX += curR;
            curR--;
        }
        Arrays.sort(x);
        double error = incorrect(x.clone(), p.clone()) - correct(x.clone(), p.clone());
        if (error > 1) {
            out.println(n);
            for (int i = 0; i < n; i++) {
                out.println(x[i] + " " + p[i]);
            }
            return;
        }
        throw new AssertionError();
    }

    static double incorrect(int[] x, int[] p) {
        int n = x.length;
        List<Double> radius = new ArrayList<Double>();
        List<Integer> s = new ArrayList<Integer>();
        double sum = 0;
        for (int i = 0; i < n; i++) {
            radius.add(1. * p[i]);
            for (int j = 0; j < Math.min(s.size(), 300); j++) {
                int r = s.size() - j - 1;
                int t = s.get(r);
                double dx = x[i] - x[t];
                radius.set(i, Math.min(radius.get(i), dx * dx / (4 * radius.get(t))));
            }
            while (!s.isEmpty() && radius.get(s.get(s.size() - 1)) <= radius.get(i)) {
                s.remove(s.size() - 1);
            }
            s.add(i);
            sum += radius.get(i);
        }
        return sum;
    }

    static double correct(int[] x, int[] p) {
        int n = x.length;
        List<Circle> circles = new ArrayList<Circle>();
        double answer = 0;
        for (int i = 0; i < n; i++) {
            double left = 0;
            double right = p[i];
            double mid = (left + right) * .5;
            while (left != mid && mid != right) {
                Circle cur = new Circle(x[i], -mid, mid);
                boolean ok = true;
                for (Circle e : circles) {
                    if (e.intersects(cur)) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    left = mid;
                } else {
                    right = mid;
                }
                mid = (left + right) * .5;
            }
            circles.add(new Circle(x[i], -left, left));
            answer += left;
        }
        return answer;
    }

    static class Circle {
        double x;
        double y;
        double r;

        Circle(double x, double y, double r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        boolean intersects(Circle c) {
            double dx = x - c.x;
            double dy = y - c.y;
            return dx * dx + dy * dy < (r + c.r) * (r + c.r);
        }
    }
}
