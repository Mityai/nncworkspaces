package lib.test.on2013_09.on2013_09_03_.Traces;

import net.egork.chelper.tester.NewTester;

import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!NewTester.test("src/lib/test/on2013_09/on2013_09_03_/Traces/Traces.task"))
			Assert.fail();
	}
}
