package coding;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.TestProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TestCase0 implements TestProvider {
	public Collection<Test> createTests() {
		List<Test> ret = new ArrayList<Test>();
        for (int i = 100000; i < 100000 + 1000; i++) {
//            ret.add(new Test(i + ""));
        }
        return ret;
	}
}
