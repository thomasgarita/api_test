package specifications;

import helpers.RequestHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

    public static RequestSpecification generateToken(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

        String token = RequestHelper.getUserToken();

        requestSpecBuilder.addHeader("Authorization", "Bearer " + token);
        return requestSpecBuilder.build();
    };

    public static RequestSpecification generateFakeToken(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeader("Authorization", "Bearer eyJhbGcaOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NfdXVpZCI6ImIwNDU5YzVjLTA0MzctNGM1OS1iZWFhLTkzMTM2ODZhNjFjZCIsImF1dGhvcml6ZWQiOnRydWUsImV4cCI6MTYxNTg4NTU2OSwidXNlcl9pZCI6NjE0fQ.r1nm8o4ltYQ0Eo1NPENz6qPkth8gWQ1oO-axN70Dapk");
        return requestSpecBuilder.build();
    };

}
