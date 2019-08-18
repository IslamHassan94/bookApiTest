package bookApiTests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

public class bookInsertionTest extends baseFixtures.baseFixture {
    @Test(priority=1)
    public void test_ThereShouldNoBookInStore(){
        RestAssured.when()
                .get(RestAssured.baseURI+"/book")
                .then()
                .body("isEmpty()", CoreMatchers.is(true));
    }
    @Test(priority=2)
    public void test_AuthorAndTitleShouldBeAdded(){
        Map<String, String> requestBody= new HashMap<String, String>();
        requestBody.put("author", "Test Author");
        requestBody.put("title", "Test Title");

        Response r = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(RestAssured.baseURI+"/book");

        r.then()
        .body("author", CoreMatchers.equalTo("Test Author"));
    }
    @Test(priority=3)
    public void test_AuthorShouldBeUpdatedByPut(){
        Map<String, String> requestBody= new HashMap<String, String>();
        requestBody.put("author", "Updated Test Author");

        Response r = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put(RestAssured.baseURI+"/book/1");

        r.then()
                .body("author", CoreMatchers.equalTo("Updated Test Author"),
                        "title", CoreMatchers.equalTo("Test Title"));
    }
}
