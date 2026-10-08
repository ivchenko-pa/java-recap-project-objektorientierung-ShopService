import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Main {

    static void main(String[] args) {

        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();

        var shopService = new ShopService(productRepo, orderRepo, idService);

        var ordersAliasesDatabase = new HashMap<String, String>();

        try (var in = new Scanner(Path.of("src/main/resources/transactions.txt"))) {
            while (in.hasNext()){
                String command = in.nextLine();
                executeCommand(command, shopService, ordersAliasesDatabase);
            }
        } catch (IOException e) {
            throw  new RuntimeException("File not found");
        }
    }

    public static void executeCommand(String command, ShopService shopService, Map<String, String> ordersAliasesDatabase){
        String[] splittedCommand = command.split(" ");
        System.out.println(Arrays.toString(splittedCommand));
        switch (splittedCommand[0]){
            case "addOrder":
                List<String> productsList = Arrays.stream(splittedCommand)
                        .skip(2)
                        .toList();
                try {
                    Order order = shopService.addOrder(productsList);
                    ordersAliasesDatabase.put(splittedCommand[1], order.id());
                } catch (ProductNotFoundException e) {
                    System.out.println(e.getMessage());;
                }
                break;
            case "setStatus":
                String id = ordersAliasesDatabase.get(splittedCommand[1]);
                String newStatus = splittedCommand[2];
                for (OrderStatus orderStatus : OrderStatus.values()){
                    if (orderStatus.name().equals(newStatus)){
                        shopService.updateOrder(id, orderStatus);
                    }
                }
                break;
            case "printOrders":
                for(OrderStatus orderStatus : OrderStatus.values())
                System.out.println(shopService.getListOfOrders(orderStatus));
                break;
            default:
                System.out.println("Bad command");
        }
    }
}
