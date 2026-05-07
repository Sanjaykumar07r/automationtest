package com.krce;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PriceTest {
    @BeforeClass
    public void setup(){
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }

    @Test
    public  void  testFilterProductByTitle(){
        RestAssured.given()
                .queryParam("title","Producto")
                .when()
                .get("/products/")
                .then()
                .statusCode(200)
                .body("[0].title", Matchers.containsString("Producto"));
    }

}
