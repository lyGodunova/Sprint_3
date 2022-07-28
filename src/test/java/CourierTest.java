import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Filter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierTest {
    private RequestLoggingFilter requestFilter;
    private ResponseLoggingFilter responseFilter;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        requestFilter  = new RequestLoggingFilter();
        responseFilter =  new ResponseLoggingFilter();
    }

    Courier courier = new Courier("ninja07","1234", "saske");
    @Test
    //курьера можно создать;
    //запрос возвращает правильный код ответа;
    //успешный запрос возвращает ok: true;
    // чтобы создать курьера, нужно передать в ручку все обязательные поля;
    public void createCourierSuccess() {

        Response response = given()
                .filters(requestFilter, responseFilter)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    //нельзя создать двух одинаковых курьеров;
    //если создать пользователя с логином, который уже есть, возвращается ошибка.
    public void createDoubleCourier() {

        // Создаем курьера
        given()
                .filters(requestFilter, responseFilter)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        // Повторно пытаемся создать курьера с теми же парамтерами
        Response response = given()
                .filters(requestFilter, responseFilter)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

    }

    @Test
    public void loginCourierSuccess() {
        // Создаем курьера
        given()
                .filters(requestFilter, responseFilter)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        //Авторизовываемся
        Response response = given()
                .filters(requestFilter, responseFilter)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @After
    public void deleteCourierAfter () {
        //Получаем id курьера
        final Courier responseCouruer = given()
                .filters(requestFilter, responseFilter)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .extract().as(Courier.class);

        //Удаление курьера
        given()
                .filters(requestFilter, responseFilter)
                .delete("/api/v1/courier/" + responseCouruer.getId())
                .then().statusCode(200);
    }
}
