package com.kkrasotina.mb.tests;

import com.kkrasotina.mb.models.RoofTopStatuses;
import com.kkrasotina.mb.schemas.ExVeError;
import com.kkrasotina.mb.schemas.VehicleStatus;
import com.kkrasotina.mb.utils.Config;
import com.kkrasotina.mb.utils.Endpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.*;

@DisplayName("Tests for RoofTop status")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class RofTopStatusTests {

    private String vehicleId;
    private String incorrectVehicleId;

    @BeforeAll
    public void saveTestVehicleId() {
        vehicleId = "WDB111111ZZZ22222";
        incorrectVehicleId = "x";
    }

    @Test
    @DisplayName("1. Positive. Get RoofTop status of existing Vehicle")
    public void testGetExistingVehicleStatus(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", Config.BEARER)
                .when()
                .get(Endpoints.rooftopstatus, vehicleId);

        response.then().assertThat()
                .statusCode(200);

        var vehicleStatus = VehicleStatus.fromJson(response.body().asString());

        Assert.assertNotNull("cannot deserialize json: " + response.body().asString(), vehicleStatus);
        Assert.assertNotNull("cannot deserialize json: " + response.body().asString(), vehicleStatus.rooftopstatus);

        // if we want to check 'status' and 'timestamp' values:
        Assert.assertTrue(vehicleStatus.rooftopstatus.value.equals(RoofTopStatuses.UNLOCKED) ||
                vehicleStatus.rooftopstatus.value.equals(RoofTopStatuses.OPEN_AND_LOCKED) ||
                vehicleStatus.rooftopstatus.value.equals(RoofTopStatuses.CLOSED_AND_LOCKED));
    }

    @Test
    @DisplayName("2. Negative. Access without token")

    public void testAccessNoTokenNegative() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(Endpoints.rooftopstatus, vehicleId);

        response.then().assertThat()
                .statusCode(401);

        var exVeError = ExVeError.fromJson(response.body().asString());

        Assert.assertEquals("2. Negative. Access without token","Unauthorized",exVeError.exveErrorMsg);
    }

    @Test
    @DisplayName("3. Negative. No such Vehicle")
    public void testAccessNoSuchVehicleNegative(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", Config.BEARER)
                .when()
                .get(Endpoints.rooftopstatus, incorrectVehicleId);

        response.then().assertThat()
                .statusCode(404);

        var exVeError = ExVeError.fromJson(response.body().asString());

        Assert.assertEquals("3. Negative. No such Vehicle","Not Found",exVeError.exveErrorMsg);
    }

    @Test
    @DisplayName("4. Negative. Incorrect method")
    public void testIncorrectMethodNegative(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", Config.BEARER)
                .when()
                .put(Endpoints.rooftopstatus, vehicleId);

        response.then().assertThat()
                .statusCode(405);
    }

    @AfterAll
    public void deleteTestVehicleId() {
    }

//    @Test
//    @Ignore
//    @DisplayName("6. Verify response for heavy request payload")
//    public void testHeavyRequest()
//    {
//        String heavyPayload = "";
//        for (int i = 0; i < 1024*1024; i++)
//        {
//            heavyPayload += "A";
//        }
//
//        Response response = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .header("Authorization", Config.BEARER)
//                .body(heavyPayload)
//                .when()
//                .get(Endpoints.rooftopstatus, vehicleId);
//
//        response.then().assertThat()
//                .statusCode(413);
//    }
}
