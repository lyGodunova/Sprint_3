import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

public class Steps {
    public static final String URL = "http://qa-scooter.praktikum-services.ru/";
    private final RequestLoggingFilter requestFilter  = new RequestLoggingFilter();
    private final ResponseLoggingFilter responseFilter = new ResponseLoggingFilter();

    @Step("Вызов апи POST /api/v1/courier")
    public Response createCourier(Courier courier) {
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .baseUri(URL)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step ("Вызов апи POST /api/v1/courier/login")
    public Response loginCourier(Courier courier){
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .baseUri(URL)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step ("Вызов апи POST /api/v1/courier/login без body")
    public Response loginCourierWithoutBody(){
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .baseUri(URL)
                .header("Content-type", "application/json")
                .and()
                .when()
                .post("/api/v1/courier/login");
    }

    @Step ("Вызов апи DELETE /api/v1/courier/{ID}")
    public Response deleteCourier(String id) {
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .baseUri(URL)
                .delete("/api/v1/courier/" + id);
    }

    @Step("Вызов апи POST /api/v1/orders")
    public Response createOrder(Order order){
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .baseUri(URL)
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Вызов апи PUT /api/v1/orders/cancel?track={track}")
    public Response deleteOrder(String track) {
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .baseUri(URL)
                .put("/api/v1/orders/cancel?track="+track);
    }

    @Step("Вызов апи GET /api/v1/orders")
    public Response getOrder (){
        return RestAssured.with()
                .filters(requestFilter, responseFilter)
                .baseUri(URL)
                .when()
                .get("/api/v1/orders");
    }
}
