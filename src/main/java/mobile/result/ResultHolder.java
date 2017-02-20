package mobile.result;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * ResultBean容器，以Map方式存放所有测试结果
 */
public class ResultHolder {
	private static Map<String, List<ResultBean>> mResultMap = new LinkedHashMap<>();
	
	private ResultHolder() {}
	
	public static void putResult(String testName, ResultBean result) {
		List<ResultBean> vRes = mResultMap.get(testName);
		
		if(vRes == null) {
			vRes = new LinkedList<>();
		}
		
		vRes.add(result);
		mResultMap.put(testName, vRes);
		
		System.out.println(testName + ", " + result.getStatus());
	}

	public static Map<String, List<ResultBean>> getmResultMap() {
		return mResultMap;
	}
}
