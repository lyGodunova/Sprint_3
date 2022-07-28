import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    private RequestLoggingFilter requestFilter;
    private ResponseLoggingFilter responseFilter;
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        requestFilter  = new RequestLoggingFilter();
        responseFilter =  new ResponseLoggingFilter();
    }

    @Test
    public void loginCourierIncorrectLogin() {

        Courier courier2 = new Courier("ninja0-","1234");

        //Авторизовываемся
        Response response = given()
                .filters(requestFilter, responseFilter)
                .header("Content-type", "application/json")
                .and()
                .body(courier2)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    public void loginCourierIncorrectPassword() {

        Courier courier2 = new Courier("ninja07","8");

        //Авторизовываемся
        Response response = given()
                .filters(requestFilter, responseFilter)
                .header("Content-type", "application/json")
                .and()
                .body(courier2)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    public void loginCourierNoLogin() {

        Courier courier2 = new Courier("","1234");

        //Авторизовываемся
        Response response = given()
                .filters(requestFilter, responseFilter)
                .header("Content-type", "application/json")
                .and()
                .body(courier2)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void loginCourierNoPassword() {

        Courier courier2 = new Courier("ninja07","");

        //Авторизовываемся
        Response response = given()
                .filters(requestFilter, responseFilter)
                .header("Content-type", "application/json")
                .and()
                .body(courier2)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
}
