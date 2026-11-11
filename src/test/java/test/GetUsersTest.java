package test;

import io.restassured.response.Response;
import model.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import util.LoggerUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class GetUsersTest extends BaseTest {

    @Test
    @Tag("regression")
    @DisplayName("GET /users/1 returns nested address and company data")
    void getUserWithNestedData() {
        LoggerUtil.info("Requesting GET /users/1");
        Response res = given()
                .pathParam("id", 1)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Leanne Graham"))
                .body("username", equalTo("Bret"))
                .body("email", equalTo("Sincere@april.biz"))
                .body("address.city", equalTo("Gwenborough"))
                .body("address.geo.lat", equalTo("-37.3159"))
                .body("company.name", equalTo("Romaguera-Crona"))
                .extract()
                .response();

        LoggerUtil.info("Response (" + res.statusCode() + ", " + res.time() + " ms):\n" + res.asPrettyString());
    }

    @Test
    @Tag("regression")
    @DisplayName("GET /users/1/todos deserializes into Todo objects")
    void getUserTodosAsPojo() {
        LoggerUtil.info("Requesting GET /users/1/todos");
        Todo[] todos = given()
                .pathParam("id", 1)
                .when()
                .get("/users/{id}/todos")
                .then()
                .statusCode(200)
                .extract()
                .as(Todo[].class);

        LoggerUtil.info("Deserialized " + todos.length + " todos; first: " + todos[0]);

        assertEquals(20, todos.length);
        assertEquals("delectus aut autem", todos[0].title());
        assertFalse(todos[0].completed());
        for (Todo todo : todos) {
            assertEquals(1, todo.userId(), "Every todo should belong to userId=1");
        }
    }
}
