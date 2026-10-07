import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")));
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenOneInvalidProductIdAndSecondIsValid_expectOneValidInOrder() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertEquals(1, actual.products().size());
    }

    @Test
    void getListOfOrders_shouldBeOnePROCESSINGOrder_whenOnlyOnePROCESSINGOrderInRepo() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);

        //WHEN
        List<Order> actualFilteredOrders = shopService.getListOfOrders(OrderStatus.PROCESSING);

        //THEN
        assertEquals(1, actualFilteredOrders.size());
    }

    @Test
    void getListOfOrders_shouldBeZeroIN_DELIVERYOrders_whenOnlyOnePROCESSINGOrderInRepo() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);

        //WHEN
        List<Order> actualFilteredOrders = shopService.getListOfOrders(OrderStatus.IN_DELIVERY);

        //THEN
        assertEquals(0, actualFilteredOrders.size());
    }
}
