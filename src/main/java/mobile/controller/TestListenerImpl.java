package mobile.controller;

import java.util.Map;

import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import mobile.result.ReportWriter;
import mobile.result.ResultBean;
import mobile.result.ResultHolder;
import testBase.SheetNames;
import testBase.StringCompartor;
import up.light.assertutil.AssertFailException;
import up.light.utils.LogUtil;
import up.light.wait.WaitUtil;
import up.light.writer.impl.ExcelWriter;

/**
 * 监听器，用于初始化Writer、解析view.xml、记录测试结果
 */
public class TestListenerImpl implements ITestListener, IConfigurationListener {
	public static final String TAG_SKIP = "skip";

	private static ExcelWriter mWriter;
	private static String mFolderName;

	@Override
	public void onTestStart(ITestResult result) {
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		recordResult(result, false);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		recordResult(result, false);
		checkResult(result, false);
		/*boolean b = checkNeedReset(result.getThrowable());
		recordResult(result, b);
		checkResult(result, b);*/
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		/*boolean b = checkNeedReset(result.getThrowable());
		recordResult(result, b);
		checkResult(result, b);*/
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		/*boolean b = checkNeedReset(result.getThrowable());
		recordResult(result, b);
		checkResult(result, b);*/
	}

	@Override
	public void onStart(ITestContext context) {
		//ViewXmlParser.parse();
		mWriter = ExcelWriter.getInstance();
		mFolderName = mWriter.getFolder();
		SheetNames.initialize();
	}

	@Override
	public void onFinish(ITestContext context) {
		new ReportWriter().doAfter();
	}

	@Override
	public void onConfigurationSuccess(ITestResult result) {
	}

	@Override
	public void onConfigurationFailure(ITestResult result) {
		//checkResult(result, checkNeedReset(result.getThrowable()));
	}

	@Override
	public void onConfigurationSkip(ITestResult result) {
		//checkResult(result, checkNeedReset(result.getThrowable()));
	}

	@SuppressWarnings("unchecked")
	private void recordResult(ITestResult result, boolean isCrashed) {
		WaitUtil.sleep(1500);
		String vClassName = result.getInstance().getClass().getSimpleName();
		String vTestName = SheetNames.getSheetName(vClassName);
		String vCaseName = getInvokeNumber(result.getMethod().getCurrentInvocationCount());
		int vResult = result.getStatus();

		if (vClassName.equals(result.getTestContext().getAttribute(TAG_SKIP))) {
			vResult = ITestResult.SKIP;
		}

		String vStatus = status2String(vResult);
		String vScreenShot = null;


		Map<String, String> vParamMap = null;
		Object[] vArrayParam = result.getParameters();

		if (vArrayParam.length > 0) {
			vParamMap = (Map<String, String>) (vArrayParam[0]);
		}

		String vParamStr = null;
		if (vParamMap != null) {
			vParamStr = param2String(vParamMap);
		}

		Throwable vEx = result.getThrowable();
		String vExpectStr = null;
		String vActualStr = null;

		if (vEx != null) {
			if (vEx instanceof AssertFailException) {
				AssertFailException ve = (AssertFailException) vEx;
				vActualStr = ve.getActual().toString();
				if (vParamMap != null) {
					vExpectStr = ve.getExpect().toString();
					vActualStr = StringCompartor.differenceWithArrow(vActualStr, vExpectStr, "->");
				}
			} else {
				vActualStr = vEx.getMessage();

				if (vActualStr != null) {
					int vIndex = vActualStr.indexOf("Build info: ");

					if (vIndex >= 0)
						vActualStr = vActualStr.substring(0, vIndex);
				}
			}
		}
		
		ResultBean vBean = new ResultBean(vCaseName, vStatus, vParamStr, vExpectStr, vActualStr, vScreenShot);
		ResultHolder.putResult(vTestName, vBean);
	}

	private String status2String(int status) {
		switch (status) {
		case ITestResult.SUCCESS:
			return "Pass";
		case ITestResult.FAILURE:
			return "Fail";
		case ITestResult.SKIP:
			return "Skip";
		case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
			return "PartFail";
		default:
			return "Unknown";
		}
	}

	private String getInvokeNumber(int i) {
		return String.format("%04d", i);
	}

	private String param2String(Map<String, String> param) {
		StringBuilder vSb = new StringBuilder();
		String vTmp = null;

		for (Map.Entry<String, String> e : param.entrySet()) {
			vTmp = e.getKey();
			if (vTmp.contains("验证") || vTmp.contains("委托编号"))
				continue;
			vSb.append(vTmp + ":" + e.getValue() + "\n");
		}

		if (vSb.length() > 0)
			vSb.deleteCharAt(vSb.length() - 1);

		String vNo = param.get("委托编号");
		if (vNo != null) {
			vSb.append("\n------------\n");
			vSb.append("委托编号：" + vNo);
		}

		return vSb.toString();
	}

	private void checkResult(ITestResult result, boolean isCrashed) {
		Throwable t = result.getThrowable();

		if (t != null) {
			// print stack
			t.printStackTrace();
			LogUtil.log.debug(t.getMessage(), t);
		}

	}

	private boolean checkNeedStop(Throwable t) {
		if (t instanceof StopException) {
			Controller.setNeedStop(true);
			return true;
		}

		return false;
	}

	/*private boolean checkNeedReset(Throwable t) {
		if (t instanceof UnreachableBrowserException) {
			LogUtil.log.fatal("[DRIVER CRASH] driver crashed, restarting...");
			// driver reset
			DriverFactory.resetDriver();
			LogUtil.log.fatal("[DRIVER CRASH] driver reset");

			// ContextHadler reset
			TestElement.initialize(DriverFactory.getContextHandler());
			LogUtil.log.fatal("[DRIVER CRASH] ContextHandler reset");

			// AbstractPage reset
			AbstractPage.resetDriver();
			LogUtil.log.fatal("[DRIVER CRASH] AbstractPage reset");

			// view reset
			ViewNavigator.reset();
			LogUtil.log.fatal("[DRIVER CRASH] navigator reset");

			System.gc();

			return true;
		}

		return false;
	}*/
}
