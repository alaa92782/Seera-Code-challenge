package tests;

import helpers.JsonBuilder;
import io.restassured.http.ContentType;
import org.junit.Rule;
import org.junit.Test;
import report.ExtendReportInstance;
import watcher.ExecutionWatcher;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class FaresCalenderApiTest {

    private static final String PATH = "v3/flights/flight/get-fares-calender";

    @Rule
    public ExecutionWatcher executionWatcher = new ExecutionWatcher(ExtendReportInstance.getInstance());

    @Test
    // Test case 1 : Make sure the fares calendar returns a successful response
    public void faresCalendarShouldReturnSuccessful() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(JsonBuilder.readJsonFile("Search-flight-success.json"))
                .when()
                .post("https://www.almosafer.com/api/" + PATH)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    // Test case 2: Assert that the response body matches the expected response body
    public void faresCalendarApiBody() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(JsonBuilder.readJsonFile("Search-flight-success.json"))
                .when()
                .post("https://www.almosafer.com/api/" + PATH)
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("json-files/Search-flight-schema.json"));
    }

    @Test
    // Test case 3 : Assert that the API returns 400 status code for invalid response
    public void faresCalendarReturnError() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(JsonBuilder.readJsonFile("Search-flight-error.json"))
                .when()
                .post("https://www.almosafer.com/api/" + PATH)
                .then()
                .assertThat()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("title", equalTo("[Gateway:``] Bad Request"));
    }

    @Test
    // Test case 4 : Make sure the API response time does not exceed specific value
    public void faresCalendarResponseTime() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(JsonBuilder.readJsonFile("Search-flight-success.json"))
                .when()
                .post("https://www.almosafer.com/api/" + PATH)
                .then()
                .assertThat().time(lessThan(500L));

}

    @Test
    // Test case 5 : Fetch the response headers
    public void faresCalendarHeaders() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(JsonBuilder.readJsonFile("Search-flight-success.json"))
                .when()
                .post("https://www.almosafer.com/api/" + PATH)
                .then()
                .extract().header();

    }
}