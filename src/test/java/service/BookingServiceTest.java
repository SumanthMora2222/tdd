package service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


import java.util.Arrays;
import java.util.List;
import model.Order;
import org.junit.Test;

public class BookingServiceTest {
  @Test
  public void shouldStoreOrder() {
    BookingRepository bookingRepository = mock(BookingRepository.class);

    final BookingService bookingService = new BookingService(bookingRepository);


    final Order order = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03+00:00");
    final List<Order> orders = Arrays.asList(order);
    bookingService.book(orders);

    verify(bookingRepository).add(orders);
  }
}
