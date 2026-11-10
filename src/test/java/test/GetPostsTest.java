package test;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import util.LoggerUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetPostsTest extends BaseTest {

    @Test
    @Tag("smoke")
    @DisplayName("GET /posts/1 returns the expected post")
    void getPost() {
        LoggerUtil.info("Requesting GET /posts/1");
        Response res = given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("userId", equalTo(1))
                .body("id", equalTo(1))
                .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
                .body("body", startsWith("quia et suscipit"))
                .extract()
                .response();

        LoggerUtil.info("Response (" + res.statusCode() + ", " + res.time() + " ms):\n" + res.asPrettyString());
    }

    @Test
    @Tag("smoke")
    @DisplayName("GET all /posts returns a list of posts")
    void getAllPosts() {
        LoggerUtil.info("Requesting GET /posts");
        Response res = given()
                .contentType("application/json")
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("id", everyItem(notNullValue()))
                .body("[0].id", equalTo(1))
                .extract()
                .response();

        LoggerUtil.info("Response (" + res.statusCode() + ", " + res.time() + " ms):\n" + res.asPrettyString());
    }

    @Test
    @Tag("regression")
    @DisplayName("GET /posts/9999 returns 404 for a non-existent post")
    void getInvalidPost() {
        LoggerUtil.info("Requesting GET invalid /posts");
        Response res = given()
                .when()
                .get("/posts/9999")
                .then()
                .statusCode(404)
                .extract()
                .response();

        LoggerUtil.info("Response (" + res.statusCode() + ", " + res.time() + " ms):\n" + res.asPrettyString());
    }

    @Test
    @Tag("regression")
    @DisplayName("GET /posts?userId=1 returns posts for userId=1")
    void getPostByUserID() {
        LoggerUtil.info("Requesting GET /posts?userId=1");
        Response res = given()
                .queryParam("userId", 1)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", equalTo(10))
                .body("userId", everyItem(equalTo(1)))
                .extract()
                .response();

        LoggerUtil.info("Response (" + res.statusCode() + ", " + res.time() + " ms):\n" + res.asPrettyString());
    }

    @Test
    @Tag("regression")
    @DisplayName("GET /posts/1/comments ")
    void getPostComments() {
        LoggerUtil.info("Requesting GET /posts/1/comments");
        Response res = given()
                .pathParam("id", 1)
                .when()
                .get("/posts/{id}/comments")
                .then()
                .statusCode(200)
                .body("size()", equalTo(5))
                .body("postId", everyItem(equalTo(1)))
                .body("[0].email", equalTo("Eliseo@gardner.biz"))
                .extract()
                .response();

        LoggerUtil.info("Response (" + res.statusCode() + ", " + res.time() + " ms):\n" + res.asPrettyString());
    }
}
