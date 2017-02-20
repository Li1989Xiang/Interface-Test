package testBase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import up.light.FolderType;
import up.light.Setting;
import up.light.utils.ArgumentUtil;

/**
 * 用于从sheetname.json中获取每个Test对应的Excel表名
 */
public class SheetNames {
	private static Map<String, String> names = new HashMap<>();

	public static String getSheetName(String key) {
		String v = names.get(key);
		ArgumentUtil.notNull(v, "can't get sheetname with key: " + key);
		return v;
	}

	/**
	 * 解析sheetname.json并存入Map
	 */
	public static void initialize() {
		String file = Setting.getPath(FolderType.CONFIGURATION) + "sheetname.json";
		FileReader r = null;

		try {
			r = new FileReader(file);
			Gson g = new Gson();

			Type type = new TypeToken<Map<String, String>>() {
				private static final long serialVersionUID = 1L;
			}.getType();

			names = g.fromJson(r, type);

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}

}
