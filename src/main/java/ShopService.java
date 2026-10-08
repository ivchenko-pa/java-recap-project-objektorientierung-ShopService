import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final IdService idService;

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

        Order newOrder = new Order(idService.generateId(), Instant.now(), products);

        return orderRepo.addOrder(newOrder);
    }

    public Order updateOrder(String orderId, OrderStatus status) {
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

    public Map<OrderStatus, Order> getOldestOrderPerStatus() {
        Map<OrderStatus, Order> pendingOrders = new HashMap<>();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            List<Order> ordersGroupedByStatusAndSourtedByTimestamp = orderRepo.getOrders().stream()
                    .filter(order -> order.status().equals(orderStatus))
                    .sorted((order1, order2) -> order1.timestamp().compareTo(order2.timestamp()))
                    .toList();

            if (ordersGroupedByStatusAndSourtedByTimestamp.size() > 0) {
                pendingOrders.put(orderStatus, ordersGroupedByStatusAndSourtedByTimestamp.get(0));
            }
        }
        return pendingOrders;
    }
}
