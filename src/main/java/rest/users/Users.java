package rest.users;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;
import rest.BaseRest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Users extends BaseRest {

    private final String basePath = "/users";

    @Step
    public ValidatableResponse createUser(String requestBody) {
        ValidatableResponse response = given().filter(new AllureRestAssured()).
                spec(requestSpec).
                body(requestBody).
                when().
                post(basePath).then().spec(responseSpec);
        return response;
    }

    @Step
    public Response getUserById(String id) {
        Response response = given().filter(new AllureRestAssured()).
                spec(requestSpec).
                get(basePath + "/" + id);
        return response;
    }

    @Step
    public Response getUserByParametr(String parameter) {
        Response response = given().filter(new AllureRestAssured()).
                spec(requestSpec).
                get(basePath + parameter);
        return response;
    }

    @Step
    public Response deleteUser(String id) {
        Response response = given().filter(new AllureRestAssured()).
                spec(requestSpec).
                delete(basePath + "/" + id);
        return response;

    }

}
