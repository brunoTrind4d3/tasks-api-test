package br.trindade;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8002/tasks-backend";
    }

    @Test
    public void mustReturnTasks(){
        RestAssured.given()
                .when()
                    .get("/todo")
                .then()
                    .statusCode(200)
        ;
    }

    @Test
    public void mustAddTask(){
        RestAssured.given()
                .body("{\"task\":\"Teste via API\", \"dueDate\": \"2030-12-30\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(201)
        ;
    }

    @Test
    public void cantAddTasksWithPasteDate(){
        RestAssured.given()
                .body("{\"task\":\"Teste via API\", \"dueDate\": \"2010-12-30\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(400).body("message", CoreMatchers.is("Due date must not be in past"))
        ;
    }

}

