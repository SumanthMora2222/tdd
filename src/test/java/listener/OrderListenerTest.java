package listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.InvalidOrderException;
import model.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import service.BookingService;


import static org.mockito.Mockito.mock;


public class OrderListenerTest {

    private BookingService bookingService;
    private OrderListener orderListener;

    @Before
    public void setUp() throws Exception {
        bookingService = mock(BookingService.class);
        orderListener = new OrderListener(bookingService);
    }

    @Test
    public void shouldProcessOrderAndPassItToBookingService() throws JsonProcessingException {
        String message = "[{\n" +
                "  \"salesOrderId\": \"123456\",\n" +
                "  \"customerId\": \"QWERASDF\",\n" +
                "  \"status\": \"PENDING\",\n" +
                "  \"timeStamp\": \"2019-08-21T11:05:03+00:00\"\n" +
                "}]";

        orderListener.process(message);
        Order order = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03+00:00");
        List<Order> orders = Collections.singletonList(order);
        Mockito.verify(bookingService).book(orders);
    }


    @Test
    public void shouldProcessOrder909909AndPassItToBookingService() throws JsonProcessingException {
        String message = "[{\n" +
                "  \"salesOrderId\": \"123456\",\n" +
                "  \"customerId\": \"QWERASDF\",\n" +
                "  \"status\": \"PENDING\",\n" +
                "  \"timeStamp\": \"2019-08-21T11:05:03+00:00\"\n" +
                "},{\n" +
                "  \"salesOrderId\": \"909909\",\n" +
                "  \"customerId\": \"QWERASDF\",\n" +
                "  \"status\": \"PENDING\",\n" +
                "  \"timeStamp\": \"2019-08-21T11:05:05+00:00\"\n" +
                "}]";

        orderListener.process(message);
        Order order1 = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03+00:00");
        Order order2 = new Order("909909", "QWERASDF", "PENDING", "2019-08-21T11:05:05+00:00");
        List<Order> orders = Arrays.asList(order1, order2);
        Mockito.verify(bookingService).book(orders);
    }

    @Test(expected = InvalidOrderException.class)
    public void shouldThrowInvalidOrderExceptionWhenInputIsEmpty() throws JsonProcessingException {
        String message = "";
        orderListener.process(message);
    }
}
