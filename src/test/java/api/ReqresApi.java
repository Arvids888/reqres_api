package api;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.failsafe.internal.util.Assert;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class ReqresApi {
    private final static String URL = "https://reqres.in";

    @Test
    public void checkAvatarAndIdTest() {
        List<UserData> users = given()

                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users?page=2")

                .then()
                .log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
//        users.forEach(x-> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
//
//        Assertions.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x->x.getId().toString()).collect(Collectors.toList());

        for (int i = 0; i<avatars.size(); i++) {
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }
}
