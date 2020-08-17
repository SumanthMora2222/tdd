package listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.InvalidOrderException;
import model.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import service.BookingService;


import static org.mockito.ArgumentMatchers.anyList;
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
    public void shouldProcessOrderAndPassItToBookingService() {
        Order order = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03");
        orderListener.process(Collections.singletonList(order));

        List<Order> orders = Collections.singletonList(order);
        Mockito.verify(bookingService).book(orders);
    }


    @Test
    public void shouldProcessOrder909909AndPassItToBookingService() throws JsonProcessingException {

        Order order1 = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03");
        Order order2 = new Order("909909", "QWERASDF", "PENDING", "2019-08-21T11:05:05");
        orderListener.process(Arrays.asList(order1, order2));
        List<Order> orders = Arrays.asList(order1, order2);
        Mockito.verify(bookingService).book(orders);
    }

    @Test(expected = InvalidOrderException.class)
    public void shouldThrowInvalidOrderExceptionWhenInputIsEmpty() {
        orderListener.process(Collections.emptyList());
    }

    @Test
    public void shouldSkipFutureOrders() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime futureTime = currentTime.plusDays(1);
        Order order = new Order("909909", "QWERASDF", "PENDING", futureTime.toString());
        orderListener.process(Arrays.asList(order));
        List<Order> orders = Arrays.asList(order);
        Mockito.verify(bookingService, Mockito.never()).book(anyList());
    }

    @Test
    public void shouldSkipFutureOrdersAndProcessOtherOrders() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime futureTime = currentTime.plusDays(1);
        LocalDateTime pastTime = currentTime.minusDays(1);
        Order order1 = new Order("909909", "QWERASDF", "PENDING", futureTime.toString());
        Order order2 = new Order("909909", "QWERASDF", "PENDING", pastTime.toString());
        orderListener.process(Arrays.asList(order1, order2));
        List<Order> orders = Arrays.asList(order2);
        Mockito.verify(bookingService).book(orders);
    }
}
