package mobile.controller;

import org.testng.IConfigurable;
import org.testng.IConfigureCallBack;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

/**
 * 测试执行控制器，等捕获到{@link StopException}时会跳过后续所有测试用例
 */
public class Controller implements IHookable, IConfigurable {
	private static boolean needStop;

	public static void setNeedStop(boolean flag) {
		needStop = flag;
	}

	@Override
	public void run(IConfigureCallBack callBack, ITestResult testResult) {
		if (!needStop) {
			callBack.runConfigurationMethod(testResult);
		} else {
			testResult.getTestContext().setAttribute(TestListenerImpl.TAG_SKIP, testResult.getTestClass().getRealClass().getSimpleName());
		}
	}

	@Override
	public void run(IHookCallBack callBack, ITestResult testResult) {
		if (!needStop) {
			callBack.runTestMethod(testResult);
		} else {
			testResult.getTestContext().setAttribute(TestListenerImpl.TAG_SKIP, testResult.getTestClass().getRealClass().getSimpleName());
		}
	}

}
