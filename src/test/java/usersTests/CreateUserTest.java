package usersTests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.json.simple.JSONObject;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.Description;
import rest.users.Users;
import utils.Utils;

public class CreateUserTest {
    Users users = new Users();
    Utils utils = new Utils();

    @Test
    @Description("Create a new user with all fields. \nCheck status code and fields of the response." +
            "\n Delete created user.")
    public void createUserWithAllFields() {

        JSONObject requestBody = new JSONObject();
        Response response;
        String email = utils.randomEmail();
        String userId;

        requestBody.put("first_name", "TestUserFirstName");
        requestBody.put("last_name", "TestUserLastName");
        requestBody.put("email", email);
        requestBody.put("gender", "male");
        requestBody.put("dob", "1988-05-09");
        requestBody.put("phone", "+18052388903");
        requestBody.put("website", "https://restapi1.test");
        requestBody.put("address", "Russia, Moscow, Test street 1, dom 4, kv. 22");
        requestBody.put("status", "active");

        response = users.createUser(requestBody.toJSONString()).statusCode(302).
                body("result.first_name", equalTo(requestBody.get("first_name"))).
                body("result.last_name", equalTo(requestBody.get("last_name"))).
                body("result.email", equalTo(requestBody.get("email"))).
                body("result.gender", equalTo(requestBody.get("gender"))).
                body("result.dob", equalTo(requestBody.get("dob"))).
                body("result.phone", equalTo(requestBody.get("phone"))).
                body("result.website", equalTo(requestBody.get("website"))).
                body("result.address", equalTo(requestBody.get("address"))).
                body("result.status", equalTo(requestBody.get("status"))).
                extract().response();

        userId = response.body().path("result.id");
        users.deleteUser(userId);
    }

    @Test
    @Description("Create a new user with required fields. \nCheck status code and fields of the response." +
            "\n Delete created user.")
    public void createUserWithRequiredFields() {

        JSONObject requestBody = new JSONObject();
        Response response;
        String email = utils.randomEmail();
        String userId;

        requestBody.put("first_name", "TestUserFirstName");
        requestBody.put("last_name", "TestUserLastName");
        requestBody.put("email", email);
        requestBody.put("gender", "male");

        response = users.createUser(requestBody.toJSONString()).statusCode(302).
                body("result.first_name", equalTo(requestBody.get("first_name"))).
                body("result.last_name", equalTo(requestBody.get("last_name"))).
                body("result.email", equalTo(requestBody.get("email"))).
                body("result.gender", equalTo(requestBody.get("gender"))).
                body("result.dob", equalTo(null)).
                body("result.phone", equalTo(null)).
                body("result.website", equalTo(null)).
                body("result.address", equalTo(null)).
                body("result.status", equalTo(null)).
                extract().response();

        userId = response.body().path("result.id");
        users.deleteUser(userId);
    }

    @Test
    @Description("Create a new user without fields. \nCheck status code." +
            "\n Check response.")
    public void createUserWithEmptyBody() {

        users.createUser("{}").statusCode(200).
                body("_meta.success", equalTo(false));
    }

    @Test
    @Description("Create a new user with existing Email address. \nCheck status code and fields of the response." +
            "\n Delete created user.")
    public void createUserWithExistingEmail() {

        JSONObject requestBody = new JSONObject();
        Response response;
        String email = utils.randomEmail();
        String userId;

        requestBody.put("first_name", "TestUserFirstName");
        requestBody.put("last_name", "TestUserLastName");
        requestBody.put("email", email);
        requestBody.put("gender", "male");

        response = users.createUser(requestBody.toJSONString()).statusCode(302).
                body("result.email", equalTo(requestBody.get("email"))).
                extract().response();

        userId = response.body().path("result.id");

        users.createUser(requestBody.toJSONString()).statusCode(200).
                body("_meta.success", equalTo(false)).
                body("result[0].message", containsString("has already been taken."));

        users.deleteUser(userId);
    }

    @Test
    @Description("Create a new user with unexpected field. \nCheck status code and fields of the response.")
    public void createUserWithUnexpectedField() {

        JSONObject requestBody = new JSONObject();
        Response response;
        String email = utils.randomEmail();
        String userId;

        requestBody.put("first_name", "TestUserFirstName");
        requestBody.put("last_name", "TestUserLastName");
        requestBody.put("email", email);
        requestBody.put("gender", "male");
        requestBody.put("city", "San Jose");

        response = users.createUser(requestBody.toJSONString()).statusCode(302).
                body("result.first_name", equalTo(requestBody.get("first_name"))).
                body("result.last_name", equalTo(requestBody.get("last_name"))).
                body("result.email", equalTo(requestBody.get("email"))).
                body("result.gender", equalTo(requestBody.get("gender"))).
                body("result", not(hasKey("city"))).
                extract().response();

        userId = response.body().path("result.id");
        users.deleteUser(userId);
    }

}
