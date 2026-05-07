package com.krce;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class UsersTest {
    @BeforeClass
    public void setup(){
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }

    @Test(priority = 1)
    public void testCreateUser(){
        String name = "user_"+System.currentTimeMillis();
        String email = "user_" + System.currentTimeMillis() + "@gmail.com";
        String password = "sanjay123";
        String avatar = "https://i.imgur.com/LDOO4Qs.jpg";
        Map body = Map.of(
                "name",name,
                "email",email,
                "password",password,
                "avatar",avatar
        );
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", Matchers.equalTo(name))
                .body("email", Matchers.equalTo(email));


    }
}
