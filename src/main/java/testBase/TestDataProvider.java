package testBase;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import up.light.FolderType;
import up.light.Setting;
import up.light.io.FileSystemResource;
import up.light.utils.DataIterator;

/**
 * Test数据提供者，采用迭代器模式，迭代过程由TestNG控制，无需人为干预
 */
public class TestDataProvider {
	private static FileSystemResource mResource;

	@DataProvider(name = "dp")
	public static DataIterator data(Method m) {
		String vClassName = m.getDeclaringClass().getSimpleName();
		String vSheetName = SheetNames.getSheetName(vClassName);

		if (mResource == null) {
			String p = Setting.getPath(FolderType.DATA);
			mResource = new FileSystemResource(p + "data.xls");
		}

		return new DataIterator(mResource, vSheetName);
	}
}
