import java.util.List;

public class Main {

    static void main(String[] args) {

        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();

        var shopService = new ShopService(productRepo, orderRepo, idService);
        shopService.addOrder(List.of("1"));
        shopService.addOrder(List.of("1"));
        shopService.addOrder(List.of("1"));
        System.out.println(shopService.getListOfOrders(OrderStatus.PROCESSING));
    }

}
