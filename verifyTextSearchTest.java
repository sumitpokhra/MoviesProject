package com.api.restapi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class verifyTextSearchTest {

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://services.groupkt.com";
    }

    @Test
    public void verifyRecords() {
        Response response = RestAssured.given().when().get("/state/search/IND?text=pradesh").then().contentType(ContentType.JSON).extract().response();
        JSONObject ja = new JSONObject(response.getBody().asString());
        ja = (JSONObject) ja.get("RestResponse");
        int records = Integer.parseInt(ja.get("messages").toString().replaceAll("[\\D]", ""));
        System.out.println("Total Records:- " + records);
        Assert.assertEquals(5, records);
    }

    @Test
    public void verifyLargestCity() {
        Response response = RestAssured.given().when().get("/state/search/IND?text=pradesh").then().contentType(ContentType.JSON).extract().response();
        JSONObject ja = new JSONObject(response.getBody().asString());
        ja = (JSONObject) ja.get("RestResponse");
        JSONArray jo = (JSONArray) ja.get("result");
        String actualLargestCity = null;
        for (int i = 0; i < jo.length(); i++) {
            if (jo.getJSONObject(i).getString("abbr").equalsIgnoreCase("AP")) {
                actualLargestCity = jo.getJSONObject(i).get("largest_city").toString();
                System.out.println("Largest City of AP is :- " + jo.getJSONObject(i).get("largest_city").toString());
                break;
            }
        }
        Assert.assertEquals("Largest City Not Matched for 'AP'", "Hyderabad Amaravati", actualLargestCity);
    }

    @Test
    public void printDetails() {
        Response response = RestAssured.given().when().get("/state/search/IND?text=pradesh").then().contentType(ContentType.JSON).extract().response();
        JSONObject ja = new JSONObject(response.getBody().asString());
        ja = (JSONObject) ja.get("RestResponse");
        JSONArray jo = (JSONArray) ja.get("result");
        for (int i = 0; i < jo.length(); i++) {
            if (jo.getJSONObject(i).getString("abbr").equalsIgnoreCase("MP")) {
                System.out.println("**** Details of Madhya Pradesh ****");
                System.out.println("id: " + jo.getJSONObject(i).getInt("id"));
                System.out.println("country: " + jo.getJSONObject(i).getString("country"));
                System.out.println("name: " + jo.getJSONObject(i).getString("name"));
                System.out.println("abbr: " + jo.getJSONObject(i).getString("abbr"));
                System.out.println("area: " + jo.getJSONObject(i).getString("area"));
                System.out.println("largest_city: " + jo.getJSONObject(i).getString("largest_city"));
                System.out.println("capital: " + jo.getJSONObject(i).getString("capital"));
                break;
            }
        }
    }
}
