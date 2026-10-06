import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderStatusTest {

    @Test
    void getDescription_shouldBeDescriptionOrderPlaced_whenNewOrderPlaced() {
        //Given
        Order order = new Order("1", new ArrayList<>());
        //When
        String actualStatus = order.status().getDescription();
        assertEquals("Order placed", actualStatus);
    }

    @Test
    void values() {
    }

    @Test
    void getStatus_shouldBeOrderStatusProcessing_whenNewOrderPlaced() {
        //Given
        Order order = new Order("1", new ArrayList<>());
        //When
        Enum actualStatus = order.status();
        assertEquals(OrderStatus.PROCESSING, actualStatus);
    }
}