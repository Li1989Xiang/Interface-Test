package test;

import java.util.Map;

import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import testBase.HttpUtil_All;
import testBase.TestDataProvider;
import up.light.assertutil.AssertUtil;
import up.light.assertutil.JsonAssertUtil;
import up.light.parser.JsonUtil;

public class TestA00Login {
	
	/*public static String httpPostWithJson(String url) throws Exception {

		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;

		httpPost.setEntity(new StringEntity(getJson().toString(), "utf-8"));
		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "UTF-8");
		}
		return respContent;
	}*/

	
	
	/*public static JsonArray getJson() {
		JsonArray ja = new JsonArray();
		JsonObject j = new JsonObject();
		JsonObject j1 = new JsonObject();
		j.addProperty("khbz", "80316041");
		j.addProperty("khbzlx", "Z");
		j.addProperty("jymm", "123321");
		j.addProperty("sessionid", "AK566S3236D33232E3");
		j.addProperty("yybdm", "5042");
		j.addProperty("token", "");
		j.addProperty("lhxx","11111111111,864394010922559,1.0.5.20161219.66099+3.0.2.20161219065646+zxjt,460079225535041,00:27:10:E4:33:20,5CFF3504BF410000,172.16.152.15");
		j.addProperty("rzfs", "1");
		ja.add(j);
		
		j1.addProperty("khbz", "888888888");
		j1.addProperty("khbzlx", "Z");
		j1.addProperty("jymm", "123321");
		j1.addProperty("sessionid", "AK566S3236D33232E3");
		j1.addProperty("yybdm", "5042");
		j1.addProperty("token", "");
		j1.addProperty("lhxx","11111111111,864394010922559,1.0.5.20161219.66099+3.0.2.20161219065646+zxjt,460079225535041,00:27:10:E4:33:20,5CFF3504BF410000,172.16.152.15");
		j1.addProperty("rzfs", "1");
		ja.add(j1);
		//StringEntity entity = new StringEntity(j.toString(), "utf-8");// 解决中文乱码问题
		return ja;
	}*/

	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void test(Map<String, String> param) throws Exception {
		//System.out.println("==================");
		JsonObject j = new JsonObject();
		
		//循环遍历param map存入jsonObject中
		for(String key :param.keySet()){
			if(key.contains("验证")||key.contains("类型")){
				continue;
			}
			j.addProperty(key, param.get(key));
		}
		//System.out.println("=======j========"+j);
		String respose = HttpUtil_All.doPostSSL(param.get("url"), j);
		//Gson string转jsonobject的方法
		//JsonObject jo = new JsonParser().parse(respose).getAsJsonObject();
		
		//校验Json返回格式是否正确（字段是否缺失，返回类型是否正确）
		//System.out.println("========respose======="+respose);
		String vCheckJson = param.get("Json验证");
		if(param.get("类型").equals("正例")){
			JsonAssertUtil.JsonAssert(vCheckJson,respose,"TestA00Login");
		}else if(param.get("类型").equals("反例")){
			JsonAssertUtil.JsonAssert(vCheckJson, respose, "TestA00LoginFL");
		}else{
			throw new RuntimeException("无此类型");
		}
		
		//jsonPath获取指定路径的value
		String actual = JsonUtil.getValue(respose, "$.cljg[0].message");
		String vCheckPoint1 = param.get("验证1");
		//AssertUtil.assertContains(respose, vCheckPoint1);
		AssertUtil.assertEquals(vCheckPoint1, actual);
		
		
		//Interface_Test/http/json
		
	}
}
