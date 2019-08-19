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
        requestBody.put("author", "Test Author Adding");
        requestBody.put("title", "Test Title Adding");

        Response r = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(RestAssured.baseURI+"/book");

        r.then()
        .body("author", CoreMatchers.equalTo("Test Author Adding"));
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
                        "title", CoreMatchers.equalTo("Test Title Adding"));
    }
    @Test(priority=4)
    public void test_AuthorShouldBeRequired(){
        Map<String, String> requestBody= new HashMap<String, String>();
        requestBody.put("title", "Test Title Author Required");

        Response r = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(RestAssured.baseURI+"/book");
        //In mockapi.io, it is not possible to put extra rules about missing parameters.
        //If I do not put parameter, mockapi.io automatically create <parameter 1>... values.
        //Thats why in this case, I check <author> key as "author 1" instead of <error> key.
        r.then()
                .body("author", CoreMatchers.equalTo("author 2"));
    }
    @Test(priority=5)
    public void test_TitleShouldBeRequired(){
        Map<String, String> requestBody= new HashMap<String, String>();
        requestBody.put("author", "Test Author Title Required");

        Response r = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(RestAssured.baseURI+"/book");
        //In mockapi.io, it is not possible to put extra rules about missing parameters.
        //If I do not put parameter, mockapi.io automatically create <parameter 1>... values.
        //Thats why in this case, I check <title> key as "title 1" instead of <error> key.
        r.then()
                .body("title", CoreMatchers.equalTo("title 3"));
    }
    @Test(priority=6)
    public void test_AuthorShouldNotBeNull(){
        Map<String, String> requestBody= new HashMap<String, String>();
        requestBody.put("author", null);
        requestBody.put("title", "Test Title Author Required");

        Response r = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(RestAssured.baseURI+"/book");
        //In mockapi.io, it is not possible to put extra rules about null parameters.
        //If I put null parameter, mockapi.io add value as null
        //Thats why in this case, I check <author> key as "null" instead of <error> key.
        r.then()
                .body("author", CoreMatchers.equalTo(null));
    }
}
