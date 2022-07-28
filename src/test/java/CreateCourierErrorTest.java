import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierErrorTest {
    private RequestLoggingFilter requestFilter;
    private ResponseLoggingFilter responseFilter;
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        requestFilter  = new RequestLoggingFilter();
        responseFilter =  new ResponseLoggingFilter();
    }

    private final String json;

    public CreateCourierErrorTest(String json) {
        this.json = json;
    }
    @Parameterized.Parameters // добавили аннотацию
    public static Object[][] getData() {
        return new Object[][] {
                {"{\"password\": \"1234\", \"firstName\": \"saske\"}"},
                {"{\"login\": \"ninja08\", \"firstName\": \"saske\"}"},
        };
    }
    @Test
    //        если одного из полей нет, запрос возвращает ошибку;
    public void createCourierError() {
        Response response = given()
                .filters(requestFilter, responseFilter)
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}
