package com.krce;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class CategoryTest {
    private int id;

    @BeforeClass
    public  void  setup(){
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }

    @Test(priority = 1)
    public void testCreateCategory(){
        String name = "user_"+System.currentTimeMillis();
        String image = "https://google.com";
        Map body = Map.of(
                "name",name,
                "image",image
        );
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/categories");
        response
                .then()
                .log().all()
                .statusCode(201)
                .body("name", Matchers.equalTo(name)); //response
        id = response.jsonPath().getInt("id");
    }

    @Test(priority = 2)
    public void testGetCategory(){
        RestAssured.given()
                .pathParam("id",id)
                .when()
                .get("categories/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id",Matchers.equalTo(id));
    }

    @Test(priority = 3)
    public void testGetUpdateCategory(){

        String name = "category_" + System.currentTimeMillis();
        String image = "https://google.com";

        Map body = Map.of(
                "name",name,
                "image",image
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(body)
                .when()
                .put("/categories/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", Matchers.equalTo(name));
    }
    @Test(priority = 4)
    private void testDeleteCategories(){
        RestAssured.given()
                .pathParam("id",id)
                .when()
                .delete("/categories/{id}")
                .then()
                .log().all()
                .statusCode(200);
    }


}
