package com.api.restapi;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * Unit test for simple App.
 */
@RunWith(Parameterized.class)
public class AppTest {
    private String country;
    private int    actualRecords;

    public AppTest(String country, int records) {
        super();
        this.country = country;
        this.actualRecords = records;
    }

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://services.groupkt.com";
    }

    /*
     * Passing Parameters as Countries and expected state count
     */
    @Parameterized.Parameters
    public static Object loadData() {
        return Arrays.asList(new Object[][] { { "IND", 36 }, { "USA", 55 }, { "ind", 0 }, { "aus", 0 } });
    }

    @Test
    public void getStates() {
        Response response = RestAssured.given().when().get("/state/get/" + country +
                "/all").then().contentType(ContentType.JSON).extract().response();
        JsonPath jsonPath = response.jsonPath();
        // Getting records count and replacing all letters
        int records = Integer.parseInt(jsonPath.get("RestResponse.messages").toString().replaceAll("[\\D]", ""));
        System.out.println(response.asString());
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(actualRecords, records);
    }
}
