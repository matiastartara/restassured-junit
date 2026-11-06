package test;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import util.LoggerUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreatePostTest extends BaseTest {

    @Test
    @Tag("regression")
    @DisplayName("POST /posts creates a post")
    void createPost() {
        LoggerUtil.info("Requesting POST /posts");
        Response res = given()
                .contentType("application/json")
                .body("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}")
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("userId", equalTo(1))
                .body("id", greaterThan(0))
                .extract()
                .response();

        LoggerUtil.info("Response (" + res.statusCode() + ", " + res.time() + " ms):\n" + res.asPrettyString());
    }
}
