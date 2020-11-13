package com.kkrasotina.mb.tests;

import com.kkrasotina.mb.schemas.ExVeError;
import com.kkrasotina.mb.schemas.VehicleStatus;
import com.kkrasotina.mb.utils.Config;
import com.kkrasotina.mb.utils.Endpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Tests for Left Front door status")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class DoorStatusFrontLeftTests {
    private String vehicleId;
    private String incorrectVehicleId;

    @BeforeAll
    public void saveTestVehicleIds() {
        vehicleId = "WDB111111ZZZ22222";
        incorrectVehicleId = "x";
    }

    @Test
    @DisplayName("1. Positive. Get status of Front Left door for existing Vehicle")
    public void testGetExistingVehicleStatus(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", Config.BEARER)
                .when()
                .get(Endpoints.doorstatusfrontleft, vehicleId);

        response.then().assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("doorstatusfrontleft-response-schema-200.json"));

        //if we need to check statuses
        var vehicleStatus = VehicleStatus.fromJson(response.body().asString());
        Assert.assertTrue(vehicleStatus.doorstatusfrontleft.value.equals("true")||
                vehicleStatus.doorstatusfrontleft.value.equals("false"));
    }

    @Test
    @DisplayName("2. Negative. Access without token")
    public void testAccessNoTokenNegative() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(Endpoints.doorstatusfrontleft, vehicleId);

        response.then().assertThat()
                .statusCode(401);

        var exVeError = ExVeError.fromJson(response.body().asString());

        Assert.assertEquals("2. Negative. Access without token","Unauthorized",exVeError.exveErrorMsg);
    }
}
