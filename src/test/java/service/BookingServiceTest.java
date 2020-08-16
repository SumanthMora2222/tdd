package service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Arrays;
import java.util.List;
import model.Order;
import org.junit.Test;

public class BookingServiceTest {

  @Test
  public void shouldPublishOrdersIfSavedSuccessfully() {
    BookingPublisher bookingPublisher = mock(BookingPublisher.class);
    BookingRepository bookingRepository = mock(BookingRepository.class);

    final BookingService bookingService = new BookingService(bookingRepository, bookingPublisher);

    final Order order = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03+00:00");
    final List<Order> orders = Arrays.asList(order);

    when(bookingRepository.add(orders)).thenReturn(true);

    bookingService.book(orders);

    verify(bookingPublisher).publish(orders);
  }

  @Test
  public void shouldNotPublishOrdersIfNotSaved() {
    BookingPublisher bookingPublisher = mock(BookingPublisher.class);
    BookingRepository bookingRepository = mock(BookingRepository.class);

    final BookingService bookingService = new BookingService(bookingRepository, bookingPublisher);

    final Order order = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03+00:00");
    final List<Order> orders = Arrays.asList(order);

    when(bookingRepository.add(orders)).thenReturn(false);

    bookingService.book(orders);

    verify(bookingPublisher,never()).publish(orders);
  }
}
