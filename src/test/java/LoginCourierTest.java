import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTest {
    private Steps client;
    @Before
    public void setUp() {
        client = new Steps();
    }

    @Test
    @DisplayName("Авторизация курьера с некорректным логином")
    public void loginCourierIncorrectLogin() {

        Courier courier2 = new Courier("ninja0-","1234");

        //Авторизовываемся
        Response response = client.loginCourier(courier2);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Авторизация курьера с некорректным паролем")
    public void loginCourierIncorrectPassword() {

        Courier courier2 = new Courier("ninja07","8");

        //Авторизовываемся
        Response response = client.loginCourier(courier2);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    public void loginCourierNoLogin() {

        Courier courier2 = new Courier("","1234");

        //Авторизовываемся
        Response response = client.loginCourier(courier2);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    public void loginCourierNoPassword() {

        Courier courier2 = new Courier("ninja07","");

        //Авторизовываемся
        Response response = client.loginCourier(courier2);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Авторизация курьера с пустым body")
    public void loginCourierFatal() {
        //Авторизовываемся
        Response response = client.loginCourierWithoutBody();
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
}
