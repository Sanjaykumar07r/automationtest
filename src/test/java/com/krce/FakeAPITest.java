package com.krce;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.reporters.jq.Main;

import java.util.List;

public class FakeAPITest {
    @BeforeClass
    public void setup(){
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }

    @Test
    public  void  testGetProducts(){
        RestAssured.given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("size()", Matchers.greaterThan(0));
    }
    @Test
    public  void  testFilterProductsByPrice(){
        RestAssured.given()
                .queryParam("price",100)
                .when()
                .get("/products/")
                .then()
                .statusCode(200)
                .body("[0].price",Matchers.equalTo(100));
    }

    @Test
    public  void  testGetCategories(){
        RestAssured.given()

                .when()
                .get("/categories")
                .then()
                .statusCode(200)
                .body("$",Matchers.instanceOf(List.class));
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

    @Test
    public  void  testFilterProductsPriceRange(){
        RestAssured.given()
                .queryParam("price_min",100)
                .queryParam("price_max",900)
                .when()
                .get("/products/")
                .then()
                .statusCode(200)
                .body("price",Matchers.everyItem(Matchers.allOf(Matchers.greaterThanOrEqualTo(100),Matchers.lessThanOrEqualTo(900))));
    }
    @Test
    public  void  testFilterProductsPagination(){
        RestAssured.given()
                .queryParam("price_min",100)
                .queryParam("price_max",900)
                .queryParam("limit",10)
                .queryParam("offset",10)
                .when()
                .get("/products/")
                .then()
                .statusCode(200)
                .body("size()",Matchers.lessThanOrEqualTo(10))  //paging
                .body("price",Matchers.everyItem(Matchers.allOf(Matchers.greaterThanOrEqualTo(100),Matchers.lessThanOrEqualTo(900))));
    }







    @Test
    public  void  testFilterProductsById(){
        RestAssured.given()
                .pathParam("id",1)
                .when()
                .get("/categories/{id}")
                .then()
                .statusCode(200)
                .body("id",Matchers.equalTo(1));
    }


}
