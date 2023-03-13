package watcher;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class ExecutionWatcher extends TestWatcher {

	private ExtentTest test;
	private ExtentReports extent;

	public ExecutionWatcher(ExtentReports extent) {
		this.extent = extent;
	}

	@Override
	protected void starting(Description description) {
		String testCaseName = className(description.getClassName()) + "." + description.getMethodName();
		test = extent.createTest(testCaseName);
	}

	private String className(String className) {
		String[] split = className.split("tests");
		return split[1].substring(1);
	}

	@Override
	protected void succeeded(Description description) {
		test.log(Status.PASS, "Tests executed successfully!");
	}

	@Override
	protected void failed(Throwable e, Description description) {
		test.log(Status.FAIL, "Tests failed!");
		test.log(Status.FAIL, "Reason(s): " + e);
	}
}