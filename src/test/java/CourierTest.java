import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierTest {
    private Steps client;

    @Before
    public void setUp() {
        client = new Steps();
    }

    Courier courier = new Courier("ninja07","1234", "saske");

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка успешного создания курьера")

    public void createCourierSuccess() {
        Response response = client.createCourier(courier);
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка ошибки при создании двух одинаковых курьеров")
    public void createDoubleCourier() {

        // Создаем курьера
        client.createCourier(courier);

        // Повторно пытаемся создать курьера с теми же парамтерами
        Response response = client.createCourier(courier);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка успешной авторизации курьера")
    public void loginCourierSuccess() {
        // Создаем курьера
        client.createCourier(courier);

        //Авторизовываемся
        Response response = client.loginCourier(courier);
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @After
    public void deleteCourierAfter () {
        //Получаем id курьера
        final Courier responseCouruer = client.loginCourier(courier)
                .then()
                .extract().as(Courier.class);

        //Удаление курьера
        client.deleteCourier(responseCouruer.getId())
                .then().statusCode(200);
    }
}