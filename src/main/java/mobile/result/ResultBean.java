package mobile.result;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * POJO，用于存放测试结果
 */
public class ResultBean {
	private String mCaseName;	//用例名称
	private String mStatus;		//结果
	private String mParameter;	//参数
	private String mExpect;		//期望结果
	private String mActual;		//实际结果
	private String mScreenShot;	//截图路径
	
	public ResultBean(String caseName, String status, String parameter, String expect, String actual,
			String screenShot) {
		mCaseName = caseName;
		mStatus = status;
		mParameter = parameter;
		mExpect = expect;
		mActual = actual;
		mScreenShot = screenShot;
	}
	
	public Map<String, String> getMap() {
		Map<String, String> vMap = new HashMap<>();
		vMap.put("caseName", mCaseName);
		vMap.put("status", mStatus);
		vMap.put("parameter", mParameter);
		vMap.put("expect", mExpect);
		vMap.put("actual", mActual);
		vMap.put("screenShot", mScreenShot);
		
		return vMap;
	}

	public String getCaseName() {
		return mCaseName;
	}

	public void setCaseName(String caseName) {
		mCaseName = caseName;
	}

	public String getStatus() {
		return mStatus;
	}

	public void setStatus(String status) {
		mStatus = status;
	}

	public String getParameter() {
		return mParameter;
	}

	public void setParameter(String parameter) {
		mParameter = parameter;
	}

	public String getExpect() {
		return mExpect;
	}

	public void setExpect(String expect) {
		mExpect = expect;
	}

	public String getActual() {
		return mActual;
	}

	public void setActual(String actual) {
		mActual = actual;
	}

	public String getScreenShot() {
		return mScreenShot;
	}

	public void setScreenShot(String screenShot) {
		mScreenShot = screenShot;
	}

	@Override
	public String toString() {
		Field[] vFs = ResultBean.class.getDeclaredFields();
		StringBuilder vSb = new StringBuilder();
		Object vStr = null;
		
		for(Field f : vFs) {
			try {
				f.setAccessible(true);
				vStr = f.get(this);
				vStr = vStr == null ? "" : vStr;
				vSb.append(vStr);
				vSb.append(", ");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(vSb.length() > 0) {
			vSb.deleteCharAt(vSb.length() - 1);
		}
		
		return vSb.toString();
	}
}