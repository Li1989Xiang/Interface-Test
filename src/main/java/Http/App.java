package Http;

import org.testng.annotations.Test;

import up.light.Runner;

public class App {

	@Test
	public static void main(String[] args) {
		String platform = null;

		if (args.length > 0) {
			platform = args[0];
		}

		Runner runner = new Runner();
		runner.run(platform);
	
	
	
		//System.out.println("===="+tl.test());
		}
	
	/*@Test
	public void test (){
		System.out.println("1");
		String jsonSchemaPath = System.getProperty("user.dir") + "/json/jsonSchame001.json";
		String jsonPath = System.getProperty("user.dir") + "/json/json001.json";
		JsonAssertUtil.JsonAssert(jsonSchemaPath, jsonPath);
	}*/

}
