package tests;

import org.junit.Rule;
import org.junit.Test;
import report.ExtendReportInstance;
import watcher.ExecutionWatcher;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;

public class HotelDetailsApiTest {

    private static final String PATH = "enigma/hotel-details/1288771"; //TODO make hotelId dynamic

    @Rule
    public ExecutionWatcher executionWatcher = new ExecutionWatcher(ExtendReportInstance.getInstance());

    @Test
    public void hotelDetailsValidResponse() {
        // Test 1 : Hotel Details API returning 200 as a valid response for the API
        // Specify the base URL to the RESTful web service
        // Assert that correct status code is returned.
        given()
                .when()
                .get("https://www.almosafer.com/api/" + PATH)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void atgHotelIdValidBody() {
        // Test 2 : Hotel Details API response matches the schema
        // Specify the base URL to the RESTful web service
        // Assert that correct status code is returned.
        given()
                .when()
                .get("https://www.almosafer.com/api/" + PATH)
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("json-files/HotelDetialsResponseSchema.json"));
    }

    @Test
    public void atgHotelWrong() {
        // Test 3 : Sending wrong hotel atg id to get response for invalid
        // Specify the base URL to the RESTful web service
        // Assert that correct status code is returned.
        given()
                .when()
                .get("https://www.almosafer.com/api/" + PATH + 12345)
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void atgResponseTime() {
        // Test 4 : Checking time for Get API response does not go over a specific time
        // Specify the base URL to the RESTful web service
        // Assert that response time does not exceed specific amount
        given()
                .when()
                .get("https://www.almosafer.com/api/" + PATH)
                .then()
                .assertThat().time(lessThan(500L));
    }

    @Test
    public void atgResponseHeaders() {
        // Test 5 : Fetch headers for the API response
        // Specify the base URL to the RESTful web service
        // Assert that response time does not exceed specific amount
        given()
                .when()
                .get("https://www.almosafer.com/api/" + PATH)
                .then()
                .extract().header();

    }
}