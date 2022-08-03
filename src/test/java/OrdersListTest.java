import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class OrdersListTest {
    private Steps client;

    @Before
    public void setUp() {
        client = new Steps();
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrdersList () {
       OrdersList ordersList = client.getOrder()
                .then()
                .statusCode(200)
                .extract().as(OrdersList.class);
        Assert.assertNotNull(ordersList);
        Assert.assertTrue(ordersList.getOrders().getClass() == ArrayList.class);
        Assert.assertTrue(ordersList.getOrders().size() > 0);
    }
}
