package baseFixtures;

import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class baseFixture {
    @BeforeClass
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(8080);
        }
        else{
            RestAssured.port = Integer.valueOf(port);
        }


        String baseHost = System.getProperty("server.host");
        if(baseHost==null){
            baseHost = "https://5d586cc06bf39a0014c6d37e.mockapi.io";
        }
        RestAssured.baseURI = baseHost;

    }
    @AfterClass
    public static void teardown() {
        RestAssured.given()
                .contentType("application/json")
                .delete(RestAssured.baseURI+"/book/1")
                .then()
                .statusCode(200);
    }
}
