import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IdServiceTest {

    @Test
    void generateId_shouldReturnDifferentIds() {
        IdService idService = new IdService();
        String id1 = idService.generateId();
        String id2 = idService.generateId();

        assertNotEquals(id1, id2);
    }

}