import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrdersTest {

    private RequestLoggingFilter requestFilter;
    private ResponseLoggingFilter responseFilter;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        requestFilter  = new RequestLoggingFilter();
        responseFilter =  new ResponseLoggingFilter();
    }

    private final Order order;

    public CreateOrdersTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters // добавили аннотацию
    public static Object[][] getData() {
        return new Object[][] {
                {new Order("Naruto","Uchiha","Konoha, 142 apt.","4","+7 800 355 35 35",5,"2020-06-06","Saske, come back to Konoha")},
                {new Order("Naruto","Uchiha","Konoha, 142 apt.","4","+7 800 355 35 35",5,"2020-06-06","Saske, come back to Konoha", new String[]{"BLACK"})},
                {new Order("Naruto","Uchiha","Konoha, 142 apt.","4","+7 800 355 35 35",5,"2020-06-06","Saske, come back to Konoha", new String[]{"GREY"})},
                {new Order("Naruto","Uchiha","Konoha, 142 apt.","4","+7 800 355 35 35",5,"2020-06-06","Saske, come back to Konoha", new String[]{"GREY","BLACK"})}
        };
    }

    //Order order = new Order("Naruto","Uchiha","Konoha, 142 apt.","4","+7 800 355 35 35",5,"2020-06-06","Saske, come back to Konoha");
    ResponseOrder responseOrder;
    @Test
    public void createOrder (){
        responseOrder = given()
                .filters(requestFilter, responseFilter)
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201)
                .extract().as(ResponseOrder.class);
    }

    @After

    public void deleteOrder () {
        given()
                .filters(requestFilter, responseFilter)
                .put("/api/v1/orders/cancel?track="+responseOrder.getTrack());
    }
}
