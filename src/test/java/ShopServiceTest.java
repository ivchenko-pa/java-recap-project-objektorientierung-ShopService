import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);
        Instant actualTimestamp = actual.timestamp();

        //THEN
        Order expected = new Order("-1", actualTimestamp, List.of(new Product("1", "Apfel")));
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenOneInvalidProductIdAndSecondIsValid_expectException() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);
        List<String> productsIds = List.of("1", "2");

        //WHEN
        try {
            shopService.addOrder(productsIds);
            fail("Expected ProductNotAvailableException not thrown, even though product 2 was ordered, that does not exist.");
            //THEN
        } catch (ProductNotFoundException e) {
            String expected = "Product mit der Id: 2 konnte nicht bestellt werden!";
            String actual = e.getMessage();
            assertEquals(expected, actual);
        }
    }

    @Test
    void getListOfOrders_shouldBeOnePROCESSINGOrder_whenOnlyOnePROCESSINGOrderInRepo() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);
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
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);

        //WHEN
        List<Order> actualFilteredOrders = shopService.getListOfOrders(OrderStatus.IN_DELIVERY);

        //THEN
        assertEquals(0, actualFilteredOrders.size());
    }

    @Test
    void updateOrder_shouldBeOneOrderIN_DELIVERY_whenUpdatedToIN_DELIVERY() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);
        List<String> productsIds = List.of("1");
        Order order = shopService.addOrder(productsIds);

        //WHEN
        shopService.updateOrder(order.id(), OrderStatus.IN_DELIVERY);

        //THEN
        List<Order> listWithUpdatedOrder = shopService.getListOfOrders(OrderStatus.IN_DELIVERY);
        assertEquals(1, listWithUpdatedOrder.size());
    }

    @Test
    void updateOrder_shouldBeZeroOrdersWithPROCESSING_whenUpdatedToIN_DELIVERYandOrderRepoIsMap() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);
        List<String> productsIds = List.of("1");
        Order order = shopService.addOrder(productsIds);

        //WHEN
        shopService.updateOrder(order.id(), OrderStatus.IN_DELIVERY);

        //THEN
        List<Order> listWithOutdatedOrder = shopService.getListOfOrders(OrderStatus.PROCESSING);
        assertEquals(0, listWithOutdatedOrder.size());
    }

    @Test
    void updateOrder_shouldBeZeroOrdersWithPROCESSING_whenUpdatedToIN_DELIVERYandOrderRepoIsList() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderListRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);
        List<String> productsIds = List.of("1");
        Order order = shopService.addOrder(productsIds);

        //WHEN
        shopService.updateOrder(order.id(), OrderStatus.IN_DELIVERY);

        //THEN
        List<Order> listWithOutdatedOrder = shopService.getListOfOrders(OrderStatus.PROCESSING);
        assertEquals(0, listWithOutdatedOrder.size());
    }

    @Test
    void updateOrder_shouldBeSameTimestamp_whenOrderStatusUpdated() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);
        List<String> productsIds = List.of("1");
        Order order = shopService.addOrder(productsIds);
        Instant expectedTimestamp = order.timestamp();

        //WHEN
        Order updatedOrder = shopService.updateOrder(order.id(), OrderStatus.IN_DELIVERY);

        //THEN
        Instant actualTimestamp = order.timestamp();
        assertEquals(expectedTimestamp, actualTimestamp);
    }
}
