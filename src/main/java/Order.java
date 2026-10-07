import lombok.With;

import java.time.Instant;
import java.util.List;

public record Order(
        String id,
        Instant timestamp,
        @With
        OrderStatus status,
        List<Product> products
) {

    public Order(String id, Instant timestamp, List<Product> products) {

        this(id, timestamp, OrderStatus.PROCESSING, products);
    }

}
