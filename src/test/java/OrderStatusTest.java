import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderStatusTest {

    @Test
    void getDescription_shouldBeDescriptionOrderPlaced_whenNewOrderPlaced() {
        //Given
        Order order = new Order("1", Instant.now(), new ArrayList<>());
        //When
        String actualStatus = order.status().getDescription();
        assertEquals("Order placed", actualStatus);
    }


    @Test
    void getStatus_shouldBeOrderStatusProcessing_whenNewOrderPlaced() {
        //Given
        Order order = new Order("1", Instant.now(), new ArrayList<>());
        //When
        Enum actualStatus = order.status();
        assertEquals(OrderStatus.PROCESSING, actualStatus);
    }
}