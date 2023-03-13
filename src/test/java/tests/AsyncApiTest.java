package tests;

import helpers.JsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Rule;
import org.junit.Test;
import report.ExtendReportInstance;
import watcher.ExecutionWatcher;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class AsyncApiTest {

    // Set the path for the API
    private static final String PATH = "enigma/search/async";

    @Rule
    public ExecutionWatcher executionWatcher = new ExecutionWatcher(ExtendReportInstance.getInstance());

    @Test
    // Test case 1 : Validate response 200 as valid response for async API
    public void asyncApiShouldReturnSuccessful() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(JsonBuilder.readJsonFile("Search-hotel-success.json"))
                .when()
                .post("https://www.almosafer.com/api/" + PATH) //TODO take base url dynamically from props - apply to all
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    // Test case 2 : validate the response matches the schema response for API
    public void asyncApiShouldReturnSId() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(JsonBuilder.readJsonFile("Search-hotel-success.json"))
                .when()
                .post("https://www.almosafer.com/api/" + PATH)
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("json-files/Search-hotel-schema.json"));
    }

    @Test
    // Test case 3 : Validate the response of the API is 400 in case of bad requests
    public void asyncApiShouldReturnError() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(JsonBuilder.readJsonFile("Search-hotel-error.json"))
                .when()
                .post("https://www.almosafer.com/api/" + PATH)
                .then()
                .assertThat()
                .statusCode(400)
                .body("errorType", equalTo("Validation error"))
                .body("error", equalTo("Bad Request"));
    }

    @Test
    // Test Case 4 (Non-Functional): Check response time for the Post API
    public void asyncApiResponseTime() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(JsonBuilder.readJsonFile("Search-hotel-success.json"))
                .when()
                .post("https://www.almosafer.com/api/" + PATH)
                .then()
                .assertThat().time(lessThan(500L));
    }

    @Test
    // Test case 5 : Fetch the response headers
    public void asyncApiHeaders() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(JsonBuilder.readJsonFile("Search-hotel-success.json"))
                .when()
                .post("https://www.almosafer.com/api/" + PATH)
                .then().extract().header();
    }




}