package com.api.restapi;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class VerifyUserRegistrationTest {

    @BeforeClass
    public void init() {
        RestAssured.baseURI = "http://restapi.demoqa.com/customer";
    }

    @Test(dataProvider = "userData")
    public void testUserRegistration(String firstName, String lastName, String userName, String password, String email) {
        HashMap<String, String> userDetails = new HashMap<>();
        userDetails.put("FirstName", firstName);
        userDetails.put("LastName", lastName);
        userDetails.put("UserName", userName);
        userDetails.put("Password", password);
        userDetails.put("Email", email);
        RequestSpecification req = RestAssured.given();
        req.body(userDetails);
        Response response = req.post("/register");
        assertEquals(response.getStatusCode(), 201, "Status Code Verification Failed");
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        System.out.println(response.getBody().asString());
        assertEquals(jsonObject.get("SuccessCode").toString(), "OPERATION_SUCCESS", "Response Validation Failed");
        System.out.println("SuccessCode: " + jsonObject.get("SuccessCode").toString());
        System.out.println("Message: " + jsonObject.get("Message").toString());
        System.out.println();
    }

    @Test(dependsOnMethods = "testUserRegistration", dataProvider = "userData")
    public void sameUserDataTest(String firstName, String lastName, String userName, String password, String email) {
        HashMap<String, String> userDetails = new HashMap<>();
        userDetails.put("FirstName", firstName);
        userDetails.put("LastName", lastName);
        userDetails.put("UserName", userName);
        userDetails.put("Password", password);
        userDetails.put("Email", email);
        RequestSpecification req = RestAssured.given();
        req.body(userDetails);
        Response response = req.post("/register");
        System.out.println("**** Verification of Response Body when User already exists ****");
        assertEquals(response.getStatusCode(), 200, "Status Code Verification Failed");
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        System.out.println(response.getBody().asString());
        assertEquals(jsonObject.get("fault").toString(), "FAULT_USER_ALREADY_EXISTS", "Response Validation Failed");
        System.out.println("FaultId: " + jsonObject.get("FaultId").toString());
        System.out.println("fault: " + jsonObject.get("fault").toString());
    }

    @DataProvider(name = "userData")
    public static Object[][] userDetails() {
        return new Object[][] { { "selenium123", "java", "seleniumjava123", "demo@123", "seleniumjava123@gmail.com" }};
    }
}
