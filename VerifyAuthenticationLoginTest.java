package com.api.restapi;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class VerifyAuthenticationLoginTest {

    @BeforeClass
    public void init() {
        RestAssured.baseURI = "https://reqres.in/";
    }
    
    @Test(testName = "Verify Successful Authentication")
    public void verifySuccessAuthentication() {
        HashMap<String, String> registeration = new HashMap<>();
        registeration.put("email", "peter@klaven");
        registeration.put("password", "cityslicka");
         /*Response response = RestAssured.given()
                            .when()
                             .body(registeration)
                             .post("/api/register")
                             .then()
                             .extract()
                             .response();*/
         
         RequestSpecification req = RestAssured.given().relaxedHTTPSValidation();
         req.body(registeration);
         Response response = req.post("/api/users");
         JsonPath jsonPath = response.jsonPath();
         assertEquals(response.getStatusCode(), 200);
        String authToken = jsonPath.get("token");
        System.out.println("Authentication Token: "+ authToken);
    }
}
