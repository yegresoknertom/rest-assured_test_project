package rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.Property;

public class BaseRest {

    protected Property property = new Property();
    public RequestSpecification requestSpec;
    public ResponseSpecification responseSpec;

    public BaseRest() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri(property.getGorestBaseUrl()).
                addHeader("Authorization", property.getAccessToken()).
                log(LogDetail.ALL).
                setContentType("application/json").
                build();

        responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }

}
