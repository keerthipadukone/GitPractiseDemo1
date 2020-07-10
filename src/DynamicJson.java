import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	//Addbook
	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String response =given().header("Content-Type","application/json")
		.body(payload.Addbook(isbn,aisle))
		.when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js=ReUsableMethods.rawToJson(response);
		String id=js.get("ID");
		System.out.println(id);
	}

	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		//array->collection of elements
		//multidimensional array->collection of arrays
		return new Object[][] {{"skyhe","70581"},{"nreycc","66907"}, {"jutdv","9773"} };
	}
	
	
	
	
}
