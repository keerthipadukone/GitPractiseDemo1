import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.payload;

public class BuildingEndToEndAutomation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//AddPlace

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response =given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().log().all().statusCode(200).body("scope",  equalTo("APP"))
		.header("Server", "Apache/2.4.18 (Ubuntu)")
		.extract().asString();
		
		System.out.println(response);
		
		JsonPath js=new JsonPath(response);
		String placeId=js.getString("place_id");
		
		System.out.println(placeId);
		
		//UpdatePlace
		String newAddress = "70 winter walk, USA";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeId+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"").
		 when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
	
		
		//GetPlace
		String getPlaceResponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1= new JsonPath(getPlaceResponse);
		String actualAddress=js1.getString("address");
		
		System.out.println(actualAddress);
		
	}

}
