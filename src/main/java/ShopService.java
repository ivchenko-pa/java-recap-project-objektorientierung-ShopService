import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {

            productRepo.getProductById(productId).ifPresentOrElse(
                    product -> products.add(product),
                    () -> System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!")
            );
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getListOfOrders(OrderStatus orderStatus) {
        Stream<Order> ordersStream = orderRepo.getOrders().stream()
                .filter((order) -> Objects.equals(order.status(), orderStatus));
        List<Order> ordersList = ordersStream.toList();
        return ordersList;
    }
}
