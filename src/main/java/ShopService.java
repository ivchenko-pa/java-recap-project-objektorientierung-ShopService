import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            try {
                Product product = productRepo.getProductById(productId).orElseThrow();
                products.add(product);
            } catch (NoSuchElementException e) {
                var productNotFoundException = new ProductNotFoundException("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                productNotFoundException.initCause(e);
                throw productNotFoundException;
            }
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), Instant.now(), products);

        return orderRepo.addOrder(newOrder);
    }

    public Order updateOrder(String orderId, OrderStatus status){
        Order order = orderRepo.getOrderById(orderId);
        orderRepo.removeOrder(orderId);
        orderRepo.addOrder(order.withStatus(status));
        return orderRepo.getOrderById(orderId);
    }

    public List<Order> getListOfOrders(OrderStatus orderStatus) {
        Stream<Order> ordersStream = orderRepo.getOrders().stream()
                .filter((order) -> Objects.equals(order.status(), orderStatus));
        List<Order> ordersList = ordersStream.toList();
        return ordersList;
    }
}
