import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrdersTest {
    private Steps client;

    @Before
    public void setUp() {
        client = new Steps();
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
    ResponseOrder responseOrder;
    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка успешного создания заказа")
    public void createOrder (){
        responseOrder = client.createOrder(order)
                .then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201)
                .extract().as(ResponseOrder.class);
    }

    @After

    public void deleteOrder () {
        client.deleteOrder(responseOrder.getTrack());
    }
}
