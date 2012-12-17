import java.io.InputStreamReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.Writer;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * @author Niyaz Nigmatullin
 */
public class Main {
	public static void main(String[] args) {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("gangs.in");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream("gangs.out");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		FastScanner in = new FastScanner(inputStream);
		FastPrinter out = new FastPrinter(outputStream);
		Gangs solver = new Gangs();
		solver.solve(1, in, out);
		out.close();
	}
}

class Gangs {
    public void solve(int testNumber, FastScanner in, FastPrinter out) {
        int all = in.nextInt();
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        int maximal = 0;
        int maxID = -1;
        for (int i = 1; i < n; i++) {
            if (maximal < a[i]) {
                maximal = a[i];
                maxID = i;
            }
        }
        all -= a[0];
        if (2 * maximal > all) {
            int left = 2 * maximal - all;
            if (left >= a[0]) {
                out.println("NO");
                return;
            }
            out.println("YES");
            out.println(a[0] - left);
            for (int j = 0; j < left; j++) {
                out.println(1);
                --a[0];
            }
            for (int j = 0; j < left; j++) {
                out.println(maxID + 1);
                --a[maxID];
            }
            doThat(out, a);
            while (a[0] > 0) {
                out.println(1);
                --a[0];
            }
            return;
        }
        int left = all % 2;
        if (left >= a[0]) {
            out.println("NO");
            return;
        }
        out.println("YES");
        out.println(a[0] - left);
        if (left > 0) {
            out.println(1);
            a[0]--;
            boolean ok = false;
            for (int j = 1; j < n; j++) {
                a[j]--;
                if (isGood(a)) {
                    out.println(j + 1);
                    ok = true;
                    break;
                }
                ++a[j];
            }
            if (!ok) {
                throw new AssertionError();
            }
        }
        doThat(out, a);
        while (a[0] > 0) {
            --a[0];
            out.println(1);
        }
    }

    static boolean isGood(int[] b) {
        int all = 0;
        int maximal = 0;
        for (int i = 1; i < b.length; i++) {
            all += b[i];
            maximal = Math.max(maximal, b[i]);
        }
        return 2 * maximal <= all;
    }

    static boolean isGood2(int[] b) {
        int all = 0;
        int maximal = 0;
        int maximal2 = 0;
        for (int i = 1; i < b.length; i++) {
            if (maximal < b[i]) {
                maximal2 = maximal;
                maximal = b[i];
            } else if (maximal2 < b[i]) {
                maximal2 = b[i];
            }
        }
        if (maximal > maximal2 + 1) {
            return all - 2 >= (maximal - 2) * 2;
        } else {
            return all - 2 >= (maximal - 1) * 2;
        }
    }

    static void doThat(FastPrinter out, int[] a) {
        int n = a.length;
        int all = 0;
        for (int i = 1; i < n; i++) {
            all += a[i];
        }
        loop:
        while (all > 0) {
            for (int i = 1; i < n; i++) {
                if (a[i] >= 2 && isGood2(a)) {
                    a[i] -= 2;
                    all -= 2;
                    out.println(i + 1);
                    out.println(i + 1);
                    continue loop;
                }
                for (int j = i + 1; j < n; j++) {
                    a[i]--;
                    a[j]--;
                    if (a[i] >= 0 && a[j] >= 0 && isGood(a)) {
                        all -= 2;
                        out.println(i + 1);
                        out.println(j + 1);
                        continue loop;
                    }
                    ++a[i];
                    ++a[j];
                }
            }
        }
    }
}

class FastScanner extends BufferedReader {

    boolean isEOF;

    public FastScanner(InputStream is) {
        super(new InputStreamReader(is));
    }

    public int read() {
        try {
            int ret = super.read();
            if (isEOF && ret < 0) {
                throw new InputMismatchException();
            }
            isEOF = ret == -1;
            return ret;
        } catch (IOException e) {
            throw new InputMismatchException();
        }
    }

    static boolean isWhiteSpace(int c) {
        return c >= -1 && c <= 32;
    }

    public int nextInt() {
        int c = read();
        while (isWhiteSpace(c)) {
            c = read();
        }
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int ret = 0;
        while (!isWhiteSpace(c)) {
            if (c < '0' || c > '9') {
                throw new NumberFormatException("digit expected " + (char) c
                        + " found");
            }
            ret = ret * 10 + c - '0';
            c = read();
        }
        return ret * sgn;
    }

    }

class FastPrinter extends PrintWriter {

    public FastPrinter(OutputStream out) {
        super(out);
    }

    public FastPrinter(Writer out) {
        super(out);
    }


}

