package RestApi1;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.annotations.Test;

public class Main {
	
	int id;
	
	@Test(priority=1)
	void getUsers() {
		given()
		
		.when()
			.get("https://reqres.in/api/users?page=2")
			
		.then()
			.statusCode(200)
			.body("page",equalTo(2))
			.log().all();
	}
	
	@Test(priority=1)
	void createUser() {
		
		HashMap dataHashMap = new HashMap();
		dataHashMap.put("name","Mehedi");
		dataHashMap.put("job","SQA Engineer");
		
		
		id = given()
			.contentType("application/json")
			.body(dataHashMap)
		.when()
			.post("https://reqres.in/api/users")
			.jsonPath().getInt("id");
//		.then()
//			.statusCode(201)
//			.log().all();
	}
	
	@Test(priority=3, dependsOnMethods = {"createUser"} )
	void updateUser() {
		
		HashMap data = new HashMap();
		data.put("name", "Mehedi Hasan");
		data.put("job", "Test Automation Engineer");
		
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.put("https://reqres.in/api/users/"+id)
		.then()
			.statusCode(200)
			.log().all();
		
	}
	
	@Test(priority=3)
	void deleteUser() {
		given()
		.when()
			.delete("https://reqres.in/api/users/"+id)
		.then()
			.statusCode(204)
			.log().all();
	}
}
