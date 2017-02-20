package mobile.result;

import java.util.List;
import java.util.Map;

import up.light.writer.IWriter;
import up.light.writer.impl.ExcelWriter;

/**
 * 报告生成器
 */
public class ReportWriter {

	public void doAfter() {
		Map<String, List<ResultBean>> vMap = ResultHolder.getmResultMap();
		IWriter vWriter = ExcelWriter.getInstance();
		
		for(Map.Entry<String, List<ResultBean>> e : vMap.entrySet()) {
			String vTestName = e.getKey();
			List<ResultBean> vList = e.getValue();
			
			for(ResultBean bean : vList) {
				Map<String, String> vToWrite = bean.getMap();
				vToWrite.put("testName", vTestName);
				vWriter.writeLine(vToWrite);
			}
		}
	}

}
