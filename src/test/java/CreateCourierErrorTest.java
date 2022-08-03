import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierErrorTest {
    private static Courier courier;
    private Steps client;

    @Before
    public void setUp() {
        client = new Steps();
    }

    public CreateCourierErrorTest(Courier courier) {
        this.courier = courier;
    }
    @Parameterized.Parameters // добавили аннотацию
    public static Object[][] getData() {
        return new Object[][] {
                {new Courier("","1234", "saske")},
                {new Courier("ninja07","", "saske")},
        };
    }
    @Test
    @DisplayName("Создание курьера, отрицательные кейсы")
    @Description("Проверка ошибки при попытке создания курьера указав недостаточное количество данных")
    //        если одного из полей нет, запрос возвращает ошибку;
    public void createCourierError() {
        Response response = client.createCourier(courier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}
